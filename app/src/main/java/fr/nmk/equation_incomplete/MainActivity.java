package fr.nmk.equation_incomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.nmk.equation_incomplete.model.Equation;
import fr.nmk.equation_incomplete.model.Level;
import fr.nmk.equation_incomplete.model.Position;

public class MainActivity extends AppCompatActivity {

    private TextView txtA;
    private TextView txtOperation;
    private TextView txtB;
    private TextView txtEquals;
    private TextView txtC;

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

    private String nombreSaisie = "...";

    private Level level;
    private Equation equation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        level = Level.easy;
        equation = new Equation(level);

        init_objetcs();
        handle_button_click();
        initializeView();
    }



    private void click_listener(String str) {
        if (str.equals("Pass")) {

        }else {
            if (str.equals("C") && !nombreSaisie.equals("...")) {
                nombreSaisie = nombreSaisie.substring(0, nombreSaisie.length()-1);
                if (nombreSaisie.equals("")) {
                    nombreSaisie = "...";
                }
            }else if (!str.equals("C") && nombreSaisie.equals("...")) {
                nombreSaisie = str;
            }else if (!str.equals("C") && !nombreSaisie.equals("...")) {
                nombreSaisie += str;
            }
        }

        if (equation.getPositionToHide().equals(Position.left)) {
            txtA.setText(nombreSaisie);
        }else if (equation.getPositionToHide().equals(Position.right)) {
            txtB.setText(nombreSaisie);
        }else if (equation.getPositionToHide().equals(Position.result)) {
            txtC.setText(nombreSaisie);
        }

        if (!nombreSaisie.equals("...")) {
            boolean correct = equation.verifyValue(Integer.parseInt(nombreSaisie));
            if (correct) {
                Toast.makeText(MainActivity.this.getApplicationContext(), "Bravooo le nombre correcte est " + nombreSaisie, Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void initializeView() {
        txtA.setText(equation.getAStr());
        txtOperation.setText(equation.getOperationStr());
        txtB.setText(equation.getBStr());
        txtC.setText(equation.getCStr());
    }

    private void init_objetcs() {
        txtA = (TextView)findViewById(R.id.txtA);
        txtOperation = (TextView)findViewById(R.id.txtOperation);
        txtB = (TextView)findViewById(R.id.txtB);
        txtEquals = (TextView)findViewById(R.id.txtEquals);
        txtC = (TextView)findViewById(R.id.txtC);

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
}