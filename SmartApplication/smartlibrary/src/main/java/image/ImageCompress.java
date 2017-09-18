package image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageCompress {

	private static final int DEFAULT_LENGTH = 100;

//	/**
//	 * 将图像保存到指定的文件
//	 *
//	 * @param bitmap
//	 * @param outPath
//	 * @throws FileNotFoundException
//	 */
//	public static void saveImageToFile(Bitmap bitmap, String pFileName)
//			throws FileNotFoundException {
//		FileOutputStream os = new FileOutputStream(pFileName);
//		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
//	}

	/**
	 * 加载图片从指定的文件
	 *
	 * @param imgPath
	 * @return
	 */
	public static Bitmap loadBitmap(String pImgFileName) {
		// Get bitmap through image path
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = false;
		newOpts.inPurgeable = true;
		newOpts.inInputShareable = true;

		// Do not compress
		newOpts.inSampleSize = 1;
		newOpts.inPreferredConfig = Config.RGB_565;
		return BitmapFactory.decodeFile(pImgFileName, newOpts);
	}

	/**
	 * 质量压缩方法：像素不变，但是减少图片的色深、像素透明变化等；但是加载到内存中还是需要原先大小，因为像素没有发生改变
	 * 该方法适用于将图像存储到文件中或者流式发送时，减少存储文件的大小情况下使用。
	 *
	 * 注意：调用该方法进行压缩时耗时还是较长的。
	 *
	 * @param pLength
	 *            要压缩成的大小 若指定为0， 则使用默认设置为100KB
	 * @param image
	 * @return
	 */
	public static Bitmap compressImageReduceQuality(Bitmap image, int pLength) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;

		// 若没有指定压缩大小，则设置默认为100kB
		if (pLength == 0) {
			pLength = DEFAULT_LENGTH;
		}

		while (baos.toByteArray().length / 1024 > pLength) { // 循环判断如果压缩后图片是否大于指定的kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10

			Log.d("ImageCompress", "options:" + options);
			Log.d("ImageCompress",
					"baos.toByteArray().length:" + baos.toByteArray().length);
		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 图片按比例大小压缩方法（根据路径获取图片并压缩）
	 *
	 * @param srcPath
	 * @return
	 */
	public static Bitmap compressImageScale(String srcPath, int pWidth,
											int pHeight) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;

		// 下面这句可选
		newOpts.inPreferredConfig = Bitmap.Config.RGB_565;

		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > pWidth) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / pWidth);
		} else if (w < h && h > pHeight) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / pHeight);
		}

		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// return compressImage(bitmap, 0);//压缩好比例大小后再进行质量压缩
		return bitmap;
	}

	/**
	 * Compress image by size, this will modify image width/height. Used to get
	 * thumbnail
	 *
	 * @param image
	 * @param pWidth
	 *            target pixel of width
	 * @param pHeight
	 *            target pixel of height
	 * @return
	 */
	public static Bitmap compressImageScale(Bitmap image, float pWidth, float pHeight) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, os);
		if (os.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			os.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, os);// 这里压缩50%，把压缩后的数据存放到baos中
		}

		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;

		// 这句可选
		newOpts.inPreferredConfig = Config.RGB_565;

		Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > pWidth) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / pWidth);
		} else if (w < h && h > pHeight) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / pHeight);
		}

		if (be <= 0) {

			be = 1;
		}

		newOpts.inSampleSize = be;// 设置缩放比例

		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		is = new ByteArrayInputStream(os.toByteArray());
		bitmap = BitmapFactory.decodeStream(is, null, newOpts);
		// 压缩好比例大小后再进行质量压缩
		// return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
		return bitmap;
	}

	/**
	 * 图片先按比例大小压缩后再方法（根据Bitmap图片压缩）
	 *
	 * @param image
	 * @return
	 */
	public static Bitmap compressImageAndScale(Bitmap image, int pWidth,
											   int pHeight) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();

		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > pWidth) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / pWidth);
		} else if (w < h && h > pHeight) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / pHeight);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImageReduceQuality(bitmap, 0);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 获取Image的扩展属性
	 *
	 * TAG_DATETIME时间日期
	 * 　　TAG_FLASH闪光灯
	 * 　　TAG_GPS_LATITUDE纬度
	 * 　　TAG_GPS_LATITUDE_REF纬度参考
	 * 　　TAG_GPS_LONGITUDE经度
	 * 　　TAG_GPS_LONGITUDE_REF经度参考
	 * 　　TAG_IMAGE_LENGTH图片长
	 * 　　TAG_IMAGE_WIDTH图片宽
	 * 　　TAG_MAKE设备制造商
	 * 　　TAG_MODEL设备型号
	 * 　　TAG_ORIENTATION方向
	 * 　　TAG_WHITE_BALANCE白平衡
	 * @throws IOException
	 */
	public static ImageExInfo getImageExInfo(String pImageFileName) throws IOException {
		ImageExInfo aExInfo = new ImageExInfo();
		ExifInterface aExifInterface = new ExifInterface(pImageFileName);

		aExInfo.orientation = aExifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
		aExInfo.model = aExifInterface.getAttribute(ExifInterface.TAG_MODEL);

		return aExInfo;
	}

	/**
	 * 将图像保存到指定的文件
	 * @param pFileName
	 * @param pBitmap
	 * @throws Exception
	 */
	public static void saveImageToFile(String pFileName, Bitmap pBitmap)
			throws Exception {
		OutputStream outputStream = null;
		try {

			if (pBitmap == null) {
				// fix me...
				// throw exception
				throw new Exception("参数错误");
			}
			File file = new File(pFileName);
			outputStream = new FileOutputStream(file);
			pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Throwable t) {
				}
			}
		}
	}

}
