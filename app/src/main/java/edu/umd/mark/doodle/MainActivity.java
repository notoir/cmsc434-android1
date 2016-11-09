package edu.umd.mark.doodle;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String[] _colorEntries;
    private String[] _opacityEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        this._colorEntries = new String[] {
                "Red", "Blue", "Yellow", "Green", "Blue", "Magenta", "Black"
        };
        Spinner colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, _colorEntries);
        colorSpinner.setAdapter(colorAdapter);

        // set up color spinner
        this._opacityEntries = new String[] {
                "20%", "40%", "60%", "80%", "100%"
        };
        Spinner opacitySpinner = (Spinner) findViewById(R.id.opacitySpinner);
        ArrayAdapter<String> opacityAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, _opacityEntries);
        opacitySpinner.setAdapter(opacityAdapter);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DoodleView doodle = (DoodleView)findViewById(R.id.doodleView);
                doodle.setColor(_colorEntries[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        opacitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DoodleView doodle = (DoodleView)findViewById(R.id.doodleView);
                doodle.setOpacity(_opacityEntries[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doodle, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //View menuItemView = findViewById(R.id.miBrush);
        switch (item.getItemId()) {
            case R.id.miBrush:
                View brushItemView = findViewById(R.id.miBrush);
                chooseBrushSize(brushItemView);
                return true;
            case R.id.miClear:
                View clearItemView = findViewById(R.id.miClear);
                clearView(clearItemView);
                return true;
            case R.id.miUndo:
                View undoView = findViewById(R.id.miUndo);
                undo(undoView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void undo(View v) {
        PopupMenu undo = new PopupMenu(MainActivity.this, v);
        undo.inflate(R.menu.undo_popup);
        //Toast.makeText(MainActivity.this,"Method is called!", Toast.LENGTH_SHORT).show();
        undo.show();

        undo.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                DoodleView doodle = (DoodleView)findViewById(R.id.doodleView);
                doodle.undo();
                Toast.makeText(MainActivity.this, "Last stroke undone.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void clearView(View v) {
        PopupMenu clear = new PopupMenu(MainActivity.this, v);
        clear.inflate(R.menu.clear_popup);
        //Toast.makeText(MainActivity.this,"Method is called!", Toast.LENGTH_SHORT).show();
        clear.show();

        clear.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                DoodleView doodle = (DoodleView)findViewById(R.id.doodleView);
                doodle.clearDoodle();
                Toast.makeText(MainActivity.this, "Drawing cleared.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void chooseBrushSize(View v) {
        //Toast.makeText(MainActivity.this,"Method is called!", Toast.LENGTH_SHORT).show();
        PopupMenu brushSize = new PopupMenu(MainActivity.this, v);
        // populate dropdown
        brushSize.inflate(R.menu.brush_popup);
        brushSize.show();

        brushSize.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                DoodleView doodle = (DoodleView) findViewById(R.id.doodleView);
                doodle.setBrushSize(item.getTitle());
                Toast.makeText(MainActivity.this, "Brush size: " +
                        item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
