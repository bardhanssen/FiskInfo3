package no.sintef.fiskinfo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Vessel {
    private String vesselName;
    private String vesselPhone;
    private String ircs;
    private String mmsi;
    private String imo;
    private String registrationNumber;
    private String contactPersonEmail;
    private String contactPersonPhone;
    private String contactPersonName;
}
