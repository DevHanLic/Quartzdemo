package amp.demo;


import amp.demo.utils.CopyUtils;
import amp.demo.utils.FileUtils;
import amp.demo.utils.JudgeUtils;


import java.io.*;

/**
 * @author han_lic
 * @date 2021/3/18 11:17
 */
public class CheckFileAmountAndTotalNumber {

    public static void main(String[] args) throws Exception {
//        Message();
        //test1();
// /Users/apple_hlc/Desktop/bankFileZilla/20220719_01_AC_NCOMTRX_13
        // test
        File file = new File("/Users/apple_hlc/Desktop/bankFileZilla/20220719_01_AC_NCOMTRX_13");
        int fileTotalLineNum = FileUtils.getFileTotalNum(file);
        System.out.println("fileTotalLineNum" + fileTotalLineNum);
        String fileCharset = FileUtils.getFileCharset(file);
        String lineContent;
        int line = 0;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        inputStreamReader = new InputStreamReader(new FileInputStream(file), fileCharset);
        br = new BufferedReader(inputStreamReader);
        while ((lineContent = br.readLine()) != null) {
            line++;
            if (line > 2) {
                if (line == fileTotalLineNum - 1) {
                    break;
                }
         /*       if (lineContent.startsWith("END")) {
                    break;
                }*/
                System.out.println("lineContent" + lineContent);
            }
        }
    }
    public static void Message() throws Exception {
        File sourceFile = new File("C:\\Users\\HLC\\Desktop\\" + "TestCUP.txt");
        if (!sourceFile.exists()) {
            throw new Exception();
        }
        String fileCharset = FileUtils.getFileCharset(sourceFile);

        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, fileCharset);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 5 * 1024 * 1024);
        String content;
        int lineNo = 0;
        int fileTotalLineNum = FileUtils.getFileTotalNum(sourceFile);
        //文件实际内容行数
        int fileRealLineNum = fileTotalLineNum - 4;
        // 使用readLine方法，一次读一行
        while ((content = bufferedReader.readLine()) != null) {
            lineNo++;
            if (content.startsWith("END")) {
                String number = content.split(";")[1];
                if (fileRealLineNum != Integer.parseInt(number)) {
                    System.out.println(number);
                    throw new Exception();
                }
            }
        }
        System.out.println(fileRealLineNum);
    }

    public static void test() {
        TestUser1 testUser1 = new TestUser1();
        testUser1.setType("1");
        TestUser1.User1 user1 = new TestUser1.User1();
        user1.setTransactionId("2");
        user1.setTransactionId2("3");

        System.out.println(user1.toString());
        TestUser2 testUser2 = new TestUser2();
        TestUser2.User1 testUser21 = new TestUser2.User1();
        testUser21.setTransactionId(user1.getTransactionId());
        testUser21.setTransactionId2(user1.getTransactionId2());
        testUser2.setUser1(testUser21);

        System.out.println(testUser2.toString());
    }

    public static void test1() {
        int lineNo = 3 - 2 - 1;
        String number = "0";
        System.out.println("lineNo" + lineNo);
        System.out.println("number" + number);
        if ((long) lineNo != Long.parseLong(number)) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }

        String b = "18817146643";
        TestUser user = new TestUser();
        user.setA(Long.parseLong(b));
        user.setB(b);
        if (JudgeUtils.notEquals(user.getA(), user.getB())) {
            System.out.println("-----" + 1);
        } else {
            System.out.println("-----" + 2);
        }
        String i = "1&2&3";
        String[] splitJournalInfo = i.split("\\&");
        System.out.println(splitJournalInfo[0]);
        System.out.println(splitJournalInfo[2]);
    }

    /**
     * 获取文件总行数
     *
     * @param file 文件
     * @return 总行数
     * @throws Exception 异常信息
     */
    public static int getFileTotalNum(File file) throws Exception {
        int fileTotalLine = 0;
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            lineNumberReader.skip(Long.MAX_VALUE);
            // 读取的文件行数比实际少一
            fileTotalLine = lineNumberReader.getLineNumber() + 1;
            lineNumberReader.close();
        } catch (IOException e) {
            throw new Exception();
        }
        return fileTotalLine;
    }
}