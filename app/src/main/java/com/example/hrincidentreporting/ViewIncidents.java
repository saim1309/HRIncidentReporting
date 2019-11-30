package com.example.hrincidentreporting;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        DatabaseHelper db = new DatabaseHelper(getContext());
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



        return view;
    }
}
