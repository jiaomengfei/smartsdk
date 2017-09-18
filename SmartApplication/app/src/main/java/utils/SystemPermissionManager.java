package utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PermissionInfo;
import android.text.TextUtils;

import bean.Permission;
import system.RuntimePermissionsCompatible;
import ui.ToastUtil;


/**
 * Created by jmf on 2017/7/25 0025.
 */

public class SystemPermissionManager {

    //检查联系人需要的相关权限
    public static final int PHBJ_PERMISSIONS_CONTACTS = 10001;

    //检查相机拍照需要的相关权限
    public static final int PHBJ_PERMISSIONS_CAMERA=10002;

    /** 默认的权限请求码 */
    public static final int PERMISSION_REQUEST_CODE = -1;

    /**
     * 检查联系人权限
     */
    public static boolean checkContactsPermission(Activity paramContext, String paramDesc, int requestCode) {
        boolean isGranted = false;
        // 音频权限
        Permission permission = new Permission(Manifest.permission.READ_CONTACTS,
                PermissionInfo.PROTECTION_DANGEROUS);

        if (RuntimePermissionsCompatible.isGranted(paramContext, permission.name)) {
            // 容许执行
            isGranted = true;
            return isGranted;
        }

        if (RuntimePermissionsCompatible.shouldPrompt(paramContext,
                permission.name)) {
            // Explain WHY you need this permission 给用户明确的解释为什么要使用对应的权限
            if (!TextUtils.isEmpty(paramDesc)) {
                ToastUtil.showToast(paramContext, paramDesc);
            }
        }

        // 执行权限请求
        if (requestCode < 0) {
            RuntimePermissionsCompatible.requestPermission(paramContext,
                    permission.name, PHBJ_PERMISSIONS_CONTACTS);
            return isGranted;
        }
        RuntimePermissionsCompatible.requestPermission(paramContext,
                permission.name, requestCode);

        return isGranted;

    }

    /**
     * 检查相机权限
     */
    public static boolean checkCameraPermission(Activity paramContext, String paramDesc){
        boolean isGranted = false;
        // 存储权限检查
        Permission permission1= new Permission(
                Manifest.permission.CAMERA,
                PermissionInfo.PROTECTION_DANGEROUS);
//        Permission permission2= new Permission(
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                PermissionInfo.PROTECTION_DANGEROUS);
//        Permission permission3= new Permission(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                PermissionInfo.PROTECTION_DANGEROUS);
        String[] permissions = new String[]{permission1.name};

        if (RuntimePermissionsCompatible.areAllGranted(paramContext, permissions)) {
            // 容许执行
            isGranted = true;
            return isGranted;
        }

        if (RuntimePermissionsCompatible.shouldPrompt(paramContext,
                permissions)) {
            // Explain WHY you need this permission 给用户明确的解释为什么要使用对应的权限
            if (!TextUtils.isEmpty(paramDesc)) {
                ToastUtil.showToast(paramContext, paramDesc);
            }
        }

        // 执行权限请求
        RuntimePermissionsCompatible.requestPermissions(paramContext, permissions,
                PHBJ_PERMISSIONS_CAMERA);
        return isGranted;
    }

}
