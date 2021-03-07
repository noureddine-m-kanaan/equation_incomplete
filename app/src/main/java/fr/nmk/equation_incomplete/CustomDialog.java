package fr.nmk.equation_incomplete;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.w3c.dom.Text;

import fr.nmk.equation_incomplete.MainActivity;


public class CustomDialog extends AppCompatDialogFragment {
    private final int bestScore;
    private MainActivity mainActivity;

    public CustomDialog(final int bestScore, MainActivity mainActivity) {
        super();
        this.bestScore = bestScore;
        this.mainActivity = mainActivity;
    }


    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_custom_dialog, null);
        TextView txtBesScore = (TextView) view.findViewById(R.id.txtBestScore);
        txtBesScore.setText("Best Score: "+bestScore);
        builder.setView(view)
                .setTitle("Good Job !!!")
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mainActivity.exit();
                        dismiss();
                    }
                })
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mainActivity.restart();
                    }
                });
        return builder.create();
    }
}


