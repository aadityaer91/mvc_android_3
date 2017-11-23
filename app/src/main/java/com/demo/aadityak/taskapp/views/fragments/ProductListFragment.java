package com.demo.aadityak.taskapp.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.aadityak.taskapp.R;
import com.demo.aadityak.taskapp.events.DataRequestEvent;
import com.demo.aadityak.taskapp.model.adapter.PLAdapter;
import com.demo.aadityak.taskapp.realmStorage.ProductsTable;
import com.demo.aadityak.taskapp.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aaditya on 22/11/17.
 */

public class ProductListFragment extends BaseFragment {

    @Bind(R.id.plRecyclerView)
    RecyclerView plRecyclerView;

    PLAdapter adapter;
    ArrayList<ProductsTable> plList = new ArrayList<>();
    int categoryId = -1;

    public static ProductListFragment newInstance(int categoryId) {

        Bundle args = new Bundle();
        args.putInt("categoryId", categoryId);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, view);

        initialiseRecyclerView();

        Bundle args = getArguments();
        if (args != null && args.containsKey("categoryId")) {
            categoryId = args.getInt("categoryId");

            if (plList.size() > 0) {
                // Already items available
            } else {
                requiredProductList(categoryId);
            }
        }

        return view;
    }

    void initialiseRecyclerView() {
        adapter = new PLAdapter(getContext(), plList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        plRecyclerView.setLayoutManager(layoutManager);
        plRecyclerView.setHasFixedSize(true);
        plRecyclerView.setAdapter(adapter);
    }

    public void requiredProductList(int categoryId) {
        Bundle args = new Bundle();
        args.putInt("categoryId", categoryId);
        EventBus.getDefault().post(new DataRequestEvent(DataRequestEvent.EventType.RequestProductList, args));
    }

    @Override
    public void updateData(Object appResponseData, String action) {

        if (action.equalsIgnoreCase(AppConstants.ActionProductListData)) {
            ProductsTable[] products = (ProductsTable[]) appResponseData;
            plList.clear();
            plList.addAll(Arrays.asList(products));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateError(Bundle bundle) {

    }

}
