package com.android.nimdemo.session.viewholder;

import android.util.Log;

import com.android.nimdemo.session.extension.DefaultCustomAttachment;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderText;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 *
 * 文本类型
 * @author devliang
 */
public class MsgViewHolderDefCustom extends MsgViewHolderText {

    public MsgViewHolderDefCustom(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected String getDisplayText() {
        DefaultCustomAttachment attachment = (DefaultCustomAttachment) message.getAttachment();
        Log.d("MsgViewHolderDefCustom", "type: " + attachment.getType() + ", data: " + attachment.getContent());
        return "type: " + attachment.getType() + ", data: " + attachment.getContent();
    }
}
