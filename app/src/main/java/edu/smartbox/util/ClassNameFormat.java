package edu.smartbox.util;

/**
 * Created by Ankit on 21/01/17.
 */
public class ClassNameFormat {

    public String classnamechange(String class_No, String sec) {
        String str = null;
        switch (class_No) {
            case "1":
                str = "one" + sec.toUpperCase();
                break;
            case "2":
                str = "two" + sec.toUpperCase();
                break;
            case "3":
                str = "three" + sec.toUpperCase();
                break;
            case "4":
                str = "four" + sec.toUpperCase();
                break;
            case "5":
                str = "five" + sec.toUpperCase();
                break;
            case "6":
                str = "six" + sec.toUpperCase();
                break;
            case "7":
                str = "seven" + sec.toUpperCase();
                break;
            case "8":
                str = "eight" + sec.toUpperCase();
                break;
            case "9":
                str = "nine" + sec.toUpperCase();
                break;
            case "10":
                str = "ten" + sec.toUpperCase();
                break;
            case "11":
                str = "eleven" + sec.toUpperCase();
                break;
            case "12":
                str = "twelve" + sec.toUpperCase();
                break;
            default:
                str = "sorry";
        }

        return str;
    }
}
