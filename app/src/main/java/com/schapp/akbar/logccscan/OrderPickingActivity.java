package com.schapp.akbar.logccscan;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;

public class OrderPickingActivity extends AppCompatActivity {



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
    String NoWavekey="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_picking);

        //get session

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            NoWavekey = extras.getString("wavekeysession");
            //The key argument here must match that used in the other activity
        }
       // orderpickdetail_list
        preferences = OrderPickingActivity.this.getSharedPreferences("settingmain", OrderPickingActivity.this.MODE_PRIVATE);
        editor=preferences.edit();
        listView = (ListView) findViewById(R.id.orderpickdetail_list);
        listView.setScrollContainer(true);
        BindSetting();
        list = new ArrayList<String>();
        _Url= _Url_fix + "/018/" + NoWavekey;
        new OrderPickingActivity.GetOrderPickDetail().execute("018");


    }

    void BindSetting()
    {
        AppSetting _appsetting=new AppSetting(OrderPickingActivity.this);
        _Url_fix=_appsetting._getAPISERVER();

    }

    String LoadLogin()
    {

        AppSetting _appsetting=new AppSetting(OrderPickingActivity.this);



        String _DataLogin= _appsetting._TempUserLogin();
        return _DataLogin;
    }

    public class GetOrderPickDetail extends AsyncTask<String,Void,JSONObject> {
        protected  String _TagProcess="";
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(OrderPickingActivity.this);
            pd.setMessage("Getting data WMS..");
            pd.show();
        }
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
                    _PickItem[i]=json.getJSONObject(i).getString("OrderKey");
                    list.add(json.getJSONObject(i).toString() );

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new ArrayAdapter<String>(OrderPickingActivity.this,  R.layout.activity_listview, _PickItem);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // ListView Clicked item index
                    int itemPosition = position;

                    // ListView Clicked item value
                    String itemValue = (String) listView.getItemAtPosition(position);


            //  open to  detil
                    isCancelled();
                    Intent OPK=new Intent(OrderPickingActivity.this, PickingDetailActivity.class);
                    OPK.putExtra("wavekeysession",NoWavekey);
                    OPK.putExtra("orderkeysession",itemValue);
                    startActivity(OPK);

                    // Show Alert
                   // Toast.makeText( OrderPickingActivity.this, "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG).show();

                }
            });

            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);

            switch (_TagProcess) {
                case "018":
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


