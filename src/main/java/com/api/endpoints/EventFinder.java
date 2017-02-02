package com.api.endpoints;

import eventstream.events.BaseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private Logger logger = LogManager.getLogger(EventFinder.class);

    //TODO inject from properties
    public String EVENTS_PACKAGE = "com.api.events";

    public Optional<Class<? extends BaseEvent>> eventType(String type){
        Reflections reflections = new Reflections(EVENTS_PACKAGE);
        Set<Class<? extends BaseEvent>> eventTypes = reflections.getSubTypesOf(BaseEvent.class);
        eventTypes.forEach(x -> logger.debug(x.getSimpleName()));
        return eventTypes.stream().filter(e -> e.getSimpleName().equals(type)).findFirst();
    }
}
