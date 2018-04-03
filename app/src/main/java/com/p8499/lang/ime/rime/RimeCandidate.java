package com.p8499.lang.ime.rime;

/**
 * Created by Administrator on 12/27/2017.
 */

public class RimeCandidate {
    public String text;
    public String comment;
    public Object reserved;

    public String getText() {
        return text;
    }

    public RimeCandidate setText(String text) {
        this.text = text;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public RimeCandidate setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Object getReserved() {
        return reserved;
    }

    public RimeCandidate setReserved(Object reserved) {
        this.reserved = reserved;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{\"text\":\"%s\",\"comment\":\"%s\"}", text, comment);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RimeCandidate) {
            RimeCandidate candidate = (RimeCandidate) obj;
            return candidate != null
                    && (Utils.equals(text, candidate.text))
                    && (Utils.equals(comment, candidate.comment));
        }
        return false;
    }
}
