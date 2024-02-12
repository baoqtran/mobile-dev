package com.baotran.schoolscheduling.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baotran.schoolscheduling.R;
import com.baotran.schoolscheduling.database.Repository;
import com.baotran.schoolscheduling.entities.Assessment;
import com.baotran.schoolscheduling.entities.Course;
import com.baotran.schoolscheduling.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    String courseName;
    String courseStartDate;
    String courseEndDate;
    String courseInsName;
    String courseInsPhone;
    String courseInsEmail;
    String note;
    int courseID;
    int termID;
    int numCourses = 0;

    Course currentCourse = null;

    EditText editCourseName;
    EditText editCourseStartDate;
    EditText editCourseEndDate;
    EditText editCourseInsName;
    EditText editCourseInsPhone;
    EditText editCourseInsEmail;
    EditText editNote;

    TextView editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("courseid", courseID);
                startActivity(intent);
            }
        });

        repository = new Repository(getApplication());
        courseName = getIntent().getStringExtra("coursename");
        editCourseName = findViewById(R.id.courseName);
        editCourseName.setText(courseName);

        courseStartDate = getIntent().getStringExtra("coursestart");
        editCourseStartDate = findViewById(R.id.courseStartDate);
        editCourseStartDate.setText(courseStartDate);

        courseEndDate = getIntent().getStringExtra("courseend");
        editCourseEndDate = findViewById(R.id.courseEndDate);
        editCourseEndDate.setText(courseEndDate);

        courseInsName = getIntent().getStringExtra("courseinsname");
        editCourseInsName = findViewById(R.id.courseInsName);
        editCourseInsName.setText(courseInsName);

        courseInsPhone = getIntent().getStringExtra("courseinsphone");
        editCourseInsPhone = findViewById(R.id.courseInsPhone);
        editCourseInsPhone.setText(courseInsPhone);

        courseInsEmail = getIntent().getStringExtra("courseinsemail");
        editCourseInsEmail = findViewById(R.id.courseInsEmail);
        editCourseInsEmail.setText(courseInsEmail);

        note = getIntent().getStringExtra("note");
        editNote = findViewById(R.id.note);
        editNote.setText(note);


        courseID = getIntent().getIntExtra("courseid", -1);
        termID = getIntent().getIntExtra("termid", -1);

        editNote=findViewById(R.id.note);
//        editDate=findViewById(R.id.date);

//        String myFormat = "MM/dd/yy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

//        editDate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Date date;
//                //get value from other screen,but I'm going to hard code it right now
//                String info = editDate.getText().toString();
//                if(info.equals(""))info="07/01/23";
//                try{
//                    myCalendarStart.setTime(sdf.parse(info));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
//                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
//                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

//        startDate = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myCalendarStart.set(Calendar.YEAR, year);
//                myCalendarStart.set(Calendar.MONTH, month);
//                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabelStart();
//
//            }
//        };

//        Spinner spinner = findViewById(R.id.spinner);
//        ArrayList<Term> termArrayList = new ArrayList<>();
//
//        termArrayList.addAll(repository.getmAllTerms());
//
//        ArrayAdapter<Term> termAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,termArrayList);
//        spinner.setAdapter(termAdapter);
//        spinner.setSelection(0);

//        ASSESSMENT
//        RecyclerView recyclerView = findViewById(R.id.assessmentrecycleview);
//        repository = new Repository(getApplication());
//        List<Assessment> allAssessments = repository.getAssociatedAssessments(courseID);
//        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
//        recyclerView.setAdapter(assessmentAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        assessmentAdapter.setmAssessments(allAssessments);


        RecyclerView recyclerView = findViewById(R.id.assessmentrecycleview);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getmAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setmAssessments(filteredAssessments);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.coursesave) {
            Course course;
            if (courseID == -1) {
                if (repository.getAllCourses().size() == 0)
                    courseID = 1;
                else
                    courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                course = new Course(courseID, editCourseName.getText().toString(), editCourseStartDate.getText().toString(),
                        editCourseEndDate.getText().toString(), termID, editCourseInsName.getText().toString(),
                        editCourseInsPhone.getText().toString(), editCourseInsEmail.getText().toString(), editNote.getText().toString());
                repository.insert(course);
                CourseDetails.this.finish();
            } else {
                course = new Course(courseID, editCourseName.getText().toString(), editCourseStartDate.getText().toString(),
                        editCourseEndDate.getText().toString(), termID ,editCourseInsName.getText().toString(),
                        editCourseInsPhone.getText().toString(), editCourseInsEmail.getText().toString(), editNote.getText().toString());
                repository.update(course);
                CourseDetails.this.finish();
            }
            return true;
        }

        if(item.getItemId() == R.id.coursedelete){
            for(Course course1:repository.getAllCourses()){
                if(course1.getCourseID() == courseID)currentCourse = course1;
            }
//                for(Course course1: repository.getAllCourses()){
//                    if(course1.getCourseID() == courseID)++numCourses;
//                }
            if(currentCourse != null){
                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, currentCourse.getCourseName() + " was deleted",Toast.LENGTH_LONG).show();
                CourseDetails.this.finish();
            }
        }


        if (item.getItemId() == R.id.share) {
            Intent sentIntent= new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
            sentIntent.putExtra(Intent.EXTRA_TITLE, editNote.getText().toString());
            sentIntent.setType("text/plain");
            Intent shareIntent=Intent.createChooser(sentIntent,null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId() == R.id.notifystart) {
            String dateFromScreen = editCourseStartDate.getText().toString();
            String myFormat = "MM-dd-yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long trigger = myDate.getTime();
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key",  courseName + " start today");
            PendingIntent sender=PendingIntent.getBroadcast(CourseDetails.this,++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, trigger,sender);
            Toast.makeText(getApplicationContext(),"Start notification for " + courseName + " has been set!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (item.getItemId() == R.id.notifyend) {
            String dateFromScreen = editCourseEndDate.getText().toString();
            String myFormat = "MM-dd-yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long trigger = myDate.getTime();
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key",  courseName + " ended today");
            PendingIntent sender=PendingIntent.getBroadcast(CourseDetails.this,++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, trigger,sender);
            Toast.makeText(getApplicationContext(),"End notification for " + courseName + " has been set!", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentrecycleview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getmAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setmAssessments(filteredAssessments);
//        Toast.makeText(TermDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }
}