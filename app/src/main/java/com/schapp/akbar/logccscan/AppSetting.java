package com.schapp.akbar.logccscan;

/**
 * Created by Akbar on 11/14/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;


  class AppSetting {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
       Context _App;

     AppSetting(Context ctx)
    {
        _App=ctx;
        preferences = _App.getSharedPreferences("settingmain", _App.MODE_PRIVATE);
        editor=preferences.edit();
    }

       String _getIPSERVER()
    {
        return preferences.getString("SERVERNAME","");
    }

      String _getDBNAME()
    {
        return preferences.getString("DBNAME","");
    }

      String _getDBUSER()
    {
        return preferences.getString("DBUSER","");
    }

      String _getDBPASS() {return preferences.getString("DBPASS","");}

      String _getSTORERKEY()
    {
        return preferences.getString("STORERKEY","");
    }

      String _getAPISERVER() {return preferences.getString("APISERVER","");}

      String _TempUserLogin() { return preferences.getString("USERLOG","");}



}
