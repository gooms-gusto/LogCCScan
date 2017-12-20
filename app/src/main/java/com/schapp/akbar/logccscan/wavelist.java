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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class wavelist extends AppCompatActivity {

    ArrayList<String> list;
    String[] _PickItem;
    String[] _Num;
    String[] _DN;
    static   String _Url_fix = "";
    static    String _Url;
    static String _Value_Type="PIC";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    static String _ValueJson="";
    ListView listView;



    ListView listv,list_head;
    ArrayList<HashMap<String, String>> mylist, mylist_title;
    ListAdapter adapter_title, adapter;
    HashMap<String, String> map1, map2;
    String[] countrys = { "India", "Pakistan", "China", "Bangladesh","Afghanistan"  };
    String[] capitals = { "New Delhi", "Islamabad", "Beijing", "Dhaka"," Kabul" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wavelist);

        listv = (ListView) findViewById(R.id.listView2);
        list_head = (ListView) findViewById(R.id.listView1);

        preferences = wavelist.this.getSharedPreferences("settingmain", wavelist.this.MODE_PRIVATE);
        editor=preferences.edit();

        BindSetting();
        list = new ArrayList<String>();
        _Url= _Url_fix + "/017/1";
        new wavelist.PickAlocated().execute("017");

    }


    void BindSetting()
    {
        AppSetting _appsetting=new AppSetting(wavelist.this);
        _Url_fix=_appsetting._getAPISERVER();

    }

    String LoadLogin()
    {

        AppSetting _appsetting=new AppSetting(wavelist.this);
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
                _DN=new String[json.length()];
                _Num=new String[json.length()];
                for (int i = 0; i < json.length(); i++) {
                    _PickItem[i]=json.getJSONObject(i).getString("wavekey");
                    _Num[i]=String.valueOf(i+1);
                    _DN[i]=String.valueOf(i+1);
                    list.add(json.getJSONObject(i).toString());
                }

                showActivity(_Num,_PickItem,_DN);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new ArrayAdapter<String>(wavelist.this,  R.layout.activity_listview, _PickItem);
            listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // ListView Clicked item index
                    int itemPosition = position;

                    // ListView Clicked item value
                    String itemValue = (String) listView.getItemAtPosition(position);

                    // Show Alert
                    //  Toast.makeText( PickingList.this, "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG).show();
                    isCancelled();
                    Intent OPK=new Intent(wavelist.this, OrderPickingActivity.class);
                    OPK.putExtra("wavekeysession",itemValue);
                    startActivity(OPK);

                }
            });

            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(wavelist.this);
            pd.setMessage("Getting data WMS..");
            pd.show();

        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);

            switch (_TagProcess) {
                case "018":
                    if (s==null) {

                    } else {
                      //  listView.setAdapter(adapter);
                    }
                    break;

            }


            if (pd != null)
            {
                pd.dismiss();
            }
        }



    }


    public void showActivity(String[] Num,String[] Wave,String[] DN) {

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/


        map1 = new HashMap<String, String>();

        map1.put("slno", "#NO");
        map1.put("one", " #WAVE");

        mylist_title.add(map1);



        try {
            adapter_title = new SimpleAdapter(this, mylist_title, R.layout.row,
                    new String[] { "slno", "one"}, new int[] {
                    R.id.Slno, R.id.one, R.id.two });
            list_head.setAdapter(adapter_title);
        } catch (Exception e) {

        }

        /********************************************************/


        /**********Display the contents************/

        for (int i = 0; i < Wave.length; i++) {
            map2 = new HashMap<String, String>();

            map2.put("slno", String.valueOf(i + 1));
            map2.put("one", Wave[i]);
            mylist.add(map2);
        }


        try {
            adapter = new SimpleAdapter(this, mylist, R.layout.row,
                    new String[] { "slno", "one" }, new int[] {
                    R.id.Slno, R.id.one });
            listv.setAdapter(adapter);

        } catch (Exception e) {

        }

        /********************************************************/

    }
}
