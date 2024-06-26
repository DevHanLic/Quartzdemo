package amp.demo;

import java.util.Comparator;

public class DirectoryCompare implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        int num1 = subNum(o1);
        int num2 = subNum(o2);
        return num1-num2;
    }

    public static int subNum(String directoryName) {
        int index = directoryName.lastIndexOf("_");
        //没匹配到“_” 说明不符合文件名规定，返回int最大值
        if (index==-1) {
            return Integer.MAX_VALUE;
        }
        String subsNum = directoryName.substring(index+1);
        return Integer.valueOf(subsNum);
    }
}
