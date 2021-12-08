package amp.demo;

import amp.demo.utils.JudgeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author devzl[zliangchn@126.com]
 * @version V1.0
 * @apiNote FileUtils
 * @date 2019/08/31 17:33 星期六
 */
@Slf4j
public class FileUtils {
    /**
     * 写文件()
     *
     * @param fileContents 文件写入内容
     * @param filePath     文件绝对路径
     * @param append       是否向后追加 true：是，false：否
     * @throws Exception 异常
     */
    public static void writeFile(String fileContents, String filePath, boolean append) throws Exception {
        // 创建文件输出流，第二个参数为是否追加
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        FileWriter fileWriter = new FileWriter(filePath, append);
        // 写入流 Buffer缓存
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            // 写入内容
            bufferedWriter.write(fileContents);
            // 写入换行符，根据不同系统自动兼容不同换行符
            bufferedWriter.newLine();
            // 刷新
            bufferedWriter.flush();
        } catch (Exception e){
            log.error("e:", e);
        } finally {
            // 从里向外关闭操作流
            if (JudgeUtils.isNotNull(bufferedWriter)) {
                bufferedWriter.close();
            }
            if (JudgeUtils.isNotNull(fileWriter)) {
                fileWriter.close();
            }
        }
    }

    /**
     * 写文件()
     *
     * @param fileContents 文件写入内容
     * @param filePath     文件绝对路径
     * @param append       是否向后追加 true：是，false：否
     * @throws Exception 异常
     */
    public static void writeSpdbFile(String fileContents, String filePath, Boolean append) throws Exception {
        // 创建文件输出流，第二个参数为是否追加
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(filePath, append),"GBK");
        // 写入流 Buffer缓存
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        try {
            // 写入内容
            bufferedWriter.write(fileContents);
            // 写入换行符，根据不同系统自动兼容不同换行符
            bufferedWriter.newLine();
            // 刷新
            bufferedWriter.flush();
        } finally {
            // 从里向外关闭操作流
            if (JudgeUtils.isNotNull(bufferedWriter)) {
                bufferedWriter.close();
            }
            if (JudgeUtils.isNotNull(outputStreamWriter)) {
                outputStreamWriter.close();
            }
        }
    }

    /**
     * 按行读取filePath中前lineNo行文件内容
     *
     * @param filePath 文件路径
     * @param lineNo   要读取的行数，为空或小于等于0则读取整个文件
     * @return 读取的文件内容
     * @throws IOException IO异常
     */
    public static String readFileByLine(String filePath, int lineNo) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("filePath:" + filePath);
        }
        boolean compareLine = JudgeUtils.isNotNull(lineNo) && (lineNo > 0);
        BufferedReader read = new BufferedReader(new FileReader(file));
        String tmpString;
        int line = 1;
        try {
            while (JudgeUtils.isNotBlank(tmpString = read.readLine())) {
                stringBuilder.append(tmpString);
                line++;
                if (compareLine && (line > lineNo)) {
                    break;
                }
            }
        }catch (Exception e){
            log.error("e:", e);
        }  finally {
            if (JudgeUtils.isNotNull(read)) {
                read.close();
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 按行读取文件内容，忽略前 beginIgnoreNo 行，后 endIgnoreNo 行
     *
     * @param filePath 文件路径
     * @param beginIgnoreNo 需要忽略的前面行数
     * @param endIgnoreNo 需要忽略的后面行数
     * @return List<> 读取的最终文件内容
     * @throws Exception 异常
     */
    public static List<String> readFileByLineToList(String filePath, int beginIgnoreNo, int endIgnoreNo) throws Exception {
        List<String> fileContents = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("filePath:" + filePath);
        }
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            beginIgnoreNo = beginIgnoreNo < 0 ? 0 : beginIgnoreNo;
            endIgnoreNo = endIgnoreNo < 0 ? 0 : endIgnoreNo;
            int totalReadNo = getFileTotalNum(filePath) - beginIgnoreNo - endIgnoreNo;
            String tmpString;
            int lineNo = 0;
            while (JudgeUtils.isNotBlank(tmpString = read.readLine())) {
                lineNo++;
                // 忽略开始 beginIgnoreNo 行
                if (beginIgnoreNo >= lineNo) {
                    continue;
                }
                // 忽略后面 endIgnoreNo 行
                if (totalReadNo <= fileContents.size()) {
                    break;
                }
                fileContents.add(tmpString);
            }
        }
        return fileContents;
    }

    /**
     * 判断文件是否存在，可使用相对路径
     *
     * @param filePath 文件路径
     * @return true:存在，false:不存在
     * @throws Exception 异常
     */
    public static boolean isExistsFile(String filePath) throws Exception {
        if (JudgeUtils.isBlank(filePath)) {
            throw new Exception("The arg of [filePath] cannot be null.");
        }
        filePath = absolutePath(filePath);
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 若文件路径不是以"/"开头，则为文件添加绝对路径
     *
     * @param fileName 文件路径
     * @return 修改后文件路径
     */
    private static String absolutePath(String fileName) {
        if (!fileName.startsWith(SystemUtils.FILE_SEPARATOR)) {
            //fileName = LemonUtils.getLemonHome() + SystemUtils.FILE_SEPARATOR + fileName;
        }
        return fileName;
    }

    /**
     * 删除文件
     *
     * @param fileName 文件路径
     * @return true:删除成功,false:删除失败
     * @throws Exception 异常信息
     */
    public static boolean deleteFile(String fileName) throws Exception {
        if (JudgeUtils.isBlank(fileName)) {
            throw new Exception("The arg of [fileName] cannot be null.");
        }
        fileName = absolutePath(fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            return true;
        }
        return file.delete();
    }

    /**
     * 获取文件总行数
     *
     * @param filePath 文件路径
     * @return 总行数
     * @throws Exception 异常信息
     */
    public static int getFileTotalNum(String filePath) throws Exception {
        File file = new File(filePath);
        int fileTotalLine;
        try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file))) {
            lineNumberReader.skip(Long.MAX_VALUE);
            // 读取的文件行数比实际少一
            fileTotalLine = lineNumberReader.getLineNumber() + 1;
        }
        return fileTotalLine;
    }

    /**
     * 文件夹不存在则创建
     * @param filePath 文件路径
     */
    public static void createDir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * 获取文件编码格式
     *
     * @param sourceFile 源文件
     * @return 字符编码
     */
    public static String getFileCharset(String sourceFile) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile))) {
            boolean checked = false;
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            int char1 = 0xFF;
            int char2 = 0xFE;
            int char3 = 0xEF;
            int char4 = 0xBB;
            int char5 = 0xBF;

            boolean unicode = first3Bytes[0] == (byte) char1 && first3Bytes[1] == (byte) char2;
            boolean unicodeBigEndian = first3Bytes[0] == (byte) char2 && first3Bytes[1] == (byte) char1;
            boolean utf8 = first3Bytes[0] == (byte) char3 && first3Bytes[1] == (byte) char4 && first3Bytes[2] == (byte) char5;
            // 文件编码为 ANSI
            if (read == -1) {
                return charset;
            }
            // 文件编码为 Unicode
            else if (unicode) {
                charset = "UTF-16LE";
                checked = true;
            }
            // 文件编码为 Unicode big endian
            else if (unicodeBigEndian) {
                charset = "UTF-16BE";
                checked = true;
            }
            // 文件编码为 UTF-8
            else if (utf8) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0) {
                        break;
                    }
                    // 单独出现BF以下的，也算是GBK
                    if (0x80 <= read && read <= char5) {
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        // 双字节 (0xC0 - 0xDF)
                        if (! (0x80 <= read && read <= char5)) {
                            break;
                        }
                    }
                    // 也有可能出错，但是几率较小
                    else if (0xE0 <= read && read <= char3) {
                        read = bis.read();
                        if (0x80 <= read && read <= char5) {
                            read = bis.read();
                            if (0x80 <= read && read <= char5) {
                                charset = "UTF-8";
                                break;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("get file encoding occurred exception, errorInfo: ", e);
        }
        return charset;
    }
}
