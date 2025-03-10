package task1;

public record Constant(double value) implements Expr {
    @Override
    public double evaluate() {
        return value;
    }

    @Override
    public String toString() {
        // Форматируем вывод, убирая нули после точки
        return String.format("%s", value == (long) value ? String.valueOf((long) value) : String.valueOf(value));
    }
}
