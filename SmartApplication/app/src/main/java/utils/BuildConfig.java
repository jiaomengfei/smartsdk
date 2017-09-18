package utils;


import test.jiao.smartapplication.R;

/**
 * Created by zxk on 2017/6/7 0007.
 */

public class BuildConfig {
    public static boolean DEBUG = true;

    public enum URL_CRT {
        URL("https://ebjyd.poolfee.com/", R.raw.phbj_);
        private String mUrl;
        private int mCrtId;

        URL_CRT(String url, int crtId) {
            this.mUrl = url;
            this.mCrtId = crtId;
        }

        public String getmUrl() {
            return mUrl;
        }

        public int getmCrtId() {
            return mCrtId;
        }

    }
}
