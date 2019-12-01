package com.example.hrincidentreporting;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ReportIncident extends Fragment {
    private String[] shiftArray = {"shiftA", "shiftB", "shiftC"};
    private String[] incidentTypeArray = {"Near Miss", "First Aid", "Medical Aid"};
    Spinner shiftSpinner, incidentTypeSpinner, injuredBodyPartSpiner;
    EditText editTextDate, name, department, position, emp_no;
    Button btn_Report_Incident, btn_ViewData, btn_IncdntData, btn_BodyData;
    RadioButton male,female;
    RadioGroup radioGroupGender;
    EmployeeRecord a = null;
    Cursor cal;
    int empInt;
    boolean radioBtnChk = false;
    ImageView img;
    String genderSelection,incidentSelection,shifSelection,currentDate,employeeNumber,bodyPartSelection,etEmpValue;
    StringBuilder sb;
    static final int CAM_REQUEST = 1;
    Uri imageUri;
    private DatabaseHelper db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.reportincident_fragment, container, false);
        //finding the spinner for shift and incidentTypeSpinner
        shiftSpinner = view.findViewById(R.id.spinnerShift);
        incidentTypeSpinner = view.findViewById(R.id.spinnerIncidentType);
        //img = view.findViewById(R.id.img_photo);
        btn_ViewData = (Button) view.findViewById(R.id.btn_ViewData);
        db = new DatabaseHelper(getActivity());
        name = view.findViewById(R.id.edtEmployeeName);
        position = view.findViewById(R.id.edtPosition);
        radioGroupGender = view.findViewById(R.id.radioGender);
        department = view.findViewById(R.id.edtDepartment);
        emp_no = view.findViewById(R.id.edtEmployeeNumber);
        btn_Report_Incident = view.findViewById(R.id.btnOrder);
        editTextDate = view.findViewById(R.id.edtIncidentDate);
        //btn_BodyData = view.findViewById(R.id.btn_BodyData);
        //btn_IncdntData = view.findViewById(R.id.btn_IncdntData);
        injuredBodyPartSpiner = view.findViewById(R.id.spinnerInjuredBodypart);
        etEmpValue = emp_no.getText().toString();



        //To auto Populate the current date in the date field
        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        //setting today's date in edit text
        editTextDate.setText(currentDate);

        //Adding values to the shift spinner list
        final ArrayAdapter shiftSpinnerList = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, shiftArray);
        shiftSpinnerList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shiftSpinner.setAdapter(shiftSpinnerList);

        //Adding values to the incidentType spinner list
        ArrayAdapter incidentTypeSpinnerList = new ArrayAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,incidentTypeArray);
        incidentTypeSpinnerList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incidentTypeSpinner.setAdapter(incidentTypeSpinnerList);

        //Adding values to the Shift spinner list
        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shifSelection = shiftSpinner.getItemAtPosition(position).toString();
               // Toast.makeText(getContext(),"Shift: "+shifSelection,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //ArrayList to get and store body parts from Db
        final List<String> incidentBodyPartList = db.getAllBodyRecords();
        //Adding values to the Body part spinner list
        ArrayAdapter incidentBodyPartAdapter = new ArrayAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,incidentBodyPartList);
        incidentBodyPartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        injuredBodyPartSpiner.setAdapter(incidentBodyPartAdapter);
        //Listener for spinner selection
        injuredBodyPartSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bodyPartSelection = injuredBodyPartSpiner.getItemAtPosition(position).toString();
                //Toast.makeText(getContext(),"body part selected: "+bodyPartSelection,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //check what gender was selected
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioMale:
                        genderSelection = "Male";
                        Toast.makeText(getContext(),"Gender: "+genderSelection,Toast.LENGTH_LONG).show();
                        break;

                    case  R.id.radioFemale:
                        genderSelection = "Female";
                        Toast.makeText(getContext(),"Gender: "+genderSelection,Toast.LENGTH_LONG).show();
                        break;

                    default:
                        Toast.makeText(getContext(),"Gender not seleected",Toast.LENGTH_LONG).show();
                }

            }
        });


        //check which incident was chosen
        incidentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incidentSelection = incidentTypeSpinner.getItemAtPosition(position).toString();
                //Toast.makeText(getContext(),"Incident: "+incidentSelection,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        //creating a new DatabaseHelper object
//        db = new DatabaseHelper(getActivity().getApplicationContext());
//
        //view data from Employee Table
        btn_ViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal= db.getAllEmpRecords();
                if(cal.getCount()==0)
                {
                    showStatus("Information", "No record Found");
                }
                else{
                    StringBuffer bfr;
                    bfr = showEmpRecords(cal);
                    showStatus("Data",bfr.toString());
                }

            }
        });

//        //view data from Body Table
//        btn_BodyData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cal= db.getAllBodyRecords();
//                if(cal.getCount()==0)
//                {
//                    showStatus("Information", "No record Found");
//                }
//                else{
//                    StringBuffer bfr;
//                    bfr = showBodyRecords(cal);
//                    showStatus("Data",bfr.toString());
//                }
//
//            }
//        });

