package com.goltsov.tracking.handler;

import com.goltsov.dispatch.message.DispatchPreparing;
import com.goltsov.tracking.service.TrackingService;
import com.goltsov.tracking.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DispatchTrackingHandlerTest {

    private DispatchTrackingHandler handler;

    private TrackingService serviceMock;

    @BeforeEach
    void setUp() {
        serviceMock = mock(TrackingService.class);
        handler = new DispatchTrackingHandler(serviceMock);
    }

    @Test
    void listenSuccess() throws Exception {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        handler.listen(testEvent);
        verify(serviceMock, times(1)).process(testEvent);
    }

    @Test
    void listenServiceThrowsException() throws Exception {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(UUID.randomUUID());
        doThrow(new RuntimeException("Service failure")).when(serviceMock).process(testEvent);

        handler.listen(testEvent);
        verify(serviceMock, times(1)).process(testEvent);
    }
}