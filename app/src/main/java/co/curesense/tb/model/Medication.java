package co.curesense.tb.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

// Root myDeserializedClass = JsonConvert.DeserializeObject<Root>(myJsonResponse);
// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */

public class Medication {

    @SerializedName("results")
    private Results[] results;

}

class Results {
    @SerializedName("notes")
    private String notes;

    @SerializedName("date_started_taking")
    private String date_started_taking;

    @SerializedName("dosage_quantity")
    private String dosage_quantity;

    @SerializedName("number_refills")
    private String number_refills;

    @SerializedName("appointment")
    private String appointment;

    @SerializedName("dosage_units")
    private String dosage_units;

    @SerializedName("frequency")
    private String frequency;

    @SerializedName("doctor")
    private String doctor;

    @SerializedName("date_prescribed")
    private String date_prescribed;

    @SerializedName("order_status")
    private String order_status;

    @SerializedName("signature_note")
    private String signature_note;
    @SerializedName("patient")
    private String patient;
    @SerializedName("name")
    private String name;
    @SerializedName("date_stopped_taking")
    private String date_stopped_taking;
    @SerializedName("id")
    private String id;
    @SerializedName("indication")
    private String indication;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate_started_taking() {
        return date_started_taking;
    }

    public void setDate_started_taking(String date_started_taking) {
        this.date_started_taking = date_started_taking;
    }

    public String getDosage_quantity() {
        return dosage_quantity;
    }

    public void setDosage_quantity(String dosage_quantity) {
        this.dosage_quantity = dosage_quantity;
    }

    public String getNumber_refills() {
        return number_refills;
    }

    public void setNumber_refills(String number_refills) {
        this.number_refills = number_refills;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getDosage_units() {
        return dosage_units;
    }

    public void setDosage_units(String dosage_units) {
        this.dosage_units = dosage_units;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate_prescribed() {
        return date_prescribed;
    }

    public void setDate_prescribed(String date_prescribed) {
        this.date_prescribed = date_prescribed;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getSignature_note() {
        return signature_note;
    }

    public void setSignature_note(String signature_note) {
        this.signature_note = signature_note;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_stopped_taking() {
        return date_stopped_taking;
    }

    public void setDate_stopped_taking(String date_stopped_taking) {
        this.date_stopped_taking = date_stopped_taking;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }
}






