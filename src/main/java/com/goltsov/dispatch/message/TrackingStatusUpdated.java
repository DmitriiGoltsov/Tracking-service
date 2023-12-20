package com.goltsov.dispatch.message;

import com.goltsov.tracking.TrackingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackingStatusUpdated {

    UUID orderId;

    TrackingStatus trackingStatus;
}
