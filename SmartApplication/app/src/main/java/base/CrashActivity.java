package base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.jiao.smartapplication.R;

/**
 * Created by jmf on 2017/4/6 0006.
 */

public class CrashActivity extends AppCompatActivity {
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.lv)
    ListView lv;
    private MYAdapter adapter;
    private ArrayList<File> filesList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        ButterKnife.bind(this);
        initView();
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(SdcardConfig.LOG_FOLDER);
                if (file.exists() && file.listFiles() != null && file.listFiles().length > 0) {
                    for (File single : file.listFiles()) {
                        single.delete();
                    }
                }
                filesList.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        lv.setAdapter(adapter = new MYAdapter(filesList = getFileList()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogActivity.startThis(CrashActivity.this, LogActivity.TYPE_WATCH, filesList.get(position).getAbsolutePath());
            }
        });
    }


    private ArrayList<File> getFileList() {
        File file = new File(SdcardConfig.LOG_FOLDER);
        File[] files = file.listFiles();
        ArrayList<File> list = new ArrayList();
        if (files != null && files.length != 0) {
            for (File fileSingle : files) {
                list.add(fileSingle);
            }
        }
        return list;
    }

    class MYAdapter extends BaseAdapter {
        ArrayList<File> list = null;

        public MYAdapter(ArrayList<File> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(CrashActivity.this, R.layout.select_bank_item2, null);
                holder.select_bank_item_title = (TextView) convertView.findViewById(R.id.select_bank_item_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.select_bank_item_title.setText(list.get(position).getName());
            return convertView;
        }

        class ViewHolder {
            private TextView select_bank_item_title;
        }
    }

    public static void startThis(Activity activity) {
        Intent intent = new Intent(activity, CrashActivity.class);
        activity.startActivity(intent);
    }
}
