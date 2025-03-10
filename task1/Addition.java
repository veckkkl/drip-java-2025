package task1;

public record Addition(Expr val1, Expr val2) implements Expr {
    @Override
    public double evaluate() {
        return val1.evaluate() + val2.evaluate();
    }

    @Override
    public String toString() {
        return "(" + val1 + " + " + val2 + ")";
    }
}