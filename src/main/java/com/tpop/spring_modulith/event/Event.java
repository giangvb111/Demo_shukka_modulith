package com.tpop.spring_modulith.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Event<T> extends ApplicationEvent {

    private EventType eventType;
    private T data;
    private String message;
    private CompletableFuture<Map<Long,Object>> future;
    private CompletableFuture<Object> objectCompletableFuture;

    public Event(Object source, EventType eventType, String message, T data) {
        super(source);
        this.eventType = eventType;
        this.message = message;
        this.data = data;
    }

    public Event(EventPublisher source, EventType eventType, String message, T data, CompletableFuture<Map<Long,Object>> future) {
        super(source);
        this.eventType = eventType;
        this.message = message;
        this.data = data;
        this.future= future;
    }

    public Event(EventPublisher source, EventType eventType, T data, String message, CompletableFuture<Object> objectCompletableFuture) {
        super(source);
        this.eventType = eventType;
        this.data = data;
        this.message = message;
        this.objectCompletableFuture = objectCompletableFuture;
    }
}

