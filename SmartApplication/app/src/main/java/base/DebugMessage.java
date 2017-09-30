package base;

import android.util.Log;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Cans;
import utils.Constant;


/**
 * Created by Administrator on 2017/3/15 0015.
 */

public class DebugMessage {

    public static void showRequestParam(byte[] params, int method, String url) {
        String log = "";
        if (Constant.CONFIG.DEBUG) {
            String methodStr = method == 0 ? "GET" : "POST";
            if (params != null) {
                log = url + "?" + splitToDrawColor(new String(params));
            } else {
                log = url;
            }
            Log.e("网络", "请求方式:[" + methodStr + "] - 地址：" + log);
            if (!log.contains(Cans.EXORC_FACE)) {
                LogActivity.saveLog("<p style=\"word-break:break-all\"><a link='http://baidu.com'><font color='red'>" + "请求方式:[" + methodStr + "] - 地址：</font><br>" + log + "</a></p>");
            }
        }
    }
    /**
     * 将 String 转为 map
     *
     * @param param
     *            aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new HashMap<String, String>();
        if ("".equals(param) || null == param) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 将map 转为 string
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, String> map,
                                           boolean isSort) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<String>(map.keySet());
        if (isSort) {
            Collections.sort(keys);
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key).toString();
            sb.append("<font color='green'>"+key + "</font>=" + value);
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.lastIndexOf("&"));
        }
      /*
       * for (Map.Entry<String, Object> entry : map.entrySet()) {
       * sb.append(entry.getKey() + "=" + entry.getValue()); sb.append("&"); }
       * String s = sb.toString(); if (s.endsWith("&")) { //s =
       * StringUtils.substringBeforeLast(s, "&"); s = s.substring(0,
       * s.lastIndexOf("&")); }
       */
        return s;
    }
    private static String splitToDrawColor(String str){
        if (str.contains("&")&&str.contains("=")){
            Map<String, String> maps = getUrlParams(str);
            String result = getUrlParamsByMap(maps,true);
            return result;
        }
        return str;
    }


    public static void showResponse(String response) {
        if (Constant.CONFIG.DEBUG) {
            Log.e("网络", "正常返回值：" + response);
            LogActivity.saveLog("<p style=\"word-break:break-all\"><font color='green'>" + "正常返回值：</font>" + "<font color='black'>" + response + "</font></p>");
        }
    }

    public static void showErrorResponse(VolleyError error) {
        if (Constant.CONFIG.DEBUG) {
            Log.e("网络", "错误返回值：" + error.getMessage());
            LogActivity.saveLog("<p style=\"word-break:break-all\"><font color='green'>" + "错误返回值：</font>" + "<font color='black'>" + error.getMessage() + "</font></p>");
        }
    }
}
