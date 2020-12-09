package amp.demo.test;

import amp.demo.entity.UserTest;
import amp.demo.mapper.UserTestMapper;
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

        } catch (Exception e) {
            throw new Exception("Set Object Null Failed!!");
        }
        Field[] fields = userTest.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
//            System.out.println(f.getType().toString());
//            System.out.println(f.getGenericType().toString());
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

    public static void main(String[] args) throws Exception {
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

//
//        TestRequest testRequest1=new TestRequest();
//        Class<? extends TestRequest> aClass1 = testRequest1.getClass();
//        TestRequest testRequest = aClass1.newInstance();
//        Method toString = aClass1.getMethod("toString");
//        Object invoke = toString.invoke(testRequest);
//        System.out.println(invoke);

        TestReflect testReflect = new TestReflect();
        testReflect.fileImportIntoDataBaseBO();
    }
}
