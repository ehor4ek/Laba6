package com.example.laba6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.Date;

public class InfoActivity extends Activity {
    private MapView mapView;
    private boolean flag = false;
    private ArrayList<Sight> sights;
    private TextView header, main_info;
    private Date arrivingTime;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        user = arguments.getParcelable(User.class.getSimpleName());
        arrivingTime = (Date) arguments.getSerializable(Date.class.getSimpleName());
        sights = arguments.getParcelableArrayList(Sight.class.getSimpleName());
        flag = arguments.getBoolean("flag");
        int i = (int) arguments.getSerializable("i");
        if (!flag)
            MapKitFactory.setApiKey("439164fa-080a-490e-a2e1-d40e22f22218");
        setContentView(R.layout.info);
        header = findViewById(R.id.header);
        main_info = findViewById(R.id.main_info);
        header.setText(sights.get(i).getName());
        main_info.setText("Время работы: " + sights.get(i).getTime() + "\n" + sights.get(i).getDescription());
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.getMap().move(new CameraPosition(new Point(sights.get(i).getX(), sights.get(i).getY()),
                        15.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0), null);
        mapView.getMap().addMapObjectLayer("asdfg").addPlacemark(new Point(sights.get(i).getX(), sights.get(i).getY()));
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InfoActivity.this, ListActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        intent.putExtra(Date.class.getSimpleName(), arrivingTime);
        intent.putExtra("flag", true);
        setResult(RESULT_OK, intent);
        finish();
    }
}