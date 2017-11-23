package com.demo.aadityak.taskapp.model.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.aadityak.taskapp.R;
import com.demo.aadityak.taskapp.events.UISwitchEvent;
import com.demo.aadityak.taskapp.realmStorage.CategoriesTable;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.demo.aadityak.taskapp.utils.AppUtils.isValidString;

/**
 * Created by aaditya on 15/11/17.
 */

public class CLAdapter extends RecyclerView.Adapter<CLAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoriesTable> plList;

    public CLAdapter(Context context, ArrayList<CategoriesTable> plList) {
        this.context = context;
        this.plList = plList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cl_list_item, parent, false);
        return new CLAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String plName = plList.get(position).getName();
        if (isValidString(plName)) {
            holder.tvPlName.setText(plName);
        }

        holder.llActualItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("parentCategoryId", plList.get(position).getId());
                if(plList.get(position).getChildType() == 1) {
                    EventBus.getDefault().post(new UISwitchEvent(UISwitchEvent.EventType.CategoryFragmentLoad, args));
                }else{
                    EventBus.getDefault().post(new UISwitchEvent(UISwitchEvent.EventType.ProductFragmentLoad, args));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return plList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvPlName)
        TextView tvPlName;

        @Bind(R.id.llActualItem)
        LinearLayout llActualItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
