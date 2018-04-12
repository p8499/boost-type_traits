package com.p8499.lang.ime;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyyoona7.lib.EasyPopup;
import com.zyyoona7.lib.HorizontalGravity;
import com.zyyoona7.lib.VerticalGravity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by Administrator on 1/27/2018.
 */

public class ViewInput extends LinearLayout implements KeyboardView.OnKeyboardActionListener, ServiceIme.KeyEventListener, RimeSession.RimeSessionListener {
    /**
     * the ime owns me
     */
    protected ServiceIme mIme;
    protected RimeSession mRimeSession;

    protected List<String> mKeyboardNames;
    protected List<Keyboard> mLowerKeyboards;
    protected List<Keyboard> mUpperKeyboards;

    @BindView(R.id.menu)
    protected RelativeLayout mMenu;
    @BindView(R.id.menu_keyboard)
    protected ImageView mMenuKeyboard;
    protected EasyPopup mMenuKeyboardPopup;
    @BindView(R.id.menu_settings)
    protected ImageView mMenuSettings;

    @BindView(R.id.keyboard)
    protected KeyboardView mKeyboard;

    /**
     * Each keyboard has different capsEabled value
     */
    private boolean mCapsEnabled;
    /**
     * Only when isShift=true, indicate whether Shift or SHIFT
     */
    private boolean mCaps;
    /**
     * If capsEnabled, this value will be set to true each time shift was pressed at the 1st time
     * and set to false after timer time up or quickly pressed the 2nd time
     */
    private boolean mCapsWaiting;
    /**
     * Invoke this handler to set capsWaiting=false
     */
    private Handler mCapsWaitingHandler;
    /**
     * used in menu_keyboard.onTouch()
     */
    private int mDownItem;
    private float mDownX;
    private float mDownY;

    //region [initialization]
    public ViewInput(Context context) {
        this(context, null);
    }

