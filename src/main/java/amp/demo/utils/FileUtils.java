package amp.demo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;

/**
 * @author devzl[zliangchn@126.com]
 * @version V1.0
 * @apiNote FileUtils
 * @date 2021/01/22 15:52 周五
 */
public class FileUtils {
    /**
     * 获取文件总行数
     *
     * @param file 文件
     * @return 总行数
     * @throws Exception 异常信息
     */
    public static int getFileTotalNum(File file) throws Exception {
        int fileTotalLine;
        try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file))) {
            lineNumberReader.skip(Long.MAX_VALUE);
            // 读取的文件行数比实际少一
            fileTotalLine = lineNumberReader.getLineNumber() + 1;
        }
        return fileTotalLine;
    }

    public static void writeToFile(String content, File fileName) throws Exception {
        FileWriter fileWriter = new FileWriter(fileName, true);
        BufferedWriter buffWriter = new BufferedWriter(fileWriter);
        buffWriter.write(content);
        buffWriter.newLine();
        buffWriter.flush();
        buffWriter.close();
    }

    /**
     * 获取文件编码格式
     *
     * @param sourceFile 源文件
     *
     * @return 字符编码
     */
    public static String getFileCharset(File sourceFile) {
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
            int char6 = 2;
            // 文件编码为 ANSI
            if (read == -1) {
                return charset;
            }
            // 文件编码为 Unicode
            else if (first3Bytes[0] == (byte) char1 && first3Bytes[1] == (byte) char2) {
                charset = "UTF-16LE";
                checked = true;
            }
            // 文件编码为 Unicode big endian
            else if (first3Bytes[0] == (byte) char2 && first3Bytes[1] == (byte) char1) {
                charset = "UTF-16BE";
                checked = true;
            }
            // 文件编码为 UTF-8
            else if (first3Bytes[0] == (byte) char3 && first3Bytes[1] == (byte) char4 && first3Bytes[char6] == (byte) char5) {
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
                    if (0x80 <= read && read <= 0xBF) {
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        // 双字节 (0xC0 - 0xDF)
                        if (! (0x80 <= read && read <= 0xBF)) {
                            break;
                        }
                    }
                    // 也有可能出错，但是几率较小
                    else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
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
            return charset;
        }
        return charset;
    }
}
