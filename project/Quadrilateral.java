package project;

import java.awt.*;

public class Quadrilateral {
    // 6 полей - два отрезка (диагонали) и 4 точки (вершины)
    Line l1, l2;
    Point p1, p2, p3, p4;

    // Конструктор - принимающий два отрезка (диагонали) - по ним создает четырехугольник
    public Quadrilateral(Line l1, Line l2) {
        this.p1 = l1.p1;
        this.p2 = l1.p2;
        this.p3 = l2.p1;
        this.p4 = l2.p2;
        this.l1 = l1;
        this.l2 = l2;
    }

    // Метод, считающий расстояние между двумя точками
    private double getLength(Point p1, Point p2) {
        double q = (p1.x - p2.x) * (p1.x - p2.x);
        double s = (p1.y - p2.y) * (p1.y - p2.y);

        return Math.sqrt(q + s);
    }

    // Метод, высчитывающий площадь четырехугольника путем сложения площадей двух треугольник по ф-ле Герона
    private double area(Point a, Point b, Point c, Point d) {
        double ab = getLength(a, b);
        double bc = getLength(b, c);
        double ac = getLength(a, c);
        double cd = getLength(c, d);
        double ad = getLength(a, d);

        return triangleSquare(ab, bc, ac) + triangleSquare(cd, ad, ac);
    }

    // Метод, высчитывающий площадь тр-ка по ф-ле Герона
    private double triangleSquare(double a, double b, double c) {
        double p = (a + b + c) / 2;

        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    // Метод, возвращающий площадь текущего 4-х угольника
    public double square() {
        return area(p1, p3, p2, p4);
    }

    // Метод, позволяющий отрисовывать текущий 4-х угольник
    public void draw(Graphics g) {
        g.drawLine((int) p1.x, (int) p1.y, (int) p3.x, (int) p3.y);
        g.drawLine((int) p3.x, (int) p3.y, (int) p2.x, (int) p2.y);
        g.drawLine((int) p2.x, (int) p2.y, (int) p4.x, (int) p4.y);
        g.drawLine((int) p4.x, (int) p4.y, (int) p1.x, (int) p1.y);
    }
}
