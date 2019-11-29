package com.example.hrincidentreporting;

import java.sql.Date;

public class IncidentHistoryRecord {

    //Creating a table name Employee to store the all Employee records
    public static final String RECORD_TABLE_NAME = "tbl_IncidentHistory";

    //list of 4 columns in the grades table
    public static final String COLUMN_INCIDENT_ID = "incidentId";
    public static final String COLUMN_INCIDENT_DATE = "incidentDate";
    public static final String COLUMN_EMPLOYEE_NUMBER = "employeeNumber";
    public static final String COLUMN_EMPLOYEE_NAME = "employeeName";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_SHIFT = "shift";
    public static final String COLUMN_DEPARTMENT = "department";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_INCIDENT_TYPE = "incidentType";
    public static final String COLUMN_INJURED_BODY_PART = "injuredBodyPart";

    //fields to hold values of Employee Records
    private int incidentId,employeeNumber;
    private String incidentDate;
    private String employeeName,department,position,gender,shift,incidentType,injuredBodyPart;



    //Drop the table if available beforehand
    public  static final String DROP_INCIDENT_HISTORY_RECORD_TABLE = "DROP TABLE IF EXISTS "+ RECORD_TABLE_NAME;

    // Create table SQL query
    public static final String CREATE_INCIDENT_HISTORY_RECORD_TABLE =
            "CREATE TABLE IF NOT EXISTS " + RECORD_TABLE_NAME + "("
                    + COLUMN_INCIDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EMPLOYEE_NUMBER + "INTEGER,"
                    + COLUMN_EMPLOYEE_NAME + " VARCHAR(40),"
                    + COLUMN_INCIDENT_DATE + "VARCHAR(40),"
                    + COLUMN_GENDER + "VARCHAR(40),"
                    + COLUMN_DEPARTMENT + " VARCHAR(40),"
                    + COLUMN_SHIFT + "VARCHAR(40),"
                    + COLUMN_POSITION + " VARCHAR(40),"
                    + COLUMN_INCIDENT_TYPE + "VARCHAR(40),"
                    + COLUMN_INJURED_BODY_PART + "VARCHAR(40)"
                    + ")";


    public IncidentHistoryRecord(){ }

    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getInjuredBodyPart() {
        return injuredBodyPart;
    }

    public void setInjuredBodyPart(String injuredBodyPart) {
        this.injuredBodyPart = injuredBodyPart;
    }

    public IncidentHistoryRecord(int incidentId, String incidentDate, int employeeNumber, String employeeName, String gender,
                                 String department, String shift, String position, String injuredBodyPart, String incidentType){
        this.incidentId = incidentId;
        this.incidentDate = incidentDate;
        this.employeeName = employeeName;
        this.employeeNumber = employeeNumber;
        this.gender = gender;
        this.shift =shift;
        this.department = department;
        this.position = position;
        this.incidentType =incidentType;
        this.injuredBodyPart =injuredBodyPart;
    }


}
