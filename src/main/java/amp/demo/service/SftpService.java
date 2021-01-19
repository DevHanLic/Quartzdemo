package amp.demo.service;

import amp.demo.BO.SftpConfigBO;

import java.io.File;

/**
 * @author han_lic
 * @date 2021/1/8 10:15
 */
public interface SftpService {
    /**
     * 文件下载
     * @param sftpConfigBO
     * @return
     */
    File download(SftpConfigBO sftpConfigBO);
}
