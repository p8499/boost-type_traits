package com.p8499.lang.ime;

import android.content.res.Configuration;
import android.inputmethodservice.InputMethodService;
import android.os.SystemClock;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.p8499.lang.ime.rime.RimeCommit;
import com.p8499.lang.ime.rime.RimeContext;
import com.p8499.lang.ime.rime.RimeTraits;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 12/28/2017.
 * Bug: nguzy@ puts cursor before @
 * Bug: ngu before candidatesView attached cause error: getHandler() returs null
 */

public class ServiceIme extends InputMethodService implements RimeSession.RimeSessionListener {
    protected ViewCandidates mCandidatesView;
    protected ViewInput mInputView;
    private boolean isRime = true;//TODO temp variable
    private RimeSession mRimeSession;
    private List<KeyEventListener> mListenerList;

    //region [static methods]

    /**
     * generate an ACTION_DOWN key event
     *
     * @param keyCode
     * @return
     */
    public static KeyEvent simulateKeyDownEvent(int keyCode) {
        long down = SystemClock.uptimeMillis();
        return new KeyEvent(down, down, KeyEvent.ACTION_DOWN, keyCode, 0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);
    }

    /**
     * generate an ACTION_UP key event
     *
     * @param downEvent
     * @return
     */
    public static KeyEvent simulateKeyUpEvent(KeyEvent downEvent) {
        return new KeyEvent(downEvent.getDownTime(), SystemClock.uptimeMillis(), KeyEvent.ACTION_UP, downEvent.getKeyCode(), 0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);
    }

    /**
     * generates rimeCode & rimeMask,
     * if keyCode+meta is a unicode char, ie printable char like, space(=20), numbers or alphabets etc, return rimeCode(lookup from Uf8Rime) & rimeMask(-Shift)
     * else if keyCode+meta is a control, ie DELETE, or SHIFT etc, return rimeCode(lookup from KeyCodeRime) & rimeMask  --\n(RETURN) is here
     * else return rimeCode(VoidSymbol) & rimeMask
     *
     * @param keyCode
     * @param event
     * @return
     */
    public static int[] key2Rime(int keyCode, KeyEvent event) {
        int rimeCode = JniWrapper.KEY_VOIDSYMBOL;
        int rimeMask = Relation.lookupMetaRimeCombination(event.getMetaState());
        if (rimeCode == JniWrapper.KEY_VOIDSYMBOL) {
            int unicode = event.getUnicodeChar();
            if (unicode > 0) {
                Relation utf8Rime = Relation.lookupUtf8Rime(unicode);
                if (utf8Rime != null) {
                    rimeCode = utf8Rime.getThruValue();
                    rimeMask &= ~JniWrapper.META_SHIFT;
                }
            }
        }
        if (rimeCode == JniWrapper.KEY_VOIDSYMBOL) {
            Relation keycodeRime = Relation.lookupKeycodeRime(keyCode);
            if (keycodeRime != null) {
                rimeCode = keycodeRime.getThruValue();
            }
        }
        return new int[]{rimeCode, rimeMask};
    }

    /**
     * generate rimeCode
     * if unicode exists in Utf8Rime, return rimeCode(lookup from Utf8Rime)
     * else return rimeCode(VoidSymbol)
     *
     * @param unicode
     * @return
     */
    public static int[] utf82Rime(int unicode) {
        int rimeCode = JniWrapper.KEY_VOIDSYMBOL;
        if (rimeCode == JniWrapper.KEY_VOIDSYMBOL) {
            Relation utf8Rime = Relation.lookupUtf8Rime(unicode);
            if (utf8Rime != null) {
                rimeCode = utf8Rime.getThruValue();
            }
        }
        return new int[]{rimeCode, 0};
    }
    //endregion

    //region [public methods]

    /**
     * this method is how the IME process the key event of both it's down and up.
     * promise that the event will be consumed.
     * add-on components can invoke this method to simulate the keyboard event
     * triggers: ServiceIme.KeyEventListener
     * triggers: RimeSession.RimeSessionListener if rime accepts
     * //     * ->.sendKeyEvent
     * //     * ---> .onKeyDown
     * //     * -----> mRimeSession.processKeyDown
     * //     * -----> later .sendDownUpKeyEvents
     * //     * ---> .onKeyUp
     * //     * -----> mRimeSession.processKeyDown
     *
     * @param keyCode
     * @return
     */
    public void sendKeyEvent(int keyCode) {
        KeyEvent downEvent = ServiceIme.simulateKeyDownEvent(keyCode), upEvent = ServiceIme.simulateKeyUpEvent(downEvent);
        boolean resultDown = onKeyDown(keyCode, downEvent), resultUp = onKeyUp(keyCode, upEvent);
        if (!resultDown && !resultUp)
            Flowable.just(keyCode)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .doOnNext(c -> sendDownUpKeyEvents(keyCode))
                    .subscribe();
    }

