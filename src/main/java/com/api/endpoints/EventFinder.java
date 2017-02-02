package com.api.endpoints;

import eventstream.events.BaseEvent;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/**
 * Created by prayagupd
 * on 2/1/17.
 */

@Component
public class EventFinder {

    //TODO inject from properties
    public String EVENTS_PACKAGE = "com.api";

    public Optional<Class<? extends BaseEvent>> eventType(String type){
        Reflections reflections = new Reflections(EVENTS_PACKAGE);
        Set<Class<? extends BaseEvent>> eventTypes = reflections.getSubTypesOf(BaseEvent.class);
        return eventTypes.stream().filter(e -> e.getSimpleName().equals(type)).findFirst();
    }

}
