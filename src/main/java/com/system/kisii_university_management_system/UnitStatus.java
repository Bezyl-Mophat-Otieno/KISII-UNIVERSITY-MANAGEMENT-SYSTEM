package com.system.kisii_university_management_system;

public class UnitStatus {
    public String unitName;
    public String unitsStatus;

    public UnitStatus() {
    }

    public UnitStatus(String unitName, String unitsStatus) {
        this.unitName = unitName;
        this.unitsStatus = unitsStatus;
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

    public void setUnitsStatus(String unitsStatus) {
        this.unitsStatus = unitsStatus;
    }
}
