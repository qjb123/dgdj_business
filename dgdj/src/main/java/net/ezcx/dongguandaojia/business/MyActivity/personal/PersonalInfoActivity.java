package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.RoundedWebImageView;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.O2OMobile.O2OMobileAppConst;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.ezcx.dongguandaojia.business.Application.O2OMobile;
import net.ezcx.dongguandaojia.business.Model.UserBalanceModel;
import net.ezcx.dongguandaojia.business.Model.UserModel;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.user.USER;
import net.ezcx.dongguandaojia.business.bean.user.userchange_avatarResponse;
import net.ezcx.dongguandaojia.business.bean.user.userchange_profileResponse;
import net.ezcx.dongguandaojia.business.bean.user.userprofileResponse;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.receiver.ReceiveType;
import net.ezcx.dongguandaojia.business.utils.PreferenceUtil;
import net.ezcx.dongguandaojia.business.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人资料界面
 */
public class PersonalInfoActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {
    public static final int MODIFER_NICKNAME = 0;//修改昵称
    public static final int MODIFER_MOBILE_PHONE = 1;//修改联系方式
    public static final int MODIFER_NATIVE_PLACE = 2;//修改籍贯
    public static final int MODIFER_ADDRESS = 3;//修改地址

    public static final int MODIFER_AGE = 4;//修改年龄
    public static final int MODIFER_WOKE_EXPERIENCE = 5;//修改工作经验
    public static final int MODIFER_SKILL = 6;//修改技能
    public static final int MODIFER_SERVICE_AREA = 7;//修改服务范围tv_service_area

