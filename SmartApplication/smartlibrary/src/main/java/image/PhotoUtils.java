package image;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by morning on 2016/3/10.
 * <p/>
 * 图片类管理
 * 1.相册，拍照
 */
public class PhotoUtils {

    //相册
    public static final int CHOOSE_PICTURE = 0x0001;
    //拍照
    public static final int PHOTO_WITH_CAMERA = 0x0002;
    //图片缩放比例
    public static final int SCALE = 5;

    public static PhotoUtils instance;

    private static ImageView mImgView;

    public static PhotoUtils getInstance() {
        if (instance == null) {
            instance = new PhotoUtils();
        }
        return instance;
    }

    public void setImageView(ImageView imgView) {
        mImgView = imgView;
    }

    public ImageView getImageView() {
        return mImgView;
    }

    /**
     * 从相册获取图片
     *
     * @param act
     */
    public void doOpenAlburm(Activity act, ImageView imgView) {
        setImageView(imgView);
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        act.startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    }

    /**
     * 拍照获取相片*
     */
    public void doTakePhoto(Activity act, ImageView imgView) {
        setImageView(imgView);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //调用系统相机
        //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
        //直接使用，没有缩小
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        act.startActivityForResult(intent, PHOTO_WITH_CAMERA);  //用户点击了从相机获取
    }


    /**
     * 缩放Bitmap图片 *
     */
    public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 保存图片到sd卡
     *
     * @param path        路径
     * @param photoName   图片名
     * @param photoBitmap 图片bitmap
     */
    public static void savePhotoToSDCard(Bitmap photoBitmap, String photoName, String path) {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File photoFile = new File(path, photoName); //在指定路径下创建文件
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null)
                        fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap, boolean isRecycled) {

        if (bitmap == null) {
            return null;
        }

        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;

        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,
                false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp)
                && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }

        // 下面这句是关键
        return bmp;// Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,
        // false);
    }

    /**
     * 创建图片不同的文件名*
     */
    private String createPhotoFileName() {
        String fileName = "";
        Date date = new Date(System.currentTimeMillis());  //系统当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        fileName = dateFormat.format(date) + ".jpg";
        return fileName;
    }

    public Bitmap onResult(int requestCode, Intent data, Activity act) {
        Bitmap cropBm = null;
        switch (requestCode) {
            //相册
            case CHOOSE_PICTURE:
                ContentResolver resolver = act.getContentResolver();
                //照片的原始资源地址
                Uri originalUri = data.getData();
                try {
                    //使用ContentProvider通过URI获取原始图片
                    Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                    if (photo != null) {
                        //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                        Bitmap smallBitmap = zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                        //释放原始图片占用的内存，防止out of memory异常发生
                        photo.recycle();
                        if (getImageView() != null) {
                            cropBm = ImageCrop(smallBitmap, true);
                            getImageView().setImageBitmap(cropBm);
                        }

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            //拍照
            case PHOTO_WITH_CAMERA:

                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) { //是否有SD卡

                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");
                    //写一个方法将此文件保存到本应用下面啦
                    if (bitmap != null) {
                        //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                        Bitmap smallBitmap = zoomBitmap(bitmap, bitmap.getWidth() / 5, bitmap.getHeight() / 5);
                        if (getImageView() != null) {
                            cropBm = ImageCrop(smallBitmap, true);
                            getImageView().setImageBitmap(cropBm);
                        }

                    }
                } else {

                }
                break;
        }

        return cropBm;
    }

}
