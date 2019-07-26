package com.android.nimdemo.session.viewholder;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nimdemo.R;
import com.android.nimdemo.session.adapter.AutoChatP2pItemRecyclerAdapter;
import com.netease.nim.uikit.business.session.bean.AutoChatInfo;
import com.netease.nim.uikit.business.session.extension.CustomAutoChatAttachment;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderBase;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * 自动聊天的ViewHolder
 *
 * @author devliang
 */
public class MsgViewHolderAutoChat extends MsgViewHolderBase {
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private TextView tvOtherContent;
    private CustomAutoChatAttachment autoChatAttachment;

    public MsgViewHolderAutoChat(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_auto_chat;
    }

    @Override
    protected void inflateContentView() {
        tvTitle = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerView);
        tvOtherContent = findViewById(R.id.other_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setFocusable(false);
    }

    @Override
    protected void bindContentView() {
//		autoChatAttachment = message.getAttachment() instanceof CustomAutoChatAttachment ? ((CustomAutoChatAttachment) message.getAttachment()) : null;
        autoChatAttachment = (CustomAutoChatAttachment) message.getAttachment();
        if (autoChatAttachment != null && autoChatAttachment.getAutoChatInfo() != null) {
            AutoChatInfo autoChatInfo = autoChatAttachment.getAutoChatInfo();
            String title = autoChatInfo.getTitle();
            String otherContent = autoChatInfo.getOtherContent();
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }
            AutoChatP2pItemRecyclerAdapter adapter = new AutoChatP2pItemRecyclerAdapter(autoChatInfo.getChatList());
            recyclerView.setAdapter(adapter);
            if (!TextUtils.isEmpty(title)) {
                tvOtherContent.setText(otherContent);
            }

        }
    }


}
