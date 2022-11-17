package com.system.kisii_university_management_system.CourseAdvisor;

import javafx.scene.control.CheckBox;

public class CADunitApprovalTable {
    public String studentID;
    public String unitName;
    public CheckBox action;


    public CADunitApprovalTable(String studentID,  String unitName, String action) {
        this.studentID = studentID;
        this.unitName = unitName;
        this.action = new CheckBox();
    }

    public CADunitApprovalTable() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }




    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public CheckBox getAction() {
        return action;
    }

    public void setAction(CheckBox action) {
        this.action = action;
    }
}
