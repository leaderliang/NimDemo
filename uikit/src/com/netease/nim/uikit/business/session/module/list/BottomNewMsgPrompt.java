package com.netease.nim.uikit.business.session.module.list;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.business.session.emoji.MoonUtil;
import com.netease.nim.uikit.business.session.helper.TeamNotificationHelper;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseFetchLoadAdapter;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * 在当前聊天界面时候，底部显示新来的消息提醒
 * 新消息提醒模块
 *
 * @author devliang
 */
public class BottomNewMsgPrompt {
    // 底部新消息提示条
    private View newMessageTipLayout;
    private TextView newMessageTipTextView;

    private Context context;
    private View view;
    private RecyclerView messageListView;
    private BaseFetchLoadAdapter adapter;
    private Handler uiHandler;
    /**
     * 来的新消息数量
     */
    private int unReadMsgCount = 0;

    public BottomNewMsgPrompt(Context context, View view, RecyclerView messageListView, BaseFetchLoadAdapter adapter,
                              Handler uiHandler) {
        this.context = context;
        this.view = view;
        this.messageListView = messageListView;
        this.adapter = adapter;
        this.uiHandler = uiHandler;
    }


    /**
     * 显示底部新信息提示条
     *
     * @param newMessage
     */
    public void show(IMMessage newMessage) {
        if (newMessageTipLayout == null) {
            init();
        }
        newMessageTipTextView.setText(++unReadMsgCount + "条新消息");
        newMessageTipLayout.setVisibility(View.VISIBLE);

        // 云信默认显示的界面，五秒后消失
        uiHandler.removeCallbacks(showNewMessageTipLayoutRunnable);
        uiHandler.postDelayed(showNewMessageTipLayoutRunnable, 8 * 1000);
    }

    public void onBackPressed() {
        removeHandlerCallback();
    }

    /**
     * 初始化底部新信息提示条
     */
    private void init() {
        ViewGroup containerView = view.findViewById(R.id.message_activity_list_view_container);
        View.inflate(context, R.layout.nim_new_message_bottom_view, containerView);
        newMessageTipLayout = containerView.findViewById(R.id.new_message_tip_layout);
        newMessageTipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageListView.scrollToPosition(adapter.getBottomDataPosition());
                newMessageTipLayout.setVisibility(View.GONE);
                unReadMsgCount = 0;
            }
        });
        newMessageTipTextView = newMessageTipLayout.findViewById(R.id.new_message_tip_text_view);
    }

    private Runnable showNewMessageTipLayoutRunnable = new Runnable() {
        @Override
        public void run() {
            newMessageTipLayout.setVisibility(View.GONE);
            unReadMsgCount = 0;
        }
    };

    private void removeHandlerCallback() {
        if (showNewMessageTipLayoutRunnable != null) {
            uiHandler.removeCallbacks(showNewMessageTipLayoutRunnable);
        }
    }

}
