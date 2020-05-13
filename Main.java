import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

public class Main {
    private static int format, width, height, max;
    private static int[][][] image;

    public static void main() throws Exception {
        File file = new File("nickk.txt");
        FileInputStream fis = new FileInputStream(file);
        int temp = 0;

        image = new int[169][297][1];

        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                temp = fis.read();
                System.out.print(temp-48 + " ");            
            }
            System.out.println();
            fis.read(); // SKIPS CR
            fis.read(); // SKIPS LF
        }

        fis.close();
    }
}