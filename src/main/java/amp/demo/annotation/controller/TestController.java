package amp.demo.annotation.controller;

import amp.demo.annotation.MyBody;
import amp.demo.annotation.PermissionCheck;
import amp.demo.entity.UserTest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author han_lic
 * @date 2020/11/12 10:58
 */
@RestController
public class TestController {
    @PostMapping("/api/test")
    public Object test( @MyBody @Validated UserTest userTest) {
        return userTest.toString();
    }

    @PostMapping("/api/test1")
    public Object test1(@RequestBody  @Validated UserTest userTest) {
        return "hello world1";
    }

    @PostMapping("/api/test2")
    @PermissionCheck(resourceKey = "testKey")
    public Object testPermissionCheck() {
        return "hello world2";
    }
}
