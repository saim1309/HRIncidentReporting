package com.example.hrincidentreporting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

        //dynamic camera and storage permission
        try {
            if(chkPermission(Manifest.permission.CAMERA))
                Toast.makeText(this,"Camera permission available",Toast.LENGTH_LONG).show();
            else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CAMERA},1);
            }
            if(chkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                Toast.makeText(this,"Storage Write permission available",Toast.LENGTH_LONG).show();
            else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
            if(chkPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
                Toast.makeText(this,"Storage Read permission available",Toast.LENGTH_LONG).show();
            else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }

        }catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Change the fragment when clicking on the Report Incident button and View Incident Button
     * @param
     */

    public boolean chkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check ==PackageManager.PERMISSION_GRANTED);
    }

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




}
