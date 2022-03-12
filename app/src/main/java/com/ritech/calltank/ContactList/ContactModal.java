package com.ritech.calltank.ContactList;

import java.io.Serializable;

public class ContactModal implements Serializable {

    String name , number;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
