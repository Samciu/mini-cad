package shapes;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Picture extends JFrame {
    private static final long serialVersionUID = 1L;
    private int width;
    private int height;
    public ShapesPanel shapesPanel;

    private ArrayList<Shape> listShape = new ArrayList<>();

    private class ShapesPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Shape s : listShape) {
                s.draw(g);
            }
        }
    }

    public void add(Shape s) {
        listShape.add(s);
    }

    public void remove(Shape s) {
        listShape.remove(s);
    }

    public Picture(int width, int height) {
        this.shapesPanel = new ShapesPanel();
        add(this.shapesPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.width = width;
        this.height = height;
        this.setLocationRelativeTo(getOwner());
    }

    public void draw() {
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Shape getTheShape(int x, int y) {
        Shape shape = null;
        for (Shape s : listShape) {
            if (s.isMe(x, y)) {
                shape = s;
                break;
            }
        }
        return shape;
    }

    private File saveFile() {
        //选择要保存的位置以及文件名字和信息
        JFileChooser chooser = new JFileChooser(".");
        chooser.showSaveDialog(null);
        return chooser.getSelectedFile();
    }

    private File getFile() {
        //选择要保存的位置以及文件名字和信息
        JFileChooser chooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "cad* File", "cad");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

    public void save() {
        File file = saveFile();
        if (file != null) {
            String path = file.getPath();

            //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
            if(!path.contains(".cad")){
                path = path + ".cad";
            }

            try {
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(path)
                );
                out.writeObject(listShape);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void read() {
        File file = getFile();
        if (file != null) {
            try {
                ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream(file)
                );
                listShape = (ArrayList<Shape>) in.readObject();
                in.close();
                repaint();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void repaint() {
        shapesPanel.repaint();
    }
}