package com.cytek.cytek.audit.service;

import com.cytek.cytek.audit.model.EnergyData;
import com.cytek.cytek.audit.model.Meter;
import com.cytek.cytek.audit.model.User;
import com.cytek.cytek.audit.repository.EnergyDataRepository;
import com.cytek.cytek.audit.repository.MeterRepository;
import com.cytek.cytek.audit.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class EnergyMeterListener implements MqttCallback {

    private final EnergyDataRepository energyDataRepository;

    private final MeterRepository meterRepository;

    @Autowired
    public EnergyMeterListener(MqttClient mqttClient, EnergyDataRepository energyDataRepository, EnergyDataService energyDataService, UserRepository userRepository, MeterRepository meterRepository) {
        this.energyDataRepository = energyDataRepository;

        this.meterRepository = meterRepository;
        mqttClient.setCallback(this);
        // Subscribe to the appropriate topic(s)
        try {
            mqttClient.subscribe("device/E3276B0D/realtime");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String data = new String(message.getPayload(), StandardCharsets.UTF_8);
        System.out.println("Received message on topic '" + topic + "': " + data);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(data);
            // Extract the serial number from the incoming data
            String extractedSerialNumber = jsonNode.get("SN").toString(); // Use asText() to ensure you get a String
            String serialNumber = extractedSerialNumber.trim();
            System.out.println("Smart meter serial number extracted from data: " + serialNumber);

            // Check if the serial number is not null or empty
            if (!serialNumber.isEmpty()) {
                 final  Long id= 1L;
                System.out.println("Started looking for data in meter table using meter serial number: " + serialNumber);


//                // Perform the database query for the meter data
                Optional<Meter> meterOptional = Optional.ofNullable(meterRepository.findBySerialNumber(serialNumber));
                System.out.println("Data from smart meter: " + meterOptional);
                if (meterOptional.isPresent()) {
                    Meter meter = meterOptional.get();
                    System.out.println("Data from smart meter: " + meter);

                    User user = meter.getUser(); // Get the User object from the Meter
                    if (user != null) {
                        System.out.println("User from smart meter: " + user);
                        // Extract values
                        double redVoltage = jsonNode.get("Datas").get(0).get(0).asDouble();
                        double yellowVoltage = jsonNode.get("Datas").get(1).get(0).asDouble();
                        double blueVoltage = jsonNode.get("Datas").get(2).get(0).asDouble();

                        double redCurrent = jsonNode.get("Datas").get(0).get(1).asDouble();
                        double yellowCurrent = jsonNode.get("Datas").get(1).get(1).asDouble();
                        double blueCurrent = jsonNode.get("Datas").get(2).get(1).asDouble();

                        double redPower = jsonNode.get("Datas").get(0).get(2).asDouble();
                        double yellowPower = jsonNode.get("Datas").get(1).get(2).asDouble();
                        double bluePower = jsonNode.get("Datas").get(2).get(2).asDouble();

                        double redPowerConsumption = jsonNode.get("Datas").get(0).get(3).asDouble();
                        double yellowPowerConsumption = jsonNode.get("Datas").get(1).get(3).asDouble();
                        double bluePowerConsumption = jsonNode.get("Datas").get(2).get(3).asDouble();

                        double redExportedEnergy = jsonNode.get("Datas").get(0).get(4).asDouble();
                        double yellowExportedEnergy = jsonNode.get("Datas").get(1).get(4).asDouble();
                        double blueExportedEnergy = jsonNode.get("Datas").get(2).get(4).asDouble();

                        double redFrequency = jsonNode.get("Datas").get(0).get(5).asDouble();
                        double yellowFrequency = jsonNode.get("Datas").get(1).get(5).asDouble();
                        double blueFrequency = jsonNode.get("Datas").get(2).get(5).asDouble();

                        double redFactor = jsonNode.get("Datas").get(0).get(6).asDouble();
                        double yellowFactor = jsonNode.get("Datas").get(1).get(6).asDouble();
                        double blueFactor = jsonNode.get("Datas").get(2).get(6).asDouble();


                        // Create an EnergyData entity and set the extracted values
                        EnergyData energyData = new EnergyData();
                        energyData.setRedVoltage(redVoltage);
                        energyData.setYellowVoltage(yellowVoltage);
                        energyData.setBlueVoltage(blueVoltage);
                        energyData.setRedCurrent(redCurrent);
                        energyData.setYellowCurrent(yellowCurrent);
                        energyData.setBlueCurrent(blueCurrent);
                        energyData.setRedPower(redPower);
                        energyData.setYellowPower(yellowPower);
                        energyData.setBluePower(bluePower);
                        energyData.setRedPowerConsumption(redPowerConsumption);
                        energyData.setYellowPowerConsumption(yellowPowerConsumption);
                        energyData.setBluePowerConsumption(bluePowerConsumption);
                        energyData.setRedExportedEnergy(redExportedEnergy);
                        energyData.setYellowExportedEnergy(yellowExportedEnergy);
                        energyData.setBlueExportedEnergy(blueExportedEnergy);
                        energyData.setRedFrequency(redFrequency);
                        energyData.setYellowFrequency(yellowFrequency);
                        energyData.setBlueFrequency(blueFrequency);
                        energyData.setRedPowerFactor(redFactor);
                        energyData.setYellowPowerFactor(yellowFactor);
                        energyData.setBluePowerFactor(blueFactor);


                        // Set the date to the current date
                        LocalDate currentDate = LocalDate.now();
                        energyData.setDate(Date.valueOf(currentDate));

                        // Set the current day (day of the week)
                        String currentDay = currentDate.getDayOfWeek().name();
                        energyData.setDay(currentDay);



                        // Set the time to the current time
                        LocalTime currentTime = LocalTime.now();
                        energyData.setTime(Time.valueOf(currentTime));

                        // Set user and meter IDs in the energy data entity
                        energyData.setUser(user);
                        energyData.setMeter(meter);


                        System.out.println("Data to be saved: " + energyData);
                        // Save the entity to the database
                        energyDataRepository.save(energyData);
                        System.out.println("Data saved: " + energyData);


                    } else {
                        System.out.println("No user associated with this meter.");
                    }
                } else {
                    System.out.println("Meter not found with serial number: " + serialNumber);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Error occured while saving data");
        }
    }

        @Override
    public void connectionLost(Throwable cause) {
        // Handle connection loss
        System.out.println("Connection to the smart meter lost: " + cause.getMessage());
            // Implement reconnection logic here
            try {
                IMqttAsyncClient mqttClient = null;
                if (!mqttClient.isConnected()) {
                    mqttClient.reconnect();
                }
            } catch (MqttException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Handle message delivery (QoS 1 and 2)
    }







}
