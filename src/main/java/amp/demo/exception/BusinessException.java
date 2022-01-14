package amp.demo.exception;


import amp.demo.utils.AlertCapable;

/**
 * @author han_lic
 * @date 2021/11/7 18:46
 */
public class BusinessException extends RuntimeException implements AlertCapable {
    private String[] alertParameters;
    private String msgCd;
    private String msgInfo;

    public BusinessException(String msgCd) {
        super(msgCd);
        this.msgCd = msgCd;
    }
    /**
     * @param msgCd
     * @param msgInfo
     */
    public BusinessException(String msgCd, String msgInfo) {
        super(msgCd + " :  " + msgInfo);
        this.msgCd = msgCd;
        this.msgInfo = msgInfo;
    }
    /**
     * @param msgCd
     * @param msgInfo
     */
    public static void throwBusinessException(String msgCd, String msgInfo) {
        throw new BusinessException(msgCd, msgInfo);
    }
    /**
     *
     * @param msgCd
     * @param msgInfo
     * @param alertParameters
     */
    public BusinessException(String msgCd, String msgInfo, String... alertParameters) {
        this(msgCd, msgInfo);
        setAlertParameters(alertParameters);
    }
    public static void throwBusinessException(String msgCd) {
        throw new BusinessException(msgCd);
    }
    public static void throwBusinessException(AlertCapable alertCapable) {
        throw new BusinessException(alertCapable.getMsgCd(), alertCapable.getMsgInfo());
    }
    private void setAlertParameters(String... alertParameters) {
        if (null != alertParameters && alertParameters.length > 0) {
            this.alertParameters = new String[alertParameters.length];
            for (int i = 0; i < alertParameters.length; i++) {
                this.alertParameters[i] = alertParameters[i];
            }
        }
    }

    /**
     * @param alertCapable
     */
    public BusinessException(AlertCapable alertCapable) {
        this(alertCapable.getMsgCd(), alertCapable.getMsgInfo());
    }

    @Override
    public String getMsgCd() {
        return this.msgCd;
    }

    public void setMsgCd(String msgCd) {
        this.msgCd = msgCd;
    }

    @Override
    public String getMsgInfo() {
        return this.msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }
}
