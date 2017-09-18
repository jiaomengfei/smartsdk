package utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * 加载通讯录联系人
 * Created by jmf on 2016/11/3.
 */

public class PhoneContacts {

    public String[] getPhoneContacts(Context context, Uri uri) {
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = context.getContentResolver();
        Cursor phone=null;
        Cursor cursor=null;
        //取得电话本中开始一项的光标
        try {
             cursor = cr.query(uri, null, null, null, null);
            if (cursor != null &&  cursor.getCount()>0) {
                cursor.moveToFirst();
                //取得联系人姓名
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name=cursor.getString(nameFieldColumnIndex);
                    contact[0] = cursor.getString(nameFieldColumnIndex);

                //取得电话号码
                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                 phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
                //change by jmf 只做非空判断有crash.
                if (phone.getCount()>0) {
                    phone.moveToFirst();
                    contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phone.close();
                cursor.close();
                return contact;
            } else {
                return null;
            }
        }catch (Exception e){
            return null;
        }finally {
            if (null != phone && !phone.isClosed()) {
                phone.close();
            }
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }

    }



}