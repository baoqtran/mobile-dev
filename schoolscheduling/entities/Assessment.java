package com.baotran.schoolscheduling.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private String name;
    private String type;
    private String goalDate;
//    private boolean radiobtn;

    public Assessment(int assessmentID, int courseID, String name, String type, String goalDate) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.name = name;
        this.goalDate = goalDate;
        setType(type);
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.equals(AssessmentType.OA_TYPE) || type.equals(AssessmentType.PA_TYPE)) {
            this.type = type;
        } else {
//            this.type = "";
            throw new RuntimeException("Invalid Assessment Type... <" + type + ">");
        }
    }

    public String getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(String goalDate) {
        this.goalDate = goalDate;
    }

//    public boolean isRadiobtn() {
//        return radiobtn;
//    }
//
//    public void setRadiobtn(boolean radiobtn) {
//        this.radiobtn = radiobtn;
//    }
}
