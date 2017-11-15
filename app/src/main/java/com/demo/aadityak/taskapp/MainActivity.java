package com.demo.aadityak.taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.demo.aadityak.taskapp.controller.AppController;
import com.demo.aadityak.taskapp.events.DataFetchedEvent;
import com.demo.aadityak.taskapp.events.DataRequestEvent;
import com.demo.aadityak.taskapp.events.MainFragmentReplaceEvent;
import com.demo.aadityak.taskapp.interfaces.UniqueFragmentNaming;
import com.demo.aadityak.taskapp.views.fragments.BaseFragment;
import com.demo.aadityak.taskapp.views.fragments.HomeFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aadityak on 15/11/2017.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.mainFragmentContainer)
    FrameLayout mainFragmentContainer;

    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        appController = new AppController(this);

        listenFragmentChange();
    }

    @Override
    protected void onStart() {
        super.onStart();
        appController.registerToListenEvent(this);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        resumeMainFragment();
    }

    /**
     * Listening Fragment change event to switch UI, if any
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MainFragmentReplaceEvent event) {
        Log.v("mainActivity", "Replacing fragment");
        replaceMainFragment(event.getFragment(), true, true);
    }

    /**
     * Listening Data request event, if any
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DataRequestEvent event) {

        appController.dataRequestEvent(event);
        //showLoadingDialog();
    }

    /**
     * Listening Data fetched event, if any
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DataFetchedEvent event) {
        appController.dataFetchedEvent(event);
    }

    /**
     * Handling Intent & action based on params, if any
     * @param intent
     */
    public void handleIntent(Intent intent) {
        Intent requestingIntent = intent;
        if (requestingIntent != null) {
            //Handle here if you want to perform some action according to new Intent of already launched app
        }
        resumeMainFragment();
    }

    /**
     * Overriden method responsible for handling new intent launched, if some intent of an app already running
     * @param intent
     */
    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        //please use local intent object, otherwise getIntent will ....
        handleIntent(intent);
    }

    /**
     * Method responsible to handle fragment load & resume if already loaded
     */
    public void resumeMainFragment() {

        BaseFragment nextFragment = currentTopFragment();

        if (nextFragment == null) {
            nextFragment = HomeFragment.newInstance();
        }
        replaceMainFragment(nextFragment, false, false);
    }

    /**
     * Method handling fragment load, transition & backstack
     * @param fragment
     * @param addToBack
     * @param addTransitions
     */
    protected void replaceMainFragment(BaseFragment fragment, boolean addToBack, boolean addTransitions) {
        FragmentManager manager = getSupportFragmentManager();
        String fragmentClassName = simpleClassName(fragment);
        if (fragment instanceof UniqueFragmentNaming) {
            Fragment foundFragment = manager.findFragmentByTag(fragmentClassName);
            if (foundFragment != null) {
                Log.v("MainActivity", "Found existing instance of unique fragment, popping instead of pushing!");
                try {
                    if (fragment instanceof HomeFragment) {
                        popBackStack(true);

                    } else {
                        manager.popBackStack(fragmentClassName, 0);
                    }
                } catch (IllegalStateException e) {
                    supportFinishAfterTransition();
                }
                return;
            }
        }

        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if (addTransitions) {
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        }
        fragmentTransaction.replace(R.id.mainFragmentContainer, fragment, fragmentClassName);
        if (addToBack) {
            fragmentTransaction.addToBackStack(fragmentClassName);
        }
        if (isFinishing()) {
            //Don't do anything, commit allowing state loss will also cause a crash because the current activity is going away
            Log.e("MainActivity", "Activity is finishing, not doing replace main fragment!");
            return;
        }
        if (manager.isDestroyed()) {
            Log.e("MainActivity", "Activity is destroyed, not doing replace main fragment!");
            return;
        }

        fragmentTransaction.commitAllowingStateLoss();
        appController.currentFragment = fragment;

    }

    /**
     * Method which will handle going back to previos fragment from current fragment
     */
    public void popBackStack() {
        //Empty params function to allow single popping without hassle
        popBackStack(false);
    }

    /**
     * Method can lead to first fragment by clearing fragment backstack
     * @param allTheWayHome
     */
    public void popBackStack(boolean allTheWayHome) {
        popBackStack(allTheWayHome, false);
    }

    /**
     * Method can lead to first fragment by clearing fragment backstack immediately
     * @param allTheWayHome
     * @param popImmediate
     */
    public void popBackStack(boolean allTheWayHome, boolean popImmediate) {

        int popFlags = 0;
        if (allTheWayHome) {
            popFlags = FragmentManager.POP_BACK_STACK_INCLUSIVE;
        }
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() == 0) { //DKG: There's nowhere to go back to
            if (allTheWayHome) {
                if (currentTopFragment() instanceof HomeFragment) {
                    HomeFragment homePageFragment = (HomeFragment) currentTopFragment();
                    if (homePageFragment != null) {
                        //do somthing if you want to refresh
                    }
                } else {
                    Log.e("MainActivity", "Non home page fragment is at bottom of stack");
                    replaceMainFragment(HomeFragment.newInstance(), false, false);
                }

            } else {
                finish();
                return;
            }
        }
        if (isFinishing()) {
            Log.e("MainActivity", "Activity is finishing, not doing pop back stack!");
            return;
        }
        try {
            if (popImmediate) {
                manager.popBackStackImmediate(null, popFlags);
            } else {
                manager.popBackStack(null, popFlags);
            }
        } catch (IllegalStateException e) {
            supportFinishAfterTransition();
        }
    }

    /**
     * Method used for getting current loaded fragment on top of fragment backstack
     * @return
     */
    @Nullable
    protected BaseFragment currentTopFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.mainFragmentContainer);
        if (fragment == null) {
            return null;
        }
        if (!(fragment instanceof BaseFragment)) {
            return null;
        }
        return (BaseFragment) fragment;
    }

    public void listenFragmentChange() {
        FragmentManager manager = getSupportFragmentManager();
        manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                appController.currentFragment = currentTopFragment();
            }
        });
    }

    /**
     * method just for fetching class name from object
     * @param object
     * @return
     */
    @Nullable
    public static String simpleClassName(Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass().getSimpleName();
    }

    @Override
    protected void onStop() {
        super.onStop();
        appController.unRegisterToListenEvent(this);
    }
}
