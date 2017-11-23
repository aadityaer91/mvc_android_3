package com.demo.aadityak.taskapp.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.demo.aadityak.taskapp.R;
import com.demo.aadityak.taskapp.events.DataRequestEvent;
import com.demo.aadityak.taskapp.events.UISwitchEvent;
import com.demo.aadityak.taskapp.interfaces.UniqueFragmentNaming;
import com.demo.aadityak.taskapp.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by aadityak on 15/11/2017.
 */

public class HomeFragment extends BaseFragment implements UniqueFragmentNaming{

    @Bind(R.id.llProceed)
    LinearLayout llProceed;

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

        return view;
    }

    @OnClick(R.id.llProceed)
    public void proceedClicked(){
        llProceed.setClickable(false);
        requiredDataForList();
    }


    void requiredDataForList() {
        Bundle bundle = new Bundle();
        bundle.putString("action", AppConstants.ActionApiData);
        EventBus.getDefault().post(new DataRequestEvent(DataRequestEvent.EventType.RequestAllData, bundle));
    }

    @Override
    public void updateData(Object appResponseData, String action) {

    }

    @Override
    public void updateSuccess(Bundle bundle) {
        String action= bundle.getString("action");
        llProceed.setClickable(true);
        if(action.equalsIgnoreCase(AppConstants.ActionApiData)){
            Timber.v("API data fetched");
            EventBus.getDefault().post(new UISwitchEvent(UISwitchEvent.EventType.CategoryFragmentLoad));
        }
    }

    @Override
    public void updateError(Bundle bundle) {
        Toast.makeText(getContext(), bundle.getString("message"), Toast.LENGTH_SHORT).show();
        llProceed.setClickable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
