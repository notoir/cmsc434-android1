package edu.umd.mark.doodle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doodle, menu);
        return true;
    }

    public void clearView(View v) {
        DoodleView doodle = (DoodleView)findViewById(R.id.doodleView);
        doodle.clearDoodle();
        doodle.invalidate();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        View menuItemView = findViewById(R.id.miBrush);
        switch(item.getItemId()) {
            case R.id.miBrush:
                chooseBrushSize(menuItemView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void chooseBrushSize(View v) {
        //Toast.makeText(MainActivity.this,"Method is called!", Toast.LENGTH_SHORT).show();
        PopupMenu brushSize = new PopupMenu(MainActivity.this, v);
        // populate dropdown
        brushSize.inflate(R.menu.brush_popup);
        brushSize.show();

        brushSize.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                DoodleView doodle = (DoodleView)findViewById(R.id.doodleView);
                doodle.setBrushSize(item.getTitle());
                Toast.makeText(MainActivity.this,"Brush size: " +
                        item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
}