//        //view data from Incident Table
//        btn_IncdntData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                cal= db.getAllReportRecords();
//                if(cal.getCount()==0)
//                {
//                    showStatus("Information", "No record Found");
//                }
//                else{
//                    StringBuffer bfr;
//                    bfr = showIncRecords(cal);
//                    showStatus("Data",bfr.toString());
//                }
//
//            }
//        });

        //Functionality of Report incident button
        btn_Report_Incident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmpValue = emp_no.getText().toString();
                //converting emp no to int iff its not empty
                if(!etEmpValue.equals(""))
                    empInt=Integer.parseInt(etEmpValue);
                //make sure all the fields are filled before reporting incident
                if(etEmpValue.equals("") || genderSelection==null)
                {
                    Toast.makeText(getContext(),"EmpNo and Gender fields are mandatory",Toast.LENGTH_LONG).show();
                }
                //employee no should be present in Db
                else if (empInt<0 || empInt>3) {
                    Toast.makeText(getContext(), "We have only  3 employees...Please enter value b/w 1 and 3", Toast.LENGTH_LONG).show();
                }
                else {
                    //gathering all info of an employee
                    employeeNumber = emp_no.getText().toString();
                    // a of type EmployeeRecord
                    a=  db.getEmployeeRecord(employeeNumber);
                    //populating values like name, dept and position based on employeeNumber
                    name.setText(a.getEmployeeName());
                    department.setText(a.getDepartment());
                    position.setText(a.getPosition());

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.Images.Media.TITLE, "AndroidImage");
                    //
                    imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues );
                    //initiate camera intent
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //attaching image file
                    camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    //starting camera activity
                    startActivityForResult(camera, CAM_REQUEST);
                }

//

            }
        });

        return view;
    }// end of OnCreate




    public void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //Bitmap tempBitmap;
        if(requestCode==CAM_REQUEST && resultCode==RESULT_OK){
            sb = new StringBuilder();
            //gets all the fields value present on Report Incident Page and save it in String Builder
            sb = reportIncidentDetails(sb);
            //function to insert all data in Incident Record Table
            db.insertRecordIncident(currentDate,employeeNumber,a.getEmployeeName(),genderSelection,a.getDepartment(),shifSelection, a.getPosition(),bodyPartSelection,incidentSelection
            );
            //intent for sending email
            Intent i = new Intent(Intent.ACTION_SEND);
            //adding recipient for the mail
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"Zzeefakarim5334@conestogac.on.ca"});
            //adding subject for the email
            i.putExtra(Intent.EXTRA_SUBJECT, "HR Incident Reporting");
            //adding the mail body
            i.putExtra(Intent.EXTRA_TEXT   , sb.toString());
            //setting the attachment type as image
            i.setType("image/jpg");
            //adding attachment to the mail
            i.putExtra(Intent.EXTRA_STREAM, imageUri);
            try {
                //starting activity and allow options for sending mail
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getContext(), "There are no email to send.", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(getContext(), "Camera req failed", Toast.LENGTH_LONG).show();
        }
    }//end of onActivityResult

    //Concat of all Employee detail...sent as email body
    private StringBuilder reportIncidentDetails(StringBuilder sb) {

        sb.append("Incident Date : " + currentDate);
        sb.append(System.getProperty("line.separator"));
        sb.append("Employee Number : " + employeeNumber);
        sb.append(System.getProperty("line.separator"));
        sb.append("employeeName : " + a.getEmployeeName());
        sb.append(System.getProperty("line.separator"));
        sb.append("Gender : "+genderSelection);
        sb.append(System.getProperty("line.separator"));
        sb.append("Shift : "+shifSelection);
        sb.append(System.getProperty("line.separator"));
        sb.append("Department : " + a.getDepartment());
        sb.append(System.getProperty("line.separator"));
        sb.append("position : " + a.getPosition());
        sb.append(System.getProperty("line.separator"));
        sb.append("Incident Type : "+incidentSelection);
        sb.append(System.getProperty("line.separator"));
        sb.append("Injured Body Part : "+bodyPartSelection);
        sb.append(System.getProperty("line.separator"));

        return sb;
    }

    //Iterate Employee table records using Cursor
    public StringBuffer showEmpRecords(Cursor cr)
    {
        StringBuffer bfr = new StringBuffer();
        bfr.append("Data"+"\n");
        while(cr.moveToNext()) {
            bfr.append("ID : " + cr.getString(0) + "\n");
            bfr.append("FirstName : " + cr.getString(1) + "\n");
            bfr.append("LastName : " + cr.getString(2) + "\n");
            bfr.append("Marks : " + cr.getString(3) + "\n");

            bfr.append("\n");
        }
        return bfr;
    }

    //Iterate Body table records using Cursor
    public StringBuffer showBodyRecords(Cursor cr)
    {
        StringBuffer bfr = new StringBuffer();
        bfr.append("Data"+"\n");
        while(cr.moveToNext()) {
            bfr.append("ID : " + cr.getString(0) + "\n");
            bfr.append("FirstName : " + cr.getString(1) + "\n");
            bfr.append("\n");
        }
        return bfr;
    }

    //Iterate Incident table records using Cursor
    public StringBuffer showIncRecords(Cursor cr)
    {
        StringBuffer bfr = new StringBuffer();
        bfr.append("Data"+"\n");
        while(cr.moveToNext()) {
            bfr.append("ID : " + cr.getString(0) + "\n");
            bfr.append("FirstName : " + cr.getString(1) + "\n");
            bfr.append("LastName : " + cr.getString(2) + "\n");
            bfr.append("Marks : " + cr.getString(3) + "\n");
            bfr.append("Marks : " + cr.getString(4) + "\n");
            bfr.append("Marks : " + cr.getString(5) + "\n");
            bfr.append("Marks : " + cr.getString(6) + "\n");
            bfr.append("Marks : " + cr.getString(7) + "\n");
            bfr.append("Marks : " + cr.getString(8) + "\n");
            bfr.append("Marks : " + cr.getString(9) + "\n");
            bfr.append("\n");
        }
        return bfr;
    }


    //Custom Dialog Builder
    public void showStatus(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
