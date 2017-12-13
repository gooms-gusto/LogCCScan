package com.schapp.akbar.logccscan;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.view.View;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnKeyListener;
import android.text.TextWatcher;
import android.text.Editable;
import android.app.ProgressDialog;

public class PickingDetailActivity extends AppCompatActivity {

    private static  String _Url_fix = "";
    private static  String _Url;
    private static String NoWavekey;
    private static String NoOrderKey;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText _input_scanlo;
    EditText _input_scanpid;

    TextView _input_inputqtypick;
    TextView _input_info_scanloc;
    TextView _input_info_qtypick;
    TextView _input_info_scanID;
    TextView _input_info_sku;
    TextView _input_info_batchno;


    private static String _ValueScanLoc;
    private static String _ValueScanPid;
    private static String _ValueSkuPicking;
    private static String _ValueInputQty;

    private static String _ValueInfoLoc;
    private static String _ValueInfoPID;
    private static String _ValueInfoQty;
    private static String _ValueInfoSKU;
    private static String _ValueInfoBatchNo;
    private static String _Value_MessageID="";
    private static String _Value_Type="PIC";
    Button _InputSubmiPick;
    Button _InputCancelPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_detail);

        // bind setting  login
        BindSetting();

        // inisialisasi Event Listener
        _EventListener();
        // get parameter

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            NoWavekey = extras.getString("wavekeysession");
            NoOrderKey=extras.getString("orderkeysession");
            preferences = PickingDetailActivity.this.getSharedPreferences("settingmain", PickingDetailActivity.this.MODE_PRIVATE);
            editor=preferences.edit();
            _Url= _Url_fix + "/019/" + NoWavekey +"/" + NoOrderKey;
            new PickingDetailActivity.GetPickDetail().execute("019");



        }


    }

    void _EventListener() {
        View.OnKeyListener KeyEnter = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    switch (v.getId()) {

                        case R.id._input_scanloc:

                            Toast.makeText(PickingDetailActivity.this, "Process Picking Success!", Toast.LENGTH_LONG).show();

                            // checking location scannnnnnnn
                            if (_ValueInfoLoc.compareTo(_input_scanlo.getText().toString()) == 0)
                                _input_scanpid.requestFocus();
                            else {
                                _input_scanlo.setError("Location input not same!");
                                _input_scanlo.setText("");
                                _input_scanlo.requestFocus();
                            }
                            break;
                        case R.id._input_scanpid:
                            // checking location scanpid
                            if (_ValueScanPid.compareTo(_input_scanpid.getText().toString()) == 0)
                                _input_scanpid.requestFocus();
                            else {
                                _input_scanpid.setError("PalletID input not same!");
                                _input_scanpid.setText("");
                                _input_scanpid.requestFocus();
                            }
                            break;
                        case R.id._input_inputqtypick:
                            // checking qty
                            if (_ValueInputQty.compareTo(_input_inputqtypick.getText().toString()) == 0) {
                                _Url = _Url_fix + "/020/" + NoWavekey + "/" + NoOrderKey;
                                new PickingDetailActivity.GetPickDetail().execute("020");
                            } else {
                                _input_inputqtypick.setError("Quantity input not same!");
                                _input_inputqtypick.setText("");
                                _input_inputqtypick.requestFocus();
                            }

                            break;
                    }

                }

                return false;
            }
        };

        _input_scanlo.setOnKeyListener(KeyEnter);
        _input_scanpid.setOnKeyListener(KeyEnter);
        _input_inputqtypick.setOnKeyListener(KeyEnter);
    }

    void BindSetting()
    {
        AppSetting _appsetting=new AppSetting(PickingDetailActivity.this);
        _Url_fix=_appsetting._getAPISERVER();

        //Input Field
        _input_scanlo= (EditText)findViewById(R.id._input_scanloc);
        _input_scanpid= (EditText)findViewById(R.id._input_scanpid);
        _input_inputqtypick= (EditText)findViewById(R.id._input_inputqtypick);

        // info Field
        _input_info_qtypick= (TextView)findViewById(R.id._input_info_qtytopic);
        _input_info_scanloc=(TextView)findViewById(R.id._input_info_scanloc);
        _input_info_scanID=(TextView)findViewById(R.id._input_info_scanID);
        _input_info_sku= (TextView)findViewById(R.id._input_info_sku);
        _input_info_batchno= (TextView)findViewById(R.id._input_info_batchno);




        //button
        _InputSubmiPick=(Button)findViewById(R.id._BtnSubmitPick);
        _InputCancelPick=(Button)findViewById(R.id._BtnCancelPick);
    }

    void BindData()
    {
        _ValueInfoLoc=_input_scanlo.getText().toString();


    }

    public void MessageIDCreate(){
        Date d = new Date();
        CharSequence s = DateFormat.format("yyyyMMddhhmmss", d.getTime());
        _Value_MessageID="PIC" + s.toString();
        _Value_Type="PIC";
    }



    String LoadLogin()
    {
        AppSetting _appsetting=new AppSetting(PickingDetailActivity.this);
        String _DataLogin= _appsetting._TempUserLogin();
        return _DataLogin;
    }

    public class GetPickDetail extends AsyncTask<String,Void,JSONObject> {


        protected  String _TagProcess="";
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(PickingDetailActivity.this);
            pd.setMessage("Getting data WMS..");
            pd.show();
        }
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONParserArray jParser = new JSONParserArray();
            JSONArray json = jParser.getJSONFromUrl(_Url);
            JSONObject jsonObject = null;

            try
            {
                _TagProcess=params[0];
                jsonObject = json.getJSONObject(0);

            }
            catch (JSONException e)
            { e.printStackTrace();
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);

            try {
                switch (_TagProcess) {
                    case "019":


                        if (s == null) {

                        } else {

                            _ValueInfoLoc = s.getString("Loc");
                            _ValueInfoQty = s.getString("Qty");
                            _ValueInfoPID = s.getString("ID");
                            _ValueInfoBatchNo = s.getString("Lottable01");
                            _ValueInfoSKU= s.getString("SKU");
                            // set Info field
                            _input_info_scanloc.setText(_ValueInfoLoc);
                            _input_info_qtypick.setText(_ValueInfoQty);
                            _input_info_scanID.setText(_ValueInfoPID);
                            _input_info_batchno.setText(_ValueInfoBatchNo);
                            _input_info_sku.setText(_ValueInfoSKU);
                        }
                        break;
                    case "020":


                        Toast.makeText(PickingDetailActivity.this,"Process Picking Success!",Toast.LENGTH_LONG).show();
                        break;



                }


                if (pd != null) {
                    pd.dismiss();
                }
            }catch (JSONException e){e.printStackTrace();}

        }


    }


}

