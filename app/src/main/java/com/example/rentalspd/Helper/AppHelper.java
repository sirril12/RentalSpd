package com.example.rentalspd.Helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.example.rentalspd.Admin.DetailSepedaAdminActivity;
import com.example.rentalspd.Admin.DetailUserActivity;
import com.example.rentalspd.Model.SepedaModel;
import com.example.rentalspd.Model.UserAdminModel;

import org.json.JSONObject;

public class AppHelper {
    public static UserAdminModel mapUserAdminModel(JSONObject rowData) {
        UserAdminModel item = new UserAdminModel();
        item.setId(rowData.optString("id"));
        item.setRoleUser(rowData.optString("roleuser"));
        item.setEmail(rowData.optString("email"));
        item.setUsername(rowData.optString("username"));
        item.setNoKtp(rowData.optString("noktp"));
        item.setNoTlp(rowData.optString("notlp"));
        item.setAlamat(rowData.optString("alamat"));



        return item;
    }

    public static void goToUserAdminDetail(Context context, UserAdminModel rowData) {
        Bundle bundle = new Bundle();

        bundle.putString("id", String.valueOf(rowData.getId()));
        bundle.putString("roleuser", rowData.getRoleUser().toUpperCase());
        bundle.putString("email", rowData.getEmail().toUpperCase());
        bundle.putString("username", rowData.getUsername().toUpperCase());
        bundle.putString("noktp", rowData.getNoKtp());
        bundle.putString("notlp", rowData.getNoTlp());
        bundle.putString("alamat", rowData.getAlamat().toUpperCase());



        Intent i = new Intent(context, DetailUserActivity.class);
        i.putExtra("extra_user", rowData);
        context.startActivity(i);
    }

    public static SepedaModel mapSepedaAdminModel(JSONObject rowData) {
        SepedaModel item = new SepedaModel();
        item.setHargaSewa(rowData.optString("hargasewa"));
        item.setJenisSepeda(rowData.optString("jenissepeda"));
        item.setKodeSepeda(rowData.optString("kodesepeda"));
        item.setMerkSepeda(rowData.optString("merksepeda"));
        item.setWarnasepeda(rowData.optString("warnasepeda"));
        item.setNamaSepeda(rowData.optString("namasepeda"));
//        item.setGambarSepeda(rowData.optString("gambarsepeda"));

        return item;
    }


    public static void goToDataAdminDetail(Context context, SepedaModel rowData) {
        Bundle bundle = new Bundle();

        bundle.putString("id", String.valueOf(rowData.getId()));
        bundle.putString("namasepeda", rowData.getNamaSepeda().toUpperCase());
        bundle.putString("kodesepeda", rowData.getKodeSepeda().toUpperCase());
        bundle.putString("jenissepeda", rowData.getJenisSepeda().toUpperCase());
        bundle.putString("merksepeda", rowData.getMerkSepeda().toUpperCase());
        bundle.putString("warnasepeda", rowData.getWarnasepeda().toUpperCase());
        bundle.putString("hargasewa", rowData.getHargaSewa().toUpperCase());


        Intent i = new Intent(context, DetailSepedaAdminActivity.class);
        i.putExtra("extra_data", rowData);
        context.startActivity(i);
    }
}
