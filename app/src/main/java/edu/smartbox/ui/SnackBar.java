package edu.smartbox.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import edu.smartbox.R;

/**
 * Created by Ankit on 18/09/16.
 */
public class SnackBar {

    public static void show(Context context, String message) {

        try {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/robo_font.otf");

            Snackbar snackbar = Snackbar.with(context)
                    .text(message)
                    .textColor(Color.parseColor("#ffffffff"))
                    .color(Color.parseColor("#4CAF50"))
                    .textTypeface(tf)
                    .type(SnackbarType.MULTI_LINE)
                    .actionLabel(context.getResources().getString(R.string.ok))
                    .actionColor(Color.parseColor("#ffffffff"))
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {

                        }
                    });

            snackbar.setPadding(5, 5, 5, 5);

            SnackbarManager.show(snackbar.duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void success(Context context, String message) {

        try {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/robo_font.otf");

            Snackbar snackbar = Snackbar.with(context)
                    .text(message)
                    .textColor(Color.parseColor("#ffffffff"))
                    .color(Color.parseColor("#388E3C"))
                    .textTypeface(tf)
                    .type(SnackbarType.MULTI_LINE)
                    .actionLabel(context.getString(R.string.ok))
                    .actionColor(Color.parseColor("#ffffffff"))
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {

                        }
                    });

            snackbar.setPadding(5, 5, 5, 5);

            SnackbarManager.show(snackbar.duration(Snackbar.SnackbarDuration.LENGTH_LONG));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void noInternet(Context context) {

        try {


            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/robo_font.otf");

            Snackbar snackbar = Snackbar.with(context)
                    .text(context.getResources().getString(R.string.no_internet))
                    .textColor(Color.parseColor("#ffffffff"))
                    .color(Color.parseColor("#E53935"))
                    .textTypeface(tf)
                    .type(SnackbarType.MULTI_LINE)
                    .actionLabel(context.getResources().getString(R.string.ok))
                    .actionColor(Color.parseColor("#ffffffff"))
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {

                        }
                    });

            snackbar.setPadding(5, 5, 5, 5);

            SnackbarManager.show(snackbar.duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
