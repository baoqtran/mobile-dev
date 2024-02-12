package com.baotran.schoolscheduling.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baotran.schoolscheduling.R;
import com.baotran.schoolscheduling.entities.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemView;
        private final TextView courseItemView2;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView3);
            courseItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("courseid", current.getCourseID());
                    intent.putExtra("termid", current.getTermID());
                    intent.putExtra("coursename", current.getCourseName());
                    intent.putExtra("coursestart", current.getCourseStartDate());
                    intent.putExtra("courseend", current.getCourseEndDate());
//                    intent.putExtra("coursestatus", true);
                    intent.putExtra("courseinsname", current.getCourseInsName());
                    intent.putExtra("courseinsphone", current.getCourseInsPhone());
                    intent.putExtra("courseinsemail", current.getCourseInsEmail());
                    intent.putExtra("note", current.getNote());

                    context.startActivity(intent);
                }
            });
        }
    }
    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(mCourses != null){
            Course current = mCourses.get(position);
            String coursename = current.getCourseName();
            int termID= current.getTermID();
            holder.courseItemView.setText(coursename);
            holder.courseItemView2.setText(Integer.toString(termID));
        }
        else{
            holder.courseItemView.setText("No course name");
            holder.courseItemView.setText("No term id");
        }
    }
    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }
    public int getItemCount() {

        if(mCourses != null) return mCourses.size();
        else return 0;
    }
}


