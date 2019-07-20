package com.android.nimdemo.session.extension;

/**
 * 除了系统 sdk 里自带的消息类型 外的消息类型，可以自定义，多段需要统一
 */
public interface CustomAttachmentType {
    // 多端统一
    int Guess = 1;
    int SnapChat = 2;
    int Sticker = 3;
    int RTS = 4;
    int RedPacket = 5;
    int OpenedRedPacket = 6;
}
