package amp.demo.test;

import amp.demo.DTO.BrokenscreeniRespDTO;
import amp.demo.annotation.MyBody;
import amp.demo.entity.UserTest;
import amp.demo.enums.ParamHelper;
import amp.demo.utils.DateTimeUtil;
import amp.demo.utils.JudgeUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author han_lic
 * @date 2021/5/6 20:15
 */
@RestController
public class TestDemoBrokenscreeni {

    @PostMapping("/brokenscreeni/test")
    public BrokenscreeniRespDTO test() {
        BrokenscreeniRespDTO brokenscreeniRespDTO = new BrokenscreeniRespDTO();
        brokenscreeniRespDTO.setProductSerialNo("356709089090332");
        brokenscreeniRespDTO.setCustomerTel("15111078421");
        brokenscreeniRespDTO.setProductModel("7P");
        brokenscreeniRespDTO.setProductBrand("苹果");
        String name = "欧阳理";
        String idCard = "431228199709251659";
        /**姓名脱敏**/
        if (JudgeUtils.isNotBlank(name)) {
            char[] chars = name.toCharArray();
            /**字大于2位**/
            if (chars.length > ParamHelper.ACCOUNT_LENGTH) {
                name = name.substring(0, 1);
                for (int i = 0; i < chars.length - 1; i++) {
                    name = name + "*";
                }
            } else if (chars.length == ParamHelper.ACCOUNT_LENGTH) {
                name = name.replaceAll(name.substring(1), "*");
            } else {
                name = new StringBuilder(name).append("*").toString();
            }
            brokenscreeniRespDTO.setCustomerName(name);
        }
        /**身份证脱敏**/
        if (JudgeUtils.isEmpty(idCard) || (idCard.length() < 8)) {
            brokenscreeniRespDTO.setCustomerCertiNo(idCard);
        } else {
            brokenscreeniRespDTO.setCustomerCertiNo(idCard.replaceAll("(?<=\\w{4})\\w(?=\\w{4})", "*"));
        }
        Date currentTime = new Date();
        System.out.println(currentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        System.out.println(dateString);
        System.out.println(DateTimeUtil.calculateDate(dateString, DateTimeUtil.CALC_DAY, -30));
        System.out.println(DateTimeUtil.calculateDate(dateString, DateTimeUtil.CALC_DAY, -30).compareTo("20210407") <= 0);
        return brokenscreeniRespDTO;
    }

}
