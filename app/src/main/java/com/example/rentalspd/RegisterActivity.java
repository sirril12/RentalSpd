package com.example.rentalspd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rentalspd.Helper.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
        private TextView tvRegister;
        private EditText txtusernameReg, txtpasswordReg, txtemailReg, txtnotlpReg, txtnoktpReg, txtalamatReg;
        private Button btnregister;
        private boolean mIsFormFilled = false;
        private SharedPreferences preferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            binding();

            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(i);
                }
            });
            btnregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIsFormFilled = true;
                    final String username = txtusernameReg.getText().toString();
                    final String email = txtemailReg.getText().toString().trim();
                    final String password = txtpasswordReg.getText().toString();
                    final String notlp = txtnotlpReg.getText().toString();
                    final String noktp = txtnoktpReg.getText().toString();
                    final String alamat = txtalamatReg.getText().toString();



                    if (username.isEmpty() || notlp.isEmpty() || alamat.isEmpty() || noktp.isEmpty() || password.isEmpty() || email.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Harap lengkapi kolom register !", Toast.LENGTH_SHORT).show();
                        mIsFormFilled = false;
                    }


                    if (mIsFormFilled) {
                        HashMap<String, String> body = new HashMap<>();
                        body.put("email", email);
                        body.put("password", password);
                        body.put("username", username);
                        body.put("notlp", notlp);
                        body.put("alamat", alamat);
                        body.put("noktp", noktp);

                        AndroidNetworking.post(config.BASE_URL+"register.php")
                                .addBodyParameter(body)
                                .setPriority(Priority.MEDIUM)
                                .setOkHttpClient(((initial) getApplication()).getOkHttpClient())
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {

//                                            String status = response.getString(config.RESPONSE_STATUS_FIELD);
                                            String message = response.getString(config.RESPONSE_MESSAGE_FIELD);

                                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                                            Log.d("f", "response: "+message);
                                            if (message.equalsIgnoreCase(config.RESPONSE_STATUS_VALUE_SUCCESS)) {

                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d("b", "JSONException: " + e.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
//                                        mProgress.dismiss();
                                        Toast.makeText(RegisterActivity.this, config.TOAST_AN_EROR, Toast.LENGTH_SHORT).show();
                                        Log.d("ab", "onError: " + anError.getErrorBody());
                                        Log.d("ab", "onError: " + anError.getLocalizedMessage());
                                        Log.d("ab", "onError: " + anError.getErrorDetail());
                                        Log.d("ab", "onError: " + anError.getResponse());
                                        Log.d("ab", "onError: " + anError.getErrorCode());
                                    }
                                });
                    }

                }
            });
        }

        private void binding() {
            tvRegister = findViewById(R.id.tvRegister);
            txtusernameReg = findViewById(R.id.txtusernameReg);
            txtpasswordReg = findViewById(R.id.txtpasswordReg);
            txtnotlpReg = findViewById(R.id.txtnotlpReg);
            txtnoktpReg = findViewById(R.id.txtnoktpReg);
            txtalamatReg = findViewById(R.id.txtalamatReg);
            txtemailReg= findViewById(R.id.txtemailReg);
            btnregister = findViewById(R.id.btnregister);
        }
}