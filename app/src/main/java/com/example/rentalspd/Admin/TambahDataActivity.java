package com.example.rentalspd.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rentalspd.Helper.config;
import com.example.rentalspd.R;
import com.example.rentalspd.initial;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import id.zelory.compressor.Compressor;

public class TambahDataActivity extends AppCompatActivity {
    ImageView ivBack,imageV;
    private Bitmap mSelectedImage;
    private String mSelectedImagePath;
    File mSelectedFileBanner;
    private EditText txtnamasepeda, txtkodesepeda, txtjenissepeda, txtmerksepeda, txtwarnasepeda, txthargasewa;
    private Button btntambahdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        binding();
        imageV = findViewById(R.id.imageView);
//        imageV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PickImageDialog.build(new PickSetup()).show(TambahDataActivity.this);
//                new PickSetup().setCameraButtonText("Gallery");
//            }
//        });

        btntambahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean mIsFormFilled = true;
                final String namasepeda = txtnamasepeda.getText().toString();
                final String kodesepeda = txtkodesepeda.getText().toString().trim();
                final String jenissepeda = txtjenissepeda.getText().toString();
                final String merksepeda = txtmerksepeda.getText().toString();
                final String warnasepeda = txtwarnasepeda.getText().toString();
                final String hargasewa = txthargasewa.getText().toString();


                if (namasepeda.isEmpty() || kodesepeda.isEmpty() || jenissepeda.isEmpty() || merksepeda.isEmpty() || warnasepeda.isEmpty() || hargasewa.isEmpty()){
                    Toast.makeText(TambahDataActivity.this, "Harap lengkapi kolom Tambah Data Sepeda!", Toast.LENGTH_SHORT).show();
                    mIsFormFilled = false;
                }


                if (mIsFormFilled) {
                    HashMap<String, String> body = new HashMap<>();
                    body.put("namasepeda", namasepeda);
                    body.put("kodesepeda", kodesepeda);
                    body.put("jenissepeda", jenissepeda);
                    body.put("merksepeda", merksepeda);
                    body.put("warnasepeda", warnasepeda);
                    body.put("hargasewa", hargasewa);

                    AndroidNetworking.post(config.BASE_URL+"tambahdata.php")
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

                                        Toast.makeText(TambahDataActivity.this, message, Toast.LENGTH_LONG).show();
                                        Log.d("f", "response: "+message);
                                        if (message.equalsIgnoreCase(config.RESPONSE_STATUS_VALUE_SUCCESS)) {

                                            Intent intent = new Intent(TambahDataActivity.this, list_data_sepedaActivity.class);
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
                                    Toast.makeText(TambahDataActivity.this, config.TOAST_AN_EROR, Toast.LENGTH_SHORT).show();
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
        txtnamasepeda= findViewById(R.id.txtnamasepeda);
        txtkodesepeda= findViewById(R.id.txtkodesepeda);
        txtjenissepeda= findViewById(R.id.txtjenissepeda);
        txtmerksepeda = findViewById(R.id.txtmerksepeda);
        txtwarnasepeda  = findViewById(R.id.txtwarnasepeda);
        txthargasewa= findViewById(R.id.txthargasewa);
        btntambahdata = findViewById(R.id.btntambahdata);
    }


}