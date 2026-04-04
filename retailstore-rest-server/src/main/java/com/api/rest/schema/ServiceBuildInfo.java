package com.api.rest.schema;

import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("java:S1134")
@ConfigurationProperties(prefix = "git")
public record ServiceBuildInfo(
        String branch,
        Build build,
        Commit commit
) {
    public record Build(String version,
                        String time,
                        String host,
                        String userEmail) {}

    public record Commit(String id,
                         Message message,
                         String time) {}

    public record Message(String shortMessage) {}
}
