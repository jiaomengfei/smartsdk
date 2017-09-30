package fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import base.BaseFragment;
import test.jiao.smartapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends BaseFragment {

    private View baseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        baseView = inflater.inflate(R.layout.fragment_, null);
        initTitle();
        initView();
        initData();
        return baseView;
    }

    private void initData() {

    }

    private void initView() {

    }

    private void initTitle() {

        setTitleContent(R.string.title_service, baseView);
    }


}
