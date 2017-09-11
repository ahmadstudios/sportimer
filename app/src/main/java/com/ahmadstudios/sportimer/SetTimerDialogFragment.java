package com.ahmadstudios.sportimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class SetTimerDialogFragment extends DialogFragment {

    interface SetTimerDialogListener {
        void onDialogPositiveClick(int minutes, int seconds, String tag);
    }

    private SetTimerDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (SetTimerDialogListener) activity;
        } catch (ClassCastException castException) {
            throw new ClassCastException(activity.toString() + "должен реализовывать интерфейс SetTimerDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_set_time, null);
        final NumberPicker minutesNumberPicker = view.findViewById(R.id.minutesNumberPicker);
        minutesNumberPicker.setMinValue(0);
        minutesNumberPicker.setMaxValue(59);

        final NumberPicker secondsNumberPicker = view.findViewById(R.id.secondsNumberPicker);
        secondsNumberPicker.setMinValue(0);
        secondsNumberPicker.setMaxValue(59);

        builder.setView(view)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(minutesNumberPicker.getValue(), secondsNumberPicker.getValue(), getTag());
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }
}