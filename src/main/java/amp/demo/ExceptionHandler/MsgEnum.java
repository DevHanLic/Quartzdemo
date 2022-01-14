package amp.demo.ExceptionHandler;


import amp.demo.utils.AlertCapable;

/**
 * <br>
 *
 * @description: error code enum
 * @author: Dev.Yi.Zeng
 * @version: v1.0
 * @time: 2019/09/19 18:14
 */
public enum MsgEnum implements AlertCapable {
    /**
     * 错误码&错误信息
     */
    SUCCESS("MPL00000", "交易成功"),
    SYS_VERSION_IS_NULL("MPL91001", "系统版本为空"),
    TERMINAL_ID_IS_NULL("MPL91002", "终端编号为空"),
    CLIENT_ID_IS_NULL("MPL91003", "客户渠道ID为空"),
    MOBILE_NO_IS_NULL("MPL91004", "手机号为空"),
    ID_NO_IS_NULL("MPL91005", "身份证号为空"),
    MESSAGE_CODE_IS_NULL("MPL91006", "短信验证码为空"),
    PAY_PASSWORD_IS_NULL("MPL91007", "支付密码不能为空"),
    CARD_NO_IS_NULL("MPL91008", "卡号不能为空"),
    ORDER_NO_IS_NULL("MPL91009", "订单号不能为空"),
    ORDER_DATE_IS_NULL("MPL91010", "订单日期不能为空"),
    APP_VERSION_IS_NULL("MPL91011", "应用版本为空"),
    PARAM_EMPTY_ERROR("MPL91012", "入参校验不通过，参数为空"),
    PARAM_LENGTH_ERR("MPL91013", "入参校验不通过，参数长度超长"),
    PARAM_MAX_ERR("MPL91014", "入参校验不通过，参数大小超过范围"),
    PARAM_NULL_ERR("MPL91015", "入参校验不通过，参数为null"),
    AREA_TYPE_ERR("MPL91016", "省市区类型不能为空"),
    PROVINCE_CODE_ERR("MPL91017", "省编码不能为空"),
    CITY_CODE_ERR("MPL91018", "市编码不能为空"),
    USER_NAME_IS_NULL("MPL91020","姓名为空"),
    CACHED_DATA_EXPIRE("MPL80005", "缓存数据已失效"),
    RANDOM_EXPIRE("MPL91019", "随机数过期"),
    LOGIN_DATA_BIND_FAILED("MPL99002", "绑定数据失败"),
    OCR_JOURNAL_NO_IS_EXITS("MPL10404","身份证OCR取号已存在"),
    NO_ORC_SERIAL("MPL10406", "身份证OCR认证流水号不存在"),
    ORC_SERIAL_DONE("MPL10408", "身份证OCR结果查询之前已成功"),
    NOT_USER_INFO("MPL24120", "用户状态异常"),
    CALL_TRANSACTION_FAILED("MPL90100", "调用交易失败"),

    LOGIN_FAILURE("MPL99000", "登录失败"),
    LOGIN_SESSION_EXPIRE("MPL99001", "用户登录session失效"),
    LOGIN_TYPE_ERROR("MPL99003", "登录类型错误"),
    ACCOUNT_INVALID("MPL80000", "您的账户状态异常，您需通过www.cmpay.com或最新版本的客户端完成身份验证后才能继续使用和包。"),
    NEED_ID_VALIDATION("MPL80006", "基于您近期的交易情况，您需通过www.cmpay.com或客户端完成身份验证后才能继续操作账户。"),
    USER_NOT_REGISTER("MPL80002", "用户未注册和包"),

