package com.dialoglib;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;


public class ProgressDialogFragment extends DialogFragment {

    public static final String TAG_PROGRESS_DIALOG = "progress_dialog";

    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";

    public static final int INVALID_RESOURCE = -1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        int title = args.getInt(KEY_TITLE);
        int message = args.getInt(KEY_MESSAGE);
        ProgressDialog dialog = new ProgressDialog(getActivity());
        if (INVALID_RESOURCE != title) {
            dialog.setTitle(title);
        }
        if (INVALID_RESOURCE != message) {
            dialog.setMessage(getString(message));
        }
        return dialog;
    }

    public static void removeDialog(Activity target) {

        if (target.isFinishing())
            return;
        target.getFragmentManager().executePendingTransactions();
        Fragment frag = target.getFragmentManager()
                .findFragmentByTag(TAG_PROGRESS_DIALOG);
        if (frag instanceof ProgressDialogFragment) {
            ProgressDialogFragment progressFrag = (ProgressDialogFragment) frag;
            progressFrag.dismiss();
        }
    }


    public static void createDialog(Activity target, int title, int message) {
        DialogFragment delFrag = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TITLE, title);
        args.putInt(KEY_MESSAGE, message);
        delFrag.setArguments(args);
        delFrag.setCancelable(false);
        delFrag.show(target.getFragmentManager(), TAG_PROGRESS_DIALOG);
    }
}
