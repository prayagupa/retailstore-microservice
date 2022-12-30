package com.api.rest.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ApiBuildInfo {
    private String deployedBranch;
    private String artifactBuildVersion;
    private String artifactBuildTime;
    private String lastCommitId;
    private String lastCommitMessage;
    private String lasCommitTime;
    private String buildHost;
    private String buildUser;
}
