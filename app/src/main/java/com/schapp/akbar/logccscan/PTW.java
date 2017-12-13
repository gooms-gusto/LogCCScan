package com.schapp.akbar.logccscan;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class PTW extends AppCompatActivity {


    EditText _InputLocationPutway;
    EditText _InputPalIDPutway;
    Button _BtnSubmitPtw;
    Button _BtnClearPtw;
    Button _BtnCancelPtw;
    static    String _ValuePalIdPutway="";
    static    String _ValueLocPutway="";
    static    String _Url;
    static   String _Url_fix = "";
    static String _ValueJson="";
    static String _Value_MessageID="";
    static String _Value_Type="PTW";
    static String _ValueLogged="";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptw);




        _InputPalIDPutway= (EditText)findViewById(R.id._input_palid_putway);
        _InputLocationPutway= (EditText)findViewById(R.id._input_location_putway);


        _BtnSubmitPtw= (Button) findViewById(R.id._BtnSubmitPutway);
        _BtnCancelPtw=(Button) findViewById(R.id._BtnCancelPutway);





        _BtnCancelPtw.setOnClickListener(keyClick);
        _BtnSubmitPtw.setOnClickListener(keyClick);


        _InputPalIDPutway.setOnKeyListener(KeyEnter);
        _InputLocationPutway.setOnKeyListener(KeyEnter);

        BindSetting();


    }

    void BindSetting()
    {
        AppSetting _appsetting=new AppSetting(PTW.this);
        _Url_fix=_appsetting._getAPISERVER();

    }


    String LoadLogin()
    {

        AppSetting _appsetting=new AppSetting(PTW.this);
        String _DataLogin= _appsetting._TempUserLogin();
        return _DataLogin;
    }

    View.OnKeyListener KeyEnter=new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&(keyCode == KeyEvent.KEYCODE_ENTER)) {

                BindVariable();

                switch (v.getId())
                {
                    case R.id._input_palid_putway:
                        if (_InputPalIDPutway.getText().length()>0) {

                            _Url= _Url_fix   + "/009/" + _ValuePalIdPutway;
                            new PTW.Putway().execute("009");

                        }
                        else
                        {
                            _InputPalIDPutway.setError("Cannot Empty!");
                            _InputPalIDPutway.requestFocus();
                        }
                        break;
                    case R.id._input_location_putway:
                        if (_InputPalIDPutway.getText().length()>0) {

                            _Url= _Url_fix   + "/010/" + _ValueLocPutway;
                            new PTW.Putway().execute("010");

                        }
                        else
                        {
                            _InputLocationPutway.setError("Cannot Empty!");
                            _InputLocationPutway.requestFocus();
                        }
                        break;
                }

            }


            return false;
        }
    };

    View.OnClickListener keyClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id._BtnSubmitPutway:
                    MessageIDCreate();
                    BindVariable();
                    _Url= _Url_fix + "/011/" +_Value_MessageID + "/" +  _ValuePalIdPutway + "/" + _ValueLocPutway + "/" + LoadLogin() +"/" +  _Value_Type;
                  //  _Url=  "http://10.213.135.3/root001/Service1.asmx/process001?MESSAGE_ID=" +_Value_MessageID + "&RLOT07=" +  _ValuePalIdPutway + " &RLOT08=" + _ValueLocPutway + "&RLOT15="+ _ValueLogged +"&TYPE=" +  _Value_Type;

                    new PTW.Putway().execute("011");
                    break;
                case R.id._BtnClearPutway:
                    BindClearAll();
                    break;
                case R.id._BtnCancelPutway:
                    finish();
                    break;
            }
        }
    };


    public class Putway extends AsyncTask<String,Void,JSONObject> {
        protected  String _TagProcess="";
        ProgressDialog pd;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(PTW.this);
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


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);

            try
            {
                switch (_TagProcess) {
                    case "009":
                        _ValueJson = s.getString("result");
                        if (_ValueJson.compareTo("0") == 0) {
                            _InputPalIDPutway.setError("Pallet ID not valid!");
                            _InputPalIDPutway.setText("");
                            _InputPalIDPutway.requestFocus();
                        } else {
                            _InputLocationPutway.requestFocus();
                        }
                        break;
                    case "010":
                        _ValueJson = s.getString("result");
                        if (_ValueJson.compareTo("0") == 0) {
                            _InputLocationPutway.setError("Location not available!");
                            _InputLocationPutway.setText("");
                            _InputLocationPutway.requestFocus();
                        } else {
                            _BtnSubmitPtw.requestFocus();
                        }
                        break;
                    case "011":
                        if (pd != null)
                        {
                            pd.dismiss();
                        }

                        BindClearAll();
                        Toast.makeText(PTW.this,"Process PTW Success!",Toast.LENGTH_LONG).show();
                        break;
                }


                if (pd != null)
                {
                    pd.dismiss();
                }
            }
            catch(org.json.JSONException e){
                e.printStackTrace();
            }


        }



    }

    public void BindVariable(){
        //Bind Variabel

        _ValuePalIdPutway = _InputPalIDPutway.getText().toString();
        _ValueLocPutway=_InputLocationPutway.getText().toString();


        Cookies _Cookies=new Cookies(PTW.this);
        _ValueLogged=_Cookies._GetUserLogin();
    }


    public void MessageIDCreate(){
        Date d = new Date();
        CharSequence s = DateFormat.format("yyyyMMddhhmmss", d.getTime());
        _Value_MessageID="PTW" + s.toString();
        _Value_Type="PTW";
    }



    void BindClearAll(){
        _InputPalIDPutway.setText("");
        _InputLocationPutway.setText("");
        _InputPalIDPutway.requestFocus();
    }

}
