package com.schapp.akbar.logccscan;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Akbar on 11/23/2017.
 */

public class Cookies {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context _Ctx;

    public Cookies( Context _ParamContent)
    {
        _Ctx=_ParamContent;
    }


public String _GetUserLogin()
{
    try
    {
        AppSetting _appsetting=new AppSetting(_Ctx);
        return _appsetting._TempUserLogin();
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return "";
}



}
