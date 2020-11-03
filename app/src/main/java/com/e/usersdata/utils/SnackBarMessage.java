package com.e.usersdata.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarMessage {

    public static void putMessage(View view, String message) {
        Snackbar.make(view.getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }
}
