package com.example.rentalspd.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rentalspd.Adapter.AdminUserAdapter;
import com.example.rentalspd.Helper.config;
import com.example.rentalspd.Model.UserAdminModel;
import com.example.rentalspd.R;
import com.example.rentalspd.initial;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailUserActivity extends AppCompatActivity {
    ImageView ivBack;
    private Button  btn_edit;
    private EditText tvUsername,tvEmail,tvNoTlp,tvNoKtp,tvAlamat;

    private SwipeRefreshLayout swipeRefresh;
    private UserAdminModel model;
    private String U_ID;
    private AdminUserAdapter mAdapter;
    private RecyclerView rv;

    private String mLoginToken = "";
    private String mUserId = "";
    private String mEmail, mUsername, mKtp, mPhone, mAlamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);



        binding();
        model = getIntent().getExtras().getParcelable("extra_user");
        if(/*bundle*/ model != null) {
            U_ID = model.getId();

            tvUsername.setText(model.getUsername());
            tvAlamat.setText(model.getAlamat());
            tvEmail.setText(model.getEmail());
            tvNoKtp.setText(model.getNoKtp());
            tvNoTlp.setText(model.getNoTlp());


        }


    }

    private void binding() {
        tvEmail = findViewById(R.id.tvEmail);
        tvUsername = findViewById(R.id.tvUsername);
        tvNoKtp = findViewById(R.id.tvNoKtp);
        tvNoTlp = findViewById(R.id.tvNoTlp);
        tvAlamat = findViewById(R.id.tvAlamat);
        btn_edit = findViewById(R.id.btnedituser);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> body = new HashMap<>();
                body.put("id", U_ID);
                body.put("username",tvUsername.getText().toString());
                body.put("email",tvEmail.getText().toString());
                body.put("noktp",tvNoKtp.getText().toString());
                body.put("notlp",tvNoTlp.getText().toString());
                body.put("alamat",tvAlamat.getText().toString());

                AndroidNetworking.post(config.BASE_URL + "updateuser.php")
                        .addBodyParameter(body)
                        .setPriority(Priority.MEDIUM)
                        .setOkHttpClient(((initial) getApplication()).getOkHttpClient())
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                String message = response.optString(config.RESPONSE_MESSAGE_FIELD);
                                String status = response.optString(config.RESPONSE_STATUS_FIELD);

                                Toast.makeText(DetailUserActivity.this, message, Toast.LENGTH_LONG).show();
                                Log.d("AS", "onResponse: "+message);
                                if (message.equalsIgnoreCase(config.RESPONSE_STATUS_VALUE_SUCCESS)) {
                                    Toast.makeText(DetailUserActivity.this,"Update berhasil",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(DetailUserActivity.this,"Update gagal",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(DetailUserActivity.this, config.TOAST_AN_ERROR, Toast.LENGTH_SHORT).show();
                                Log.d("A", "onError: " + anError.getErrorBody());
                                Log.d("A", "onError: " + anError.getLocalizedMessage());
                                Log.d("A", "onError: " + anError.getErrorDetail());
                                Log.d("A", "onError: " + anError.getResponse());
                                Log.d("A", "onError: " + anError.getErrorCode());

                            }
                        });

            }
        });
    }
}
