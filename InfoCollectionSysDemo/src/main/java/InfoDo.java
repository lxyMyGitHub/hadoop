package main.java;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import main.utils.IdWorker;
import main.utils.StringUtil;

//采集信息实体类
public class InfoDo implements Serializable{
    private static final long serialVersionUID = 1L;
    //伊利植选豆奶原味和黑豆包装新升级|浙江大区|东阳市场|现代通路|华润万家|人民路店|上报内容123123123|赵函星|20190623123000|23|zhx0101.png|zhx0102.jpg
    //产品名称|销售大区|销售市场|地址|门店名称|上报类型|上报内容|姓名|时间|有效价值|图片1.jpg|图片2.jpg
    private long id ;//唯一标识
    private String productName;//产品名称
    private String region;//销售大区
    private String market;//销售市场
    private String address;//地址
    private String storeName;//门店名称
    private String reportingType;//上报类型
    private String reportingContent;//上报内容
    private String name;//姓名
    private Date reportingTime;//时间
    private String effective;//有效价值
    private String administrator;//统计人
    private String fileOneName;//文件1名称
    private String fileTwoName;//文件2名称
    private Date dateTime;//文件2名称
    private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    
    
    
    public InfoDo(long id, String productName, String region, String market, String address, String storeName, String reportingType, String reportingContent,
            String name, Date reportingTime, String effective, String administrator, String fileOneName, String fileTwoName, Date dateTime) {
        super();
        this.id = id;
        this.productName = productName;
        this.region = region;
        this.market = market;
        this.address = address;
        this.storeName = storeName;
        this.reportingType = reportingType;
        this.reportingContent = reportingContent;
        this.name = name;
        this.reportingTime = reportingTime;
        this.effective = effective;
        this.administrator = administrator;
        this.fileOneName = fileOneName;
        this.fileTwoName = fileTwoName;
        this.dateTime = dateTime;
    }
    public static InfoDo getInstence(String infoStr) throws Exception {
        if(StringUtil.isBlank(infoStr)) {
            return null;
        }
      //伊利植选豆奶原味和黑豆包装新升级|浙江大区|东阳市场|现代通路|华润万家|人民路店|上报内容123123123|赵函星|20190623123000|23|zhx0101.png|zhx0102.jpg
        String[] message = infoStr.split("\\|");
        InfoDo info = new InfoDo();
        info.id = new IdWorker().nextId();
        info.productName = message[0];
        info.region = message[1];
        info.market = message[2];
        info.address = message[3];
        info.storeName = message[4];
        info.reportingType = message[5];
        info.reportingContent = message[6];
        info.name = message[7];
        info.reportingTime = format.parse(message[8]);
        info.effective = message[9];
        info.fileOneName = message[10];
        info.fileTwoName = message[11];
        info.dateTime = new Date();
        return info;
        
    }
    public InfoDo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Date getReportingTime() {
        return reportingTime;
    }
    public Date getDateTime() {
        return dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public void setReportingTime(Date reportingTime) {
        this.reportingTime = reportingTime;
    }
    public String getFileOneName() {
        return fileOneName;
    }
    public void setFileOneName(String fileOneName) {
        this.fileOneName = fileOneName;
    }
    public String getFileTwoName() {
        return fileTwoName;
    }
    public void setFileTwoName(String fileTwoName) {
        this.fileTwoName = fileTwoName;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getMarket() {
        return market;
    }
    public void setMarket(String market) {
        this.market = market;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getReportingType() {
        return reportingType;
    }
    public void setReportingType(String reportingType) {
        this.reportingType = reportingType;
    }
    public String getReportingContent() {
        return reportingContent;
    }
    public void setReportingContent(String reportingContent) {
        this.reportingContent = reportingContent;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEffective() {
        return effective;
    }
    public void setEffective(String effective) {
        this.effective = effective;
    }
    public String getAdministrator() {
        return administrator;
    }
    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }
    @Override
    public String toString() {
        return "InfoDo [id=" + id + ", productName=" + productName + ", region=" + region + ", market=" + market + ", address=" + address + ", storeName="
                + storeName + ", reportingType=" + reportingType + ", reportingContent=" + reportingContent + ", name=" + name + ", reportingTime="
                + reportingTime + ", effective=" + effective + ", administrator=" + administrator + ", fileOneName=" + fileOneName + ", fileTwoName="
                + fileTwoName + "]";
    }
    
    
}
