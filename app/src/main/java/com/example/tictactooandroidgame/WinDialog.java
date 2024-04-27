package com.example.tictactooandroidgame;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WinDialog extends Dialog {
    private final String message;
    private final MainActivity mainActivity;
    public WinDialog(@NonNull Context context, String message, MainActivity mainActivity) {
        super(context);
        this.message = message;
        this.mainActivity = mainActivity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog_layout);
        final TextView messageText = findViewById(R.id.messageText);
        final Button startAgain = findViewById(R.id.startAgain);
        messageText.setText(message);
        startAgain.setOnClickListener(v -> {
            mainActivity.newGame();
            dismiss();
        });
    }
}
