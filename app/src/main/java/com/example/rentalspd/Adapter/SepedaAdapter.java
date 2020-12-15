package com.example.rentalspd.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rentalspd.Admin.DetailSepedaAdminActivity;
import com.example.rentalspd.Admin.list_data_sepedaActivity;
import com.example.rentalspd.Helper.AppHelper;
import com.example.rentalspd.Helper.config;
import com.example.rentalspd.Model.SepedaModel;
import com.example.rentalspd.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class SepedaAdapter extends RecyclerView.Adapter<SepedaAdapter.ItemViewHolder> {
    private Context context;
    private List<SepedaModel> mList;
    private boolean mBusy = false;
    private String mLoginToken = "";
    private ProgressDialog mProgressDialog;
    private list_data_sepedaActivity mAdminActivity;
    private TextView tvNamaSepeda;

    public SepedaAdapter(Context context, List<SepedaModel> mList, Activity AdminDataActivity) {

        this.context = context;
        this.mList = mList;
        this.mAdminActivity = (list_data_sepedaActivity) AdminDataActivity;

    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_list_data_sepeda, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        final SepedaModel Amodel = mList.get(i);
        itemViewHolder.bind(Amodel);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clearData() {
        int size = this.mList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mList.remove(0);
            }
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamasepeda, tvJenissepeda, tvHargaSewa;
        private LinearLayout card_sepeda;
        private ImageView ivGambarSepeda;


        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvNamasepeda = itemView.findViewById(R.id.tvNamaSepeda);
            tvJenissepeda = itemView.findViewById(R.id.tvJenisSepeda);
            tvHargaSewa = itemView.findViewById(R.id.tvHargaSewa);
            card_sepeda = itemView.findViewById(R.id.card_sepeda);
            ivGambarSepeda = itemView.findViewById(R.id.ivGambarSepeda);

        }

        private void bind(final SepedaModel Amodel) {
            tvNamasepeda.setText(Amodel.getNamaSepeda());
            tvJenissepeda.setText(Amodel.getJenisSepeda());
            tvHargaSewa.setText(Amodel.getHargaSewa());
//            Picasso.get()
//                    .load(config.BASE_URL+"img/"+Amodel.getGambarSepeda())
//                    .into(ivGambarSepeda);
            card_sepeda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailSepedaAdminActivity.class);
                    AppHelper.goToDataAdminDetail(context,Amodel);
                }
            });

        }


    }

}
