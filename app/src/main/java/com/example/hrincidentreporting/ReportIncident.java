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

import static android.app.Activity.RESULT_OK;

public class ReportIncident extends Fragment {
    private String[] shiftArray = {"shiftA", "shiftB", "shiftC"};
    private String[] incidentTypeArray = {"Near Miss", "First Aid", "Medical Aid"};
    Spinner shiftSpinner, incidentTypeSpinner;
    EditText editTextDate, name, department, position, emp_no;
    Button btn_Report_Incident, btn_ViewData;
    RadioButton male,female;
    RadioGroup radioGroupGender;
    EmployeeRecord a = null;
    boolean radioBtnChk = false;
    ImageView img;
    String genderSelection,incidentSelection,shifSelection,currentDate,employeeNumber;
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
        img = view.findViewById(R.id.img_photo);
        btn_ViewData = (Button) view.findViewById(R.id.btn_ViewData);
        db = new DatabaseHelper(getActivity());
        name = view.findViewById(R.id.edtEmployeeName);
        position = view.findViewById(R.id.edtPosition);
        radioGroupGender = view.findViewById(R.id.radioGender);
        department = view.findViewById(R.id.edtDepartment);
        emp_no = view.findViewById(R.id.edtEmployeeNumber);
        btn_Report_Incident = view.findViewById(R.id.btnOrder);
        editTextDate = view.findViewById(R.id.edtIncidentDate);



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


        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shifSelection = shiftSpinner.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),"Shift: "+shifSelection,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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



        incidentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incidentSelection = incidentTypeSpinner.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),"Incident: "+incidentSelection,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //creating a new DatabaseHelper object
        db = new DatabaseHelper(getActivity().getApplicationContext());


        btn_Report_Incident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //make sure all the fields are filled before reporting incident
                if(emp_no.getText().toString()=="" || shifSelection=="" || genderSelection=="" || incidentSelection=="")
                {

                    Toast.makeText(getContext(),"EmpNo,Gender,Shift,Incident fields are mandatory",Toast.LENGTH_LONG).show();
                }
                else {
                    employeeNumber = emp_no.getText().toString();
                    // EmployeeRecord
                    a=  db.getEmployeeRecord(employeeNumber);
                    name.setText(a.getEmployeeName());
                    department.setText(a.getDepartment());
                    position.setText(a.getPosition());

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.Images.Media.TITLE, "AndroidImage");

                    imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues );

                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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
//            Bundle extras = data.getExtras();
//            Bitmap photo = (Bitmap) extras.get("data");
//            img.setImageBitmap(photo);
            sb = new StringBuilder();
            //gets all the fields value present on Report Incident Page and save it in String Builder
            sb = reportIncidentDetails(sb);

            db.insertRecordIncident(employeeNumber,a.getEmployeeName(),genderSelection,a.getDepartment(),shifSelection, a.getPosition(),"head",incidentSelection
            );


            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"chaitanyauttarwar046@gmail.com","Zzeefakarim5334@conestogac.on.ca"});     //setting up the email
            i.putExtra(Intent.EXTRA_SUBJECT, "HR Incident Reporting");                //Initializing the Email Subject
            i.putExtra(Intent.EXTRA_TEXT   , sb.toString());                                //intenting all the text data into the string
            i.setType("image/jpg");                                 //setting type of image format in jpg
            i.putExtra(Intent.EXTRA_STREAM, imageUri);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getContext(), "There are no email to send.", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(getContext(), "Camera req failed", Toast.LENGTH_LONG).show();
        }
    }//end of onActivityResult

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
//            need to get body part from table which will be prepared later
        sb.append("Injured Body Part : Head");
        sb.append(System.getProperty("line.separator"));

        return sb;
    }


}
