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
//	 * ��ͼ�񱣴浽ָ�����ļ�
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
	 * ����ͼƬ��ָ�����ļ�
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
	 * ����ѹ�����������ز��䣬���Ǽ���ͼƬ��ɫ�����͸���仯�ȣ����Ǽ��ص��ڴ��л�����Ҫԭ�ȴ�С����Ϊ����û�з����ı�
	 * �÷��������ڽ�ͼ��洢���ļ��л�����ʽ����ʱ�����ٴ洢�ļ��Ĵ�С�����ʹ�á�
	 *
	 * ע�⣺���ø÷�������ѹ��ʱ��ʱ���ǽϳ��ġ�
	 *
	 * @param pLength
	 *            Ҫѹ���ɵĴ�С ��ָ��Ϊ0�� ��ʹ��Ĭ������Ϊ100KB
	 * @param image
	 * @return
	 */
	public static Bitmap compressImageReduceQuality(Bitmap image, int pLength) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
		int options = 100;

		// ��û��ָ��ѹ����С��������Ĭ��Ϊ100kB
		if (pLength == 0) {
			pLength = DEFAULT_LENGTH;
		}

		while (baos.toByteArray().length / 1024 > pLength) { // ѭ���ж����ѹ����ͼƬ�Ƿ����ָ����kb,���ڼ���ѹ��
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ��options%����ѹ��������ݴ�ŵ�baos��
			options -= 10;// ÿ�ζ�����10

			Log.d("ImageCompress", "options:" + options);
			Log.d("ImageCompress",
					"baos.toByteArray().length:" + baos.toByteArray().length);
		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ��ByteArrayInputStream��������ͼƬ
		return bitmap;
	}

	/**
	 * ͼƬ��������Сѹ������������·����ȡͼƬ��ѹ����
	 *
	 * @param srcPath
	 * @return
	 */
	public static Bitmap compressImageScale(String srcPath, int pWidth,
											int pHeight) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;

		// ��������ѡ
		newOpts.inPreferredConfig = Bitmap.Config.RGB_565;

		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// ��ʱ����bmΪ��

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > pWidth) {// �����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / pWidth);
		} else if (w < h && h > pHeight) {// ����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / pHeight);
		}

		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// �������ű���
		// ���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// return compressImage(bitmap, 0);//ѹ���ñ�����С���ٽ�������ѹ��
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
		if (os.toByteArray().length / 1024 > 1024) {// �ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���
			os.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, os);// ����ѹ��50%����ѹ��������ݴ�ŵ�baos��
		}

		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;

		// ����ѡ
		newOpts.inPreferredConfig = Config.RGB_565;

		Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > pWidth) {// �����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / pWidth);
		} else if (w < h && h > pHeight) {// ����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / pHeight);
		}

		if (be <= 0) {

			be = 1;
		}

		newOpts.inSampleSize = be;// �������ű���

		// ���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		is = new ByteArrayInputStream(os.toByteArray());
		bitmap = BitmapFactory.decodeStream(is, null, newOpts);
		// ѹ���ñ�����С���ٽ�������ѹ��
		// return compress(bitmap, maxSize); // �����ٽ�������ѹ�������岻�󣬷�������Դ��ɾ��
		return bitmap;
	}

	/**
	 * ͼƬ�Ȱ�������Сѹ�����ٷ���������BitmapͼƬѹ����
	 *
	 * @param image
	 * @return
	 */
	public static Bitmap compressImageAndScale(Bitmap image, int pWidth,
											   int pHeight) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// �ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// ����ѹ��50%����ѹ��������ݴ�ŵ�baos��
		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();

		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > pWidth) {// �����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / pWidth);
		} else if (w < h && h > pHeight) {// ����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / pHeight);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// �������ű���
		// ���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImageReduceQuality(bitmap, 0);// ѹ���ñ�����С���ٽ�������ѹ��
	}

	/**
	 * ��ȡImage����չ����
	 *
	 * TAG_DATETIMEʱ������
	 * ����TAG_FLASH�����
	 * ����TAG_GPS_LATITUDEγ��
	 * ����TAG_GPS_LATITUDE_REFγ�Ȳο�
	 * ����TAG_GPS_LONGITUDE����
	 * ����TAG_GPS_LONGITUDE_REF���Ȳο�
	 * ����TAG_IMAGE_LENGTHͼƬ��
	 * ����TAG_IMAGE_WIDTHͼƬ��
	 * ����TAG_MAKE�豸������
	 * ����TAG_MODEL�豸�ͺ�
	 * ����TAG_ORIENTATION����
	 * ����TAG_WHITE_BALANCE��ƽ��
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
	 * ��ͼ�񱣴浽ָ�����ļ�
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
				throw new Exception("��������");
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
