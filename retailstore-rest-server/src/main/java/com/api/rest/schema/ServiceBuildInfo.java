package com.api.rest.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//FIXME make it non spring
@Component
@NoArgsConstructor
@Data
@SuppressWarnings("java:S1134")
public class ServiceBuildInfo {

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
}
