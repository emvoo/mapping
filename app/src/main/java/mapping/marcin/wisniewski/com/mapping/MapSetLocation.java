package mapping.marcin.wisniewski.com.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapSetLocation extends Activity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_location);


        Button setlocation = (Button)findViewById(R.id.setlocation);
        setlocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        EditText latitude = (EditText)findViewById(R.id.latitude);
        EditText longitude = (EditText)findViewById(R.id.longitude);
        if(!latitude.getText().toString().equals("") && !longitude.getText().toString().equals(""))
        {
            bundle.putString("latitude",latitude.getText().toString());
            bundle.putString("longitude", longitude.getText().toString());
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "no value", Toast.LENGTH_SHORT).show();
            
        }

    }
}
