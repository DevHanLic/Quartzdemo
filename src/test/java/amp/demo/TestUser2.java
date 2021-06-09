package amp.demo;

import lombok.Data;

/**
 * @author han_lic
 * @date 2021/3/22 18:27
 */
@Data
public class TestUser2 {

    private User1 user1;

    private String type;

    @Data
    public static class User1 {

        private String transactionId;

        private String transactionId2;
    }
}
