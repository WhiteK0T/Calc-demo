import controller.Intepretator;
import controller.Lexem;
import view.ConsoleView;

public class Calc {
    public static void main(String[] args) {
        Intepretator intepretator = new Intepretator();
        Lexem lexem = new Lexem(intepretator);
        ConsoleView consoleView = new ConsoleView();
        for ( ; ; ) {
            String s = consoleView.getExpression();
            int result = lexem.AnalizStrAndCalculation(s);
            consoleView.print(result, lexem.isRoman());
        }
    }
}
