package metadata;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class MetadataUtils {


    public static String getValue(Context context, String paramKeyName) {
        Bundle metaData = null;
        String strValue = null;
        
        try {
            ApplicationInfo aApplicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (aApplicationInfo == null) {
				return null;
			}
            
              metaData = aApplicationInfo.metaData;
            if (metaData == null) {
            	return null;				
			}
            
            strValue = metaData.getString(paramKeyName);
             
        } catch (NameNotFoundException e) {
        			e.printStackTrace();
        }
        
        return strValue;
    }
}
