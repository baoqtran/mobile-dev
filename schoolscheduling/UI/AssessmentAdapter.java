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
import com.baotran.schoolscheduling.entities.Assessment;
import com.baotran.schoolscheduling.entities.Course;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;


        private AssessmentViewHolder(View itemView) {
        super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessment);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                final Assessment current = mAssessments.get(position);
                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessmentid", current.getAssessmentID());
                intent.putExtra("courseid", current.getCourseID());
                intent.putExtra("name", current.getName());
                intent.putExtra("type", current.getType());
                intent.putExtra("goaldate", current.getGoalDate());
                context.startActivity(intent);
            }
        });
        }
    }

    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            Assessment current = mAssessments.get(position);
            String name = current.getName();
            int courseID= current.getCourseID();
            holder.assessmentItemView.setText(name);
        }
        else{
            holder.assessmentItemView.setText("No Assessments");
        }
    }
    public void setmAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }
    public int getItemCount() {

        if(mAssessments != null) return mAssessments.size();
        else return 0;
    }
}
