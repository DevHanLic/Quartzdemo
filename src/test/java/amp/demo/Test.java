package amp.demo;


import amp.demo.utils.JudgeUtils;
import amp.demo.utils.StringTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang.StringUtils;
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
                "    \"code\": \"0\",\n" +
                "    \"msg\": \"通讯正常返回\",\n" +
                "    \"body\": [\n" +
                "        {\n" +
                "            \"altid\": \"7050743\",\n" +
                "            \"pripid\": \"430104000022018112000296\",\n" +
                "            \"altitem\": \"71\",\n" +
                "            \"Altitem_cn\": \"章程备案\",\n" +
                "            \"altbe\": \"无\",\n" +
                "            \"altaf\": \"2020-07-30新章程\",\n" +
                "            \"altdate\": \"2020-07-30 15:28:56.0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"altid\": \"7050742\",\n" +
                "            \"pripid\": \"430104000022018112000296\",\n" +
                "            \"altitem\": \"70\",\n" +
                "            \"Altitem_cn\": \"高级管理人员备案（董事、监事、经理等）\",\n" +
                "            \"altbe\": \"曾宪可\",\n" +
                "            \"altaf\": \"杨小杰\",\n" +
                "            \"altdate\": \"2020-07-30 15:28:56.0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"altid\": \"7050741\",\n" +
                "            \"pripid\": \"430104000022018112000296\",\n" +
                "            \"altitem\": \"03\",\n" +
                "            \"Altitem_cn\": \"法定代表人变更\",\n" +
                "            \"altbe\": \"曾宪可\",\n" +
                "            \"altaf\": \"杨小杰\",\n" +
                "            \"altdate\": \"2020-07-30 15:28:56.0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"altid\": \"7050740\",\n" +
                "            \"pripid\": \"430104000022018112000296\",\n" +
                "            \"altitem\": \"02\",\n" +
                "            \"Altitem_cn\": \"地址变更\",\n" +
                "            \"altbe\": \"湖南省长沙市岳麓区桔子洲街道潇湘中路298号曙光泊岸锦园1栋601房\",\n" +
                "            \"altaf\": \"湖南省长沙市岳麓区桔子洲街道潇湘中路298号曙光泊岸锦园7栋702房\",\n" +
                "            \"altdate\": \"2020-07-30 15:28:56.0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"altid\": \"7050739\",\n" +
                "            \"pripid\": \"430104000022018112000296\",\n" +
                "            \"altitem\": \"70\",\n" +
                "            \"Altitem_cn\": \"高级管理人员备案（董事、监事、经理等）\",\n" +
                "            \"altbe\": \"曾宪可\",\n" +
                "            \"altaf\": \"杨小杰\",\n" +
                "            \"altdate\": \"2020-07-30 15:28:56.0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"altid\": \"7050738\",\n" +
                "            \"pripid\": \"430104000022018112000296\",\n" +
                "            \"altitem\": \"78\",\n" +
                "            \"Altitem_cn\": \"联络员备案\",\n" +
                "            \"altbe\": \"曾宪可 *** 备案手机：***\",\n" +
                "            \"altaf\": \"杨小杰 *** 备案手机：***\",\n" +
                "            \"altdate\": \"2020-07-30 15:28:56.0\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Map jsonToMap = JSONObject.parseObject(respContent);
        List<Object> list = JSONArray.parseArray(jsonToMap.get("body").toString(), Object.class);
        System.out.println("list" + list.size());
        list.stream().forEach(System.out::println);

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
        CheckParamsBO checkParamBO = new CheckParamsBO();
        checkParamBO.setCoopBusinessType("EM");
        CheckParamsBO checkParamBO1 = new CheckParamsBO();
        checkParamBO1 = null;
        if (JudgeUtils.isNotNull(checkParamBO1) || isCoopBusinessTypeWdc(checkParamBO)) {
            System.out.println("get");
        } else {
            System.out.println("not");
        }
        String dateStr = "Thu Jul 30 15:28:56 CST 2020";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = sdf.parse(dateStr);
        //xxxx-xx-xx
        String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        System.out.println(formatStr);

        //xxxx-xx-xx xx:x:xx
        String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        System.out.println(formatStr2);

        String responseBody = "{\"code\":\"0\",\"msg\":\"通讯正常返回\",\"body\":[]}";
        responseBody = "{\"code\": \"0\", \"msg\": \"通讯正常返回\",\"body\": [{\"altid\": \"7050743\",\"pripid\": \"430104000022018112000296\",\"altitem\": \"71\",\"Altitem_cn\": \"章程备案\",\"altbe\": \"无\",\"altaf\": \"2020-07-30新章程\",\"altdate\": \"2020-07-30 15:28:56.0\"},{\"altid\": \"7050742\",\"pripid\": \"430104000022018112000296\",\"altitem\": \"70\",\"Altitem_cn\": \"高级管理人员备案（董事、监事、经理等）\",\"altbe\": \"曾宪可\",\"altaf\": \"杨小杰\",\"altdate\": \"2020-07-30 15:28:56.0\"} ]}";

        JSONObject responseBodyObj = JSONObject.parseObject(responseBody);
        String body = responseBodyObj.getString("body");

        BigDecimal sumFeeAmt = new BigDecimal(0);
        BigDecimal feeAmt = new BigDecimal(0);
        String costInf = "00#CNY0.0087#1";
        String[] costInfSplit = costInf.split("-");
        int bankCostNum = Integer.parseInt("1");
        for (int i = 0; i < bankCostNum; i++) {
            feeAmt = new BigDecimal(costInfSplit[i].substring(6, costInfSplit[i].length()-3)).setScale(4);
            sumFeeAmt = sumFeeAmt.add(feeAmt).setScale(4);
        }
        System.out.println(sumFeeAmt);

        List<BProCompanyChangeInf> inventoryDTOs = JSON.parseArray(body, BProCompanyChangeInf.class);

        System.out.println("inventoryDTOs" + inventoryDTOs);

        String str1 = "B202012210016|2020122103000000000024261770100|CNY2.00|0120|00|Z2010743000012|C1010611003601|01|6252496555476677-陈定芳||120099|F000|2|00#CNY10000.1000#1-01#CNY0.0200#1|";
        BigDecimal feeAmts = new BigDecimal(0);
        String txamt1 = "00#CNY10000.1000#1";
        String[] str11 = txamt1.split("-");
        for (int i = 0; i<str11.length; i++) {
            feeAmt = new BigDecimal(str11[i].substring(6, str11[i].length()-2)).setScale(4);
            feeAmts = feeAmts.add(feeAmt).setScale(4);
            System.out.println(feeAmts.toString());
        }


        String string = "hello world";

        byte[] bytes = string.getBytes();

        String s21 = new String(bytes);
        List<String> list1 = Arrays.asList(s21);
        System.out.println(list1.size());
        for (String qs : list1){
            System.out.println(qs);
        }
        Integer integer =new Integer("123");
        int aw = 123;
        System.out.println(integer == aw);

        String u = "0";
        if (JudgeUtils.equals(u,"0")){
            System.out.println("123");
        }else{
            System.out.println("456");
        }
    }
    

    private static boolean isCoopBusinessTypeWdc(CheckParamsBO checkParamBO) {
        if (JudgeUtils.equalsAny(checkParamBO.getCoopBusinessType(), "03", "EM")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
