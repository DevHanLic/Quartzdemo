package amp.demo;

import amp.demo.entity.UserTest;
import amp.demo.mapper.UserTestMapper;
import amp.demo.utils.DateTimeUtil;
import amp.demo.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestReflect {
    @Autowired
    UserTestMapper userTestMapper;

    private List<Integer> list = new ArrayList<>();

    void demo() {
        String str = "abcdfr";
        System.out.println(str.substring(0, 2));
        System.out.println(list.getClass() + "----------" + list.stream().collect(Collectors.toList()));
        initDemo("1");
        System.out.println(list.getClass() + "----------" + list.stream().collect(Collectors.toList()));
        destoy();
        initDemo("2");
    }

    private void destoy() {
        list.clear();
    }

    private void initDemo(String str) {
        if ("1".equals("1")) {
            List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
            System.out.println(list.getClass() + "initDemo----------" + list.stream().collect(Collectors.toList()));
        } else {
            System.out.println(list.getClass() + "initDemo----------" + list.stream().collect(Collectors.toList()));
        }
    }

    public void fileImportIntoDataBaseBO() throws Exception {
//        "交易类型;交易流水号;交易金额;业务种类;清算日期;原交易流水号;原支付交易金额;订单号;发送机构标识;付款方账户所属机构标识;付款方账户类型;付款方账户;渠道方机构标识;签约协议号;收款方账户所属机构标识;收款方账户类型;收款方账户;保留域1;保留域2;产品类型;产品辅助信息;商户编码;商户类别;二级商户编码;二级商户类别;交易终端类型;网络服务费;品牌费;应付业务参与价;应收业务参与价;商户名称;二级商户名称;发起/接收方保留域;清算信息;交易状态;交易日期时间;业务功能;原交易类型;原业务功能;商户分期实际贴息费率;分期期数;业务推广费;商户分期贴息;网关渠道标识;付款方名称;付款方开户行支付系统行号;付款方开户行支付系统行名称;收款方名称;收款方开户行支付系统行号;收款方开户行支付系统行名称"
        String str = "1.12;2;3;4.3;5;6";
        String path = "C:\\Users\\HLC\\Desktop\\testTxt.txt";
        List<String> fileList = readFile(path);
        List<UserTest>  checkCupDataBOList =new ArrayList<>();
        fileList.stream().forEach(t->{
            if (t.length() > 0) {
                UserTest userTest = new UserTest();
                try {
                    MessageParsing(userTest, t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                checkCupDataBOList.add(userTest);
            }
        });

        System.out.println(checkCupDataBOList.toString());
        System.out.println(userTestMapper.insertCheckCupData(checkCupDataBOList));
        System.out.println(userTestMapper.insertCheckCupData(checkCupDataBOList) == 1 ? "入库成功":"入库失败");
    }

    public void MessageParsing(UserTest userTest, String fieldNames) throws Exception {

        try {
            String[] split = fieldNames.split(";");
            userTest.setId(split[0]);
            userTest.setName(split[1]);
            userTest.setUserNo(Long.parseLong(split[2]));
//                    userTest.setSex(split[2]);
            float a = 0f;
                    a = Float.parseFloat(split[4].trim());

            userTest.setTxAmt(new BigDecimal(String.valueOf(Float.parseFloat(split[1].trim())
                    + (Float.parseFloat(split[2].trim()) - Float.parseFloat(split[3].trim()))
                    + a)));
            userTest.setTmSmp(DateTimeUtils.getCurrentDateTimeStr());
        } catch (Exception e) {
            throw new Exception("Set Object Null Failed!!");
        }
        Field[] fields = userTest.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            System.out.println(f.getType().toString());
            System.out.println(f.getGenericType().toString());
            if ("class java.lang.String".equals(f.getGenericType().toString())) {
                f.setAccessible(true);
                String val = (String) f.get(userTest);
                if (CommonUtils.isNull(val)) {
                    f.set(userTest, " ");
                }
            } else if ("class java.math.BigDecimal".equals(f.getGenericType().toString())) {
                f.setAccessible(true);
                if (CommonUtils.isNull(f.get(userTest))) {
                    f.set(userTest, new BigDecimal(0));
                }
            }
        }
    }
    private List<String> readFile(String filePath) {
        ArrayList<String> fileList = new ArrayList<>();
        File file = new File(filePath);
        FileReader fileReader = null;
        BufferedReader br = null;
        if (file.exists()) {
            try {
                fileReader = new FileReader(file);
                br = new BufferedReader(fileReader);

                String lineContent;
//                if (!((br.readLine()).equals("1.0.0"))){
//                    System.out.println("-读取对账文件失败");
//                }
                int line = 0;
                while ((lineContent = br.readLine()) != null ) {
                    line++;
//                    if(line == 1) {
//                        continue;
//                    }
                    fileList.add(lineContent);
                    System.out.println(fileList.toString());
                }
            } catch (FileNotFoundException e) {
                System.out.println("------------------no this file---------------");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("----------------io exception-----------------");
                e.printStackTrace();
            }finally {
                try {
                    br.close();
                    fileReader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }

    private void test(){
        float SUM_FEE_AMT = 0f;
        for (int i = 1; i < 5; i++) {
            SUM_FEE_AMT = Float.sum(SUM_FEE_AMT ,i) ;
        }
        System.out.println(new BigDecimal(SUM_FEE_AMT));
    }
    /**
     * 功能：产生1-1000中的900个不重复的随机数
     *
     * min:产生随机数的其实位置
     * mab：产生随机数的最大位置
     * n: 所要产生多少个随机数
     *
     */
    public static int[] randomNumber(int min,int max,int n){

        //判断是否已经达到索要输出随机数的个数
        if(n>(max-min+1) || max <min){
            return null;
        }

        int[] result = new int[n]; //用于存放结果的数组

        int count = 0 ;
        while(count <n){
            int num = (int)(Math.random()*(max-min))+min;
            boolean flag = true;
            for(int j=0;j<n;j++){
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }
    private static void test2(){
        String str = "D0000000000049185500    00049993    698780 1220101231 6228483038323378775 000000500000 000000000000  00000000000 0200 000000 4814 01080209 918551048140002 395024934198 00 000000 01030000    000000 00 011 000000000007 000000000000 D00000000002 1 000 0 0 0000000000 01030000    0 07 09  00000000000  03000111001   49185500    01030000        1220 32 中国移动                                 156  00000000 D00000015              00000000              00000000              00000000              00000000              00000000              00000000              00000000              00000000              00000000              00000000  00000000  00000000  00000000                        00852020040412582383070054911253         0001                                                                                                                                                                                                                                        ";
        String str1 ="B201707270015;;CNY2.00;0110;00;C1010611003601;C1010611003601;";
        String[] split = str1.split(";");
        UserTest u =new UserTest();
        u.setName(split[1].trim());
        System.out.println(u);
        BigDecimal demo1 = new BigDecimal(111).add(new BigDecimal("222")).divide(new BigDecimal(100)).setScale(2);
        BigDecimal demo2 = new BigDecimal(111).add(new BigDecimal("222")).divide(new BigDecimal(100),2);
        System.out.println(demo1 +"-----------"+demo2);
//        System.out.println(StrUtils.isBlank(str.substring(10,55))?" ":str.substring(10,55).trim());
    }
    public static void main(String[] args) throws Exception {
//        int[] result = TestReflect.randomNumber(99999,999999, 24000);
//        for(int k =0 ;k<result.length;k++){
//            System.out.println(result[k]);
//        }
        TestReflect.test2();
//        //1
//        Class<?> aClass = Class.forName("amp.demo.aop.TestRequest");
//        System.out.println(aClass);
//
//        Object o = aClass.newInstance();
//        System.out.println(o);
//        Method toString = aClass.getMethod("toString");
//
//        toString.invoke(o);
//
//        //2
//        Class<TestResponse> testRequestClass = TestResponse.class;
//        System.out.println(testRequestClass);
//        TestResponse testRequest = testRequestClass.newInstance();
//        System.out.println(testRequest);
//        Method toString1 = testRequestClass.getMethod("toString");
//        toString1.invoke(testRequest);


//        TestRequest testRequest1=new TestRequest();
//        Class<? extends TestRequest> aClass1 = testRequest1.getClass();
//        TestRequest testRequest = aClass1.newInstance();
//        Method toString = aClass1.getMethod("toString");
//        Object invoke = toString.invoke(testRequest);
//        System.out.println(invoke);

//        TestReflect testReflect = new TestReflect();
//        testReflect.test();
    }
}
