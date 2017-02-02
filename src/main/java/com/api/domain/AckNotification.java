package com.api.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by prayagupd
 * on 1/30/17.
 */

public class AckNotification {
    @Getter @Setter private String eventId;
    @Getter @Setter private String status;

    public AckNotification(String eventId, String status) {
        this.eventId = eventId;
        this.status = status;
    }
}
