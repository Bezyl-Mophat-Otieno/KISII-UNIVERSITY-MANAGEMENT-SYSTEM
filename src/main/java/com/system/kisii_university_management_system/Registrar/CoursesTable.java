package com.system.kisii_university_management_system.Registrar;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CoursesTable {
    public final SimpleStringProperty courseId;
    public final SimpleStringProperty courseName;
    public final SimpleIntegerProperty courseDuration;
    public final SimpleDoubleProperty costPrice;


    public CoursesTable(String  courseId, String courseName, Integer courseDuration, Double costPrice) {
        this.courseId = new SimpleStringProperty(courseId);
        this.courseName = new SimpleStringProperty(courseName);
        this.courseDuration = new SimpleIntegerProperty(courseDuration);
        this.costPrice = new SimpleDoubleProperty(costPrice);
    }

    public String getCourseId(){
        return courseId.get();
    }
    public String getCourseName(){ return courseName.get(); }
    public Integer getCourseDuration(){ return courseDuration.get();}
    public Double getCostPrice(){return costPrice.get(); }

}
