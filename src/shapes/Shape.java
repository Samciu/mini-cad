package shapes;

import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable {

    int x1;
    int y1;
    int x2;
    int y2;
    private int x1Start;
    private int y1Start;
    private int x2Start;
    private int y2Start;
    private Color color;
    private float stroke;

    Shape(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = Color.black;
    }

    public void set(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.stroke = 1;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(stroke));
    }

    public boolean isMe(int x, int y) {
        boolean isMe = false;
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        if (minX <= x && x <= maxX && minY <= y && y <= maxY) {
            isMe = true;
        }
        return isMe;
    }

    public void drag(int x1, int y1, int x2, int y2) {
        int plusY = y2 - y1;
        int plusX = x2 - x1;
        this.x1 = x1Start + plusX;
        this.x2 = x2Start + plusX;
        this.y1 = y1Start + plusY;
        this.y2 = y2Start + plusY;
    }

    public void markStart() {
        x1Start = x1;
        x2Start = x2;
        y1Start = y1;
        y2Start = y2;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void strokeAdd() {
        this.stroke += 1;
    }

    public void strokeMinus() {
        this.stroke -= 1;
    }

    @Override
    public String toString() {
        return "x1:" + x1 + ";x2:" + x2 + ";y1:" + y1 + ";y2:" + y2 + super.toString();
    }
}
