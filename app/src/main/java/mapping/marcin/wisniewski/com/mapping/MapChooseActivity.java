package mapping.marcin.wisniewski.com.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapChooseActivity extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mca);

        Button regular = (Button)findViewById(R.id.btnRegular);
        regular.setOnClickListener(this);
        Button cyclemap = (Button)findViewById(R.id.btnCyclemap);
        cyclemap.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        boolean cyclemap = false;
        if(v.getId() == R.id.btnCyclemap)
        {
            cyclemap=true;
        }
        bundle.putBoolean("chosenmap", cyclemap);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
