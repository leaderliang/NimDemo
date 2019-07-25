package com.android.nimdemo.session.action;

import com.android.nimdemo.R;
import com.android.nimdemo.session.extension.CustomAutoChatAttachment;
import com.android.nimdemo.utils.JsonFormat;
import com.netease.nim.uikit.business.session.actions.BaseAction;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Tip类型消息测试
 *
 * @author devliang
 */
public class TipAction extends BaseAction {

    public TipAction() {
        super(R.drawable.message_plus_tip_selector, R.string.input_panel_tip);
    }

    @Override
    public void onClick() {
        /*IMMessage msg = MessageBuilder.createTipMessage(getAccount(), getSessionType());
        msg.setContent("一条Tip测试消息");

        CustomMessageConfig config = new CustomMessageConfig();
        // 不推送
        config.enablePush = false;
        msg.setConfig(config);

        sendMessage(msg);*/

        String data = JsonFormat.getJson("auto_chat_data.json");
         CustomAutoChatAttachment attachment= new CustomAutoChatAttachment(data);
        IMMessage imMessage = MessageBuilder.createCustomMessage(getAccount(), getSessionType(), attachment);
        imMessage.setContent(data);
//        imMessage.setStatus(MsgStatusEnum.success);
        CustomMessageConfig config = new CustomMessageConfig();
        // 不推送
        config.enablePush = false;
        config.enableHistory = false;
        imMessage.setConfig(config);
        imMessage.setDirect(MsgDirectionEnum.Out);

        sendMessage(imMessage);
    }
}