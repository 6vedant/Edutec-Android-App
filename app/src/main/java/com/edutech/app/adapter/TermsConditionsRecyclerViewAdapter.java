package com.edutech.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edutech.app.R;
import com.edutech.app.prototype.TermsConditonsPrototype;

import java.util.ArrayList;

/**
 * Created by vedant on 1/15/2018.
 */

public class TermsConditionsRecyclerViewAdapter extends RecyclerView.Adapter<TermsConditionsRecyclerViewAdapter.MyViewHolder> {


    Context context;
    public ArrayList<TermsConditonsPrototype> termsConditonsPrototypes;

    public TermsConditionsRecyclerViewAdapter(Context context, ArrayList<TermsConditonsPrototype> termsConditonsPrototypes) {
        this.context = context;
        this.termsConditonsPrototypes = termsConditonsPrototypes;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.termscondition_item, parent, false);
        return new MyViewHolder(layout);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text.setText(termsConditonsPrototypes.get(position).getText());


    }

    @Override
    public int getItemCount() {
        return termsConditonsPrototypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.terms_textviewitem);
        }


    }
}
