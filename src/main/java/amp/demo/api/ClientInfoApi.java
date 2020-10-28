package amp.demo.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author HLC
 */
@RequestMapping("/v1")
public interface ClientInfoApi {

    @PostMapping("/clientInfo/pageList")
    String pageList(String reqDTO);
}
