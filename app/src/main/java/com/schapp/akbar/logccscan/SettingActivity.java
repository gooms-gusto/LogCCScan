package com.schapp.akbar.logccscan;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "library.db";


    String url = null;
    EditText _InputServerIP;
    EditText _InputDbName;
    EditText _InputDbUser;
    EditText _InputDbPassword;
    EditText _inputStorerKey;
    EditText _inputApiServer;

    Button _InputSubmit;
    Button _InputCancel;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    // Json Value
    public static  String _ValueServerIp = "";
    public static  String _ValueDbName="";
    public static  String _ValueDbUser="";
    public static  String _ValueDbPassword="";
    public static  String _ValueStorerKey="";
    public static  String _ValueApiServer="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        _InputServerIP= (EditText)findViewById(R.id._input_server);
        _InputDbName= (EditText)findViewById(R.id._input_dbname);
        _InputDbUser= (EditText)findViewById(R.id._input_userdb);
        _InputDbPassword= (EditText)findViewById(R.id._input_passworddb);
        _inputStorerKey= (EditText)findViewById(R.id._input_storerkey);
        _inputApiServer= (EditText)findViewById(R.id._input_ApiServer);

        _InputSubmit=(Button)findViewById(R.id._BtnSubmit);
        _InputCancel=(Button)findViewById(R.id._BtnCancel);


        preferences = SettingActivity.this.getSharedPreferences("settingmain", SettingActivity.this.MODE_PRIVATE);
        editor=preferences.edit();

        View.OnClickListener onClick=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id._BtnSubmit:
                        if (BindVariable()) {
                            if  (SaveSetting())
                            {
                                LoadData();
                                Toast.makeText(SettingActivity.this,"setting saved!",Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(SettingActivity.this,"setting failed!",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id._BtnCancel:
                        finish();
                        break;

                }
            }
        };

        _InputSubmit.setOnClickListener(onClick);
        _InputCancel.setOnClickListener(onClick);



        LoadData();

    }

    public void LoadData(){

        _InputServerIP.setText(preferences.getString("SERVERNAME",""));
        _InputDbName.setText(preferences.getString("DBNAME",""));
        _InputDbUser.setText(preferences.getString("DBUSER",""));
        _InputDbPassword.setText(preferences.getString("DBPASS",""));
        _inputStorerKey.setText(preferences.getString("STORERKEY",""));
        _inputApiServer.setText(preferences.getString("APISERVER" , ""));
    }


    public boolean SaveSetting() {
        try {
            editor.putString("SERVERNAME", _ValueServerIp);
            editor.putString("DBNAME", _ValueDbName);
            editor.putString("DBUSER", _ValueDbUser);
            editor.putString("DBPASS", _ValueDbPassword);
            editor.putString("APISERVER", _ValueApiServer);
            editor.putString("STORERKEY", _ValueStorerKey);
            editor.commit();

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }


    public boolean BindVariable(){


        int _count=0;
        if (_InputServerIP.getText().length()>0)
            _ValueServerIp=_InputServerIP.getText().toString();
        else
        {
            _count++;
            _InputServerIP.requestFocus();
            _InputServerIP.setError("Empty Field");
        }

        if (_InputDbName.getText().length()>0)
            _ValueDbName=_InputDbName.getText().toString();
        else
        {
            _count++;
            _InputDbName.requestFocus();
            _InputDbName.setError("Empty Field");
        }

        if (_InputDbUser.getText().length()>0)
            _ValueDbUser=_InputDbUser.getText().toString();
        else
        {
            _count++;
            _InputDbUser.requestFocus();
            _InputDbUser.setError("Empty Field");
        }

        if (_InputDbPassword.getText().length()>0)
            _ValueDbPassword=_InputDbPassword.getText().toString();
        else
        {
            _count++;
            _InputDbPassword.requestFocus();
            _InputDbPassword.setError("Empty Field");
        }

        if (_inputStorerKey.getText().length()>0)
            _ValueStorerKey=_inputStorerKey.getText().toString();
        else
        {
            _count++;
            _inputStorerKey.requestFocus();
            _inputStorerKey.setError("Empty Field");
        }

        if (_inputApiServer.getText().length()>0)
            _ValueApiServer=_inputApiServer.getText().toString();
        else
        {
            _count++;
            _inputApiServer.requestFocus();
            _inputApiServer.setError("Empty Field");
        }
        if (_count > 0){
            return false;

        }

        return true;

    }
}
