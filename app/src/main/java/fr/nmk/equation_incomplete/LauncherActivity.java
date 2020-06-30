package fr.nmk.equation_incomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.nmk.equation_incomplete.model.Level;

public class LauncherActivity extends AppCompatActivity {

    private Button btnEasy;
    private Button btnMedium;
    private Button btnHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        init_objetcs();
        handle_button_click();
    }


    private void init_objetcs() {
        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnHard = (Button) findViewById(R.id.btnHard);
    }

    private void click_listener(Level level) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    private void handle_button_click() {
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LauncherActivity.this.click_listener(Level.easy);
            }
        });
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LauncherActivity.this.click_listener(Level.medium);
            }
        });
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LauncherActivity.this.click_listener(Level.hard);
            }
        });
    }

}