package com.system.kisii_university_management_system;

import javafx.scene.control.CheckBox;

public class Transcript {

    public String UnitCode;
    public Double GPA;

    public Transcript() {
    }

    public Transcript(String unitCode, Double GPA) {
        this.UnitCode = unitCode;
        this.GPA = GPA;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String unitCode) {
        this.UnitCode = unitCode;
    }

    public Double getGPA() {
        return GPA;
    }

    public void setGPA(Double GPA) {
        this.GPA = GPA;
    }
}