    CLIENT_ALREADY_EXPIRE("FUN03148", "渠道已经失效"),
    CAPTCHA_ERROR("FUN03283", "图形验证码错误"),
    URM_CLICHK_FAIL("FUN03148","客户端授权信息错误"),
    URM_CLICHK_LINK_FAIL("FUN05004","渠道链接地址已失效,请使用新地址"),
    URM_OPERATE_ERROR("MPL10242","操作过于频繁"),
    WECHAT_ADDWXTOKENMBLNO_ERROR("INS00030","登记微信令牌信息失败"),
    WECHAT_GETMBLNO_ERROR("INS00029","查询手机号失败"),
    WECHAT_TOKENUSEDLOGIN_ERROR("INS00031","当前登陆手机号授权已失效,请重新登陆"),
    WECHAT_UPDWXTOKENSTS_ERROR("INS00032","更新微信令牌状态失败"),
    ADD_THIRD_SSO_MBLNO_ERROR("INS00030","登记第三方免登信息失败"),
    UPD_THIRD_SSO_STS_ERROR("INS00030","更新第三方免登信息状态失败"),
    REGISTER_TOKEN_FAILURE("FUN03149", "登记token失败"),
    NO_HALL_PRODUCT("MPL80010", "查询掌厅产品信息失败"),
    GET_MOBILE_IS_NULL("MPL91013", "获取手机号失败"),
    NOT_INIT_PASSWORD_CANNOT_RESET("FUN03181", "支付密码非初始状态不能直接设置"),

