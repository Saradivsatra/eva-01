package project;

// импорты
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// класс Окна, в котором будет отрисовываться задача
public class MyFrame extends JFrame implements ActionListener {

    // тут будем хранить все точки
    List<Point> points = new ArrayList<>();

    // тут будем хранить все линии
    List<Line> lines = new ArrayList<>();

    // запуск нашего окна осуществляется здесь
    public static void main(String[] args) {
        new MyFrame("project");
    }

    // кнопки добавить, сохранить в файл, прочитать файл, решить задачу и очистить доску
    JButton add;
    JButton save;
    JButton read;
    JButton solve;
    JButton clear;
    JButton rand;

    // текстовые поля для ввода :
    //
    // х-овой координаты точки,
    // у-овой координаты точки,
    // отображения инф-ции о последней введеной точке
    JTextField tfX;
    JTextField tfY;
    JTextArea tf_INFO;

    // инициализация нашего окна
    public MyFrame(String s) {
        super(s); // заголовок окна

        // Инициализация кнопки
        add = new JButton("add");
        add.setBounds(270, 10, 200, 50); // параметры кнопки
        add.addActionListener(this); // добавляем слушателя к кнопке (обработать нажатие)

        // Инициализация кнопки
        save = new JButton("save to file");
        save.setBounds(50, 160, 200, 50); // параметры кнопки
        save.addActionListener(this); // добавляем слушателя к кнопке (обработать нажатие)

        // Инициализация кнопки
        read = new JButton("read file");
        read.setBounds(50, 220, 200, 50); // параметры кнопки
        read.addActionListener(this); // добавляем слушателя к кнопке (обработать нажатие)

        // Инициализация кнопки
        clear = new JButton("clear");
        clear.setBounds(50, 350, 200, 50); // параметры кнопки
        clear.addActionListener(this); // добавляем слушателя к кнопке (обработать нажатие)

        // Инициализация кнопки
        solve = new JButton("solve");
        solve.setBounds(50, 300, 200, 50); // параметры кнопки
        solve.addActionListener(this); // добавляем слушателя к кнопке (обработать нажатие)

        rand = new JButton("addRandom");
        rand.setBounds(270, 60, 200, 50); // параметры кнопки
        rand.addActionListener(this); // добавляем слушателя к кнопке (обработать нажатие)

        // Инициализации текстовых полей
        tfX = new JTextField("x here");
        tfX.setBounds(50, 10, 80, 50); // параметры текстового поля

        tfY = new JTextField(" y here");
        tfY.setBounds(155, 10, 80, 50); // параметры текстового поля

        tf_INFO = new JTextArea("");
        tf_INFO.setBounds(50, 80, 200, 50); // параметры текстового поля

        // метод, добавляющий все компоненты к нашему окну
        addComponents();

        // параметры окна
        getContentPane().setBackground(Color.darkGray);
        setLayout(null);
        setSize(1000, 1000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addMouseListener(new MyListener()); // поддержка ввода точек мышью
    }

    // метод, добавляющий все компоненты к нашему окну
    private void addComponents() {
        add(tfX);
        add(tfY);
        add(tf_INFO);
        add(add);
        add(read);
        add(clear);
        add(save);
        add(solve);
        add(rand);
    }

    // метод, обрабатывающий нажатие на определенную кнопку
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == solve) {
            solve(); // если нажали solve -> показываем решение
        }

        if (e.getSource() == clear) { // нажали clear -> все очищаем и перерисовываем
            points.clear();
            lines.clear();
            repaint();
        }

        if (e.getSource() == add) { // нажали add -> получем информацию из текстовых полей
            double x, y;

            x = Double.parseDouble(tfX.getText()); // получаем эту информацию здесь
            y = Double.parseDouble(tfY.getText()); // и здесь

            points.add(new Point(x, y)); // добавляем в points новую точку
            repaint(); // перерисовываем
        }
        if (e.getSource() == rand) { // нажали add -> получем информацию из текстовых полей
            double x, y;

            x = Math.random()*1000; // получаем эту информацию здесь
            y = Math.random()*1000; // и здесь

            points.add(new Point(x, y)); // добавляем в points новую точку
            repaint(); // перерисовываем
        }

