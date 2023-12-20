package com.goltsov.tracking.util;

import com.goltsov.dispatch.message.DispatchPreparing;

import java.util.UUID;

public class TestEventData {

    public static DispatchPreparing buildDispatchPreparingEvent(UUID orderId) {
        return DispatchPreparing.builder()
                .orderId(orderId)
                .build();
    }
}
