package com.petrol.ChargingSession.repo;

import com.petrol.ChargingSession.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepo extends JpaRepository<SessionEntity,Integer> {
}
