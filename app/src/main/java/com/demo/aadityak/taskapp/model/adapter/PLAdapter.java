package com.demo.aadityak.taskapp.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.aadityak.taskapp.R;
import com.demo.aadityak.taskapp.realmStorage.ProductsTable;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aaditya on 22/11/17.
 */

public class PLAdapter extends RecyclerView.Adapter<PLAdapter.ViewHolder>{

    Context context;
    ArrayList<ProductsTable> plList;

    public PLAdapter(Context context, ArrayList<ProductsTable> plList) {
        this.context = context;
        this.plList = plList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pl_list_item, parent, false);
        return new PLAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvPlName.setText(plList.get(position).getName());
        holder.tvPlTaxLabel.setText(plList.get(position).getTaxType());
        holder.tvPlTax.setText(String.valueOf(plList.get(position).getTax()));
    }

    @Override
    public int getItemCount() {
        return plList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvPlName)
        TextView tvPlName;

        @Bind(R.id.tvPlTaxLabel)
        TextView tvPlTaxLabel;

        @Bind(R.id.tvPlTax)
        TextView tvPlTax;

        @Bind(R.id.llActualItem)
        LinearLayout llActualItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
