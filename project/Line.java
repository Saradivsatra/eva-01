package project;

public class Line {
    // Два поля у отрезка - две точки - его концы
    public Point p1;
    public Point p2;

    // Конструктор, принимающий две точки - создает по ним отрезок
    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    private boolean intersect_1(double a, double b, double c, double d) {
        if (a > b) {
            double z = a;
            a = b;
            b = z;
        }
        if (c > d) {
            double x = c;
            c = d;
            d = x;
        }
        return Math.max(a, c) <= Math.min(b, d);
    }

    // Метод для вычисления определителя
    private double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }

    // Метод, возвращает значение "true" если с лежит между a и b
    private boolean between(double a, double b, double c) {
        double EPS = 1E-9;
        return Math.min(a, b) <= c + EPS && c <= Math.max(a, b) + EPS;
    }

    // Метод, возвращающий пересекаются ли отрезки AB, CD
    private boolean hasIntersection(Point a, Point b, Point c, Point d) {
        double A1 = a.y - b.y, B1 = b.x - a.x, C1 = -A1 * a.x - B1 * a.y;
        double A2 = c.y - d.y, B2 = d.x - c.x, C2 = -A2 * c.x - B2 * c.y;
        double zn = det(A1, B1, A2, B2);
        if (zn != 0) {
            double x = -det(C1, B1, C2, B2) / zn;
            double y = -det(A1, C1, A2, C2) / zn;
            return between(a.x, b.x, x) && between(a.y, b.y, y)
                    && between(c.x, d.x, x) && between(c.y, d.y, y);
        } else
            return det(A1, C1, A2, C2) == 0 && det(B1, C1, B2, C2) == 0
                    && intersect_1(a.x, b.x, c.x, d.x)
                    && intersect_1(a.y, b.y, c.y, d.y);
    }

    // Метод, возвращающий пересекается ли данный отрезок с каким-то другим отрезком l1
    public boolean hasIntersection(Line l1) {
        return hasIntersection(l1.p1, l1.p2, this.p1, this.p2);
    }

    // Метод, возвращающий длину текущего отрезка
    public double length() {
        return Math.sqrt(this.p1.x * this.p1.x + this.p2.x * this.p2.x);
    }

    // Метод, возвращающий имеет ли данный отрезок общие точки с отрезком l2
    public boolean checkCommonPoint(Line l2) {
        return ((p1.x == l2.p1.x && p1.y == l2.p1.y)
                || (p1.x == l2.p2.x && p1.y == l2.p2.y)
                || (p2.x == l2.p1.x && p2.y == l2.p1.y)
                || (p2.x == l2.p2.x && p2.y == l2.p2.y));
    }
}
