package com.example.laba6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yandex.mapkit.MapKitFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText surname;
    private EditText name;
    private EditText email;
    private EditText time_input;
    private User user;
    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    private Date arrivingTime;
    //кнопка в данный момент
    //ввод времени прибытия на главной

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                surname = findViewById(R.id.surname_input);
                name = findViewById(R.id.name_input);
                email = findViewById(R.id.email_input);
                time_input = findViewById(R.id.time_input);
                if(checkData()) {
                    user = new User(name.getText().toString(), surname.getText().toString(), email.getText().toString());
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra(User.class.getSimpleName(), user);
                    try {
                        arrivingTime = formatter.parse(time_input.getText().toString());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    intent.putExtra(Date.class.getSimpleName(), arrivingTime);
                    intent.putExtra("flag", false);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public boolean checkData() {
        String notification = "";
        boolean l = true;
        if (surname.getText().toString().equals("") || surname.getText().toString().equals(" ")) {
            l = false;
            notification = "Введите фамилию";
        } else if (name.getText().toString().equals("") || name.getText().toString().equals(" ")) {
            l = false;
            notification = "Введите имя";
        } else if (email.getText().toString().equals("") || email.getText().toString().equals(" ") || !email.getText().toString().contains("@")) {
            l = false;
            notification = "Неверный формат для e-mail";
        } else if (time_input.getText().toString().equals("") || time_input.getText().toString().contains(" ") || !time_input.getText().toString().contains(":")) {
            l = false;
            notification = "Неверный формат времени пребытия";
        }
        if (!l)
            Toast.makeText(this, notification, Toast.LENGTH_LONG).show();
        return l;
    }
}