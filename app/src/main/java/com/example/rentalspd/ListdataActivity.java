package com.example.rentalspd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListdataActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private RentalAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<model> rentalArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdata);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(new Runnable() {

            @Override
            public void run() {
                getDataFromRemote();
            }
        });
    }

    private void getDataFromRemote() {
        refreshLayout.setRefreshing(true);
        AndroidNetworking.post(BaseURL.url+"getAll.php")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        // do anything with response
                        refreshLayout.setRefreshing(false);
                        Log.d("hasiljson", "onResponse: " + response.toString());

                        rentalArraylist = new ArrayList<>();
                        try {
                            Log.d("hasiljson", "onResponse: " + response.toString());
                            JSONArray jsonArray = response.getJSONArray("result");
                            Log.d("hasiljson2", "onResponse: " + jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.i("jsonobject", "onResponse: " + jsonObject);
                                model item = new model();

                                item.setId(jsonObject.optString("id"));
                                item.setRole_user(jsonObject.optString("roleuser"));
                                item.setEmail(jsonObject.optString("email"));
                                item.setNama(jsonObject.optString("nama"));
                                item.setNoktp(jsonObject.optString("noktp"));
                                item.setNohp(jsonObject.optString("nohp"));
                                item.setAlamat(jsonObject.optString("alamat"));
                                rentalArraylist.add(item);
                            }

//                            adapter = new RentalAdapter(rentalArraylist, new RentalAdapter.Callback() {
//                                @Override
//                                public void onClick(int position) {
//                                    Intent in = new Intent(ListdataActivity.this, EditCustomer.class);
//                                    in.putExtra("email", rentalArraylist.get(position).getEmail());
//                                    in.putExtra("nama", rentalArraylist.get(position).getNama());
//                                    in.putExtra("nohp", rentalArraylist.get(position).getNohp());
//                                    in.putExtra("alamat", rentalArraylist.get(position).getAlamat());
//                                    in.putExtra("noktp", rentalArraylist.get(position).getNoktp());
//                                    startActivityForResult(in, 23);
//                                }
//
//                                @Override
//                                public void test() {
//
//                                }
//                            });
                            adapter = new RentalAdapter(rentalArraylist);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListdataActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        refreshLayout.setRefreshing(false);
                        Log.d("errorku", "onError errorCode : " + error.getErrorCode());
                        Log.d("errorku", "onError errorBody : " + error.getErrorBody());
                        Log.d("errorku", "onError errorDetail : " + error.getErrorDetail());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            //refresh list
            getDataFromRemote();
            Toast.makeText(this, "hihihihi", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRefresh() {
        getDataFromRemote();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);                    }
                }).create().show();
    }
}
