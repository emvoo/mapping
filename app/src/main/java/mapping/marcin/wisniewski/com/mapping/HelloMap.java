package mapping.marcin.wisniewski.com.mapping;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.MenuInflater;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class HelloMap extends Activity
{

    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;

    class MyOverlayGestureListener implements ItemizedIconOverlay.OnItemGestureListener<OverlayItem>
    {
        public boolean onItemLongPress(int i, OverlayItem item)
        {
            Toast.makeText(HelloMap.this, "LONG PRESS: " + item.getSnippet(), Toast.LENGTH_SHORT).show();
            return true;
        }

        public boolean onItemSingleTapUp(int i, OverlayItem item)
        {
            Toast.makeText(HelloMap.this, "TAP: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    MyOverlayGestureListener markerGestureListener;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this line tells OpenStreetMap about our app.
        // If you miss this out, you might get banned from OSM servers
        Configuration.getInstance().load
                (this, PreferenceManager.getDefaultSharedPreferences(this));

        mv = (MapView)findViewById(R.id.map1);

        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(50.7968, -1.50));

        markerGestureListener = new MyOverlayGestureListener();

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        OverlayItem fernhurst = new OverlayItem("Fernhurst", "Village in West Sussex", new GeoPoint(51.05, -0.72));
        OverlayItem blackdown = new OverlayItem("Blackdown", "Highest point in West Sussex", new GeoPoint(51.0581, -0.6897));
        fernhurst.setMarker(getResources().getDrawable(R.drawable.marker));
        items.addItem(fernhurst);
        items.addItem(blackdown);
        try
        {
            FileReader fr = new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/poi.txt");
            BufferedReader reader = new BufferedReader(fr);
            String line = "";
            while((line = reader.readLine()) != null)
            {
                String[] bitz = line.split(",");
                OverlayItem item = new OverlayItem(bitz[0].trim(), bitz[1].trim(), bitz[2].trim(), new GeoPoint(Double.parseDouble(bitz[4].trim()), Double.parseDouble(bitz[3].trim())));
                if(bitz[1].equals("pub"))
                {
                    item.setMarker(getResources().getDrawable(R.drawable.pub));
                }
                else if(bitz[1].equals("restaurant"))
                {
                    item.setMarker(getResources().getDrawable(R.drawable.rest));
                }
                items.addItem(item);
            }
            mv.getOverlays().add(items);
            reader.close();
        }
        catch(IOException e)
        {
            new AlertDialog.Builder(this).setMessage("ERROR: " + e).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hello_map, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.choosemap)
        {
            // react to the menu item being selected...
            Intent intent = new Intent(this, MapChooseActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        if(item.getItemId() == R.id.setlocation)
        {
            Intent intent = new Intent(this, MapSetLocation.class);
            startActivityForResult(intent, 1);
            return true;
        }
        if(item.getItemId() == R.id.maptypelist)
        {
            Intent intent = new Intent(this, ChooseMapType.class);
            startActivityForResult(intent, 0);
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                Bundle extras = intent.getExtras();
                boolean cyclemap = extras.getBoolean("chosenmap");
                if(cyclemap==true)
                {
                    mv.setTileSource(TileSourceFactory.CYCLEMAP);
                }
                else
                {
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }

        }
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                Bundle extras = intent.getExtras();
                String latitude = extras.getString("latitude");
                String longitude = extras.getString("longitude");
                mv.getController().setCenter(new GeoPoint(Double.parseDouble(latitude), Double.parseDouble(longitude)));
            }
        }
    }



}