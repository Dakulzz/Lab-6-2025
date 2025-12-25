// functions/Functions.java
package functions;

import functions.meta.*;

public final class Functions {
    
    private Functions() {
        throw new UnsupportedOperationException("Нельзя создать объект класса Functions");
    }

    public static Function shift(Function f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY) {
        return new Scale(f, scaleX, scaleY);
    }
    
    public static Function power(Function f, double power) {
        return new Power(f, power);
    }
    
    public static Function sum(Function f1, Function f2) {
        return new Sum(f1, f2);
    }
    
    public static Function mult(Function f1, Function f2) {
        return new Mult(f1, f2);
    }
    
    public static Function composition(Function f1, Function f2) {
        return new Composition(f1, f2);
    }

    public static double integral(Function f, double leftX, double rightX, double step) {
        if (leftX < f.getLeftDomainBorder() || rightX > f.getRightDomainBorder()) {
            throw new IllegalArgumentException("ОИ выходит за ОО функции");
        }

        double sum = 0;
        double currentX = leftX;

        while (currentX < rightX) {
            double nextX = currentX + step;
            
            if (nextX > rightX) {
                nextX = rightX;
            }

            double y1 = f.getFunctionValue(currentX);
            double y2 = f.getFunctionValue(nextX);
            
            double h = nextX - currentX;
            sum += h * (y1 + y2) / 2.0;

            currentX = nextX;
        }

        return sum;
    }
}