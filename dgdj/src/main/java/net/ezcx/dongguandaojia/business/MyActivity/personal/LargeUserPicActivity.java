package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.BeeFramework.activity.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.utils.PreferenceUtil;

/**
 * Created by 秦锦波 on 2016/4/9 0009.
 * 放大用户头像图片
 */
public class LargeUserPicActivity extends BaseActivity {
    private ImageView mUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_user_pic);

        mUserImage=(ImageView)findViewById(R.id.iv_lager_user_pic);
        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .build();
        ImageLoader.getInstance().displayImage(PreferenceUtil.getValue("thumb", LargeUserPicActivity.this), mUserImage, options);
        mUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
