package amp.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HLC
 */
@RestController
@RequestMapping("/v1")
public class ClientInfoController {

    @PostMapping("/clientInfo/pageList")
    String pageList(String reqDTO){
    return "hello";
    }
}