    NAME_MISMATCH("MPL24220", "您上传的身份证与实名信息不符"),
    ORC_CHECK_FAIL("MPL10407", "身份证OCR结果查询校验失败"),
    ORC_INFO_INCOMPLETE("MPL10405", "身份证OCR结果查询信息不全"),
    ID_CARD_EXP_DATE_ERR("MPL10337", "身份证有效期不合法"),
    ID_CARD_DATE_INVALID("MPL24221", "您的身份证已过期，请重新上传"),
    ID_CARD_FRONT_AND_BACK_ERROR("MPL24223","身份证正反面错误"),
    LIVE_SERIAL_NO_EXIST("MPL10401", "活体唇语取号已存在"),
    LIVE_SERIAL_NO_NOT_EXIST("MPL10402", "活体唇语流水号不存在"),
    LIVE_SERIAL_NO_EXP("MPL10403", "活体唇语流水号已失效"),
    EXIST_EFFECTIVE_AMOUNT("MPL10346", "您已拥有该贷款提供方的额度,可前往借钱页面"),
    EXIST_PENDING_ORDER("MPL24133", "您有一笔在待审核的订单,请勿重复提交"),
    ID_CARD_FAILURE("MPL10347", "身份证信息已失效"),
    ID_CARD_EXP_ERROR("MPL45057", "身份证有效期异常"),
    IDENTIFY_ERROR("MPL24222", "活体认证失败，请重试"),
    LIVE_VERSION_OLD("MPL22469", "活体验证版本过低，请下载最新客户端重试"),
    LIVE_INFO_NON_EXISTENT("MPL22470","此次活体验证流水不存在或已过期"),
    LOAN_SERVICE_TIME_OUT("MPL24138","由于系统维护，当前贷款产品借款服务已暂停，详见首页公告通知"),
    USER_STATUS_ABNORMAL("MPL24120","您的账户异常，请稍后再试"),
    SCHOOL_STUDENT_CHECK("MPL24145","号码借不支持在校学生申请借款，感谢您的支持"),
    ERROR_BANK_CARD_NO("MPL10025","银行卡号信息有误，不支持设置为换卡"),
    NO_EFFECTIVE_CARD("MPL04099","该用户尚无生效还款卡，无法解绑"),
    LOAN_APPLICATION_FAILED("MPL20264","借款申请失败"),
    NO_VALID_QUOTA("MPL10325","当前无有效额度"),
    INSUFFICIENT_QUOTA("MPL10326","当前额度不足"),
    DISCOUNTELEMENT_TILTIE_ALREADY_EXISTS("MPL05321", "活动要素标题已存在"),
    SMS_CODE_VALIDATION_FAILED("MPL24156","短信校验不通过"),
    REPAY_TRAIL_EXPIRE("MPL45017","当前试算已失效，请返回账单页面重试"),
    REPAY_TYPE_ERROR("MPL45018","还款类型错误"),
    REPAY_CHANNEL_ERROR("MPL45062","还款通道错误"),
    DATEBASE_INSERT_ERROR("MPL45019","Banner数据库插入错误"),
    DATEBASE_UPDATE_ERROR("MPL45020","Banner数据库更新错误"),
    DATEBASE_DELETE_ERROR("MPL45021","Banner数据库删除错误"),
    BANNER_MODULE_NO_IS_NULL("MPL45022","Banner所属区域号为空"),
    BANNER_MODULE_COUNT_FULL("MPL45023","所属区域Banner个数已满"),
    BANNER_JRN_NO_IS_NULL("MPL45024","Banner流水号为空"),
    BANNER_TIME_ILLEGAL("MPL45025","失效时间不能早于当前时间"),
    BANNER_SEQ_ILLEGAL("MPL45026","此Banner序号已存在"),
    ILLEGAL_ALREADY_EXISTS("MPL45027","数据库存在重复记录"),
    BATCH_INSERT_ERROR("MPL45028","批量导入失败"),
    NO_REPAYED_ORDER("MPL10274","未查询到需要还款的订单或账单"),
    HAS_PROCESS_LOAN_ORDER("MPL24117","您有一笔正在处理的借款订单"),
    SMS_VERIFY_FAIL_NEED_REISSUE("MPL51404","短信验证码失效,请重新获取"),
    SMS_SEND_FAIL("MPL80004","获取短信验证码失败"),
    TRIAL_FAIL("MPL99015","试算错误,请修改金额或期数后重试"),
    ONLINE_LOAN_CONTRACT_FAIL("MPL99017","授信/支用合同生成错误,请修改金额或期数后重试"),
    ONLINE_LOAN_PREVIEW_APPLY_FAIL("MPL99018","网贷待签名电子合同生成错误,请稍后重试"),
    ONLINE_LOAN_PREVIEW_QUERY_FAIL("MPL99019","网贷待签名电子合同查询错误,请稍后重试"),
    ONLINE_LOAN_APPLY_FAIL("MPL99020","网贷电子合同合成签名错误,请稍后重试"),
    ONLINE_LOAN_QUERY_FAIL("MPL99021","网贷电子合同合成签名查询错误,请稍后重试"),
    FILE_DEAL_ERROR("MPL99022","网贷电子合同XML解析错误"),
    CER_TYP_ERROR("MPL99023","身份证类型有误"),
    ONLINE_LOAN_PREVIEW_QUERY_EMPTY("MPL99024","网贷待签名电子合同正在生成中,请稍后"),
    INVITER_CODE_FAIL("MPL10383","邀请码错误或已失效，请重新填写"),
    INVITATION_CODE_ERROR("MPL10384","推荐人不能是自己"),
    QUOTA_ACTIVE_FALSE("MPL10385","额度激活失败"),
    COMPANY_NOT_EXIST("MPL99025","您选择的公司不存在"),
    COMPANY_EXIST("MPL99028","第二推荐人编号已存在，请勿重复添加"),
    ORDER_REFUND("MPL99030","该笔订单存在退票或打款不成功"),
    TRIAL_AMOUNT_ERROR("MPL99031","合作方自动还款处理中，暂不支持主动还款，请稍后再试"),
    FIRST_REFERRER_NOT_EXISTD("MPL99032","第一推荐人不存在"),
    CREDITQUOTANO_NOT_EXIST("MPL99038","授信额度编号不存在"),
    CHANGE_BANK_CARD_ERROR("MPL99039","变更还款卡失败"),
    SING_AGREEMENT_NO_NOT_EXIST("MPL99040","签约协议号不存在"),