    public ViewInput(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewInput(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /**
     * When the input view is initiated, ie still an empty view,
     * all the keyboards are getting created.
     */
    public ViewInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mKeyboardNames = new ArrayList<>();
        mLowerKeyboards = new ArrayList<>();
        mUpperKeyboards = new ArrayList<>();
        //add qwerty
        mKeyboardNames.add(getResources().getString(R.string.service_ime_qwerty));
        mLowerKeyboards.add(new Keyboard(getContext(), R.xml.keyboard_qwerty_lower));
        mUpperKeyboards.add(new Keyboard(getContext(), R.xml.keyboard_qwerty_upper));
        //add symbolic
        mKeyboardNames.add(getResources().getString(R.string.service_ime_symbols));
        mLowerKeyboards.add(new Keyboard(getContext(), R.xml.keyboard_symbolic_lower));
        mUpperKeyboards.add(new Keyboard(getContext(), R.xml.keyboard_symbolic_upper));
        //TODO add symbolic and numeric keyboard here
        //TODO add input type specified keyboard here
        //
        popup1Create();
        mCapsWaitingHandler = new Handler(msg -> mCapsWaiting = false);
    }

    public static ViewInput create(LayoutInflater inflater) {
        return (ViewInput) inflater.inflate(R.layout.service_ime_input, null, false);
    }

    /**
     * When the input view is inflated,
     * The keyboard view handle is got.
     * Then determine which keyboard would be shown first.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mKeyboard.setOnKeyboardActionListener(this);
        mKeyboard.setKeyboard(mLowerKeyboards.get(0));//TODO change to show the last keyboard
        mCapsEnabled = true;//TODO change to show the last keyboard
    }

    //endregion

    //region [form control]
    @OnTouch(R.id.menu_keyboard)
    public boolean onMenuKeyboardTouch(View v, MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mMenuKeyboard.setPressed(true);
                /*place popup1*/
                popup1Show();
                /*select popup1 item with current keyboard*/
                popup1ItemSelect(getKeyboardIndex());
                /*the original item position when touch down*/
                mDownItem = popup1ItemSelected();
                mDownX = e.getX();
                mDownY = e.getY();
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                float distance = e.getY() - mDownY;
                /*target: item position the user targeting at. firstly assuming it equals the original selected position*/
                int target = mDownItem;
                /*thresholdFrom, thresholdTo: the distance scope that target holds*/
                float thresholdFrom = -popup1ItemHeight(target) / 2;
                float thresholdTo = +popup1ItemHeight(target) / 2;
                /*let's calculate target*/
                if (distance < thresholdFrom)
                    /*y is above down's y*/
                    while (distance < thresholdFrom && target > 0) {
                        target -= 1;
                        thresholdTo = thresholdFrom;
                        thresholdFrom -= popup1ItemHeight(target);
                    }
                else if (distance > thresholdTo)
                    /*y is below down's y*/
                    while (distance > thresholdTo && target < popup1ItemCount() - 1) {
                        target += 1;
                        thresholdFrom = thresholdTo;
                        thresholdTo += popup1ItemHeight(target);
                    }
                if (target != popup1ItemSelected())
                    popup1ItemSelect(target);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                mMenuKeyboard.setPressed(false);
                /*hide popup1*/
                mMenuKeyboardPopup.dismiss();
                int selected = popup1ItemSelected();
                Keyboard old = mKeyboard.getKeyboard();
                /*switch keyboard if item changed*/
                if (old != mLowerKeyboards.get(selected) && old != mUpperKeyboards.get(selected))
                    mKeyboard.setKeyboard(mLowerKeyboards.get(selected));
                return true;
            }
            default:
                return false;
        }

    }

    @OnClick(R.id.menu_settings)
    public void onMenuSettingsClick(View v) {
        Intent intent = new Intent(getContext(), ActivityPreference.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sessionId", mRimeSession.getRimeSessionId());
        getContext().startActivity(intent);
    }
    //endregion

    //region [ServiceIme.KeyEventListener client]
    public ViewInput setService(ServiceIme i) {
        mIme = i;
        if (!i.hasKeyEventListener(this))
            i.addKeyEventListener(this);
        return this;
    }

    @Override
    public boolean preKeyEvent(boolean down, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void postKeyEvent(boolean down, int keyCode, KeyEvent event, boolean result) {
    }
    //endregion

    //region [RimeSession.RimeSessionListener client]
    public ViewInput setRimeSession(RimeSession s) {
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
    }

    @Override
    public boolean preRimeSelectCandidate(RimeSession session, int index) {
        return false;
    }

    @Override
    public void postRimeSelectCandidate(RimeSession session, int index, boolean result) {
    }

    @Override
    public void preRimeDestroy(RimeSession session) {

    }

    @Override
    public void postRimeDestroy(RimeSession session) {

    }
    //endregion

    //region [KeyboardView.OnKeyboardActionListener client]
    @Override
    public void onPress(int primaryCode) {
        Log.d("xxxxxxOnKeyboardAction", String.format("onPress, primaryCode=%d", primaryCode));
    }

    @Override
    public void onRelease(int primaryCode) {
        Log.d("xxxxxxOnKeyboardAction", String.format("onRelease, primaryCode=%d", primaryCode));
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (keyCodes.length == 12)
            if (primaryCode > 0)
                switch (keyCodes[1]) {
                    case 1:
                        //keys: KeyEvent.XXX, just like press the hard keyboard
                        mIme.sendKeyEvent(keyCodes[0]);
                        break;
                    case 0:
                        //chars: http://www.unicode.org/Public/11.0.0/ucd/UnicodeData-11.0.0d11.txt, send it to RIME engine
                        mIme.sendUnicode(keyCodes[0]);
                        break;
                    default:
                        break;
                }
            else
                switch (primaryCode) {
                    case Keyboard.KEYCODE_SHIFT:
                        //any operation like switching keyboard or shift status will affect the keyboardview itself only
                        handleShift();
                        break;
                    case Keyboard.KEYCODE_DELETE:
                        onKey(KeyEvent.KEYCODE_DEL, new int[]{KeyEvent.KEYCODE_DEL, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                        break;
                    default:
                        break;
                }
    }

    @Override
    public void onText(CharSequence text) {
        Log.d("xxxxxxOnKeyboardAction", String.format("onText, primaryCode=%s", text));
    }

    @Override
    public void swipeLeft() {
        Log.d("xxxxxxOnKeyboardAction", "swipeLeft");
    }

    @Override
    public void swipeRight() {
        Log.d("xxxxxxOnKeyboardAction", "swipeRight");
    }

    @Override
    public void swipeDown() {
        Log.d("xxxxxxOnKeyboardAction", "swipeDown");
    }

    @Override
    public void swipeUp() {
        Log.d("xxxxxxOnKeyboardAction", "swipeUp");
    }

    private void handleShift() {
        Keyboard current = mKeyboard.getKeyboard();
        if (mLowerKeyboards.contains(current)) {
            //shift -> Shift
            handleShift1(current, mUpperKeyboards.get(mLowerKeyboards.indexOf(current)));
        } else if (mUpperKeyboards.contains(current) && mCapsWaiting) {
            //Shift -> SHIFT
            handleShift2(mLowerKeyboards.get(mUpperKeyboards.indexOf(current)), current);
        } else if (mUpperKeyboards.contains(current) && !mCapsWaiting) {
            //Shift/SHIFT -> shift
            handleShift3(mLowerKeyboards.get(mUpperKeyboards.indexOf(current)), current);
        }
    }

    private void handleShift1(Keyboard lower, Keyboard upper) {
        mCapsWaitingHandler.removeMessages(0);
        mKeyboard.setKeyboard(upper);
        mKeyboard.setShifted(true);
        upper.getKeys().get(upper.getShiftKeyIndex()).icon = getResources().getDrawable(R.drawable.ic_shifted_black_24dp, getContext().getTheme());
        mCaps = false;
        if (mCapsEnabled) {
            mCapsWaiting = true;
            mCapsWaitingHandler.sendEmptyMessageDelayed(0, 800);
        } else {
            mCapsWaiting = false;
        }
    }

    private void handleShift2(Keyboard lower, Keyboard upper) {
        mCapsWaitingHandler.removeMessages(0);
        mKeyboard.setShifted(true);
        upper.getKeys().get(upper.getShiftKeyIndex()).icon = getResources().getDrawable(R.drawable.ic_caps_black_24dp, getContext().getTheme());
        mCaps = true;
        mCapsWaiting = false;
    }

    private void handleShift3(Keyboard lower, Keyboard upper) {
        mCapsWaitingHandler.removeMessages(0);
        mKeyboard.setKeyboard(lower);
        mKeyboard.setShifted(false);
        lower.getKeys().get(lower.getShiftKeyIndex()).icon = getResources().getDrawable(R.drawable.ic_shift_black_24dp, getContext().getTheme());
    }
    //endregion

    //region [Menu Keyboard Popup]
    private void popup1Create() {
        ViewGroup popup1 = (ViewGroup) inflate(getContext(), R.layout.service_ime_input_popup1, null);
        if (mKeyboardNames.size() == 1) {
            TextView item = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.service_ime_input_popup1_itemsingle, popup1, false);
            item.setText(mKeyboardNames.get(0));
            popup1.addView(item);
        } else {
            for (int i = 0; i < mKeyboardNames.size(); i++) {
                TextView item = (TextView) LayoutInflater.from(getContext()).inflate(
                        i == 0 ? R.layout.service_ime_input_popup1_itemfirst
                                : i == mKeyboardNames.size() - 1 ? R.layout.service_ime_input_popup1_itemlast
                                : R.layout.service_ime_input_popup1_itemmiddle, popup1, false);
                item.setText(mKeyboardNames.get(i));
                popup1.addView(item);
            }
        }
        mMenuKeyboardPopup = new EasyPopup(getContext()).setContentView(popup1)
                .setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setFocusAndOutsideEnable(true)
                .setFocusable(false)
                .setOutsideTouchable(false)
                .createPopup();
    }

    private void popup1Show() {
        mMenuKeyboardPopup.showAtAnchorView(mMenuKeyboard, VerticalGravity.ABOVE, HorizontalGravity.ALIGN_LEFT, 16, -32);
    }

    private int popup1ItemCount() {
        ViewGroup container = (ViewGroup) mMenuKeyboardPopup.getContentView();
        return container.getChildCount();
    }

    private void popup1ItemSelect(int position) {
        ViewGroup container = (ViewGroup) mMenuKeyboardPopup.getContentView();
        for (int i = 0; i < container.getChildCount(); i++)
            container.getChildAt(i).setSelected(i == position);
    }

    private int popup1ItemSelected() {
        ViewGroup container = (ViewGroup) mMenuKeyboardPopup.getContentView();
        for (int i = 0; i < container.getChildCount(); i++)
            if (container.getChildAt(i).isSelected())
                return i;
        return -1;
    }

    private int popup1ItemHeight(int position) {
        ViewGroup container = (ViewGroup) mMenuKeyboardPopup.getContentView();
        return container.getChildAt(position).getHeight();
    }

    //endregion
    //region [utilities]
    private int getKeyboardIndex() {
        for (int i = 0; i < mLowerKeyboards.size(); i++)
            if (mKeyboard.getKeyboard() == mLowerKeyboards.get(i) || mKeyboard.getKeyboard() == mUpperKeyboards.get(i))
                return i;
        return -1;
    }
    //endregion
}