    /**
     * this method is how the IME process a unicode char
     * promise that the event will be consumed.
     * add-on components (especially soft keyboard) can invoke this method to simulate the unicode char
     * triggers: RimeSession.RimeSessionListener if rime accepts
     * //     * ->.sendUnicode
     * //     * ---> mRimeSession.processKeyDown
     * //     * ---> mRimeSession.processKeyUp
     * //     * ---> later .sendKeyChar
     *
     * @param unicode
     * @return
     */
    public void sendUnicode(int unicode) {
        int[] rime = ServiceIme.utf82Rime(unicode);
        boolean resultDown = mRimeSession.processKeyDown(rime[0], 0), resultUp = mRimeSession.processKeyUp(rime[0], 0);
        if (!resultDown && !resultUp)
            Flowable.just(unicode)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .doOnNext(c -> sendKeyChar((char) unicode))
                    .subscribe();
    }

    /**
     * ServiceIme's implementation for onKeyDown
     *
     * @param event
     * @return
     */
    private boolean processKeyDownEvent(KeyEvent event) {
        for (KeyEventListener listener : mListenerList)
            if (listener.preKeyEvent(true, event.getKeyCode(), event))
                return true;
        int[] rime = ServiceIme.key2Rime(event.getKeyCode(), event);
        boolean result = mRimeSession.processKeyDown(rime[0], rime[1]);
        for (KeyEventListener listener : mListenerList)
            listener.postKeyEvent(true, event.getKeyCode(), event, result);
        return result;
    }

    /**
     * ServiceIme's implementation for onKeyUp
     *
     * @param event
     * @return
     */
    private boolean processKeyUpEvent(KeyEvent event) {
        for (KeyEventListener listener : mListenerList)
            if (listener.preKeyEvent(false, event.getKeyCode(), event))
                return true;
        int[] rime = ServiceIme.key2Rime(event.getKeyCode(), event);
        boolean result = mRimeSession.processKeyUp(rime[0], rime[1]);
        for (KeyEventListener listener : mListenerList)
            listener.postKeyEvent(false, event.getKeyCode(), event, result);
        return result;
    }

    //endregion

    //region [IME lifecycle]

    /**
     * When the ime service is activated, this event and the following will be fired.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize RIME
        RimeTraits traits = new RimeTraits();
        traits.appName = "ime";
        traits.sharedDataDir = "/data/data/com.p8499.lang.ime/files";
        traits.userDataDir = "/data/data/com.p8499.lang.ime/files";
        JniWrapper.startup(traits);
        JniWrapper.rimeSetup();
        JniWrapper.rimeInitialize();
//        if (JniWrapper.rimeStartMaintenance(1).intValue() == 1)
//            JniWrapper.rimeJoinMaintenanceThread();
        Relation.initialize(getResources());
        mListenerList = new ArrayList<>();
        mCandidatesView = ViewCandidates.create(getLayoutInflater()).setService(this);
        mInputView = ViewInput.create(getLayoutInflater()).setService(this);
    }

    /**
     * When switch between hard/soft keyboard, this event would be fired
     *
     * @param newConfig
     */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * When rotate the screen, this event and the following will be fired.
     */
    @Override
    public void onInitializeInterface() {
        super.onInitializeInterface();
//        Flowable.empty()
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnComplete(() -> setCandidatesViewShown(true))
//                .subscribe();
    }

    /**
     * TODO
     */
    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        Flowable.just(RimeSession.create().setOption("soft_cursor", 1).setOption("simplification", 1))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(session -> mCandidatesView.setRimeSession(session))
                .doOnNext(session -> mInputView.setRimeSession(session))
                .doOnNext(session -> setRimeSession(session))
                .subscribe();
    }

    @Override
    public View onCreateInputView() {
        ViewGroup vg = (ViewGroup) mInputView.getParent();
        if (vg != null)
            vg.removeView(mInputView);
        return mInputView;
    }

    @Override
    public View onCreateCandidatesView() {
        ViewGroup vg = (ViewGroup) mCandidatesView.getParent();
        if (vg != null)
            vg.removeView(mCandidatesView);
        return mCandidatesView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = processKeyDownEvent(event) || super.onKeyDown(keyCode, event);
//        if (!result)
//            Log.d("zzz", "down not acceptable");
        return result;
//        getCurrentInputConnection().setComposingText("aaa", 1);
//        getCurrentInputConnection().commitText("aaa", 1);
//        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean result = processKeyUpEvent(event) || super.onKeyUp(keyCode, event);
//        if (!result)
//            Log.d("zzz", "up not acceptable");
        return result;
    }

