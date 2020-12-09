package amp.demo.controller;

import amp.demo.entity.ScheduleJob;
import amp.demo.annotation.MyBody;
import amp.demo.entity.UserTest;
import amp.demo.mapper.UserTestMapper;
import amp.demo.test.CommonUtils;
import amp.demo.test.TestReflect;
import amp.demo.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HLC
 */
@RestController
@RequestMapping("/v1")
public class ClientInfoController {
    @Autowired
    UserTestMapper userTestMapper;

    @PostMapping("/clientInfo/pageList")
    String pageList(String reqDTO) {
        return "hello";
    }

    @PostMapping("/clientInfo/testNote")
    String testNote(@MyBody ScheduleJob job) {
        String jobName = job.getJobName();
        return jobName;
    }
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
    @PostMapping("/WriteToFile")
    void WriteToFile() throws IOException {
        int[] ints = randomNumber(100000,999999, 24000);
        for (int i = 0; i < 23000; i++) {
        BufferedWriter bw = null ;
        try {
            String contentIvrCup = "D0000000000049185500    00049993    698780 1220101231 6228483038323378775 000000500000 000000000000  00000000000 0200 000000 4814 01080209 918551048140002 395024934198 00 000000 01030000    000000 00 011 000000000007 000000000000 D00000000002 1 000 0 0 0000000000 01030000    0 07 09  00000000000  03000111001   49185500    01030000        1220 32 中国移动                                     156  00000000 D00000015              00000000              00000000              00000000              00000000              00000000              00000000              00000000              00000000              00000000              00000000  00000000  00000000  00000000                        0085202004041258"+ints[i]+"0054911253         0001                                                                                                                                                                                                                                        ";
            String contentCup = "1101;0324141621060225;156000000010000;100006;20200324;0324112712060224;156000000010000;20200324112712060223;49189202;49189202;;;0149189202;UPW0ISS0014918920201049189202W0ISS001202003230000060214;W0ISS001;01;6212143000000000011;;;00000000;;918551041210012;4121;;;99;00000000000;00000000000;156000000000000;156000000000000;和包支付;;;1523;0;20200324141621;411011;1001;111011;;;00000000000;00000000000;;;;;;;";
            String contentEpcc = "B201707270015|20200918"+ints[i]+"00000202191760100|CNY10.0|0110|00|C1010611003601|C1010611003601|";
            File file = new File("C:\\Users\\HLC\\Desktop\\TestIVRCUP.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            bw = new BufferedWriter(fw);
            bw.write(contentIvrCup+"\r\n");
            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bw.close();
        }
        }
    }

    @PostMapping("/test")
    void test() throws Exception {
        /** 批量提交条数 */
        int batchCount = 2;
        String path = "C:\\Users\\HLC\\Desktop\\testTxt.txt";
        List<String> fileList = readFile(path);
        List<UserTest> checkCupDataBOList = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).length() > 0) {
                UserTest userTest = new UserTest();
                try {
                    MessageParsing(userTest, fileList.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                checkCupDataBOList.add(userTest);
                if(checkCupDataBOList.size() >= batchCount || i == fileList.size() - 1 ){
                    System.out.println(checkCupDataBOList.size());
                    System.out.println(checkCupDataBOList.toString());
                    System.out.println(userTestMapper.insertCheckCupData(checkCupDataBOList) != 0 ? "入库成功" : "入库失败");
                    checkCupDataBOList = new  ArrayList<>();
                }
            }
        }

    }

    public void MessageParsing(UserTest userTest, String fieldNames) throws Exception {

        try {
            String[] split = fieldNames.split(";");
            userTest.setId(split[0]);
            userTest.setName(split[1]);
            userTest.setUserNo("".equals(split[2]) ? null : Long.parseLong(split[2]));
//                    userTest.setSex(split[2]);
            float a = 0f;
            a = userTest.getUserNo() == null ? a : Float.parseFloat(split[4].trim());
            userTest.setTxAmt(new BigDecimal(Float.parseFloat(split[1].trim())
                    + ("".equals(split[2]) ? 0f : Float.parseFloat(split[2].trim()) - Float.parseFloat(split[3].trim()))
                    + a));
            userTest.setTmSmp(DateTimeUtils.getCurrentDateTimeStr());
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
            } else if ("class java.lang.Long".equals(f.getGenericType().toString())) {
                f.setAccessible(true);
                if (CommonUtils.isNull(f.get(userTest))) {
                    f.set(userTest, Long.parseLong("0"));
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
                while ((lineContent = br.readLine()) != null) {
                    line++;
                    if (line <= 2) {
                        continue;
                    }
                    fileList.add(lineContent);
                }
                fileList.remove(fileList.size()-1);
                System.out.println(fileList.toString());
            } catch (FileNotFoundException e) {
                System.out.println("------------------no this file---------------");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("----------------io exception-----------------");
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    fileReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }
}
