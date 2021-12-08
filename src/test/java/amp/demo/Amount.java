package amp.demo;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author han_lic
 * @date 2021/8/25 17:41
 */
public class Amount {
    public static final int DEFAULT_SCALE = 2;
    private BigDecimal opr1;

    public Amount(int amt) {
        this.opr1 = new BigDecimal(amt);
    }

    public Amount(long amt) {
        this.opr1 = new BigDecimal(amt);
    }

    public Amount(double amt) {
        this.opr1 = new BigDecimal(Double.toString(amt));
    }

    public Amount(String amt) {
        this.opr1 = new BigDecimal(amt);
    }

    public Amount(BigInteger amt) {
        this.opr1 = new BigDecimal(amt);
    }

    public Amount(BigDecimal amt) {
        this.opr1 = amt;
    }

    BigDecimal getOpr() {
        return this.opr1;
    }

    @Override
    public String toString() {
        return this.opr1.toString();
    }

    public Amount add(Amount... opr2) {
        BigDecimal result = this.opr1;
        Amount[] arr$ = opr2;
        int len$ = opr2.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Amount tmp = arr$[i$];
            result = result.add(tmp.opr1);
        }

        return new Amount(result);
    }

    public Amount sub(Amount... opr2) {
        BigDecimal result = this.opr1;
        Amount[] arr$ = opr2;
        int len$ = opr2.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Amount tmp = arr$[i$];
            result = result.subtract(tmp.opr1);
        }

        return new Amount(result);
    }

    public Amount mulAndRound(Amount... opr2) {
        BigDecimal result = this.opr1;
        Amount[] arr$ = opr2;
        int len$ = opr2.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Amount tmp = arr$[i$];
            result = result.multiply(tmp.opr1);
        }

        result = result.divide(new BigDecimal("1"), 2, 4);
        return new Amount(result);
    }

    public Amount mul(Amount... opr2) {
        BigDecimal result = this.opr1;
        Amount[] arr$ = opr2;
        int len$ = opr2.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Amount tmp = arr$[i$];
            result = result.multiply(tmp.opr1);
        }

        return new Amount(result);
    }

    public Amount mulAndRound(int scale, Amount... opr2) {
        BigDecimal result = this.opr1;
        Amount[] arr$ = opr2;
        int len$ = opr2.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Amount tmp = arr$[i$];
            result = result.multiply(tmp.opr1);
        }

        result = result.divide(new BigDecimal("1"), scale, 4);
        return new Amount(result);
    }

    public Amount div(int scale, Amount... opr2) {
        if (opr2.length == 1) {
            return new Amount(this.opr1.divide(opr2[0].opr1, scale, 4));
        } else {
            BigDecimal result = this.opr1;
            Amount[] arr$ = opr2;
            int len$ = opr2.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Amount tmp = arr$[i$];
                result = result.divide(tmp.opr1, scale * 2, 4);
            }

            result = result.divide(new BigDecimal("1"), scale, 4);
            return new Amount(result);
        }
    }

    public Amount div(Amount... opr2) {
        if (opr2.length == 1) {
            return new Amount(this.opr1.divide(opr2[0].opr1, 2, 4));
        } else {
            BigDecimal result = this.opr1;
            Amount[] arr$ = opr2;
            int len$ = opr2.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Amount tmp = arr$[i$];
                result = result.divide(tmp.opr1, 4, 4);
            }

            result = result.divide(new BigDecimal("1"), 2, 4);
            return new Amount(result);
        }
    }

    public Amount round(int scale) {
        return new Amount(this.opr1.divide(new BigDecimal("1"), scale, 4));
    }

    public Amount round() {
        return new Amount(this.opr1.divide(new BigDecimal("1"), 2, 4));
    }

    public int compareTo(Amount opr2) {
        return this.opr1.compareTo(opr2.getOpr());
    }

    public Amount min(Amount... opr2) {
        BigDecimal tmp1 = this.opr1;
        Amount[] arr$ = opr2;
        int len$ = opr2.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Amount tmp = arr$[i$];
            tmp1 = tmp1.min(tmp.opr1);
        }

        return new Amount(tmp1);
    }

    public Amount max(Amount... opr2) {
        BigDecimal tmp1 = this.opr1;
        Amount[] arr$ = opr2;
        int len$ = opr2.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Amount tmp = arr$[i$];
            tmp1 = tmp1.max(tmp.opr1);
        }

        return new Amount(tmp1);
    }

    /**
     * Returns a {@code Amount} which is numerically equal to
     * this one but with any trailing zeros removed from the
     * representation.
     * @return
     */
    public Amount stripTrailingZeros() {
        return new Amount(new BigDecimal(opr1.stripTrailingZeros().toPlainString()));
    }
}
