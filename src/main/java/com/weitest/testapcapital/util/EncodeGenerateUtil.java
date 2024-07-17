package com.weitest.testapcapital.util;


import java.util.Calendar;

public class EncodeGenerateUtil {
    public static final String USER_RECHARFE ="userRecharge";
    public static final String PLACE_ORDER= "placeOrder";

    public static String getRecordCode(String kinds, int userId){
        Calendar c = Calendar.getInstance();

        String flag="";
        switch (kinds){
            case USER_RECHARFE://用戶充值
                flag="UR";
                break;
            case PLACE_ORDER://訂單賣出
                flag="PO";
                break;
            default:
        }
        return flag+((c.get(Calendar.YEAR) - 2000) + c.get(Calendar.MONTH) + c.get(Calendar.DATE)) +
                String.format("%03d", ((c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) ))) +
                String.format("%03d", c.get(Calendar.MILLISECOND)) +
                String.format("%03d", userId % 1000) +
                String.valueOf((Math.random() * 9 + 1)).substring(2, 9);
    }

    public static void main(String[] args)  {
        String x = getRecordCode(USER_RECHARFE, 1807364);
        System.out.println(x);
    }

}

