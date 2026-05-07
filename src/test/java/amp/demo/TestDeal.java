package amp.demo;

import amp.demo.utils.JudgeUtils;
import amp.demo.utils.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestDeal {
    @Test
    public void test1(){
        String a = "240e:370:6d05:4560:35fe:1bd9:de61:4af1";
        System.out.println(a.length());
        String refundType = "";
        String refundChannelLevel = "0|1|3|4|5|2|6|7|8|9|";
        Integer defaultRefundLevel =9999;
        //1001100000
        String refundTypeStatus = "1001100000";
        //允许退款次数 RFD_PMT_CNT 2|2|3|0|5|

        String[] split = StringUtils.split("2|2|0|0|0|2|", "|");
        //已退款次数组 RFD_TYP_CNT 0|0|0|0|0|0|0|0|0|0|
        String[] refundTypeCount = StringUtils.split("1|0|0|0|0|0|0|0|0|0|", "|");
        String[] refundChannelLevels = StringUtils.split(refundChannelLevel, "|");
        for (int i = 0; i < refundTypeStatus.length(); i++) {
            String refundTypeEffect = StringUtils.substring(refundTypeStatus, i, i + 1);
            //允许退款次数
            Integer refundAvailableCount = 0;
            //已退款次数
            Integer refundedCount = 0;
            if (JudgeUtils.equals("1", refundTypeEffect)) {
                if (i < split.length && JudgeUtils.isNotBlank(split[i])) {
                    refundAvailableCount = Integer.valueOf(split[i]);
                }
                if (i < refundTypeCount.length && JudgeUtils.isNotBlank(refundTypeCount[i])) {
                    refundedCount = Integer.valueOf(refundTypeCount[i]);
                }
                if (refundAvailableCount.compareTo(refundedCount) > 0) {
                    Integer singleRefundChannel = i < refundChannelLevels.length ? Integer.valueOf(refundChannelLevels[i]) : 9999;
                    if (defaultRefundLevel.compareTo(singleRefundChannel) > 0) {
                        defaultRefundLevel = singleRefundChannel;
                        refundType = String.valueOf(i);
                    }
                }
            }
        }
        System.out.println(refundType);
        /**
         * 姓名脱敏支持生僻字
         * 张𪷿𪷿 --> **𪷿
         * @param input
         * @return
         */
        String str =  "6250947000000097";
        String str1 = "";
        System.out.println(NumberUtil.middle(str));
    }
}
