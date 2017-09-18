package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

import test.jiao.smartapplication.R;
import utils.PhoneContacts;
import system.RuntimePermissionsCompatible;
import utils.SystemPermissionManager;

public class PermissionActivity extends Activity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private EditText mPhoneNumber;
    private Button mGetNumber;
    private Button mChoicePicture;
    private ImageView mDisplayPicture;
    //读取联系人，拍照选择图库照片，权限检测
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mGetNumber = (Button) findViewById(R.id.getNumber);
        mGetNumber.setOnClickListener(this);
        mChoicePicture = (Button) findViewById(R.id.choicePicture);
        mChoicePicture.setOnClickListener(this);
        mDisplayPicture = (ImageView) findViewById(R.id.displayPicture);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getNumber:
                try {
                    if (RuntimePermissionsCompatible.isNeedCheckPermission(PermissionActivity.this)) {
                        if (SystemPermissionManager.checkContactsPermission(PermissionActivity.this, "",SystemPermissionManager.PERMISSION_REQUEST_CODE)) {
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(intent, 0);
                        }
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, 0);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.choicePicture:

                break;
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(resultCode==Activity.RESULT_CANCELED){
           return;
       }
        if (requestCode == 0 && resultCode==Activity.RESULT_OK) {
            if (data != null) {
                PhoneContacts phone = new PhoneContacts();
                Uri uri = data.getData();
                String[] contacts = phone.getPhoneContacts(this, uri);
                if (contacts!=null) {
                    String number = contacts[1].replaceAll(" ", "");
                    mPhoneNumber.setText(number);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (null == permissions || permissions.length == 0) {
            return;
        }
        Map<String, Boolean> runtimePermissionsResults = RuntimePermissionsCompatible
                .onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        if (runtimePermissionsResults == null) {
            return;
        }

        boolean readContacts = runtimePermissionsResults.get(permissions[0]);
        boolean readPhoneState = runtimePermissionsResults.get(permissions[1]);
        boolean writeContacts=runtimePermissionsResults.get(permissions[2]);
        // 容许后执行业务
        switch (requestCode) {

            case SystemPermissionManager.PHBJ_PERMISSIONS_CONTACTS: {

                if (readContacts && readPhoneState && writeContacts) {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 0);
                    // permission was granted, yay! do the calendar task you need to
                    // do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    // 不容许使用下载功能
                    //ToastUtil.showToast(this, "由于权限被拒，您不能使用此功能！");
                    Toast.makeText(PermissionActivity.this,"jmf",Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }
}
