package cad;

import shapes.*;
import shapes.Rectangle;
import shapes.Shape;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Set;

public class Index {

    private Picture pic;
    private HashMap<String, Handler> handlerList = new HashMap<>();
    private Handler handler;
    private String actionCommand;
    private Shape currentShape;
    private HashMap<String, Color> colorList = new HashMap<>();

    private Index() {
        this.pic = new Picture(1024, 800);

        handlerList.put("Draw", new HandlerDraw());
        handlerList.put("Find", new HandlerFind());
        handler = handlerList.get("Find");

        colorList.put("黑色",Color.black);
        colorList.put("红色",Color.red);
        colorList.put("黄色",Color.yellow);
        colorList.put("蓝色",Color.blue);

        this.bindEvent();
    }

    class Handler {

        void mousePressed(int x, int y) {
        }

        void mouseDragged(int x, int y) {
        }
    }

    class HandlerDraw extends Handler {

        private Shape shape;
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        @Override
        void mousePressed(int x, int y) {
            x1 = x;
            y1 = y;
            switch (actionCommand) {
                case "直线":
                    shape = new Line(x, y, x, y);
                    break;
                case "圆形":
                    shape = new Circle(x, y, x, y);
                    break;
                case "矩形":
                    shape = new Rectangle(x, y, x, y);
                    break;
            }
            pic.add(shape);
            pic.repaint();
            currentShape = shape;
        }

        @Override
        void mouseDragged(int x, int y) {
            x2 = x;
            y2 = y;
            if (shape != null) {
                //shape = new Line(x1, y1, x2, y2);
                shape.set(x1, y1, x2, y2);
                pic.repaint();
            }
        }
    }

    class HandlerFind extends Handler {

        Shape shape;
        int x1;
        int y1;
        int x2;
        int y2;

        @Override
        void mousePressed(int x, int y) {
            shape = pic.getTheShape(x, y);
            x1 = x;
            y1 = y;
            if (shape != null) {
                shape.markStart();
                currentShape = shape;
            }
        }

        @Override
        void mouseDragged(int x, int y) {
            if (shape != null) {
                x2 = x;
                y2 = y;
                shape.drag(x1, y1, x2, y2);
                pic.repaint();
            }
        }
    }

    class MouseEvent implements MouseInputListener {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            handler.mousePressed(e.getX(), e.getY());
            JPanel panel = pic.shapesPanel;
            panel.requestFocus();
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
            handler = handlerList.get("Find");
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseDragged(java.awt.event.MouseEvent e) {
            handler.mouseDragged(e.getX(), e.getY());
        }

        @Override
        public void mouseMoved(java.awt.event.MouseEvent e) {

        }
    }

    private void bindEvent() {

        JPanel btnsPanel = new JPanel(); // 东北边面板（存放图形按钮）
        btnsPanel.setPreferredSize(new Dimension(110, 800));
        btnsPanel.setBackground(Color.gray);
        pic.add(btnsPanel, BorderLayout.EAST);

        String[] btnsName = {"直线", "圆形", "矩形"};

        for (String name : btnsName) {
            JButton btn = new JButton(name);
            btnsPanel.add(btn);
            btn.setPreferredSize(new Dimension(100, 30));
            btn.addActionListener(e -> {
                actionCommand = e.getActionCommand();
                handler = handlerList.get("Draw");
            });
        }

        JButton btnSave = new JButton("保存");
        btnSave.setPreferredSize(new Dimension(100, 30));
        btnsPanel.add(btnSave);
        btnSave.addActionListener(e -> pic.save());

        JButton btnRead = new JButton("读取");
        btnRead.setPreferredSize(new Dimension(100, 30));
        btnsPanel.add(btnRead);
        btnRead.addActionListener(e -> pic.read());

        JPanel colorPanel = new JPanel(); // 西边面板（存方颜色）
        colorPanel.setPreferredSize(new Dimension(110, 800));
        colorPanel.setBackground(Color.gray);
        pic.add(colorPanel, BorderLayout.WEST);

        Set<String> colorNames = colorList.keySet();

        for (String colorName : colorNames) {
            JButton btn = new JButton(colorName);
            colorPanel.add(btn);
            btn.setPreferredSize(new Dimension(100, 30));
            btn.addActionListener(e -> {
                if (currentShape != null) {
                    currentShape.setColor(colorList.get(e.getActionCommand()));
                    pic.repaint();
                }
            });
        }

        MouseEvent mouseEvent = new MouseEvent();
        JPanel panel = pic.shapesPanel;
        panel.addMouseListener(mouseEvent);
        panel.addMouseMotionListener(mouseEvent);

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char c = e.getKeyChar();
                int code = e.getKeyCode();
                if (currentShape != null) {
                    switch (code){
                        case 38:
                            currentShape.strokeAdd();
                            break;
                        case 40:
                            currentShape.strokeMinus();
                            break;
                        case 8:
                            pic.remove(currentShape);
                            break;
                    }
                    pic.repaint();
                }
            }
        });
        panel.setFocusable(true);
    }

    public static void main(String[] args) {
        Index index = new Index();
        Picture pic = index.pic;
        pic.draw();
    }
}