package base;

import android.support.v4.app.Fragment;
import android.view.View;

import test.jiao.smartapplication.R;

/**
 * Created by jmf on 2017/3/15 0015.
 */

public class BaseFragment extends Fragment implements View.OnClickListener {
    public void fragmentIsShowing() {
    }
    /**
     * 设置title内容
     *
     * @param titleRes
     */
    protected void setTitleContent(int titleRes, View view) {
        BaseTitle baseTitle = (BaseTitle) view.findViewById(R.id.title);
        if (baseTitle != null) {
            baseTitle.setTitleContent(titleRes);
        } else {
        }
    }

    /**
     * 注册右侧点击事件
     */
    protected void registerTitleRight(int textRes, View.OnClickListener listener, View view) {
        BaseTitle baseTitle = (BaseTitle) view.findViewById(R.id.title);
        if (baseTitle != null) {
            baseTitle.registerRight(textRes);
            baseTitle.getRightLayout().setOnClickListener(listener);
        } else {
        }
    }


    protected void registerTitleRight(int textRes, int imgId, View.OnClickListener listener, View view) {
        BaseTitle baseTitle = (BaseTitle) view.findViewById(R.id.title);
        if (baseTitle != null) {
            if (imgId != 0) {
                baseTitle.getRightImage().setImageResource(imgId);
            }
            if (textRes != 0) {
                baseTitle.getRightText().setText(textRes);
            }
            baseTitle.getRightLayout().setOnClickListener(listener);
        } else {
        }
    }

    protected void setTitleBg(int color, View view) {
        BaseTitle baseTitle = (BaseTitle) view.findViewById(R.id.title);
        if (baseTitle != null) {
            if (color != 0) {
                baseTitle.setBg(color);
            }
        } else {
        }
    }

    /**
     * 注册左侧为返回
     */
    protected void registerLeftBack(View view) {
        BaseTitle baseTitle = (BaseTitle) view.findViewById(R.id.title);
        if (baseTitle != null) {
            baseTitle.registerLeftBack();
        } else {
        }
    }

    protected void registerTitleLeft(int textId, View.OnClickListener listener, View view) {
        registerTitleLeft(textId, 0, listener, view);
    }

    /**
     * 注册左侧点击事件
     */
    protected void registerTitleLeft(int textId, int imageId, View.OnClickListener listener, View view) {
        BaseTitle baseTitle = (BaseTitle) view.findViewById(R.id.title);
        if (baseTitle != null) {
            if (imageId != 0) {
                baseTitle.getLeftImage().setImageResource(imageId);
            }
            if (textId != 0) {
                baseTitle.getLeftText().setText(textId);
            }
            baseTitle.getLeftLayout().setOnClickListener(listener);
        } else {
        }
    }

    public static boolean isEmpty(String string){
        if (string != null && !string.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
