package com.p8499.lang.ime;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.p8499.lang.ime.rime.RimeCandidate;
import com.p8499.lang.ime.rime.RimeComposition;
import com.p8499.lang.ime.rime.RimeMenu;
import com.p8499.lang.ime.rime.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2/13/2018.
 */

public class ViewCandidates extends LinearLayout implements ServiceIme.KeyEventListener, RimeSession.RimeSessionListener {
    /**
     * the ime owns me
     */
    protected ServiceIme mIme;
    protected RimeSession mRimeSession;
    /**
     * data to present: the composition
     */
    protected RimeComposition mComposition;

    @BindView(R.id.preedit)
    protected LinearLayout mPreeditView;
    @BindView(R.id.compbefore)
    protected TextView mCompbefore;
    @BindView(R.id.compsel)
    protected TextView mCompsel;
    @BindView(R.id.compafter)
    protected TextView mCompafter;

    @BindView(R.id.cands)
    protected RecyclerView mCandsView;

    //region [initialization]
    public ViewCandidates(Context context) {
        this(context, null);
    }

    public ViewCandidates(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewCandidates(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ViewCandidates(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public static ViewCandidates create(LayoutInflater inflater) {
        return (ViewCandidates) inflater.inflate(R.layout.service_ime_candidates, null, false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mCandsView.setAdapter(new CandsAdapter());
        mCandsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        mCandsView.addOnScrollListener(new CandsOnScrollListener());
    }
    //endregion

    //region [ServiceIme.KeyEventListener client]
    public ViewCandidates setService(ServiceIme i) {
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
        //after keyboard navigating, scroll to that candidate
//        Flowable.empty()
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnComplete(() -> Option.ofObj(keyCode).filter(code -> result && (code == KeyEvent.KEYCODE_PAGE_UP || code == KeyEvent.KEYCODE_PAGE_DOWN || code == KeyEvent.KEYCODE_DPAD_UP || code == KeyEvent.KEYCODE_DPAD_DOWN))
//                        .ifSome(code -> mCandsView.getLayoutManager().scrollToPosition(mRimeSession.getRimeContext().menu.pageNo * mRimeSession.getPageSize() + mRimeSession.getRimeContext().menu.highlightedCandidateIndex)))
//                .subscribe();
    }
    //endregion

    //region [RimeSession.RimeSessionListener client: reload preedit and cands and scroll to the current candidate]
    public ViewCandidates setRimeSession(RimeSession s) {
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
        Flowable.empty()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> reloadPreedit(session.getRimeContext().composition))
                .doOnComplete(() -> reloadCands(session.getRimeCandidateList(), session.getRimeContext().menu))
                .doOnComplete(() -> mCandsView.getLayoutManager().scrollToPosition(mRimeSession.getRimeContext().menu.pageNo * mRimeSession.getPageSize() + mRimeSession.getRimeContext().menu.highlightedCandidateIndex))
                .subscribe();
    }

    @Override
    public boolean preRimeSelectCandidate(RimeSession session, int index) {
        return false;
    }

    @Override
    public void postRimeSelectCandidate(RimeSession session, int index, boolean result) {
        Flowable.empty()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> reloadPreedit(session.getRimeContext().composition))
                .doOnComplete(() -> reloadCands(session.getRimeCandidateList(), session.getRimeContext().menu))
                .doOnComplete(() -> mCandsView.getLayoutManager().scrollToPosition(mRimeSession.getRimeContext().menu.pageNo * mRimeSession.getPageSize() + mRimeSession.getRimeContext().menu.highlightedCandidateIndex))
                .subscribe();
    }

    @Override
    public void preRimeDestroy(RimeSession session) {

    }

    @Override
    public void postRimeDestroy(RimeSession session) {
        Flowable.empty()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> reloadPreedit(new RimeComposition()))
                .doOnComplete(() -> reloadCands(new ArrayList<>(), new RimeMenu()))
                .subscribe();
    }

    private ViewCandidates reloadPreedit(RimeComposition c) {
        boolean needUpdate = false;
        if (!Utils.equals(mComposition, c)) {
            mComposition = c;
            needUpdate = true;
        }
        if (needUpdate) {
            StringBuffer sbBefore = new StringBuffer();
            StringBuffer sbSel = new StringBuffer();
            StringBuffer sbAfter = new StringBuffer();
            for (int i = 0; mComposition.preedit != null && i < mComposition.preedit.length(); i++) {
                //i0 is preedit[i]'s first byte position
                int i0 = mComposition.preedit.substring(0, i).getBytes().length;
                //i0 is preedit[i]'s last byte position (exclusive)
                int i1 = i0 + mComposition.preedit.substring(i, i + 1).getBytes().length;
                (i1 < mComposition.selStart ? sbBefore : mComposition.selEnd <= i0 ? sbAfter : sbSel)
                        .append(String.valueOf(mComposition.preedit.charAt(i)));
            }
            mCompbefore.setText(sbBefore.toString());
            mCompsel.setText(sbSel.toString());
            mCompafter.setText(sbAfter.toString());
//            mPreeditView.removeAllViews();
//            //place the preedit chars
//            for (int i = 0; mComposition.preedit != null && i < mComposition.preedit.length(); i++) {
//                TextView textView = (TextView) mIme.getLayoutInflater().inflate(R.layout.service_ime_candidates_composition_text, mPreeditView, false);
//                //set its text
//                textView.setText(String.valueOf(mComposition.preedit.charAt(i)));
//                //set its tag
//                textView.setTag(i);
//                //i0 is preedit[i]'s first byte position
//                int i0 = mComposition.preedit.substring(0, i).getBytes().length;
//                //i0 is preedit[i]'s last byte position (exclusive)
//                int i1 = i0 + mComposition.preedit.substring(i, i + 1).getBytes().length;
//                //set its background
//                if (mComposition.selStart <= i0 && i1 <= mComposition.selEnd) {
//                    //if preedit.bytes[start to end] is only 1 char
//                    textView.setBackground(getResources().getDrawable(new String(Arrays.copyOfRange(mComposition.preedit.getBytes(), mComposition.selStart, mComposition.selEnd)).length() == 1 ? R.drawable.bg_componly_grey
//                            //if preedit[i]'s left edge = start
//                            : i0 == mComposition.selStart ? R.drawable.bg_compfirst_grey
//                            //if preedit[i]'s right edge = end
//                            : i1 == mComposition.selEnd ? R.drawable.bg_complast_grey
//                            //if preedit[i]'s is not first or last
//                            : R.drawable.bg_compmiddle_grey, getContext().getTheme()));
//                }
//                //set its text color
//                textView.setTextColor(getResources().getColorStateList(i1 <= mComposition.selStart ? R.color.tx_compbefore_grey
//                        : mComposition.selEnd <= i0 ? R.color.tx_compafter_grey
//                        : R.color.tx_compsel_grey));
//                mPreeditView.addView(textView);
            //if this is a soft cursor, scroll to proper position
//                if (mComposition.preedit.charAt(i) == '‸')
//                    getHandler().postAtFrontOfQueue(() -> {
//                        //to prevent the code run if it is fired too late while cursor is already removed from preeditView
//                        if (textView.getParent() != null) {
//                            //from rect.left to rect.right is the visible part of preeditView related to preeditView itself
//                            Rect rect = new Rect();
//                            mPreeditView.getLocalVisibleRect(rect);
//                            //cursor.getLeft(), cursor.getRight() is the cursor's left and right edge related to preeditView
//                            if (textView.getLeft() < rect.left)
//                                //scroll to cursor's left
//                                scrollTo(textView.getLeft(), getScrollY());
//                            else if (textView.getRight() > rect.right)
//                                //scroll to cursor's right
//                                scrollTo(textView.getRight() - rect.width(), getScrollY());
//                        }
//                    });TODO
        }
        return this;
    }

    private ViewCandidates reloadCands(List<RimeCandidate> l, RimeMenu m) {
        boolean needUpdate = false;
        CandsAdapter adapter = (CandsAdapter) mCandsView.getAdapter();
        List<RimeCandidate> oldCandidateList = adapter.getCandidateList();
        RimeMenu oldMenu = adapter.getMenu();
        if (!Utils.equals(oldCandidateList, l)) {
            adapter.setCandidateList(l);
            needUpdate = true;
        }
        if (!Utils.equals(oldMenu, m)) {
            adapter.setMenu(m);
            needUpdate = true;
        }
        if (needUpdate) {
//            if (oldMenu != null && oldMenu.pageNo.intValue() != m.pageNo.intValue())
//                adapter.notifyItemRangeChanged(oldMenu.pageNo * mRimeSession.getPageSize(), oldMenu.numCandidates);
//            adapter.notifyItemRangeChanged(m.pageNo * mRimeSession.getPageSize(), m.numCandidates);
            adapter.notifyDataSetChanged();
        }
        return this;
    }
//endregion

//    private ViewCandidates highlight(int ind) {
//        RimeMenu menu = ((CandsAdapter) mCandsView.getAdapter()).mMenu;
//        int current = menu.pageNo * mRimeSession.getPageSize() + menu.highlightedCandidateIndex;
//        if (current < ind) {
//            int distance = ind - current;
//            int quotient = distance / mRimeSession.getPageSize();
//            int remainder = distance % mRimeSession.getPageSize();
//            for (int i = 0; i < quotient; i++) {
//                mRimeSession.processKeyDown(JniWrapper.KEY_PAGE_DOWN, 0);
//                mRimeSession.processKeyUp(JniWrapper.KEY_PAGE_DOWN, 0);
//                Log.d("p8498", String.format("%s", mRimeSession.getRimeContext().menu.candidates[mRimeSession.getRimeContext().menu.highlightedCandidateIndex].text));
//            }
//            for (int i = 0; i < remainder; i++) {
//                mRimeSession.processKeyDown(JniWrapper.KEY_DOWN, 0);
//                mRimeSession.processKeyUp(JniWrapper.KEY_DOWN, 0);
//                Log.d("p8498", String.format("%s", mRimeSession.getRimeContext().menu.candidates[mRimeSession.getRimeContext().menu.highlightedCandidateIndex].text));
//            }
//        } else if (ind < current) {
//            int distance = current - ind;
//            int quotient = distance / mRimeSession.getPageSize();
//            int remainder = distance % mRimeSession.getPageSize();
//            for (int i = 0; i < quotient; i++) {
//                mRimeSession.processKeyDown(JniWrapper.KEY_PAGE_UP, 0);
//                mRimeSession.processKeyUp(JniWrapper.KEY_PAGE_UP, 0);
//            }
//            for (int i = 0; i < remainder; i++) {
//                mRimeSession.processKeyDown(JniWrapper.KEY_UP, 0);
//                mRimeSession.processKeyUp(JniWrapper.KEY_UP, 0);
//            }
//        }
//        return this;
//    }

    class CandsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.index)
        public TextView mIndex;
        @BindView(R.id.text)
        public TextView mText;

        public CandsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CandsAdapter extends RecyclerView.Adapter<CandsViewHolder> {
        /**
         * data to present: all candidates
         */
        protected List<RimeCandidate> mCandidateList;
        /**
         * data to present: current page
         */
        protected RimeMenu mMenu;

        public List<RimeCandidate> getCandidateList() {
            return mCandidateList;
        }

        public CandsAdapter setCandidateList(List<RimeCandidate> mCandidateList) {
            this.mCandidateList = mCandidateList;
            return this;
        }

        public RimeMenu getMenu() {
            return mMenu;
        }

        public CandsAdapter setMenu(RimeMenu mMenu) {
            this.mMenu = mMenu;
            return this;
        }

        @Override
        public CandsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CandsViewHolder(mIme.getLayoutInflater().inflate(R.layout.service_ime_candidates_menu_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(CandsViewHolder holder, int position) {
            //text
            holder.mText.setText(mCandidateList.get(position).text);
            if (mMenu.pageNo * mRimeSession.getPageSize() <= position && position < (mMenu.pageNo + 1) * mRimeSession.getPageSize()) {
                //if within current page & hard keyboard: activated
                holder.itemView.setActivated(!mIme.isInputViewShown());
                //if within current page: has index
                holder.mIndex.setText(String.valueOf(position % mRimeSession.getPageSize() + 1));
                //if hard keyboard: index shown
                holder.mIndex.setVisibility(!mIme.isInputViewShown() ? View.VISIBLE : View.INVISIBLE);
                //if within current page & highlighted & hard keyboard: selected
                holder.mText.setActivated(!mIme.isInputViewShown() ? position == mMenu.pageNo * mRimeSession.getPageSize() + mMenu.highlightedCandidateIndex : false);
            } else {
                //if without current page, deactivated
                holder.itemView.setActivated(false);
                //if without current page, has no index
                holder.mIndex.setText(" ");
                //if hard keyboard: index shown
                holder.mIndex.setVisibility(!mIme.isInputViewShown() ? View.VISIBLE : View.INVISIBLE);
                //if without current page, deselected
                holder.mText.setActivated(false);
            }
            holder.itemView.setOnClickListener(view -> mRimeSession.selectCandidateOnCurrentPage(position % mRimeSession.getPageSize()));
        }

        @Override
        public int getItemCount() {
            return mCandidateList == null ? 0 : mCandidateList.size();
        }
    }

//    class CandsOnScrollListener extends RecyclerView.OnScrollListener {
//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//        }
//
//        //TODO debug this, why strange
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            LinearLayoutManager layoutManager = (LinearLayoutManager) mCandsView.getLayoutManager();
//            Flowable.empty()
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnComplete(() ->
//                            Option.ofObj(dx).filter(x -> x > 0)
//                                    //if discover the right
//                                    .ifSome(x -> Option.ofObj(layoutManager.findLastCompletelyVisibleItemPosition()).filter(lastComplete -> lastComplete > -1)
//                                            //lastComplete or lastPartial
//                                            .orOption(() -> Option.ofObj(layoutManager.findLastVisibleItemPosition()))
//                                            //highlight
//                                            .ifSome(last -> highlight(last))))
//                    .doOnComplete(() ->
//                            Option.ofObj(dx).filter(x -> x < 0)
//                                    //if discover the left
//                                    .ifSome(x -> Option.ofObj(layoutManager.findFirstCompletelyVisibleItemPosition()).filter(firstComplete -> firstComplete > -1)
//                                            //firstComplete or firstPartial
//                                            .orOption(() -> Option.ofObj(layoutManager.findFirstVisibleItemPosition()))
//                                            //highlight
//                                            .ifSome(first -> highlight(first))))
//                    .subscribe();
//        }
//    }
}
