package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 与ScollView兼容的GridView
 * Created by jmf on 2016/11/3.
 */

public class SVGridView extends GridView {


    public SVGridView(Context context) {
        super(context);
    }

    public SVGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SVGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
