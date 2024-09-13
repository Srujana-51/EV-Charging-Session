package com.petrol.ChargingSession.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionModel {
    private Integer stationId;
    private Integer portId;
    private Integer driverId;
}