        if (e.getSource() == save) { // нажали save -> сохраняем все точки в файл output.txt

            // при работе с файлами необходима конструкция try { ... } catch () { ... }
            // она необходима, чтобы ловить ошибки/исключения
            // пример: нельзя открыть файл

            File file = new File("output.txt"); // создали файл
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file)); // для записи в файл
                for (Point point : points) { // бежим по листу points -> каждую точку из этого листа записываем в файл
                    out.write(point.x + " " + point.y + "\n");
                }
                out.close(); // закрываем поток вывода информации
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == read) { // нажали read -> читаем данные из файла

            // опять try { … } catch () { … }

            try {
                Scanner in = new Scanner(new File("input.txt")); // Сканнером читаем данные из входного файла

                while (in.hasNextLine()) { // пока есть след строка в файле ->
                    String s = in.nextLine(); // считываем строку

                    StringBuilder cX = new StringBuilder(); // наша будущая х-овая координата
                    StringBuilder cY = new StringBuilder(); // наша будущая н-овая координата

                    // каждая строка в входном файле имеет вид:
                    // a b
                    // a, b - какие-то числа
                    // бежим по этой строке, пока не находим пробел
                    // все символы до этого пробела запоминаем в одну строку
                    // все символы после пробела - в другую

                    for (int i = 0; i < s.length(); i++) {
                        if (s.charAt(i) != ' ') {
                            cX.append(s.charAt(i));
                        } else {
                            for (int j = i + 1; j < s.length(); j++) {
                                cY.append(s.charAt(j));
                            }
                            break;
                        }
                    }

                    double x = Double.parseDouble(String.valueOf(cX)); // преобразовываем строку в число
                    double y = Double.parseDouble(String.valueOf(cY)); // преобразовываем строку в число

                    points.add(new Point(x, y)); // добавляем в points новую точку
                    repaint(); // отрисовываем
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    // метод для графической демонстрации решения
    private void solve() {
        Graphics g = getGraphics(); // графика

        // это необязательно рассказывать
        Graphics2D g1 = (Graphics2D) g;
        BasicStroke pen1 = new BasicStroke(5); //толщина линии 20
        g1.setStroke(pen1);
        // это необязательно рассказывать

        lines = getLines(points); // заполняем лист линий всевозможными линиями

        Quadrilateral quadrilateral = maxSize(lines, g); // из него берем максимальный по площади 4-х угольник
        g.setColor(Color.BLACK); // меняем цвет на черный

        // рисуем каждую точку
        for (Point point : points) {
            g.drawOval((int) point.x, (int) point.y, 2, 2);
        }

        // рисуем этот 4-х угольник красным
        if (quadrilateral != null) {
            g.setColor(Color.RED);
            quadrilateral.draw(g);
        }
    }

    // Метод отрисовки
    @Override
    public void paint(Graphics g) {
        super.paint(g); // так надо

        // необзяталельно
        Graphics2D g1 = (Graphics2D) g;
        BasicStroke pen1 = new BasicStroke(6); //толщина линии 6
        g1.setStroke(pen1);

        g1.setColor(Color.GREEN); // цвет == зеленый
        for (Point point : points) { // рисуем каждую точку
            g.drawOval((int) point.x, (int) point.y, 5, 5);
            g.fillOval((int) point.x, (int) point.y, 5, 5);
        }
    }

    // Метод, заполняющий массив отрезков по данному мн-ву точек
    public List<Line> getLines(List<Point> points) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                lines.add(new Line(points.get(i), points.get(j))); // добавляем отрезок, соединяющий (i, j) точки : i = 0 .. size, j = i + 1 .. size
            }
        }

        return lines; // возвращаем этот лист
    }

    // Метод, ищущий 4-х угольник максимальной площади
    public Quadrilateral maxSize(List<Line> lines, Graphics g) {
        Quadrilateral maxQ = null;
        for (int i = 0; i < lines.size(); i++) {
            for (Line line : lines) {
                // Бежим двумя циклами по всему мн-ву отрезков
                // Перебираем все пары отрезков
                if (lines.get(i).hasIntersection(line) && !lines.get(i).checkCommonPoint(line)) {
                    Quadrilateral quadrilateral = new Quadrilateral(lines.get(i), line);
                    // Находим 4-х угольник максимальной площади
                    if (maxQ == null) {
                        maxQ = quadrilateral;
                    } else {
                        if (maxQ.square() < quadrilateral.square()) {
                            maxQ = new Quadrilateral(quadrilateral.l1, quadrilateral.l2);
                        }
                    }
                }
            }
        }

        // Возвращаем 4-х угольник максимальной площади
        return maxQ;
    }

    // класс чтобы обрабатывать нажатия мышкой на Окно (именно ввод точек, а не нажатия на кнопки)
    private class MyListener implements MouseListener {
        // При клике мышкой - добавляем эту точку в лист и перерисовываем
        @Override
        public void mouseClicked(MouseEvent e) {
            points.add(new Point(e.getX(), e.getY()));

            assert points.size() > 0;

            tf_INFO.setText("Last point was : \n    " + "\n" + points.get(points.size() - 1));
            repaint();
        }

        // все что ниже - не используется в данной задаче
        // но эти методы должны быть, даже если имеют пустые тела как здесь

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