    public static final int MODIFER_BUSINESS_AREA = 8;//修改常驻商圈tv_business_area
    public static final int MODIFER_SERVICE_TIME = 9;//修改服务时间tv_service_time
    @Bind(R.id.iv_head)
    RoundedWebImageView ivHead;
    @Bind(R.id.ll_head)
    LinearLayout llHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.ll_name)
    LinearLayout llName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.ll_sex)
    LinearLayout llSex;
    @Bind(R.id.tv_native_place)
    TextView tvNativePlace;
    @Bind(R.id.ll_native_place)
    LinearLayout llNativePlace;
    @Bind(R.id.tv_work_experience)
    TextView tvWorkExperience;
    @Bind(R.id.ll_work_experience)
    LinearLayout llWorkExperience;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.ll_age)
    LinearLayout llAge;
    @Bind(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @Bind(R.id.ll_phoneNumber)
    LinearLayout llPhoneNumber;
    @Bind(R.id.tv_job)
    TextView tvJob;
    @Bind(R.id.ll_skill)
    LinearLayout llSkill;
    @Bind(R.id.tv_adress)
    TextView tvAdress;
    @Bind(R.id.ll_adress)
    LinearLayout llAdress;
    @Bind(R.id.tv_service_area)
    TextView tvServiceArea;
    @Bind(R.id.ll_service_area)
    LinearLayout llServiceArea;
    @Bind(R.id.tv_business_area)
    TextView tvBusinessArea;
    @Bind(R.id.ll_business_area)
    LinearLayout llBusinessArea;
    @Bind(R.id.tv_service_time)
    TextView tvServiceTime;
    @Bind(R.id.ll_service_time)
    LinearLayout llServiceTime;
    @Bind(R.id.tv_alterPassw)
    TextView tvAlterPassw;
    @Bind(R.id.ll_alterPassw)
    LinearLayout llAlterPassw;

    private UserBalanceModel userBalanceModel;
    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    private static final int REQUEST_IMAGE = 100;
    private ArrayList<String> mSelectPath;
    private String mImagePath;
    private USER user;
    private UserBalanceModel mUserBalance;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mShared;
    private UserModel userModel;
    private int sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        initData();//从缓存中取数据
        mShared = getSharedPreferences(O2OMobileAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        mUserBalance = new UserBalanceModel(this);
        mUserBalance.addResponseListener(this);
    }

    /**
     * 获取个人资料
     */
    private void initData() {
        userBalanceModel = new UserBalanceModel(PersonalInfoActivity.this);
        userBalanceModel.addResponseListener(this);
        //userBalanceModel.getProfile();
        ImageLoader.getInstance().displayImage(PreferenceUtil.getValue("thumb", getBaseContext()), ivHead, O2OMobile.options_head);
        tvName.setText(PreferenceUtil.getValue("nickname", getBaseContext()));
        if ("0".equals(PreferenceUtil.getValue("gender", getBaseContext()))) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }

        if (PreferenceUtil.getValue("origin", getBaseContext()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("origin", getBaseContext()))) {
            tvNativePlace.setHint("请设置");//籍贯
        }else{
            tvNativePlace.setText(PreferenceUtil.getValue("origin", getBaseContext()));
        }
        if (PreferenceUtil.getValue("age", getBaseContext()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("age", getBaseContext()))|| "0".equals(PreferenceUtil.getValue("age", getBaseContext()))) {
            tvAge.setHint("请设置");
        } else{
            tvAge.setText(PreferenceUtil.getValue("age", getBaseContext()));
        }
        if (PreferenceUtil.getValue("worktime", getBaseContext()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("worktime", getBaseContext()))) {
            tvWorkExperience.setHint("请设置");//工作年限
        } else{
            tvWorkExperience.setText(PreferenceUtil.getValue("worktime", getBaseContext()));
        }

        if (PreferenceUtil.getValue("mobile_phone", getBaseContext()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("mobile_phone", getBaseContext()))) {
            tvPhoneNumber.setHint("请设置");//联系方式
        } else{
            tvPhoneNumber.setText(PreferenceUtil.getValue("mobile_phone", getBaseContext()));
        }

        if (PreferenceUtil.getValue("skill", getBaseContext()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("skill", getBaseContext()))) {
            tvJob.setHint("请设置");//技能
        } else{
            tvJob.setText(PreferenceUtil.getValue("skill", getBaseContext()));
        }
        if (PreferenceUtil.getValue("address", getBaseContext()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("address", getBaseContext()))) {
            tvAdress.setHint("请设置");//家庭住址
        }else{
            tvAdress.setText(PreferenceUtil.getValue("address", getBaseContext()));
        }

        if (PreferenceUtil.getValue("service_area", getBaseContext()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("service_area", getBaseContext()))) {
            tvServiceArea.setHint("请设置");//服务范围
        }else{
            tvServiceArea.setText(PreferenceUtil.getValue("service_area", getBaseContext()));
        }

        if (PreferenceUtil.getValue("businee_circle", getBaseContext()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("businee_circle", getBaseContext()))) {
            tvBusinessArea.setHint("请设置");//常驻商圈
        }else{
            tvBusinessArea.setText(PreferenceUtil.getValue("businee_circle", getBaseContext()));
        }

        if (PreferenceUtil.getValue("service_time", getBaseContext()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("service_time", getBaseContext()))) {
            tvServiceTime.setHint("请设置");//服务时间
        }else{
            tvServiceTime.setText(PreferenceUtil.getValue("service_time", getBaseContext()));
        }
    }

    /**
     * 返回数据
     *
     * @param url
     * @param jo
     * @param status
     * @throws JSONException
     */
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
       if (url.endsWith(ApiInterface.USER_CHANGE_AVATAR)){
            userchange_avatarResponse response = new userchange_avatarResponse();
            response.fromJson(jo);
            user = response.user;
            if (response.succeed == 1) {
                mImageLoader.displayImage(user.avatar.thumb, ivHead, O2OMobile.options_head);
            }
            ToastView toast = new ToastView(this, getString(R.string.change_avatar_success));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            PreferenceUtil.setEdit("thumb", user.avatar.thumb, PersonalInfoActivity.this);
            //发出广播
            Intent dataIntent = new Intent();
            dataIntent.setAction(ReceiveType.UPDATEDATA);
            sendBroadcast(dataIntent);

        } else if (url.endsWith(ApiInterface.USER_CHANGE_PROFILE)) {
            userchange_profileResponse response = new userchange_profileResponse();
            response.fromJson(jo);
            user = response.user;
            if (response.succeed == 1) {
                PreferenceUtil.setEdit("gender",user.gender+"",PersonalInfoActivity.this);
                //发出广播
                Intent dataIntent = new Intent();
                dataIntent.setAction(ReceiveType.UPDATEDATA);
                sendBroadcast(dataIntent);
                if (user.gender == 0) {
                    tvSex.setText("男");
                } else {
                    tvSex.setText("女");
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE && data != null) {
                // 获取返回的图片列表
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    File files = new File(mSelectPath.get(0));
                    //Log.e("获得的图片路径",files+"");
                    if (files.exists()) {
                        userBalanceModel.changeAvatar(files);//发送修改头像的网络请求.
                    }
                }
            } else if (requestCode == MODIFER_NICKNAME && data != null) {
                tvName.setText(data.getStringExtra("name"));
            }else if (requestCode == MODIFER_NATIVE_PLACE && data != null) {
                tvNativePlace.setText(data.getStringExtra("origin"));
            } else if (requestCode == MODIFER_AGE && data != null) {
                tvAge.setText(data.getStringExtra("age"));

            }else if (requestCode == MODIFER_WOKE_EXPERIENCE && data != null) {
                tvWorkExperience.setText(data.getStringExtra("worktime"));
            }else if (requestCode == MODIFER_MOBILE_PHONE && data != null) {
                tvPhoneNumber.setText(data.getStringExtra("phone"));
            } else if (requestCode == MODIFER_SKILL && data != null) {
                tvJob.setText(data.getStringExtra("skill"));
            }else if (requestCode == MODIFER_ADDRESS && data != null) {
                tvAdress.setText(data.getStringExtra("address"));
            }else if (requestCode == MODIFER_SERVICE_AREA && data != null) {
                tvServiceArea.setText(data.getStringExtra("service_area"));
            }else if (requestCode == MODIFER_BUSINESS_AREA && data != null) {
                tvBusinessArea.setText(data.getStringExtra("businee_circle"));
            }else if (requestCode == MODIFER_SERVICE_TIME && data != null) {
                tvServiceTime.setText(data.getStringExtra("service_time"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.iv_head, R.id.ll_head, R.id.ll_name, R.id.tv_sex, R.id.ll_sex, R.id.tv_native_place, R.id.ll_native_place, R.id.tv_work_experience, R.id.ll_work_experience, R.id.tv_age, R.id.ll_age, R.id.tv_phoneNumber, R.id.ll_phoneNumber, R.id.tv_job, R.id.ll_skill, R.id.tv_adress, R.id.ll_adress, R.id.tv_service_area, R.id.ll_service_area, R.id.tv_business_area, R.id.ll_business_area, R.id.tv_service_time, R.id.ll_service_time, R.id.ll_alterPassw})
    public void onClick(View view) {
        Intent intent;
        userModel = new UserModel(PersonalInfoActivity.this);
        userModel.addResponseListener(this);
        switch (view.getId()) {
            case R.id.iv_head:
                break;
            case R.id.ll_head://修改头像
                intent = new Intent(PersonalInfoActivity.this, MultiImageSelectorActivity.class);
// 是否显示调用相机拍照
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
// 默认选择图片,回填选项(支持String ArrayList)
//                intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_RESULT, mSelectPath);
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
            case R.id.ll_name://修改昵称
                intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("title.key", "修改昵称");
                intent.putExtra("value.key", tvName.getText().toString());
//                startActivity(intent1);//修改的时候要用startActivityForResult
                startActivityForResult(intent, MODIFER_NICKNAME);
                break;

            case R.id.ll_sex://修改性别
                new AlertDialog.Builder(this)
                        .setTitle("请选择")
//                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(new String[]{"男", "女"}, 0,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        userModel.changeNickname(tvSex.getText().toString(), "2");//修改性别网络请求
                                        sex = which;
                                        dialog.dismiss();
                                    }
                                }
                        )
                        .setNegativeButton("取消", null)
                        .show();
                break;

            case R.id.ll_native_place://修改籍贯
                intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("value.key", tvNativePlace.getText().toString());
                intent.putExtra("title.key", "修改籍贯");
                startActivityForResult(intent, MODIFER_NATIVE_PLACE);
                //Toast.makeText(PersonalInfoActivity.this, "暂无接口!!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_work_experience:
                intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("value.key", tvWorkExperience.getText().toString());
                intent.putExtra("title.key", "工作经验");
                startActivityForResult(intent, MODIFER_WOKE_EXPERIENCE);
                //Toast.makeText(PersonalInfoActivity.this, "暂无接口!!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_age:
                intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("value.key", tvAge.getText().toString());
                intent.putExtra("title.key", "修改年龄");
                startActivityForResult(intent, MODIFER_AGE);
                //Toast.makeText(PersonalInfoActivity.this, "暂无接口!!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_phoneNumber://修改联系方式
                intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("value.key", tvPhoneNumber.getText().toString());
                intent.putExtra("title.key", "修改电话");
                startActivityForResult(intent, MODIFER_MOBILE_PHONE);
                break;

            case R.id.ll_skill:
                intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("value.key", tvJob.getText().toString());
                intent.putExtra("title.key", "技能");
                startActivityForResult(intent, MODIFER_SKILL);
                //Toast.makeText(PersonalInfoActivity.this, "暂无接口!!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_adress://修改住址
                intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("value.key", tvAdress.getText().toString());
                intent.putExtra("title.key", "修改住址");
                startActivityForResult(intent, MODIFER_ADDRESS);
                break;

            case R.id.ll_service_area://服务范围
                intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("value.key", tvServiceArea.getText().toString());
                intent.putExtra("title.key", "服务范围");
                startActivityForResult(intent, MODIFER_SERVICE_AREA);

                //Toast.makeText(PersonalInfoActivity.this, "暂无接口!!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_business_area://常驻商圈
               /* intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("value.key", tvBusinessArea.getText().toString());
                intent.putExtra("title.key", "常驻商圈");
                startActivityForResult(intent, MODIFER_BUSINESS_AREA);*/

                intent = new Intent(this, ChangZhuSqAty.class);
                startActivityForResult(intent, MODIFER_BUSINESS_AREA);
                break;

            case R.id.ll_service_time:
                intent = new Intent(this, ModifterInfoActivity.class);
                intent.putExtra("value.key", tvServiceTime.getText().toString());
                intent.putExtra("title.key", "服务时间");
                startActivityForResult(intent, MODIFER_SERVICE_TIME);
               //Toast.makeText(PersonalInfoActivity.this, "暂无接口!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_alterPassw://修改密码
                Intent intent7 = new Intent(this, AlterPasswordActivity.class);
//                intent7.putExtra("value.key", tv_alterPassw.getText().toString());
                startActivity(intent7);
                break;
        }
    }
}
