package fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import test.jiao.smartapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcountFragment extends BaseFragment {

   @BindView(R.id.version_update)
    LinearLayout mVersionUpdate;

    public AcountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_acount, container, false);
        ButterKnife.bind(this,view);
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
