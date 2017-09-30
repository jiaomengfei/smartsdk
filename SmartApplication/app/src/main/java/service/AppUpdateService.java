package service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.StatFs;
import android.widget.RemoteViews;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import test.jiao.smartapplication.R;
import utils.UIUtils;

/***
 * 更新版本
 * 
 * 
 */
public class AppUpdateService extends Service {
    private static final int TIMEOUT = 60 * 1000;// 超时
    private static final int OK = 1;
    private static final int ERROR = 0;
    private static final int NO_SPACE = 2;
    private static final int CANCEL_NOTIFICATION = 3;

    private String appName;
    private String downUrl;

    private NotificationManager notificationManager;
    private Notification notification;

    private Intent updateIntent;
    private PendingIntent pendingIntent;

    private int notificationId = 0x0;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            appName = intent.getStringExtra("app_name");

            downUrl = intent.getStringExtra("down_url");

            // 创建文件
            int ret = createFile(appName);

            if (ret == NO_SPACE) {
                UIUtils.toastShort("存储卡空间不足");
            } else if (ret == ERROR) {
                UIUtils.toastShort("下载失败");
            } else {
                createNotification();
                createThread();
            }
        }

        return super.onStartCommand(intent, flags, startId);

    }

    /***
     * 更新UI
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case OK:
                // 下载完成，自动安装
                Uri uri = Uri.fromFile(updateFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                getApplicationContext().startActivity(intent);

                notification.flags = Notification.FLAG_AUTO_CANCEL;
                notification.tickerText = "Smart下载成功，正在安装...";
              //  notification.setLatestEventInfo(AppUpdateService.this, appName, "下载成功，正在安装...", pendingIntent);

                notificationManager.notify(notificationId, notification);

                stopService(updateIntent);

                handler.sendEmptyMessageDelayed(CANCEL_NOTIFICATION, 3 * 1000);
                break;
            case ERROR:
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                notification.tickerText = "下载失败";
                //notification.setLatestEventInfo(AppUpdateService.this, appName, "下载失败", pendingIntent);
                notificationManager.notify(notificationId, notification);
                stopService(updateIntent);
                break;

            case CANCEL_NOTIFICATION:
                notificationManager.cancel(notificationId);
                break;

            default:
                stopService(updateIntent);
                break;
            }

        }
    };

    /***
     * 开线程下载
     */
    public void createThread() {

        final Message message = new Message();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    long downloadSize = downloadUpdateFile(downUrl, updateFile.toString());
                    if (downloadSize > 0) {
                        // 下载成功
                        message.what = OK;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    message.what = ERROR;
                    handler.sendMessage(message);
                }

            }
        }).start();
    }

    /***
     * 创建通知栏
     */
    RemoteViews contentView;

    public void createNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = "Smart开始下载";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_NO_CLEAR;

        /***
         * 在这里我们用自定的view来显示Notification
         */
        contentView = new RemoteViews(getPackageName(), R.layout.item_notification_upgrade);
        contentView.setTextViewText(R.id.notificationTitle, "Smart正在下载");
        contentView.setTextViewText(R.id.notificationPercent, "0%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);

        notification.contentView = contentView;

        updateIntent = new Intent();
        updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

        notification.contentIntent = pendingIntent;

        notificationManager.notify(notificationId, notification);

    }

    /***
     * 下载文件
     * 
     * @return
     * @throws MalformedURLException
     */
    public long downloadUpdateFile(String down_url, String file) throws Exception {
        int down_step = 5;// 提示step
        int totalSize;// 文件总大小
        int downloadCount = 0;// 已经下载好的大小
        int updateCount = 0;// 已经上传的文件大小
        InputStream inputStream;
        OutputStream outputStream;

        URL url = new URL(down_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setConnectTimeout(TIMEOUT);
        httpURLConnection.setReadTimeout(TIMEOUT);
        // 获取下载文件的size
        totalSize = httpURLConnection.getContentLength();
        if (httpURLConnection.getResponseCode() == 404) {
            throw new Exception("fail!");
        }
        inputStream = httpURLConnection.getInputStream();
        outputStream = new FileOutputStream(file, false);// 文件存在则覆盖掉
        byte buffer[] = new byte[1024];
        int readsize = 0;
        while ((readsize = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readsize);
            downloadCount += readsize;// 时时获取下载到的大小
            /**
             * 每次增张5%
             */
            if (updateCount == 0 || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
                updateCount += down_step;
                // 改变通知栏
                contentView.setTextViewText(R.id.notificationPercent, updateCount + "%");
                contentView.setProgressBar(R.id.notificationProgress, 100, updateCount, false);
                notificationManager.notify(notificationId, notification);

            }

        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        inputStream.close();
        outputStream.close();

        return downloadCount;

    }

    private File updateDir = null;
    private File updateFile = null;

    /***
     * 创建文件
     */
    public int createFile(String name) {
        int result = OK;
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        boolean isEnoughSDSpace = ((long) sf.getAvailableBlocks() * sf.getBlockSize()) > 8 * 1024 * 1024;
        if (!isEnoughSDSpace) {
            result = NO_SPACE;
        } else if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            updateDir = new File(Environment.getExternalStorageDirectory() + "/" + "");
            updateFile = new File(updateDir + "/" + name + ".apk");

            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }
            if (!updateFile.exists()) {
                try {
                    updateFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    result = ERROR;
                }
            }

        } else {
            result = ERROR;
        }
        return result;
    }

}
