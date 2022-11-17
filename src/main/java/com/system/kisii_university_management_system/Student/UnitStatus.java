package com.system.kisii_university_management_system.Student;

public class UnitStatus {
    public  String unitCode;
    public String unitName;
    public String unitsStatus;

    public UnitStatus() {
    }

    public UnitStatus(String unitName,String unitCode, String unitsStatus) {
        this.unitName = unitName;
        this.unitsStatus = unitsStatus;
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitsStatus() {
        return unitsStatus;
    }
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    public String getUnitCode(){
        return  this.unitCode;
    }

    public void setUnitsStatus(String unitsStatus) {
        this.unitsStatus = unitsStatus;
    }
}