    /**
     * 新增
     */
    BANK_CARD_NO_BLANK_ERROR("MPL24226","银行卡号信息不能为空"),
    ID_NO_AND_USR_NM_BLANK_ERROR("MPL24227","身份证号或用户姓名不能为空"),
    ID_NO_BLANK_ERROR("MPL24228","身份证号不能为空"),
    USR_NM_BLANK_ERROR("MPL24229","用户姓名不能为空"),
    USR_NM_INCONSISTENT("MPL24230","用户姓名不一致"),
    ID_NO_INCONSISTENT("MPL24231","身份证号不一致"),
    FILE_NOT_EXIST("MPL24232","文件不存在"),
    QUERY_MIDDLE_END_REPAY_FAIL("MPL24244","查询中台还款订单失败"),
    ELECTRONIC_ACCOUNT_BALANCE("MPL24250","电子账户余额不足，请充值后重试"),
    SUPPORT_BANK_CARD_LIST_NOT_NULL("MPL24252","资金方支持银行卡列表不能为空"),
    /**
     * 用户未进行授信或者授信已失效错误码
     */
    NO_CREDIT_OR_CREDIT_FAILURE("MPL28001","用户未进行授信或者授信已失效"),
    FREQUENT_OPERATION("MPL99012","您的操作过于频繁，请一小时后再试"),
    NOT_EXISTS_CREDIT("MPL28002","授信流水不存在"),
    SYSTEM_ERROR("MPL24904", "系统开小差"),
    QUERY_DB_RECORD_FAIL("MPL90001", "查询数据库表记录失败"),
    OPERATE_DB_RECORD_FAIL("MPL90002", "操作数据库表记录失败"),
    UNABLE_TO_REPAY_FOR_IN_FIVE_MINITES("MPL24169","主动还款，两笔还款时间间隔过短"),
    NO_PRODUCTS_FOUND("MPL24001","未找到有效产品信息"),
    HAS_REPAY_PROCESS_ORDER("MPL24165","您有正在处理中的还款，请稍后再试"),
    LOAN_ORDER_NOT_FOUND("MPL10194","借款订单不存在"),
    DATE_FORMAT_ERR("MPL45000","日期格式错误"),
    UNUSED_REPAYMENT_CARD("MPL45001","无可用还款卡"),
    SERIALNO_IS_NULL("MPL24226", "参数为空"),
    NOT_SUPPORT_PRESETTLE("MPL24162","该产品暂不支持提前结清"),
    NOT_SUPPORT_PRESETTLE_BY_LOANDAY("MPL24168","借款当天不允许提前结清"),
    NOT_SUPPORT_PREPAYMENT("MPL24167","该产品暂不支持提前还款"),
    NOT_SUPPORT_OVERDUE("MPL24233","该产品暂不支持当期/逾期还款"),
    NOT_SUPPORT_MORE_OVERDUE("MPL24234","该产品暂不支持多期逾期还款"),
    NOT_SUPPORT_MORE_MULTI("MPL24235","该产品暂不支持多期混合还款"),
    NOT_SUPPORT_PREPAYMENT_NOT_CURRENT("MPL24236","提前还款只支持当期"),
    CURRENT_PERIOD_SETTLE("MPL24237","当前期数已还清，无需还款"),
    NOT_SUPPORT_INTER_TEMPORAL_REPAYMENT("MPL24238","不支持跨期还款"),
    REPAY_PERIOD_NOT_EXIST("MPL24239","还款期数不存在"),
    REPAYMENT_PERIODS_FAMAT_ERROR("MPL45002","还款期数格式错误"),
    HAS_OVER_DUE_BILL("MPL24163","您有逾期账单未还，请还款后重试"),
    HAS_NEED_PAY_BILL("MPL05014","您有未还的和包贷账单，请完成还款后重新解约"),
    PRODUCT_NO_IS_NULL("MPL45003","产品编号为空"),
    ONLY_SUPPORTS_ONE_OVERDUE_PAYMENT("MPL24170","当前账单仅支持多期逾期一笔还清"),
    ONLY_SUPPORTS_ONE_PAYMENT("MPL24171","当前账单仅支持多期一笔还清"),
    BILL_NOT_EXITS("MPL45015","账单不存在"),
    BILL_DATA_ERROR("MPL45004","当前账单没有待还期数"),
    NEED_REPAY_OVER_DUE_BILL("MPL45005","您有贷款已逾期，须偿还逾期贷款后才能进行正常还款"),
    INSERT_REPAYMENT_ORDER_ERROR("MPL45006","登记还款订单失败"),
    REPAYMENT_ORDER_NOT_EXITS("MPL45007","还款订单不存在"),
    UPDATE_REPAYMENT_ORDER_ERROR("MPL45008","还款订单更新失败"),
    PAY_PASSWORD_ERROR("MPL45009","支付密码错误"),
    REPAYMENT_CARD_SIGNING("MPL10020","还款卡签约中,请稍后"),
    REPAYMENT_CARD_CHANGE_COUNT_OVERRUN("MPL10028","还款卡当日更换次数超限"),
    NON_REAL_NAME_NOT_ALLOW_CHANGE_REPAYMENT_CARD("MPL10005","非实名用户无法更换还款卡"),
    SET_UP_REPAYMENT_CARD_TYPE_ERROR("MPL45010","设置还款卡类型错误"),
    QUICK_PAYMENT_AGREEMENT_INVALID("MPL45011","快捷协议已失效，请解绑后重试"),
    INPUT_PARAM_ERROR("MPL45012","输入参数{1}有误"),
    PARAM_NOT_FOUND("MPL45013","参数不存在"),
    INPUT_PARAM_USER_REPAY_TYPE_ERROR("MPL45029","传入用户还款方式有误"),
    BANK_CARD_NO_ERROR("MPL10026","银行卡号信息有误"),
    NOT_FOUND_USER_BANK_CARD("MPL99010","用户未设置银行卡"),
    SERVICE_MAINTAIN("MPL24139","由于系统维护，当前贷款产品借款服务已暂停，详见首页公告通知"),
    UNBUNDLE_CARD_FALSE("MPL10267","解绑还款卡检查失败"),
    CREDIT_STATUS_ERROR("MPL45014","授信状态异常"),
    LOAN_TRIAL_INFO_ERROR("MPL45058","借款试算信息不存在"),
    LOAN_INTEREST_RATE_INFO_ERROR("MPL45059","借款利率查询结果不存在"),
    BILL_AGREEMENT_NOT_EXITS("MPL45016","协议不存在"),
    NOT_FOUND_FUND_SIDE("MPL45030","未查询到资金方"),
    DEVICE_FINGER_PRINT_NOT_EXITS("MPL45060", "设备指纹信息不存在"),
    IMAGE_DOWNLOAD_FAILED("MPL24122","图片下载失败"),
    IMAGE_COMPRESSION_FAILED("MPL24130","图片压缩失败"),
    FILE_UPLOAD_FAILED("MPL90007","上传文件失败"),
    QUERY_PARAMETERS_FAILED("MPL04225","查询配置参数失败"),
    CAPITAL_MERCHANT_ERROR("MPL45061", "资方商户配置信息异常"),
    MESSAGE_FORMAT_ERROR("MPL99011", "请求报文格式错误"),
    CONTACT_MBL_NO_REPEAT("MPL15345","紧急联系人手机号不能与本机登陆手机号相同"),
    CONTACT_MBL_NO_NOT_ALLOW("MPL04233","您不能使用自己的手机号作为紧急联系人"),
    CONTACT_MBL_NO_SAME("MPL15347","两个紧急联系人手机号不能为同一个"),
    CONTACT_USER_NAME_REPEAT("MPL10449","紧急联系人姓名不能与本人注册姓名一致，请修改"),
    CONTACT_USER_NAME_SAME("MPL24150","两个紧急联系人的姓名不能一致，请修改"),
    BILL_STATUS_ERROR("MPL45064","账单状态异常"),
    BILL_HAS_REPAYED("MPL10195","账单已还"),
    WORK_MOBILE_THE_SAME("MPL10056","工作单位联系电话与用户的手机号一致"),
    WORK_MOBILE_IS_ILEGAL("MPL10058","工作单位联系电话号码为违规号段"),
    CONTACT_MBL_NO_IS_ILEGAL("MPL51337","紧急联系人手机号段为违规号段"),
    WORK_UNIT_NOT_ALLOW_ALL_NUMBER("MPL45065","工作信息不能全为数字"),
    HAS_SUCCESS_REPAY_ORDER("MPL24112","该订单已还清，无需再次还款"),
    BILL_HAS_REPAY("MPL10195","账单已还"),
    ORDER_ALREADY_EXISTS("MPL10308","已存在订单，请勿重复下单"),
    NOT_IN_TIME_REPAY_OR_LOAN_PERIOD("MPL24225", "该贷款提供方仅于{1}-{2}开放{3}服务，请稍候申请"),

