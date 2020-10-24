package com.example.rentalspd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
     EditText etemail;
     EditText etpassword;
     EditText etnama ;
     TextView txtlogin;

     EditText etnohp;
     EditText etalamat;
     EditText etnoktp;

    ProgressDialog progressDialog;
     SharedPreferences sharedPreferences;

    private Button btn_regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

//        progressDialog = new ProgressDialog(this);


        etemail = findViewById(R.id.email);
        txtlogin = findViewById(R.id.textlogin);
        etpassword = findViewById(R.id.password);
        etnama = findViewById(R.id.nama);
        etnohp = findViewById(R.id.nohp);
        etalamat = findViewById(R.id.alamat);
        etnoktp = findViewById(R.id.noktp);

        btn_regis = findViewById(R.id.btn_register);


        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etemail.getText().toString();
                String password = etpassword.getText().toString();
                String nama = etnama.getText().toString();
                String nohp = etnohp.getText().toString();
                String alamat = etalamat.getText().toString();
                String noktp = etnoktp.getText().toString();

                if (email.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (nama.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (nohp.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Nohp tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (alamat.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (noktp.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Noktp tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
//                progressDialog.setTitle("Register In...");
//                progressDialog.show();
                AndroidNetworking.post(BaseURL.url+"register.php")
                        .addBodyParameter("email",email)
                        .addBodyParameter("password",password)
                        .addBodyParameter("nama",nama)
                        .addBodyParameter("nohp",nohp)
                        .addBodyParameter("alamat",alamat)
                        .addBodyParameter("noktp",noktp)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: ");
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean respon = hasil.getBoolean("respon");
                                    Log.d("STATUS", "onResponse: " + hasil);
                                    if (respon) {
                                        sharedPreferences.edit().putString("logged","customer").apply();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
//                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "gagal", Toast.LENGTH_SHORT).show();
//                                        progressDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                            @Override
                            public void onError(ANError anError) {
//                                progressDialog.dismiss();
                                Log.d("TAG", "onError: " + anError.getErrorDetail());
                                Log.d("TAG", "onError: " + anError.getErrorBody());
                                Log.d("TAG", "onError: " + anError.getErrorCode());
                                Log.d("TAG", "onError: " + anError.getResponse());

                                Toast.makeText(RegisterActivity.this, "Register Gagal", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}