package com.api.domain;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

public class HealthStatus {

    private final long id;
    private final String eventId;
    private final String status;

    public HealthStatus(long id, String eventId, String content) {
        this.id = id;
        this.eventId = eventId;
        this.status = content;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getEventId() {
        return eventId;
    }
}
