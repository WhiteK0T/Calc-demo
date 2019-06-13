package model;

public class Division implements ICalc{

    private final String operation = "/";

    @Override
    public String getOperation() {
        return operation;
    }

    @Override
    public int calc(int a, int b) {
        return a / b;
    }
}
