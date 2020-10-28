package amp.demo;

import java.text.DecimalFormat;

public class CreditInstalments {
    public static void main(String[] args) {
        // 分期总额：5W
        double money = 50000;
        // 分期税率
        double taxRate = 0.0064;
        // 期数
        int periods = 24;
        // 每月分期手续费
        double commission = money*taxRate;
        double all = 0;
        for(int i=0;i<periods;i++){
            all = all + commission;
        }

        System.out.println("每月还款手续费： " + commission);

        DecimalFormat df = new DecimalFormat("######0.00");
        double monthMoney = Double.parseDouble(df.format(money/periods));
        System.out.println("每月还款分期额： " + monthMoney);
        System.out.println("每月还款总金额： " + (monthMoney + commission));

        System.out.println("分期总金额： " + money);
        // 总体手续费
        System.out.println("总共手续费： " + all);
        System.out.println("分期总额和手续费： " + (money + all));
    }
}
