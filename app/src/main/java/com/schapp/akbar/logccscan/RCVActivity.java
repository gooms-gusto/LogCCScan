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
import android.widget.Toast;
import android.view.View.OnKeyListener;
import android.text.TextWatcher;
import android.text.Editable;
import android.app.ProgressDialog;

public class RCVActivity extends AppCompatActivity {

    String url = null;
    EditText _InputASN;
    EditText _InputLine;
    EditText _InputSKUCode;
    EditText _InputSKUDesc;
    EditText _InputBatch;
    EditText _InputExpired;
    EditText _InputQuantity;
    EditText _InputLocation;
    EditText _InputPalID;
    EditText _InputQtySts;
    Spinner _InputDamage;

    Button _InputSubmit;
    Button _InputCancel;
    //JSON Node Names

    // Json Value
    static  String _ValueASN = "";
    static  String _ValueLine="";
    static  String _ValueSku="";
    static String _ValueExp="";
    static String _ValueBalance="";
    static  String _ValueBatch="";
    static  String _ValuePalId="";
    static  String _ValueQty="";
    static String _ValueLoc="";
    static String _ValueJson="";
    static String _ValueOpenQty="";
    static String _Value_MessageID="";
    static String _Value_Type="IBD";
    private String array_spinner[];
    static String _ValueDamage;
    static String  _TagContinue;
    static  String _ValueLogged="";
    public  Context context;
    private static  String _Url_fix = "";
    private static  String _Url;
    JSONArray user = null;
   // SharedPreferences preferences;
   // SharedPreferences.Editor editor;

    void BindSetting()
    {
        AppSetting _appsetting=new AppSetting(RCVActivity.this);
        _Url_fix=_appsetting._getAPISERVER();

    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcv);



        BindSetting();


        _InputASN= (EditText)findViewById(R.id._input_asn);
        _InputLine= (EditText)findViewById(R.id._input_line);
        _InputSKUCode= (EditText)findViewById(R.id._input_skucode);
        _InputSKUDesc= (EditText)findViewById(R.id._input_skudesc);
        _InputExpired= (EditText)findViewById(R.id._input_expdate);
        _InputBatch= (EditText)findViewById(R.id._input_batchnumber);
        _InputPalID=(EditText) findViewById(R.id._input_palid);
        _InputQuantity= (EditText)findViewById(R.id._input_quantity);
        _InputLocation= (EditText)findViewById(R.id._input_location);
        _InputDamage=(Spinner) findViewById(R.id._input_damage);
        _InputSubmit=(Button)findViewById(R.id._BtnSubmit);
        _InputCancel=(Button)findViewById(R.id._BtnCancel);
        _InputQtySts=(EditText)findViewById(R.id._input_qty_balance);



// inisialisasi event
        MessageIDCreate();


