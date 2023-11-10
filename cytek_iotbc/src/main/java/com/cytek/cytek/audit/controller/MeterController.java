package com.cytek.cytek.audit.controller;

import com.cytek.cytek.audit.model.Meter;
import com.cytek.cytek.audit.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MeterController {

    private final MeterService meterService;

    @Autowired
    public MeterController(MeterService meterService) {
        this.meterService = meterService;
    }

    @GetMapping("/meters")
    public List<Meter> getAllMeters() {

        return meterService.getAllMeters();
    }

    @GetMapping("/meters/{id}")
    public ResponseEntity<Meter> getMeterById(@PathVariable Long id) {
        return meterService.getMeterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("meters/add")
    public Meter addMeter(@RequestBody Meter meter) {
        return meterService.addMeter(meter);
    }

    @PutMapping("/meters/{id}")
    public ResponseEntity<Meter> updateMeter(@PathVariable Long id, @RequestBody Meter updatedMeter) {
        Meter meter = meterService.updateMeter(id, updatedMeter);
        return ResponseEntity.ok(meter);
    }

    @DeleteMapping("/meters/{id}")
    public ResponseEntity<Void> deleteMeter(@PathVariable Long id) {
        meterService.deleteMeter(id);
        return ResponseEntity.noContent().build();
    }
}
