package com.schapp.akbar.logccscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class PickingList extends AppCompatActivity {

    ArrayList<String> list;
    String[] _PickItem;
    static   String _Url_fix = "";
    static    String _Url;
    static String _Value_Type="PTW";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    static String _ValueJson="";
    ListView listView;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_list);

        preferences = PickingList.this.getSharedPreferences("settingmain", PickingList.this.MODE_PRIVATE);
        editor=preferences.edit();
        listView = (ListView) findViewById(R.id.mobile_list);
        listView.setScrollContainer(true);
        BindSetting();
        list = new ArrayList<String>();
        _Url= _Url_fix + "/017/1";
        new PickingList.PickAlocated().execute("017");



       // adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, _PickItem);
       // listView.setAdapter(adapter);




    }

    void BindSetting()
    {
        AppSetting _appsetting=new AppSetting(PickingList.this);
        _Url_fix=_appsetting._getAPISERVER();

    }

    String LoadLogin()
    {

        AppSetting _appsetting=new AppSetting(PickingList.this);
        String _DataLogin= _appsetting._TempUserLogin();
        return _DataLogin;
    }


    public class PickAlocated extends AsyncTask<String,Void,JSONObject> {

        protected  String _TagProcess="";
        ProgressDialog pd;
        @Override
        protected JSONObject doInBackground(String... params) {

            JSONParserArray jParser = new JSONParserArray();
            JSONArray json = jParser.getJSONFromUrl(_Url);
            JSONObject jsonObject = null;
            try {
                _TagProcess=params[0];
                jsonObject = json.getJSONObject(0);
                _PickItem=new String[json.length()];
                for (int i = 0; i < json.length(); i++) {
                    _PickItem[i]=json.getJSONObject(i).getString("wavekey");
                    list.add(json.getJSONObject(i).toString());
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new ArrayAdapter<String>(PickingList.this,  R.layout.activity_listview, _PickItem);
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // ListView Clicked item index
        int itemPosition = position;

        // ListView Clicked item value
        String itemValue = (String) listView.getItemAtPosition(position);

        // Show Alert
      //  Toast.makeText( PickingList.this, "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG).show();
        isCancelled();
        Intent OPK=new Intent(PickingList.this, OrderPickingActivity.class);
        OPK.putExtra("wavekeysession",itemValue);
        startActivity(OPK);

    }
});

            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(PickingList.this);
            pd.setMessage("Getting data WMS..");
            pd.show();

        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);

                switch (_TagProcess) {
                    case "017":
                        if (s==null) {

                        } else {
                            listView.setAdapter(adapter);
                        }
                        break;

                }


                if (pd != null)
                {
                    pd.dismiss();
                }
            }



        }


    }



