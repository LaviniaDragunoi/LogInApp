package com.example.laurentiudragunoi.loginapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lavinia Dragunoi on 9/14/2017.
 */

public class Employee implements Parcelable {
    private String name;
    private String bankAccount;
    private double amount;

    protected Employee(Parcel in) {
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

    public Employee(String name, String bankAccount, double amount){
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
        dest.writeString(name);
        dest.writeString(bankAccount);
        dest.writeDouble(amount);
    }
}