//    @Override
//    public void onComputeInsets(InputMethodService.Insets outInsets) {
//        super.onComputeInsets(outInsets);
//        if (!isFullscreenMode()) {
//            outInsets.contentTopInsets = outInsets.visibleTopInsets;
//        }
//    }

//    /**
//     * each time system show or hide input view (switching soft/hard keyboard), let the candidate view shown
//     */
//    @Override
//    public void updateInputViewShown() {
//        super.updateInputViewShown();
//        setCandidatesViewShown(true);
//    }

    /**
     * TODO
     */
    @Override
    public void onFinishInput() {
        Flowable.empty()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> mRimeSession.destroy())
                .doOnComplete(() -> mRimeSession = null)
                .subscribe();
        super.onFinishInput();
    }

    /**
     * TODO
     */
    @Override
    public void onDestroy() {
        JniWrapper.rimeCleanupAllSessions();
        JniWrapper.rimeFinalize();
        JniWrapper.shutdown();
        super.onDestroy();
    }

    //endregion

    //region [ServiceIme.KeyEventListener server]
    public boolean hasKeyEventListener(KeyEventListener listener) {
        return mListenerList.contains(listener);
    }

    public ServiceIme addKeyEventListener(KeyEventListener listener) {
        mListenerList.add(listener);
        return this;
    }

    //endregion

    //region [RimeSession.RimeSessionListener client]
    public ServiceIme setRimeSession(RimeSession s) {
        mRimeSession = s;
        if (!s.hasRimeSessionListener(this))
            s.addRimeSessionListener(this);
        return this;
    }

    @Override
    public boolean preRimeProcessKey(RimeSession session, int code, int meta) {
        return false;
    }

    @Override
    public void postRimeProcessKey(RimeSession session, int code, int meta, boolean result) {
        setCandidatesViewShown(session.getRimeCandidateList().size() > 0);
        if (result)
            compose();
        commit();
//        applyRimeToInputConnection(context, commit);

        //        Flowable.empty()
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnComplete(() -> setCandidatesViewShown(session.getRimeCandidateList().size() > 0))
//                .subscribe();

//        Flowable.empty()
//                .observeOn(AndroidSchedulers.mainThread())
////                .doOnComplete(() -> applyRimeToInputConnection(context, commit))
//                .doOnComplete(() -> Option.ofObj(session.getRimeCandidateList()).filter(cl -> cl.size() > 0 && getCandidatesHiddenVisibility() != View.VISIBLE || cl.size() == 0 && getCandidatesHiddenVisibility() == View.VISIBLE)
//                        //if candidates view should show or should hide
//                        .ifSome(m -> setCandidatesViewShown(session.getRimeCandidateList().size() > 0)))
//                .subscribe();
    }

    @Override
    public boolean preRimeSelectCandidate(RimeSession session, int index) {
        return false;
    }

    @Override
    public void postRimeSelectCandidate(RimeSession session, int index, boolean result) {
        setCandidatesViewShown(session.getRimeCandidateList().size() > 0);
        if (result)
            compose();
        commit();

//        applyRimeToInputConnection(context, commit);

//        Flowable.empty()
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnComplete(() -> setCandidatesViewShown(session.getRimeCandidateList().size() > 0))
//                .subscribe();

//        Flowable.empty()
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnComplete(() -> Option.ofObj(result).filter(r -> r)
//                        //if result = true
//                        .ifSome(r -> applyRimeToInputConnection(context, commit))
//                        .ifSome(r -> setCandidatesViewShown(false)))
//                .subscribe();
    }

    @Override
    public void preRimeDestroy(RimeSession session) {

    }

    @Override
    public void postRimeDestroy(RimeSession session) {

    }

    private void compose() {
        InputConnection conn = getCurrentInputConnection();
        RimeContext context = mRimeSession.getRimeContext();
        if (conn != null && context != null) {
            String preview = context.commitTextPreview;
            conn.setComposingText(preview == null ? "" : preview, 1);
        }
    }

    private void commit() {
        InputConnection conn = getCurrentInputConnection();
        RimeCommit commit = mRimeSession.getRimeCommit();
        if (conn != null && commit != null) {
            String text = commit.text;
            conn.commitText(text, 1);
        }
    }
//    private void applyRimeToInputConnection(RimeContext context, RimeCommit commit) {
//        InputConnection conn = getCurrentInputConnection();
//        if (conn != null) {
//            if (context != null) {
//                String preview = context.commitTextPreview;
//                conn.setComposingText(preview == null ? "" : preview, 1);
//            }
//            if (commit != null) {
//                String text = commit.text;
//                conn.commitText(text, 1);
//            }
//        }
//    }

    //endregion

    interface KeyEventListener {
        boolean preKeyEvent(boolean down, int keyCode, KeyEvent event);

        void postKeyEvent(boolean down, int keyCode, KeyEvent event, boolean result);
    }
}