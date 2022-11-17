package com.system.kisii_university_management_system.Student;

import  javafx.scene.control.CheckBox;

public class Units {
    public String unitCode;
    public String unitName;
    public String unitDesc;
    public CheckBox selectUnit;
    public Units() {
    }

    public Units(String unitCode, String unitName, String unitDesc , String select) {
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.unitDesc = unitDesc;
        this.selectUnit = new CheckBox();
    }

    public CheckBox getSelectUnit() {
        return selectUnit;
    }

    public void setSelectUnit(CheckBox selectUnit) {
        this.selectUnit = selectUnit;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }
}
