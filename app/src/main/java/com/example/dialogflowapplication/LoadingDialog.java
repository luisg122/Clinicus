package com.example.dialogflowapplication;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;

    LoadingDialog(Activity activity){
        this.activity = activity;
    }

    void startLoadingDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.progress_bar_dialog, null);

        builder.setView(view);
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

        //Window window = alertDialog.getWindow();
        //assert window != null;
        //window.setLayout(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        windowResize(alertDialog);
    }

    private void windowResize(AlertDialog alertDialog){
        // Store access variables for window and blank point
        Window window = alertDialog.getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        assert window != null;
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 85% of the screen width
        window.setLayout((int) (size.x * 0.85), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
    }



    void dismissDialog(){
        alertDialog.dismiss();
    }


}
