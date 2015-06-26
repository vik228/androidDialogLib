package com.dialoglib;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class CustomAlertDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private static String KEY_TITLE = "title";
    private static String TAG = "alert_dialog";
    private static String KEY_DIALOG_ID = "dialog_id";
    private static String KEY_MESSAGE = "message";
    private AlertDialogClickListener mListener;
    private int mDialogId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = getArguments().getInt(KEY_TITLE);
        int message = getArguments().getInt(KEY_MESSAGE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(title));
        builder.setMessage(getString(message));
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        Dialog alertDialog = builder.create();
        return alertDialog;
    }

    public static void CreateAlertDialog (Activity target, int title, int dialogId, int message) {
        CustomAlertDialogFragment customAlertDialogFragment = new CustomAlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TITLE,title);
        bundle.putInt(KEY_DIALOG_ID, dialogId);
        bundle.putInt(KEY_MESSAGE,message);
        customAlertDialogFragment.setArguments(bundle);
        customAlertDialogFragment.show(target.getFragmentManager(), TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogId = getArguments().getInt(KEY_DIALOG_ID);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof AlertDialogClickListener){
            mListener = (AlertDialogClickListener)activity;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(null != mListener){
            mListener.onClick(dialog,mDialogId,which);
        }

    }

    public interface AlertDialogClickListener {
        void onClick(DialogInterface dialog, int dialogId, int which);
    }

}
