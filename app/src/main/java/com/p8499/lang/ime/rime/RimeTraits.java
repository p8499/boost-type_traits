package com.p8499.lang.ime.rime;

/**
 * Created by Administrator on 12/27/2017.
 */

public class RimeTraits {
    public Integer dataSize;
    public String sharedDataDir;
    public String userDataDir;
    public String distributionName;
    public String distributionCodeName;
    public String distributionVersion;
    public String appName;
    public String[] modules;

    public Integer getDataSize() {
        return dataSize;
    }

    public RimeTraits setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
        return this;
    }

    public String getSharedDataDir() {
        return sharedDataDir;
    }

    public RimeTraits setSharedDataDir(String sharedDataDir) {
        this.sharedDataDir = sharedDataDir;
        return this;
    }

    public String getUserDataDir() {
        return userDataDir;
    }

    public RimeTraits setUserDataDir(String userDataDir) {
        this.userDataDir = userDataDir;
        return this;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public RimeTraits setDistributionName(String distributionName) {
        this.distributionName = distributionName;
        return this;
    }

    public String getDistributionCodeName() {
        return distributionCodeName;
    }

    public RimeTraits setDistributionCodeName(String distributionCodeName) {
        this.distributionCodeName = distributionCodeName;
        return this;
    }

    public String getDistributionVersion() {
        return distributionVersion;
    }

    public RimeTraits setDistributionVersion(String distributionVersion) {
        this.distributionVersion = distributionVersion;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public RimeTraits setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String[] getModules() {
        return modules;
    }

    public RimeTraits setModules(String[] modules) {
        this.modules = modules;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{\"dataSize\":%d,\"sharedDataDir\":\"%s\",\"userDataDir\":\"%s\",\"distributionName\":\"%s\",\"distributionCodeName\":\"%s\",\"distributionVersion\":\"%s\",\"appName\":\"%s\",\"modules\":\"%s\"}", dataSize, sharedDataDir, userDataDir, distributionName, distributionCodeName, distributionVersion, appName, toStringModules());
    }

    private String toStringModules() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for (int i = 0; modules == null ? false : i < modules.length; i++) {
            buffer.append(String.format("\"%s\"", modules[0]));
            if (i < modules.length)
                buffer.append(",");
        }
        buffer.append("]");
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RimeTraits) {
            RimeTraits traits = (RimeTraits) obj;
            return traits != null
                    && (Utils.equals(dataSize, traits.dataSize))
                    && (Utils.equals(sharedDataDir, traits.sharedDataDir))
                    && (Utils.equals(userDataDir, traits.userDataDir))
                    && (Utils.equals(distributionName, traits.distributionName))
                    && (Utils.equals(distributionCodeName, traits.distributionCodeName))
                    && (Utils.equals(distributionVersion, traits.distributionVersion))
                    && (Utils.equals(appName, traits.appName))
                    && (Utils.equals(modules, traits.modules));
        }
        return false;
    }
}
