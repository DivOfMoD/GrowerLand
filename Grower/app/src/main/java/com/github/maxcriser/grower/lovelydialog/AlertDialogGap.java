package com.github.maxcriser.grower.lovelydialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

public class AlertDialogGap {

    public static View.OnClickListener wrap(Dialog.OnClickListener listener) {
        return new DialogOnClickListenerAdapter(listener);
    }

    static class DialogOnClickListenerAdapter implements View.OnClickListener {

        private Dialog.OnClickListener adapted;

        DialogOnClickListenerAdapter(DialogInterface.OnClickListener adapted) {
            this.adapted = adapted;
        }

        public void onClick(DialogInterface dialogInterface, int which) {
            if (adapted != null) {
                adapted.onClick(dialogInterface, which);
            }
        }

        @Override
        public void onClick(View v) {

        }
    }
}
