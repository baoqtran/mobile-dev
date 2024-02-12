package com.baotran.schoolscheduling.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.baotran.schoolscheduling.dao.AssessmentDAO;
import com.baotran.schoolscheduling.dao.CourseDAO;
import com.baotran.schoolscheduling.dao.TermDAO;
import com.baotran.schoolscheduling.entities.Assessment;
import com.baotran.schoolscheduling.entities.Course;
import com.baotran.schoolscheduling.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 5, exportSchema = false)
public abstract class ScheduleDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile ScheduleDatabaseBuilder INSTANCE;

    static ScheduleDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScheduleDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleDatabaseBuilder.class, "MyScheduleDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
