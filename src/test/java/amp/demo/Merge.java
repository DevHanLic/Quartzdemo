package amp.demo;

public class Merge {
    public static void main(String[] args) {
        //一级目录路径
        String path = "/Users/apple_hlc/Library/Containers/com.tencent.tenvideo/Data/Library/Application Support/Download/video/0b53g4aauaaaraakh23qjnsman6dbiyqadsa.f321004.hls";
        //合并后的文件路径，合并后的文件名为:一级目录的字符串名.ts，如：z0726wuq3en.322002.hls.ts
        String targetPath = "/Users/apple_hlc/Downloads/素材/Template/";
        MergeUtil.videoMergeForDirectoryLv2(path,targetPath);
    }
}
