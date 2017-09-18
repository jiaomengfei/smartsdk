package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bean.Home;
import bean.Params;


/**
 * 解析Json数据
 */

public class ParserJson {
    //参数
    public static ArrayList<Params> getParamsJson(String str) {
        ArrayList<Params> paramsArrayList = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(str);
            int code = jo.getInt("code");
            String message = jo.getString("message");
            JSONArray ja = jo.getJSONArray("items");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo2 = ja.getJSONObject(i);
                String parm_KEY = jo2.getString("parm_KEY");
                String parm_VALUE = jo2.getString("parm_VALUE");
                Params params = new Params(code, message, parm_KEY, parm_VALUE);
                paramsArrayList.add(params);

            }
            if (paramsArrayList != null) {
                return paramsArrayList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    //主页
    public static ArrayList<Home> getHomJson(String str) {
        ArrayList<Home> homeArrayList = new ArrayList<>();

        try {
            JSONObject jo = new JSONObject(str);
            int code = jo.getInt("code");
            String message = jo.getString("message");

            JSONArray ja = jo.getJSONArray("items");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo2 = ja.getJSONObject(i);
                String function_CODE = jo2.getString("function_CODE");
                String function_NAME = jo2.getString("function_NAME");
                String function_URL = jo2.getString("function_URL");
                String img_PATH = jo2.getString("img_PATH");
                Home home = new Home(code, message, function_CODE, function_NAME, function_URL, img_PATH);
                homeArrayList.add(home);
            }
            if (homeArrayList != null) {
                return homeArrayList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
