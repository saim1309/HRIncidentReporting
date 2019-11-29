package com.example.hrincidentreporting;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;    //fragment manager is used for managing fragments and handle transaction b/w them
    private FragmentTransaction fragmentTransaction; //fragment transaction are seq of steps used ike adding,replacing or removing fragments
    private Fragment selectedFragment; //fragment represents a behaviour or a portion of UI in an activity something like sub-activity
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);

        // db.insertRecord("Zeefa","RPA","RPA Architect");

    }

    /**
     * Change the fragment when clicking on the Report Incident button and View Incident Button
     * @param view
     */


    public void onChangeFragment(View view){
        //checking if the id is matching with reportIncident Fragment
        if (view == findViewById(R.id.reportIncidentBtn)) {
            selectedFragment = new ReportIncident(); //creation of object for ReportIncident fragment
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.replace wll replace or display the ReportIncident fragment when corr button is pressed
            fragmentTransaction.replace(R.id.frameLayout, selectedFragment);
            //adding the fragment stack to null - back button will go back to home screen
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        //checking if the id is matching with viewIncident Fragment
        else if (view == findViewById(R.id.viewIncidentBtn)) {
            selectedFragment = new ViewIncidents();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, selectedFragment);
            //adding the fragment stack to null - back button will go back to home screen
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public   void takePhoto(){


    }


}
