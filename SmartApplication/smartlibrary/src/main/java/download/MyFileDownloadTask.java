package download;

/**
 * Created by jmf on 2017/4/13 0013.
 */

import android.os.AsyncTask;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.toolsfinal.io.FileUtils;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Desction:
 * Author:pengjianbo
 * Date:15/12/10 下午10:45
 */
public class MyFileDownloadTask extends AsyncTask<Void, Long, Boolean> {

    private okhttp3.OkHttpClient okHttpClient;
    private MyFileDownloadCallback callback;
    private String url;
    private File target;
    //开始下载时间，用户计算加载速度
    private long previousTime;

    public MyFileDownloadTask(String url, File target, MyFileDownloadCallback callback) {
        this.url = url;
        this.okHttpClient = OkHttpFinal.getInstance().getOkHttpClientBuilder().build();
        this.callback = callback;
        this.target = target;

        FileUtils.mkdirs(target.getParentFile());
        if (target.exists()) {
            target.delete();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        previousTime = System.currentTimeMillis();
        if (callback != null) {
            callback.onStart();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //构造请求
        final Request request = new Request.Builder()
                .url(url)
                .build();

        boolean suc = false;
        try {
            Response response = okHttpClient.newCall(request).execute();
            long total = response.body().contentLength();
            saveFile(response);
            if (total == target.length()) {
                suc = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            suc = false;
        }

        return suc;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        if (callback != null && values != null && values.length >= 2) {
            long sum = values[0];
            long total = values[1];

            int progress = (int) (sum * 100.0f / total);
            //计算下载速度
            long totalTime = (System.currentTimeMillis() - previousTime) / 1000;
            if (totalTime == 0) {
                totalTime += 1;
            }
            long networkSpeed = sum / totalTime;
            callback.onProgress(progress,total, sum);
        }
    }

    @Override
    protected void onPostExecute(Boolean suc) {
        super.onPostExecute(suc);
        if (suc) {
            if (callback != null) {
                callback.onDone();
            }
        } else {
            if (callback != null) {
                callback.onFailure();
            }
        }
    }

    public String saveFile(Response response) throws IOException {
        if (isCancelled()) return null;
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;

            FileUtils.mkdirs(target.getParentFile());

            fos = new FileOutputStream(target);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);

                if (callback != null) {
                    publishProgress(sum, total);
                }
            }
            fos.flush();

            return target.getAbsolutePath();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
