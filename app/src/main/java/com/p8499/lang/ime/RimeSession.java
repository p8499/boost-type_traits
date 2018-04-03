package com.p8499.lang.ime;

import com.p8499.lang.ime.rime.RimeCandidate;
import com.p8499.lang.ime.rime.RimeCommit;
import com.p8499.lang.ime.rime.RimeContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/8/2018.
 */

public class RimeSession {
    private Long mRimeSessionId;
    private Integer mPageSize;
    private RimeContext mRimeContext;
    private String mRimeInput;
    private List<RimeCandidate> mRimeCandidateList;
    private RimeCommit mRimeCommit;
    private List<RimeSessionListener> mListenerList;

    private RimeSession(long rimeSessionId) {
        mRimeSessionId = rimeSessionId;
        mPageSize = 5;//TODO
        mListenerList = new ArrayList<>();
    }

    public static RimeSession create() {
        return new RimeSession(JniWrapper.rimeCreateSession());
    }

    public long getRimeSessionId() {
        return mRimeSessionId == null ? 0 : mRimeSessionId.longValue();
    }

    public int getPageSize() {
        return mPageSize == null ? 0 : mPageSize.intValue();
    }

    public boolean hasRimeSessionListener(RimeSessionListener listener) {
        return mListenerList.contains(listener);
    }

    public RimeSession addRimeSessionListener(RimeSessionListener listener) {
        mListenerList.add(listener);
        return this;
    }

    public RimeSession setOption(String option, int value) {
        JniWrapper.rimeSetOption(mRimeSessionId, option, value);
        return this;
    }

    public boolean processKeyDown(int code, int meta) {
        return processKeyInternal(code, meta);
    }

    public boolean processKeyUp(int code, int meta) {
        return processKeyInternal(code, meta | JniWrapper.META_RELEASE);
    }

    protected boolean processKeyInternal(int code, int meta) {
        for (RimeSessionListener listener : mListenerList)
            if (listener.preRimeProcessKey(this, code, meta))
                return true;
        boolean result = JniWrapper.rimeProcessKey(mRimeSessionId, code, meta).intValue() == 1;
        clearContext();
        for (RimeSessionListener listener : mListenerList)
            listener.postRimeProcessKey(this, code, meta, result);
        return result;
    }

    public RimeContext getRimeContext() {
        return mRimeContext == null ? mRimeContext = JniWrapper.rimeGetContext(mRimeSessionId) : mRimeContext;
    }

    public String getRimeInput() {
        return mRimeInput == null ? mRimeInput = JniWrapper.rimeGetInput(mRimeSessionId) : mRimeInput;
    }

    public List<RimeCandidate> getRimeCandidateList() {
        return mRimeCandidateList == null ? mRimeCandidateList = JniWrapper.rimeGetAllCandidates(mRimeSessionId, 999999) : mRimeCandidateList;
    }

    public RimeCommit getRimeCommit() {
        return mRimeCommit == null ? mRimeCommit = JniWrapper.rimeGetCommit(mRimeSessionId) : mRimeCommit;
    }

    public boolean selectCandidateOnCurrentPage(int index) {
        for (RimeSessionListener listener : mListenerList)
            if (listener.preRimeSelectCandidate(this, getRimeContext().menu.pageNo * mPageSize + index))
                return true;
        boolean result = JniWrapper.rimeSelectCandidateOnCurrentPage(mRimeSessionId, index) == 1;
        clearContext();
        for (RimeSessionListener listener : mListenerList)
            listener.postRimeSelectCandidate(this, getRimeContext().menu.pageNo * mPageSize + index, result);
        return result;
    }

    private void clearContext() {
        mRimeContext = null;
        mRimeInput = null;
        mRimeCandidateList = null;
        mRimeCommit = null;
    }

    public void destroy() {
        for (RimeSessionListener listener : mListenerList)
            listener.preRimeDestroy(this);
        JniWrapper.rimeDestroySession(mRimeSessionId);
        mRimeSessionId = null;
        for (RimeSessionListener listener : mListenerList)
            listener.postRimeDestroy(this);
    }

    public interface RimeSessionListener {
        boolean preRimeProcessKey(RimeSession session, int code, int meta);

        void postRimeProcessKey(RimeSession session, int code, int meta, boolean result);

        boolean preRimeSelectCandidate(RimeSession session, int index);

        void postRimeSelectCandidate(RimeSession session, int index, boolean result);

        void preRimeDestroy(RimeSession session);

        void postRimeDestroy(RimeSession session);
    }
}
