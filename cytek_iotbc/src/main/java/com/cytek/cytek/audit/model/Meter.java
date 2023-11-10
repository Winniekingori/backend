package com.cytek.cytek.audit.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String macAddress;
    private String serialNumber;
    private String version;
    private String status;

    private Date addedOn;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private User user;



    public Meter(Integer meterId) {
    }
}