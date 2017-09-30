package ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import regex.StringUtils;


/**
 * Created by jmfz on 2017/4/18 0018.
 */

public class TextWatcherCreater {
    /**
     * @Override public void notNull() {
     * login_btn_login.setClickable(true);
     * login_btn_login.setBackgroundResource(R.drawable.login_btn);
     * }
     * @Override public void isNull() {
     * login_btn_login.setClickable(false);
     * login_btn_login.setBackgroundColor(getResources().getColor(R.color.btn_cannot_click));
     * }
     */
    public interface OnAllTextNotNullListener {
        /**
         * 所有的EditText都填写了
         */
        void notNull();

        /**
         * 某个或某几个EditText没有填写
         */
        void isNull();
    }

    public static void setTextWatcher(final OnAllTextNotNullListener callBack, final EditText... editTexts) {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean someOneNull = false;
                for (EditText editText : editTexts) {
                    if (!StringUtils.isNotNull(editText.getText().toString())) {
                        //当前这个是空的
                        someOneNull = true;
                    }
                }
                if (!someOneNull) {
                    callBack.notNull();
                } else {
                    callBack.isNull();
                }
            }
        };
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(watcher);
        }
    }
}
