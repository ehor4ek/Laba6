package com.example.laba6;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListActivity extends Activity {
    private User user;
    private boolean flag = false;
    private Date arrivingTime;
    private Button nowBtn;
    private ArrayList<Sight> sights, goodSights, nowSights;
    private Date nowTime;
    private boolean isNow = false;
    private ListView lvMain;
    private ArrayAdapter<String> adapter, adapterEx;
    private String[] names, namesEx;
    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    @SuppressLint("MissingInflatedId")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        TextView greetings = findViewById(R.id.greetings);
        sights = new ArrayList<Sight>();
        try {
            sights.add(new Sight("Знаменская церковь", "Знаменский храм первоначально построен в 1683 году, неоднократно перестраивался и благоукрашался. В 1930-е годы был закрыт, частично разрушен и разорен. Сейчас восстановлен и действует. Каменная церковь в честь иконы Пресвятой Богородицы «Знамение» в селе Губайлово построена в 1683 г. боярином Иваном Федоровичем Волынским.",
                    55.820538, 37.322062, "14:00-18:00"));
            sights.add(new Sight("ДК Подмосковье", "Дворец культуры «Подмосковье» - уникальное учреждение культуры, расположенное в историко–культурном центре Красногорка, рядом с усадьбой 'Знаменское-Губайлово'. В настоящее время Дворец культуры «Подмосковье» является не только центром культурной жизни, но и современным, многофункциональным культурным центром, местом комфортного общения и самореализации жителей Красногорска.",
                    55.824128, 37.319278, "10:00-22:30"));
            sights.add(new Sight("Опалиховский лесопарк", "Опалиховский лес (Опалиховский лесопарк) — хвойный лес, преимущественно сосновый и еловый, площадью 2989 га. Относится к защитным лесам Москвы, основан в 1935 году.",
                    55.823387, 37.283126, "11:00-22:30"));
            sights.add(new Sight("Зелёный театр", "В Городском парке располагаются такие объекты, как «Зелёный театр» с открытой концертной площадкой, профессиональной сценой, гримерными комнатами и зрительным залом, рассчитанным на 2400 человек; аттракционы для детей и взрослых; ротонда, парковые скульптуры, изящные мостики и беседки; игровые и спортивные площадки, специализированная площадка с уличными тренажёрами workout.",
                    55.823894,37.326926, "15:00-00:00"));
            sights.add(new Sight("Ивановские пруды", "Парк культуры и отдыха «Ивановские пруды» расположен в центре г. Красногорск. В 2017 году здесь были проведены масштабные реконструкционные работы. Фотография из свободных источников. ... Водоемы были вычищены, а набережные, береговая линия и близлежащие окрестности облагорожены. Тропинки для пеших прогулок покрыли брусчаткой, сделали дорожки для велосипедистов и тех, кто занимается бегом, вдоль тропинок и дорожек установили без малого 60 удобных лавочек для отдыха.",
                    55.819764, 37.316468, "13:30-23:00"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        goodSights = sights;
        Bundle arguments = getIntent().getExtras();
        //flag = (boolean) arguments.getSerializable("flag");
        if (!flag) {
            user = arguments.getParcelable(User.class.getSimpleName());
            arrivingTime = (Date) arguments.getSerializable(Date.class.getSimpleName());
            greetings.setText("Здравствуйте, " + user.getSurname() + " " + user.getName());
            goodSights = new ArrayList<Sight>();
            for(int i = 0; i < sights.size(); i++) {
                try {
                    if ( sights.get(i).getTime().equals("-") ||
                            (formatter.parse(sights.get(i).getRealTime0()).getTime() <= arrivingTime.getTime() &&
                                    arrivingTime.getTime() <= formatter.parse(sights.get(i).getRealTime1()).getTime()) )
                        goodSights.add(sights.get(i));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        names = new String[goodSights.size()];
        for(int i = 0; i < goodSights.size(); i++) {
            names[i] = goodSights.get(i).getName() + "\nВремя работы: " + goodSights.get(i).getTime();
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        lvMain = (ListView)findViewById(R.id.mainLV);
        lvMain.setAdapter(adapter);
        nowBtn = findViewById(R.id.now_btn);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListActivity.this, InfoActivity.class);
                intent.putExtra(User.class.getSimpleName(), user);
                intent.putExtra(Date.class.getSimpleName(), arrivingTime);
                if (!isNow)
                    intent.putExtra(Sight.class.getSimpleName(), goodSights);
                else
                    intent.putExtra(Sight.class.getSimpleName(), nowSights);
                System.out.println(goodSights.get(i).toString());
                intent.putExtra("flag", flag);
                intent.putExtra("i", i);
                startActivityForResult(intent, 1);
            }
        });

        Context c = this;

        nowBtn.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View view) {
                if (!isNow) {
                    try {
                        nowTime = formatter.parse(formatter.format(new Date()));
                        nowSights = new ArrayList<Sight>();
                        for(int i = 0; i < sights.size(); i++) {
                            if ( sights.get(i).getTime().equals("-") ||
                                    (formatter.parse(sights.get(i).getRealTime0()).getTime() <= nowTime.getTime() &&
                                            nowTime.getTime() <= formatter.parse(sights.get(i).getRealTime1()).getTime()) )
                                nowSights.add(sights.get(i));
                        }
                        namesEx = new String[nowSights.size()];
                        for(int i = 0; i < nowSights.size(); i++) {
                            namesEx[i] = nowSights.get(i).getName() + "\nВремя работы: " + nowSights.get(i).getTime();
                        }
                        adapterEx = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, namesEx);
                        lvMain.setAdapter(adapterEx);
                        isNow = true;
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    lvMain.setAdapter(adapter);
                    isNow = false;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        intent.putExtra("flag", flag);
        startActivityForResult(intent, 1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle arguments = intent.getExtras();
        flag = arguments.getBoolean("flag");
    }
}