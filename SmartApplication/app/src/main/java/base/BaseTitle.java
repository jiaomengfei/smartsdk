package base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import test.jiao.smartapplication.R;


/**
 * Created by xiaoganggao on 15/8/6.
 * 公共title
 */
public class BaseTitle extends RelativeLayout {

    private Context context;
    private LinearLayout rightLayout,leftLayout;
    private TextView rightText,leftText,titleText;
    private ImageView rightImage,leftImage;

    public BaseTitle(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BaseTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_title,this);
        setBackgroundResource(R.drawable.bg_title_shape);
        titleText = (TextView) findViewById(R.id.title_content);
        rightLayout = (LinearLayout) findViewById(R.id.title_right_layout);
        rightText = (TextView) findViewById(R.id.title_right_text);
        rightImage = (ImageView) findViewById(R.id.title_right_image);
        leftLayout = (LinearLayout) findViewById(R.id.title_left_layout);
        leftText = (TextView) findViewById(R.id.title_left_text);
        leftImage = (ImageView) findViewById(R.id.title_left_image);
    }

    /**
     * 设置title内容
     */
    public void setTitleContent(int stringId){
        titleText.setText(stringId);
    }

    /**
     * 设置title内容
     */
    public void setTitleContent(String string){
        titleText.setText(string);
    }

    /**
     * 注册左侧返回
     */
    public void registerLeftBack(){
        leftImage.setImageResource(R.mipmap.title_back);
    }

    /**
     * 注册左侧返回
     */
    public void registerLeftBack(OnClickListener onClickListener){
        leftImage.setImageResource(R.mipmap.title_back);
        leftLayout.setOnClickListener(onClickListener);
    }

    /**
     * 注册右侧文字
     */
    public void registerRight(int text){
        rightText.setText(text);
    }

    /**
     * 注册右侧文字
     */
    public void registerRight(String text){
        rightText.setText(text);
    }

    public void setBg(int color){
        setBackgroundColor(color);
    }


    public LinearLayout getRightLayout() {
        return rightLayout;
    }

    public LinearLayout getLeftLayout() {
        return leftLayout;
    }

    public TextView getRightText() {
        return rightText;
    }

    public TextView getLeftText() {
        return leftText;
    }

    public TextView getTitleText() {
        return titleText;
    }

    public ImageView getRightImage() {
        return rightImage;
    }

    public ImageView getLeftImage() {
        return leftImage;
    }
}
