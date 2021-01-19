package amp.demo.BO;

import lombok.Data;

/**
 * @author han_lic
 * @date 2021/1/8 10:15
 */
@Data
public class SftpConfigBO {
    /**
     * @Fields urlNo
     */
    private String urlNo;
    /**
     * @Fields systemType
     */
    private String systemType;
    /**
     * @Fields serverUrl
     */
    private String serverUrl;
    /**
     * @Fields userName
     */
    private String userName;
    /**
     * @Fields userPassword
     */
    private String userPassword;
    /**
     * @Fields extendParam
     */
    private String extendParam;
    /**
     * @Fields systemChannel
     */
    private String systemChannel;
    /**
     * @Fields businessChannel
     */
    private String businessChannel;
    /**
     * @Fields fileType
     */
    private String fileType;
    /**
     * @Fields objectiveDirectory
     */
    private String objectiveDirectory;
    /**
     * @Fields localDirectory
     */
    private String localDirectory;
    /**
     * @Fields remark
     */
    private String remark;
    /**
     * @Fields localBank
     */
    private String localBank;
    /**
     * @Fields sendDirectory
     */
    private String sendDirectory;
    /**
     * @Fields receiveDirectory
     */
    private String receiveDirectory;


    /** 下载文件  **/
    String downloadFileName;

    /** 保存文件路径 **/
    String localFileName;

    /** 服务器目录 **/
    String serverDirectory;
}
