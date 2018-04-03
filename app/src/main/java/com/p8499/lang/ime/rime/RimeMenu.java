package com.p8499.lang.ime.rime;

/**
 * Created by Administrator on 12/27/2017.
 */

public class RimeMenu {
    public Integer pageSize;
    public Integer pageNo;
    public Integer isLastPage;
    public Integer highlightedCandidateIndex;
    public Integer numCandidates;
    public RimeCandidate[] candidates;
    public String selectKeys;

    public Integer getPageSize() {
        return pageSize;
    }

    public RimeMenu setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public RimeMenu setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public Integer getIsLastPage() {
        return isLastPage;
    }

    public RimeMenu setIsLastPage(Integer isLastPage) {
        this.isLastPage = isLastPage;
        return this;
    }

    public Integer getHighlightedCandidateIndex() {
        return highlightedCandidateIndex;
    }

    public RimeMenu setHighlightedCandidateIndex(Integer highlightedCandidateIndex) {
        this.highlightedCandidateIndex = highlightedCandidateIndex;
        return this;
    }

    public Integer getNumCandidates() {
        return numCandidates;
    }

    public RimeMenu setNumCandidates(Integer numCandidates) {
        this.numCandidates = numCandidates;
        return this;
    }

    public RimeCandidate[] getCandidates() {
        return candidates;
    }

    public RimeMenu setCandidates(RimeCandidate[] candidates) {
        this.candidates = candidates;
        return this;
    }

    public String getSelectKeys() {
        return selectKeys;
    }

    public RimeMenu setSelectKeys(String selectKeys) {
        this.selectKeys = selectKeys;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{\"pageSize\":%d,\"pageNo\":%d,\"isLastPage\":%d,\"highlightedCandidateIndex\":%d,\"numCandidates\":%s,\"candidates\":\"%s\",selectKeys:\"%s\"}", pageSize, pageNo, isLastPage, highlightedCandidateIndex, numCandidates, toStringCandidates(), selectKeys);
    }

    private String toStringCandidates() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for (int i = 0; candidates == null ? false : i < candidates.length; i++) {
            buffer.append(candidates[i]);
            if (i < candidates.length - 1)
                buffer.append(",");
        }
        buffer.append("]");
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RimeMenu) {
            RimeMenu menu = (RimeMenu) obj;
            return menu != null
                    && (Utils.equals(pageSize, menu.pageSize))
                    && (Utils.equals(pageNo, menu.pageNo))
                    && (Utils.equals(isLastPage, menu.isLastPage))
                    && (Utils.equals(highlightedCandidateIndex, menu.highlightedCandidateIndex))
                    && (Utils.equals(numCandidates, menu.numCandidates))
                    && (Utils.equals(candidates, menu.candidates))
                    && (Utils.equals(selectKeys, menu.selectKeys));
        }
        return false;
    }
}
