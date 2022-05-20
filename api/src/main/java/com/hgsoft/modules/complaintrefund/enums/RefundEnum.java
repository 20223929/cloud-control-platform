package com.hgsoft.modules.complaintrefund.enums;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 投诉相关枚举
 * Created by 吴鉴武 on 2021/5/7 14:42
 */
public interface RefundEnum {

    String DEFAULT_DESC = "未知";

    /** 退费渠道枚举 */
    enum Channel{

//        FRONT_SYSTEM(1,"前端系统确认"),
        BEHIND_SYSTEM(1,"后台自动退费"),
        OPERATOR_INPUT(2,"业务员录入"),
        APPLETS_APPLY(3,"小程序申请"),
        REALTIME_REFUND(4,"在线实时退费")
        ;
        @Getter
        private Integer code;
        @Getter
        private String name;
        Channel(Integer code,String name){
            this.code = code;
            this.name = name;
        }

        /**
         * 获取申请渠道名称
         * @param code
         * @return
         */
        public static String getNameByCode(Integer code){
            if(code == null) return null;
            for (Channel channel : Channel.values()) {
                if(channel.code.equals(code)) return channel.name;
            }
            return DEFAULT_DESC;
        }
    }

    /** 是否退费枚举 */
    enum RefundFlag{

        REFUND(1,"是"),
        NO_REFUND(2,"否");

        @Getter
        private Integer code;
        @Getter
        private String desc;
        RefundFlag(Integer code,String desc){
            this.code = code;
            this.desc = desc;
        }

        /**
         * 获取是否退费描述
         * @param code
         * @return
         */
        public static String getDescByCode(Integer code){
            if(code == null) return null;
            for (RefundFlag refundFlag : RefundFlag.values()) {
                if(refundFlag.code.equals(code)) return refundFlag.desc;
            }
            return DEFAULT_DESC;
        }
    }

    /** 退费状态枚举 */
    enum RefundStatus{
        WAIT_REFUND(0,"待退费"),
        REFUNDING(1,"退费中"),
        REFUND_SUCCESS(2,"退费成功"),
        REFUND_FAIL(3,"退费失败"),
        NOT_REFUND(4,"不退费");

        @Getter
        private Integer code;
        @Getter
        private String desc;
        RefundStatus(Integer code,String desc){
            this.code = code;
            this.desc = desc;
        }

        /**
         * 获取退费状态描述
         * @param code
         * @return
         */
        public static String getDescByCode(Integer code){
            if(code == null) return null;
            for (RefundStatus refundStatus : RefundStatus.values()) {
                if(refundStatus.code.equals(code)) return refundStatus.desc;
            }
            return DEFAULT_DESC;
        }
    }

    /** 退费形式枚举 */
    enum RefundWayType{
        UNIONPAY_REFUND(1,"银联线上退费","银联"),
        MERCHANT_REFUND(2,"商户线下退费","ETC"),
        NETWOTK_CENTER_REFUND(3,"联网中心退费","ETC");

        @Getter
        private Integer code;
        @Getter
        private String desc;
        @Getter
        private String tradeMode;
        RefundWayType(Integer code,String desc,String tradeMode){
            this.code = code;
            this.desc = desc;
            this.tradeMode = tradeMode;
        }

        /**
         * 获取退费形式描述
         * @param code
         * @return
         */
        public static String getDescByCode(Integer code){
            if(code == null) return null;
            for (RefundWayType refundWayType : RefundWayType.values()) {
                if(refundWayType.code.equals(code)) return refundWayType.desc;
            }
            return DEFAULT_DESC;
        }
        /**
         * 获取交易模式
         * @param code
         * @return
         */
        public static String getTradeModeByCode(Integer code){
            if(code == null) return null;
            for (RefundWayType refundWayType : RefundWayType.values()) {
                if(refundWayType.code.equals(code)) return refundWayType.tradeMode;
            }
            return DEFAULT_DESC;
        }
    }

    /** 退费申请处理结果枚举 */
    enum RefundApplyHandlerResult{
        DRAFT(0,"草稿","待处理"),
        WAIT_AUDIT(1,"待审核","是"),
        DONE_AUDIT(2,"已审核","否");

        @Getter
        private Integer code;
        @Getter
        private String desc;
        @Getter
        private String refundFlagDesc;
        RefundApplyHandlerResult(Integer code,String desc,String refundFlagDesc){
            this.code = code;
            this.desc = desc;
            this.refundFlagDesc = refundFlagDesc;
        }

        /**
         * 获取退费形式描述
         * @param code
         * @return
         */
        public static String getDescByCode(Integer code){
            if(code == null) return null;
            for (RefundApplyHandlerResult refundApplyHandlerResult : RefundApplyHandlerResult.values()) {
                if(refundApplyHandlerResult.code.equals(code)) return refundApplyHandlerResult.desc;
            }
            return DEFAULT_DESC;
        }

        /**
         * 获取退费标志描述
         * @param code
         * @return
         */
        public static String getRefundFlagDescByCode(Integer code){
            if(code == null) return null;
            for (RefundApplyHandlerResult refundApplyHandlerResult : RefundApplyHandlerResult.values()) {
                if(refundApplyHandlerResult.code.equals(code)) return refundApplyHandlerResult.refundFlagDesc;
            }
            return DEFAULT_DESC;
        }
    }
}
