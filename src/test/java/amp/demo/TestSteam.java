package amp.demo;

import amp.demo.entity.UserTest;
import amp.demo.utils.DateTimeUtil;
import amp.demo.utils.DateTimeUtils;
import amp.demo.utils.JudgeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class TestSteam {

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void main(String[] args) throws ParseException {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Integer> listT = Arrays.asList(1,4, 5);
        List<Integer> collect6 = listT.stream().filter(s -> list.stream().noneMatch(m -> m.equals(s))).collect(toList());

        collect6.stream().forEach(System.out::println);

        System.out.println("''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
        UserTest userTest=new UserTest();
        list.stream().forEach(s->{
             listT.stream().forEach(t->{
                if (s.equals(t))
                    userTest.setId(String.valueOf(s));
            });
        });
        System.out.println("'''userTest'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''"+userTest.toString());
        List<Integer> collect1 = list.stream().filter(item -> !listT.contains(item)).collect(toList());

        System.out.println(collect1);

        // 并集
        List<Integer> listAll = list.parallelStream().collect(Collectors.toList());
        List<Integer> listAll2 = listT.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);
        listAll.stream().forEach(System.out :: println);

        List<Integer> collect2 = listAll.stream().distinct().collect(toList());
        collect2.stream().forEach(System.out :: println);

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

        System.out.println("collect5------------"+collect5);


        System.out.println("---------------------------------");
//        List<String> collect = listTow.stream().map(User::getBankUserName).sorted().collect(Collectors.toList());
//        System.out.println(collect);

        listTow.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getPassword())));
        System.out.println( listTow.stream().collect(toList()));

        List<Integer> collect9 = listTow.stream().map(User::getFlag).collect(toList());
        System.out.println("collect9--------"+collect9.toString());


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
        listTow.stream().findFirst().ifPresent(t->listTows.add(t.getBankUserName()));
        listTows.stream().forEach(System.out::println);

        OptionalInt max = listTow.stream().mapToInt(User::getFlag).max();
        System.out.println("max:"+ max.getAsInt());

        List<User> c = listTow.stream().filter(t -> JudgeUtils.equals(t.getBankUserName(), "甲3")).collect(toList());
        System.out.println("sss"+c);
//        int i = max.orElse(3);
//        System.out.println(i);
//        List<User> collect1 = listTow.stream().filter(t -> t.getFlag()>2).skip(1).collect(Collectors.toList());
//        System.out.println(collect1);
//        IntSummaryStatistics collect2 = listTow.stream().collect(summarizingInt(User::getFlag));
//        System.out.println(collect2);
//
        Map<String, User> collect3 = listTow.stream().collect(
                groupingBy(t -> t.getBankUserName(),collectingAndThen(minBy(Comparator.comparingInt(User::getFlag)), Optional::get)));

        Map<String, User> collect4 = listTow.stream().collect(
                groupingBy(t -> t.getBankUserName(),collectingAndThen(maxBy(Comparator.comparingInt(User::getFlag)), Optional::get)));

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
        calendar.setTime( sdf.parse(fileDate));
        //计算前一个月的日期
        calendar.add(Calendar.MONTH, -1);
        fileDate = sdf.format(calendar.getTime());
        System.out.println(fileDate);
        String format = LocalDate.parse(fileDate, DateTimeUtils.DEFAULT_DATE_FORMATTER).minusMonths(1L).format(DateTimeUtils.DEFAULT_DATE_FORMATTER);
        System.out.println(format);
        boolean contains = list.contains(4);

        System.out.println(contains);

        boolean a = list.stream().filter(t -> t==4).findAny().isPresent();
        boolean b = list.stream().anyMatch(t -> t==4);
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
        System.out.println(str.replaceAll(String.valueOf((char)45),""));

        char character = 'a';
        int ascii = (int) character;
        System.out.println(ascii);

        List<Integer> listA = Arrays.asList(1,4, 5);
        List<Integer> listB = Arrays.asList();
        List<String> collect8 = Optional.of(listTow1)
                .orElse(new ArrayList<>()).stream().map(User::getBankUserName).collect(toList());
        System.out.println(collect8.toString());
        List<Integer> integersq = Optional.of(listB).orElse(new ArrayList<>());
        System.out.println(integersq.toString());

        List<Integer> sss = Arrays.asList(1,4, 5);
        listA.stream().forEach(t->{
            if (t>3){
                System.out.println("---"+t);
                return;
            }
            System.out.println(t);
        });

        for (Integer s1:sss) {
            if (s1>3){
                System.out.println("---"+s1);
                continue;
            }
            System.out.println(s1);
        }
        TestUser testUser =new TestUser();
        testUser.setB("0");
        System.out.println(Integer.parseInt(testUser.getB()));

        List<User> listTow3 = new ArrayList<>();
        listTow3.add(User.builder()
                .bankUserName("甲1").password("1").flag(1).build());
        listTow3.add(User.builder()
                .bankUserName("甲2").password("2").flag(2).build());

        List<User> collect10 = listTow3.stream().filter(t -> JudgeUtils.equals("1", t.getPassword())).collect(toList());
        System.out.println("collect10-------------");
        collect10.stream().forEach(System.out::println);
    }

}
