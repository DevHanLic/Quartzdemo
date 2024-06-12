package amp.demo;

import amp.demo.entity.UserTest;
import amp.demo.utils.DateTimeUtil;
import amp.demo.utils.DateTimeUtils;
import amp.demo.utils.JudgeUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

public class TestSteam {
    private final Random rand = SecureRandom.getInstanceStrong();

    public TestSteam() throws NoSuchAlgorithmException {
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void conversionTokenPayData(String tokenPayData) {
        /****/
        /**判断第一个字符是否为“{”**/
        if (tokenPayData.startsWith("{")) {
            tokenPayData = tokenPayData.substring(1);
        }
        /**判断最后一个字符是否为“}” **/
        if (tokenPayData.endsWith("}")) {
            tokenPayData = tokenPayData.substring(0, tokenPayData.length() - 1);
        }
        String token = "";
        String trId = "";
        String tokenEnd = "";
        boolean status = tokenPayData.contains("&");
        boolean temp = tokenPayData.contains("=");
        if (status) {
            String[] strArr = tokenPayData.split("&");
            for (int i = 0; i < strArr.length; i++) {
                String tempString = strArr[i];
                boolean tempStatus = tempString.contains("=");
                if (tempStatus) {
                    String[] tempStrArr = tempString.split("=");
                    if ("token".equals(tempStrArr[0])) {
                        if (tempStrArr.length > 1) {
                            token = tempStrArr[1];
                        }
                    } else if ("trId".equals(tempStrArr[0])) {
                        if (tempStrArr.length > 1) {
                            trId = tempStrArr[1];
                        }
                    } else if ("tokenEnd".equals(tempStrArr[0])) {
                        if (tempStrArr.length > 1) {
                            tokenEnd = tempStrArr[1];
                        }
                    }
                }
            }
        } else if (temp) {
            String[] tempStrArr = tokenPayData.split("=");
            if ("token".equals(tempStrArr[0])) {
                if (tempStrArr.length > 1) {
                    token = tempStrArr[1];
                }
            } else if ("trId".equals(tempStrArr[0])) {
                if (tempStrArr.length > 1) {
                    trId = tempStrArr[1];
                }
            } else if ("tokenEnd".equals(tempStrArr[0])) {
                if (tempStrArr.length > 1) {
                    tokenEnd = tempStrArr[1];
                }
            }
        }

        System.out.println(token);
        System.out.println(trId);
        System.out.println(tokenEnd);
    }

    public static void conversionString(String instalTransInfo) {
        /**{numberOfInstallments=06&mchntFeeSubsidy=004320}**/
        /**判断第一个字符是否为“{”**/
        if (instalTransInfo.startsWith("{")) {
            instalTransInfo = instalTransInfo.substring(1);
        }
        /**判断最后一个字符是否为“}” **/
        if (instalTransInfo.endsWith("}")) {
            instalTransInfo = instalTransInfo.substring(0, instalTransInfo.length() - 1);
        }
        String numberOfInstallments = "";
        String instalRate = "";
        String mchntFeeSubsidy = "";
        boolean status = instalTransInfo.contains("&");
        boolean temp = instalTransInfo.contains("=");
        if (status) {
            String[] strArr = instalTransInfo.split("&");
            for (int i = 0; i < strArr.length; i++) {
                String tempString = strArr[i];
                boolean tempStatus = tempString.contains("=");
                if (tempStatus) {
                    String[] tempStrArr = tempString.split("=");
                    if ("numberOfInstallments".equals(tempStrArr[0])) {
                        if (tempStrArr.length > 1) {
                            numberOfInstallments = tempStrArr[1];
                        }
                    } else if ("instalRate".equals(tempStrArr[0])) {
                        if (tempStrArr.length > 1) {
                            instalRate = tempStrArr[1];
                        }
                    } else if ("mchntFeeSubsidy".equals(tempStrArr[0])) {
                        if (tempStrArr.length > 1) {
                            mchntFeeSubsidy = tempStrArr[1];
                        }
                    }
                }
            }
        } else if (temp) {
            String[] tempStrArr = instalTransInfo.split("=");
            if ("numberOfInstallments".equals(tempStrArr[0])) {
                if (tempStrArr.length > 1) {
                    numberOfInstallments = tempStrArr[1];
                }
            } else if ("instalRate".equals(tempStrArr[0])) {
                if (tempStrArr.length > 1) {
                    instalRate = tempStrArr[1];
                }
            } else if ("mchntFeeSubsidy".equals(tempStrArr[0])) {
                if (tempStrArr.length > 1) {
                    mchntFeeSubsidy = tempStrArr[1];
                }
            }
        }

        System.out.println(numberOfInstallments);
        System.out.println(instalRate);
        System.out.println(mchntFeeSubsidy);
    }


    public static void main(String[] args) throws ParseException {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Integer> listT = Arrays.asList(1, 4, 5);
        List<Integer> collect6 = listT.stream().filter(s -> list.stream().noneMatch(m -> m.equals(s))).collect(toList());

        collect6.stream().forEach(System.out::println);

        System.out.println("''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
        UserTest userTest = new UserTest();
        list.stream().forEach(s -> {
            listT.stream().forEach(t -> {
                if (s.equals(t))
                    userTest.setId(String.valueOf(s));
            });
        });
        System.out.println("'''userTest'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''" + userTest.toString());
        List<Integer> collect1 = list.stream().filter(item -> !listT.contains(item)).collect(toList());

        System.out.println(collect1);

        // 并集
        List<Integer> listAll = list.parallelStream().collect(Collectors.toList());
        List<Integer> listAll2 = listT.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);
        listAll.stream().forEach(System.out::println);

        List<Integer> collect2 = listAll.stream().distinct().collect(toList());
        collect2.stream().forEach(System.out::println);

        List<Integer> s = list.stream()
                .map(t -> t * t)
//                .filter(t->t>=16)
//                .forEach(System.out::println);
                .collect(Collectors.toList());
        System.out.println(s);
        //*
        List<User> listTow = new ArrayList<>();
        listTow.add(User.builder()
                .bankUserName("甲1").password("1").flag(1).build());
        listTow.add(User.builder()
                .bankUserName("甲2").password("2").flag(2).build());
        listTow.add(User.builder()
                .bankUserName("甲3").password("3").flag(3).build());
        listTow.add(User.builder()
                .bankUserName("甲5").password("3").flag(3).build());
        listTow.add(User.builder()
                .bankUserName("甲3").password("3").flag(4).build());
        listTow.add(User.builder()
                .bankUserName("甲4").password("4").flag(4).build());

        System.out.println("-----------------------------------------");

        List<User> listTow1 = new ArrayList<>();
        listTow1.add(User.builder()
                .bankUserName("甲1").password("1").flag(1).build());
        listTow1.add(User.builder()
                .bankUserName("甲2").password("2").flag(2).build());
        listTow1.add(User.builder()
                .bankUserName("甲3").password("3").flag(3).build());
        List<User> listTow2 = new ArrayList<>();
        listTow2.add(User.builder()
                .bankUserName("甲1").password("1").flag(1).build());
        listTow2.add(User.builder()
                .bankUserName("甲2").password("2").flag(2).build());
        listTow2.add(User.builder()
                .bankUserName("甲3").password("3").flag(3).build());
        listTow2.add(User.builder()
                .bankUserName("甲4").password("4").flag(4).build());
        listTow2.add(User.builder()
                .bankUserName("甲5").password("5").flag(5).build());
        //listTow2 - listTow1  差集
        List<User> collect5 = listTow2.stream().filter(item -> !listTow1.contains(item)).collect(toList());

        System.out.println("collect5------------" + collect5);


        System.out.println("---------------------------------");
//        List<String> collect = listTow.stream().map(User::getBankUserName).sorted().collect(Collectors.toList());
//        System.out.println(collect);

        listTow.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getPassword())));
        System.out.println(listTow.stream().collect(toList()));

        List<Integer> collect9 = listTow.stream().map(User::getFlag).collect(toList());
        System.out.println("collect9--------" + collect9.toString());


//
//        Optional<Integer> reduce1 = listTow.stream().map(User::getFlag).reduce(Integer::sum);
//        System.out.println(reduce1.get());
//
//        int sum = listTow.stream().mapToInt(User::getFlag).sum();
//        System.out.println(sum);
//
        System.out.println("---------------------------------------------");
        //按照对象的属性，对对象列表进行去重
        listTow.stream().forEach(System.out::println);
        System.out.println("---------------------------------------------");
        List<User> collect = listTow.stream().filter(distinctByKey(User::getFlag)).collect(toList());
        collect.stream().forEach(System.out::println);
        System.out.println("---------------------------------------------");

        List<String> listTows = new ArrayList<>();
        listTow.stream().findFirst().ifPresent(t -> listTows.add(t.getBankUserName()));
        listTows.stream().forEach(System.out::println);

        OptionalInt max = listTow.stream().mapToInt(User::getFlag).max();
        System.out.println("max:" + max.getAsInt());

        List<User> c = listTow.stream().filter(t -> JudgeUtils.equals(t.getBankUserName(), "甲3")).collect(toList());
        System.out.println("sss" + c);
//        int i = max.orElse(3);
//        System.out.println(i);
//        List<User> collect1 = listTow.stream().filter(t -> t.getFlag()>2).skip(1).collect(Collectors.toList());
//        System.out.println(collect1);
//        IntSummaryStatistics collect2 = listTow.stream().collect(summarizingInt(User::getFlag));
//        System.out.println(collect2);
//
        Map<String, User> collect3 = listTow.stream().collect(
                groupingBy(t -> t.getBankUserName(), collectingAndThen(minBy(Comparator.comparingInt(User::getFlag)), Optional::get)));

        Map<String, User> collect4 = listTow.stream().collect(
                groupingBy(t -> t.getBankUserName(), collectingAndThen(maxBy(Comparator.comparingInt(User::getFlag)), Optional::get)));

        System.out.println(collect3);
        System.out.println(collect3.get("甲4"));

        Date currentTime = new Date();
        System.out.println(currentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        System.out.println(dateString);


        String beginTime = "2020-12-04 17:05:19";
        System.out.println(dateString.compareTo(beginTime) > 0);

        String beginTimesOne = "2020-1-1 00:00:00";

        String beginTimeTwo = "2020-01-01 00:00:00";

        System.out.println(dateString.compareTo(beginTimesOne));

        System.out.println(dateString.compareTo(beginTimeTwo));

        System.out.println(currentTime.compareTo(formatter.parse(beginTimesOne)));

        String endTimesOne = "2020-1-1 23:59:59";

        String endTimeTwo = "2020-01-01 23:59:59";

        System.out.println(dateString.compareTo(endTimesOne));

        System.out.println(dateString.compareTo(endTimeTwo));

        System.out.println(currentTime.compareTo(formatter.parse(endTimesOne)));

        String fileDate = "20190903";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(fileDate));
        //计算前一个月的日期
        calendar.add(Calendar.MONTH, -1);
        fileDate = sdf.format(calendar.getTime());
        System.out.println(fileDate);
        String format = LocalDate.parse(fileDate, DateTimeUtils.DEFAULT_DATE_FORMATTER).minusMonths(1L).format(DateTimeUtils.DEFAULT_DATE_FORMATTER);
        System.out.println(format);
        boolean contains = list.contains(4);

        System.out.println(contains);

        boolean a = list.stream().filter(t -> t == 4).findAny().isPresent();
        boolean b = list.stream().anyMatch(t -> t == 4);
        System.out.println(a);
        System.out.println(b);
        boolean b1 = list.stream().allMatch(t -> t == 1);
        System.out.println(b1);
        //3.获取两个List<User>中得所有名字name集合
        List<String> collect7 = Stream.of(listTow, listTow1).flatMap(r -> r.stream().map(User::getBankUserName)).distinct().collect(toList());
        System.out.println(collect7);

