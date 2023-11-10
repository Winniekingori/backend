package com.cytek.cytek.audit.repository;

import com.cytek.cytek.audit.model.EnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EnergyDataRepository extends JpaRepository<EnergyData, Long> {
    @Query("SELECT p.redPower, p.date, p.day, p.time FROM EnergyData p")
    List<Object[]> findRedPowerData();

    @Query("SELECT p.bluePower, p.date, p.day, p.time FROM EnergyData p")
    List<EnergyData> findBluePowerData();

    @Query("SELECT p.redPower FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestRedPower();
    @Query("SELECT p.yellowPower FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestYellowPower();
    @Query("SELECT p.bluePower FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestBluePower();


    //CURRENT
    @Query("SELECT p.redCurrent FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestRedCurrent();
    @Query("SELECT p.yellowCurrent FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighesYellowCurrent();
    @Query("SELECT p.blueCurrent FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestBlueCurrent();

//    REAL TIME DATA

    @Query("SELECT p FROM EnergyData p ORDER BY p.time DESC")
    List<EnergyData> getRealtimeData();

    List<EnergyData> findByUserId(Integer user_id);

    List<EnergyData> findByUser_Id(Integer user_id);
}
