package net.ezcx.dongguandaojia.business.Application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.BeeFramework.AppConst;
import com.BeeFramework.activity.DebugCancelDialogActivity;
import com.BeeFramework.activity.DebugTabActivity;
import com.external.activeandroid.app.Application;
import com.insthub.O2OMobile.O2OMobileAppConst;

import net.ezcx.dongguandaojia.business.R;

import com.insthub.O2OMobile.SESSION;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.PlatformConfig;

public class BeeFrameworkApp extends Application implements OnClickListener {
    private static BeeFrameworkApp instance;
    private ImageView bugImage;
    public Context currContext;

    private WindowManager wManager;
    private boolean flag = true;

    public Handler messageHandler;

    public static BeeFrameworkApp getInstance() {
        if (instance == null) {
            instance = new BeeFrameworkApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        //Todo
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("4039875342", "319e6aae52223bb1f3d5d5327d6a6d83");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105215561", "22TQ6Puya4gkMVJf");
        // QQ和Qzone appid appkey
       /* PlatformConfig.setAlipay("2015111700822536");
        //支付宝 appid
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        //易信 appkey
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        //Twitter appid appkey
        PlatformConfig.setPinterest("1439206");
        //Pinterest appid
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        //来往 appid appkey*/
        initConfig();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + AppConst.LOG_DIR_PATH;
//        File storePath = new File(path);
//        storePath.mkdirs();
//        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(
//                path, null));

        initImageLoader(this);
//        CrashHandler.getInstance().init(currContext,path, SplashActivity.class);//错误日志初始化.

    }

    void initConfig() {
        SharedPreferences shared;
        shared = this.getSharedPreferences(O2OMobileAppConst.USERINFO, 0);
        SESSION.getInstance().uid = shared.getInt("uid", 0);
        SESSION.getInstance().sid = shared.getString("sid", "");

    }

    public void showBug(final Context context) {
        BeeFrameworkApp.getInstance().currContext = context;
        wManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        LayoutParams wmParams = new LayoutParams();
        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL |
                LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        wmParams.x = 0;
        wmParams.y = 0;

        wmParams.width = LayoutParams.WRAP_CONTENT;
        wmParams.height = LayoutParams.WRAP_CONTENT;

        if (bugImage != null) { //判断bugImage是否存在，如果存在则移除，必须加在 new ImageView(context)
            wManager.removeView(bugImage);
        }

        bugImage = new ImageView(context);
        bugImage.setImageResource(R.drawable.bug);
        wManager.addView(bugImage, wmParams);
        bugImage.setOnClickListener(this);

        bugImage.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                DebugCancelDialogActivity.parentHandler = messageHandler;
                Intent intent = new Intent(BeeFrameworkApp.getInstance().currContext, DebugCancelDialogActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                flag = false;
                return false;
            }
        });

        messageHandler = new Handler() {

            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    wManager.removeView(bugImage);
                    bugImage = null; // 必须要把bugImage清空，否则再次进入debug模式会与102行冲突
                }
            }
        };
    }

    public void onClick(View v) {
        if (flag != false) {
            Intent intent = new Intent(BeeFrameworkApp.getInstance().currContext, DebugTabActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        flag = true;
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(2)
                .threadPoolSize(2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.wri zteDebugLogs() // Remove for release app
                .memoryCache(new WeakMemoryCache())
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


}
