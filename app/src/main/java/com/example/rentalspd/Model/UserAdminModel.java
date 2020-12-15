package com.example.rentalspd.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAdminModel implements Parcelable {
    private String Id;
    private String Username;
    private String Email;
    private String RoleUser;
    private String NoKtp;
    private String NoTlp;
    private String Alamat;

    public UserAdminModel() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id= id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRoleUser() {
        return RoleUser;
    }

    public void setRoleUser(String roleUser) {
        RoleUser = roleUser;
    }

    public String getNoTlp() {
        return NoTlp;
    }

    public void setNoTlp(String noTlp) {
        NoTlp = noTlp;
    }

    public String getNoKtp() {
        return NoKtp;
    }

    public void setNoKtp(String noKtp) {
        NoKtp = noKtp;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }



    public UserAdminModel(Parcel in) {
        Id = in.readString();
        Username = in.readString();
        Email = in.readString();
        RoleUser = in.readString();
        NoKtp = in.readString();
        NoTlp = in.readString();
        Alamat = in.readString();
    }


    public static Creator<UserAdminModel> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<UserAdminModel> CREATOR = new Creator<UserAdminModel>() {
        @Override
        public UserAdminModel createFromParcel(Parcel in) {
            return new UserAdminModel(in);
        }

        @Override
        public UserAdminModel[] newArray(int size) {
            return new UserAdminModel[size];
        }
    };




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Username);
        dest.writeString(Email);
        dest.writeString(RoleUser);
        dest.writeString(NoKtp);
        dest.writeString(NoTlp);
        dest.writeString(Alamat);
    }


}