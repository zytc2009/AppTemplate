package com.arch.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by shishoufeng on 2019/12/11.
 * <p>
 * desc : 操作 bitmap 工具库
 */
public final class ImageUtils {


    public static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * View to bitmap.
     *
     * @param view The view.
     * @return bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) {
            return null;
        }
        boolean drawingCacheEnabled = view.isDrawingCacheEnabled();
        boolean willNotCacheDrawing = view.willNotCacheDrawing();
        view.setDrawingCacheEnabled(true);
        view.setWillNotCacheDrawing(false);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (null == drawingCache) {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            bitmap = Bitmap.createBitmap(view.getDrawingCache());
        } else {
            bitmap = Bitmap.createBitmap(drawingCache);
        }
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCacheDrawing);
        view.setDrawingCacheEnabled(drawingCacheEnabled);

        return bitmap;
    }


    /**
     * Return the skewed bitmap.
     *
     * @param src The source of bitmap.
     * @param kx  The skew factor of x.
     * @param ky  The skew factor of y.
     * @return the skewed bitmap
     */
    public static Bitmap skew(final Bitmap src, final float kx, final float ky) {
        return skew(src, kx, ky, 0, 0, false);
    }

    /**
     * Return the skewed bitmap.
     *
     * @param src     The source of bitmap.
     * @param kx      The skew factor of x.
     * @param ky      The skew factor of y.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the skewed bitmap
     */
    public static Bitmap skew(final Bitmap src,
                              final float kx,
                              final float ky,
                              final boolean recycle) {
        return skew(src, kx, ky, 0, 0, recycle);
    }

    /**
     * Return the skewed bitmap.
     *
     * @param src The source of bitmap.
     * @param kx  The skew factor of x.
     * @param ky  The skew factor of y.
     * @param px  The x coordinate of the pivot point.
     * @param py  The y coordinate of the pivot point.
     * @return the skewed bitmap
     */
    public static Bitmap skew(final Bitmap src,
                              final float kx,
                              final float ky,
                              final float px,
                              final float py) {
        return skew(src, kx, ky, px, py, false);
    }

    /**
     * Return the skewed bitmap.
     *
     * @param src     The source of bitmap.
     * @param kx      The skew factor of x.
     * @param ky      The skew factor of y.
     * @param px      The x coordinate of the pivot point.
     * @param py      The y coordinate of the pivot point.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the skewed bitmap
     */
    public static Bitmap skew(final Bitmap src,
                              final float kx,
                              final float ky,
                              final float px,
                              final float py,
                              final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);

        if (recycle && !src.isRecycled() && ret != src) {
            src.recycle();
        }

        return ret;
    }


    /**
     * 获取本地资源路径
     *
     * @param localPath
     * @return
     */
    public static String getUrlFromLocalPath(String localPath) {
        if (!TextUtils.isEmpty(localPath) && !localPath.startsWith("file://")) {
            return "file://" + localPath;
        }
        return localPath;
    }

    /**
     * 获取resource下的文件路径
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static String getUrlFromResource(Context context, int resourceId) {
        if (context == null) return "";

        String packageName = context.getPackageName();
        return "android.resource://" + packageName + "/" + resourceId;
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 获取图片宽高
     *
     * @param path
     * @return
     */
    public static String getSizeStr(String path) {
        int width = 0, height = 0, degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            if (degree == 0 || degree == 180) {
                width = exifInterface.getAttributeInt(
                        ExifInterface.TAG_IMAGE_WIDTH, 0);
                height = exifInterface.getAttributeInt(
                        ExifInterface.TAG_IMAGE_LENGTH, 0);
            } else {
                width = exifInterface.getAttributeInt(
                        ExifInterface.TAG_IMAGE_LENGTH, 0);
                height = exifInterface.getAttributeInt(
                        ExifInterface.TAG_IMAGE_WIDTH, 0);
            }
            // Log.d("333", width + "," + height + "," + orientation);

            if (width <= 0 || height <= 0) {
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, option);
                width = option.outWidth;
                height = option.outHeight;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return width + "-" + height;

        }
    }

    public static int getHeightByBitmapRes(Resources resource, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true; // 设置该参数为true后，bitmap是空的，指获取宽高
        BitmapFactory.decodeResource(resource, resId, options);
        return options.outHeight;
    }

    /**
     * 保存Bitmap到文件
     *
     * @param bitmap
     * @param target
     */
    public static void saveBitmap(Bitmap bitmap, File target) {
        if (target.exists()) {
            target.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(target);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Bitmap compress(String imageFile, String targetFile, boolean isSave, boolean qualityCompress, boolean isPng, long maxSize, int targetWidth, int targeHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options); //加载图片信息
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        options.inJustDecodeBounds = false;
        //计算inSampleSize
        int inSampleSize = 1;
        //先根据宽度进行缩小
        while (sourceWidth / inSampleSize > targetWidth) {
            inSampleSize++;
        }
        //然后根据高度进行缩小
        while (sourceHeight / inSampleSize > targeHeight) {
            inSampleSize++;
        }

        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile, options);//加载真正bitmap

        bitmap = compressBitmap(bitmap, false, targetWidth, targeHeight); //等比缩放
        if (qualityCompress) {
            bitmap = compressBitmap(bitmap, true, isPng, maxSize); //压缩质量
        }

        if (isSave) {
            String savePath = imageFile;
            if (!StringUtils.isEmpty(targetFile)) {
                savePath = targetFile;
            }

            saveBitmap(bitmap, new File(savePath));//保存图片
        }

        return bitmap;
    }

    /**
     * 压缩bitmp到目标大小（质量压缩）
     *
     * @param bitmap
     * @param needRecycle
     * @param maxSize
     * @return
     */
    private static Bitmap compressBitmap(Bitmap bitmap, boolean needRecycle, boolean isPng, long maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(isPng ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length > maxSize) {
            baos.reset();//重置baos即清空baos
            bitmap.compress(isPng ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bm = BitmapFactory.decodeStream(isBm, null, null);
        if (needRecycle) {
            bitmap.recycle();
        }
        bitmap = bm;
        return bitmap;
    }

    /**
     * 等比压缩（宽高等比缩放）
     *
     * @param bitmap
     * @param needRecycle
     * @param targetWidth
     * @param targeHeight
     * @return
     */
    private static Bitmap compressBitmap(Bitmap bitmap, boolean needRecycle, int targetWidth, int targeHeight) {
        float sourceWidth = bitmap.getWidth();
        float sourceHeight = bitmap.getHeight();

        float scaleWidth = targetWidth / sourceWidth;
        float scaleHeight = targeHeight / sourceHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight); //长和宽放大缩小的比例
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (needRecycle) {
            if (!(!bitmap.isMutable() && (matrix == null || matrix.isIdentity()))) {
                bitmap.recycle();
            }
        }
        bitmap = bm;
        return bitmap;
    }

    /**
     * 根据path
     *
     * @param path
     * @return
     */
    public final static int getDegress(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转bitmap
     *
     * @param bitmap
     * @param degress     旋转角度
     * @param needRecycle
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress, boolean needRecycle) {
        Matrix m = new Matrix();
        m.postRotate(degress);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        if (needRecycle) {
            if (!(!bitmap.isMutable() && (m == null || m.isIdentity()))) {
                bitmap.recycle();
            }
        }
        return bm;
    }

    /**
     * 图片缩放-尺寸缩放
     *
     * @param imageFile
     * @param targetWidth
     * @param targeHeight
     * @return
     */
    public static Bitmap compressBitmap(String imageFile, boolean isPng, int targetWidth, int targeHeight) {
        return compressBitmap(imageFile, false, isPng, 0L, targetWidth, targeHeight);
    }

    public static Bitmap compressBitmap(String imageFile, boolean qualityCompress, boolean isPng, long maxSize, int targetWidth, int targeHeight) {
        return compress(imageFile, null, false, qualityCompress, isPng, maxSize, targetWidth, targeHeight);
    }

    /**
     * 文件是否不可用(路径为空；文件不存在；文件内容为空)
     */
    private static boolean isInvalidFile(String filePath) {
        try {
            File file = new File(filePath);
            if (TextUtils.isEmpty(filePath) || !file.exists() || file.length() <= 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取图片文件的宽高乘积
     */
    private static int getWidthMultiplyHight(String filePath) {
        int width = 0, height = 0, degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(filePath);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            if (degree == 0 || degree == 180) {
                width = exifInterface.getAttributeInt(
                        ExifInterface.TAG_IMAGE_WIDTH, 0);
                height = exifInterface.getAttributeInt(
                        ExifInterface.TAG_IMAGE_LENGTH, 0);
            } else {
                width = exifInterface.getAttributeInt(
                        ExifInterface.TAG_IMAGE_LENGTH, 0);
                height = exifInterface.getAttributeInt(
                        ExifInterface.TAG_IMAGE_WIDTH, 0);
            }

            if (width <= 0 || height <= 0) {
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, option);
                width = option.outWidth;
                height = option.outHeight;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return width * height;
    }

    /**
     * 通过decodeStream获取图片，以达到解决oom
     *
     * @param drawableId
     * @return
     */
    public static Bitmap getDecodeResource(Resources res, int drawableId) {
        if (drawableId == 0) {
            return null;
        }
        try {
            return BitmapFactory.decodeResource(res, drawableId);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过decodeStream获取bitmap，以达到解决oom
     *
     * @return
     */
    public static Bitmap getSmallCropBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            return null;
        }

        // 如果传递的参数为0则使用以前的处理方式
        if (width <= 0 || height <= 0) {
            return bitmap;
        }

        int bWidth = 0;
        int bHeight = 0;
        try {
            int x = 0, y = 0, imgWidth, imgHight;
            bWidth = bitmap.getWidth();
            bHeight = bitmap.getHeight();
            if (bWidth * 1.0f / width > bHeight * 1.0f / height) {// 应该裁宽度
                imgWidth = bHeight * width / height;
                imgHight = bHeight;
                x = (bWidth - imgWidth) / 2;
            } else {
                imgWidth = bWidth;
                imgHight = bWidth * height / width;
                y = (bHeight - imgHight) / 2;
            }
            // 裁剪
            bitmap = Bitmap.createBitmap(bitmap, x, y, imgWidth, imgHight);
            if (bitmap == null) {
                return null;
            }

            // 缩放
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            // System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap toRoundCorner(Bitmap source, int radius) {
        if (source == null) {
            return null;
        }

        Bitmap result = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP,
                BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;

    }

    /**
     * 裁剪图片
     * Return the clipped bitmap.
     *
     * @param src    The source of bitmap.
     * @param x      The x coordinate of the first pixel.
     * @param y      The y coordinate of the first pixel.
     * @param width  The width.
     * @param height The height.
     * @return the clipped bitmap
     */
    public static Bitmap clip(final Bitmap src, final int x, final int y, final int width, final int height) {
        return clip(src, x, y, width, height, false);
    }

    /**
     * 裁剪图片
     * Return the clipped bitmap.
     *
     * @param src     The source of bitmap.
     * @param x       The x coordinate of the first pixel.
     * @param y       The y coordinate of the first pixel.
     * @param width   The width.
     * @param height  The height.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the clipped bitmap
     */
    public static Bitmap clip(final Bitmap src, final int x, final int y, final int width, final int height, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Bitmap ret = Bitmap.createBitmap(src, x, y, width, height);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }


    /**
     * 得到bitmap的大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (bitmap == null) { //容错bitmap为空时出现异常
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    /**
     * 根据图片路径获取图片宽高
     */
    public static int[] getImageWidthHeight(String path) {
        if (TextUtils.isEmpty(path)) {
            return new int[]{0, 0};
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        return new int[]{options.outWidth, options.outHeight};
    }

    /**
     * 根据图片路径获取文件头信息
     *
     * @param filePath 文件路径
     * @return 文件头信息
     */
    public static String getImageHeader(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);

            CloseUtils.closeIO(is);

            is = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return "";
        }
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            String hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    /**
     * 获取图片格式
     *
     * @return image/png  image/jpeg
     */
    public static String getImageFormat(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options.outMimeType;
    }


}
