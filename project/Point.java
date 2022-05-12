package project;

public class Point {
    // Два поля - два вещественных числа - координаты точки
    public double x;
    public double y;

    // Конструктор - принимает два вещественных числа - создает по ним точку
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "\t(" + x + ", " + y + ")";
    }
}
