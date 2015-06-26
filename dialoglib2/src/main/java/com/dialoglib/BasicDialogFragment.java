package com.dialoglib;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BasicDialogFragment extends DialogFragment {

    public static String RES_ID_KEY = "resId";
    public static String TITLE_KEY = "titleKey";
    public static String TAG = "basicDialog";
    private OnViewInflat mViewInflatListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnViewInflat) {
            mViewInflatListener = (OnViewInflat)activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement onViewInflat");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        int resId  = args.getInt(RES_ID_KEY);
        int title  = args.getInt(TITLE_KEY);
        getDialog().setTitle(getString(title));
        View view = inflater.inflate(resId, container);
        mViewInflatListener.setDialogView(view);
        return view;
    }

    public static void removeBasicDialog (Activity target) {
        try {
            target.getFragmentManager().executePendingTransactions();
            Fragment frag = target.getFragmentManager().findFragmentByTag(TAG);
            if (frag instanceof BasicDialogFragment) {
                ((BasicDialogFragment) frag).dismiss();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void createBasicDialog (Activity target, int title, int resId) {
        BasicDialogFragment basicDialogFragment = new BasicDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RES_ID_KEY, resId);
        bundle.putInt(TITLE_KEY, title);
        basicDialogFragment.setArguments(bundle);
        basicDialogFragment.show(target.getFragmentManager(), TAG);
    }

    public interface OnViewInflat {
         void setDialogView (View view);
    }
}
