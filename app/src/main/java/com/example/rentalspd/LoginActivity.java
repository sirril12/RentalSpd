package com.example.rentalspd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.rentalspd.Admin.AdminActivity;
import com.example.rentalspd.Helper.config;
import com.example.rentalspd.User.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private TextView tvLogin;
    private EditText txtemailLog, txtpasswordLog;
    private boolean isFormFilled = false;
    private Button btnLogin;
    private String roleuser, id,gmail,username,noktp,notlp,alamat;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtemailLog = findViewById(R.id.txtemailLog);
        txtpasswordLog = findViewById(R.id.txtpasswordLog);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin = findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFormFilled = true;
                final String email = txtemailLog.getText().toString();
                final String password = txtpasswordLog.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Harap isi kolom login !", Toast.LENGTH_SHORT).show();
                    isFormFilled = false;
                }
//                isFormFilled = false;
                if (isFormFilled) {
                    HashMap<String, String> body = new HashMap<>();
                    body.put("email", email);
                    body.put("password", password);
                    AndroidNetworking.post(config.BASE_URL+"login.php")
                            .addBodyParameter(body)
                            .setOkHttpClient(((initial) getApplication()).getOkHttpClient())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("e", "respon : " + response);
                                    String status = response.optString(config.RESPONSE_STATUS_FIELD);
                                    String message = response.optString(config.RESPONSE_MESSAGE_FIELD);
//                                    String login = response.optString("login");
                                    if (message.equalsIgnoreCase(config.RESPONSE_STATUS_VALUE_SUCCESS)) {
                                        JSONArray loginArray = response.optJSONArray("login");
                                        if (loginArray == null) return;
                                        for (int i = 0; i <loginArray.length(); i++) {
                                            final JSONObject aLogin = loginArray.optJSONObject(i);
                                            roleuser = aLogin.optString("roleuser");
                                            gmail = aLogin.optString("email");
                                            username = aLogin.optString("username");
                                            id = aLogin.optString("id");
                                            noktp = aLogin.optString("noktp");
                                            notlp = aLogin.optString("notlp");
                                            alamat = aLogin.optString("alamat");
                                        }
                                        Log.d("AGG", "respon : " + roleuser);
                                        preferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                        preferences.edit()
                                                .putString(config.LOGIN_ID_SHARED_PREF, id)
                                                .putString(config.LOGIN_NAME_SHARED_PREF, username)
                                                .putString(config.LOGIN_GROUP_ID_SHARED_PREF, roleuser)
                                                .putString(config.LOGIN_EMAIL_SHARED_PREF, gmail)
                                                .putString(config.LOGIN_KTP,noktp)
                                                .putString(config.LOGIN_PHONE_SHARED_PREF,notlp)
                                                .putString(config.LOGIN_ADDRESS_SHARED_PREF,alamat)
                                                .apply();
                                        if (roleuser.equalsIgnoreCase("admin")) {
                                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else {
                                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onError(ANError anError) {

                                    Log.d("e", "onError: " + anError.getErrorBody());
                                    Log.d("e", "onError: " + anError.getLocalizedMessage());
                                    Log.d("e", "onError: " + anError.getErrorDetail());
                                    Log.d("e", "onError: " + anError.getResponse());
                                    Log.d("e", "onError: " + anError.getErrorCode());
                                }
                            });
                }
            }
        });

    }
}
