package com.cytek.cytek.audit.repository;

import com.cytek.cytek.audit.model.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {
    Meter findBySerialNumber(String serialNumber);


    List<Meter> findByUserId(Integer user_id);
}
