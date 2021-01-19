package amp.demo.config;

/**
 * @author han_lic
 * @date 2021/1/8 10:20
 */
public interface SftpConstants {
    /**
     * 文件类型
     * 0数据文件
     */
    String FILE_TYPE_OF_DATA = "0";
    /**
     * 竖线分割
     */
    String SEPARATE_OF_VERTICAL_LINE = "/";
    /**
     * 检查主机key
     */
    String STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
    /**
     * 检查主机key
     */
    String NO = "no";
    /**
     * 开发渠道
     */
    String OPEN_CHANNEL_OF_SFTP = "sftp";
    /**
     * 系统类型
     * 0 – 文件服务器
     * 1 – 应用服务器
     */
    String SYSTEM_TYPE_OF_APPLICATION = "1";
    /**
     * 系统渠道
     * PWM充提
     */
    String SYSTEM_CHANNEL_OF_PWM ="PWM";
}
