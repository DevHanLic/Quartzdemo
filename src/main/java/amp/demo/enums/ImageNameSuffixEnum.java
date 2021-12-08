package amp.demo.enums;
import lombok.Getter;

/**
 * 图片名后缀
 *
 * Created on 2020/7/28
 *
 * @author: qinpo
 */
@Getter
public enum ImageNameSuffixEnum {

    /**
     * 后缀名
     */
    JPG(".jpg"),
    PNG(".png"),
    JPEG(".jpeg"),
    GIF(".gif");

    ImageNameSuffixEnum(String suffix) {
        this.suffix = suffix;
    }

    private final String suffix;
}
