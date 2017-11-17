package fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import base.BaseFragment;
import base.GlideImageLoader;
import test.jiao.smartapplication.R;
import view.WebViewActivity;


public class ShareFragment extends BaseFragment implements View.OnClickListener{


    private Button mWebUrl;
    private Banner banner;

    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_share, container, false);
        mWebUrl = (Button) view.findViewById(R.id.web_url);
        mWebUrl.setOnClickListener(this);
        banner = (Banner) view.findViewById(R.id.banner);
        return view;
    }

    private boolean started = false;

    /*初始化轮播图适配器*/
    private void initAdsAdapter(ArrayList<String> imgs) {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(imgs);
        if (started) {
            banner.update(list);
            return;
        }
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //WebViewActivity.startSelf(getActivity(), adsList.get(position).title, adsList.get(position).img_URL);
            }
        });
        started = true;
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    //例如：加载assets文件夹下的test.html页面   js交互
        String url="file:///android_asset/test.html";

    @Override
    public void onClick(View view) {
        WebViewActivity.startSelf(getActivity(),url);
    }
}
