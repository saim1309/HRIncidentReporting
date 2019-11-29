package com.example.hrincidentreporting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "employeeRecord.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("drop table if exists Employee");
        db.execSQL(EmployeeRecord.CREATE_EMPLOYEE_RECORD_TABLE);
        db.execSQL(IncidentHistoryRecord.CREATE_INCIDENT_HISTORY_RECORD_TABLE);
        insertRecord("Zeefa Karim","RPA","RPA Architect");
        insertRecord("Chaitnaya","API","API Designer");
        insertRecord("Saim Ahmad","Android","Android Developer");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", EmployeeRecord.CREATE_EMPLOYEE_RECORD_TABLE));
        //db.execSQL(String.format("DROP TABLE IF EXISTS %s", IncidentHistoryRecord.CREATE_INCIDENT_HISTORY_RECORD_TABLE));

        // Create tables again
        onCreate(db);
    }

    /**
     * Method to insert Records in the Database
     * @param employeeName name of the employee
     * @param department  department of the employee
     * @param position    position of the employee
     * @return true if the records is inserted else false
     */
    public boolean insertRecord(String employeeName, String department, String position){
        //getting WritableDatabase permission to write into the DB
        SQLiteDatabase db = this.getWritableDatabase();

        //using ContentValues to put values to the columns
        ContentValues recordValues = new ContentValues();

        recordValues.put(EmployeeRecord.COLUMN_EMPLOYEE_NAME,employeeName);
        recordValues.put(EmployeeRecord.COLUMN_DEPARTMENT,department);
        recordValues.put(EmployeeRecord.COLUMN_POSITION,position);

        long recordId = db.insert(EmployeeRecord.RECORD_TABLE_NAME, null, recordValues);

        db.close();

        if(recordId == -1){
            return false;
        }
        return true;
    }



    public boolean insertRecordIncident(String employeeNumber, String employeeName, String gender,
                                        String department, String shift, String position, String injuredBodyPart, String incidentType){
        //getting WritableDatabase permission to write into the DB
        SQLiteDatabase db = this.getWritableDatabase();

        //using ContentValues to put values to the columns
        ContentValues recordValues = new ContentValues();

        //recordValues.put(IncidentHistoryRecord.COLUMN_INCIDENT_DATE,incidentDate);
        recordValues.put(IncidentHistoryRecord.COLUMN_EMPLOYEE_NUMBER,employeeNumber);
        recordValues.put(IncidentHistoryRecord.COLUMN_EMPLOYEE_NAME,employeeName);
        recordValues.put(IncidentHistoryRecord.COLUMN_GENDER,gender);
        recordValues.put(IncidentHistoryRecord.COLUMN_SHIFT,shift);
        recordValues.put(IncidentHistoryRecord.COLUMN_EMPLOYEE_NUMBER,employeeNumber);
        recordValues.put(IncidentHistoryRecord.COLUMN_DEPARTMENT,department);
        recordValues.put(IncidentHistoryRecord.COLUMN_EMPLOYEE_NUMBER,employeeNumber);
        recordValues.put(IncidentHistoryRecord.COLUMN_POSITION,position);
        recordValues.put(IncidentHistoryRecord.COLUMN_INCIDENT_TYPE,incidentType);
        recordValues.put(IncidentHistoryRecord.COLUMN_INJURED_BODY_PART,injuredBodyPart);


        long recordId = db.insert(IncidentHistoryRecord.RECORD_TABLE_NAME, null, recordValues);

        db.close();

        if(recordId == -1){
            return false;
        }
        return true;
    }

    /**
     * Get the Single Employee Records using the id column value
     * @param id Id of the record using which the data is stored
     * @return Return the EmployeeRecord object
     */

    public EmployeeRecord getEmployeeRecord(String id) {
        //getting Readable DB access as we are the reading the records only
        SQLiteDatabase db = this.getReadableDatabase();
        EmployeeRecord employeeRecord = null;

        //Cursor is used to fetch the record using the selection based on Column ID
        Cursor recordCursor = db.query(EmployeeRecord.RECORD_TABLE_NAME, new String[]
                        {EmployeeRecord.COLUMN_ID, EmployeeRecord.COLUMN_EMPLOYEE_NAME,
                                EmployeeRecord.COLUMN_DEPARTMENT, EmployeeRecord.COLUMN_POSITION},
                EmployeeRecord.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        //Checking if the cursor in not null and having some records
        if (recordCursor != null) {
            if (recordCursor.moveToFirst()) {
                employeeRecord = new EmployeeRecord(recordCursor.getInt(recordCursor.getColumnIndex(EmployeeRecord.COLUMN_ID)),
                                recordCursor.getString(recordCursor.getColumnIndex(EmployeeRecord.COLUMN_EMPLOYEE_NAME)),
                                recordCursor.getString(recordCursor.getColumnIndex(EmployeeRecord.COLUMN_DEPARTMENT)),
                                recordCursor.getString(recordCursor.getColumnIndex(EmployeeRecord.COLUMN_POSITION)));
            }
        }

        db.close();
        return employeeRecord;
    }

    /**
     * Deleting the record using the EmployeeRecord Object
     * @param studentRecord Input the EmployeeRecord object needs to be deleted
     */

    public void deleteRecord(EmployeeRecord studentRecord) {
        //getting the writable permission as we are modifying the DB
        SQLiteDatabase db = this.getWritableDatabase();

        //deleting the Student Record using the column ID
        db.delete(EmployeeRecord.RECORD_TABLE_NAME, EmployeeRecord.COLUMN_ID + " = ?",
                new String[]{String.valueOf(studentRecord.getId())});
        db.close();
    }

    /**
     * Getting list of all employee records in the DB
     * @return Return a list of EmployeeRecords
     */
    public Cursor getAllStudentRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * From "+ EmployeeRecord.RECORD_TABLE_NAME,null);
        //List<EmployeeRecord> studentRecordList = new ArrayList<>();

       // String allRecordQuery = null;

        //Checking the progCode value
       // allRecordQuery = "SELECT * FROM " + EmployeeRecord.RECORD_TABLE_NAME + " ORDER BY "
                   // + EmployeeRecord.COLUMN_ID + " ASC";

       // Cursor recordCursor = db.rawQuery(allRecordQuery,null);

//        if(recordCursor != null && recordCursor.moveToFirst()){
//            while(recordCursor.moveToNext()){
//                EmployeeRecord studentRecord = new EmployeeRecord();
//                studentRecord.setId(recordCursor.getInt(recordCursor.getColumnIndex(EmployeeRecord.COLUMN_ID)));
//                studentRecord.setEmployeeName(recordCursor.getString(recordCursor.getColumnIndex(EmployeeRecord.COLUMN_EMPLOYEE_NAME)));
//                studentRecord.setDepartment(recordCursor.getString(recordCursor.getColumnIndex(EmployeeRecord.COLUMN_DEPARTMENT)));
//                studentRecord.setPosition(recordCursor.getString(recordCursor.getColumnIndex(EmployeeRecord.COLUMN_POSITION)));
//
//                studentRecordList.add(studentRecord);
//            }
//        }
        //db.close();
        //return studentRecordList;

    }
}
