package com.android.nimdemo.session.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.nimdemo.R;
import com.android.nimdemo.session.bean.AutoChatInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author devliang
 * @date 2017/9/19
 * 单聊的自动聊天的recyclerView的item
 */
public class AutoChatP2pItemRecyclerAdapter extends BaseQuickAdapter<AutoChatInfo.ChatListBean, BaseViewHolder> {


    public AutoChatP2pItemRecyclerAdapter(@Nullable List<AutoChatInfo.ChatListBean> data) {
        super(R.layout.nim_message_item_auto_chat_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AutoChatInfo.ChatListBean item) {
        helper.setText(R.id.item_simple_text, item.getSimpleText());
        helper.addOnClickListener(R.id.item_simple_text);
    }


}
