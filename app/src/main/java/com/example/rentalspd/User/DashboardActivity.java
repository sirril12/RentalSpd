package com.example.rentalspd.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.rentalspd.Admin.list_data_sepedaActivity;
import com.example.rentalspd.R;


public class DashboardActivity extends AppCompatActivity {
 private Button btnprofile;
 private CardView daftarsepeda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
            btnprofile = findViewById(R.id.btnprofile);
            daftarsepeda = findViewById(R.id.daftarsepeda);
            btnprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DashboardActivity.this, UserProfileActivity.class);
                    startActivity(i);
                }
            });
        daftarsepeda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, list_data_sepedaActivity.class);
                startActivity(i);
            }
        });
    }
}