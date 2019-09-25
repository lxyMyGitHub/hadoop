package cn.itcast.hadoop.hdfs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;

/**
 * hdfs工具类
 * @author CFCA
 *
 */
public class HdfsUtil {
    private FileSystem fs = null;
    
    @Before
    public void init() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://weekend110:9000/");
        fs = FileSystem.get(new URI("hdfs://weekend110:9000/"),conf,"hadoop");
    }
    
    
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://weekend110:9000/");
        FileSystem fs = FileSystem.get(conf);
        Path src = new Path("hdfs://weekend110:9000/aa/qingshu2.txt");
        FSDataInputStream in = fs.open(src);
        FileOutputStream os = new FileOutputStream("d:/hadoopData/qingshu4.txt");
        IOUtils.copy(in, os);
        System.out.println("copy successful");
    }
    
    /**
     * 上传文件
     * @throws Exception 
     */
    @Test
    public void upload() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path dst = new Path("hdfs://weekend110:9000/aa/qingshu.txt");
        FSDataOutputStream os = fs.create(dst);
        FileInputStream is = new FileInputStream("D:/hadoopData/qingshu.txt");
        IOUtils.copy(is,os);
    }
    /**
     * 上传文件
     * @throws Exception 
     */
    @Test
    public void upload2() throws Exception {
        fs.copyFromLocalFile(new Path("d:/hadoopData/qingshu.txt"), new Path("hdfs://weekend110:9000/aaa/bbb/ccc/qingshu4.txt"));
    }
    
    
    /**
     * 下载文件
     */
    @Test
    public void download() throws Exception {
        fs.copyToLocalFile(false,new Path("hdfs://weekend110:9000/aa/qingshu2.txt"), new Path("d:/hadoopData/qingshu3.txt"),true);
        
    }
    
    
    /**
     * 创建文件夹
     * @throws Exception 
     * @throws  
     */
    @Test
    public void mkdir() throws Exception {
        fs.mkdirs(new Path("/aaa/bbb/ccc"));
    }
    
    /**
     * 删除文件
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        fs.delete(new Path("/aa"),true);
    }
    /**
     * 查看目录
     * @throws Exception 
     * @throws  
     * @throws  
     */
    @Test
    public void listFiles() throws Exception {
        
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"), true);
        
        while(files.hasNext()) {
            LocatedFileStatus file = files.next();
            Path filePath = file.getPath();
            String fileName = filePath.getName();
            System.out.println(fileName);
        }
        System.out.println("------------------------------------");
        
        
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for (FileStatus status : listStatus) {
            String name = status.getPath().getName();
            System.out.println(name + (status.isDirectory()?" is dir":" is file"));
        }
    }
}

