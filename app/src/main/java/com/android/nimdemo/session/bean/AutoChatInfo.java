package com.android.nimdemo.session.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 单聊模块自动聊天消息体
 *
 * @author devliang
 */

/*public class AutoChatInfo implements Serializable {

    *//**
     * title : 欢迎使用”掌上会计“，请问有什么可以帮助您？
     * chatList : [{"id":"0","actionType":"0","simpleText":" 我在今天购买的是哪个月份的报税服务？","detailText":"以每个月纳税申报的截止日期为界（通常为15日，具体日期以办税日历为准），如果在该日期之后（含）购买本商品，则服务当前月份的报税；反之则服务上月报税。"},{"id":"1","actionType":"0","simpleText":"我有多家公司,需要分别注册吗？","detailText":"注册一个手机用户即可在\u201c我的-全部公司\u201d中新增多家公司，但是付费服务是按每个统一信用代码分别收费的。"},{"id":"2","actionType":"0","simpleText":"纳税人性质发生变更怎么办？","detailText":"可在\u201c我的-企业纳税人性质\u201d进行调整，如果从小规模纳税人变更为一般纳税人，则需要补交差价；如果从一般纳税人变更为小规模纳税人，则系统会自动延长服务期限。注意：纳税人性质变更会清空当前未报税账期内所有已填报的票据，因此请您务必小心纳税人性质不要选错，建议您在确认了纳税人性质后再填报票据。"},{"id":"3","actionType":"0","simpleText":"可以在电脑上访问掌上会计吗？","detailText":"掌上会计目前仅限手机端访问,敬请期待PC端。"}]
     * otherContent : 点击以上问题为您解答,如有其他问题，请编辑发送给我吧！
     *//*

    private String title;
    private String otherContent;
    private List<ChatListBean> chatList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOtherContent() {
        return otherContent;
    }

    public void setOtherContent(String otherContent) {
        this.otherContent = otherContent;
    }

    public List<ChatListBean> getChatList() {
        return chatList;
    }

    public void setChatList(List<ChatListBean> chatList) {
        this.chatList = chatList;
    }

    public static class ChatListBean {
        *//**
         * id : 0
         * actionType : 0
         * simpleText :  我在今天购买的是哪个月份的报税服务？
         * detailText : 以每个月纳税申报的截止日期为界（通常为15日，具体日期以办税日历为准），如果在该日期之后（含）购买本商品，则服务当前月份的报税；反之则服务上月报税。
         *//*

        private String id;
        private String actionType;
        private String simpleText;
        private String detailText;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActionType() {
            return actionType;
        }

        public void setActionType(String actionType) {
            this.actionType = actionType;
        }

        public String getSimpleText() {
            return simpleText;
        }

        public void setSimpleText(String simpleText) {
            this.simpleText = simpleText;
        }

        public String getDetailText() {
            return detailText;
        }

        public void setDetailText(String detailText) {
            this.detailText = detailText;
        }
    }
}*/
