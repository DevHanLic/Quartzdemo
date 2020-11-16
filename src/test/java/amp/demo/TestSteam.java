package amp.demo;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class TestSteam {

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> listT = Arrays.asList(1, 2, 3);

        List<Integer> collect1 = list.stream().filter(item -> !listT.contains(item)).collect(toList());

        System.out.println(collect1);

        // 并集
        List<Integer> listAll = list.parallelStream().collect(Collectors.toList());
        List<Integer> listAll2 = listT.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);
        System.out.println("---得到并集 listAll---");
        listAll.stream().forEach(System.out :: println);

        List<Integer> collect2 = listAll.stream().distinct().collect(toList());
        System.out.println("---去重得到并集 listAll---");
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

//        List<String> collect = listTow.stream().map(User::getBankUserName).sorted().collect(Collectors.toList());
//        System.out.println(collect);

        listTow.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getPassword())));
        System.out.println( listTow.stream().collect(toList()));

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        System.out.println(dateString);

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


    }
}
