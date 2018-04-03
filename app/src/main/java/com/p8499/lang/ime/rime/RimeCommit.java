package com.p8499.lang.ime.rime;

/**
 * Created by Administrator on 12/27/2017.
 */

public class RimeCommit {
    public Integer dataSize;
    public String text;

    public Integer getDataSize() {
        return dataSize;
    }

    public RimeCommit setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
        return this;
    }

    public String getText() {
        return text;
    }

    public RimeCommit setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{\"dataSize\":%d,\"text\":\"%s\"}", dataSize, text);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RimeCommit) {
            RimeCommit commit = (RimeCommit) obj;
            return commit != null
                    && (Utils.equals(dataSize, commit.dataSize))
                    && (Utils.equals(text, commit.text));
        }
        return false;
    }
}
