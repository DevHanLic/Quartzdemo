package amp.demo.service.impl;

import amp.demo.entity.UserTest;
import amp.demo.mapper.UserTestMapper;
import amp.demo.service.UserTestService;
import amp.demo.test.CommonUtils;
import amp.demo.utils.DateTimeUtil;
import amp.demo.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author han_lic
 * @date 2020/12/10 10:55
 */
@Service
public class UserTestServicelmpl implements UserTestService {

    @Autowired
    UserTestMapper userTestMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void deal() {
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
                    System.out.println(checkCupDataBOList.size());
                    if (i == fileList.size() - 1 ){
                        System.out.println(checkCupDataBOList.size());
                        throw new RuntimeException("报错");
                    }
                    checkCupDataBOList.clear();
                }
            }
        }
    }

    @Override
    public void insetUserTest(List<UserTest> checkCupDataBOList) {
        userTestMapper.insertCheckCupData(Optional.ofNullable(checkCupDataBOList).orElse(null));
    }

    @Override
    public void updateUserTest(List<UserTest> checkCupDataBOList) {
        userTestMapper.updateData(Optional.ofNullable(checkCupDataBOList).orElse(null));
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