    NOT_WHITELIST_USER("MPL24242","该产品仅对特定用户开放，感谢您的关注"),
    HAS_CREDIT_PROCESSING("MPL24240", "您有正在处理中的授信，请稍后再试"),
    HAS_CREDIT_LOCKED("MPL24241", "授信失败，当前处于锁定期{1}内"),
    BANK_CARD_ERROR("MPL24243","该产品不支持您当前使用的还款卡，请更换后重试"),
    BANK_CARD_IS_NOT_SUPPORTED("MPL10429","不支持该银行卡"),
    PROVINCIAL_COMPANY_EXIST_ERROR("MPL24249","已存在该省份的省公司"),
    SMS_CHECK_FAIL("MPL24251","短信验证码错误，请输入正确的验证码"),

    /**
     * 浦发公积金点贷
     */
    USR_EDUCATION_BLANK_ERROR("MPL50011","教育程度不能为空"),
    USR_MARITAL_BLANK_ERROR("MPL50012","婚姻状态不能为空"),
    USR_INDUSTRY_BLANK_ERROR("MPL50013","职业类型不能为空"),
    USR_AREA_BLANK_ERROR("MPL50014","所在地区不能为空"),
    USR_ZIP_BLANK_ERROR("MPL50015","邮政编码不能为空"),
    USR_ADDR_BLANK_ERROR("MPL50016","详细地址不能为空"),

