package com.coderman.uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用Java进行文件分块
 *
 * @Author zhangyukang
 * @Date 2021/1/16 12:59
 * @Version 1.0
 **/
public class TestFile {

    private static Logger logger = LoggerFactory.getLogger(TestFile.class);

    public static void main(String[] args) throws IOException {
        //源文件
        File sourceFile = new File("C:\\Users\\Administrator\\Desktop\\java.mp4");
        //保存块文件的目录
        String chunkFolder = "E:\\IDM下载\\视频\\chunks\\";
        //分片的大小
        long shareSize = 1024 * 1024;
        logger.info("分片大小:{}", shareSize);
        //总块数
        long total = (long) Math.ceil(sourceFile.length() * 1.0 / shareSize);
        logger.info("分片总数:{}", total);
        //创建读文件的对象
        RandomAccessFile randomAccessFileReader = new RandomAccessFile(sourceFile, "r");
        byte[] bytes = new byte[1024];
        for (int i = 0; i < total; i++) {
            File chunkFile = new File(chunkFolder + i);
            logger.info("分片:{},chunkFile:{}", i + 1, chunkFile.getName());
            //创建一个写对象
            RandomAccessFile randomAccessFileWriter = new RandomAccessFile(chunkFile, "rw");
            int len;
            while ((len = randomAccessFileReader.read(bytes)) != -1) {
                randomAccessFileWriter.write(bytes, 0, len);
                //如果分片的大小>=分片的大小，读下一块
                if (chunkFile.length() >= shareSize) {
                    break;
                }
            }
            randomAccessFileWriter.close();
        }
        randomAccessFileReader.close();

        //合并文件
        mergeFile(new File(chunkFolder));
    }

    /**
     * 合并块文件
     * @param chunkFolder
     */
    public static void mergeFile(File chunkFolder) throws IOException {
        //块文件夹下文件列表
        File[] files = chunkFolder.listFiles();
        assert files != null;
        List<File> fileList = Arrays.stream(files).sorted(
                Comparator.comparing(o -> Long.valueOf(o.getName())))
                .collect(Collectors.toList());
        //合并的文件
        File mergeFile = new File("E:\\IDM下载\\视频\\java_merge.mp4");
        //创建新文件
        boolean success = mergeFile.createNewFile();
        //创建写对象
        RandomAccessFile randomAccessFileWriter = new RandomAccessFile(mergeFile, "rw");
        byte[] bytes = new byte[1024];
        for (int i = 0; i < fileList.size(); i++) {
            File chunkFile = fileList.get(i);
            logger.info("合并------>分片:{},chunkFile:{}", i + 1, chunkFile.getName());
            RandomAccessFile randomAccessFileReader = new RandomAccessFile(chunkFile, "r");
            int len;
            while ((len = randomAccessFileReader.read(bytes)) != -1) {
                randomAccessFileWriter.write(bytes, 0, len);
            }
            randomAccessFileReader.close();
        }
        randomAccessFileWriter.close();
    }
}
