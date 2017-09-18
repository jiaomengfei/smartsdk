/*
 *	Copyright (c) 2012, Yulong Information Technologies
 *	All rights reserved.
 *  
 *  @Project: CarBaoshijie
 *  @author: Robot	
 */
package utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static utils.Cans.MENU_LIST_HOME;
import static utils.Cans.MENU_LIST_MORE;


/**
 * 文件操作工具类
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

    public static void clearFiles(Context context) {
        String[] files = context.fileList();
        if (files != null && files.length > 0) {
            for (String name : files) {
                if (name == null) {
                    continue;
                }
                if (name.startsWith("cache_")) {
                    context.deleteFile(name);
                }
            }
        }
    }

    /**
     * 保存数据对象 没有cache开头
     */
    public static boolean saveWithCatch(Context context, Object object, String filename) {
        boolean result = false;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
//        String absolutePath = context.getFilesDir().getAbsolutePath() + File.separator;

        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            result = true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage() + "");
            }
        }
        return result;
    }

    /**
     * 保存数据对象
     */
    public static boolean save(Context context, Object object, String filename) {
        if ("getParams".equals(filename) || MENU_LIST_HOME.equals(filename) || MENU_LIST_MORE.equals(filename)) {

        } else {
            filename = "cache_" + filename;
        }
        boolean result = false;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
//        String absolutePath = context.getFilesDir().getAbsolutePath() + File.separator;

        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            result = true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage() + "");
            }
        }
        return result;
    }

    /**
     * 读取数据对象
     *
     * @param context
     * @param filename
     * @return
     */
    public static Object getObject(Context context, String filename) {
        if (context == null || filename == null) {
            return null;
        }
        if ("getParams".equals(filename) || MENU_LIST_HOME.equals(filename) || MENU_LIST_MORE.equals(filename)) {

        } else {
            filename = "cache_" + filename;
        }
        Object object = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        String absolutePath = context.getFilesDir().getAbsolutePath() + File.separator;
        try {
            if (exists(context, filename)) {
                fis = context.openFileInput(filename);
                ois = new ObjectInputStream(fis);
                object = ois.readObject();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage() + "");
            }
        }
        return object;
    }

    /**
     * 删除数据对象
     *
     * @param context
     * @param filename
     */
    public static boolean remove(Context context, String filename) {
        if ("getParams".equals(filename) || MENU_LIST_HOME.equals(filename) || MENU_LIST_MORE.equals(filename)) {

        } else {
            filename = "cache_" + filename;
        }
        boolean flag = false;
        if (context == null || filename == null) {
            return flag;
        }
        File file = context.getFileStreamPath(filename);
        if (file != null && file.exists() && file.isFile()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 判断文件是否存在（应用内部目录）
     *
     * @param context
     * @param filename
     * @return
     */
    private static boolean exists(Context context, String filename) {

        File file = context.getFileStreamPath(filename);
        return file != null && file.exists();
        // String path = context.getFilesDir().getPath() + "/" + filename;
        // File file = new File(path);
        // return file.exists();
    }

    public static Bitmap getBitmap(String filePath, int destWidth, int destHeight) {
        //第一次采样
        BitmapFactory.Options options = new BitmapFactory.Options();
        //该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
        options.inJustDecodeBounds = true;
        //第一次加载图片，这时只会加载图片的边框进来，并不会加载图片中的像素点
        BitmapFactory.decodeFile(filePath, options);
        //获得原图的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //定义缩放比例
        int sampleSize = 1;
        while (outHeight / sampleSize > destHeight || outWidth / sampleSize > destWidth) {
            //如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
            //sampleSize应该为2的n次幂，如果给sampleSize设置的数字不是2的n次幂，那么系统会就近取值
            sampleSize *= 2;
        }
        //至此，第一次采样已经结束，我们已经成功的计算出了sampleSize的大小
        //二次采样开始
        //二次采样时我需要将图片加载出来显示，不能只加载图片的框架，因此inJustDecodeBounds属性要设置为false
        options.inJustDecodeBounds = false;
        //设置缩放比例
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //加载图片并返回
        return BitmapFactory.decodeFile(filePath, options);
    }


    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (activeInfo != null) {
            return activeInfo.isConnected();
        }
        return false;
    }

    public static boolean saveImageToSDCard(Context context, Bitmap bmp, String filename, int yasuo) {
        File appDir = new File(SdcardConfig.LOG_FOLDER_LOG, "phbj");
        boolean compress = false;
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file;
        if (yasuo == 99) {

            file = new File(appDir, filename + ".png");
        } else {
            file = new File(appDir, filename + ".jpg");
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            compress = bmp.compress(Bitmap.CompressFormat.JPEG, yasuo, fos);

            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compress;
    }

    public static boolean saveImage(Context context, Bitmap bmp, String filename, int yasuo) {
        File appDir = new File(context.getFilesDir(), "phbj");
        boolean compress = false;
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file;
        if (yasuo == 99) {

            file = new File(appDir, filename + ".png");
        } else {
            file = new File(appDir, filename + ".jpg");
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            compress = bmp.compress(Bitmap.CompressFormat.JPEG, yasuo, fos);

            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compress;
    }

    public static String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


}
