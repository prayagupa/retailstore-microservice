package com.api.rest.schema;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

//FIXME make it non spring
@Component
public class ApiBuildInfo {

    @Value("${git.branch}")
    private String deployedBranch;

    @Value("${git.build.version}")
    private String artifactBuildVersion;

    @Value("${git.build.time}")
    private String artifactBuildTime;

    @Value("${git.commit.id}")
    private String lastCommitId;

    @Value("${git.commit.message.short}")
    private String lastCommitMessage;

    @Value("${git.commit.time}")
    private String lasCommitTime;

    @Value("${git.build.host}")
    private String buildHost;

    @Value("${git.build.user.email}")
    private String buildUser;

    public ApiBuildInfo() {
    }

    public String getDeployedBranch() {
        return deployedBranch;
    }

    public String getArtifactBuildVersion() {
        return artifactBuildVersion;
    }

    public String getArtifactBuildTime() {
        return artifactBuildTime;
    }

    public String getLastCommitId() {
        return lastCommitId;
    }

    public String getLastCommitMessage() {
        return lastCommitMessage;
    }

    public String getLasCommitTime() {
        return lasCommitTime;
    }

    public String getBuildHost() {
        return buildHost;
    }

    public String getBuildUser() {
        return buildUser;
    }
}
