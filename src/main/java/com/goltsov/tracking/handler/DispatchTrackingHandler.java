package com.goltsov.tracking.handler;

import com.goltsov.dispatch.message.DispatchPreparing;
import com.goltsov.tracking.service.TrackingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DispatchTrackingHandler {

    private final TrackingService service;

    @KafkaListener(
            id = "trackingConsumerClient",
            topics = "dispatch.tracking",
            groupId = "tracking.dispatch.tracking",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(DispatchPreparing payload) {
        log.info("Received a message. Its payload is " + payload);

        try {
            service.process(payload);
        } catch (Exception e) {
            log.error("Processing failure in Tracking service. Exception message: " + e.getMessage());
        }
    }
}
