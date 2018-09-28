package com.example.laurentiudragunoi.loginapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lavinia Dragunoi on 9/14/2017.
 */

public class Employee implements Parcelable {
    private String userName;
    private String name;
    private String bankAccount;
    private double amount;

    protected Employee(Parcel in) {
        userName = in.readString();
        name = in.readString();
        bankAccount = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



    //empty constructor, don't delete it. Default constructor.
    public Employee(){

    }

    public Employee(String userName,String name, String bankAccount, double amount){
        this.userName = userName;
        this.name = name;
        this.bankAccount = bankAccount;
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(name);
        dest.writeString(bankAccount);
        dest.writeDouble(amount);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName",userName);
        result.put("name", name);
        result.put("bankAccount", bankAccount);
        result.put("amount", amount);

        return result;
    }

}
