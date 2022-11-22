package com.system.kisii_university_management_system.School;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class TranscriptTable {
    public final SimpleStringProperty unitCode;
    public final SimpleStringProperty unitName;
    public final SimpleDoubleProperty GPA;


    public TranscriptTable(String  unitCode, String unitName, Double GPA) {
        this.unitCode = new SimpleStringProperty(unitCode);
        this.unitName= new SimpleStringProperty(unitName);
        this.GPA = new SimpleDoubleProperty(GPA);
    }

    public String getUnitCode(){return unitCode.get();}
    public String getUnitName(){ return unitName.get(); }
    public Double getGPA(){ return GPA.get();}
}
