package mapping.marcin.wisniewski.com.mapping;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseMapType extends ListActivity {

    String[] data, desc;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        data = new String[] {"Regular Map", "Cycle Map"};
        desc = new String[] {"Just a regular view", "Just a cycle view"};
        MyAdapter adapter = new MyAdapter();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView lv, View view, int index, long id)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        boolean cyclemap = false;
//        String selectedValue = (String)getListAdapter().getItem(index);
        if(index == 1)
        {
            cyclemap = true;
        }
        bundle.putBoolean("chosenmap", cyclemap);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    public class MyAdapter extends ArrayAdapter<String>{
        public MyAdapter()
        {
            super(ChooseMapType.this, android.R.layout.simple_list_item_1, data);
        }

        public View getView(int index, View converView, ViewGroup parent){
            View view = converView;
            if(view == null)
            {
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate((R.layout.choose_map_type), parent, false);
            }
            TextView title = (TextView)view.findViewById(R.id.maptype), detail = (TextView)view.findViewById(R.id.mapdesc);
            title.setText(data[index]);
            detail.setText(desc[index]);
            return view;
        }
    }
}