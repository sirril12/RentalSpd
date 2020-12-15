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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.rentalspd.Adapter.AdminUserAdapter;
import com.example.rentalspd.Helper.config;
import com.example.rentalspd.Model.UserAdminModel;
import com.example.rentalspd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailAdminActivity extends AppCompatActivity {
    ImageView ivBack;
    private Button btnlogout;
    private TextView tvUsername,tvEmail,tvNoTlp,tvNoKtp,tvAlamat,tvRoleUser;

    private SwipeRefreshLayout swipeRefresh;
    private ArrayList<UserAdminModel> mList = new ArrayList<>();
    private AdminUserAdapter mAdapter;
    private RecyclerView rv;
    private UserAdminModel Amodel;

    private String mLoginToken = "";
    private String mUserId = "";
    private String mEmail, mUsername, mKtp, mPhone, mAlamat, mStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_admin);

        binding();
        SharedPreferences sp = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mEmail = sp.getString(config.LOGIN_EMAIL_SHARED_PREF,"");
        mUsername = sp.getString(config.LOGIN_NAME_SHARED_PREF,"");
        mKtp = sp.getString(config.LOGIN_KTP,"");
        mPhone = sp.getString(config.LOGIN_PHONE_SHARED_PREF,"");
        mAlamat = sp.getString(config.LOGIN_ADDRESS_SHARED_PREF,"");
        mStatus = sp.getString(config.LOGIN_GROUP_ID_SHARED_PREF,"");
        tvEmail.setText(mEmail);
        tvUsername.setText(mUsername);
        tvNoKtp.setText(mKtp);
        tvNoTlp.setText(mPhone);
        tvAlamat.setText(mAlamat);
        tvRoleUser.setText(mStatus);

        btnlogout = findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                logout();
            }
        });



    }
    private void logout() {
        new AlertDialog.Builder(DetailAdminActivity.this)
                .setTitle("Logout")
                .setMessage("Anda yakin akan logout ?")
                .setNegativeButton("Tidak", null)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    private void doNothing() {

                    }

                    public void onClick(DialogInterface arg0, int arg1) {
                        config.forceLogout(DetailAdminActivity.this);
                    }
                }).create().show();
    }
    private void binding() {
        tvEmail = findViewById(R.id.tvEmail);
        tvUsername = findViewById(R.id.tvUsername);
        tvNoKtp = findViewById(R.id.tvNoKtp);
        tvNoTlp = findViewById(R.id.tvNoTlp);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvRoleUser = findViewById(R.id.tvRoleUser);
    }


}
