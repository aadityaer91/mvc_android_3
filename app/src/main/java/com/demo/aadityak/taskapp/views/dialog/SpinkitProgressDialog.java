package com.demo.aadityak.taskapp.views.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.KeyEvent;


import com.demo.aadityak.taskapp.R;

import timber.log.Timber;

/**
 * Created by Aaditya on 28/12/16.
 */
public class SpinkitProgressDialog extends AppCompatDialogFragment {
    public boolean started;
    @Override
    public void setupDialog(Dialog dialog, int style) {
        //super.setupDialog(dialog, style);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_spinkit_progress);
    }
    @Override
    public void onResume() {
        super.onResume();
        Timber.v("Dialog resumed");
        Dialog dialog = getDialog();
        if (dialog != null){
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Timber.v("Caught key code:%d, keyEvent:%s",keyCode,event);
                    return true;
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        started = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        started = false;
    }
}

