package com.api.events;

import eventstream.events.BaseEvent;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by prayagupd
 * on 2/2/17.
 */

public class TestIngestionEvent extends BaseEvent {
    @Getter @Setter private String field1;
}
