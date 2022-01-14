package amp.demo;

import lombok.Data;

/**
 * @author shou_qiang
 * @date 2020/11/10 15:46
 * @desc 对账参数BO
 **/
@Data
public class CheckParamsBO {
    /**
     * @Fields pathCoopOrg
     */
    private String pathCoopOrg;
    /**
     * @Fields coopBusinessType
     */
    private String coopBusinessType;
    /**
     * @Fields effectFlag
     */
    private String effectFlag;
    /**
     * @Fields checkType
     */
    private String checkType;
    /**
     * @Fields checkFlag
     */
    private String checkFlag;
    /**
     * @Fields batchCheckFlag
     */
    private String batchCheckFlag;
    /**
     * @Fields loadService
     */
    private String loadService;
    /**
     * @Fields loadTransactionCode
     */
    private String loadTransactionCode;
    /**
     * @Fields checkService
     */
    private String checkService;
    /**
     * @Fields checkTransactionCode
     */
    private String checkTransactionCode;
    /**
     * @Fields toErrorDays
     */
    private Short toErrorDays;
    /**
     * @Fields checkBeginTime
     */
    private String checkBeginTime;
    /**
     * @Fields checkEndTime
     */
    private String checkEndTime;
    /**
     * @Fields allowSupplementFlag
     */
    private String allowSupplementFlag;
    /**
     * @Fields supplementComponents
     */
    private String supplementComponents;
    /**
     * @Fields cancelFlag
     */
    private String cancelFlag;
    /**
     * @Fields cancelComponents
     */
    private String cancelComponents;
    /**
     * @Fields autoSkipFlag
     */
    private String autoSkipFlag;
    /**
     * @Fields journalTableName
     */
    private String journalTableName;
    /**
     * @Fields getFileComponents
     */
    private String getFileComponents;
    /**
     * @Fields importDatabseComponents
     */
    private String importDatabseComponents;
    /**
     * @Fields checkComponents
     */
    private String checkComponents;
    /**
     * @Fields filePath
     */
    private String filePath;
    /**
     * @Fields checkTable
     */
    private String checkTable;
    /**
     * @Fields formatName
     */
    private String formatName;
    /**
     * @Fields checkSubStatus
     */
    private String checkSubStatus;
    /**
     * @Fields afterProcess
     */
    private String afterProcess;
    /**
     * @Fields checkCountMode
     */
    private String checkCountMode;
    /**
     * @Fields modifyDebitCreditFlag
     */
    private String modifyDebitCreditFlag;
    /**
     * @Fields coopBusinessFlag
     */
    private String coopBusinessFlag;
    /**
     * @Fields operateId
     */
    private String operateId;
    /**
     * @Fields packageName
     */
    private String packageName;
    /**
     * @Fields groupName
     */
    private String groupName;

}
