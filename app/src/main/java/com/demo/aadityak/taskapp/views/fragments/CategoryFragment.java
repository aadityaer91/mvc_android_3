package com.demo.aadityak.taskapp.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.aadityak.taskapp.R;
import com.demo.aadityak.taskapp.events.DataRequestEvent;
import com.demo.aadityak.taskapp.model.adapter.CLAdapter;
import com.demo.aadityak.taskapp.realmStorage.CategoriesTable;
import com.demo.aadityak.taskapp.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by aaditya on 22/11/17.
 */

public class CategoryFragment extends BaseFragment {
    @Bind(R.id.plRecyclerView)
    RecyclerView recyclerView;

    CLAdapter adapter;
    ArrayList<CategoriesTable> clList = new ArrayList<>();
    int parentCategoryId = -1;

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    public static CategoryFragment newInstance(int categoryId) {
        Bundle args = new Bundle();
        args.putInt("parentCategoryId", categoryId);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);

        initialiseRecyclerView();

        Bundle args = getArguments();
        if (args != null && args.containsKey("parentCategoryId")) {
            parentCategoryId = args.getInt("parentCategoryId");
        }

        if (clList.size() > 0) {
            Timber.v("Already have list");
        } else {
            requiredDataForList(parentCategoryId);
        }

        return view;
    }

    void initialiseRecyclerView() {
        adapter = new CLAdapter(getContext(), clList);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    void requiredDataForList(int parentCategoryId) {
        if (parentCategoryId == -1) {
            EventBus.getDefault().post(new DataRequestEvent(DataRequestEvent.EventType.RequestAllCategories));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("action", AppConstants.ActionChildCategoryData);
            bundle.putInt("parentCategoryId", parentCategoryId);
            DataRequestEvent dataRequestEvent = new DataRequestEvent(DataRequestEvent.EventType.RequestChildCategories, bundle);
            EventBus.getDefault().post(dataRequestEvent);
        }
    }

    @Override
    public void updateData(Object appResponseData, String action) {
        if (action.equalsIgnoreCase(AppConstants.ActionCategoryData)) {
            CategoriesTable[] categories = (CategoriesTable[]) appResponseData;
            clList.clear();
            clList.addAll(Arrays.asList(categories));
            adapter.notifyDataSetChanged();
        }else if(action.equalsIgnoreCase(AppConstants.ActionChildCategoryData)){
            CategoriesTable[] categories = (CategoriesTable[]) appResponseData;
            clList.clear();
            clList.addAll(Arrays.asList(categories));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateSuccess(Bundle bundle) {

    }

    @Override
    public void updateError(Bundle bundle) {
        Toast.makeText(getContext(), bundle.getString("message"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
