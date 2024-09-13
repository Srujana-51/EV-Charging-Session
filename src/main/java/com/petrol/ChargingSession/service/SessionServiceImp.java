package com.petrol.ChargingSession.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrol.ChargingSession.constant.KafkaConstant;
import com.petrol.ChargingSession.entity.SessionEntity;
import com.petrol.ChargingSession.model.Charger;
import com.petrol.ChargingSession.model.SessionModel;
import com.petrol.ChargingSession.repo.SessionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionServiceImp implements SessionService {

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImp.class);

    @Override
    public String createSession(SessionModel request) throws JsonProcessingException {
        try {
            SessionEntity session = new SessionEntity();
            session.setStationId(request.getStationId());
            session.setPortId(request.getPortId());
            session.setDriverId(request.getDriverId());
            session.setStatus("charging");
            session.setStartingTime(LocalDateTime.now());
            session.setEndTime(session.getStartingTime().plusMinutes(2));
            SessionEntity savedSession = sessionRepo.save(session);
            String sessionString = objectMapper.writeValueAsString(savedSession);
            kafkaTemplate.send(KafkaConstant.CHARGER, sessionString)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            logger.error("Failed to send charger data to Kafka", ex);
                        } else {
                            logger.info("Charger data has been sent to Kafka successfully");
                        }
                    });

            logger.info("Charger data has been saved successfully");
            return "Station ID " + request.getStationId() + " with Port ID " + request.getPortId() + " is " + session.getStatus();
        } catch (Exception e) {
            logger.error("Error creating session", e);
            throw e;
        }
    }

    @KafkaListener(topics = KafkaConstant.UPDATE_TOPIC, groupId = KafkaConstant.ID_FOR_CHARGERSESSION)
    public void listen(String charger) throws JsonProcessingException {
        Charger value = objectMapper.readValue(charger, Charger.class);
        Optional<SessionEntity> optionalSessionEntity = sessionRepo.findById(value.getId());
        if (optionalSessionEntity.isPresent()) {
            SessionEntity sessionEntity = optionalSessionEntity.get();
            sessionEntity.setStatus(value.getStatus());
            sessionRepo.save(sessionEntity);
        }
    }
}