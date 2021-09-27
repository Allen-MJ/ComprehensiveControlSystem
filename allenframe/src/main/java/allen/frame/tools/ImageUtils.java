package allen.frame.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

public class ImageUtils {
	/**
	 * 解码base64  转换为图片
	 * @param base64String
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64String) {
		byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
		Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
		return bitmap;
	}
	public static String getImgeData(Bitmap bitmap){
		byte[] data = null;
		data = bitmap2byte(bitmap);
//			return new String(data, "ISO-8859-1");
		return Base64.encodeToString(data, Base64.DEFAULT);
	}
	public static byte[] bitmap2byte(Bitmap bitmap){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	public static String bitmap2base64(Bitmap bitmap){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		String base = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
		return base;
	}
	
	public static String BitmapToString(Bitmap bitmap){
		if(bitmap==null){
			return "";
		}
		return getImgeData(bitmap);
	}
	
	private void saveBitmap(Bitmap bitmap) {
		try {
			String path = Environment.getExternalStorageDirectory().getPath() + "/decodeImage.jpg";
			OutputStream stream = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据返回的图片路径来压缩图片
	 * 
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
			// be = (int) ((w / newOpts.outWidth + h / newOpts.outHeight) / 2);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
			// be = (int) ((w / newOpts.outWidth + h / newOpts.outHeight) / 2);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
		//return bitmap;// 压缩好比例大小后再进行质量压缩
	}
	private static Bitmap compressImage(Bitmap image) {
		if(image==null){
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

		return bitmap;
	}
}
