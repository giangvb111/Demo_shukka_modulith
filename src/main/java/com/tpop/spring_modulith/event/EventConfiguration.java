package com.tpop.spring_modulith.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Setter
@Getter
public class EventConfiguration<T>  extends ApplicationEvent {

    private EventType eventType;
    private T data;
    private CompletableFuture<Map<String , Object>> future;

    public EventConfiguration(Object source) {
        super(source);
    }

    public EventConfiguration(Object source, Clock clock) {
        super(source, clock);
    }

    public EventConfiguration(Object source, EventType eventType , T data , CompletableFuture<Map<String , Object>> future) {
        super(source);
        this.eventType = eventType;
        this.data = data;
        this.future = future;
    }
}
