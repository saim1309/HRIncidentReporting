package com.example.hrincidentreporting;

public class BodyParts {
    //Creating a table name Employee to store the all Employee records
    public static final String RECORD_TABLE_NAME = "tbl_BodyParts";
    //list of 2 columns in the table
    public static final String COLUMN_BODY_PART_ID = "bodyPartId";
    public static final String COLUMN_BODY_PART_NAME = "bodyPartName";


    //fields to hold values of Body Parts Records
    private int bodyPartId;
    private String bodyPartName;


    //Drop the table if available beforehand
    public  static final String DROP_BODY_PART_TABLE = "DROP TABLE IF EXISTS "+ RECORD_TABLE_NAME;

    // Create table SQL query
    public static final String CREATE_BODY_PART_TABLE =
            "CREATE TABLE IF NOT EXISTS " + RECORD_TABLE_NAME + "("
                    + COLUMN_BODY_PART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_BODY_PART_NAME + " VARCHAR(40)"
                    + ")";

    public  BodyParts(){}


    public int getBodyPartId() {
        return bodyPartId;
    }

    public void setBodyPartId(int bodyPartId) {
        this.bodyPartId = bodyPartId;
    }

    public String getBodyPartName() {
        return bodyPartName;
    }

    public void setBodyPartName(String bodyPartName) {
        this.bodyPartName = bodyPartName;
    }

    public BodyParts(int bodyPartId, String bodyPartName){
        this.bodyPartId = bodyPartId;
        this.bodyPartName = bodyPartName;

    }


}
