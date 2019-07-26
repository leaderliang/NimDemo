package com.netease.nim.uikit.business.session.extension;


import com.alibaba.fastjson.JSONObject;
import com.netease.nim.uikit.business.session.bean.AutoChatInfo;
import com.netease.nim.uikit.common.util.string.JsonFormat;

/**
 * @author devliang
 * @date 2017/9/18
 *  AutoChatInfo autoChatInfo = JsonFormat.JsonToObject(JsonFormat.getJson("auto_chat_data.json"), AutoChatInfo.class);
 * 自动聊天的自定义消息(仅单聊)
 */

public class CustomAutoChatAttachment extends CustomAttachment {
    private AutoChatInfo autoChatInfo;

    public CustomAutoChatAttachment() {
        super(CustomAttachmentType.AUTO_CHAT);
    }

    public CustomAutoChatAttachment(String data) {
        this();
        autoChatInfo = JsonFormat.JsonToObject(data, AutoChatInfo.class);
    }

    @Override
    protected void parseData(JSONObject data) {
        try {
            autoChatInfo = new AutoChatInfo();
            autoChatInfo = JsonFormat.JsonToObject(data.toJSONString(), AutoChatInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject packData() {
        try {
            return JSONObject.parseObject(JSONObject.toJSONString(autoChatInfo));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AutoChatInfo getAutoChatInfo() {
        return autoChatInfo;
    }
}
