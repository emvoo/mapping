package mapping.marcin.wisniewski.com.mapping;


import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.Double2;
import android.util.Log;
import android.view.MenuInflater;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;


public class HelloMap extends Activity
{

    MapView mv;

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
        mv.getController().setCenter(new GeoPoint(40.01,22.5));
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
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                Bundle extras = intent.getExtras();
                boolean cyclemap = extras.getBoolean("mapping.marcin.wisniewski.com.mapping.cyclemap");
                if(cyclemap==true)
                {
                    mv.setTileSource(TileSourceFactory.CYCLEMAP);
                }
                else
                {
                    mv.getTileProvider().setTileSource(TileSourceFactory.MAPNIK);
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