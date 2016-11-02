package edu.umd.mark.doodle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clearView(View v) {
        DoodleView doodle = (DoodleView)findViewById(R.id.doodleView);
        doodle.clearDoodle();
        doodle.invalidate();
    }
}
