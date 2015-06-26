package com.dialoglib;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BasicDialogFragment extends DialogFragment {

    private static String KEY_LAYOUT_ID = "res_id";
    private static String KEY_TITLE = "title_key";
    private static String TAG = "basic_dialog";
    private OnLayoutInflateListener mViewInflatListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnLayoutInflateListener) {
            mViewInflatListener = (OnLayoutInflateListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement onViewInflat");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        int resId = args.getInt(KEY_LAYOUT_ID);
        int title = args.getInt(KEY_TITLE);
        getDialog().setTitle(getString(title));
        View view = inflater.inflate(resId, container);
        mViewInflatListener.onLayoutInflate(view);
        return view;
    }

    public static void removeBasicDialog(Activity target) {

        if (target.isFinishing())
            return;
        target.getFragmentManager().executePendingTransactions();
        Fragment frag = target.getFragmentManager().findFragmentByTag(TAG);
        if (frag instanceof BasicDialogFragment) {
            ((BasicDialogFragment) frag).dismiss();
        }
    }


    public static void createBasicDialog(Activity target, int title, int resId) {
        BasicDialogFragment basicDialogFragment = new BasicDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LAYOUT_ID, resId);
        bundle.putInt(KEY_TITLE, title);
        basicDialogFragment.setArguments(bundle);
        basicDialogFragment.show(target.getFragmentManager(), TAG);
    }

    public interface OnLayoutInflateListener {
        void onLayoutInflate(View view);
    }
}
