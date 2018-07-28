package com.edutech.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edutech.app.R;
import com.edutech.app.prototype.FaqPrototype;

import java.util.ArrayList;

/**
 * Created by vedant on 1/18/2018.
 */

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {

    Context context;
    public ArrayList<FaqPrototype> faqPrototypes;

    public FaqAdapter(Context context,ArrayList<FaqPrototype> faqPrototypes){
        this.context = context;
        this.faqPrototypes = faqPrototypes;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.faqs_itemrecyclerview,parent,false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.question_tv.setText(faqPrototypes.get(position).getQuestion());
        holder.answer_tv.setText(faqPrototypes.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return faqPrototypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView question_tv,answer_tv;

        public MyViewHolder(View itemView) {
            super(itemView);

            question_tv = (TextView)itemView.findViewById(R.id.question_faqitem);
            answer_tv = (TextView)itemView.findViewById(R.id.answer_faqitem);

        }
    }
}
