package com.android.nimdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.nimdemo.config.preference.Preferences;
import com.android.nimdemo.config.preference.UserPreferences;
import com.android.nimdemo.net.BaseResponse;
import com.android.nimdemo.net.LoginBean;
import com.android.nimdemo.net.NimAccountInfo;
import com.android.nimdemo.net.NimUser;
import com.android.nimdemo.net.RetrofitClient;
import com.android.nimdemo.session.SessionHelper;
import com.bumptech.glide.Glide;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private String TAG = this.getClass().getSimpleName();

    AbortableFuture<LoginInfo> loginRequest;

    private final int BASIC_PERMISSION_REQUEST_CODE = 110;

    private String mAccid;

    private String mToken;
    private String mToChatAccid;
    EditText etUserName,
            etUserToken,
            etChatUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestBasicPermission();

        ImageView iv = findViewById(R.id.iv_test);
        etUserName = findViewById(R.id.et_user_name);
        etUserToken = findViewById(R.id.et_user_token);
        etChatUserId = findViewById(R.id.et_chat_user_id);


        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556783439129&di=a4273466763cf63bcd9932d9f691186f&imgtype=0&src=http%3A%2F%2Ffile.youboy.com%2Fd%2F153%2F19%2F92%2F7%2F813577s.jpg").into(iv);


        findViewById(R.id.bt_login).setOnClickListener(v -> doLogin());

        findViewById(R.id.bt_p2p_chat).setOnClickListener(v -> doChat());

        findViewById(R.id.bt_clear).setOnClickListener(v -> doClear());

        requestServer();
    }

    private void requestServer() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "17600850539");
        params.put("accid", "17600850539");
        params.put("userType", "4");
        params.put("channel", "App");

        NimUser nimUser = new NimUser("17610840539", "17610840539", 4, "App");
        RetrofitClient.getInstance().getApiService().createNimAccountId(nimUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<NimAccountInfo>>() {
                    @Override
                    public void accept(BaseResponse<NimAccountInfo> nimAccountInfoBaseResponse) throws Exception {
                        if (nimAccountInfoBaseResponse != null) {
                            NimAccountInfo nimAccountInfo = nimAccountInfoBaseResponse.getData();
                            if (nimAccountInfo == null) {
                                Log.d("MainActivity", " createNimAccountId  nimAccountInfo == null");
                                return;
                            }
                            mAccid = nimAccountInfo.getAccid();
                            mToken = nimAccountInfo.getToken();
                            ToastHelper.showToastLong(MainActivity.this, " getAccid " + mAccid + " getToken" + mToken);
                            if (!TextUtils.isEmpty(mAccid)) {
                                requestAllotNimAccountId(mAccid);
                            }
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastHelper.showToastLong(MainActivity.this, throwable.getMessage());
                    }
                });
    }

    private void requestAllotNimAccountId(String accid) {
        RetrofitClient.getInstance().getApiService().allotNimAccountId(accid, 4, "APP", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<NimAccountInfo>>() {
                    @Override
                    public void accept(BaseResponse<NimAccountInfo> nimAccountInfoBaseResponse) throws Exception {
                        if (nimAccountInfoBaseResponse != null) {
                            NimAccountInfo nimAccountInfo = nimAccountInfoBaseResponse.getData();
                            if (nimAccountInfo == null) {
                                Log.d("MainActivity", " requestAllotNimAccountId  nimAccountInfo == null");
                                return;
                            }
                            mToChatAccid = nimAccountInfo.getAccid();
                            ToastHelper.showToastLong(MainActivity.this, " mToChatAccid " + mToChatAccid);
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastHelper.showToastLong(MainActivity.this, throwable.getMessage());
                    }
                });
    }

    /**
     * 基本权限管理
     * 需要提前申请
     */
    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private void requestBasicPermission() {
        MPermission.with(MainActivity.this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    private void doClear() {
        Preferences.clear();
    }

    private void doChat() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            mToChatAccid = etChatUserId.getText().toString();
            if(TextUtils.isEmpty(mToChatAccid)){
                Toast.makeText(this, "请输入聊天对象 ID",Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "已登录，准备打开单聊！",Toast.LENGTH_SHORT).show();
//            SessionHelper.startP2PSession(MainActivity.this, "liangyy");

            SessionHelper.startP2PSession(MainActivity.this, mToChatAccid);

//            SessionHelper.startP2PSession(MainActivity.this, "17610840539");

        } else {
            ToastHelper.showToast(this, "请先登录！");
        }
    }

    public void doLogin() {
        mAccid = etUserName.getText().toString();
        mToken = etUserToken.getText().toString();
        if (TextUtils.isEmpty(mAccid) || TextUtils.isEmpty(mToken)) {
            Toast.makeText(this, "请输入用户名或 token",Toast.LENGTH_SHORT).show();
            return;
        }

        DialogMaker.showProgressDialog(this, null, "登录中...", true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (loginRequest != null) {
                    loginRequest.abort();
                    onLoginDone();
                }
            }
        }).setCanceledOnTouchOutside(false);
        // 云信只提供消息通道，并不包含用户资料逻辑。开发者需要在管理后台或通过服务器接口将用户帐号和token同步到云信服务器。
        // 在这里直接使用同步到云信服务器的帐号和token登录。
        // 这里为了简便起见，demo就直接使用了密码的md5作为token。
        // 如果开发者直接使用这个demo，只更改appkey，然后就登入自己的账户体系的话，需要传入同步到云信服务器的token，而不是用户密码。
//        final String account = "liangyq";
//        final String token = "123456";

//        final String account = "liangyy";
//        final String token = "123456";

//        final String account = "liuxiaoxue15";
//        final String token = "5d683f74267fd8d9c696922e609be512";

        final String account = mAccid;
        final String token = mToken;

//        final String token = tokenFromPassword("123456");
       /* RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        LogUtil.i(TAG, "login success");
                    }

                    @Override
                    public void onFailed(int code) {
                        LogUtil.i(TAG, "登录失败: " + code);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LogUtil.i(TAG, "onException" + throwable);
                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(new LoginInfo(account, token, appkey)).setCallback(callback);*/


        // 登录
        loginRequest = NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtil.i(TAG, "login success");
                onLoginDone();
                DemoCache.setAccount(account);
                saveLoginInfo(account, token);
                // 初始化消息提醒配置
                initNotificationConfig();
                // 进入主界面
                ToastHelper.showToast(MainActivity.this, "登录成功");
//                finish();
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    ToastHelper.showToast(MainActivity.this, "账号或密码错误");
                    LogUtil.i(TAG, "账号或密码错误");
                } else {
                    ToastHelper.showToast(MainActivity.this, "登录失败: " + code);
                    LogUtil.i(TAG, "登录失败: " + code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                ToastHelper.showToast(MainActivity.this, "无效输入");
                LogUtil.i(TAG, "无效输入");
                onLoginDone();
            }
        });
    }

    private void onLoginDone() {
        loginRequest = null;
        DialogMaker.dismissProgressDialog();
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    //DEMO中使用 username 作为 NIM 的account ，md5(password) 作为 token
    //开发者需要根据自己的实际情况配置自身用户系统和 NIM 用户系统的关系
    private String tokenFromPassword(String password) {
        String appKey = readAppKey(this);
        boolean isDemo = "ace4ef5cf95f4bfb14ce5f4501b10a02".equals(appKey) ||
                "fe416640c8e8a72734219e1847ad2547".equals(appKey);

        return isDemo ? MD5.getStringMD5(password) : password;
    }

    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.android.nimdemo.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }

}
