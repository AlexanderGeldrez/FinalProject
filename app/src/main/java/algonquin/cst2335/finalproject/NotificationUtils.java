package algonquin.cst2335.finalproject;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

public class NotificationUtils {

    public static void showToast(Context context, @StringRes int messageResId, int duration) {
        Toast.makeText(context, messageResId, duration).show();
    }

    public static void showSnackbar(View view, @StringRes int messageResId, int duration) {
        Snackbar.make(view, messageResId, duration).show();
    }

    public static void showAlertDialog(Context context, @StringRes int titleResId, @StringRes int messageResId) {
        new AlertDialog.Builder(context)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
