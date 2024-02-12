package com.baotran.schoolscheduling.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baotran.schoolscheduling.R;
import com.baotran.schoolscheduling.database.Repository;
import com.baotran.schoolscheduling.entities.Assessment;
import com.baotran.schoolscheduling.entities.AssessmentType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    String assessmentName;
    String assessmentType;
    String assessmentGoal;
    int courseID;
    int assessmentID;

    RadioGroup radioGroupType;
    RadioButton radioOA;
    RadioButton radioPA;

    Repository repository;
    Assessment currentAssessment = null;



    EditText editName;
    EditText editGoalDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);


        repository = new Repository(getApplication());

        assessmentName = getIntent().getStringExtra("name");
        editName = findViewById(R.id.assessmentname);

        assessmentType = getIntent().getStringExtra("type");
        if (assessmentType == null || assessmentType.trim().isEmpty()) {
            assessmentType = AssessmentType.PA_TYPE;
        }

        assessmentGoal = getIntent().getStringExtra("goaldate");
        editGoalDate = findViewById(R.id.assessmentgoaldate);

        assessmentID = getIntent().getIntExtra("assessmentid", -1);
        courseID = getIntent().getIntExtra("courseid", -1);

        radioGroupType = findViewById(R.id.radiogroup);
        radioOA = findViewById(R.id.radioOA);
        radioPA = findViewById(R.id.radioPA);

        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (findViewById(checkedId) == radioOA) {
                    assessmentType = AssessmentType.OA_TYPE;
                } else if (findViewById(checkedId) == radioPA) {
                    assessmentType = AssessmentType.PA_TYPE;
                }
            }
        });
    }

    @Override
    public void onResume () {
        super.onResume();

        editName.setText(assessmentName);
        editGoalDate.setText(assessmentGoal);
        if (assessmentType.equals(AssessmentType.OA_TYPE) ) {
            radioOA.setChecked(true);
        } else if (assessmentType.equals(AssessmentType.PA_TYPE)) {
            radioPA.setChecked(true);
        } else {
//            this.type = "";
            throw new RuntimeException("Invalid Assessment Type... <" + assessmentType + ">");
        }

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.assessmentsave) {
            Assessment assessment;
            if (assessmentID == -1) {

                assessment = new Assessment(0, courseID, editName.getText().toString(), assessmentType,
                        editGoalDate.getText().toString());
                repository.insert(assessment);
                AssessmentDetails.this.finish();
            } else {
                assessment = new Assessment(assessmentID, courseID, editName.getText().toString(), assessmentType,
                        editGoalDate.getText().toString());
                repository.update(assessment);
                AssessmentDetails.this.finish();
            }
            return true;
        }

        if(item.getItemId() == R.id.assessmentdelete){
            for(Assessment assessment1:repository.getmAllAssessments()){
                if(assessment1.getAssessmentID() == assessmentID)currentAssessment = assessment1;
            }

            if(currentAssessment != null){
                repository.delete(currentAssessment);
                Toast.makeText(AssessmentDetails.this, currentAssessment.getName() + " was deleted",Toast.LENGTH_LONG).show();
                AssessmentDetails.this.finish();
            }

        }



        if (item.getItemId() == R.id.notifygoaldate) {
            String dateFromScreen = editGoalDate.getText().toString();
            String myFormat = "MM-dd-yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long trigger = myDate.getTime();
            Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
            intent.putExtra("key", assessmentName + " ended today");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, trigger, sender);
            Toast.makeText(getApplicationContext(), "Goal notification for " + assessmentName + " has been set!", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

