package com.example.hrincidentreporting;

public class EmployeeRecord {
    //Creating a table name Employee to store the all Employee records
    public static final String RECORD_TABLE_NAME = "Employee";

    //list of 4 columns in the grades table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMPLOYEE_NAME = "employeeName";
    public static final String COLUMN_DEPARTMENT = "department";
    public static final String COLUMN_POSITION = "position";

    //fields to hold values of Employee Records
    private int id;
    private String employeeName;
    private String department;
    private String position;


    //Drop the table if available beforehand
    public  static final String DROP_EMPLOYEE_RECORD_TABLE = "DROP TABLE IF EXISTS "+ RECORD_TABLE_NAME;

    // Create table SQL query
    public static final String CREATE_EMPLOYEE_RECORD_TABLE =
            "CREATE TABLE IF NOT EXISTS " + RECORD_TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EMPLOYEE_NAME + " VARCHAR(40),"
                    + COLUMN_DEPARTMENT + " VARCHAR(40),"
                    + COLUMN_POSITION + " VARCHAR(40)"
                    + ")";


    public EmployeeRecord(){ }

    public EmployeeRecord(int id, String employeeName, String department,
                         String position){
        this.id = id;
        this.employeeName = employeeName;
        this.department = department;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
