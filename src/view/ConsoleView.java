package view;

import controller.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleView {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    {
        System.out.println("Калькулятор. Enter выход!");
    }

    public String getExpression() {
        String result = "";
        System.out.println("Введите выражение");
        try {
            result = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.isEmpty()) System.exit(0);
        return result;
    }

    public void print(int result, boolean roman) {
        System.out.println(roman ? Util.ArabToRim(result) : result);
    }
}
