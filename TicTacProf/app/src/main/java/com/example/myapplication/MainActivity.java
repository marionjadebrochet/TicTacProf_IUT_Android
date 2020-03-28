package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final String EXTRA_TEXT1 = "text_to_display";
    final String EXTRA_TEXT2 = "text_to_display";

    String[] tableau  = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button val = findViewById(R.id.val);

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText saisiej1 = findViewById(R.id.editText);
                final EditText saisiej2 = findViewById(R.id.editText2);
                final TextView textViewJ1 = findViewById(R.id.textViewJ1);
                final TextView textViewJ2 = findViewById(R.id.textViewJ2);

                textViewJ1.setText(saisiej1.getText());
                textViewJ2.setText(saisiej2.getText());
            }
        });

        final Button jouer = findViewById(R.id.jouer);

        jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText saisiedej1 = findViewById(R.id.editText);
                final EditText saisiedej2 = findViewById(R.id.editText2);



                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("EXTRA_TEXT1", "" + saisiedej1.getText().toString());
                intent.putExtra("EXTRA_TEXT2", "" + saisiedej2.getText().toString());
                startActivity(intent);
            }
        });
    }
}
