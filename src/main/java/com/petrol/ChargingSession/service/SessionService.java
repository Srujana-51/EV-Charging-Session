package com.petrol.ChargingSession.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petrol.ChargingSession.entity.SessionEntity;
import com.petrol.ChargingSession.model.Charger;
import com.petrol.ChargingSession.model.SessionModel;

public interface SessionService {

    public String createSession(SessionModel request) throws JsonProcessingException;
    public void listen(String charger) throws JsonProcessingException;

}
