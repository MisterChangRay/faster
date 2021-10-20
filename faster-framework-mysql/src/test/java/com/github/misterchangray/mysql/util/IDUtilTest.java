package com.github.misterchangray.mysql.util;



class IDUtilTest {
    public static void main(String[] args) {
        IDUtil id = new IDUtil();
        id.setAppId(321);
        id.setAppInstanceId(52);

        System.out.println(id.parseDate(id.nextId()));
        System.out.println(id.parseAppId(id.nextId()));
        System.out.println(id.parseAppInstanceId(id.nextId()));

    }

}