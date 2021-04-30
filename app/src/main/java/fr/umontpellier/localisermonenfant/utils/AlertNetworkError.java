package fr.umontpellier.localisermonenfant.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class AlertNetworkError {


    public static void showNetworkDialog(Context context, String message){
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Erreur !");

        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
