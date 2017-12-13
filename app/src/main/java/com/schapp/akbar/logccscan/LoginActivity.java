package com.schapp.akbar.logccscan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import jp.wasabeef.blurry.Blurry;
public class LoginActivity extends AppCompatActivity {

    EditText _InputUsername;
    EditText _InputPassword;
    static String _ValueUsername="";
    static String _ValuePassword="";
    static String _Url;
    private String _Url_fix = "";
    static String _ValueJson="";
    static String _Value_MessageID="";
    static String _Value_Type="LGN";
    ImageButton _InputSetting;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    void BindSetting()
    {
         AppSetting _appsetting=new AppSetting(LoginActivity.this);
        _Url_fix=_appsetting._getAPISERVER();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BindSetting();

        _InputUsername=(EditText)findViewById(R.id._TxtUsername);
        _InputPassword=(EditText)findViewById(R.id._TxtPassword);
        _InputSetting=(ImageButton) findViewById(R.id._input_setting);
        _InitiateListener();

        long startMs = System.currentTimeMillis();


        final RelativeLayout root = (RelativeLayout) findViewById(R.id.content);
        root.post(new Runnable() {
            @Override
            public void run() {



                Blurry.with(LoginActivity.this)
                        .radius(8)
                        .sampling(2)
                        .async()
                        .capture(findViewById(R.id._imgblur))
                        .into((ImageView) findViewById(R.id._imgblur));



            }
        });


    }

    void WriteLogin(String UserLogin)
    {
        try {
            preferences = LoginActivity.this.getSharedPreferences("settingmain", LoginActivity.this.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString("USERLOG", UserLogin);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    void _BindVariable(){
        _ValueUsername=_InputUsername.getText().toString();
        _ValuePassword=_InputPassword.getText().toString();
    }


    void _InitiateListener(){

        View.OnClickListener keyclick=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId())
                {
                    case R.id._input_setting:
                        Intent ISt= new Intent(LoginActivity.this, SettingActivity.class);
                        startActivity(ISt);

                        break;
                }
            }
        };



        View.OnKeyListener key=new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&(keyCode == KeyEvent.KEYCODE_ENTER)) {


                    _BindVariable();
                    switch (v.getId())
                    {

                        case R.id._TxtUsername:
                            break;
                        case R.id._TxtPassword:
                            if (_InputUsername.length() >0 && _InputPassword.length() >0)
                            {

                                _Url=_Url_fix + "/012/" + _ValueUsername +"/" + _ValuePassword +"/LEGRAND_TEST";
                                new LoginActivity.Login().execute("012");
                            }
                            else
                            {
                                //_InputPassword.setError("");
                            }
                            break;
                    }

                }
                return false;
            }
        };

        _InputUsername.setOnKeyListener(key);
        _InputPassword.setOnKeyListener(key);
        _InputSetting.setOnClickListener(keyclick);


    }

    public class Login extends AsyncTask<String,Void,JSONObject>{

        protected  String _TagProcess="";
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Getting data login..");
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
            //  try {
            switch (_TagProcess) {
                case "012":

                    if (s!=null) {
                        Intent IPwd = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(IPwd);
                        WriteLogin(_ValueUsername);
                        _InputPassword.setText("");
                        _InputUsername.setText("");

                        if (pd != null) {
                            pd.dismiss();
                        }
                    }
                    else
                    {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("WMS Information")
                                .setMessage("Login process failed!")
                                .setCancelable(true)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        if (pd != null) {
                                            pd.dismiss();
                                        }
                                    }
                                }).show();

                    }
                    break;

            }

            if (pd != null)
            {
                pd.dismiss();
            }
            //  }
            //  catch(org.json.JSONException e){
            //     e.printStackTrace();
            // }
        }
    }

}
