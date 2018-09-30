package com.example.administrator.kaopu.commond;

/**
 * Created by Administrator on 2017/3/28.
 */

public class HTTP_GD {
    public static boolean IsOrNot_Null (String jsonString)
    {
        jsonString = jsonString.replace("\r", "");
        jsonString = jsonString.replace("\n", "");
        jsonString = jsonString.replace("\b", "");
        jsonString = jsonString.replace("[", "");
        jsonString = jsonString.replace("]", "");
        jsonString = jsonString.replace("{", "");
        jsonString = jsonString.replace("}", "");
        jsonString = jsonString.replace(" ", "");
        jsonString = jsonString.replace("\uFEFF", "");
        jsonString = jsonString.replace("-", "");

        if(jsonString.equals(""))
        {
            return true;
        }
        return false;
    }
}
