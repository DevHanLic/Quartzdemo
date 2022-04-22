package amp.demo;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
public class RSA {
    static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqZMsVdU0UedRrP+FTv4Vr2m77eeO/F9vwAUYjiK9YkT/YmxwBjGsTCj623Z5fGDTZOrTgWLvpRunXdWnZdKblXO3dSWLftErhkYR6mrCdsdSaYdz5pyevkWtwFu82xRD8f7C8qIEnqlWOmrMymZfe9e/Aeqh3qwPyB9Ofs3XYTJexI5FNaoL/2GijZN5QAyCB4VfXuyj+OPZzO18KPq+vnizPHeJC/7fVyf/vVmCrGtlnjv5ikerf8Y1xq8ehvO4xRh3uxsZA4+64cCZHmlf7w7a2Xf0MMgdOBhv326apT0aOvm1wE8sr9dFLfineTdGl1sQUd/vlwZ2qJAmZ0K+6QIDAQAB";
    static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpkyxV1TRR51Gs/4VO/hWvabvt5478X2/ABRiOIr1iRP9ibHAGMaxMKPrbdnl8YNNk6tOBYu+lG6dd1adl0puVc7d1JYt+0SuGRhHqasJ2x1Jph3PmnJ6+Ra3AW7zbFEPx/sLyogSeqVY6aszKZl97178B6qHerA/IH05+zddhMl7EjkU1qgv/YaKNk3lADIIHhV9e7KP449nM7Xwo+r6+eLM8d4kL/t9XJ/+9WYKsa2WeO/mKR6t/xjXGrx6G87jFGHe7GxkDj7rhwJkeaV/vDtrZd/QwyB04GG/fbpqlPRo6+bXATyyv10Ut+Kd5N0aXWxBR3++XBnaokCZnQr7pAgMBAAECggEAStkCJbSgjlI609BJCA/QhId4As9FZpG9YpUdPIeK7hgkXU4duC4urcMczsehYcvfItkfmCmSO19UZW+Lnr7yG2pjHJsA0m4C9xZwi/apdQIPlzLJbo3M55Eb2x7i4hQ01VistanbvihiPhCr6d574M7eqTrmTcHGiX+ABypWGbkI6kVgcuXLYpnzU1+dOu9CPjA3Ybtcw17Bsc6nxLgP/fH5sPrckKy6NNF43vnsVWayc7IfAWyctyeW2gWD71kMwXcr8Zha+/Ttt+4TfzXDTA4NLaeYWQbee+AAw663JF2R5p4ks4VaVnk99Tv6+QNuBlEfIWlUMWVf09gsoxdxAQKBgQDV/5t1GQ+J2wldj1V7U5XBm/NR7ww5aL7HEUMuusjzNSnD8Q68kqUdXHYkMPAsu7xCW1t8O2u5Yt1lUTphUvBD0U+T9hJcusanNTC150MlnCoBh2ZGUSMZRiZ2x8ktSWLJjg0Ms1fO6xP6GZ8mdunFWn/hreeojR8jjQ6jk9Bp4QKBgQDK237PuEwLAGlQov3jEX8Ilj2RD74ptGy2VeODARPC3QmFBpsLHhIOpAxwT4s+4icCrC0mTx6DYGweohLi8vVSnDmNwEUvC+z7zqdPMuz2+BdcozBclQVHmsUN5rPD+MErmgwcJWZ3/gZzeZMtOjZu0soNwjAkLndarBGDAmvGCQKBgQDDazrEMRFuexZPWN/f0hF0jvwguyI3nr5wvQPTvYBaEBdGtnAVpfigGlpMbGA3CW0+T3z4sq6JyY2Rwx6D16BO3epEnLZh9bII6VrFEh3QT45QfRvZZKifoN7pI0JEV7qdglnKWk/6RdOlVRZvorGRpjYXmZ5t3gkshthyaj4tIQKBgALVC7E0voMMz2ubyvWnoeFpz5Q95wbICRiGnHWQx0jaMy7TR8jg85mWusG6qpQHLkpAQyUcx61H5FqNIrxebgRoP/l1rMGaZgWh/f4CFQLhtdnKQXuZbQhx5bxq8ymSSIgmT3L5JrQYLNjh4qcn/+iOMtn8UxW+PFZcvqXYm86ZAoGABQBS8Qzbcz9cwDQfeqr2hltKTPrdugwv7k8fT9tgmxusiRM5qh+/t8ZSzrZNlqrOelOS1E9xw3hPLFMqu3mC2RZ6m1yEGBa7hi62FMF+hb94lufahnCvD76F+sm1D/rnNGrlbAzVRClcC2w/zhC/6VDnCPpcqlYEQwE5PjTKVSQ=";
    public static final String DEFAULT_ALGORITHM = "RSA/ECB/PKCS1Padding";

    public static final String DEFAULT_BASE64_CHARSET = "UTF-8";

    public static void main(String[] args) {
        try {
            String ciphertext = priEncrypt("利程哥牛皮");
            log.info("{}", ciphertext);

            String plaintext = pubDecrypt(ciphertext);
            log.info("{}", plaintext);

            ciphertext = pubEncrypt("利程哥牛皮");
            log.info("{}", ciphertext);

            plaintext = priDecrypt(ciphertext);
            log.info("{}", plaintext);

        } catch (Exception e) {
            log.error("", e);
        };
    }

    static String priEncrypt(String txt) throws Exception{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey secretKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(privateKey)
        ));

        Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(txt.getBytes(StandardCharsets.UTF_8)));
    };
    static String pubDecrypt(String txt) throws Exception{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey secretKey = keyFactory.generatePublic(new X509EncodedKeySpec(
                Base64.getDecoder().decode(publicKey)
        ));

        Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(txt)), StandardCharsets.UTF_8);
    };

    static String pubEncrypt(String txt) throws Exception {
        byte[] dataBytes = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey.getBytes(DEFAULT_BASE64_CHARSET));
        X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(dataBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        String encodeBase64String = org.apache.commons.codec.binary.Base64.encodeBase64String(cipher.doFinal(txt.getBytes(DEFAULT_BASE64_CHARSET)));
        return encodeBase64String;
    };

    static String priDecrypt(String txt) throws Exception {
        byte[] dataBytes = org.apache.commons.codec.binary.Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(dataBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        String decryptBase64String =  new String(cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(txt)), StandardCharsets.UTF_8);
        return decryptBase64String;
    };

}
