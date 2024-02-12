package com.baotran.schoolscheduling.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baotran.schoolscheduling.R;
import com.baotran.schoolscheduling.database.Repository;
import com.baotran.schoolscheduling.entities.Course;
import com.baotran.schoolscheduling.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    String termname;
    int termID;
    String termstart;
    String termend;
    EditText editTermName;
    EditText editTermStart;
    EditText editTermEnd;
    Repository repository;
    Term currentTerm;
    int numTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        editTermName=findViewById(R.id.termtitletext);
        editTermStart=findViewById(R.id.termstartdate);
        editTermEnd=findViewById(R.id.termenddate);

        termID = getIntent().getIntExtra("id", -1);
        termname = getIntent().getStringExtra("termname");
        termstart = getIntent().getStringExtra("termstart");
        termend = getIntent().getStringExtra("termend");

        editTermName.setText(termname);
        editTermStart.setText(termstart);
        editTermEnd.setText(termend);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("termid", termID);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.courserecycleview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == termID) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.termsave){
            Term term;
            if (termID == -1){
                if (repository.getmAllTerms().size() == 0) termID = 1;
                else termID = repository.getmAllTerms().get(repository.getmAllTerms().size() - 1).getTermID() + 1;
                term = new Term(termID, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
                repository.insert(term);
                this.finish();
            }
            else{
                term = new Term(termID, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
                repository.update(term);
                this.finish();
            }
        }
        if(item.getItemId() == R.id.termdelete){
            for(Term term:repository.getmAllTerms()){
                if(term.getTermID() == termID)currentTerm = term;
            }
            numTerms = 0;
            for(Course course: repository.getAllCourses()){
                if(course.getTermID() == termID)++numTerms;
            }
            if(numTerms == 0){
                repository.delete(currentTerm);
                Toast.makeText(TermDetails.this, currentTerm.getTermName() + " was deleted",Toast.LENGTH_LONG).show();
                TermDetails.this.finish();
            }
            else{
                Toast.makeText(TermDetails.this, "Can't delete a term with courses",Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }
    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courserecycleview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == termID) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);

//        Toast.makeText(TermDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }
}