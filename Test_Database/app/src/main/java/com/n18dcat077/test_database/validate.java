package com.n18dcat077.test_database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validate {
    DatabaseQLCB db;
    public static boolean validateDate(String date) {
        Pattern pattern = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(date);
        return matcher.find();
    }

    public static boolean validateLetters(String txt) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+[\\-'\\s]?[a-zA-Z ]+$",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }

    public static boolean validatephoneNumber(String sdt){
        Pattern pattern = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
        Matcher matcher  = pattern.matcher(sdt);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public static String chuanHoaDanhTuRieng(String str) {
        str = chuanHoa(str);
        String temp[] = str.split(" ");
        str = "";
        for (int i = 0; i < temp.length; i++) {
            str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1).toLowerCase();
            if (i < temp.length - 1) {
                str += " ";
            }
        }
        return str;
    }
    public static String chuanHoa(String s) {
        s = s.trim();
        s = s.replaceAll("\\s+", " ");
        return s;
    }
    public static boolean isNumber(String s){
        try {
            Integer.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean KiemTraChiPhi(String s){
        int a =Integer.parseInt(s);
        if(a<10000 || a>50000){
            return false;
        }
        return true;
    }
}