    ORDER_NOT_EXISTS_ERROR("MPL50030","订单不存在"),
    PRE_CREDIT_NOT_EXISTS_ERROR("MPL50031","预授信信息不存在"),
    PARTNER_NO_NOT_EXISTS_ERROR("MPL50032","资金方编号为空"),
    IDENTITY_ID_NOT_EXISTS_ERROR("MPL50033","身份影像信息不存在"),
    SMS_SCENCE_NOT_EXISTS_ERROR("MPL50034","短信场景类型为空"),
    ORDER_STATUS_ERR_CANNOT_UPDATE("MPL99027","当前订单状态不允许执行修改"),
    ENTERPRIS_USER_NOT_EXISTS_ERROR("MPL50035","您不是政企用户，不允许此项操作"),
    NOT_FUND_WAIT_COUPON("MPL99040","未查询到待激活的优惠卷"),
    GET_USER_ERR("MPL91020","用户信息获取失败"),
    EXIST_WAIT_COUPON("MPL99041","已存在待激活的优惠卷"),
    GROUP_PRODUCT_ERROR("MPL99051","产品分组筛选失败"),
    ;

    MsgEnum(String msgCd, String msgInfo) {
        this.msgCd = msgCd;
        this.msgInfo = msgInfo;
    }

    /**
     * error code
     */
    private String msgCd;
    /**
     * error info
     */
    private String msgInfo;

    @Override
    public String getMsgCd() {
        return this.msgCd;
    }

    @Override
    public String getMsgInfo() {
        return this.msgInfo;
    }
}
