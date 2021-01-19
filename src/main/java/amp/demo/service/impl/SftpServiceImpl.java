package amp.demo.service.impl;

import amp.demo.BO.SftpConfigBO;
import amp.demo.config.SftpConstants;
import amp.demo.service.SftpService;
import amp.demo.utils.JudgeUtils;
import amp.demo.utils.SettleLogger;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Properties;

/**
 * @author han_lic
 * @date 2021/1/8 10:16
 */
@Service
public class SftpServiceImpl implements SftpService {
    private static final Logger logger = LoggerFactory.getLogger(SftpServiceImpl.class);
    /**
     * 文件下载
     *
     * @param sftpConfigBO
     * @return
     */
    @Override
    public File download(SftpConfigBO sftpConfigBO) {
        ChannelSftp sftpConnect = null;
        FileOutputStream fileOutputStream = null;
        File file = new File(sftpConfigBO.getLocalFileName());
        try {
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            sftpConnect = getSftpConnect(sftpConfigBO);

            /** 判断是否以/结尾 **/
            if(!sftpConfigBO.getObjectiveDirectory().endsWith(SftpConstants.SEPARATE_OF_VERTICAL_LINE)) {
                sftpConfigBO.setObjectiveDirectory(sftpConfigBO.getObjectiveDirectory() + SftpConstants.SEPARATE_OF_VERTICAL_LINE);
            }

            if (JudgeUtils.isNotBlank(sftpConfigBO.getObjectiveDirectory())) {
                if (JudgeUtils.isNotBlank(sftpConfigBO.getServerDirectory())) {
                    sftpConnect.cd(sftpConfigBO.getObjectiveDirectory()  + sftpConfigBO.getServerDirectory());
                } else {
                    sftpConnect.cd(sftpConfigBO.getObjectiveDirectory());
                }
            }
            sftpConnect.get(sftpConfigBO.getDownloadFileName(), fileOutputStream);
        } catch (Exception e) {
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            String stackTraceElement = "";
            if (stackTraceElements.length > 1) {
                stackTraceElement = stackTraceElements[1].toString();
            }
            logger.info("Download file Failed!!! Message is " + stackTraceElement);
            e.printStackTrace();
        } finally {
            disConnectSftp(sftpConnect);
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    logger.info("Close FileOutputStream Failed!!");
                }
            }
        }
        return file;
    }
    /**
     * 获取sftp连接
     * @param sftpConfigBO
     * @return
     */
    private ChannelSftp getSftpConnect(SftpConfigBO sftpConfigBO) {
        ChannelSftp sftp = null;
        try {
            com.jcraft.jsch.Logger logger = new SettleLogger();
            JSch.setLogger(logger);

            URI uri = new URI(sftpConfigBO.getServerUrl());
            JSch jsch = new JSch();
            Session sshSession = jsch.getSession(sftpConfigBO.getUserName(), uri.getHost(), uri.getPort());
            sshSession.setPassword(sftpConfigBO.getUserPassword());
            Properties sshConfig = new Properties();
            sshConfig.put(SftpConstants.STRICT_HOST_KEY_CHECKING, SftpConstants.NO);
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel(SftpConstants.OPEN_CHANNEL_OF_SFTP);
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            logger.info("get sftp channel failed");
            e.printStackTrace();
        }
        return sftp;
    }
    /**
     * 关闭sftp连接
     * @param sftp
     * @throws JSchException
     */
    private void disConnectSftp(final ChannelSftp sftp){
        try {
            Session sshSession = sftp.getSession();
            if (sshSession != null && sshSession.isConnected()) {
                sshSession.disconnect();
            }
            if (sftp != null && sftp.isConnected()) {
                sftp.disconnect();
            }
        } catch (Exception e) {
            logger.info("Close Sftp Channel Failed !!!");
        }
    }
}
