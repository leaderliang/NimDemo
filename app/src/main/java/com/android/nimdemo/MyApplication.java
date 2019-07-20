package com.android.nimdemo;

import android.app.Application;
import android.text.TextUtils;

import com.android.nimdemo.config.preference.Preferences;
import com.android.nimdemo.event.DemoOnlineStateContentProvider;
import com.android.nimdemo.mixpush.DemoPushContentProvider;
import com.android.nimdemo.session.SessionHelper;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.UIKitOptions;
import com.netease.nim.uikit.business.contact.core.util.ContactHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;


/**
 * @author devliang
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(this));

        // 以下逻辑只在主进程初始化时执行
        if (NIMUtil.isMainProcess(this)) {
            // 初始化UIKit模块
            initUIKit();
        }

    }

    private void initUIKit() {
        // 初始化
        NimUIKit.init(this, buildUIKitOptions());


        // IM 会话窗口的定制初始化。
        SessionHelper.init();

        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        NimUIKit.setCustomPushContentProvider(new DemoPushContentProvider());
        // 展示在线状态
        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }

    private UIKitOptions buildUIKitOptions() {
        UIKitOptions options = new UIKitOptions();
        // 设置app图片/音频/日志等缓存目录
        options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(this) + "/app";
        return options;
    }

    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

}





