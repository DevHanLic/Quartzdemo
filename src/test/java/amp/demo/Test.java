package amp.demo;

import amp.demo.utils.JudgeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author han_lic
 * @date 2021/7/15 14:53
 */
public class Test {
    public static void main(String[] args) throws ParseException {

        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse("20210127")));
        System.out.println(LocalDate.parse("20210127", DateTimeFormatter.ofPattern("yyyyMMdd")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        System.out.println("---"+sdf.format());
//        String a = "";
//        String b = " ";
//        String c = null;
//        System.out.println(JudgeUtils.isNull(a));
//        System.out.println(JudgeUtils.isNull(b));
//        System.out.println(JudgeUtils.isNull(c));
        TestUser testUser = new TestUser();
//        BigDecimal showSaleAmount = new BigDecimal("0");
//        testUser.setMerchantBonusAmount(new BigDecimal("50"));
//        testUser.setCouponAmt("50");
//        BigDecimal merchantBonusAmount = JudgeUtils.isNull(testUser.getMerchantBonusAmount()) ? new BigDecimal("0") : testUser.getMerchantBonusAmount();
//        BigDecimal couponAmt = JudgeUtils.isNull(testUser.getCouponAmt()) ? new BigDecimal("0") : new BigDecimal(testUser.getCouponAmt());
//
//        showSaleAmount = showSaleAmount.add(merchantBonusAmount).add(couponAmt);
//        System.out.println(showSaleAmount);
//
//        if (JudgeUtils.isNotNull(user.getBankUserName())) {
//            System.out.println(1);
//        }
//        testUser.setUser(user);
//        System.out.println(testUser.toString());
//
//        String y = "";
//        if (JudgeUtils.isNotNull(y)&&JudgeUtils.isNotBlank(y)){
//            System.out.println(1);
//        }else{
//            System.out.println(2);
//        }
        User user = User.builder().build();
//        user.setBankUserName("666");
        testUser.setUser(user);
        TestUserdemo testUserdemo = new TestUserdemo();
        testUserdemo.setUser(testUser.getUser());
        if (JudgeUtils.isNotNull(testUserdemo.getUser())) {
            UserResp userResp = new UserResp();
            BeanUtils.copyProperties(userResp, testUserdemo.getUser());
        }
        String str = "新环境部署 phone-loan-business-app";
        System.out.println(str.split(" ")[1]);
        BigDecimal provinceBonusAmount = new BigDecimal("0.00");
        BigDecimal bonusAmount = new BigDecimal("8.00");
        provinceBonusAmount = AmountUtils.add(provinceBonusAmount, bonusAmount);
        System.out.println(provinceBonusAmount);

        AutoGetMobileCacheBO autoGetMobileCacheBO = new AutoGetMobileCacheBO();
        autoGetMobileCacheBO.setAutoGetMobileNoFlag(true);
        if (JudgeUtils.isNull(autoGetMobileCacheBO) || !autoGetMobileCacheBO.isAutoGetMobileNoFlag()) {
            System.out.println(1);
            System.out.println(autoGetMobileCacheBO.isAutoGetMobileNoFlag());
        } else {
            System.out.println(2);
            System.out.println(autoGetMobileCacheBO.isAutoGetMobileNoFlag());
        }
        TestUser testUser1 = new TestUser();
        Long mobileNo = 0l;
        System.out.println(testUser1.toString());
        System.out.println(String.valueOf(testUser1.getMobileNo()).equals("0"));
        testUser1.setMobileNo(String.valueOf(mobileNo));
        System.out.println(testUser1.toString());

        List<String> listT = Arrays.asList("1", "4", "5");
        String a = "1";

//        for (int i = 0; i < listT.size(); i++) {
//            System.out.println(i);
//            if (JudgeUtils.equals(a, listT.get(i))) {
//                System.out.println("yy");
//                return;
//            }
//        }

        String s = "1;2;;";
        String[] split = s.split(";", -1);
        System.out.println(split[2]);


        String respContent = "{\n" +
                "\t\"respCd\": \"0000\",\n" +
                "\t\"respContent\": \"{\\\"transTp\\\":\\\"01\\\",\\\"discountAmt\\\":\\\"0\\\",\\\"orderId\\\":\\\"01240220211126174500690100000042\\\",\\\"appName\\\":\\\"1\\\",\\\"refundAt\\\":\\\"0\\\",\\\"traceNo\\\":\\\"353652\\\",\\\"accNo\\\":\\\"622638****0095\\\",\\\"settleAmt\\\":\\\"80000\\\",\\\"instalTransInfo\\\":\\\"{numberOfInstallments=06}\\\",\\\"settleDate\\\":\\\"20211126\\\",\\\"transSt\\\":\\\"00\\\",\\\"deviceInfo\\\":\\\"\\\",\\\"mchntOrderId\\\":\\\"2021112674320057008006\\\",\\\"subTransTp\\\":\\\"0124\\\",\\\"traceTime\\\":\\\"1126174500\\\",\\\"currency\\\":\\\"156\\\",\\\"bankNm\\\":\\\"HXBANK\\\",\\\"refundCnt\\\":\\\"0\\\",\\\"transAt\\\":\\\"80000\\\"}\",\n" +
                "\t\"respMsg\": \"查询交易成功\",\n" +
                "\t\"seqId\": \"0380775395992064\",\n" +
                "\t\"sign\": \"DbyYFc+8vpeI5N+4ZG7zjQOoJmiYuVlt/1FOTAl9ZkJnWfTk/zj5wKKHbjTCjiMLIIPPvgcEcLJaOtQ7sWnRm6NA20eiI4epiNSljFCV11zNjpyx7m2md0wQmEGFBpBRs+INv3iL6E6WW5A74BSXvZPzbgBdvUkaDGzwtZw/FNyVgkCqkOqtZOtIoG02HrF+XyvpdhrHvIyYdRUDzV3AASr3B3GDeYayNpaOAxeTZhsfiiicZW1oR9pInaDuO5RER7DruvORjhht+DztclvU7Jhv6odlFWbqeN26o+y3Yv4+HyhJBVSNG+a+i9BFRW7nqH+lG9sAJvRwxDrrZ8Uu2Q==\",\n" +
                "\t\"timestamp\": \"20211126204827\",\n" +
                "\t\"version\": \"1.0.2\"\n" +
                "}\n";

        Map jsonToMap = JSONObject.parseObject(respContent);
        System.out.println("reqData=" + jsonToMap.toString());
        System.out.println("instalTransInfo"+JSONObject.parseObject(jsonToMap.get("respContent").toString()).get("instalTransInfo").toString());




//        Map map = new HashMap();
//        map.put("a", "a");
//        map.put("b", "b");
//        String string = JSON.toJSONString(map);
//        System.out.println("string" + string);
//        Map jsonToMap = JSON.parseObject(string);
//        System.out.println("reqData=" + jsonToMap.toString());
        BigDecimal constant = new BigDecimal(10000);
        Double s8 = 3214434000d;
        System.out.println(new BigDecimal(s8).divide(constant).setScale(2, RoundingMode.HALF_UP));


    }
}
