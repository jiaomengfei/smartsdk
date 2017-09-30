package fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import base.BaseFragment;
import base.NetWorkChangeBroadcastReceiver;
import base.SmartAPP;
import bean.Home;
import butterknife.BindView;
import butterknife.ButterKnife;
import test.jiao.smartapplication.R;
import utils.Cans;
import utils.FileUtil;
import utils.ParserJson;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @BindView(R.id.home_gridview)
    GridView mGridView;
    private BroadcastReceiver mNetReceiver;
    //gridView数据
    private ArrayList<Home> mHomeList = new ArrayList<>();
    private String TAG = "HomeFragment";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        sp = getActivity().getSharedPreferences("bjpay", MODE_PRIVATE);
        editor = sp.edit();
        mGridView.setOnItemClickListener(this);
        registNetworEnableReceiver();
        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentIsShowing();
    }

    private void registNetworEnableReceiver() {
        mNetReceiver= new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
               //判断当前网络状态
                if (intent.getIntExtra(NetWorkChangeBroadcastReceiver.NET_TYPE, 0) == 0) {
                    //无网络
                    //这里填你想要执行的东西
                } else {
                    //有网络
                    fragmentIsShowing();
                }
            }
        };

        IntentFilter filter = new IntentFilter(NetWorkChangeBroadcastReceiver.NET_CHANGE);
        getActivity().registerReceiver(mNetReceiver, filter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void fragmentIsShowing() {
        super.fragmentIsShowing();
        //初始化home数据
        initHomeData();
    }

    /*加载12个功能模块数据*/
    private void initHomeData() {
        // 从本地读取 首页 menu列表
        mHomeList=(ArrayList<Home>) FileUtil.getObject(getActivity(), Cans.MENU_LIST_HOME);
        if (mHomeList == null || !(mHomeList.size() > 0)) {
            //本地没有，从服务器获得数据保存到本地
            mHomeList = new ArrayList<>();
            loadHomeDataFromServer();
        }else{
            //本地有数据
            setHomeAdapter(mHomeList);
            loadHomeDataFromServer();
        }

    }

    private void setHomeAdapter(ArrayList<Home> mHomeList) {

    }

    private void loadHomeDataFromServer() {
        StringRequest homeRequest = new StringRequest( Request.Method.GET, "https://ebjyd.poolfee.com/config/" + Cans.GET_HOME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.d(TAG, "onResponse: " + response);
                    //服务器获得的列表
                    ArrayList<Home> homJson = ParserJson.getHomJson(response);
                    /*取本地和服务器相同的保存*/
                    ArrayList<Home> list = new ArrayList<>();
                    getNewHomeData(homJson);
                    for (int i1 = 0; i1 < mHomeList.size(); i1++) {
                        //遍历本地的列表
                        String function_code1 = mHomeList.get(i1).getFunction_CODE();
                        for (int i = 0; i < homJson.size(); i++) {
                            //遍历服务器的列表
                            String function_code = homJson.get(i).getFunction_CODE();
                            if ("HOME_GD".equals(function_code)) {
                                continue;
                            }
                            //本地的code和服务器的code是一样的，表示本地有的
                            if (function_code.equals(function_code1)) {
                                //在结果中保存当前有的信息，如果当地列表中有数据，服务器中没有的话，那么相当于把这一项删掉了
                                //替换最新图片地址
                                Home newItem = mHomeList.get(i1);
                                String newImgPath = homJson.get(i).getImg_PATH();
                                String oldImgPath = newItem.getImg_PATH();
                                if (newImgPath != null && newImgPath.length() > 0 && !newImgPath.equals(oldImgPath)) {
                                    newItem.setImg_PATH(newImgPath);
                                }
                                String newName = homJson.get(i).getFunction_NAME();
                                String oldNmae = newItem.getImg_PATH();
                                if (newName != null && newName.length() > 0 && !newName.equals(oldNmae)) {
                                    newItem.setFunction_NAME(newName);
                                }
                                String newUrl = homJson.get(i).getFunction_URL();
                                String oldUrl = newItem.getFunction_URL();
                                if (newUrl != null && newUrl.length() > 0 && !newUrl.equals(oldUrl)) {
                                    newItem.setFunction_URL(newUrl);
                                }

                                list.add(newItem);
                            }
                        }
                    }
                    /*保存到本地*/
                    FileUtil.save(getActivity(), list, Cans.MENU_LIST_HOME);

                    Home home11 = new Home(200, "", "HOME_GD", "更多", "", sp.getString("pic_GD", ""));

                    if (list.size() > 10) {
                        list.add(11, home11);
                    }
                    list.add(home11);

                    /*清空集合数据 添加新的数据源*/
                    mHomeList.clear();
                    mHomeList.addAll(list);
                    FileUtil.save(getActivity(), mHomeList, Cans.MENU_LIST_HOME);
                    setHomeAdapter(mHomeList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: home");
                if (error.toString().equals("com.android.volley.TimeoutError")) {
                    Toast.makeText(getActivity(), "网络超时，请重试", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        homeRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SmartAPP.getQueue("http").add(homeRequest);
    }

    /**
     * 获得服务器新上线的产品
     */
    private ArrayList<Home> getNewHomeData(ArrayList<Home> requestHome) {
        mHomeList = (ArrayList<Home>) FileUtil.getObject(getActivity(), Cans.MENU_LIST_HOME);
        if (mHomeList == null) {
            mHomeList = new ArrayList<>();
            mHomeList.addAll(requestHome);
        }
        ArrayList<Home> resultHomes = new ArrayList<>();
        //本地保存的服务器的产品
        ArrayList<Home> allHome = (ArrayList<Home>) FileUtil.getObject(getActivity(), Cans.MENU_LIST_MORE);
        for (Home home : requestHome) {
            //遍历网络请求回来的，从里面查找这个数据是否在总的里面存在，如果不存在，就将它放到结果列表中的第一个
            if (contains(home, allHome)) {
                //如果home在allHome中存在
            } else {
                //allHome中没有当前的Home
                resultHomes.add(home);
                continue;
            }
        }
        for (Home home : resultHomes) {
            if (contains(home, mHomeList)) {

            } else {
                mHomeList.add(0, home);
            }
        }
        if (resultHomes.size() > 0) {
            FileUtil.save(getActivity(), mHomeList, Cans.MENU_LIST_HOME);
        }
        FileUtil.save(getActivity(), requestHome, Cans.MENU_LIST_MORE);
        return resultHomes;
    }

    /**
     * 在list中查找要找的home存在不存在
     */
    private boolean contains(Home searchHome, ArrayList<Home> allHome) {
        if (allHome == null) {
            return false;
        }
        for (Home home : allHome) {
            if (searchHome.getFunction_CODE().equals(home.getFunction_CODE())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNetReceiver != null)
            getActivity().unregisterReceiver(mNetReceiver);
    }
}
