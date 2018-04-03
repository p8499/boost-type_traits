package com.p8499.lang.ime.rime;

/**
 * Created by Administrator on 12/27/2017.
 */

public class RimeComposition {
    public Integer length;
    public Integer cursorPos;
    public Integer selStart;
    public Integer selEnd;
    public String preedit;

    public Integer getLength() {
        return length;
    }

    public RimeComposition setLength(Integer length) {
        this.length = length;
        return this;
    }

    public Integer getCursorPos() {
        return cursorPos;
    }

    public RimeComposition setCursorPos(Integer cursorPos) {
        this.cursorPos = cursorPos;
        return this;
    }

    public Integer getSelStart() {
        return selStart;
    }

    public RimeComposition setSelStart(Integer selStart) {
        this.selStart = selStart;
        return this;
    }

    public Integer getSelEnd() {
        return selEnd;
    }

    public RimeComposition setSelEnd(Integer selEnd) {
        this.selEnd = selEnd;
        return this;
    }

    public String getPreedit() {
        return preedit;
    }

    public RimeComposition setPreedit(String preedit) {
        this.preedit = preedit;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{\"length\":%d,\"cursorPos\":%d,\"selStart\":%d,\"selEnd\":%d,\"preedit\":\"%s\"}", length, cursorPos, selStart, selEnd, preedit);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RimeComposition) {
            RimeComposition composition = (RimeComposition) obj;
            return composition != null
                    && (Utils.equals(length, composition.length))
                    && (Utils.equals(cursorPos, composition.cursorPos))
                    && (Utils.equals(selStart, composition.selStart))
                    && (Utils.equals(selEnd, composition.selEnd))
                    && (Utils.equals(preedit, composition.preedit));
        }
        return false;
    }
}
