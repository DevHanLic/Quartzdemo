package amp.demo.aop;



/**
 * 测试响应
 *
 * @author: zetting
 * @date:2018/12/27
 */
public class TestResponse {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 银行卡号
     */
    @EncryptField
    private String bankCardNo;

    /**
     * 身份证号
     */
    @EncryptField
    private String idCard;


    @Override
    public String toString() {
        if (this == null) {
            return null;
        }
        System.out.println(2);
        return String.format("{\n" +
                "    \"name\": \"%s\",\n" +
                "    \"sex\": \"%s\",\n" +
                "    \"bankCardNo\": \"%s\",\n" +
                "    \"idCard\": \"%s\"\n" +
                "}", this.name, this.sex, this.bankCardNo, this.idCard);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
