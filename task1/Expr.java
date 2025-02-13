package task1;

public sealed interface Expr permits Constant, Negate, Exponent, Addition, Multiplication{
    double evaluate();
}
