package com.petrol.ChargingSession.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petrol.ChargingSession.entity.SessionEntity;
import com.petrol.ChargingSession.model.SessionModel;
import com.petrol.ChargingSession.service.SessionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SessionController {
    @Autowired
    private SessionServiceImp serviceImp;

    @PostMapping("/sessionCreation")
    public String createSession(@RequestBody SessionModel sessionModel) throws JsonProcessingException {
        return serviceImp.createSession(sessionModel);
    }

}
