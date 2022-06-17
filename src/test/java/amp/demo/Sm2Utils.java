//package amp.demo;
//
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.bouncycastle.crypto.params.ECDomainParameters;
//import org.bouncycastle.math.ec.ECCurve;
//import org.bouncycastle.math.ec.ECPoint;
//import org.bouncycastle.util.encoders.Base64;
//import org.bouncycastle.util.encoders.Hex;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.math.BigInteger;
//import java.security.SecureRandom;
//import java.util.Arrays;
//
///**
// * @auther: hlc
// * @date: 2022/05/07 10:31
// * @description: Sm2Utils
// */
//@Slf4j
//public class Sm2Utils {
//
//    public static class SM2 {
//
//        private static final BigInteger n = new BigInteger("FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" +
//                "7203DF6B" + "21C6052B" + "53BBF409" + "39D54123", 16);
//
//        private static BigInteger p = new BigInteger(
//                "FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFF", 16);
//
//        private static BigInteger a = new BigInteger(
//                "FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFC", 16);
//
//        private static BigInteger b = new BigInteger(
//                "28E9FA9E" + "9D9F5E34" + "4D5A9E4B" + "CF6509A7" + "F39789F5" + "15AB8F92" + "DDBCBD41" + "4D940E93", 16);
//
//        private static BigInteger gx = new BigInteger(
//                "32C4AE2C" + "1F198119" + "5F990446" + "6A39C994" + "8FE30BBF" + "F2660BE1" + "715A4589" + "334C74C7", 16);
//
//        private static BigInteger gy = new BigInteger(
//                "BC3736A2" + "F4F6779C" + "59BDCEE3" + "6B692153" + "D0A9877C" + "C62A4740" + "02DF32E5" + "2139F0A0", 16);
//
//        private static ECDomainParameters ecc_bc_spec;
//
//        private static ECPoint G;
//
//        private static ECCurve.Fp curve;
//
//        private static final SecureRandom RANDOM = new SecureRandom();
//
//        private final boolean throwException;
//
//        public SM2(boolean throwException) {
//            curve = new ECCurve.Fp(p, a, b);
//            G = curve.createPoint(gx, gy);
//            ecc_bc_spec = new ECDomainParameters(curve, G, n);
//            this.throwException = false;
//        }
//
//        public SM2() {
//            this(false);
//        }
//
//        /**
//         * 生成16位随机IV
//         * @return
//         */
//        public static byte[] randomIV() {
//            return Hex.encode(new BigInteger(60, RANDOM).toByteArray());
//        }
//
//        /**
//         * 生成指定长度随机HEX
//         * @return
//         */
//        public static byte[] randomIV(final int length) {
//            int bitLength = (Math.max(length, 2)) * 4 - 2;
//            return Hex.encode(new BigInteger(bitLength, RANDOM).toByteArray());
//        }
//
//        /**
//         * 公钥加密
//         *
//         * @param input     明文
//         * @param publicKey 公钥
//         * @return
//         */
//        @SneakyThrows
//        public byte[] encrypt(String input, ECPoint publicKey) {
//            try {
//                byte[] inputBuffer = input.getBytes();
//                byte[] C1Buffer;
//                ECPoint kpb;
//                byte[] t;
//                do {
//                    // 1 产生随机数k，k属于[1, n-1]
//                    BigInteger k = random(n);
//
//                    // 2 计算椭圆曲线点 C1 = [k]G = (x1, y1)
//                    ECPoint C1 = G.multiply(k);
//                    C1Buffer = C1.getEncoded(false);
//
//                    // 3 计算椭圆曲线点 S = [h]Pb
//                    BigInteger h = ecc_bc_spec.getH();
//                    if (h != null) {
//                        ECPoint S = publicKey.multiply(h);
//                        if (S.isInfinity()) {
//                            throw new IllegalStateException();
//                        }
//                    }
//
//                    // 4 计算 [k]PB = (x2, y2)
//                    kpb = publicKey.multiply(k).normalize();
//
//                    // 5 计算 t = KDF(x2||y2, kLen)
//                    byte[] kpbBytes = kpb.getEncoded(false);
//                    t = KDF(kpbBytes, inputBuffer.length);
//                } while (allZero(t));
//
//                // 6 计算 C2 = M^t
//                byte[] C2 = new byte[inputBuffer.length];
//                for (int i = 0; i < inputBuffer.length; i++) {
//                    C2[i] = (byte) (inputBuffer[i] ^ t[i]);
//                }
//
//                // 7 计算 C3 = Hash(x2 || M || y2)
//                byte[] C3 = sm3hash(kpb.getXCoord().toBigInteger().toByteArray(), inputBuffer,
//                        kpb.getYCoord().toBigInteger().toByteArray());
//
//                // 8 输出密文 C = C1 || C2 || C3
//
//                byte[] encryptResult = new byte[C1Buffer.length + C2.length + C3.length];
//
//                System.arraycopy(C1Buffer, 0, encryptResult, 0, C1Buffer.length);
//                System.arraycopy(C2, 0, encryptResult, C1Buffer.length, C2.length);
//                System.arraycopy(C3, 0, encryptResult, C1Buffer.length + C2.length, C3.length);
//
//                return encryptResult;
//            } catch (Exception e) {
//                if (throwException) {
//                    throw e;
//                } else {
//                    return null;
//                }
//            }
//        }
//
//        /**
//         * 设置公钥文件
//         *
//         * @param path
//         * @return
//         */
//        @SneakyThrows
//        public ECPoint setPublicKeyFromFile(String path) {
//            File file = new File(path);
//            if (!file.exists()) {
//                return null;
//            }
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            byte[] buffer = new byte[16];
//            int size;
//            while ((size = fis.read(buffer)) != -1) {
//                baos.write(buffer, 0, size);
//            }
//            fis.close();
//            return curve.decodePoint(baos.toByteArray());
//        }
//
//        /**
//         * 设置公钥
//         *
//         * @param publicKey
//         * @return
//         */
//        public ECPoint setPublicKey(String publicKey) {
//            return curve.decodePoint(Base64.decode(publicKey.getBytes()));
//        }
//
//        /**
//         * 判断字节数组是否全0
//         *
//         * @param buffer
//         * @return
//         */
//        private boolean allZero(byte[] buffer) {
//            for (byte b : buffer) {
//                if (b != 0) {
//                    return false;
//                }
//            }
//            return true;
//        }
//
//        /**
//         * 生成随机数
//         *
//         * @param max
//         * @return
//         */
//        private static BigInteger random(BigInteger max) {
//            BigInteger r = new BigInteger(256, RANDOM);
//            while (r.compareTo(max) >= 0) {
//                r = new BigInteger(128, RANDOM);
//            }
//            return r;
//        }
//
//        /**
//         * 拼接字节数组
//         *
//         * @param params
//         * @return
//         */
//        @SneakyThrows
//        public static byte[] join(byte[]... params) {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            for (byte[] param : params) {
//                baos.write(param);
//            }
//            return baos.toByteArray();
//        }
//
//        /**
//         * sm3摘要
//         *
//         * @param params
//         * @return
//         */
//        @SneakyThrows
//        private static byte[] sm3hash(byte[]... params) {
//            return SM3.hash(join(params));
//        }
//
//        /**
//         * 密钥派生函数
//         *
//         * @param Z
//         * @param kLen 生成kLen字节数长度的密钥
//         * @return
//         */
//        @SneakyThrows
//        private static byte[] KDF(byte[] Z, int kLen) {
//            int ct = 1;
//            int end = (int) Math.ceil(kLen * 1.0 / 32);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            for (int i = 1; i < end; i++) {
//                baos.write(sm3hash(Z, SM3.toByteArray(ct)));
//                ct++;
//            }
//            byte[] last = sm3hash(Z, SM3.toByteArray(ct));
//            if (kLen % 32 == 0) {
//                baos.write(last);
//            } else {
//                baos.write(last, 0, kLen % 32);
//            }
//            return baos.toByteArray();
//        }
//    }
//
//    public static class SM3 {
//
//        private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
//                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//
//        private static final BigInteger IV = new BigInteger("7380166F" + "4914B2B9" + "172442D7" + "DA8A0600" +
//                "A96F30BC" + "163138AA" + "E38DEE4D" + "B0FB0E4E", 16);
//
//        private static final Integer Tj15 = Integer.valueOf("79CC4519", 16);
//
//        private static final Integer Tj63 = Integer.valueOf("7A879D8A", 16);
//
//        private static final byte[] FIRST_PADDING = {(byte) 0x80};
//
//        private static final byte[] ZERO_PADDING = {(byte) 0x00};
//
//        @SneakyThrows
//        public static byte[] hash(byte[] source) {
//            byte[] m1 = padding(source);
//            int n = m1.length / (512 / 8);
//            byte[] b;
//            byte[] vi = IV.toByteArray();
//            byte[] vi1 = null;
//            for (int i = 0; i < n; i++) {
//                b = Arrays.copyOfRange(m1, i * 64, (i + 1) * 64);
//                vi1 = CF(vi, b);
//                vi = vi1;
//            }
//            return vi1;
//        }
//
//        @SneakyThrows
//        private static byte[] padding(byte[] source) {
//            if (source.length >= 0x2000000000000000L) {
//                throw new IllegalArgumentException("Invalid src data");
//            }
//            long l = source.length * 8L;
//            long k = 448 - (l + 1) % 512;
//            if (k < 0) {
//                k = k + 512;
//            }
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            baos.write(source);
//            baos.write(FIRST_PADDING);
//            long i = k - 7;
//            while (i > 0) {
//                baos.write(ZERO_PADDING);
//                i -= 8;
//            }
//            baos.write(long2bytes(l));
//            return baos.toByteArray();
//        }
//
//        private static byte[] long2bytes(long l) {
//            byte[] bytes = new byte[8];
//            for (int i = 0; i < 8; i++) {
//                bytes[i] = (byte) (l >>> ((7 - i) * 8));
//            }
//            return bytes;
//        }
//
//        private static int T(int j) {
//            if (j >= 0 && j <= 15) {
//                return Tj15;
//            } else if (j >= 16 && j <= 63) {
//                return Tj63;
//            } else {
//                throw new IllegalArgumentException("Invalid data");
//            }
//        }
//
//        private static Integer FF(Integer x, Integer y, Integer z, int j) {
//            if (j >= 0 && j <= 15) {
//                return x ^ y ^ z;
//            } else if (j >= 16 && j <= 63) {
//                return (x & y)
//                        | (x & z)
//                        | (y & z);
//            } else {
//                throw new IllegalArgumentException("Invalid data");
//            }
//        }
//
//        private static Integer GG(Integer x, Integer y, Integer z, int j) {
//            if (j >= 0 && j <= 15) {
//                return x ^ y ^ z;
//            } else if (j >= 16 && j <= 63) {
//                return (x & y)
//                        | (~x & z);
//            } else {
//                throw new IllegalArgumentException("Invalid data");
//            }
//        }
//
//        private static Integer P0(Integer x) {
//            return x
//                    ^ Integer.rotateLeft(x, 9)
//                    ^ Integer.rotateLeft(x, 17);
//        }
//
//        private static Integer P1(Integer x) {
//            return x
//                    ^ Integer.rotateLeft(x, 15)
//                    ^ Integer.rotateLeft(x, 23);
//        }
//
//        private static byte[] CF(byte[] vi, byte[] bi) throws IOException {
//            int a, b, c, d, e, f, g, h;
//            a = toInteger(vi, 0);
//            b = toInteger(vi, 1);
//            c = toInteger(vi, 2);
//            d = toInteger(vi, 3);
//            e = toInteger(vi, 4);
//            f = toInteger(vi, 5);
//            g = toInteger(vi, 6);
//            h = toInteger(vi, 7);
//
//            int[] w = new int[68];
//            int[] w1 = new int[64];
//            for (int i = 0; i < 16; i++) {
//                w[i] = toInteger(bi, i);
//            }
//            for (int j = 16; j < 68; j++) {
//                w[j] = P1(w[j - 16] ^ w[j - 9] ^ Integer.rotateLeft(w[j - 3], 15))
//                        ^ Integer.rotateLeft(w[j - 13], 7) ^ w[j - 6];
//            }
//            for (int j = 0; j < 64; j++) {
//                w1[j] = w[j] ^ w[j + 4];
//            }
//            int ss1, ss2, tt1, tt2;
//            for (int j = 0; j < 64; j++) {
//                ss1 = Integer
//                        .rotateLeft(
//                                Integer.rotateLeft(a, 12) + e
//                                        + Integer.rotateLeft(T(j), j), 7);
//                ss2 = ss1 ^ Integer.rotateLeft(a, 12);
//                tt1 = FF(a, b, c, j) + d + ss2 + w1[j];
//                tt2 = GG(e, f, g, j) + h + ss1 + w[j];
//                d = c;
//                c = Integer.rotateLeft(b, 9);
//                b = a;
//                a = tt1;
//                h = g;
//                g = Integer.rotateLeft(f, 19);
//                f = e;
//                e = P0(tt2);
//            }
//            byte[] v = toByteArray(a, b, c, d, e, f, g, h);
//            for (int i = 0; i < v.length; i++) {
//                v[i] = (byte) (v[i] ^ vi[i]);
//            }
//            return v;
//        }
//
//        private static int toInteger(byte[] source, int index) {
//            StringBuilder valueStr = new StringBuilder();
//            for (int i = 0; i < 4; i++) {
//                valueStr.append(HEX_DIGITS[(byte) ((source[index * 4 + i] & 0xF0) >> 4)]);
//                valueStr.append(HEX_DIGITS[(byte) (source[index * 4 + i] & 0x0F)]);
//            }
//            return Long.valueOf(valueStr.toString(), 16).intValue();
//        }
//
//        public static byte[] toByteArray(int i) {
//            byte[] byteArray = new byte[4];
//            byteArray[0] = (byte) (i >>> 24);
//            byteArray[1] = (byte) ((i & 0xFFFFFF) >>> 16);
//            byteArray[2] = (byte) ((i & 0xFFFF) >>> 8);
//            byteArray[3] = (byte) (i & 0xFF);
//            return byteArray;
//        }
//
//        private static byte[] toByteArray(int a, int b, int c, int d, int e, int f,
//                                          int g, int h) throws IOException {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream(32);
//            baos.write(toByteArray(a));
//            baos.write(toByteArray(b));
//            baos.write(toByteArray(c));
//            baos.write(toByteArray(d));
//            baos.write(toByteArray(e));
//            baos.write(toByteArray(f));
//            baos.write(toByteArray(g));
//            baos.write(toByteArray(h));
//            return baos.toByteArray();
//        }
//    }
//
//}
//
