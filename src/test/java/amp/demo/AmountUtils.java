package amp.demo;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Create on 2018/12/29
 *
 * @author lizhen
 */
public class AmountUtils {
    private AmountUtils() {

    }

    public static BigDecimal add(BigDecimal... amount) {
        Amount result = new Amount(BigDecimal.ZERO);
        BigDecimal[] arr = amount;
        int len = amount.length;

        for (int i = 0; i < len; ++i) {
            Amount tmp = new Amount(Optional.ofNullable(arr[i]).orElse(BigDecimal.ZERO));
            result = result.add(tmp);
        }

        return new BigDecimal(result.toString());
    }

    public static BigDecimal sub(BigDecimal... amount) {
        BigDecimal[] arr = amount;
        Amount result = new Amount(Optional.ofNullable(arr[0]).orElse(BigDecimal.ZERO));
        int len = amount.length;

        for (int i = 1; i < len; ++i) {
            Amount tmp = new Amount(Optional.ofNullable(arr[i]).orElse(BigDecimal.ZERO));
            result = result.sub(tmp);
        }

        return new BigDecimal(result.toString());
    }

    public static BigDecimal mul(BigDecimal... amount) {
        BigDecimal[] arr = amount;
        Amount result = new Amount(arr[0]);
        int len = amount.length;

        for (int i = 1; i < len; ++i) {
            Amount tmp = new Amount(arr[i]);
            result = result.mul(tmp);
        }

        return new BigDecimal(result.toString());
    }

    public static BigDecimal div(BigDecimal... amount) {
        BigDecimal[] arr = amount;
        Amount result = new Amount(arr[0]);
        int len = amount.length;

        for (int i = 1; i < len; ++i) {
            Amount tmp = new Amount(arr[i]);
            result = result.div(tmp);
        }

        return new BigDecimal(result.toString());
    }

    public static int compareTo(BigDecimal opr1, BigDecimal opr2) {
        return Optional.ofNullable(opr1).orElse(BigDecimal.ZERO).
                compareTo(Optional.ofNullable(opr2).orElse(BigDecimal.ZERO));
    }


}