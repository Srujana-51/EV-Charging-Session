package com.petrol.ChargingSession.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Charger {
    private Integer id;
    private Integer stationId;
    private Integer portId;
    private Integer driverId;
    private String status;
    private LocalDateTime startingTime;
    private LocalDateTime endTime;
}
