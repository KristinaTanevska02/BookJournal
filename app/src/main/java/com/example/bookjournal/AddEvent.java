package com.example.bookjournal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDateTime;

public class AddEvent extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private LatLng startLocation;
    private Button buttonSaveEvent;
    private DatabaseHelper dbHelper;
    private TextView chosenTime;
    private Button chooseButton;
    private EditText titleplacetext;

    private int selectedYear, selectedMonth, selectedDay;
    private int selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buttonSaveEvent = findViewById(R.id.buttonSaveEvent);
        dbHelper = new DatabaseHelper(this);
        chosenTime = findViewById(R.id.textViewChosenDateTime);
        chooseButton = findViewById(R.id.buttonChooseDateTime);
        titleplacetext = findViewById(R.id.titleplacetext);
        chooseButton.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View view){
                                                openDialog();
                                            }
                                        }
        );
        buttonSaveEvent.setOnClickListener(v -> {
            if(startLocation != null){
                dbHelper.addEvent(1,titleplacetext.getText().toString(),chosenTime.getText().toString(),startLocation.latitude, startLocation.longitude);
            Intent intent = new Intent(getApplicationContext(), Events.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void openDialog(){
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedYear = year;
                selectedMonth = month+1;
                selectedDay = day;

                openDialog2();
            }
        },2024,12,18);
        dialog.show();
    }
    private void openDialog2(){
        TimePickerDialog dialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                selectedHour = hour;
                selectedMinute = minute;
                // Concatenate and display the full date-time result
                String result = selectedYear + "-"
                        + String.format("%02d", selectedMonth + 1) + "-"
                        + String.format("%02d", selectedDay) + "T"
                        + String.format("%02d:%02d:%02d", selectedHour, selectedMinute, 0);

                chosenTime.setText(result);
            }
        },15,00,true);
        dialog.show();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        myMap.clear();
        LatLng macedoniaCenter = new LatLng(41.6086, 21.7453);
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(macedoniaCenter, 7));

        myMap.setOnMapClickListener(latLng -> {
            if (startLocation == null) {
                startLocation = latLng;
                myMap.addMarker(new MarkerOptions().position(startLocation).title("Location"));
            }
        });
    }
}