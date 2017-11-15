package com.demo.aadityak.taskapp.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.aadityak.taskapp.App;
import com.demo.aadityak.taskapp.R;
import com.demo.aadityak.taskapp.services.response.HomePageListItemData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.demo.aadityak.taskapp.utils.AppUtils.isValidString;

/**
 * Created by aaditya on 15/11/17.
 */

public class PLAdapter extends RecyclerView.Adapter<PLAdapter.ViewHolder> {

    Context context;
    ArrayList<HomePageListItemData> plList;

    public PLAdapter(Context context, ArrayList<HomePageListItemData> plList) {
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
        String iconUrl = plList.get(position).getIcon();
        if (isValidString(iconUrl)) {
            ImageLoader.getInstance().displayImage(iconUrl, holder.ivIcon, App.defaultDisplayImageOptions());
        }
        String plName = plList.get(position).getName();
        if (isValidString(plName)) {
            holder.tvPlName.setText(plName);
        }
    }

    @Override
    public int getItemCount() {
        return plList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivIcon)
        ImageView ivIcon;

        @Bind(R.id.tvPlName)
        TextView tvPlName;

        @OnClick(R.id.llActualItem)
        public void plItemClicked(){

        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
