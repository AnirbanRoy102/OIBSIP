package com.lamra.calculator;

import androidx.appcompat.app.AppCompatActivity;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView dot, equals, clear, minus, plus, multiply, module, zero, one, two, three, four, five, six, seven, eight, nine;
    TextView history, dashboard;
    String userInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dot = findViewById(R.id.dot);
        equals = findViewById(R.id.equals);
        clear = findViewById(R.id.clear);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        multiply = findViewById(R.id.multiply);
        module = findViewById(R.id.module);
        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);

        history = findViewById(R.id.history);
        dashboard = findViewById(R.id.dashboard);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history.setText("");
                dashboard.setText("");
            }
        });

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + ".");
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "+");
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "-");
            }
        });

        module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "%");
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "x");
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "0");
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "1");
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "2");
            }
        });


        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "3");
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "4");
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "5");
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "6");
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "7");
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "8");
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();
                history.setText(userInput + "9");
            }
        });

        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = history.getText().toString();

                userInput = userInput.replaceAll("x", "*");
                userInput = userInput.replaceAll("%", "/100");

                Context context = Context.enter();
                context.setOptimizationLevel(-1);

                String show = "";

                Scriptable scriptable = context.initStandardObjects();
                show = context.evaluateString(scriptable, userInput, "Javsscript", 1, null).toString();

                dashboard.setText(show);

            }
        });

    }
}