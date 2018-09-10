package com.example.laurentiudragunoi.loginapp;

/**
 * Created by Lavinia Dragunoi on 9/14/2017.
 */

public class Employee {
    private String name;
    private String bankAccount;
    private double amount;

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
}