        _InputSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                BindVariable();
                _ValueExp="NULL";
                _Url= _Url_fix + "/000/" +_Value_MessageID + "/" + _ValueASN + "/" + _ValueLine + "/" + _ValueSku + "/" + _ValueExp + "/" + _ValueQty + "/" +_ValueBatch + "/" +  _ValuePalId + "/" + _ValueLoc + "/" +  _ValueQty + "/" + _ValueDamage + "/" + _Value_Type;
                new RCVActivity.GetDataReceipt().execute("000");
            }});


        _InputCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        OnKeyListener key=new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&(keyCode == KeyEvent.KEYCODE_ENTER)) {

                    BindVariable();


                    ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                            .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);


                    switch (v.getId()){
                        case R.id._input_asn:
                            if (_InputASN.getText().length()>0) {

                                _Url= _Url_fix + "/001/" + _ValueASN;
                                new RCVActivity.GetDataReceipt().execute("001");

                            }
                            else
                            {
                                _InputASN.setError("Cannot Empty!");
                                _InputASN.requestFocus();
                            }
                            break;
                        case R.id._input_line:
                            if (_InputLine.getText().length()>0) {

                                _Url=_Url_fix + "/002/" + _ValueASN +"/" +_ValueLine;
                                new RCVActivity.GetDataReceipt().execute("002");
                              //  _Url=_Url_fix + "/015/" + _ValueASN +"/" +_ValueLine;
                              //  new RCVActivity.GetDataReceipt().execute("015");

                            }
                            else
                            {
                                _InputLine.setError("Cannot Empty!");
                                _InputLine.requestFocus();
                            }
                            break;
                        case R.id._input_quantity:
                            if (_InputQuantity.getText().length()>0)
                            {
                                _Url=_Url_fix + "/007/" + _ValueASN +"/" +_ValueLine;
                                new RCVActivity.GetDataReceipt().execute("007");
                            } else
                            {
                                _InputQuantity.setError("Cannot Empty!");
                                _InputQuantity.requestFocus();
                            }
                            break;
                        case R.id._input_palid:
                            if (_InputPalID.getText().length()>0)
                            {
                                _Url=_Url_fix  + "/006/" + _ValueASN +"/" +_ValuePalId;
                                new RCVActivity.GetDataReceipt().execute("006");
                            } else
                            {
                                _InputPalID.setError("Cannot Empty!");
                                _InputPalID.requestFocus();
                            }
                            break;
                        case R.id._input_location:
                            if (_InputLocation.getText().length()>0)
                            {
                                _Url= _Url_fix + "/008/" + _ValueLoc;
                                new RCVActivity.GetDataReceipt().execute("008");
                            } else
                            {
                                _InputLocation.setError("Cannot Empty!");
                                _InputLocation.requestFocus();
                            }
                            break;


                    }


                   // ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                        //    .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                }
                return false;
            }
        };



        _InputASN.setOnKeyListener(key);
        _InputLine.setOnKeyListener(key);
        _InputExpired.addTextChangedListener(new RCVActivity.addListenerOnTextChange());
        _InputQuantity.setOnKeyListener(key);
        _InputPalID.setOnKeyListener(key);
        _InputLocation.setOnKeyListener(key);



        array_spinner=new String[2];
        array_spinner[0]="No";
        array_spinner[1]="Yes";
        ArrayAdapter adapter=new ArrayAdapter(RCVActivity.this,android.R.layout.simple_spinner_item,array_spinner);
        _InputDamage.setAdapter(adapter);
    }

    public class addListenerOnTextChange implements TextWatcher {
        private String current = "";
        private String ddmmyyyy = "DDMMYYYY";
        private Calendar cal = Calendar.getInstance();



        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                _InputExpired.setText(current);
                _InputExpired.setSelection(sel < current.length() ? sel : current.length());
            }
        }
    }


    public void BindVariable(){
        //Bind Variabel

        _ValueASN = _InputASN.getText().toString();
        _ValueLine=_InputLine.getText().toString();
        _ValuePalId=_InputPalID.getText().toString();
        _ValueQty=_InputQuantity.getText().toString();
        _ValueLoc=_InputLocation.getText().toString();
        _ValueExp=_InputExpired.getText().toString();
        _ValueBatch=_InputBatch.getText().toString();
        _ValueSku=_InputSKUCode.getText().toString();

        if (_InputDamage.getSelectedItem().toString().compareTo("No")==0)
            _ValueDamage="N";
        else
            _ValueDamage="Y";


            Cookies _Cookies=new Cookies(RCVActivity.this);
            _ValueLogged=_Cookies._GetUserLogin();

    }

    public void MessageIDCreate(){
        Date d = new Date();
        CharSequence s = DateFormat.format("yyyyMMddhhmmss", d.getTime());
        _Value_MessageID="RF" + s.toString();
        _Value_Type="IBD";
        _TagContinue="FFF";
    }

    public void BindClearAll(){
        _TagContinue="CCC";
        _ValueASN = "";
        _ValueLine="";
        _ValuePalId="";
        _ValueQty="";
        _ValueLoc="";
        _ValueExp="";
        _ValueBatch="";
        _ValueSku="";
        _ValueBalance="";
        _InputASN.setText("");
        _InputLine.setText("");
        _InputPalID.setText("");
        _InputBatch.setText("");
        _InputQuantity.setText("");
        _InputSKUCode.setText("");
        _InputSKUDesc.setText("");
        _InputSKUDesc.setText("");
        _InputExpired.setText("");
        _InputLocation.setText("");
        _InputQtySts.setText("");
        _InputASN.requestFocus();
        MessageIDCreate();
        _InputASN= (EditText)findViewById(R.id._input_asn);
        _InputLine= (EditText)findViewById(R.id._input_line);
        _InputSKUCode= (EditText)findViewById(R.id._input_skucode);
        _InputSKUDesc= (EditText)findViewById(R.id._input_skudesc);
        _InputExpired= (EditText)findViewById(R.id._input_expdate);
        _InputBatch= (EditText)findViewById(R.id._input_batchnumber);
        _InputPalID=(EditText) findViewById(R.id._input_palid);
        _InputQuantity= (EditText)findViewById(R.id._input_quantity);
        _InputLocation= (EditText)findViewById(R.id._input_location);
        _InputSubmit=(Button)findViewById(R.id._BtnSubmit);
        _InputCancel=(Button)findViewById(R.id._BtnCancel);
        _InputDamage.setSelection(0);
        _ValueDamage="N";
    }

    public class GetDataReceipt extends AsyncTask<String,Void,JSONObject> {
        protected  String _TagProcess="";
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(RCVActivity.this);
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
            try {



                switch (_TagProcess){
                    case "001": // validation _ASN
                        _ValueJson=s.getString("result");
                        if (!_ValueJson.isEmpty()) {
                            if (_ValueJson.compareTo("0") == 0) {
                                _InputASN.setError("ASN# Not Valid!");
                                _InputASN.requestFocus();
                            } else {
                                _InputLine.requestFocus();
                            }
                        }
                        break;
                    case "002": // validation _batch#
                        if (s==null) {
                            _InputLine.setError("Line Number # Not Valid!");
                            _InputSKUCode.setText("");
                            _InputSKUDesc.setText("");
                            _InputExpired.setText("");
                            _InputLocation.setText("");
                            _InputLine.requestFocus();
                        } else {
                            _InputSKUCode.setText(s.getString("sku"));
                            _InputSKUDesc.setText(s.getString("descr"));
                            _InputBatch.setText(s.getString("batch"));
                            _InputExpired.setText(s.getString("rdate"));
                            _InputLocation.setText("STAGE");
                            _InputPalID.requestFocus();
                        }
                        break;
                    case "006":
                        _ValueJson=s.getString("result");
                        if (_ValueJson.compareTo("0") == 0) {
                            _InputQuantity.requestFocus();
                        }
                        else
                        {
                            _InputPalID.setError("Palet ID # Not Valid!");
                            _InputPalID.setText("");
                            _InputPalID.requestFocus();
                        }
                        break;
                    case "007":
                        _ValueJson=s.getString("result");
                        Integer _Cqty=0;
                        Integer _CqtyInput=0;
                        if (_ValueJson.length()>0)
                            _Cqty=Integer.parseInt(_ValueJson);

                        if (_ValueQty.length()>0 )
                            _CqtyInput=Integer.parseInt(_ValueQty);


                        if (_CqtyInput <= _Cqty) {
                            _InputLocation.requestFocus();
                        }
                        else
                        {
                            _InputQuantity.setError("too much quantity!");
                            _InputQuantity.setText("");
                            _InputQuantity.requestFocus();
                        }
                        break;
                    case "008":
                        _ValueJson=s.getString("result");
                        if (_ValueJson.compareTo("0") == 0) {
                            _InputLocation.setError("Location not available!");
                            _InputLocation.setText("");
                            _InputLocation.requestFocus();
                        }
                        else
                        {
                            _InputSubmit.requestFocus();

                        }
                        break;
                    case "015": // validation line
                        if (s==null) {

                            _InputQtySts.setText("");

                        } else {
                            _ValueBalance=s.getString("BALANCE");
                            _ValueOpenQty=s.getString("OPENQTY");


                            _InputQtySts.setText(s.getString("BALANCE"));

                            // pengecekan line jika udah selesai receive
                            if (_TagContinue.compareTo("LLL")==0)
                            {
                                if (Double.parseDouble(_ValueBalance)> 0)
                                {
                                    MessageIDCreate();
                                    _ValuePalId="";
                                    _ValueQty="";

                                    _InputPalID.setText("");
                                    _InputQuantity.setText("");
                                    _InputPalID.requestFocus();
                                }
                                else
                                {
                                    if (Double.parseDouble(_ValueOpenQty)>0)
                                    {
                                        _ValuePalId="";
                                        _ValueQty="";
                                        _ValueOpenQty="";
                                        _ValueBalance="";
                                        _InputLine.setText("");
                                        _InputPalID.setText("");
                                        _InputQuantity.setText("");
                                        _InputQtySts.setText("");

                                        _InputSKUCode.setText("");
                                        _InputSKUDesc.setText("");
                                        _InputBatch.setText("");



                                        _InputLine.requestFocus();
                                    }
                                    else
                                    BindClearAll();

                                }

                            }

                        }


                        break;
                    case "000":

/*
                        if (pd != null)
                        {
                            pd.dismiss();
                        }

*/
                        Toast.makeText(RCVActivity.this,"Process RCV Success!",Toast.LENGTH_LONG).show();
                        break;
                    case "014":

                        break;
                }


                // Finalize
                if (pd != null)
                {
                    pd.dismiss();

                    if ( _TagProcess.compareTo("002")==0)
                    {
                          _Url=_Url_fix + "/015/" + _ValueASN +"/" +_ValueLine;
                          new RCVActivity.GetDataReceipt().execute("015");
                    }

                    if ( _TagProcess.compareTo("008")==0)
                    {

                         BindVariable();
                        _ValueExp="NULL";
                        _Url= _Url_fix + "/000/" +_Value_MessageID + "/" + _ValueASN + "/" + _ValueLine + "/" + _ValueSku + "/" + _ValueExp + "/" + _ValueQty + "/" +_ValueBatch + "/" +  _ValuePalId + "/" + _ValueLoc + "/" +  _ValueQty + "/" + _ValueDamage + "/" + _Value_Type + "/" + _ValueLogged;
                        new RCVActivity.GetDataReceipt().execute("000");
                    }



                    // jika receive selesai


                    if (_TagProcess.compareTo("000")==0)
                    {
                        // cek qty sisa
                        _Url=_Url_fix + "/015/" + _ValueASN +"/" +_ValueLine;
                        _TagContinue="LLL";
                        new RCVActivity.GetDataReceipt().execute("015");




                    }

                    /*

                    if (_TagProcess.compareTo("015")==0)
                    {
                        // cek qty sisa
                        if (_ValueBalance.compareTo("0.0000")==0)
                        {
                            BindClearAll();
                        }
                    }
                    */
                }


            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
