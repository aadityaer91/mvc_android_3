package com.demo.aadityak.taskapp.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.aadityak.taskapp.R;
import com.demo.aadityak.taskapp.events.DataRequestEvent;
import com.demo.aadityak.taskapp.model.adapter.PLAdapter;
import com.demo.aadityak.taskapp.services.response.AppResponseData;
import com.demo.aadityak.taskapp.services.response.HomePageListItemData;
import com.demo.aadityak.taskapp.services.response.HomePageResponseData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by aadityak on 15/11/2017.
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.plRecyclerView)
    RecyclerView recyclerView;

    PLAdapter adapter;
    ArrayList<HomePageListItemData> plList = new ArrayList<>();
    int currentPage = 1;
    boolean loading = false;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        initialiseRecyclerView();

        if (plList.size() > 0) {
            Timber.v("Already have list");
        } else {
            requiredDataForList(currentPage);
        }

        return view;
    }

    void initialiseRecyclerView() {
        adapter = new PLAdapter(getContext(), plList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = linearLayoutManager.getChildCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!loading && lastVisibleItem >= totalItemCount - 1) {
                    loading = true;
                    requiredDataForList(++currentPage);
                }
            }

        });

    }

    void requiredDataForList(int pageNumber) {
        Bundle bundle = new Bundle();
        bundle.putString("pageNumber", String.valueOf(pageNumber));
        EventBus.getDefault().post(new DataRequestEvent(DataRequestEvent.EventType.RequestProgListData, bundle));
    }

    @Override
    public void updateData(AppResponseData appResponseData, String action) {
        if (appResponseData instanceof HomePageResponseData) {
            HomePageListItemData[] homePageListItemData = ((HomePageResponseData) appResponseData).getItems();
            if (homePageListItemData != null && homePageListItemData.length > 0) {
                plList.addAll(Arrays.asList(homePageListItemData));
                if(plList.size()<10) {
                    adapter.notifyDataSetChanged();
                }else{
                    adapter.notifyItemRangeInserted(plList.size(),homePageListItemData.length);
                }
                if (currentPage == ((HomePageResponseData) appResponseData).getTotalPages()) {
                    loading = true;
                } else {
                    loading = false;
                }
            }
        }
    }

    @Override
    public void updateError(Bundle bundle) {
        Toast.makeText(getContext(), bundle.getString("message"), Toast.LENGTH_SHORT).show();
        loading = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
