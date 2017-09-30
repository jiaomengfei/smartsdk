package base;

import android.os.Environment;

import java.io.File;

/**
 * Created by 焦孟飞
 * on 2017/1/3.
 */

public class SdcardConfig {

    /**
     * sdcard
     */
    public static final String SDCARD_FOLDER = Environment.getExternalStorageDirectory().toString();

    /**
     * 根目录
     */
    public static final String ROOT_FOLDER = SDCARD_FOLDER + "/SMART/";

    /**
     * 日志目录
     */
    public static final String LOG_FOLDER = ROOT_FOLDER + "crash/";
    public static final String LOG_FOLDER_LOG = ROOT_FOLDER + "log/";
    public static final String LOG_FOLDER_DOWNLOAD = ROOT_FOLDER + "download/";
    public static final String LOG_FOLDER_IMG = ROOT_FOLDER + "imgs/";

    private static SdcardConfig sSdcardConfig;

    public static synchronized SdcardConfig getInstance() {
        if (sSdcardConfig == null) {
            sSdcardConfig = new SdcardConfig();
        }
        return sSdcardConfig;
    }

    /**
     * sd卡初始化
     */
    public void initSdcard() {
        if (!hasSDCard())
            return;
        File logFile = new File(LOG_FOLDER);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        File logFile2 = new File(LOG_FOLDER_LOG);
        if (!logFile2.exists()) {
            logFile2.mkdirs();
        }
        File logFile3 = new File(LOG_FOLDER_DOWNLOAD);
        if (!logFile3.exists()) {
            logFile3.mkdirs();
        }
        File logFile4 = new File(LOG_FOLDER_IMG);
        if (!logFile4.exists()) {
            logFile4.mkdirs();
        }
    }

    /**
     * 判断是否存在SDCard
     *
     * @return
     */
    public boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

}
