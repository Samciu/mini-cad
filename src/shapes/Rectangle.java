package shapes;

import java.awt.Graphics;

public class Rectangle extends Shape {

	public Rectangle(int x1, int y1, int x2, int y2) {
		super(x1, y1, x2, y2);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

}
