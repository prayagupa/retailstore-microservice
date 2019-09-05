package com.api.rest.schema;

public class ApiBuildInfo {
    private String deployedBranch;
    private String artifactBuildVersion;
    private String artifactBuildTime;
    private String lastCommitId;
    private String lastCommitMessage;
    private String lasCommitTime;
    private String buildHost;
    private String buildUser;

    public ApiBuildInfo(String deployedBranch,
                        String artifactBuildVersion,
                        String artifactBuildTime,
                        String lastCommitId,
                        String lastCommitMessage,
                        String lasCommitTime,
                        String buildHost,
                        String buildUser) {
        this.deployedBranch = deployedBranch;
        this.artifactBuildVersion = artifactBuildVersion;
        this.artifactBuildTime = artifactBuildTime;
        this.lastCommitId = lastCommitId;
        this.lastCommitMessage = lastCommitMessage;
        this.lasCommitTime = lasCommitTime;
        this.buildHost = buildHost;
        this.buildUser = buildUser;
    }

    public String getDeployedBranch() {
        return deployedBranch;
    }

    public void setDeployedBranch(String deployedBranch) {
        this.deployedBranch = deployedBranch;
    }

    public String getArtifactBuildVersion() {
        return artifactBuildVersion;
    }

    public void setArtifactBuildVersion(String artifactBuildVersion) {
        this.artifactBuildVersion = artifactBuildVersion;
    }

    public String getArtifactBuildTime() {
        return artifactBuildTime;
    }

    public void setArtifactBuildTime(String artifactBuildTime) {
        this.artifactBuildTime = artifactBuildTime;
    }

    public String getLastCommitId() {
        return lastCommitId;
    }

    public void setLastCommitId(String lastCommitId) {
        this.lastCommitId = lastCommitId;
    }

    public String getLastCommitMessage() {
        return lastCommitMessage;
    }

    public void setLastCommitMessage(String lastCommitMessage) {
        this.lastCommitMessage = lastCommitMessage;
    }

    public String getLasCommitTime() {
        return lasCommitTime;
    }

    public void setLasCommitTime(String lasCommitTime) {
        this.lasCommitTime = lasCommitTime;
    }

    public String getBuildHost() {
        return buildHost;
    }

    public void setBuildHost(String buildHost) {
        this.buildHost = buildHost;
    }

    public String getBuildUser() {
        return buildUser;
    }

    public void setBuildUser(String buildUser) {
        this.buildUser = buildUser;
    }
}
