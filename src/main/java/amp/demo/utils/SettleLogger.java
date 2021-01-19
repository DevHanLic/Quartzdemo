package amp.demo.utils;

/**
 * @author han_lic
 * @date 2021/1/8 11:39
 */
public class SettleLogger implements com.jcraft.jsch.Logger {

    @Override
    public boolean isEnabled(int level) {
        return true;
    }

    @Override
    public void log(int level, String msg) {
        System.out.println(msg);
    }

}
