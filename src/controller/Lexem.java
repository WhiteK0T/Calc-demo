package controller;


public class Lexem {

    private String str;

    private Intepretator intr;

    private boolean roman = false;

    public Lexem(Intepretator intr) {
        this.intr = intr;
    }

    public boolean isRoman() {
        return roman;
    }

    public int AnalizStrAndCalculation(String str) {
        this.str = str.trim();

        String[] strArr = new String[3];
        for (String operation : intr.getListSupportOperations()) {
            int indexOpr = str.indexOf(operation);
            if (indexOpr > 0) {
                strArr[0] = str.substring(0, indexOpr).trim();
                strArr[1] = String.valueOf(str.charAt(indexOpr));
                strArr[2] = str.substring(indexOpr + 1).trim();
            }
        }
       // String[] strArr = str.split(" ");

        if (strArr[0] == null) throw new NumberFormatException("Не поддерживаемая операция!");

        if (strArr[0].matches("^(M{0,3})(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX])$")) {
            roman = true;
        }


 /*       if (!intr.getListSupportOperations().contains(strArr[1])) {
            throw new NumberFormatException("Не поддерживаемая операция :" + strArr[1]);
        }
*/
        int a = roman ? Util.RimToArab(strArr[0]) : Integer.parseInt(strArr[0]);
        int b = roman ? Util.RimToArab(strArr[2]) : Integer.parseInt(strArr[2]);

        if (a > 10 | b > 10) throw new NumberFormatException("Числа больше 10 не поддерживаются!");

        return intr.startIntr(a, b, strArr[1]);
    }
}
