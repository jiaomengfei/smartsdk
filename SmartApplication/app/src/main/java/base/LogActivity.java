package base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.jiao.smartapplication.R;

/**
 * Created by jmf on 2017/4/5 0005.
 */

public class LogActivity extends AppCompatActivity {
    @BindView(R.id.log_clear)
    Button logClear;
    @BindView(R.id.log_wv)
    WebView logWv;
    @BindView(R.id.textView6)
    TextView textView6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null && TYPE_WATCH.equals(intent.getStringExtra(TYPE))) {
            logClear.setVisibility(View.GONE);
            textView6.setVisibility(View.GONE);
            String path = intent.getStringExtra(PATH);
            logWv.loadData(getCrash(path), "text/html; charset=UTF-8", null);
        } else {
            initView();
            setAction();
        }
    }

    final String mimeType = "text/html";
    final String encoding = "UTF-8";

    private void initView() {
        logWv.loadData(getLog(), "text/html; charset=UTF-8", null);
    }

    private static String path = SdcardConfig.LOG_FOLDER_LOG;

    public static void writeFile(String filePathAndName, String fileContent) {
        try {
            File f = new File(filePathAndName);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f, true), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            //PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePathAndName)));
            //PrintWriter writer = new PrintWriter(new FileWriter(filePathAndName));
            writer.write(fileContent);
            writer.close();
        } catch (Exception e) {
            System.out.println("写文件内容操作出错");
            e.printStackTrace();
        }
    }

    public static void saveLog(String content) {
        String fileName = path + "phbjlog.txt";
        writeFile(fileName, content);
    }

    public static void deleteLog() {
        String fileName = path + "phbjlog.txt";
        File file = new File(fileName);
        if (file.exists()) {
            boolean result = file.delete();
            Log.e("sumile", "deleteLog: " + result);
        }
    }

    public static String readFile(String filePathAndName) {
        String fileContent = "";
        try {
            File f = new File(filePathAndName);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent += line;
                }
                read.close();
            }
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
        return fileContent;
    }

    public static String getLog() {
        String fileName = path + "phbjlog.txt";
        return readFile(fileName);
    }

    public static String getCrash(String path) {
        return readFile(path);
    }

    private void setAction() {
        logClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLog();
                initView();
            }
        });
    }

    public static void startThis(Activity activity) {
        Intent intent = new Intent(activity, LogActivity.class);
        activity.startActivity(intent);
    }

    public static final String TYPE = "type";
    public static final String TYPE_WATCH = "type_watch";
    public static final String PATH = "path";

    public static void startThis(Activity activity, String type, String path) {
        Intent intent = new Intent(activity, LogActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(PATH, path);
        activity.startActivity(intent);
    }
}
