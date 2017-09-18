package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import test.jiao.smartapplication.R;


/**
 * 引导页ViewPager适配器jmf
 */
public class GuideAdapter extends PagerAdapter {
    private ArrayList<Integer> img;
    private Context context;
    private LayoutInflater inflater;
    private JumpCallBack mCallBack;

    public GuideAdapter(ArrayList<Integer> img, Context context) {
        this.img = img;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setmCallBack(JumpCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public int getCount() {
        return img.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout=inflater.inflate(R.layout.item_guide_viewpage,container,false);
        ImageView itemImage = (ImageView) imageLayout.findViewById(R.id.itemImage);
        itemImage.setBackgroundResource(img.get(position));
        Button enterBtn = (Button) imageLayout.findViewById(R.id.mBtnEnter);
        if (position == img.size() - 1) {
            enterBtn.setVisibility(View.VISIBLE);
            enterBtn.setOnClickListener(onClick);
        } else {
            enterBtn.setVisibility(View.GONE);
        }
        container.addView(imageLayout,0);
        return imageLayout;
    }

    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mBtnEnter:
                    if (mCallBack != null) {
                        mCallBack.toJump();
                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 跳转回调
     */
    public interface JumpCallBack {
         void toJump();
    }
}
