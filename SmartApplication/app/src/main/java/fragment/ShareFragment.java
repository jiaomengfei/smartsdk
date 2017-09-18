package fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import base.BaseFragment;
import test.jiao.smartapplication.R;
import view.WebViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends BaseFragment implements View.OnClickListener{


    private Button mWebUrl;

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
        return view;
    }

    //例如：加载assets文件夹下的test.html页面
        String url="file:///android_asset/test.html";

    @Override
    public void onClick(View view) {
        WebViewActivity.startSelf(getActivity(),url);
    }
}
