package com.baotran.schoolscheduling.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.baotran.schoolscheduling.R;
import com.baotran.schoolscheduling.database.Repository;
import com.baotran.schoolscheduling.entities.Course;
import com.baotran.schoolscheduling.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getmAllTerms();
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
//        System.out.println(getIntent().getStringExtra("test"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;

    }

    @Override
    protected void onResume() {

        super.onResume();
        List<Term> allTerms = repository.getmAllTerms();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.mysample) {
//            repository = new Repository(getApplication());
////            Toast.makeText(TermList.this,"put in sample data",Toast.LENGTH_LONG).show();
//
//            Term term = new Term(0, "Spring", "01-01-23", "05-30-23");
//            repository.insert(term);
//            term = new Term(0, "Summer", "06-01-23", "09-30-23");
//            repository.insert(term);
//
//            Course course = new Course(0, "Project Plus", "01-01-23",
//                    "03-30-23", 1, "Adam Smith",
//                    "123-456-7890", "adam.smith@wgu.edu", "Sample Note 1");
//            repository.insert(course);
//            course = new Course(0, "Software 1", "04-01-23",
//                    "07-30-23", 1, "John Doe",
//                    "312-416-7410", "john.doe@wgu.edu", "Sample Note 2");
//            repository.insert(course);
//            return true;
//        }
//        if (item.getItemId() == android.R.id.home) {
//            this.finish();
////            Intent intent=new Intent(TermList.this,TermList.class);
////            startActivity(intent);
//            return true;
//        }
//
//        return true;
//
//    }
}