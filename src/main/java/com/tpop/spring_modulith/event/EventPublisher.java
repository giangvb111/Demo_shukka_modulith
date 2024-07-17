package com.tpop.spring_modulith.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public <T> CompletableFuture<Map<Long,Object>> publishEvent(EventType eventType, String message, T data) {
        CompletableFuture<Map<Long,Object>> future = new CompletableFuture<>();
        Event<T> event = new Event<>(this, eventType, message, data, future);
        applicationEventPublisher.publishEvent(event);
        return future;
    }

    public <T> CompletableFuture<Object> publishCustomEvent(EventType eventType, String message, T data) {
        CompletableFuture<Object> future = new CompletableFuture<>();
        Event<T> event = new Event<>(this, eventType,data, message, future);
        applicationEventPublisher.publishEvent(event);
        return future;
    }

}
