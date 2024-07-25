package com.example.demo.models;

public enum Raridades {

    COMUM("comum"),
    RARO("raro");

    private String raridade;

    Raridades(String raridade){
        this.raridade = raridade;
    }

    public String getRaridade(){
        return this.raridade;
    }

}
