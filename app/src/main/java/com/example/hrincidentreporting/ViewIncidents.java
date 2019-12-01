package com.example.hrincidentreporting;

import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class ViewIncidents extends Fragment {
    TextView tvViewIncident;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.viewincidents_fragment, container, false);
        tvViewIncident = view.findViewById(R.id.tvViewIncident);
        //adding scroll features to see all records
        tvViewIncident.setMovementMethod(new ScrollingMovementMethod());

        DatabaseHelper db = new DatabaseHelper(getContext());
        //fetch all the records from Report Incident and display in ViewIncident Page
        Cursor res = db.getAllReportRecords();
        while (res.moveToNext()){
            tvViewIncident.append("ID: " +res.getString(0)+ "\n");
            tvViewIncident.append("EMP_NUMBER: " +res.getString(1)+ "\n");
            tvViewIncident.append("EMP_NAME: " +res.getString(2)+ "\n");
            tvViewIncident.append("DATE: " +res.getString(3)+ "\n");
            tvViewIncident.append("GENDER: " +res.getString(4)+ "\n");
            tvViewIncident.append("DEPARTMENT: " +res.getString(5)+ "\n");
            tvViewIncident.append("SHIFT: " +res.getString(6)+ "\n");
            tvViewIncident.append("POSITION: " +res.getString(7)+ "\n");
            tvViewIncident.append("INCIDENT_TYPE: " +res.getString(8)+ "\n");
            tvViewIncident.append("INCIDENT_BODY_PART: " +res.getString(9)+ "\n\n");
        }

//        btn_clearIncidentRecords = view.findViewById(R.id.btn_clearIncidentRecords);
//        btn_clearIncidentRecords.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseHelper db = new DatabaseHelper(getContext());
//                db.deleteAllReportRecords();
//            }
//        });

        return view;
    }
}
