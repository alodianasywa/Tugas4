package com.example.myfirebasecrud;

import java.io.Serializable;

public class Peserta implements Serializable
{
    private String email;
    private String nama;
    private String hari;
    private String key;

    public Peserta()
    {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString()
    {
        return " "+email+"" +
                "" +nama+"" +
                "" +hari;
    }
    public Peserta(String em, String nm, String hr)
    {
        email=em;
        nama=nm;
        hari=hr;
    }
}


