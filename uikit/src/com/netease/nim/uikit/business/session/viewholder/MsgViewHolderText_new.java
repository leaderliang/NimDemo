package com.netease.nim.uikit.business.session.viewholder;

import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.emoji.MoonUtil;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.impl.NimUIKitImpl;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderText_new extends MsgViewHolderBase {

    protected TextView bodyTextViewLeft,bodyTextViewRight;

    public MsgViewHolderText_new(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_text_new;
    }

    @Override
    protected void inflateContentView() {
        bodyTextViewLeft = findViewById(R.id.nim_message_item_text_body_left);
        bodyTextViewRight = findViewById(R.id.nim_message_item_text_body_right);
    }

    @Override
    protected void bindContentView() {
        layoutDirection();
        bodyTextViewLeft.setOnClickListener(v -> onItemClick());
        bodyTextViewRight.setOnClickListener(v -> onItemClick());

        MoonUtil.identifyFaceExpression(NimUIKit.getContext(), bodyTextViewLeft, getDisplayText(), ImageSpan.ALIGN_BOTTOM);
        MoonUtil.identifyFaceExpression(NimUIKit.getContext(), bodyTextViewRight, getDisplayText(), ImageSpan.ALIGN_BOTTOM);

        bodyTextViewLeft.setMovementMethod(LinkMovementMethod.getInstance());
        bodyTextViewRight.setMovementMethod(LinkMovementMethod.getInstance());

        bodyTextViewLeft.setOnLongClickListener(longClickListener);
        bodyTextViewRight.setOnLongClickListener(longClickListener);
    }

    private void layoutDirection() {
        if (isReceivedMessage()) {
            bodyTextViewLeft.setVisibility(View.VISIBLE);
            bodyTextViewRight.setVisibility(View.GONE);
            bodyTextViewLeft.setBackgroundResource(NimUIKitImpl.getOptions().messageLeftBackground);
            bodyTextViewLeft.setTextColor(Color.BLACK);
            bodyTextViewLeft.setPadding(ScreenUtil.dip2px(15), ScreenUtil.dip2px(8), ScreenUtil.dip2px(12), ScreenUtil.dip2px(8));


        } else {
            bodyTextViewLeft.setVisibility(View.GONE);
            bodyTextViewRight.setVisibility(View.VISIBLE);
            bodyTextViewRight.setBackgroundResource(NimUIKitImpl.getOptions().messageRightBackground);
            bodyTextViewRight.setTextColor(Color.WHITE);
            bodyTextViewRight.setPadding(ScreenUtil.dip2px(12), ScreenUtil.dip2px(8), ScreenUtil.dip2px(15), ScreenUtil.dip2px(8));

        }
    }

    @Override
    protected int leftBackground() {
        return 0;
    }

    @Override
    protected int rightBackground() {
        return 0;
    }

    protected String getDisplayText() {
        Log.d("MsgViewHolderText", message.getContent());
        return message.getContent();
    }
}
