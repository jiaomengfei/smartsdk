package view;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import base.BaseAppActivity;
import test.jiao.smartapplication.R;
import net.NetworkUtil;
import system.RuntimePermissionsCompatible;
import utils.SdcardConfig;
import utils.SystemPermissionManager;
import ui.ToastUtil;


/**
 * 网页
 */

public class WebViewActivity extends BaseAppActivity implements View.OnClickListener{

    private Intent intent;
    private WebView mWebView;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        dialog = Loading.dialog(this);
        dialog.show();
        initViews();
        initData1();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null != mUploadMessage) {
                Uri result = intent == null || resultCode != RESULT_OK ? null
                        : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else if (INPUT_FILE_REQUEST_CODE == requestCode) {
            Uri[] results = null;

            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (intent == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = intent.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else if (REQUEST_CODE_PHOTO == requestCode) {
            //低版本的通过拍照进入
            if (intent == null) {
                // 从相机返回的数据
                if (photo != null) {
                    if (mFiveUploadMsg != null) {
                        FileInputStream fis;
                        try {
                            fis = new FileInputStream(photo);
                            int fileLen = fis.available();
                            if (fileLen<=0){
                                photo.deleteOnExit();
                                cancelFilePathCallback(mFiveUploadMsg);
                            }else{
                                mFiveUploadMsg.onReceiveValue(Uri.parse("file:" + photo.getAbsolutePath()));
                            }
                        } catch (FileNotFoundException e) {
                            cancelFilePathCallback(mFiveUploadMsg);
                        } catch (IOException e) {
                            cancelFilePathCallback(mFiveUploadMsg);
                        }
                    }
                } else {
                }
            }
            photo = null;
            mFiveUploadMsg = null;
        }
    }

    private void initViews() {
        mWebView = (WebView) findViewById(R.id.webview);
    }

    String titleStr = "";

    private void initData1() {
        intent = getIntent();
        titleStr = intent.getStringExtra("tv_title");
        String url = intent.getStringExtra("url");

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);

        //add by jmf 根据网络状态改变WebView加载状态
        if (NetworkUtil.isNetworkAvailable(WebViewActivity.this)) {
            mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        saveData(mWebSettings);

        newWin(mWebSettings);

        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
        mWebView.addJavascriptInterface(new WebBankInterface(),"InterfaceName");
//
//        if ("普惠百家收银台".equals(titleStr)) {
//            String postData = intent.getStringExtra("postData");
//
//            mWebView.postUrl(url, postData.getBytes());
//        } else
//            mWebView.loadUrl(url);
        mWebView.loadUrl("file:///android_asset/test.html");

    }

    @Override
    public void onClick(View view) {

    }

    public class WebBankInterface{
        @JavascriptInterface
        public void send(String message){
        ToastUtil.showToast(WebViewActivity.this,message);
        }
    }

    public static void startSelf(Activity activity,String url) {
        Intent intent = new Intent();
        intent.setClass(activity, WebViewActivity.class);
        intent.putExtra("url", url);
        Log.e(TAG, "WebViewActivity 请求地址: " + url);
        activity.startActivity(intent);
    }


    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(false);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
    }

    WebViewClient webViewClient = new WebViewClient() {

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dialog.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


    };
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;

    public static final int INPUT_FILE_REQUEST_CODE = 2;
    private ValueCallback<Uri[]> mFilePathCallback;

    private String mCameraPhotoPath;
    WebChromeClient webChromeClient = new WebChromeClient() {
        //关键代码，以下函数是没有API文档的，所以在Eclipse中会报错，如果添加了@Override关键字在这里的话。
// For Android 5.0+
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            try {
                if(RuntimePermissionsCompatible.isNeedCheckPermission(WebViewActivity.this)){
                    if(SystemPermissionManager.checkCameraPermission(WebViewActivity.this,"")) {
                        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
                        int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_CAMERA, android.os.Process.myUid(), getPackageName());
                        if (checkOp == AppOpsManager.MODE_IGNORED) {
                            ToastUtil.showToast(WebViewActivity.this,"由于相机权限被拒，您不能使用此功能！");
                            return false;
                        }

                    }else{
                        ToastUtil.showToast(WebViewActivity.this,"由于相机权限被拒，您不能使用此功能！");
                        return false;
                    }
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            // android 5.0
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePathCallback;

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {

                }
                takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");

            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

            return true;
        }


        // For Android 3.0+
        public void openFileChooser(final ValueCallback<Uri> uploadMsg) {
            showCameraOrImageDialog(uploadMsg,new CameraOrImageCallBack() {
                @Override
                public void onCameraClick() {
                    toPhoto(uploadMsg);
                }

                @Override
                public void onImageClick() {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    WebViewActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
                }
            });

        }

        // For Android 3.0+
        public void openFileChooser(final ValueCallback uploadMsg, String acceptType) {
            showCameraOrImageDialog(uploadMsg,new CameraOrImageCallBack() {

                @Override
                public void onCameraClick() {
                    toPhoto(uploadMsg);
                }

                @Override
                public void onImageClick() {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    WebViewActivity.this.startActivityForResult(
                            Intent.createChooser(i, "File Browser"),
                            FILECHOOSER_RESULTCODE);
                }
            });

        }

        //For Android 4.1
        public void openFileChooser(final ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            showCameraOrImageDialog(uploadMsg,new CameraOrImageCallBack() {
                @Override
                public void onCameraClick() {
                    toPhoto(uploadMsg);
                }

                @Override
                public void onImageClick() {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    WebViewActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), WebViewActivity.FILECHOOSER_RESULTCODE);
                }
            });
        }

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:et_name="android.permission.INTERNET"/>
        //<uses-permission android:et_name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:et_name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================

        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }
        //=========多窗口的问题==========================================================


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            if (newProgress == 100)
                dialog.dismiss();

        }
    };

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "capturedImage";
        File storageDir = new File(SdcardConfig.LOG_FOLDER_IMG);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    /**
     * 拍照返回的照片
     */
    File photo;
    private static final int REQUEST_CODE_PHOTO = 3;
    private ValueCallback<Uri> mFiveUploadMsg;

    /**
     * 去拍照
     */
    private void toPhoto(ValueCallback<Uri> uploadMsg) {
        if (uploadMsg == null)
            return;
        mFiveUploadMsg = uploadMsg;
        try {
            photo = createImageFile();
        } catch (IOException e) {

        }
        Uri u = Uri.fromFile(photo);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    interface CameraOrImageCallBack {
        public void onCameraClick();

        public void onImageClick();
    }

    private AlertDialog chooserDialog = null;

    private void showCameraOrImageDialog(final ValueCallback uploadCall, final CameraOrImageCallBack callBack) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.image_or_camera, null);
        TextView camera = (TextView) view.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onCameraClick();
                    if (chooserDialog != null) {
                        chooserDialog.dismiss();
                        chooserDialog = null;
                    }
                }
            }
        });
        TextView image = (TextView) view.findViewById(R.id.file);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onImageClick();
                    if (chooserDialog != null) {
                        chooserDialog.dismiss();
                        chooserDialog = null;
                    }
                }
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooserDialog.dismiss();
                chooserDialog = null;
                cancelFilePathCallback(uploadCall);
            }
        });
        chooserDialog = builder.create();
        chooserDialog.setCanceledOnTouchOutside(false);
        chooserDialog.show();
    }

    /**
     * 取消mFilePathCallback回调
     * @param uploadCall
     */
    private void cancelFilePathCallback(ValueCallback uploadCall) {
        if (uploadCall != null) {
            uploadCall.onReceiveValue(null);
            uploadCall = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mWebView != null) {
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
    }

}