package amp.demo;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class testPoiMain {
    public static void main(String[] args) throws Exception {

        File file = new File("/Users/apple_hlc/Desktop/dev/dfpay/app/dfpay-app/src/main/java/com/cmpay/dfpay/controller");
        //获取当前目录下的所有文件名字
        List<String> list = getFile(file);
        List<String> strings = new ArrayList<>();
        for (String s : list) {
            FileInputStream in = new FileInputStream(new File(s));
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
            BufferedReader bufReader = new BufferedReader(inReader);
            String line = null;
            String sub="";
            int i = 1;
            while ((line = bufReader.readLine()) != null) {
                sub+=line;
                i++;
            }
            strings.add(sub.replace(" ", ""));
        }
        List<testPoi> user=new ArrayList<>();
        for (String string : strings) {
            int i1 = string.indexOf("@RequestMapping(\"");
            String substring = string.substring(i1+17);
            int i2 = substring.indexOf("\"");
            String substring1 = substring.substring(0, i2);
            String[] split = string.split("@PostMapping\\(\"");
            for (int i = 0; i < split.length; i++) {
                if(i>0){
                    testPoi u=new testPoi();
                    u.setName1(substring1 + split[i].split("\"\\)")[0]);
                    String notes = split[i].split("\"\\)")[1].split("notes")[1].split("\\,")[0].replace("\"", "").replace("=", "");
                    u.setName2(notes);
                    u.setName3("POST");
                    user.add(u);
                }
            }
        }
        for (String string : strings) {
            int i1 = string.indexOf("@RequestMapping(\"");
            String substring = string.substring(i1+17);
            int i2 = substring.indexOf("\"");
            String substring1 = substring.substring(0, i2);
            String[] split = string.split("@GetMapping\\(\"");
            for (int i = 0; i < split.length; i++) {
                if(i>0){
                    testPoi u=new testPoi();
                    u.setName1(substring1 + substring+split[i].split("\"\\)")[0]);
                    String notes = split[i].split("\"\\)")[1].split("notes")[1].split("\\,")[0].replace("\"", "").replace("=", "");
                    u.setName2(notes);
                    u.setName3("GET");
                    user.add(u);
                }
            }
        }
        getExcel(user, "/Users/apple_hlc/Desktop/work/接口注解梳理/dfpay.xlsx");
    }

    private static List<String> getFile(File file) {
        List<String> list = new ArrayList<>();
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            //如果是文件
            if (array[i].isFile()) {
                list.add(array[i].getAbsolutePath());
            }
        }
        return list;
    }


    private static void getExcel(List<testPoi> list, String fileName) throws IOException {
        List<String> header = Arrays.asList("Mapping", "notes","request");
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        for (int j = 0; j < header.size(); j++) {
            Cell cell = row.createCell(j);
            HSSFRichTextString text = new HSSFRichTextString(header.get(j));
            cell.setCellValue(text);
        }
        int index = 1;
        for (int i = 0; i < list.size(); i++) {
            testPoi user = list.get(i);

            Row row2 = sheet.createRow(index++);

            Cell cell1 = row2.createCell(0);
            HSSFRichTextString text1 = new HSSFRichTextString(user.getName1());
            cell1.setCellValue(text1);

            Cell cell2 = row2.createCell(1);
            HSSFRichTextString text2 = new HSSFRichTextString(user.getName2());
            cell2.setCellValue(text2);

            Cell cell3 = row2.createCell(2);
            HSSFRichTextString text3 = new HSSFRichTextString(user.getName3());
            cell3.setCellValue(text3);
        }
        workbook.write(new FileOutputStream(fileName));
    }
}
