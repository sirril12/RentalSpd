package com.example.rentalspd.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rentalspd.Adapter.AdminUserAdapter;
import com.example.rentalspd.Helper.AppHelper;
import com.example.rentalspd.Helper.config;
import com.example.rentalspd.Model.UserAdminModel;
import com.example.rentalspd.R;
import com.example.rentalspd.initial;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class list_data_customerActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ImageView ivAdd, ivBack;

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rv;
    private FloatingActionButton tambahuser;

    private ArrayList<UserAdminModel> mList = new ArrayList<>();
    private AdminUserAdapter mAdapter;

    private String mLoginToken = "";
    private String mUserId = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_customer_admin);
        binding();
        getUserList();
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.post(new Runnable() {
            private void doNothing() {

            }

            @Override
            public void run() {
                getUserList();
            }
        });

        tambahuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(list_data_customerActivity.this,TambahUserActivity.class);
                startActivity(i);
            }
        });


        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        rv.setHasFixedSize(true);
        mAdapter = new AdminUserAdapter (list_data_customerActivity.this, mList, list_data_customerActivity.this);
        rv.setAdapter(mAdapter);

    }
    private void binding() {
        tambahuser = findViewById(R.id.tambahuser);

        rv = findViewById(R.id.rvUserManage);
        swipeRefresh = findViewById(R.id.swipeRefresh);

    }

    @Override
    public void onRefresh() {
        getUserList();
    }
    public void show(){

    }

    public void getUserList() {
        swipeRefresh.setRefreshing(true);
        AndroidNetworking.get(config.BASE_URL + "getdatauser.php")
                .setPriority(Priority.LOW)
                .setOkHttpClient(((initial) getApplication()).getOkHttpClient())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        swipeRefresh.setRefreshing(false);
                        if (mAdapter != null) {
                            mAdapter.clearData();
                            mAdapter.notifyDataSetChanged();
                        }
                        if (mList != null) mList.clear();
                        Log.d("RBA", "res" + response);
                        try {
                            Log.i("AB", "respo: "+response);
                            //Loop the Array
                            for(int i=0;i < response.length();i++) {
                                JSONObject data = response.getJSONObject(i);
                                Log.e("ADF", "ponse: "+data );
                                UserAdminModel item = AppHelper.mapUserAdminModel(data);
                                mList.add(item);
                            }
                            mAdapter = new AdminUserAdapter (list_data_customerActivity.this, mList, list_data_customerActivity.this);
                            rv.setAdapter(mAdapter);
                        } catch(JSONException e) {
                            Log.e("log_tag", "Error parsing data "+e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(list_data_customerActivity.this, config.TOAST_AN_EROR, Toast.LENGTH_SHORT).show();
                        Log.d("A", "onError1: " + anError.getErrorBody());
                        Log.d("A", "onError: " + anError.getLocalizedMessage());
                        Log.d("A", "onError: " + anError.getErrorDetail());
                        Log.d("A", "onError: " + anError.getResponse());
                        Log.d("A", "onError: " + anError.getErrorCode());
                    }
                });

    }
}