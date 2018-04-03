package com.p8499.lang.ime.rime;

/**
 * Created by Administrator on 12/27/2017.
 */

public class RimeStatus {
    public Integer dataSize;
    public String schemaId;
    public String schemaName;
    public Integer isDisabled;
    public Integer isComposing;
    public Integer isAsciiMode;
    public Integer isFullShape;
    public Integer isSimplified;
    public Integer isTraditional;
    public Integer isAsciiPunct;

    public Integer getDataSize() {
        return dataSize;
    }

    public RimeStatus setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
        return this;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public RimeStatus setSchemaId(String schemaId) {
        this.schemaId = schemaId;
        return this;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public RimeStatus setSchemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public RimeStatus setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
        return this;
    }

    public Integer getIsComposing() {
        return isComposing;
    }

    public RimeStatus setIsComposing(Integer isComposing) {
        this.isComposing = isComposing;
        return this;
    }

    public Integer getIsAsciiMode() {
        return isAsciiMode;
    }

    public RimeStatus setIsAsciiMode(Integer isAsciiMode) {
        this.isAsciiMode = isAsciiMode;
        return this;
    }

    public Integer getIsFullShape() {
        return isFullShape;
    }

    public RimeStatus setIsFullShape(Integer isFullShape) {
        this.isFullShape = isFullShape;
        return this;
    }

    public Integer getIsSimplified() {
        return isSimplified;
    }

    public RimeStatus setIsSimplified(Integer isSimplified) {
        this.isSimplified = isSimplified;
        return this;
    }

    public Integer getIsTraditional() {
        return isTraditional;
    }

    public RimeStatus setIsTraditional(Integer isTraditional) {
        this.isTraditional = isTraditional;
        return this;
    }

    public Integer getIsAsciiPunct() {
        return isAsciiPunct;
    }

    public RimeStatus setIsAsciiPunct(Integer isAsciiPunct) {
        this.isAsciiPunct = isAsciiPunct;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{\"dataSize\":%d,\"schemaId\":\"%s\",\"schemaName\":\"%s\",\"isDisabled\":%d,\"isComposing\":%d,\"isAsciiMode\":%d,\"isFullShape\":%d,\"isSimplified\":%d,\"isTraditional\":%d,\"isAsciiPunct\":%d}", dataSize, schemaId, schemaName, isDisabled, isComposing, isAsciiMode, isFullShape, isSimplified, isTraditional, isAsciiPunct);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RimeStatus) {
            RimeStatus status = (RimeStatus) obj;
            return status != null
                    && (Utils.equals(dataSize, status.dataSize))
                    && (Utils.equals(schemaId, status.schemaId))
                    && (Utils.equals(schemaName, status.schemaName))
                    && (Utils.equals(isDisabled, status.isDisabled))
                    && (Utils.equals(isComposing, status.isComposing))
                    && (Utils.equals(isAsciiMode, status.isAsciiMode))
                    && (Utils.equals(isFullShape, status.isFullShape))
                    && (Utils.equals(isSimplified, status.isSimplified))
                    && (Utils.equals(isTraditional, status.isTraditional))
                    && (Utils.equals(isAsciiPunct, status.isAsciiPunct));
        }
        return false;
    }
}
