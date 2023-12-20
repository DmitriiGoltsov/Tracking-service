package com.goltsov.tracking.service;

import com.goltsov.dispatch.message.DispatchPreparing;
import com.goltsov.dispatch.message.TrackingStatusUpdated;
import com.goltsov.tracking.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrackingServiceTest {

    private TrackingService service;

    private KafkaTemplate kafkaProducerMock;

    @BeforeEach
    void setUp() {
        kafkaProducerMock = mock(KafkaTemplate.class);
        service = new TrackingService(kafkaProducerMock);
    }

    @Test
    void processSuccess() throws Exception {
        when(kafkaProducerMock.send(anyString(), any(TrackingStatusUpdated.class)))
                .thenReturn(mock(CompletableFuture.class));

        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        service.process(testEvent);
        verify(kafkaProducerMock, times(1))
                .send(eq("tracking.status"), any(TrackingStatusUpdated.class));
    }

    @Test
    void processTrackingServiceThrowsException() {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        doThrow(new RuntimeException("Producer failure"))
                .when(kafkaProducerMock).send(eq("tracking.status"), any(TrackingStatusUpdated.class));

        Exception exception = assertThrows(RuntimeException.class, () -> service.process(testEvent));

        verify(kafkaProducerMock, times(1))
                .send(eq("tracking.status"), any(TrackingStatusUpdated.class));

        assertEquals(exception.getMessage(), "Producer failure");

    }
}