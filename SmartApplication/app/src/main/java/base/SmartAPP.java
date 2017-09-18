package base;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import bean.Params;
import utils.Cans;
import utils.Constant;
import utils.ParserJson;
import utils.SdcardConfig;
import ui.ToastUtil;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by jmf on 2017/8/11 0011.
 */

public class SmartAPP extends Application {

    private static SmartAPP mInstance = null;
    public static ArrayList<Params> mParamsList;
    private static RequestQueue queue;
    private static RequestQueue httpsQueue;

    public static SmartAPP getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("Application is not created.");
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if (Constant.CONFIG.getRequestType()) {
            /*HTTPS请求队列*/
            httpsQueue = Volley.newRequestQueue(getApplicationContext(), null, Constant.CONFIG.getCurrentUrl().getmCrtId());
        } else {
            /*HTTP请求队列*/
            queue = Volley.newRequestQueue(getApplicationContext());
        }
        getParams();
        // 初始化文件目录
        SdcardConfig.getInstance().initSdcard();
    }

    public static RequestQueue getQueue(String type) {

        if (Constant.CONFIG.getRequestType()) {
            return httpsQueue;
        } else {
            return queue;
        }
    }

    private void getParams() {
        getParams(null);
    }

    boolean result = false;
    public void getParams(final GetParamCallBack callBack) {
        mParamsList = new ArrayList<>();
        final String params = (String) FileUtil.getObject(getApplicationContext(), "getParams");
        if (params != null) {
            ArrayList<Params> paramsJson = ParserJson.getParamsJson(params);
            if (paramsJson == null) {
                if (callBack != null) {
                    callBack.getParamResult(result);
                }
            } else {

            }
        }

        if(FileUtil.isConnected(getApplicationContext())){
            StringRequest paramsRequest=new StringRequest(Request.Method.GET, Cans.GET_PARAMS, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    if (response != null) {
                        ArrayList<Params> paramsJson = ParserJson.getParamsJson(response);
                        if (paramsJson == null) {
                            if (callBack != null) {
                                callBack.getParamResult(result);
                            }
                            return;
                        }
                        Log.d(TAG, "onResponse: " + response);
                        FileUtil.save(getApplicationContext(), response, "getParams");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ToastUtil.showToast(getApplicationContext(),"error");
                }
            });
            getQueue("http").add(paramsRequest);
        }
    }

    public static interface GetParamCallBack {
        void getParamResult(boolean result);
    }
}
