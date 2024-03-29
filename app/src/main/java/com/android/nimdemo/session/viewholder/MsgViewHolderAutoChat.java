package com.android.nimdemo.session.viewholder;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nimdemo.R;
import com.android.nimdemo.session.adapter.AutoChatP2pItemRecyclerAdapter;
import com.netease.nim.uikit.business.session.bean.AutoChatInfo;
import com.netease.nim.uikit.business.session.extension.CustomAutoChatAttachment;
import com.netease.nim.uikit.business.session.helper.MessageListPanelHelper;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderBase;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

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

            adapter.setOnItemChildClickListener((adapter1, view, position) -> {
                AutoChatInfo.ChatListBean chatListData = (AutoChatInfo.ChatListBean) adapter1.getData().get(position);
//                    ToastHelper.showToastLong(context, chatListData.getDetailText());
                // 创建一个文本消息
                IMMessage imMessage = MessageBuilder.createTextMessage("liangyy", SessionTypeEnum.P2P, chatListData.getSimpleText());
                MessageListPanelHelper.getInstance().notifyAddMessage(imMessage);


                NIMClient.getService(MsgService.class).sendMessage(imMessage, false).setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        ToastHelper.showToastLong(context, "onSuccess");

                        IMMessage imMessage = MessageBuilder.createTextMessage("liangyy", SessionTypeEnum.P2P, chatListData.getDetailText());
                        CustomMessageConfig config = new CustomMessageConfig();
                        // 不推送
                        config.enablePush = false;
                        // 该消息是否要保存到服务器，如果为false，通过MsgService.pullMessageHistory(IMMessage, int, boolean)拉取的结果将不包含该条消息。
                        config.enableHistory = false;
                        imMessage.setConfig(config);
                        imMessage.setDirect(MsgDirectionEnum.In);
                        imMessage.setStatus(MsgStatusEnum.success);
                        MessageListPanelHelper.getInstance().notifyAddMessage(imMessage);
                    }

                    @Override
                    public void onFailed(int i) {
//                        ToastHelper.showToastLong(context, "onFailed " + i);
                    }

                    @Override
                    public void onException(Throwable throwable) {
//                        ToastHelper.showToastLong(context, "throwable " + throwable);
                    }
                });
            });

            if (!TextUtils.isEmpty(title)) {
                tvOtherContent.setText(otherContent);
            }

        }
    }


}
