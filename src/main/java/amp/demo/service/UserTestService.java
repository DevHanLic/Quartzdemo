package amp.demo.service;

import amp.demo.entity.UserTest;

import java.util.List;

/**
 * @author han_lic
 * @date 2020/12/10 10:55
 */
public interface UserTestService {

    void deal ();

    void insetUserTest(List<UserTest> checkCupDataBOList);

    void updateUserTest(List<UserTest> checkCupDataBOList);

}
