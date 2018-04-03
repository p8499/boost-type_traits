package com.p8499.lang.ime.rime;

/**
 * Created by Administrator on 12/27/2017.
 */

public class RimeContext {
    public Integer dataSize;
    public RimeComposition composition;
    public RimeMenu menu;
    public String commitTextPreview;
    public String[] selectLabels;

    public Integer getDataSize() {
        return dataSize;
    }

    public RimeContext setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
        return this;
    }

    public RimeComposition getComposition() {
        return composition;
    }

    public RimeContext setComposition(RimeComposition composition) {
        this.composition = composition;
        return this;
    }

    public RimeMenu getMenu() {
        return menu;
    }

    public RimeContext setMenu(RimeMenu menu) {
        this.menu = menu;
        return this;
    }

    public String getCommitTextPreview() {
        return commitTextPreview;
    }

    public RimeContext setCommitTextPreview(String commitTextPreview) {
        this.commitTextPreview = commitTextPreview;
        return this;
    }

    public String[] getSelectLabels() {
        return selectLabels;
    }

    public RimeContext setSelectLabels(String[] selectLabels) {
        this.selectLabels = selectLabels;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{\"dataSize\":%d,\"composition\":%s,\"menu\":%s,commitTextPreview:\"%s\",selectLabels:%s}", dataSize, composition, menu, commitTextPreview, toStringSelectLabels());
    }

    private String toStringSelectLabels() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for (int i = 0; selectLabels == null ? false : i < selectLabels.length; i++) {
            buffer.append(String.format("\"%s\"", selectLabels[0]));
            if (i < selectLabels.length - 1)
                buffer.append(",");
        }
        buffer.append("]");
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RimeContext) {
            RimeContext context = (RimeContext) obj;
            return context != null
                    && (Utils.equals(dataSize, context.dataSize))
                    && (Utils.equals(composition, context.composition))
                    && (Utils.equals(menu, context.menu))
                    && (Utils.equals(commitTextPreview, context.commitTextPreview))
                    && (Utils.equals(selectLabels, context.selectLabels));
        }
        return false;
    }
}

