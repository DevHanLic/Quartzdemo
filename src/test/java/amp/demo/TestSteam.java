package amp.demo;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class TestSteam {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> s = list.stream()
                .map(t -> t * t)
//                .filter(t->t>=16)
//                .forEach(System.out::println);
                .collect(Collectors.toList());
        System.out.println(s);
        //*
        List<User> listTow = new ArrayList<>();
        listTow.add(User.builder()
                .bankUserName("甲4").password("4").flag(4).build());
        listTow.add(User.builder()
                .bankUserName("甲1").password("1").flag(1).build());
        listTow.add(User.builder()
                .bankUserName("甲2").password("2").flag(2).build());
        listTow.add(User.builder()
                .bankUserName("甲3").password("3").flag(3).build());
        listTow.add(User.builder()
                .bankUserName("甲3").password("3").flag(4).build());
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
//        OptionalInt max = listTow.stream().mapToInt(User::getFlag).max();
//        System.out.println(max.getAsInt());
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
