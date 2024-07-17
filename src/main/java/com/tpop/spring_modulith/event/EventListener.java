package com.tpop.spring_modulith.event;

import com.tpop.spring_modulith.master.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventListener implements ApplicationListener<Event<?>> {

    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);

    private final NouhinsakiServiceImpl  nouhinsakiService;
    private final SeihinServiceImpl seihinService;
    private final SoukoServiceImpl soukoService;
    private final TantoshaServiceImpl tantoshaService;
    private final SettingDataService settingDataService;
    @Override
    public void onApplicationEvent(Event<?> event) {
        switch (event.getEventType()) {
            case TYPE_NOUHINSAKI -> nouhinsakiService.handEventNouhinsaki(event);
            case TYPE_SEIHIN -> seihinService.handEventSeihin(event);
            case TYPE_SOUKO -> soukoService.handEventSouko(event);
            case TYPE_TANTOSHA -> tantoshaService.handleEventTantosha(event);
            case TYPE_SCREENID -> settingDataService.handleEventSettingData(event);
            default -> logger.error("Unhandled event type: {}", event.getEventType());
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
