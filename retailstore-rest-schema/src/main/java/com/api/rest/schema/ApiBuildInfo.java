package com.api.rest.schema;

public record ApiBuildInfo(
        String deployedBranch,
        String artifactBuildVersion,
        String artifactBuildTime,
        String lastCommitId,
        String lastCommitMessage,
        String lasCommitTime,
        String buildHost,
        String buildUser
) {
}
