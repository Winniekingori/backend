package com.cytek.cytek.audit.service;

import com.cytek.cytek.audit.model.Meter;

import java.util.List;
import java.util.Optional;

public interface MeterService {
    List<Meter> getAllMeters();
    Optional<Meter> getMeterById(Long id);
    Meter addMeter(Meter meter);
 
    void deleteMeter(Long id);

    Meter updateMeter(Long id, Meter updatedMeter);
}