//        int b= 45;//制表符
//        char cb =  (char)b;
        String str = "-15.15-";
        System.out.println(str.replaceAll(String.valueOf((char) 45), ""));

        char character = 'a';
        int ascii = (int) character;
        System.out.println(ascii);

        List<Integer> listA = Arrays.asList(1, 4, 5);
        List<Integer> listB = Arrays.asList();
        List<String> collect8 = Optional.of(listTow1)
                .orElse(new ArrayList<>()).stream().map(User::getBankUserName).collect(toList());
        System.out.println(collect8.toString());
        List<Integer> integersq = Optional.of(listB).orElse(new ArrayList<>());
        System.out.println(integersq.toString());

        List<Integer> sss = Arrays.asList(1, 4, 5);
        listA.stream().forEach(t -> {
            if (t > 3) {
                System.out.println("---" + t);
                return;
            }
            System.out.println(t);
        });

        for (Integer s1 : sss) {
            if (s1 > 3) {
                System.out.println("---" + s1);
                continue;
            }
            System.out.println(s1);
        }
        TestUser testUser = new TestUser();
        testUser.setB("0");
        System.out.println(Integer.parseInt(testUser.getB()));

        List<User> listTow3 = new ArrayList<>();
        listTow3.add(User.builder()
                .bankUserName("甲1").password("1").flag(1).build());
        listTow3.add(User.builder()
                .bankUserName("甲2").password("2").flag(2).build());
        listTow3.add(User.builder()
                .bankUserName("甲3").password("1").flag(3).build());
        List<User> collect10 = listTow3.stream().filter(s1 -> JudgeUtils.equals(s1.getPassword(), "1")).collect(toList());
        System.out.println("collect10-------------");
        collect10.stream().forEach(System.out::println);

        String brandSerFeeD = "30.5";
        String brandSerFeeC = "0.2";
        String brandSerFeet = "30.1";

        String brandSerFee1 = "30.5";
        String brandSerFee2 = "0.2";
        String brandSerFee3 = "30.1";

        testUser.setMerchantBonusAmount(new BigDecimal(String.valueOf(new BigDecimal(brandSerFeeD).subtract(new BigDecimal(brandSerFeeC).add(new BigDecimal(brandSerFeet)))))
                .add(new BigDecimal(brandSerFee1)).subtract(new BigDecimal(brandSerFee2).add(new BigDecimal(brandSerFee3))));
        System.out.println(testUser.getMerchantBonusAmount().toString());

        String s1 = new String(Base64.decodeBase64("aHR0cHM6Ly9pbnN0YWxsLmFwcGNlbnRlci5tcy91c2Vycy9jbGFzaHgvYXBwcy9jbGFzaHgtcHJvL2Rpc3RyaWJ1dGlvbl9ncm91cHMvcHVibGlj"));
        System.out.println(s1);

        if (new BigDecimal(0.05).compareTo(new BigDecimal(0.05)) == 0) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }

        Object f1 = null, f2 = null, f3 = null;
        boolean f = f1 == null && f2 == null && f3 == null;
        System.out.println(f);

        BigDecimal payAmount = new BigDecimal(2.51);
        if (payAmount.compareTo(new BigDecimal(2.51)) == 0) {
            Thread t1 = new Thread() {
                @Override
                public void run() {
                    System.out.println("线程执行：" + LocalDateTime.now());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程结束：" + LocalDateTime.now());
                }
            };
            t1.start();
        }

        //conversionTokenPayData("{token=}");
        conversionString("{numberOfInstallments=}");
        TestUser user = new TestUser();
        user.setMerchantBonusAmount(new BigDecimal("1.06"));
        System.out.println(user.getMerchantBonusAmount().setScale(2));
        user = null;
        System.out.println(JudgeUtils.isNull(user) || JudgeUtils.isNull(user.getMerchantBonusAmount()) ?
                1 : 2);

        TestUser testUser1 = new TestUser();
        if (JudgeUtils.isNotEmpty(testUser1.getUserList())) {
            for (User testUser2 : testUser1.getUserList()) {
                System.out.println("1");
            }
        }
        TestUser testUser2 = new TestUser();
        testUser2.setA(13974879400L);
        if (13974879400L == testUser2.getA()) {
            System.out.println("1------");
        }

        String s8 = "中移电子商务有限公司8";
        String s2 = "中国石化销售股份有限公司吉林长春石油分公司";
        System.out.println(s8.getBytes(StandardCharsets.UTF_8).length);
        checkTimeRange("040000","020000","HHmmss");

        System.out.println("1111  "+countErrorRate(1,1));

        BigDecimal constant = new BigDecimal(100);
        BigDecimal errorRate = new BigDecimal(1)
                .divide(new BigDecimal(1)).setScale(4).multiply(constant).setScale(2);
        System.out.println("222  "+errorRate.toString());

        Long aw= 0l;
        Integer as = aw.intValue();
        System.out.println("as"+as);
        BigDecimal bigDecimal = new BigDecimal("73.00");
        String strs = "73.00";
        int i = strs.compareTo("73.00");
        System.out.println(i);
        BigDecimal bigDecimal1 = new BigDecimal("0.02");
        System.out.println(bigDecimal1.compareTo(new BigDecimal(0.02)));

        String u = "说三道四大大啊";
        String bankQuickPaymentAgreementCountLeft = StringUtils.left(u, 4);
        String bankQuickPaymentAgreementCountRight = StringUtils.right(u,3);
        System.out.println("bankQuickPaymentAgreementCountLeft"+bankQuickPaymentAgreementCountLeft);
        System.out.println("bankQuickPaymentAgreementCountRight"+bankQuickPaymentAgreementCountRight);


        String y="%3Cp%3E%26nbsp%3B%3C%2Fp%3E%3Cp%3E%3C%2Fp%3E%3Cp%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-ALIGN%3A%20center%3B%20LINE-HEIGHT%3A%20150%25%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2024px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E9%9D%9E%E9%93%B6%E8%A1%8C%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-ALIGN%3A%20center%3B%20LINE-HEIGHT%3A%20150%25%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2024px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E%E5%AE%A2%E6%88%B7%E6%8E%88%E6%9D%83%E5%8D%8F%E8%AE%AE%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22LINE-HEIGHT%3A%20150%25%3B%20TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E%26nbsp%3B%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E%E7%94%B2%E6%96%B9%EF%BC%9A%E4%B8%AA%E4%BA%BA%E5%AE%A2%E6%88%B7%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E4%B9%99%E6%96%B9%EF%BC%9A%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E8%82%A1%E4%BB%BD%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8_____%E5%88%86%E6%94%AF%E8%A1%8C%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%3B%20COLOR%3A%20%5C%22%3E%26nbsp%3B%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22MARGIN%3A%20auto%20auto%20auto%2088px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-FAMILY%3A%20%E5%AE%8B%E4%BD%93color%3A%23000000%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%3Cspan%20style%3D%5C%22undefinedfont-size%3A%2016px%5C%22%3E%E7%AC%AC%E4%B8%80%E6%9D%A1%3Cspan%3E%26nbsp%3B%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fspan%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E4%B8%9A%E5%8A%A1%E5%AE%9A%E4%B9%89%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E%E5%AE%A2%E6%88%B7%EF%BC%9A%E6%8C%87%E5%9C%A8%E6%9C%AC%E5%8D%8F%E8%AE%AE%E4%B8%AD%E6%8E%88%E6%9D%83%E4%B9%99%E6%96%B9%EF%BC%88%E4%BB%A5%E5%8F%8A%E4%B9%99%E6%96%B9%E6%80%BB%E8%A1%8C%E7%9A%84%E5%90%84%E5%88%86%E6%94%AF%E6%9C%BA%E6%9E%84%EF%BC%89%E5%AF%B9%E5%85%B6%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E8%B4%A6%E6%88%B7%E8%BF%9B%E8%A1%8C%E6%89%A3%E6%AC%BE%E7%9A%84%E4%B8%AA%E4%BA%BA%E5%AE%A2%E6%88%B7%EF%BC%8C%E6%9C%AC%E5%8D%8F%E8%AE%AE%E4%B8%AD%E6%8C%87%E7%94%B2%E6%96%B9%E3%80%82%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%EF%BC%9A%E9%9D%9E%E9%93%B6%E8%A1%8C%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%EF%BC%88%E4%BB%A5%E4%B8%8B%E7%AE%80%E7%A7%B0%E2%80%9C%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E2%80%9D%EF%BC%89%E6%98%AF%E6%8C%87%E4%BE%9D%E6%8D%AE%E4%B8%AD%E5%9B%BD%E4%BA%BA%E6%B0%91%E9%93%B6%E8%A1%8C%E3%80%8A%E9%9D%9E%E9%87%91%E8%9E%8D%E6%9C%BA%E6%9E%84%E6%94%AF%E4%BB%98%E6%9C%8D%E5%8A%A1%E7%AE%A1%E7%90%86%E5%8A%9E%E6%B3%95%E3%80%8B%E8%A7%84%E5%AE%9A%EF%BC%8C%E5%8F%96%E5%BE%97%E3%80%8A%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%E8%AE%B8%E5%8F%AF%E8%AF%81%E3%80%8B%EF%BC%8C%E8%8E%B7%E5%87%86%E5%8A%9E%E7%90%86%E4%BA%92%E8%81%94%E7%BD%91%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%E7%9A%84%E9%9D%9E%E9%87%91%E8%9E%8D%E6%9C%BA%E6%9E%84%EF%BC%8C%E6%9C%AC%E5%8D%8F%E8%AE%AE%E4%B8%AD%E6%8C%87%3C%2Fspan%3E%3Cspan%20style%3D%5C%22TEXT-DECORATION%3A%20underline%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%3B%20text-underline%3A%20single%5C%22%3E%E4%B8%AD%E7%A7%BB%E7%94%B5%E5%AD%90%E5%95%86%E5%8A%A1%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%3C%2Fspan%3E%3C%2Fspan%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%EF%BC%9A%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E6%98%AF%E6%8C%87%E4%BE%9D%E6%89%98%E6%B8%85%E7%AE%97%E6%9C%BA%E6%9E%84%E8%BD%AC%E6%8E%A5%E5%BC%80%E5%B1%95%E7%9A%84%EF%BC%8C%E4%B9%99%E6%96%B9%3C%2Fspan%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%EF%BC%88%E4%BB%A5%E5%8F%8A%E4%B9%99%E6%96%B9%E6%80%BB%E8%A1%8C%E7%9A%84%E5%90%84%E5%88%86%E6%94%AF%E6%9C%BA%E6%9E%84%EF%BC%89%3C%2Fspan%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E4%BD%9C%E4%B8%BA%E7%94%B2%E6%96%B9%E8%B4%A6%E6%88%B7%E7%9A%84%E5%BC%80%E6%88%B7%E6%9C%BA%E6%9E%84%EF%BC%8C%E7%94%B2%E6%96%B9%E5%AF%B9%E4%B9%99%E6%96%B9%E8%BF%9B%E8%A1%8C%E6%8E%88%E6%9D%83%E5%90%8E%EF%BC%8C%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%90%91%E4%B9%99%E6%96%B9%E5%8F%91%E9%80%81%E6%94%AF%E4%BB%98%E6%8C%87%E4%BB%A4%EF%BC%8C%E6%89%A3%E5%88%92%E7%94%B2%E6%96%B9%E5%9C%A8%E4%B9%99%E6%96%B9%E7%9A%84%E8%B4%A6%E6%88%B7%E8%B5%84%E9%87%91%E5%B9%B6%E9%80%9A%E8%BF%87%E6%B8%85%E7%AE%97%E6%9C%BA%E6%9E%84%E5%88%92%E8%BD%AC%E8%87%B3%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E7%9A%84%E4%B8%9A%E5%8A%A1%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%3B%20FONT-WEIGHT%3A%20bold%3B%20COLOR%3A%20%5C%22%3E%26nbsp%3B%3C%2Fspan%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%AC%AC%E4%BA%8C%E6%9D%A1%20%E7%94%B2%E6%96%B9%E7%9A%84%E6%9D%83%E5%88%A9%E5%92%8C%E4%B9%89%E5%8A%A1%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E1.%E7%94%B2%E6%96%B9%E5%BA%94%E6%8B%A5%E6%9C%89%E4%B9%99%E6%96%B9%E8%B4%A6%E6%88%B7%EF%BC%8C%E5%B9%B6%E9%81%B5%E5%AE%88%E4%B9%99%E6%96%B9%E5%85%B3%E4%BA%8E%E8%B4%A6%E6%88%B7%E7%9A%84%E7%AB%A0%E7%A8%8B%E4%B8%8E%E7%BA%A6%E5%AE%9A%E3%80%82%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E2.%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%94%B2%E6%96%B9%E5%BA%94%E4%B8%A5%E6%A0%BC%E9%81%B5%E5%AE%88%E4%B9%99%E6%96%B9%E6%9C%89%E5%85%B3%E8%B4%A6%E6%88%B7%E5%AE%89%E5%85%A8%E7%AE%A1%E7%90%86%E8%A7%84%E5%AE%9A%EF%BC%8C%E4%B8%8D%E5%BE%97%E5%B0%86%E6%9C%AC%E4%BA%BA%E8%B4%A6%E6%88%B7%E7%9B%B8%E5%85%B3%E5%AF%86%E7%A0%81%E3%80%81%E7%9F%AD%E4%BF%A1%E9%AA%8C%E8%AF%81%E7%A0%81%E7%AD%89%E9%87%8D%E8%A6%81%E4%BF%A1%E6%81%AF%E5%90%91%E4%BB%96%E4%BA%BA%E9%80%8F%E9%9C%B2%EF%BC%8C%E5%90%A6%E5%88%99%E7%94%B1%E6%AD%A4%E5%AF%BC%E8%87%B4%E7%9A%84%E6%8D%9F%E5%A4%B1%E7%94%B1%E7%94%B2%E6%96%B9%E6%89%BF%E6%8B%85%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E3.%E7%94%B2%E6%96%B9%E5%90%91%E4%B9%99%E6%96%B9%E6%89%BF%E8%AF%BA%3A%E5%85%B6%E5%90%91%E4%B9%99%E6%96%B9%E6%8F%90%E4%BA%A4%E8%BA%AB%E4%BB%BD%E4%BF%A1%E6%81%AF%E3%80%81%E9%80%9A%E8%BF%87%E7%9F%AD%E4%BF%A1%E9%AA%8C%E8%AF%81%E7%A0%81%E6%96%B9%E5%BC%8F%E8%BF%9B%E8%A1%8C%E6%8E%88%E6%9D%83%E3%80%81%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E7%AD%BE%E7%BA%A6%E8%A1%8C%E4%B8%BA%E5%9D%87%E4%B8%BA%E7%94%B2%E6%96%B9%E7%9C%9F%E5%AE%9E%E6%84%8F%E6%84%BF%E8%A1%A8%E7%A4%BA%EF%BC%8C%E6%8E%88%E6%9D%83%E5%8F%8A%E7%AD%BE%E7%BA%A6%E6%8C%87%E4%BB%A4%E7%9C%9F%E5%AE%9E%E3%80%81%E5%90%88%E6%B3%95%E4%B8%94%E6%9C%89%E6%95%88%EF%BC%8C%E7%94%B2%E6%96%B9%E5%B7%B2%E7%BB%8F%E5%85%85%E5%88%86%E4%BA%86%E8%A7%A3%E5%92%8C%E6%98%8E%E7%A1%AE%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%E6%89%A3%E6%AC%BE%E9%80%82%E7%94%A8%E8%8C%83%E5%9B%B4%E4%BB%A5%E5%8F%8A%E4%BA%A4%E6%98%93%E9%AA%8C%E8%AF%81%E6%96%B9%E5%BC%8F%EF%BC%8C%E7%94%B2%E6%96%B9%E6%84%BF%E6%84%8F%E6%89%BF%E6%8B%85%E7%9B%B8%E5%85%B3%E6%B3%95%E5%BE%8B%E5%90%8E%E6%9E%9C%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E4.%E7%94%B2%E6%96%B9%E2%80%9C%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E2%80%9D%E7%AD%BE%E7%BA%A6%E6%88%90%E5%8A%9F%E5%90%8E%EF%BC%8C%E5%8D%B3%E8%A7%86%E4%B8%BA%E7%94%B2%E6%96%B9%E6%8E%88%E6%9D%83%E4%B9%99%E6%96%B9%E6%8C%89%E7%85%A7%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E7%9A%84%E4%BA%A4%E6%98%93%E6%8C%87%E4%BB%A4%E4%BB%8E%E7%AD%BE%E7%BA%A6%E9%93%B6%E8%A1%8C%E8%B4%A6%E6%88%B7%E6%89%A3%E5%88%92%E8%B5%84%E9%87%91%E3%80%82%E7%94%B2%E6%96%B9%E4%B8%8D%E5%BA%94%E4%BB%A5%E6%9C%AA%E5%9C%A8%E4%BA%A4%E6%98%93%E5%8D%95%E6%8D%AE%E4%B8%AD%E7%AD%BE%E5%90%8D%E3%80%81%E7%AD%BE%E5%90%8D%E4%B8%8D%E7%AC%A6%E3%80%81%E9%9D%9E%E6%9C%AC%E4%BA%BA%E7%9C%9F%E5%AE%9E%E4%BA%A4%E6%98%93%E3%80%81%E6%9C%AA%E9%AA%8C%E8%AF%81%E9%93%B6%E8%A1%8C%E5%8D%A1%E6%94%AF%E4%BB%98%E5%AF%86%E7%A0%81%E3%80%81%E6%9C%AA%E9%AA%8C%E8%AF%81%E9%93%B6%E8%A1%8C%E5%8D%A1%E6%94%AF%E4%BB%98%E7%9B%BE%E7%AD%89%E5%8E%9F%E5%9B%A0%E8%A6%81%E6%B1%82%E4%B9%99%E6%96%B9%E9%80%80%E6%AC%BE%E6%88%96%E6%89%BF%E6%8B%85%E5%85%B6%E4%BB%96%E8%B4%A3%E4%BB%BB%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E5.%E7%94%B2%E6%96%B9%E5%8F%AF%E9%80%9A%E8%BF%87%E4%B9%99%E6%96%B9%E7%94%B5%E5%AD%90%E6%B8%A0%E9%81%93%E6%9F%A5%E8%AF%A2%26quot%3B%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%26quot%3B%E4%B8%9A%E5%8A%A1%E7%AD%BE%E7%BA%A6%E6%83%85%E5%86%B5%EF%BC%8C%E6%A0%B9%E6%8D%AE%E8%87%AA%E8%BA%AB%E9%A3%8E%E9%99%A9%E6%89%BF%E5%8F%97%E8%83%BD%E5%8A%9B%EF%BC%8C%E8%87%AA%E4%B8%BB%E8%AE%BE%E7%BD%AE%E4%B8%8E%E4%B9%8B%E6%89%BF%E5%8F%97%E8%83%BD%E5%8A%9B%E7%9B%B8%E5%8C%B9%E9%85%8D%E7%9A%84%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%E5%8D%95%E7%AC%94%E9%99%90%E9%A2%9D%E3%80%81%E5%8D%95%E6%97%A5%E9%99%90%E9%A2%9D%E3%80%82%E7%94%B2%E6%96%B9%E5%9C%A8%E4%BD%BF%E7%94%A8%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E6%9C%8D%E5%8A%A1%E6%97%B6%E9%9C%80%E5%90%8C%E6%97%B6%E5%8F%97%E4%B9%99%E6%96%B9%E5%92%8C%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E8%AE%BE%E7%BD%AE%E7%9A%84%E6%9C%80%E5%A4%A7%E6%94%AF%E4%BB%98%E9%99%90%E9%A2%9D%E7%BA%A6%E6%9D%9F%EF%BC%8C%E5%A6%82%E5%AE%9E%E9%99%85%E6%94%AF%E4%BB%98%E9%87%91%E9%A2%9D%E5%A4%A7%E4%BA%8E%E6%94%AF%E4%BB%98%E9%99%90%E9%A2%9D%EF%BC%8C%E4%B9%99%E6%96%B9%E5%B0%86%E6%8B%92%E7%BB%9D%E6%89%A7%E8%A1%8C%E4%BA%A4%E6%98%93%E6%8C%87%E4%BB%A4%E3%80%82%E4%B9%99%E6%96%B9%E4%BF%9D%E7%95%99%E6%A0%B9%E6%8D%AE%E4%BA%A4%E6%98%93%E5%AE%89%E5%85%A8%E9%9C%80%E8%A6%81%E8%AE%BE%E7%BD%AE%E6%88%96%E4%BF%AE%E6%94%B9%E6%9C%80%E5%A4%A7%E6%94%AF%E4%BB%98%E9%99%90%E9%A2%9D%E7%9A%84%E6%9D%83%E5%88%A9%E3%80%82%E4%BF%A1%E7%94%A8%E5%8D%A1%E6%94%AF%E4%BB%98%E9%99%90%E9%A2%9D%E5%90%8C%E6%97%B6%E5%8F%97%E9%99%90%E4%BA%8E%E4%BF%A1%E7%94%A8%E9%A2%9D%E5%BA%A6%E3%80%82II%E7%B1%BB%E3%80%81III%E7%B1%BB%E9%93%B6%E8%A1%8C%E8%B4%A6%E6%88%B7%E5%8F%97%E8%B4%A6%E6%88%B7%E8%87%AA%E8%BA%AB%E9%99%90%E9%A2%9D%E9%99%90%E5%88%B6%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E6.%E7%94%B2%E6%96%B9%E4%B8%8D%E5%BE%97%E5%88%A9%E7%94%A8%E2%80%9C%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E2%80%9D%E4%B8%9A%E5%8A%A1%E8%BF%9B%E8%A1%8C%E5%A5%97%E7%8E%B0%E3%80%81%E8%99%9A%E5%81%87%E4%BA%A4%E6%98%93%E3%80%81%E6%B4%97%E9%92%B1%E7%AD%89%E8%BF%9D%E6%B3%95%E8%A1%8C%E4%B8%BA%EF%BC%8C%E6%9C%89%E4%B9%89%E5%8A%A1%E9%85%8D%E5%90%88%E4%B9%99%E6%96%B9%E8%BF%9B%E8%A1%8C%E7%9B%B8%E5%85%B3%E8%B0%83%E6%9F%A5%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%A6%82%E8%8B%A5%E7%94%B2%E6%96%B9%E6%8B%92%E7%BB%9D%E9%85%8D%E5%90%88%E8%BF%9B%E8%A1%8C%E7%9B%B8%E5%85%B3%E8%B0%83%E6%9F%A5%E6%88%96%E4%B9%99%E6%96%B9%E8%AE%A4%E4%B8%BA%E5%AD%98%E5%9C%A8%E6%88%96%E6%B6%89%E5%AB%8C%E8%99%9A%E5%81%87%E4%BA%A4%E6%98%93%E3%80%81%E6%B4%97%E9%92%B1%E3%80%81%E5%A5%97%E7%8E%B0%E6%88%96%E4%BB%BB%E4%BD%95%E5%85%B6%E4%BB%96%E9%9D%9E%E6%B3%95%E6%B4%BB%E5%8A%A8%E3%80%81%E6%AC%BA%E8%AF%88%E6%88%96%E8%BF%9D%E5%8F%8D%E8%AF%9A%E4%BF%A1%E5%8E%9F%E5%88%99%E7%9A%84%E8%A1%8C%E4%B8%BA%E3%80%81%E6%88%96%E8%BF%9D%E5%8F%8D%E6%9C%AC%E5%8D%8F%E8%AE%AE%E7%BA%A6%E5%AE%9A%E7%9A%84%EF%BC%8C%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E4%B9%99%E6%96%B9%E6%9C%89%E6%9D%83%E9%87%87%E5%8F%96%E4%BB%A5%E4%B8%8B%E4%B8%80%E7%A7%8D%E3%80%81%E5%A4%9A%E7%A7%8D%E6%88%96%E5%85%A8%E9%83%A8%E6%8E%AA%E6%96%BD%EF%BC%9A%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%EF%BC%881%EF%BC%89%E6%9A%82%E5%81%9C%E6%88%96%E7%BB%88%E6%AD%A2%E6%8F%90%E4%BE%9B%E6%9C%AC%E5%8D%8F%E8%AE%AE%E9%A1%B9%E4%B8%8B%E2%80%9C%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E2%80%9D%E6%9C%8D%E5%8A%A1%EF%BC%9B%EF%BC%882%EF%BC%89%E7%BB%88%E6%AD%A2%E6%9C%AC%E5%8D%8F%E8%AE%AE%EF%BC%9B%EF%BC%883%EF%BC%89%E5%8F%96%E6%B6%88%E7%94%B2%E6%96%B9%E7%9A%84%E7%94%A8%E5%8D%A1%E8%B5%84%E6%A0%BC%E3%80%82%E8%8B%A5%E5%9B%A0%E7%94%B2%E6%96%B9%E7%9A%84%E5%89%8D%E8%BF%B0%E8%A1%8C%E4%B8%BA%E8%80%8C%E7%BB%99%E4%B9%99%E6%96%B9%E9%80%A0%E6%88%90%E6%8D%9F%E5%A4%B1%E7%9A%84%EF%BC%8C%E7%94%B2%E6%96%B9%E5%BA%94%E8%B4%9F%E8%B4%A3%E8%B5%94%E5%81%BF%E5%B9%B6%E6%89%BF%E6%8B%85%E6%B3%95%E5%BE%8B%E8%B4%A3%E4%BB%BB%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%AC%AC%E4%B8%89%E6%9D%A1%20%E4%B9%99%E6%96%B9%E7%9A%84%E6%9D%83%E5%88%A9%E5%92%8C%E4%B9%89%E5%8A%A1%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E1.%E4%B9%99%E6%96%B9%E6%A0%B9%E6%8D%AE%E7%94%B2%E6%96%B9%E7%9A%84%E6%8E%88%E6%9D%83%E5%92%8C%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%90%91%E4%B9%99%E6%96%B9%E6%8F%90%E4%BA%A4%E7%9A%84%E6%94%AF%E4%BB%98%E6%89%A3%E6%AC%BE%E6%8C%87%E4%BB%A4%E5%8F%8A%E6%94%AF%E4%BB%98%E6%89%A3%E6%AC%BE%E6%95%B0%E6%8D%AE%EF%BC%8C%E7%9B%B4%E6%8E%A5%E4%BB%8E%E7%94%B2%E6%96%B9%E6%8C%87%E5%AE%9A%E7%9A%84%E9%93%B6%E8%A1%8C%E8%B4%A6%E5%8F%B7%E4%B8%AD%E6%89%A3%E7%BC%B4%E7%9B%B8%E5%85%B3%E8%B4%B9%E7%94%A8%E8%87%B3%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E6%8C%87%E5%AE%9A%E8%B4%A6%E6%88%B7%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E2.%E4%B9%99%E6%96%B9%E4%B8%BA%E4%BF%9D%E6%8A%A4%E8%B5%84%E9%87%91%E5%AE%89%E5%85%A8%EF%BC%8C%E6%8E%A7%E5%88%B6%E4%B8%9A%E5%8A%A1%E9%A3%8E%E9%99%A9%EF%BC%8C%E5%8F%AF%E6%A0%B9%E6%8D%AE%E4%B8%9A%E5%8A%A1%E5%8F%91%E5%B1%95%E9%9C%80%E8%A6%81%E8%AE%BE%E7%BD%AE%E6%88%96%E4%BF%AE%E6%94%B9%E6%94%AF%E4%BB%98%E9%99%90%E9%A2%9D%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E3.%E4%B9%99%E6%96%B9%E6%9C%89%E6%9D%83%E9%9A%8F%E6%97%B6%E5%8F%98%E6%9B%B4%E7%94%B2%E6%96%B9%E5%BC%80%E9%80%9A%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E5%90%8E%E9%80%9A%E8%BF%87%E4%B9%99%E6%96%B9%E8%BF%9B%E8%A1%8C%E7%94%B5%E5%AD%90%E6%94%AF%E4%BB%98%E6%97%B6%E7%9A%84%E8%BA%AB%E4%BB%BD%E9%AA%8C%E8%AF%81%E6%96%B9%E5%BC%8F%EF%BC%8C%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E4%BE%8B%E5%A6%82%E4%B9%99%E6%96%B9%E5%8F%AF%E8%A6%81%E6%B1%82%E7%94%B2%E6%96%B9%E5%9C%A8%E9%80%89%E6%8B%A9%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E6%97%B6%E5%BA%94%E5%BD%93%E9%80%9A%E8%BF%87%E4%B9%99%E6%96%B9%E7%BD%91%E4%B8%8A%E9%93%B6%E8%A1%8C%E6%88%96%E8%B4%A6%E5%8F%B7%E9%AA%8C%E8%AF%81%EF%BC%8C%E6%88%96%E8%80%85%E4%BD%BF%E7%94%A8%E7%BD%91%E9%93%B6%E7%9B%BE%E6%88%96%E8%80%85%E7%9F%AD%E4%BF%A1%E9%AA%8C%E8%AF%81%E7%A0%81%E7%AD%89%E6%96%B9%E5%BC%8F%E4%BD%9C%E4%B8%BA%E6%A0%A1%E9%AA%8C%E6%A0%87%E5%87%86%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E4.%E4%B9%99%E6%96%B9%E6%9C%89%E6%9D%83%E4%BE%9D%E6%8D%AE%E5%9B%BD%E5%AE%B6%E6%9C%89%E5%85%B3%E8%A7%84%E5%AE%9A%E5%8F%8A%E4%B8%9A%E5%8A%A1%E9%9C%80%E8%A6%81%E5%AF%B9%E6%9C%8D%E5%8A%A1%E5%86%85%E5%AE%B9%E3%80%81%E6%94%B6%E8%B4%B9%E9%A1%B9%E7%9B%AE%E6%88%96%E6%A0%87%E5%87%86%E3%80%81%E6%93%8D%E4%BD%9C%E6%B5%81%E7%A8%8B%E3%80%81%E5%AE%A2%E6%88%B7%E9%A1%BB%E7%9F%A5%E7%AD%89%E5%86%85%E5%AE%B9%E8%BF%9B%E8%A1%8C%E8%B0%83%E6%95%B4%EF%BC%8C%E6%B6%89%E5%8F%8A%E6%94%B6%E8%B4%B9%E6%88%96%E5%85%B6%E4%BB%96%E5%AE%A2%E6%88%B7%E6%9D%83%E5%88%A9%E4%B9%89%E5%8A%A1%E5%8F%98%E6%9B%B4%E7%9A%84%E8%B0%83%E6%95%B4%EF%BC%8C%E5%B0%86%E6%AD%A3%E5%BC%8F%E5%AF%B9%E5%A4%96%E5%85%AC%E5%91%8A%E4%B8%80%E5%AE%9A%E6%97%B6%E6%9C%9F%E5%90%8E%E6%89%A7%E8%A1%8C%E5%B9%B6%E9%80%82%E7%94%A8%E4%BA%8E%E6%9C%AC%E5%8D%8F%E8%AE%AE%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%A6%82%E6%9C%89%E9%9C%80%E8%A6%81%EF%BC%8C%E4%B9%99%E6%96%B9%E5%B0%86%E5%9C%A8%E5%85%AC%E5%91%8A%E5%89%8D%E6%8A%A5%E7%BB%8F%E6%9C%89%E5%85%B3%E9%87%91%E8%9E%8D%E7%9B%91%E7%AE%A1%E9%83%A8%E9%97%A8%E6%A0%B8%E5%87%86%E6%88%96%E5%A4%87%E6%A1%88%EF%BC%9B%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%94%B2%E6%96%B9%E6%9C%89%E6%9D%83%E5%9C%A8%E4%B9%99%E6%96%B9%E5%85%AC%E5%91%8A%E6%9C%9F%E9%97%B4%E9%80%89%E6%8B%A9%E6%98%AF%E5%90%A6%E7%BB%A7%E7%BB%AD%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E6%9C%8D%E5%8A%A1%EF%BC%8C%E5%A6%82%E6%9E%9C%E7%94%B2%E6%96%B9%E4%B8%8D%E6%84%BF%E6%8E%A5%E5%8F%97%E4%B9%99%E6%96%B9%E5%85%AC%E5%91%8A%E5%86%85%E5%AE%B9%E7%9A%84%EF%BC%8C%E5%BA%94%E5%9C%A8%E5%85%AC%E5%91%8A%E6%96%BD%E8%A1%8C%E5%89%8D%E9%80%9A%E8%BF%87%E4%B9%99%E6%96%B9%E6%B8%A0%E9%81%93%E3%80%81%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E4%BA%8C%E7%BA%A7%E5%95%86%E6%88%B7%E6%88%96%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%90%91%E4%B9%99%E6%96%B9%E7%94%B3%E8%AF%B7%E5%8F%98%E6%9B%B4%E6%88%96%E7%BB%88%E6%AD%A2%E7%9B%B8%E5%85%B3%E6%9C%8D%E5%8A%A1%EF%BC%9B%E5%A6%82%E6%9E%9C%E7%94%B2%E6%96%B9%E6%9C%AA%E7%94%B3%E8%AF%B7%E5%8F%98%E6%9B%B4%E6%88%96%E7%BB%88%E6%AD%A2%E7%9B%B8%E5%85%B3%E6%9C%8D%E5%8A%A1%EF%BC%8C%E8%A7%86%E4%B8%BA%E5%90%8C%E6%84%8F%E7%9B%B8%E5%85%B3%E8%B0%83%E6%95%B4%EF%BC%8C%E5%8F%98%E6%9B%B4%E5%90%8E%E7%9A%84%E5%86%85%E5%AE%B9%E5%AF%B9%E7%94%B2%E6%96%B9%E4%BA%A7%E7%94%9F%E6%B3%95%E5%BE%8B%E7%BA%A6%E6%9D%9F%E5%8A%9B%EF%BC%8C%E8%8B%A5%E7%94%B2%E6%96%B9%E4%B8%8D%E6%89%A7%E8%A1%8C%E5%8F%98%E6%9B%B4%E5%90%8E%E7%9A%84%E5%86%85%E5%AE%B9%EF%BC%8C%E4%B9%99%E6%96%B9%E6%9C%89%E6%9D%83%E9%80%89%E6%8B%A9%E7%BB%88%E6%AD%A2%E6%9C%AC%E9%A1%B9%E6%9C%8D%E5%8A%A1%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%AC%AC%E5%9B%9B%E6%9D%A1%20%E7%89%B9%E5%88%AB%E7%BA%A6%E5%AE%9A%E6%9D%A1%E6%AC%BE%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E1.%E7%94%B2%E6%96%B9%E6%89%BF%E8%AF%BA%E5%9C%A8%E6%8C%87%E5%AE%9A%E7%9A%84%E4%B9%99%E6%96%B9%E4%BB%98%E6%AC%BE%E8%B4%A6%E6%88%B7%E4%B8%AD%E4%BF%9D%E7%95%99%E8%B6%B3%E5%A4%9F%E7%9A%84%E4%BD%99%E9%A2%9D%EF%BC%8C%E5%B9%B6%E4%BF%9D%E6%8C%81%E8%B4%A6%E6%88%B7%E5%A4%84%E4%BA%8E%E6%AD%A3%E5%B8%B8%E7%8A%B6%E6%80%81%E3%80%82%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%9B%A0%E4%BB%98%E6%AC%BE%E8%B4%A6%E6%88%B7%E4%BD%99%E9%A2%9D%E4%B8%8D%E8%B6%B3%E3%80%81%E8%B4%A6%E6%88%B7%E8%A2%AB%E5%86%BB%E7%BB%93%E3%80%81%E9%94%80%E6%88%B7%E7%AD%89%E5%8E%9F%E5%9B%A0%E8%80%8C%E5%AF%BC%E8%87%B4%E7%9A%84%E6%97%A0%E6%B3%95%E6%88%90%E5%8A%9F%E6%94%AF%E4%BB%98%E8%B4%B9%E7%94%A8%E5%8F%8A%E6%89%80%E9%80%A0%E6%88%90%E7%9A%84%E7%BA%A0%E7%BA%B7%E5%92%8C%E8%BF%9D%E7%BA%A6%E8%B4%A3%E4%BB%BB%E3%80%81%E6%8D%9F%E5%A4%B1%E7%AD%89%E6%B3%95%E5%BE%8B%E5%90%8E%E6%9E%9C%E7%94%B1%E7%94%B2%E6%96%B9%E6%89%BF%E6%8B%85%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E2.%E4%B9%99%E6%96%B9%E4%BB%85%E4%B8%BA%E7%94%B2%E6%96%B9%E6%8F%90%E4%BE%9B%E6%94%AF%E4%BB%98%E7%BB%93%E7%AE%97%E6%9C%8D%E5%8A%A1%EF%BC%8C%E4%BE%9D%E6%8D%AE%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E6%8F%90%E4%BE%9B%E7%9A%84%E4%BA%A4%E6%98%93%E6%8C%87%E4%BB%A4%E5%AE%9E%E6%96%BD%E8%B5%84%E9%87%91%E6%89%A3%E5%88%92%E3%80%82%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%94%B2%E6%96%B9%E6%89%BF%E8%AF%BA%EF%BC%9A%E5%AF%B9%E4%BA%8E%E5%9B%A0%E8%B4%AD%E4%B9%B0%E5%95%86%E5%93%81%E6%88%96%E6%9C%8D%E5%8A%A1%E8%80%8C%E4%BA%A7%E7%94%9F%E7%9A%84%E5%85%B3%E4%BA%8E%E5%95%86%E5%93%81%E3%80%81%E6%9C%8D%E5%8A%A1%E5%8F%8A%E8%B4%B9%E7%94%A8%E6%89%A3%E6%94%B6%E7%9A%84%E4%BA%89%E8%AE%AE%E7%94%B1%E7%94%B2%E6%96%B9%E4%B8%8E%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E8%87%AA%E8%A1%8C%E5%8D%8F%E5%95%86%E8%A7%A3%E5%86%B3%E3%80%82%E5%A6%82%E5%9B%A0%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%8E%9F%E5%9B%A0%E4%BA%A7%E7%94%9F%E7%9A%84%E9%A3%8E%E9%99%A9%E4%BA%8B%E4%BB%B6%EF%BC%8C%E7%94%B1%E7%94%B2%E6%96%B9%E4%B8%8E%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%8D%8F%E5%95%86%E8%A7%A3%E5%86%B3%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E3.%E4%B9%99%E6%96%B9%E6%8E%A5%E6%94%B6%E5%88%B0%E7%9A%84%E6%89%A3%E6%AC%BE%E6%8C%87%E4%BB%A4%E4%BF%A1%E6%81%AF%E4%B8%8D%E6%98%8E%E7%A1%AE%E3%80%81%E4%B8%8D%E5%AE%8C%E6%95%B4%E3%80%81%E4%B8%8D%E6%B8%85%E6%99%B0%E6%88%96%E6%97%A0%E6%B3%95%E8%BE%A8%E8%AE%A4%EF%BC%8C%E5%AF%BC%E8%87%B4%E4%B9%99%E6%96%B9%E6%97%A0%E6%B3%95%E6%89%A3%E6%AC%BE%E7%9A%84%EF%BC%8C%E4%B9%99%E6%96%B9%E4%B8%8D%E6%89%BF%E6%8B%85%E8%B4%A3%E4%BB%BB%EF%BC%8C%E7%94%B1%E7%94%B2%E6%96%B9%E4%B8%8E%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%8D%8F%E5%95%86%E8%A7%A3%E5%86%B3%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E4.%E5%A6%82%E7%94%B2%E6%96%B9%E6%88%96%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%90%91%E4%B9%99%E6%96%B9%E4%BC%A0%E9%80%81%E7%9A%84%E9%93%B6%E8%A1%8C%E5%8D%A1%E5%8F%B7%E3%80%81%E6%89%8B%E6%9C%BA%E5%8F%B7%E7%A0%81%E7%AD%89%E4%BF%A1%E6%81%AF%E6%9C%89%E8%AF%AF%EF%BC%8C%E5%AF%BC%E8%87%B4%E4%B9%99%E6%96%B9%E4%B8%8D%E8%83%BD%E6%8C%89%E7%BA%A6%E5%AE%9A%E6%89%A3%E5%88%92%E8%B5%84%E9%87%91%E6%88%96%E8%87%B4%E4%BD%BF%E7%94%B2%E6%96%B9%E5%8F%8A%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%8F%91%E7%94%9F%E6%8D%9F%E5%A4%B1%E7%9A%84%EF%BC%8C%E4%B9%99%E6%96%B9%E4%B8%8D%E6%89%BF%E6%8B%85%E8%B4%A3%E4%BB%BB%EF%BC%8C%E7%94%B1%E7%94%B2%E6%96%B9%E4%B8%8E%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%8D%8F%E5%95%86%E8%A7%A3%E5%86%B3%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%AC%AC%E4%BA%94%E6%9D%A1%20%E6%B3%95%E5%BE%8B%E9%80%82%E7%94%A8%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%8D%8F%E8%AE%AE%E5%86%85%E5%90%84%E6%96%B9%E5%9C%A8%E5%B1%A5%E8%A1%8C%E6%9C%AC%E5%8D%8F%E8%AE%AE%E7%9A%84%E8%BF%87%E7%A8%8B%E4%B8%AD%E5%A6%82%E5%8F%91%E7%94%9F%E4%BA%89%E8%AE%AE%EF%BC%8C%E5%BA%94%E5%8D%8F%E5%95%86%E8%A7%A3%E5%86%B3%EF%BC%9B%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%8D%8F%E5%95%86%E4%B8%8D%E6%88%90%E7%9A%84%EF%BC%8C%E5%90%91%E4%B9%99%E6%96%B9%E4%BD%8F%E6%89%80%E5%9C%B0%E4%BA%BA%E6%B0%91%E6%B3%95%E9%99%A2%E6%8F%90%E8%B5%B7%E8%AF%89%E8%AE%BC%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E%E6%9C%AC%E5%8D%8F%E8%AE%AE%E9%80%82%E7%94%A8%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E6%B3%95%E5%BE%8B%E3%80%82%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%AC%AC%E5%85%AD%E6%9D%A1%20%E4%B8%8D%E5%8F%AF%E6%8A%97%E5%8A%9B%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E%E5%9B%A0%E4%B8%8D%E5%8F%AF%E6%8A%97%E5%8A%9B%EF%BC%88%E5%8C%85%E6%8B%AC%E4%BD%86%E4%B8%8D%E9%99%90%E4%BA%8E%E4%B8%8D%E8%83%BD%E9%A2%84%E8%A7%81%E3%80%81%E4%B8%8D%E8%83%BD%E9%81%BF%E5%85%8D%E5%B9%B6%E4%B8%8D%E8%83%BD%E5%85%8B%E6%9C%8D%E7%9A%84%E6%88%98%E4%BA%89%E3%80%81%E6%9A%B4%E5%8A%A8%E3%80%81%E8%87%AA%E7%84%B6%E7%81%BE%E5%AE%B3%E3%80%81%E6%94%BF%E5%BA%9C%E8%A1%8C%E4%B8%BA%E3%80%81%E7%A4%BE%E4%BC%9A%E4%BA%8B%E4%BB%B6%E3%80%81%E9%BB%91%E5%AE%A2%E6%94%BB%E5%87%BB%E3%80%81%E4%BE%9B%E7%94%B5%E3%80%81%E9%80%9A%E8%AE%AF%E3%80%81%E4%BA%A4%E6%98%93%E7%B3%BB%E7%BB%9F%E6%95%85%E9%9A%9C%E7%AD%89%E5%AE%A2%E8%A7%82%E6%83%85%E5%86%B5%EF%BC%89%E5%AF%BC%E8%87%B4%E7%94%B2%E6%96%B9%E6%8D%9F%E5%A4%B1%E7%9A%84%EF%BC%8C%E4%B9%99%E6%96%B9%E5%B0%86%E8%A7%86%E6%83%85%E5%86%B5%E5%8D%8F%E5%8A%A9%E7%94%B2%E6%96%B9%E8%A7%A3%E5%86%B3%E6%88%96%E6%8F%90%E4%BE%9B%E5%BF%85%E8%A6%81%E7%9A%84%E5%B8%AE%E5%8A%A9%E3%80%82%E6%A0%B9%E6%8D%AE%E4%B8%8D%E5%8F%AF%E6%8A%97%E5%8A%9B%E7%9A%84%E5%BD%B1%E5%93%8D%EF%BC%8C%E4%B9%99%E6%96%B9%E9%83%A8%E5%88%86%E6%88%96%E5%85%A8%E9%83%A8%E5%85%8D%E9%99%A4%E8%B4%A3%E4%BB%BB%E3%80%82%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%AC%AC%E4%B8%83%E6%9D%A1%20%E5%8D%8F%E8%AE%AE%E7%AD%BE%E7%BD%B2%E4%B8%8E%E7%BB%88%E6%AD%A2%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E6%9C%AC%E5%8D%8F%E8%AE%AE%E7%BB%8F%E7%94%B2%E6%96%B9%E5%9C%A8%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E4%BA%92%E8%81%94%E7%BD%91%E6%88%96%E4%B9%99%E6%96%B9%E6%89%8B%E6%9C%BA%E9%93%B6%E8%A1%8CAPP%E5%8F%8A%E8%87%AA%E5%8A%A9%E8%AE%BE%E5%A4%87%E7%AD%89%E6%B8%A0%E9%81%93%E5%B1%95%E7%A4%BA%E6%9C%AC%E5%8D%8F%E8%AE%AE%E7%9A%84%E9%A1%B5%E9%9D%A2%E7%82%B9%E5%87%BB%E2%80%9C%E5%90%8C%E6%84%8F%E2%80%9D%E6%88%96%E2%80%9C%E6%8F%90%E4%BA%A4%E2%80%9D%E6%8C%89%E9%92%AE%E5%90%8E%E7%94%9F%E6%95%88%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E%E2%80%9C%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E2%80%9D%E7%AD%BE%E7%BA%A6%E5%85%B3%E7%B3%BB%E4%B8%80%E6%97%A6%E8%A7%A3%E9%99%A4%EF%BC%8C%E6%9C%AC%E5%8D%8F%E8%AE%AE%E5%8D%B3%E5%91%8A%E7%BB%88%E6%AD%A2%E3%80%82%E5%8D%8F%E8%AE%AE%E7%BB%88%E6%AD%A2%E5%89%8D%E5%B7%B2%E5%8F%91%E9%80%81%E4%B9%99%E6%96%B9%E5%A4%84%E7%90%86%E7%9A%84%E4%BA%A4%E6%98%93%E6%8C%87%E4%BB%A4%E4%BB%8D%E6%9C%89%E6%95%88%EF%BC%8C%E7%94%B2%E6%96%B9%E5%BA%94%E6%89%BF%E6%8B%85%E7%9B%B8%E5%BA%94%E5%90%8E%E6%9E%9C%E3%80%82%E5%A6%82%E5%9B%A0%E7%AD%BE%E7%BA%A6%E5%8D%A1%2F%E6%8A%98%E6%B3%A8%E9%94%80%E3%80%81%E8%A1%A5%EF%BC%88%E6%8D%A2%EF%BC%89%E5%8D%A1%2F%E6%8A%98%E7%AD%89%E5%8E%9F%E5%9B%A0%E5%AF%BC%E8%87%B4%E5%8D%A1%2F%E6%8A%98%E5%8F%B7%E5%8F%98%E6%9B%B4%EF%BC%8C%E7%94%B2%E6%96%B9%E9%A1%BB%E9%87%8D%E6%96%B0%E7%AD%BE%E8%AE%A2%26quot%3B%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%26quot%3B%E7%AD%BE%E7%BA%A6%E5%85%B3%E7%B3%BB%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%89%B9%E5%88%AB%E6%8F%90%E7%A4%BA%EF%BC%9A%E5%A6%82%E5%AE%A2%E6%88%B7%E5%AF%B9%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E4%BA%A7%E5%93%81%E6%88%96%E6%9C%8D%E5%8A%A1%E6%9C%89%E4%BB%BB%E4%BD%95%E7%96%91%E9%97%AE%E3%80%81%E6%84%8F%E8%A7%81%E6%88%96%E5%BB%BA%E8%AE%AE%EF%BC%8C%E5%8F%AF%E9%80%9A%E8%BF%87%E6%8B%A8%E6%89%93%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C95533%E5%AE%A2%E6%88%B7%E6%9C%8D%E5%8A%A1%E4%B8%8E%E6%8A%95%E8%AF%89%E7%83%AD%E7%BA%BF%E5%92%A8%E8%AF%A2%E4%B8%8E%E5%8F%8D%E6%98%A0%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20Calibri%5C%22%3E%E7%94%B2%E6%96%B9%E6%89%BF%E8%AF%BA%EF%BC%9A%E6%9C%AC%E4%BA%BA%E5%B7%B2%E4%BB%94%E7%BB%86%E9%98%85%E8%AF%BB%E4%B8%8A%E8%BF%B0%E6%89%80%E6%9C%89%E6%9D%A1%E6%AC%BE%EF%BC%8C%E5%B9%B6%E5%B7%B2%E7%89%B9%E5%88%AB%E6%B3%A8%E6%84%8F%E5%AD%97%E4%BD%93%E5%8A%A0%E9%BB%91%E7%9A%84%E5%86%85%E5%AE%B9%E3%80%82%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E5%B7%B2%E5%BA%94%E6%9C%AC%E4%BA%BA%E8%A6%81%E6%B1%82%E5%AF%B9%E7%9B%B8%E5%85%B3%E6%9D%A1%E6%AC%BE%E4%BA%88%E4%BB%A5%E6%98%8E%E7%A1%AE%E8%AF%B4%E6%98%8E%EF%BC%8C%E6%9C%AC%E4%BA%BA%E5%AF%B9%E6%89%80%E6%9C%89%E6%9D%A1%E6%AC%BE%E7%9A%84%E5%90%AB%E4%B9%89%E5%8F%8A%E7%9B%B8%E5%BA%94%E7%9A%84%E6%B3%95%E5%BE%8B%E5%90%8E%E6%9E%9C%E5%B7%B2%E5%85%A8%E9%83%A8%E9%80%9A%E6%99%93%E5%B9%B6%E5%85%85%E5%88%86%E7%90%86%E8%A7%A3%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%26nbsp%3B%3C%2Fp%3E%3Cp%20style%3D%5C%22LINE-HEIGHT%3A%2016px%5C%22%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-ALIGN%3A%20center%3B%20LINE-HEIGHT%3A%2016px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2024px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E9%9D%9E%E9%93%B6%E8%A1%8C%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-ALIGN%3A%20center%3B%20LINE-HEIGHT%3A%2016px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2024px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%E6%8E%88%E6%9D%83%E5%8D%8F%E8%AE%AE%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-ALIGN%3A%20center%3B%20LINE-HEIGHT%3A%2016px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2024px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%26nbsp%3B%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%E6%8E%88%E6%9D%83%E5%8D%8F%E8%AE%AE%EF%BC%88%E4%BB%A5%E4%B8%8B%E7%AE%80%E7%A7%B0%E2%80%9C%E6%9C%AC%E5%8D%8F%E8%AE%AE%E2%80%9D%EF%BC%89%E6%98%AF%E5%AF%B9%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E8%82%A1%E4%BB%BD%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8_____%E5%88%86%E6%94%AF%E8%A1%8C%EF%BC%88%E4%BB%A5%E4%B8%8B%E7%AE%80%E7%A7%B0%E2%80%9C%E6%88%91%E8%A1%8C%E2%80%9D%E3%80%81%E2%80%9C%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E2%80%9D%EF%BC%89%E4%B8%8E%E6%82%A8%E5%B0%B1%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%E5%9C%BA%E6%99%AF%E5%B0%86%E6%82%A8%E7%9A%84%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%E6%8F%90%E4%BE%9B%E7%BB%99%E9%9D%9E%E9%93%B6%E8%A1%8C%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%EF%BC%88%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%85%B7%E4%BD%93%E5%90%8D%E7%A7%B0%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22TEXT-DECORATION%3A%20underline%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%3B%20text-underline%3A%20single%5C%22%3E%E4%B8%AD%E7%A7%BB%E7%94%B5%E5%AD%90%E5%95%86%E5%8A%A1%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%8F%8A%E8%81%94%E7%B3%BB%E6%96%B9%E5%BC%8F________%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%EF%BC%89%E5%8F%8A%E7%94%B5%E4%BF%A1%E8%BF%90%E8%90%A5%E5%95%86%EF%BC%88%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%94%B5%E4%BF%A1%E8%BF%90%E8%90%A5%E5%95%86%E5%85%B7%E4%BD%93%E5%90%8D%E7%A7%B0______%E5%8F%8A%E8%81%94%E7%B3%BB%E6%96%B9%E5%BC%8F________%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%EF%BC%89%E7%AD%89%E6%9C%BA%E6%9E%84%E7%9B%B8%E5%85%B3%E4%BA%8B%E9%A1%B9%E8%AE%A2%E7%AB%8B%E7%9A%84%E6%9C%89%E6%95%88%E5%90%88%E7%BA%A6%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E8%AF%B7%E6%82%A8%E4%BB%94%E7%BB%86%E9%98%85%E8%AF%BB%E5%B9%B6%E5%85%85%E5%88%86%E7%90%86%E8%A7%A3%E6%9C%AC%E5%8D%8F%E8%AE%AE%E5%85%A8%E9%83%A8%E5%86%85%E5%AE%B9%E3%80%82%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%A6%82%E6%82%A8%E5%AF%B9%E6%9C%AC%E5%8D%8F%E8%AE%AE%E5%86%85%E5%AE%B9%E5%8F%8A%E9%A1%B5%E9%9D%A2%E6%8F%90%E7%A4%BA%E4%BF%A1%E6%81%AF%E6%9C%89%E7%96%91%E9%97%AE%EF%BC%8C%E8%AF%B7%E5%8B%BF%E8%BF%9B%E8%A1%8C%E4%B8%8B%E4%B8%80%E6%AD%A5%E6%93%8D%E4%BD%9C%E3%80%82%E6%82%A8%E5%9C%A8%E5%8A%9E%E7%90%86%E9%A1%B5%E9%9D%A2%E9%80%9A%E8%BF%87%E7%82%B9%E5%87%BB%E6%88%96%E5%85%B6%E4%BB%96%E5%90%8C%E7%AD%89%E6%84%8F%E4%B9%89%E7%9A%84%E6%98%8E%E7%A4%BA%E6%96%B9%E5%BC%8F%E7%A1%AE%E8%AE%A4%E5%8D%B3%E8%A1%A8%E7%A4%BA%E6%82%A8%E5%B7%B2%E7%9F%A5%E6%99%93%E5%B9%B6%E5%90%8C%E6%84%8F%E6%9C%AC%E5%8D%8F%E8%AE%AE%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%E4%B8%80%E3%80%81%E5%8F%8C%E6%96%B9%E6%9D%83%E5%88%A9%E4%B9%89%E5%8A%A1%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E1.%20%E5%9C%A8%E6%88%91%E8%A1%8C%E4%B8%BA%E6%82%A8%E6%8F%90%E4%BE%9B%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E6%9C%8D%E5%8A%A1%E7%9A%84%E6%83%85%E5%BD%A2%E4%B8%8B%EF%BC%8C%E6%82%A8%E5%90%8C%E6%84%8F%E5%B9%B6%E6%8E%88%E6%9D%83%E6%88%91%E8%A1%8C%E5%B0%86%E6%88%91%E8%A1%8C%E6%94%B6%E9%9B%86%E7%9A%84%E6%82%A8%E7%9A%84%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%A7%93%E5%90%8D%E3%80%81%E8%AF%81%E4%BB%B6%E7%B1%BB%E5%9E%8B%E3%80%81%E8%AF%81%E4%BB%B6%E5%8F%B7%E7%A0%81%E3%80%81%E9%93%B6%E8%A1%8C%E5%8D%A1%E5%8F%B7%E3%80%81%E6%89%8B%E6%9C%BA%E5%8F%B7%E7%A0%81%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E4%BF%A1%E6%81%AF%E5%8F%91%E9%80%81%E7%BB%99%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%EF%BC%88%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%85%B7%E4%BD%93%E5%90%8D%E7%A7%B0%EF%BC%89%E7%94%A8%E4%BA%8E%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%E7%9A%84%E7%AD%BE%E7%BA%A6%E9%AA%8C%E8%AF%81%E5%8F%8A%E5%90%8E%E7%BB%AD%E6%9C%8D%E5%8A%A1%E3%80%82%E6%82%A8%E5%90%8C%E6%84%8F%E5%B9%B6%E6%8E%88%E6%9D%83%E6%88%91%E8%A1%8C%E5%B0%86%E4%B8%8A%E8%BF%B0%E4%BF%A1%E6%81%AF%E5%8F%91%E9%80%81%E8%87%B3%E7%94%B5%E4%BF%A1%E8%BF%90%E8%90%A5%E5%95%86%EF%BC%88%E5%A6%82%E6%9C%89%EF%BC%8C%E5%88%99%E6%98%8E%E7%A1%AE%E5%85%B7%E4%BD%93%E5%90%8D%E7%A7%B0%EF%BC%89%E8%BF%9B%E8%A1%8C%E5%BF%85%E8%A6%81%E7%9A%84%E4%BF%A1%E6%81%AF%E6%9F%A5%E8%AF%A2%E4%B8%8E%E6%A0%B8%E9%AA%8C%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%A6%82%E6%82%A8%E4%B8%8D%E5%90%8C%E6%84%8F%E6%88%91%E8%A1%8C%E5%B0%86%E6%82%A8%E7%9A%84%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%A7%93%E5%90%8D%E3%80%81%E8%AF%81%E4%BB%B6%E7%B1%BB%E5%9E%8B%E3%80%81%E8%AF%81%E4%BB%B6%E5%8F%B7%E7%A0%81%E3%80%81%E9%93%B6%E8%A1%8C%E5%8D%A1%E5%8F%B7%E3%80%81%E6%89%8B%E6%9C%BA%E5%8F%B7%E7%A0%81%E4%BF%A1%E6%81%AF%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%8F%91%E9%80%81%E7%BB%99%E4%B8%8A%E8%BF%B0%E4%BF%A1%E6%81%AF%E6%8E%A5%E6%94%B6%E6%96%B9%EF%BC%8C%E5%B0%86%E6%97%A0%E6%B3%95%E5%AE%8C%E6%88%90%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%E7%9A%84%E7%AD%BE%E7%BA%A6%E6%B5%81%E7%A8%8B%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E2.%20%E6%88%91%E8%A1%8C%E9%87%8D%E8%A7%86%E5%AF%B9%E6%82%A8%E7%9A%84%E4%BF%A1%E6%81%AF%E4%BF%9D%E6%8A%A4%EF%BC%8C%E5%AF%B9%E4%BA%8E%E6%82%A8%E5%90%8C%E6%84%8F%E6%88%91%E8%A1%8C%E5%A4%84%E7%90%86%E7%9A%84%E6%82%A8%E7%9A%84%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%EF%BC%8C%E6%88%91%E8%A1%8C%E5%B0%86%E4%B8%A5%E6%A0%BC%E6%8C%89%E7%85%A7%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84%E3%80%81%E7%9B%91%E7%AE%A1%E8%A7%84%E5%AE%9A%E5%8F%8A%E4%B8%8E%E6%82%A8%E7%9A%84%E7%BA%A6%E5%AE%9A%E5%BC%80%E5%B1%95%E4%BF%A1%E6%81%AF%E5%A4%84%E7%90%86%E8%A1%8C%E4%B8%BA%EF%BC%8C%E5%B9%B6%E6%8C%89%E7%85%A7%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84%E3%80%81%E7%9B%91%E7%AE%A1%E8%A7%84%E5%AE%9A%E5%AF%B9%E6%82%A8%E7%9A%84%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%E9%87%87%E5%8F%96%E7%9B%B8%E5%BA%94%E7%9A%84%E4%BF%9D%E6%8A%A4%E6%8E%AA%E6%96%BD%E3%80%82%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E6%88%91%E8%A1%8C%E6%89%BF%E8%AF%BA%E5%B0%86%E5%90%91%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E6%98%8E%E7%A1%AE%E5%85%B6%E4%BF%9D%E6%8A%A4%E6%82%A8%E7%9A%84%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%E7%9A%84%E8%81%8C%E8%B4%A3%EF%BC%8C%E5%B9%B6%E8%A6%81%E6%B1%82%E5%85%B6%E6%89%BF%E6%8B%85%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%E6%95%B0%E6%8D%AE%E7%9A%84%E7%9B%B8%E5%BA%94%E4%BF%9D%E6%8A%A4%E8%81%8C%E8%B4%A3%E5%92%8C%E4%BF%9D%E5%AF%86%E4%B9%89%E5%8A%A1%E3%80%82%20%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%E4%BA%8C%E3%80%81%E5%8D%8F%E8%AE%AE%E5%8F%98%E6%9B%B4%E4%B8%8E%E7%BB%88%E6%AD%A2%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%A6%82%E8%8B%A5%E6%82%A8%E4%B8%8D%E5%90%8C%E6%84%8F%E4%B8%8A%E8%BF%B0%E4%BF%A1%E6%81%AF%E6%8E%A5%E6%94%B6%E6%96%B9%E7%BB%A7%E7%BB%AD%E5%A4%84%E7%90%86%E4%B9%8B%E5%89%8D%E6%88%91%E8%A1%8C%E5%9F%BA%E4%BA%8E%E6%82%A8%E7%9A%84%E6%8E%88%E6%9D%83%E5%90%8C%E6%84%8F%E5%90%91%E5%85%B6%E6%8F%90%E4%BE%9B%E7%9A%84%E6%82%A8%E7%9A%84%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%EF%BC%8C%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E6%82%A8%E5%8F%AF%E9%80%9A%E8%BF%87%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84APP%E5%8F%8A%E7%BD%91%E7%AB%99%E6%88%96%E6%88%91%E8%A1%8C%E6%89%8B%E6%9C%BA%E9%93%B6%E8%A1%8CAPP%E5%8F%8A%E8%87%AA%E5%8A%A9%E8%AE%BE%E5%A4%87%E7%AD%89%E6%B8%A0%E9%81%93%E8%A7%A3%E9%99%A4%E7%AD%BE%E7%BA%A6%E5%85%B3%E7%B3%BB%E7%9A%84%E6%96%B9%E5%BC%8F%E6%92%A4%E5%9B%9E%E6%94%AF%E4%BB%98%E6%9C%BA%E6%9E%84%E5%A4%84%E7%90%86%E6%82%A8%E7%9A%84%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%E7%9A%84%E6%8E%88%E6%9D%83%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%9F%BA%E4%BA%8E%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84%E5%8F%8A%E7%9B%91%E7%AE%A1%E6%94%BF%E7%AD%96%E5%8F%98%E5%8C%96%E6%88%96%E4%B8%9A%E5%8A%A1%E8%B0%83%E6%95%B4%E9%9C%80%E8%A6%81%EF%BC%8C%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E6%88%91%E8%A1%8C%E5%9C%A8%E6%8C%89%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84%E3%80%81%E7%9B%91%E7%AE%A1%E8%A7%84%E5%AE%9A%E5%AF%B9%E6%82%A8%E8%BF%9B%E8%A1%8C%E9%80%9A%E7%9F%A5%E5%90%8E%EF%BC%8C%E6%9C%89%E6%9D%83%E7%BB%88%E6%AD%A2%E5%90%91%E6%82%A8%E6%8F%90%E4%BE%9B%E6%9C%AC%E5%8D%8F%E8%AE%AE%E9%A1%B9%E4%B8%8B%E7%9A%84%E6%9C%8D%E5%8A%A1%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%E4%B8%89%E3%80%81%E8%B4%A3%E4%BB%BB%E6%89%BF%E6%8B%85%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E8%AF%B7%E6%82%A8%E7%90%86%E8%A7%A3%EF%BC%8C%E7%94%B1%E4%BA%8E%E6%8A%80%E6%9C%AF%E6%B0%B4%E5%B9%B3%E9%99%90%E5%88%B6%E5%8F%8A%E5%8F%AF%E8%83%BD%E5%AD%98%E5%9C%A8%E7%9A%84%E5%90%84%E7%A7%8D%E6%81%B6%E6%84%8F%E4%BA%8B%E4%BB%B6%EF%BC%8C%E6%88%91%E8%A1%8C%E6%9C%89%E5%8F%AF%E8%83%BD%E5%9B%A0%E4%B8%8D%E5%8F%AF%E6%8A%97%E5%8A%9B%E3%80%81%E8%AE%A1%E7%AE%97%E6%9C%BA%E9%BB%91%E5%AE%A2%E8%A2%AD%E5%87%BB%E3%80%81%E7%B3%BB%E7%BB%9F%E6%95%85%E9%9A%9C%E3%80%81%E9%80%9A%E8%AE%AF%E6%95%85%E9%9A%9C%E3%80%81%E4%BE%9B%E7%94%B5%E7%B3%BB%E7%BB%9F%E6%95%85%E9%9A%9C%E3%80%81%E7%94%B5%E8%84%91%E7%97%85%E6%AF%92%E3%80%81%E6%81%B6%E6%84%8F%E7%A8%8B%E5%BA%8F%E6%94%BB%E5%87%BB%E5%8F%8A%E5%85%B6%E4%BB%96%E4%B8%8D%E5%8F%AF%E5%BD%92%E5%9B%A0%E4%BA%8E%E6%88%91%E8%A1%8C%E7%9A%84%E6%83%85%E5%86%B5%E8%80%8C%E5%AF%BC%E8%87%B4%E6%82%A8%E6%97%A0%E6%B3%95%E5%AE%8C%E6%88%90%E5%BF%AB%E6%8D%B7%E6%94%AF%E4%BB%98%E4%B8%9A%E5%8A%A1%E7%9A%84%E7%AD%BE%E7%BA%A6%E6%88%96%E9%80%A0%E6%88%90%E5%85%B6%E4%BB%96%E6%8D%9F%E5%A4%B1%EF%BC%8C%3C%2Fspan%3E%3Cstrong%3E%3Cspanstyle%3D%20font-family%3A%3D%5C%22%5C%22%20font-size%3A%3D%5C%22%5C%22%3E%E6%88%91%E8%A1%8C%E5%B0%86%E5%9C%A8%E8%BF%87%E9%94%99%E8%8C%83%E5%9B%B4%E5%86%85%E4%BE%9D%E6%B3%95%E4%BE%9D%E7%BA%A6%E6%89%BF%E6%8B%85%E8%B4%A3%E4%BB%BB%EF%BC%8C%E5%B9%B6%E8%A7%86%E6%83%85%E5%86%B5%E5%8D%8F%E5%8A%A9%E6%82%A8%E8%A7%A3%E5%86%B3%E6%88%96%E6%8F%90%E4%BE%9B%E5%BF%85%E8%A6%81%E7%9A%84%E5%B8%AE%E5%8A%A9%3C%2Fspanstyle%3D%3E%3C%2Fstrong%3E%3C%2Fspan%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E3%80%82%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%E5%9B%9B%E3%80%81%E4%BA%89%E8%AE%AE%E8%A7%A3%E5%86%B3%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%8F%8C%E6%96%B9%E5%9C%A8%E5%B1%A5%E8%A1%8C%E6%9C%AC%E5%8D%8F%E8%AE%AE%E7%9A%84%E8%BF%87%E7%A8%8B%E4%B8%AD%EF%BC%8C%E5%A6%82%E5%8F%91%E7%94%9F%E4%BA%89%E8%AE%AE%EF%BC%8C%E5%BA%94%E5%8F%8B%E5%A5%BD%E5%8D%8F%E5%95%86%E8%A7%A3%E5%86%B3%E3%80%82%E5%8D%8F%E5%95%86%E4%B8%8D%E6%88%90%E7%9A%84%EF%BC%8C%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E4%BB%BB%E4%BD%95%E4%B8%80%E6%96%B9%E5%9D%87%E5%8F%AF%E5%90%91%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E8%82%A1%E4%BB%BD%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8_____%E5%88%86%E6%94%AF%E8%A1%8C%E4%BD%8F%E6%89%80%E5%9C%B0%E6%9C%89%E7%AE%A1%E8%BE%96%E6%9D%83%E7%9A%84%E4%BA%BA%E6%B0%91%E6%B3%95%E9%99%A2%E6%8F%90%E8%B5%B7%E8%AF%89%E8%AE%BC%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%26nbsp%3B%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E7%89%B9%E5%88%AB%E6%8F%90%E7%A4%BA%EF%BC%9A%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%A6%82%E6%82%A8%E5%AF%B9%E6%9C%AC%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF%E4%BF%9D%E6%8A%A4%E4%BA%8B%E9%A1%B9%E6%9C%89%E4%BB%BB%E4%BD%95%E7%96%91%E9%97%AE%E3%80%81%E6%84%8F%E8%A7%81%E6%88%96%E5%BB%BA%E8%AE%AE%EF%BC%8C%E5%8F%AF%E4%BB%A5%3C%2Fspan%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E9%80%9A%E8%BF%87%E6%8B%A8%E6%89%93%E6%88%91%E8%A1%8C95533%E5%AE%A2%E6%9C%8D%E7%83%AD%E7%BA%BF%E3%80%81%E7%99%BB%E5%BD%95%E6%88%91%E8%A1%8C%E5%AE%98%E6%96%B9%E7%BD%91%E7%AB%99%EF%BC%88WWW.CCB.COM%29%E3%80%81%E5%85%B3%E6%B3%A8%E2%80%9C%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E2%80%9D%E5%BE%AE%E4%BF%A1%E5%85%AC%E4%BC%97%E5%8F%B7%E6%88%96%E5%88%B0%E6%88%91%E8%A1%8C%E5%90%84%E8%90%A5%E4%B8%9A%E7%BD%91%E7%82%B9%3C%2Fspan%3E%3C%2Fstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E5%92%A8%E8%AF%A2%E6%88%96%E5%8F%8D%E6%98%A0%E3%80%82%E5%8F%97%E7%90%86%E6%82%A8%E7%9A%84%E9%97%AE%E9%A2%98%E5%90%8E%EF%BC%8C%E6%88%91%E8%A1%8C%E4%BC%9A%E5%8F%8A%E6%97%B6%E3%80%81%E5%A6%A5%E5%96%84%E5%A4%84%E7%90%86%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%26nbsp%3B%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22COLOR%3A%20%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E5%BD%A9%E8%99%B9%E7%B2%97%E4%BB%BF%E5%AE%8B%5C%22%3E%E6%88%91%E8%A1%8C%E5%85%A8%E7%A7%B0%EF%BC%9A__________________%EF%BC%8C%E6%B3%A8%E5%86%8C%E5%9C%B0%E5%9D%80%EF%BC%9A__________________%EF%BC%8C%E9%82%AE%E7%BC%96__________________%E3%80%82%3C%2Fspan%3E%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%26nbsp%3B%3C%2Fspan%3E%3C%2Fp%3E%3Cp%20style%3D%5C%22TEXT-INDENT%3A%2032px%5C%22%3E%3Cstrong%3E%3Cspan%20style%3D%5C%22FONT-SIZE%3A%2016px%3B%20FONT-FAMILY%3A%20%E7%AD%89%E7%BA%BF%5C%22%3E%E5%AE%A2%E6%88%B7%E6%89%BF%E8%AF%BA%EF%BC%9A%E6%9C%AC%E4%BA%BA%E5%B7%B2%E4%BB%94%E7%BB%86%E9%98%85%E8%AF%BB%E4%B8%8A%E8%BF%B0%E6%89%80%E6%9C%89%E6%9D%A1%E6%AC%BE%EF%BC%8C%E5%B9%B6%E5%B7%B2%E7%89%B9%E5%88%AB%E6%B3%A8%E6%84%8F%E5%AD%97%E4%BD%93%E5%8A%A0%E9%BB%91%E7%9A%84%E5%86%85%E5%AE%B9%E3%80%82%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%E5%B7%B2%E5%BA%94%E6%9C%AC%E4%BA%BA%E8%A6%81%E6%B1%82%E5%AF%B9%E7%9B%B8%E5%85%B3%E6%9D%A1%E6%AC%BE%E4%BA%88%E4%BB%A5%E6%98%8E%E7%A1%AE%E8%AF%B4%E6%98%8E%EF%BC%8C%E6%9C%AC%E4%BA%BA%E5%AF%B9%E6%89%80%E6%9C%89%E6%9D%A1%E6%AC%BE%E7%9A%84%E5%90%AB%E4%B9%89%E5%8F%8A%E7%9B%B8%E5%BA%94%E7%9A%84%E6%B3%95%E5%BE%8B%E5%90%8E%E6%9E%9C%E5%B7%B2%E5%85%A8%E9%83%A8%E9%80%9A%E6%99%93%E5%B9%B6%E5%85%85%E5%88%86%E7%90%86%E8%A7%A3%E3%80%82%3C%2Fspan%3E%3C%2Fstrong%3E%3Cstrong%3E%3C%2Fstrong%3E%3C%2Fp%3E%3Cp%3E%3C%2Fp%3E%20%20%20%20%20%20%20%20";
        String textCont ="";
        try {
            textCont = URLDecoder.decode(y, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        System.out.println("y" +textCont);
    }

    public static boolean checkTimeRange(String startTime, String endTime, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date startDate = null;
            Date endDate = null;

            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);

            if (startDate.after(endDate)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String countErrorRate(int errorCount, int totalCount){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format((float)errorCount/(float)totalCount * 100);
    }


}
