// functions/FunctionPoint.java
package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable, Cloneable { // Задание 1: Added Cloneable
	
    private static final double EPSILON = 1e-9; // Использование EPSILON для сравнения float

	private double x;
    private double y;
	
	public FunctionPoint(double x, double y) {
		this.x=x;
		this.y=y;
	}
		
	public FunctionPoint(FunctionPoint point){
		this.x=point.x;
		this.y=point.y;
	}
	
	public FunctionPoint(){
		this.x=0;
		this.y=0;
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public void setX(double x) { this.x=x; }
	public void setY(double y) { this.y=y; } 

    // з1: String toString()
    @Override public String toString() {
        return "(" + x + "; " + y + ")";
    }

    // з1: boolean equals(Object o)
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunctionPoint)) return false;
        
        FunctionPoint that = (FunctionPoint) o;
        
        // сравнение чисел с плавающей точкой
        return Math.abs(this.x - that.x) < EPSILON && Math.abs(this.y - that.y) < EPSILON;
    }

    // з1: int hashCode()
    @Override public int hashCode() {
        long xBits = Double.doubleToLongBits(x);
        long yBits = Double.doubleToLongBits(y);
        
        // XOR (исключающее ИЛИ) для объединения 8 байт в 4
        int hash = (int) (xBits ^ (xBits >>> 32)); // Старшие и младшие 4 байта X
        hash ^= (int) (yBits ^ (yBits >>> 32));    // Старшие и младшие 4 байта Y
        
        return hash;
    }

    // з1: clone()
    @Override public Object clone() {
        try {
            // Простое клонирование, т.к. поля - примитивы (по сути, глубокое)
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError("FunctionPoint is Cloneable but clone failed.");
        }
    }
}