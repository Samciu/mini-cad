package shapes;

import java.awt.*;

public class Line extends Shape {

    public Line(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public boolean isMe(int x, int y) {
        boolean isMe = super.isMe(x,y);
        if (isMe && Math.abs(x1-x) * Math.abs(y2-y) == Math.abs(x2-x) * Math.abs(y1-y)){
            isMe = true;
        }
        return isMe;
    }
}
