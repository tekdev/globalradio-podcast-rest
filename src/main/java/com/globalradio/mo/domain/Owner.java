package com.globalradio.mo.domain;

public class Owner {
    private String name;
    private String email;

    public Owner(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Owner() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
