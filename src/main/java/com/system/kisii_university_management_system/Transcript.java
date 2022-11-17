package com.system.kisii_university_management_system;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transcript {

    public String UnitCode;
    public Double GPA;

    public Transcript() {
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

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }
}
