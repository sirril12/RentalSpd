package com.example.rentalspd.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class SepedaModel implements Parcelable{
    private String Id;
    private String NamaSepeda;
    private String KodeSepeda;
    private String MerkSepeda;
    private String JenisSepeda;
    private String warnasepeda;
    private String HargaSewa;
    private String GambarSepeda;

    public SepedaModel(Parcel in) {
        Id = in.readString();
        NamaSepeda = in.readString();
        KodeSepeda = in.readString();
        MerkSepeda = in.readString();
        JenisSepeda = in.readString();
        warnasepeda = in.readString();
        HargaSewa = in.readString();
        GambarSepeda = in.readString();
    }

    public static final Creator<SepedaModel> CREATOR = new Creator<SepedaModel>() {
        @Override
        public SepedaModel createFromParcel(Parcel in) {
            return new SepedaModel(in);
        }

        @Override
        public SepedaModel[] newArray(int size) {
            return new SepedaModel[size];
        }
    };

    public SepedaModel() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id= id;
    }


    public String getNamaSepeda() {
        return NamaSepeda;
    }

    public void setNamaSepeda(String namaSepeda) {
        NamaSepeda = namaSepeda;
    }

    public String getKodeSepeda() {
        return KodeSepeda;
    }

    public void setKodeSepeda(String kodeSepeda) {
        KodeSepeda = kodeSepeda;
    }

    public String getMerkSepeda() {
        return MerkSepeda;
    }

    public void setMerkSepeda(String merkSepeda) {
        MerkSepeda = merkSepeda;
    }

    public String getJenisSepeda() {
        return JenisSepeda;
    }

    public void setJenisSepeda(String jenisSepeda) {
        JenisSepeda = jenisSepeda;
    }

    public String getWarnasepeda() {
        return warnasepeda;
    }

    public void setWarnasepeda(String warnasepeda) {
        this.warnasepeda = warnasepeda;
    }

    public String getHargaSewa() {
        return HargaSewa;
    }

    public void setHargaSewa(String hargaSewa) {
        HargaSewa = hargaSewa;
    }

    public String getGambarSepeda() {
        return GambarSepeda;
    }

    public void setGambarSepeda(String gambarSepeda) {
        GambarSepeda = gambarSepeda;
    }

    public static Creator<SepedaModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(NamaSepeda);
        dest.writeString(KodeSepeda);
        dest.writeString(MerkSepeda);
        dest.writeString(JenisSepeda);
        dest.writeString(warnasepeda);
        dest.writeString(HargaSewa);
        dest.writeString(GambarSepeda);
    }


}
