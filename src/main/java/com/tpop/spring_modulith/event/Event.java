package com.tpop.spring_modulith.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Event<T> extends ApplicationEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    private EventType eventType;
    private transient T data;
    private transient String message;
    private transient CompletableFuture<Map<Long,Object>> future;
    private transient CompletableFuture<Object> objectCompletableFuture;

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

