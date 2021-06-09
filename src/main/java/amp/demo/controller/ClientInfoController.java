package amp.demo.controller;

import amp.demo.BO.SftpConfigBO;
import amp.demo.config.SftpConstants;
import amp.demo.encryption.HSMClientUtils;
import amp.demo.entity.ScheduleJob;
import amp.demo.annotation.MyBody;
import amp.demo.entity.UserTest;
import amp.demo.mapper.UserTestMapper;
import amp.demo.service.SftpService;
import amp.demo.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
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
    @Autowired
    UserTestService userTestService;
    @Autowired
    SftpService sftpService;

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
    void test(){
       userTestService.deal();
    }

    @PostMapping("/testEncryption")
    void testEncryption(){
        String str1 = "151110784x1";
        String str2 = "15556566";
        System.out.println("(纯数字)加密数据"+ HSMClientUtils.encrtyptASCII(str1));
        System.out.println("(纯数字)jie密数据"+ HSMClientUtils.decryptASCII(str1));
    }

    @PostMapping("/clientInfo/add")
    int add(String reqDTO) {
        List<UserTest> checkCupDataBOList = new ArrayList<>();
        return 1;
    }

    @PostMapping("/sftp/test")
    void sftpTest() {
        SftpConfigBO sftpConfigBO = new SftpConfigBO();
        sftpConfigBO.setSystemType(SftpConstants.SYSTEM_TYPE_OF_APPLICATION);
        sftpConfigBO.setSystemChannel(SftpConstants.SYSTEM_CHANNEL_OF_PWM);
        sftpConfigBO.setDownloadFileName("pv-1.6.6.tar.gz");
        sftpConfigBO.setLocalFileName("C:\\Users\\HLC\\Desktop\\test\\pv-1.6.6.tar.gz");
        sftpConfigBO.setFileType(SftpConstants.FILE_TYPE_OF_DATA);
        sftpConfigBO.setServerUrl("sftp://192.168.184.128:22");
        sftpConfigBO.setObjectiveDirectory("/home/elk");
        sftpConfigBO.setUserPassword("root");
        sftpConfigBO.setUserName("root");
//        sftpConfigBO.setServerDirectory(bankCheckFileBO.getCheckFileDate());
        sftpService.download(sftpConfigBO);
    }
}
