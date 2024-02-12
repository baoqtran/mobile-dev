package com.baotran.schoolscheduling.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private String courseStartDate;
    private String courseEndDate;
    private int termID;

    private String note;
//    private int courseStatus;
    private String courseInsName;
    private String courseInsPhone;
    private String courseInsEmail;

    public Course(int courseID, String courseName, String courseStartDate,
                  String courseEndDate, int termID,
                  String courseInsName, String courseInsPhone, String courseInsEmail, String note) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.termID = termID;
//        this.courseStatus = courseStatus;
        this.courseInsName = courseInsName;
        this.courseInsPhone = courseInsPhone;
        this.courseInsEmail = courseInsEmail;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

//    public boolean isCourseStatus() {
//        return courseStatus;
//    }
//
//    public void setCourseStatus(boolean courseStatus) {
//        this.courseStatus = courseStatus;
//    }

    public String getCourseInsName() {
        return courseInsName;
    }

    public void setCourseInsName(String courseInsName) {
        this.courseInsName = courseInsName;
    }

    public String getCourseInsPhone() {
        return courseInsPhone;
    }

    public void setCourseInsPhone(String courseInsPhone) {
        this.courseInsPhone = courseInsPhone;
    }

    public String getCourseInsEmail() {
        return courseInsEmail;
    }

    public void setCourseInsEmail(String courseInsEmail) {
        this.courseInsEmail = courseInsEmail;
    }
}




