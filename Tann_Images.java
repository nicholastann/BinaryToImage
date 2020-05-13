import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.*;
public class Tann_Images {
    private static int format, width, height, max;
    private static int[][][] image;
    private static DrawPanel drawPanel;
    private static MenuPanel menuPanel;

    public static void main() throws Exception {
        File file = new File("nickk.txt");
        FileInputStream fis = new FileInputStream(file);
        int temp;
        fis.read(); //skips P
        format = fis.read()-48;
        fis.read(); //skips cr
        fis.read(); //skips lf

        temp = fis.read();
        while (temp != 32) {
            width = width * 10 + temp -48;
            temp = fis.read();
        }

        temp = fis.read();
        while(temp!= 13) {
            height = height * 10 + temp -48;
            temp = fis.read();
        }	
        fis.read(); //skips lf

        temp = fis.read();
        while(temp != 13) {
            max = max * 10 +temp-48;
            temp = fis.read();	
        }
        fis.read(); 
        System.out.println(height);
        System.out.println(width);
        System.out.println(format);
        System.out.println(max);
        //skips lf
        //System.out.println(max);
        if (format != 2) image = new int[height][width][format];

        else  image = new int[height][width][format-1];

        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                for (int p=0; p<format; p++ ) {
                    temp = fis.read();
                    int doggo = 0;
                    while(temp != 32 && temp != 13) {
                        doggo = doggo *10 + temp -48;
                        temp = fis.read();
                    }
                    image[r][c][p] = doggo;
                    if (format ==2) p++;
                }
            }
            //System.out.println();
            if (temp != 13) fis.read(); // SKIPS CR
            fis.read(); // SKIPS LF
        }

        fis.close();
        JFrame frame = new JFrame("Image Viewer");
        drawPanel = new DrawPanel();
        menuPanel = new MenuPanel();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,800);
        frame.setLocationRelativeTo(null); 
        frame.add(drawPanel, BorderLayout.CENTER);
        frame.add(menuPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private static class DrawPanel extends JPanel {
        public DrawPanel() {
            setBackground(new Color(255,255,255));   
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for(int r=0; r<image.length; r++) {
                for(int c=0; c<image[r].length; c++) {
                    if (format == 1) {
                        if (image[r][c][0] == 1) {
                            g.setColor(new Color(255,255,255)); 
                        }
                        else {
                            g.setColor(new Color(0,0,0));
                        }
                    }

                    else if (format == 2)  {
                        int val = 255/max*image[r][c][0];
                        g.setColor(new Color(val,val,val));
                    }
                    else if (format == 3){
                        int red = 255/max*image[r][c][0];
                        if (red > max) red = max;
                        if (red < 0) red = 0;
                        int green = 255/max*image[r][c][1];
                        if (green > max) green = max;
                        if (green < 0) green = 0;
                        int blue = 255/max*image[r][c][2];
                        if (blue > max) blue = max;
                        if (blue < 0) blue = 0;

                        g.setColor(new Color(red,green,blue));
                    }
                    g.fillRect(c,r,1,1);
                }
            }

        }
    }

    private static class MenuPanel extends JPanel implements ActionListener {
        JButton[] buttons = new JButton[8];
        public MenuPanel() {
            setBackground(new Color(255,255,255));
            setLayout(new GridLayout(1,8));
            buttons = new JButton[8];
            buttons[0] = new JButton(new ImageIcon("1_Save.png"));
            buttons[1] = new JButton(new ImageIcon("2_Load.png"));
            buttons[2] = new JButton(new ImageIcon("3_RotateLeft.png"));
            buttons[3] = new JButton(new ImageIcon("4_RotateRight.png"));
            buttons[4] = new JButton(new ImageIcon("5_FlipHorizontal.png"));
            buttons[5] = new JButton(new ImageIcon("6_FlipVertical.png"));
            buttons[6] = new JButton(new ImageIcon("7_Brighten.png"));
            buttons[7] = new JButton(new ImageIcon("8_Darken.png"));
            for(JButton b : buttons) {
                b.setPreferredSize(new Dimension(100,100));
                b.addActionListener(this);
                this.add(b);
            }
        }

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == buttons[0]) {
                System.out.println("Save");
                // Save the file to output.ppm
                drawPanel.repaint();
            }
            else if(e.getSource() == buttons[1]) {
                System.out.println("Load");
                // Load the file from input.ppm
                drawPanel.repaint();
            }
            else if(e.getSource() == buttons[2]) {
                System.out.println("Rotate Left");
                // Rotate the image 90 degrees counter clockwise

                if (format == 2) { int[][][] image2 = new int[width][height][format-1]; }
                int[][][] image2 = new int[width][height][format];  
                for (int r = 0; r<width; r++) {
                    for (int c=0; c < height; c++) {
                        for (int x = 0; x<format; x++) {
                            image2[r][c][x] = image[c][width-1-r][x];
                            if (format == 2) x++;
                        }
                    }
                }
                image = image2;
                int temp = width;
                width = height;
                height = temp;
                drawPanel.repaint();
            }
            else if(e.getSource() == buttons[3]) {
                System.out.println("Rotate Right");
                // Rotate the image 90 degrees clockwise

                if (format == 2) { int[][][] image2 = new int[width][height][format-1]; }
                int[][][] image2 = new int[width][height][format];  
                for (int r = 0; r<width; r++) {
                    for (int c=0; c < height; c++) {
                        for (int x = 0; x<format; x++) {
                            image2[r][c][x] = image[height-1-c][r][x];
                            if (format == 2) x++;
                        }
                    }
                }
                image = image2;
                int temp = width;
                width = height;
                height = temp;
                drawPanel.repaint();
            }
            else if(e.getSource() == buttons[4]) {
                System.out.println("Flip Horizontal");

                if (format == 2) { int[][][] image2 = new int[height][width][format-1]; }
                int[][][] image2 = new int[height][width][format]; 
                for (int r = 0; r<height; r++) {
                    for (int c=0; c < width; c++) {
                        for (int x = 0; x<format; x++) {
                            image2[r][c][x] = image[r][width-1-c][x];
                            if (format == 2) x++;
                        }
                    }
                }
                image = image2;
                // Flip the image horizontally
                drawPanel.repaint();
            }
            else if(e.getSource() == buttons[5]) {
                System.out.println("Flip Vertical");
                // Flip the image vertically
                if (format == 2) { int[][][] image2 = new int[height][width][format-1]; }
                int[][][] image2 = new int[height][width][format]; 
                for (int r = 0; r<height; r++) {
                    for (int c=0; c < width; c++) {
                        for (int x = 0; x<format; x++) {
                            image2[r][c][x] = image[height-1-r][c][x];
                            if (format == 2) x++;
                        }
                    }
                }
                image = image2;
                drawPanel.repaint();
            }
            else if(e.getSource() == buttons[6]) {
                System.out.println("Brighten");
                // Raise every pixels value by 10
                for (int r = 0; r<height; r++) {
                    for (int c=0; c < width; c++) {
                        for (int x = 0; x<format; x++) {
                            image[r][c][x] = (int)((image[r][c][x]+1)*1.1);
                            if (format == 2) x++;
                        }
                    }
                }

                drawPanel.repaint();
            }
            else if(e.getSource() == buttons[7]) {
                System.out.println("Darken");
                // Darken every pixels value by 10
                for (int r = 0; r<height; r++) {
                    for (int c=0; c < width; c++) {
                        for (int x = 0; x<format; x++) {
                            image[r][c][x] = (int)((image[r][c][x]+1)*.9);
                            if (format == 2) x++;
                        }
                    }
                }
                drawPanel.repaint();
            }
        }
    }
}