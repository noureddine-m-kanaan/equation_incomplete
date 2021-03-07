package fr.nmk.equation_incomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import fr.nmk.equation_incomplete.model.AboutActivity;
import fr.nmk.equation_incomplete.model.Equation;
import fr.nmk.equation_incomplete.model.Level;
import fr.nmk.equation_incomplete.model.Position;

public class MainActivity extends AppCompatActivity {

    private TextView txtA;
    private TextView txtOperation;
    private TextView txtB;
    private TextView txtC;
    private TextView txtView_score;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnC;
    private Button btnPass;

    private String nombreSaisie = "";
    private Level level;
    private Equation equation;
    private final int nb_max_digit = 4;
    private int currentScore;
    private int bestScore;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSeconds;
    private TextView countDownTimerText;
    private int defaultTextColor = Color.WHITE;
    private int currentTextColor = Color.RED;
    private int correctTextColor = Color.GREEN;
    private boolean timeRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        level = (Level) intent.getSerializableExtra("level");
        setTitle(getTitle() + " ("+ level + ")");
        equation = new Equation(level);
        this.currentScore = 0;
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (this.level == Level.easy) {
            bestScore = prefs.getInt("bestScoreEasy", 0);
            if (bestScore < currentScore) {
                editor.putInt("bestScoreEasy", currentScore);
            }
        } else if (this.level == Level.medium) {
            bestScore = prefs.getInt("bestScoreMedium", 0);
            if (bestScore < currentScore) {
                editor.putInt("bestScoreMedium", currentScore);
            }
        } else {
            bestScore = prefs.getInt("bestScoreHard", 0);
            if (bestScore < currentScore) {
                editor.putInt("bestScoreHard", currentScore);
            }
        }
        init_objetcs();
        handle_button_click();
        initializeView();
        startStop();

    }

    private void win() {
        // informer l'utilisateur qu'il a gagné
        showAnimationCorrectAnswer(false);
        this.currentScore++;
        // après 1.5 secondes, il faut changer l'équation
        changerEquation();
    }



    private void showAnimationCorrectAnswer(boolean isPass) {

        TextView txt = null;
        switch (equation.getPositionToHide()) {
            case left:
                txt = txtA;
                break;
            case right:
                txt = txtB;
                break;
            case result:
                txt = txtC;
                break;
        }
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        txt.setTextColor(currentTextColor);
        // Ne pas changer la couleur en bleu si l'utilisateur a cliqué sur passe
        if (!isPass) {
            txt.setTextColor(correctTextColor);
        }
        txt.startAnimation(myFadeInAnimation);

    }


    private void stopAnimationCorrectAnswer() {

        txtA.clearAnimation();
        txtB.clearAnimation();
        txtC.clearAnimation();

    }

    private void changerEquation() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                equation = new Equation(level);
                nombreSaisie="";
               // expectedScore++;
                initializeView();
            }
        }, 1000);

    }

    private void showMsg(String msg) {

        Toast toast = Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT);
        TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.BLUE);
        View view = toast.getView();
        view.setBackgroundColor(Color.GRAY);
        toast.show();
        // Toast.makeText(MainActivity.this.getApplicationContext(), "Bravooo le nombre correcte est " + nombreSaisie, Toast.LENGTH_SHORT).show();

    }

    private void pass(){

        int correctAsnwer = equation.getTheCorrectAnswer();
        if (correctAsnwer != -1) {
            showAnimationCorrectAnswer(true);
            nombreSaisie = ""+ correctAsnwer;
            update_text_views(nombreSaisie);
        }else {
            showMsg("Any number is correct");
        }
        changerEquation();

    }

    private void erase_last_digit() {

        if (!nombreSaisie.equals("")) {
            nombreSaisie = nombreSaisie.substring(0, nombreSaisie.length() - 1);
            if (nombreSaisie.equals("")) {
                nombreSaisie = "";
            }
        }

    }

    private void add_digit(String str) {

        if(nombreSaisie.length() <= nb_max_digit) {
            nombreSaisie += str;
        }

    }

    private void update_text_views(String str) {

        if (equation.getPositionToHide().equals(Position.left)) {
            txtA.setText(str == "" ? "..." : nombreSaisie);
        }else if (equation.getPositionToHide().equals(Position.right)) {
            txtB.setText(str == "" ? "..." : nombreSaisie);
        }else if (equation.getPositionToHide().equals(Position.result)) {
            txtC.setText(str == "" ? "..." : nombreSaisie);
        }

    }

    private void check_if_win() {

        if (!nombreSaisie.equals("")) {
            boolean correct = equation.verifyValue(Integer.parseInt(nombreSaisie));
            if (correct) {
                win();
            }
        }

    }

    private void click_listener(String str) {

        // update the number
        if (str.equals("Pass")) {
            pass();
        }else if (str.equals("C")){
            erase_last_digit();
            // update text
            update_text_views(nombreSaisie);
        }else {
            add_digit(str);
            // update text
            update_text_views(nombreSaisie);
            // check if the number is correct
            check_if_win();
        }

    }

    private void initializeView() {

        txtA.setText(equation.getAStr());
        txtOperation.setText(equation.getOperationStr());
        txtB.setText(equation.getBStr());
        txtC.setText(equation.getCStr());
        switch (equation.getPositionToHide()) {
            case left:
                txtA.setTextColor(defaultTextColor);
                txtB.setTextColor(defaultTextColor);
                txtC.setTextColor(defaultTextColor);
                break;
            case right:
                txtA.setTextColor(defaultTextColor);
                txtB.setTextColor(defaultTextColor);
                txtC.setTextColor(defaultTextColor);
                break;
            case result:
                txtA.setTextColor(defaultTextColor);
                txtB.setTextColor(defaultTextColor);
                txtC.setTextColor(defaultTextColor);
                break;
        }
        txtView_score.setText("Score : " + this.currentScore);
        stopAnimationCorrectAnswer();

    }

    private void init_objetcs() {

        txtA = (TextView)findViewById(R.id.txtA);
        txtOperation = (TextView)findViewById(R.id.txtOperation);
        txtB = (TextView)findViewById(R.id.txtB);
        txtC = (TextView)findViewById(R.id.txtC);
        txtView_score = (TextView)findViewById(R.id.txtView_score);
        countDownTimerText = (TextView)findViewById((R.id.txtView_timer));
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnC = (Button) findViewById(R.id.btnClear);
        btnPass = (Button) findViewById(R.id.btnPasse);
        timeLeftInMilliSeconds = 60000;
    }

    public void finish(){

        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    private void handle_button_click() {

        btn0.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("0");
            }
        });
        btn1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("1");
            }
        });
        btn2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("2");
            }
        });
        btn3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("3");
            }
        });
        btn4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("4");
            }
        });
        btn5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("5");
            }
        });
        btn6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("6");
            }
        });
        btn7.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("7");
            }
        });
        btn8.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("8");
            }
        });
        btn9.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("9");
            }
        });
        btnC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("C");
            }
        });
        btnPass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.click_listener("Pass");
            }
        });

    }

    public void startStop() {

        if(timeRunning) {
            stopTimer();
        } else {
            startTimer();
        }

    }

    public void startTimer() {
        timeRunning = true;
        countDownTimer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliSeconds = l;
                updateTimer();
            }
            @Override
            public void onFinish() {
                endGame();
                timeRunning = false;
            }
        }.start();
    }

    public void stopTimer() {

        countDownTimer.cancel();
        timeRunning = false;

    }

    public void updateTimer() {
        int seconds = (int) timeLeftInMilliSeconds % 60000 / 1000;
        String timeLeftText = "";
        if(seconds < (10))
            timeLeftText+="0";
        timeLeftText+=seconds;
        countDownTimerText.setText(timeLeftText);

    }

    public void exit() {
        System.exit(0);
    }

    public void restart() {
        // saveBestScore();
        this.currentScore = 0;
        init_objetcs();
        handle_button_click();
        initializeView();
        startStop();
    }

    public void endGame(){
        saveBestScore();
        CustomDialog cdd = new CustomDialog(bestScore, this);
        cdd.show(getSupportFragmentManager(), "exemple");
    }

    public void saveBestScore() {
        // informer l'utilisateur que la partie est terminé
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (this.level == Level.easy) {
            if (bestScore < currentScore) {
                editor.putInt("bestScoreEasy", currentScore);
                bestScore = currentScore;
            }
        } else if (this.level == Level.medium) {
            if (bestScore < currentScore) {
                editor.putInt("bestScoreMedium", currentScore);
                bestScore = currentScore;
            }
        } else {
            if (bestScore < currentScore) {
                editor.putInt("bestScoreHard", currentScore);
                bestScore = currentScore;
            }
        }
        editor.commit();
    }

}