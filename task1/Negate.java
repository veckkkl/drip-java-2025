package task1;

public record Negate(Expr expr) implements Expr {
    @Override
    public double evaluate() {
        return -expr.evaluate();
    }

    @Override
    public String toString() {
        return "-(" + expr + ")";
    }
}

