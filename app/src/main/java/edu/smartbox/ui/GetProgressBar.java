package edu.smartbox.ui;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import edu.smartbox.R;

/**
 * Created by adityaagrawal on 19/04/16.
 */

public class GetProgressBar {
    public static MaterialDialog.Builder get(Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context).content(context.getResources().getString(R.string.loading)).cancelable(false).progress(true, 0);
        return builder;
    }
}