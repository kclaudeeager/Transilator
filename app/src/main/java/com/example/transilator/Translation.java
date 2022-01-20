package com.example.transilator;

public class Translation {
    String variable,english,kinyarwanda,french,kiswahil;
public Translation(){

}
    @Override
    public String toString() {
        return "Translation{" +
                "variable='" + variable + '\'' +
                ", english='" + english + '\'' +
                ", kinyarwanda='" + kinyarwanda + '\'' +
                ", french='" + french + '\'' +
                ", kiswahil='" + kiswahil + '\'' +
                '}';
    }

    public Translation(String variable, String english, String kinyarwanda, String french, String kiswahil) {
        this.variable = variable;
        this.english = english;
        this.kinyarwanda = kinyarwanda;
        this.french = french;
        this.kiswahil = kiswahil;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKinyarwanda() {
        return kinyarwanda;
    }

    public void setKinyarwanda(String kinyarwanda) {
        this.kinyarwanda = kinyarwanda;
    }

    public String getFrench() {
        return french;
    }

    public void setFrench(String french) {
        this.french = french;
    }

    public String getKiswahil() {
        return kiswahil;
    }

    public void setKiswahil(String kiswahil) {
        this.kiswahil = kiswahil;
    }
}
