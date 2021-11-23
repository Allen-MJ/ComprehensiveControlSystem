package allen.frame.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.os.Environment.MEDIA_MOUNTED;

public class FileUtils {
    private static FileUtils utils;
    private String genFile;
    private static File gen;

    private FileUtils() {
        // genFile = Environment.getExternalStorageDirectory()+"/" +
        // PackageUtiles.getInstance().getPackagename();
        genFile = Environment.getExternalStorageDirectory() + "/"
                + "dzzzCache";
        gen = new File(genFile);
        if (!gen.exists()) {
            gen.mkdirs();
        }
    }

    public static FileUtils getInstance() {
        if (utils == null) {
            utils = new FileUtils();
        }
        return utils;
    }

    /**
     * 判断文件类型
     *
     * @param type 文件后缀名
     * @return
     */
    public static String fileType(String type) {
        type = type.toLowerCase();
        String[] image = {"bmp", "png", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd", "cdr",
                "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf"};
        for (int i = 0; i < image.length; i++) {
            if (image[i].equals(type)) {
                return "image";
            }
        }
        String[] video = {"mp4", "avi", "mov", "mpeg", "mpg", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb",
                "webm", "flv", "ts", "tb", "divx", "xvid", "m4v"};
        for (int i = 0; i < video.length; i++) {
            if (video[i].equals(type)) {
                return "video";
            }
        }

        String[] audio = {"mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
                "m4a", "vqf"};
        for (int i = 0; i < audio.length; i++) {
            if (audio[i].equals(type)) {
                return "audio";
            }
        }
        String[] docment = {"doc", "docx", "xls", "rtf", "wpd", "pdf", "ppt", "pps", "xlsx", "txt", "pptx"};
        for (int i = 0; i < docment.length; i++) {
            if (docment[i].equals(type)) {
                return "docment";
            }
        }

        String[] html = {"html", "aspx", "htm", "jsp", "chm"};
        for (int i = 0; i < html.length; i++) {
            if (html[i].equals(type)) {
                return "html";
            }
        }
        String[] file = {"file"};
        for (int i = 0; i < file.length; i++) {
            if (file[i].equals(type)) {
                return "file";
            }
        }
        return "";
    }

    public String getGenPath() {
        return genFile;
    }

    /**
     * 创建新文件夹
     *
     * @param name
     * @return
     */
    public File creatNewDir(String name) {
        File dir = new File(genFile + "/" + name);
        if (dir == null || !dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public String url2LocalName(String url){
        String type = StringUtils.getFileTypeByPath(url);
        if(StringUtils.notEmpty(type)){
            return EncryptUtils.MD5Encoder(url)+"."+type;
        }else{
            return StringUtils.getFileNameByPath(url);
        }
    }

    /**
     * 创建文件
     *
     * @param dir
     * @param name
     * @return
     */
    public File creatNewFile(String dir, String name) {
        File file = new File(creatNewDir(dir), name);
        if (file == null || !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }


    public static String getFileSize(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        }
        else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        }
        else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        }
        else {
            if (size <= 0) {
                bytes.append("0B");
            }
            else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();

    }

    public static void SortFilesByName(List<File> fileList) {
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return Collator.getInstance(java.util.Locale.CHINA).compare(o1.getName(), o2.getName());
            }

        });
    }

    public static int getSubfolderNum(String path) {
        int i=0;
        File[] files = new File(path).listFiles();
        if (files==null){
            return 0;
        }
        for (File f : files) {
            if (f.getName().indexOf(".") != 0){
                i++;
            }
        }
        return i;
    }

    public static boolean isAudioFileType(String path){
        String type = getFileTypeByPath(path);
        if(StringUtils.notEmpty(type)){
            return type.equals("audio");
        }
        return false;
    }
    public static boolean isImageFileType(String path){
        String type = getFileTypeByPath(path);
        if(StringUtils.notEmpty(type)){
            return type.equals("image");
        }
        return false;
    }
    public static boolean isVideoFileType(String path){
        String type = getFileTypeByPath(path);
        if(StringUtils.notEmpty(type)){
            return type.equals("video");
        }
        return false;
    }

    /**
     * 根据路径获取文件类型
     * @param path
     * @return
     */
    public static String getFileTypeByPath(String path){
        if(StringUtils.empty(path)){
            return "";
        }
        int last = path.lastIndexOf(".");
        if(last<0){
            return "";
        }
        String type = path.substring(last+1);
        return fileType(type);
    }

    /**
     * @param f
     * @return
     */
    public static String file2byte(File f) {
        FileInputStream fs = null;
        ByteArrayOutputStream outStream = null;
        try {
            fs = new FileInputStream(f);
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while (-1 != (len = fs.read(buffer))) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base = Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT);
        return base;
    }


    @SuppressWarnings("resource")
    public String creatImgFile(String dir, String name, Bitmap bitmap) {
        File imgfile = creatNewFile(dir, name);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100 /* ignored for PNG */, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imgfile);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return imgfile.getAbsolutePath();
    }

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    public static File createTmpFile(Context context) throws IOException {
        File dir = null;
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (!dir.exists()) {
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
                if (!dir.exists()) {
                    dir = getCacheDirectory(context, true);
                }
            }
        } else {
            dir = getCacheDirectory(context, true);
        }
        return File.createTempFile(JPEG_FILE_PREFIX, JPEG_FILE_SUFFIX, dir);
    }

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * Returns application cache directory. Cache directory will be created on
     * SD card <i>("/Android/data/[app_package_name]/cache")</i> if card is
     * mounted and app has appropriate permission. Else - Android defines cache
     * directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card
     * is unmounted and {@link Context#getCacheDir()
     * Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    /**
     * Returns application cache directory. Cache directory will be created on
     * SD card <i>("/Android/data/[app_package_name]/cache")</i> (if card is
     * mounted and app has appropriate permission) or on device's file system
     * depending incoming parameters.
     *
     * @param context        Application context
     * @param preferExternal Whether prefer external location for cache
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card
     * is unmounted and {@link Context#getCacheDir()
     * Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e) { // (sh)it happens too (Issue
            // #989)
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    /**
     * Returns individual application cache directory (for only image caching
     * from ImageLoader). Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is
     * mounted and app has appropriate permission. Else - Android defines cache
     * directory on device's file system.
     *
     * @param context  Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir",
     *                 "AppDir/cache/images")
     * @return Cache {@link File directory}
     */
    public static File getIndividualCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(appCacheDir, cacheDir);
        if (!individualCacheDir.exists()) {
            if (!individualCacheDir.mkdir()) {
                individualCacheDir = appCacheDir;
            }
        }
        return individualCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    //将文件转换成Byte数组
    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //将Byte数组转换成文件
    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getRealPathFromURI(Uri contentUri, Activity activity) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:")) {
                    return id.replaceFirst("raw:", "");
                }
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
