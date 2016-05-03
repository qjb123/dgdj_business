package net.ezcx.dongguandaojia.business.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.MyDialog;
import com.BeeFramework.view.RoundedWebImageView;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.insthub.O2OMobile.O2OMobileAppConst;
import com.insthub.O2OMobile.SESSION;
import com.nostra13.universalimageloader.core.ImageLoader;
import net.ezcx.dongguandaojia.business.Application.MessageConstant;
import net.ezcx.dongguandaojia.business.Application.O2OMobile;
import net.ezcx.dongguandaojia.business.Model.UserBalanceModel;
import net.ezcx.dongguandaojia.business.MyActivity.personal.AboutActivity;
import net.ezcx.dongguandaojia.business.MyActivity.personal.B0_SigninActivity;
import net.ezcx.dongguandaojia.business.MyActivity.personal.ChangZhuSqAty;
import net.ezcx.dongguandaojia.business.MyActivity.personal.FeedbackActivity;
import net.ezcx.dongguandaojia.business.MyActivity.personal.LargeUserPicActivity;
import net.ezcx.dongguandaojia.business.MyActivity.personal.MyEvaluateActivity;
import net.ezcx.dongguandaojia.business.MyActivity.personal.PersonalInfoActivity;
import net.ezcx.dongguandaojia.business.MyActivity.personal.QingjiaAty;
import net.ezcx.dongguandaojia.business.MyActivity.wallet.MyWalletActivity;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.base.BaseFragment;
import net.ezcx.dongguandaojia.business.bean.user.USER;
import net.ezcx.dongguandaojia.business.bean.user.usersignoutResponse;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.receiver.ReceiveType;
import net.ezcx.dongguandaojia.business.utils.PreferenceUtil;
import net.ezcx.dongguandaojia.business.utils.StringUtil;
import net.ezcx.dongguandaojia.business.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeFragment extends BaseFragment implements BusinessResponse {
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvProvince)
    TextView tvProvince;
    @Bind(R.id.tvAge)
    TextView tvAge;
    @Bind(R.id.tvSkill)
    TextView tvSkill;
    @Bind(R.id.llSetting)
    LinearLayout llSetting;
    @Bind(R.id.llMyWallet)
    LinearLayout llMyWallet;
    @Bind(R.id.llMyEvaluate)
    LinearLayout llMyEvaluate;
    @Bind(R.id.llToLeave)
    LinearLayout llToLeave;
    @Bind(R.id.llFeedback)
    LinearLayout llFeedback;
    @Bind(R.id.llAboutUs)
    LinearLayout llAboutUs;
    @Bind(R.id.im_icon)
    RoundedWebImageView imIcon;
    @Bind(R.id.exit_login)
    LinearLayout exitLogin;
    @Bind(R.id.im_sex)
    ImageView imSex;
    @Bind(R.id.skill)
    LinearLayout skill;

    private UserBalanceModel userBalanceModel;
    private UserBalanceModel mUserBalance;
    private USER user;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mShared;

    //广播
    private ReceiveBroadCast receiveBroadCast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_layout, container, false);
        ButterKnife.bind(this, view);

        mShared = getActivity().getSharedPreferences(O2OMobileAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        mUserBalance = new UserBalanceModel(getActivity());
        mUserBalance.addResponseListener(this);



        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ReceiveType.UPDATEDATA);
        getActivity().registerReceiver(receiveBroadCast, filter);
        return view;
    }

    /**
     * 初始化个人资料
     */
    @Override
    public void initData() {
        if(!"".equals(PreferenceUtil.getValue("thumb", getActivity()))){
            ImageLoader.getInstance().displayImage(PreferenceUtil.getValue("thumb", getActivity()), imIcon, O2OMobile.options_head);
            tvName.setText(PreferenceUtil.getValue("nickname", getActivity()));
        }

        if ("0".equals(PreferenceUtil.getValue("gender", getActivity()))) {
            imSex.setBackgroundResource(R.drawable.nan);
        } else {
            imSex.setBackgroundResource(R.drawable.nv);
        }

        if (PreferenceUtil.getValue("address", getActivity()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("address", getActivity()))) {
            tvProvince.setVisibility(View.INVISIBLE);
        }else{
            tvProvince.setVisibility(View.VISIBLE);
            tvProvince.setText(PreferenceUtil.getValue("address", getActivity()));
        }

        if (PreferenceUtil.getValue("age", getActivity()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("age", getActivity()))|| "0".equals(PreferenceUtil.getValue("age", getActivity()))) {
            tvAge.setVisibility(View.INVISIBLE);
        }else{
            tvAge.setVisibility(View.VISIBLE);
            tvAge.setText(PreferenceUtil.getValue("age", getActivity()));
        }

        if (PreferenceUtil.getValue("skill", getActivity()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("skill", getActivity()))) {
            skill.setVisibility(View.INVISIBLE);
        }else{
            skill.setVisibility(View.VISIBLE);
            tvSkill.setText(PreferenceUtil.getValue("skill", getActivity()));
        }

    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.im_icon,R.id.tvName, R.id.tvProvince, R.id.tvAge, R.id.tvSkill, R.id.llSetting, R.id.llMyWallet, R.id.llMyEvaluate, R.id.llToLeave, R.id.llFeedback, R.id.llAboutUs, R.id.exit_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_icon:
                //点击小图像跳转到大图片观看
                Intent innt = new Intent(getActivity(), LargeUserPicActivity.class);
                startActivity(innt);
                break;
            case R.id.tvName:
                break;
            case R.id.tvProvince:
                break;
            case R.id.tvAge:
                break;
            case R.id.tvSkill:
                break;
            case R.id.llSetting://设置
                Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.llMyWallet://我的钱包
                Intent intent4 = new Intent(getActivity(), MyWalletActivity.class);
                startActivity(intent4);
                break;
            case R.id.llMyEvaluate://我的评价
                Intent intent5 = new Intent(getActivity(), MyEvaluateActivity.class);
                startActivity(intent5);
                break;
            case R.id.llToLeave:
                Intent intent1 = new Intent(getActivity(), QingjiaAty.class);
                startActivity(intent1);
                break;
            case R.id.llFeedback://意见反馈
                Intent intent2 = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent2);
                break;
            case R.id.llAboutUs://关于我们
                Intent intent3 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent3);
                break;
            case R.id.exit_login://退出
                //登出
                final MyDialog myDialog = new MyDialog(getActivity(), "是否注销该账号?");
                myDialog.show();
                myDialog.positive.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mUserBalance.signout();//发送登出账号的请求
                        myDialog.dismiss();
                    }
                });
                myDialog.negative.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }

                });
               /* Intent intent6 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent6);*/
                break;
        }
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.USER_SIGNOUT)) {
            usersignoutResponse response = new usersignoutResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                mEditor.putBoolean("isLogin", false);
                mEditor.putString("user", "");
                mEditor.putInt("uid", 0);
                mEditor.putString("sid", "");
                mEditor.commit();
                SESSION.getInstance().uid = mShared.getInt("uid", 0);
                SESSION.getInstance().sid = mShared.getString("sid", "");
                ToastView toast = new ToastView(getActivity(), getString(R.string.logout_success));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Message msg = new Message();
                msg.what = MessageConstant.LOGINOUT;
                EventBus.getDefault().post(msg);
                Intent intent = new Intent(getActivity(), B0_SigninActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                ToastView toast = new ToastView(getActivity(), "退出失败");
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }


    class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ReceiveType.UPDATEDATA)) {
                //searchDiamon();
                if (PreferenceUtil.getValue("id", context) != null && !PreferenceUtil.getValue("id", context).equals("")) {
                    //收到广播修改用户信息
                    ImageLoader.getInstance().displayImage(PreferenceUtil.getValue("thumb", getActivity()), imIcon, O2OMobile.options_head);
                    tvName.setText(PreferenceUtil.getValue("nickname", getActivity()));
                    if ("0".equals(PreferenceUtil.getValue("gender", getActivity()))) {
                        imSex.setBackgroundResource(R.drawable.nan);
                    } else {
                        imSex.setBackgroundResource(R.drawable.nv);
                    }

                    if (PreferenceUtil.getValue("address", getActivity()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("address", getActivity()))) {
                        tvProvince.setVisibility(View.INVISIBLE);
                    }else{
                        tvProvince.setVisibility(View.VISIBLE);
                        tvProvince.setText(PreferenceUtil.getValue("address", getActivity()));
                    }

                    if (PreferenceUtil.getValue("age", getActivity()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("age", getActivity()))) {
                        tvProvince.setVisibility(View.INVISIBLE);
                    }else{
                        tvProvince.setVisibility(View.VISIBLE);
                        tvAge.setText(PreferenceUtil.getValue("age", getActivity()));
                    }

                    if (PreferenceUtil.getValue("skill", getActivity()) == null || StringUtil.isEmpty(PreferenceUtil.getValue("skill", getActivity()))) {
                        skill.setVisibility(View.INVISIBLE);
                    }else{
                        tvSkill.setVisibility(View.VISIBLE);
                        tvSkill.setText(PreferenceUtil.getValue("skill", getActivity()));
                    }

                } else {
                    ToastUtil.getNormalToast(context,
                            "获取用户信息失败");
                }
            }
        }
    }


}
