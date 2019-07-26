package com.android.nimdemo.session.extension;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

/**
 * @author devliang
 */
public class CustomAttachParser implements MsgAttachmentParser {

    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";


    public static String packData(int type, JSONObject data) {
        JSONObject object = new JSONObject();
        object.put(KEY_TYPE, type);
        if (data != null) {
            object.put(KEY_DATA, data);
        }

        return object.toJSONString();
    }

    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;
        try {
            JSONObject object = JSON.parseObject(json);
            if (object.containsKey(KEY_TYPE)) {
                int type = object.getInteger(KEY_TYPE);
                JSONObject data = object.getJSONObject(KEY_DATA);
                switch (type) {
//                case CustomAttachmentType.Guess:
//                    attachment = new GuessAttachment();
//                    break;
//                case CustomAttachmentType.SnapChat:
//                    return new SnapChatAttachment(data);
//                case CustomAttachmentType.Sticker:
//                    attachment = new StickerAttachment();
//                    break;
//                case CustomAttachmentType.RTS:
//                    attachment = new RTSAttachment();
//                    break;
//                case CustomAttachmentType.RedPacket:
//                    attachment = new RedPacketAttachment();
//                    break;
//                case CustomAttachmentType.OpenedRedPacket:
//                    attachment = new RedPacketOpenedAttachment();
//                    break;
                    case CustomAttachmentType.AUTO_CHAT:
                        attachment = new CustomAutoChatAttachment();
                        break;
                    default:
                        attachment = new DefaultCustomAttachment();
                        break;
                }

                if (attachment != null) {
                    attachment.fromJson(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return attachment;
    }

}
