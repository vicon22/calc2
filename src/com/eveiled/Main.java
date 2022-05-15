package com.eveiled;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(calc(scanner.nextLine()));
    }

    public static String calc(String input){

        String ret;

        checkInput(input.split(" "));

        if (checkNumeralSystems(input.split(" ")[0]) == 0) {
            ret = calcArabic(input.split(" "));
        } else{
            ret = calcRoman(input.split(" "));
        }

        return ret;
    }

    private static String calcRoman(String[] arrayInput) {

        int ans = 0;
        int operand1 = RomanNumbers.RomanToArabic(arrayInput[0]);
        int operand2 = RomanNumbers.RomanToArabic(arrayInput[2]);

        if ("-".equals(arrayInput[1])) {
            ans = operand1 - operand2;
            if (ans < 1){
                throw new RuntimeException("в римской системе нет отрицательных чисел");
            }
        } else if ("+".equals(arrayInput[1])) {
            ans = operand1 + operand2;
        } else if ("*".equals(arrayInput[1])) {
            ans = operand1 * operand2;
        } else if ("/".equals(arrayInput[1])) {
            ans = operand1 / operand2;
        }

        return RomanNumbers.ArabicToRoman(ans);
    }

    private static String calcArabic(String[] arrayInput) {

        int ans = 0;
        int operand1 = Integer.parseInt(arrayInput[0]);
        int operand2 = Integer.parseInt(arrayInput[2]);

        if ("-".equals(arrayInput[1])) {
            ans = operand1 - operand2;
        } else if ("+".equals(arrayInput[1])) {
            ans = operand1 + operand2;
        } else if ("*".equals(arrayInput[1])) {
            ans = operand1 * operand2;
        } else if ("/".equals(arrayInput[1])) {
            ans = operand1 / operand2;
        }

        return Integer.toString(ans);
    }

    private static int checkNumeralSystems(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            if (RomanNumbers.RomanToArabic(s) != 0) {
                return 1;
            }
            else {
                return -1;
            }
        }
        return 0;
    }

    private static void checkInput(String[] arrayInput) {
        if (arrayInput.length == 1){
            throw new RuntimeException("строка не является математической операцией");
        }
        else if (arrayInput.length != 3){
            throw new RuntimeException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        else if (!(arrayInput[1].equals("+") || arrayInput[1].equals("-") || arrayInput[1].equals("*") || arrayInput[1].equals("/"))){
            throw new RuntimeException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        else if (checkNumeralSystems(arrayInput[0]) == -1 || checkNumeralSystems(arrayInput[2]) == -1){
            throw new RuntimeException("Неизвестные системы счисления у операндов");
        }
        else if (checkNumeralSystems(arrayInput[0]) != checkNumeralSystems(arrayInput[2])){
            throw new RuntimeException("используются одновременно разные системы счисления");
        }
        else if (checkNumeralSystems(arrayInput[0]) == 0 && (Integer.parseInt(arrayInput[0]) > 10 || Integer.parseInt(arrayInput[0]) < 1)){
            throw new RuntimeException("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
        }
        else if (checkNumeralSystems(arrayInput[2]) == 0 && (Integer.parseInt(arrayInput[2]) > 10 || Integer.parseInt(arrayInput[2]) < 1)){
            throw new RuntimeException("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
        }
        else if (checkNumeralSystems(arrayInput[0]) == 1 && (RomanNumbers.RomanToArabic(arrayInput[0]) > 10 || RomanNumbers.RomanToArabic(arrayInput[0]) < 1)){
            throw new RuntimeException("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
        }
        else if (checkNumeralSystems(arrayInput[2]) == 1 && (RomanNumbers.RomanToArabic(arrayInput[2]) > 10 || RomanNumbers.RomanToArabic(arrayInput[2]) < 1)){
            throw new RuntimeException("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
        }
    }

}
enum RomanNumbers {

    M(1000), CM(900), D(500), CD(400),
    C(100), XC(90), L(50), XL(40),
    X(10), IX(9), V(5), IV(4), I(1);


    int value;

    RomanNumbers(int value) {
        this.value = value;
    }

    static int RomanToArabic(String s) {

        int result = 0;
        int i = 0;

        while ((s.length() > 0) && (i < values().length)) {
            RomanNumbers symbol = values()[i];
            if (s.startsWith(symbol.name())) {
                result += symbol.value;
                s = s.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        return result;
    }

    static String ArabicToRoman(int value) {

        String ret = "";

        while (value != 0) {
            for (RomanNumbers number : RomanNumbers.values()) {
                if (number.value <= value) {
                    ret += number.toString();
                    value -= number.value;
                    break;
                }
            }
        }

        return ret;
    }
}