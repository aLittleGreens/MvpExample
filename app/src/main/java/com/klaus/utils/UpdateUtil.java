package com.klaus.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;


import com.klaus.App;
import com.klaus.BuildConfig;
import com.klaus.api.Api;
import com.klaus.common.commonutil.ToastUitl;

import org.reactivestreams.Subscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/1/27 0027.
 */

public class UpdateUtil {
    private static final String TAG = "UpdateUtils";
    private static final int PROGRESS_CHANGE = 0;
    private static final int UPDATE_DOWNLOAD_SUCCESS = 1;
    private static final int DOWNLOAD_FAIL = 2;
    private static final int HSOW_UPDATE_DOWNLOAD_DIALOG = 3;
    private static final int NO_NET = 5;
    private static final int UNKNOWN_ERROR = 6;
    private static ProgressDialog pd;
    private static boolean isShowToast;
    private static Handler handler;
    private static String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "es100" + File.separator + "download";
    private static String fileName;
    private static AlertDialog mAlertDialog;
    private static boolean isUploading;

//    /**
//     * 检查更新Dialog
//     *
//     * @param appUpgradeParams 更新信息
//     */
//    public static void showUpdateDialog(Context context, final UpdateResponse appUpgradeParams,
//                                        int oldVersion) {
//        initHandler(context);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("检测到新版本可更新");
//        builder.setMessage(String.format("新版版本号:%s\n当前版本号:%s\n\n更新日志:\n%s",
//                appUpgradeParams.data.version, oldVersion + "",
//                TextUtils.isEmpty(appUpgradeParams.data.description) ? "无" : appUpgradeParams.data.description));
//        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Message msg = handler.obtainMessage();
//                msg.arg1 = Integer.parseInt(appUpgradeParams.data.file.size);
//                msg.what = HSOW_UPDATE_DOWNLOAD_DIALOG;
//                handler.sendMessage(msg);
//                fileName = context.getString(R.string.app_name) + ".apk";
//                downloadFile(appUpgradeParams.data.file.fileUrl, context);
//                mAlertDialog = null;
//            }
//        });
//        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                mAlertDialog = null;
//            }
//        });
//
//        if (mAlertDialog == null) {
//            mAlertDialog = builder.create();
//        }
//
//        if (!mAlertDialog.isShowing()) {
//            mAlertDialog.show();
//        }
//    }
    private static void downloadFile(String fileUrl, Context context) {
        Log.i(TAG, "开始下载,请求api = " + fileUrl);
        Api.getInstance()
                .downloadFile(fileUrl)
                .subscribeOn(Schedulers.io())

                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        saveFile(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "访问网络出错： ", e);
                        handler.sendEmptyMessage(DOWNLOAD_FAIL);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static void saveFile(ResponseBody body) {
        Log.i(TAG, "saveFile");
        InputStream is = null;
        byte[] buf = new byte[2048];
        FileOutputStream fos = null;
        // 限速标志(单位:Kb/s)
//        int limitSpeed = AppConfig.getInstance().getInt(Const.UPDATE_APK_LIMIT, 0);
//        MLog.i(TAG, "limitSpeed: " + limitSpeed);
        try {
            is = body.byteStream();
            // 创建文件
            File file1 = new File(rootPath);
            if (!file1.exists()) {
                file1.mkdirs();
            }

            File file = new File(rootPath, fileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            // 每次读取的byte数
            int byteRead;
            // 总共读取的文件大小
            int byteSum = 0;

            long prevTime = System.currentTimeMillis();
            while ((byteRead = is.read(buf)) != -1) {

                byteSum += byteRead;
                fos.write(buf, 0, byteRead);

                Message msg = handler.obtainMessage();
                msg.arg2 = byteSum;
                msg.what = PROGRESS_CHANGE;
                handler.sendMessage(msg);

                // 限速
                //当前时间
                long currentTime = System.currentTimeMillis();
                int speed = 0;
                if (currentTime - prevTime > 0) {
                    // 当前下载速率
                    speed = (int) (byteSum / (currentTime - prevTime));
                    Log.i(TAG, "当前的下载速度：" + speed);
                }
//                if(limitSpeed > 0 && speed > limitSpeed) {
//                    int sleepTime = (int) (byteSum / limitSpeed + prevTime - currentTime);
//                    MLog.i(TAG, "sleepTime: " + sleepTime);
//                    Thread.sleep(sleepTime);
//                }
            }
            fos.flush();
            // 安装
            Message msg = handler.obtainMessage();
            msg.what = UPDATE_DOWNLOAD_SUCCESS;
            msg.obj = file;
            handler.sendMessage(msg);
        } catch (Exception e) {
            //下载失败
            Log.e(TAG, TAG, e);
            handler.sendEmptyMessage(DOWNLOAD_FAIL);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ignored) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    private static void initHandler(final Context context) {
        handler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HSOW_UPDATE_DOWNLOAD_DIALOG:
                        isUploading = true;
                        pd = new ProgressDialog(context);
                        // 必须一直下载完，不可取消
                        pd.setCancelable(false);
                        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pd.setTitle("版本升级");
                        pd.setMessage("正在下载升级文件");
                        pd.setProgressNumberFormat("%1d Kb/%2d Kb");
                        pd.setMax(msg.arg1 / 1024);
                        pd.show();
                        break;
                    case NO_NET:
                        if (pd != null) {
                            pd.dismiss();
                        }
                        isUploading = false;
                        ToastUitl.showShort("网络不可用，请检查网络!");
                        break;
                    case UNKNOWN_ERROR:
                        if (pd != null) {
                            pd.dismiss();
                            pd = null;
                        }
                        isUploading = false;
                        ToastUitl.showShort("未知错误");
//                        UIHelper.getInstance().toastMessageTop("未知错误");
                        break;
                    case DOWNLOAD_FAIL:
                        if (pd != null) {
                            pd.dismiss();
                            pd = null;
                        }
                        isUploading = false;
                        ToastUitl.showShort("下载失败");
//                        UIHelper.getInstance().toastMessageTop("下载失败");
                        break;
                    case PROGRESS_CHANGE:
                        if (pd != null) {
                            pd.setProgress(msg.arg2 / 1024);
                        }
                        break;
                    case UPDATE_DOWNLOAD_SUCCESS:
                        // 结束掉进度条对话框
                        pd.dismiss();
                        pd = null;
                        isUploading = false;
                        //判断是否是AndroidN以及更高的版本
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", (File) msg.obj);
                            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                        } else {
                            intent.setDataAndType(Uri.fromFile((File) msg.obj), "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        context.startActivity(intent);
                        break;
                    default:
                        break;

                }
            }
        };
    }
}
