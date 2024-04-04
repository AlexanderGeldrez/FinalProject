package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;

public class MenuHelper {

    public static void createOptionsMenu(Menu menu, Context context) {
        menu.add("Help").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Show help dialog
                showHelpDialog(context);
                return true;
            }
        });
    }

    private static void showHelpDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Help");
        builder.setMessage("Instructions for using the interface:\n- Enter your query in the search field.\n- Click on the 'Lookup' button to search.\n- Click on an item to view more details.\n- Long press on an item to delete it from the database.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

