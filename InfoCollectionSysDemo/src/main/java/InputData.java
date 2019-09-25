package main.java;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import main.utils.ConnectDB;
import main.utils.StringUtil;

public class InputData {
    private static Logger logger = Logger.getLogger(InfoDo.class);
    public static void main(String[] args) {
        logger.info("action is :" + args[0]);
        logger.info("source dir is :" + args[1]);
        logger.info("admin is :" + args[2]);
        String admin = args[2];
        logger.info("InputData start ...");
        String srcPath = args[1];
        if(StringUtil.isBlank(srcPath)) {
            logger.error("source path is null...");
            return ;
        }
        logger.info("InputData source Path is :" + srcPath);
        try {
            File srcFile = new File(srcPath);
            File[] files = srcFile.listFiles();
            for (File file : files) {
                String fileName = file.getName();
                Connection mysqlConn;
                Properties prop = initConfig();
                mysqlConn = ConnectDB.getConnection(prop.getProperty("dataBase"), prop.getProperty("url"),prop.getProperty("userName"),prop.getProperty("password"));
                logger.info("Connection DataBase is successful ! ! !");
                PreparedStatement ps = null;
                if(fileName.contains(".txt")) {
                    List<String> contentList = getFileContent(srcPath + "\\" + fileName);
                    for (String content : contentList) {
                        logger.info("content is :" + content);
                        InfoDo infoBean = InfoDo.getInstence(content);
                        logger.info("insert begin :" + infoBean);
                        InputStream blobIn1 = null;
                        InputStream blobIn2 = null;
                        if(infoBean.getFileOneName()!=null && !infoBean.getFileOneName().equals("null")) {
                            blobIn1 = src2InputStream(srcPath, infoBean.getFileOneName());
                        }
                        if(infoBean.getFileTwoName()!=null && !infoBean.getFileTwoName().equals("null")) {
                            blobIn2 = src2InputStream(srcPath, infoBean.getFileTwoName());
                        }
                        //获取数据库连接
                        logger.info("mysqlConn :" + mysqlConn);
                        //存入字段
                        String insertSql = "INSERT INTO info (id,productName,region,market,address,storeName,reportingType,reportingContent,name,reportingTime,effective,administrator,fileOneName,fileTwoName,fileOne,fileTwo,dateTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        ps = mysqlConn.prepareStatement(insertSql);
                        ps.setLong(1, infoBean.getId());
                        ps.setString(2, infoBean.getProductName());
                        ps.setString(3, infoBean.getRegion());
                        ps.setString(4, infoBean.getMarket());
                        ps.setString(5, infoBean.getAddress());
                        ps.setString(6, infoBean.getStoreName());
                        ps.setString(7, infoBean.getReportingType());
                        ps.setString(8, infoBean.getReportingContent());
                        ps.setString(9, infoBean.getName());
                        logger.info(infoBean.getReportingTime().toString());
                        ps.setObject(10, new java.sql.Timestamp(infoBean.getReportingTime().getTime()));
                        ps.setString(11, infoBean.getEffective());
                        ps.setString(12, admin);
                        ps.setString(13, infoBean.getFileOneName());
                        ps.setString(14, infoBean.getFileTwoName());
                        if(blobIn1==null) {
                            ps.setNull(15,Types.BLOB);
                        }else {
                            ps.setBlob(15, blobIn1);
                        }
                        if(blobIn2==null) {
                            ps.setNull(16,Types.BLOB);
                        }else {
                            ps.setBlob(16, blobIn2);
                        }
                        ps.setObject(17, new java.sql.Timestamp(infoBean.getDateTime().getTime()));
                        logger.info(infoBean.getDateTime());
                        int count = ps.executeUpdate();
                        if (count > 0) {
                            logger.info("insert successful one message!");
                        } else {
                            logger.info("insert fail one message!");
                        }
                        if(blobIn1!=null) {
                            blobIn1.close();
                        }
                        if(blobIn2!=null) {
                            blobIn2.close();
                        }
                    }
            }else{
                logger.info("picture dir is exists .");
            }
        }
    }catch(Exception e) {
        logger.error(e.getMessage(),e);
    }
}


    private static Properties initConfig() throws Exception {
        Properties prop = new Properties();
        InputStream in = new BufferedInputStream (new FileInputStream("/InfoCollection/conf/config.properties"));
        prop.load(in);
        return prop;
    }

    public static FileInputStream src2InputStream(String srcPath, String fileName) throws FileNotFoundException {
        File sourceFile = new File(srcPath + "\\picture\\" + fileName);
        FileInputStream in = null;
        in = new FileInputStream(sourceFile);
        return in;
    }
    
    
    
    /**
     * 读取文件的内容
     * 读取指定文件的内容
     * @param path 为要读取文件的绝对路径
     * @return 以行读取文件后的内容。
     * @since  1.0
     */
    private static List<String> getFileContent(String path) throws IOException
    {
      ArrayList<String> contentList = new ArrayList<>();
      try {
        File f = new File(path);
        if (f.exists()) {
          FileReader fr = new FileReader(path);
          BufferedReader br = new BufferedReader(fr); //建立BufferedReader对象，并实例化为br
          String line = br.readLine(); //从文件读取一行字符串
          //判断读取到的字符串是否不为空
          while (line != null) {
            contentList.add(line);
            line = br.readLine(); //从文件中继续读取一行数据
          }
          br.close(); //关闭BufferedReader对象
          fr.close(); //关闭文件
        }

      }
      catch (IOException e) {
        throw e;
      }
      return contentList;
    }
}
