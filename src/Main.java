import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * Created by Janusz on 2014-11-02.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        int red, green, blue;
        File plik = new File("C:\\Users\\Janusz\\IdeaProjects\\Shape_detection\\sunshine2k.bmp");
        BufferedImage picture = null;
        try {
            picture = ImageIO.read(plik);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = picture.getWidth();
        System.out.println("szerokosc " + width);
        int height = picture.getHeight();
        Color[][] tab_RGB = new Color[width][height];
        Color[][] tab_grey_scale = new Color[width][height];
        Color[][] tab_edge_X = new Color[width][height];
        Color[][] tab_edge_Y = new Color[width][height];
        Color[][] tab_edge_white = new Color[width][height];
        int[][] tab_Red = new int[width][height];
        int[][] tab_Green = new int[width][height];
        int[][] tab_Blue = new int[width][height];
        System.out.println("wysokosc " + height);

        //wczytanie ze zdjecia kolorow do tab_RGB
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                tab_RGB[i][j] = new Color(picture.getRGB(i, j));
                tab_Red[i][j] = tab_RGB[i][j].getRed();
                tab_Green[i][j] = tab_RGB[i][j].getGreen();
                tab_Blue[i][j] = tab_RGB[i][j].getBlue();
            }

        //utworzenie zdjecia w skali szarosci
        BufferedImage image_grey = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                tab_grey_scale[i][j] = new Color((int) (0.3 * tab_Red[i][j]), (int) (0.59 * tab_Green[i][j]), (int) (0.11 * tab_Blue[i][j]));
                image_grey.setRGB(i, j, tab_grey_scale[i][j].getRGB());
            }
        File outputfile = new File("image_grey.bmp");
        ImageIO.write(image_grey, "bmp", outputfile);

        for (int i = 0; i < width; i++)
            tab_edge_X[i][0] = tab_edge_Y[i][0] = tab_edge_X[i][width-1] = tab_edge_Y[i][width-1] = new Color (0, 0, 0);

        for (int i = 0; i < height; i++)
            tab_edge_X[0][i] = tab_edge_Y[0][i] = tab_edge_X[height-1][i] = tab_edge_Y[height-1][i] = new Color (0, 0, 0);

        //utworzenie zdjecia po zastosowaniu operatora Sobela X
        BufferedImage image_edge_X = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 1; i < width-1; i++)
            for (int j = 1; j < height-1; j++) {

                red = tab_grey_scale[i-1][j-1].getRed()
                        -tab_grey_scale[i-1][j+1].getRed()
                        +2*tab_grey_scale[i-1][j].getRed()
                        -2*tab_grey_scale[i+1][j].getRed()
                        +tab_grey_scale[i-1][j-1].getRed()
                        -tab_grey_scale[i+1][j+1].getRed();

                green = tab_grey_scale[i-1][j-1].getGreen()
                        -tab_grey_scale[i-1][j+1].getGreen()
                        +2*tab_grey_scale[i-1][j].getGreen()
                        -2*tab_grey_scale[i+1][j].getGreen()
                        +tab_grey_scale[i-1][j-1].getGreen()
                        -tab_grey_scale[i+1][j+1].getGreen();

                blue = tab_grey_scale[i-1][j-1].getBlue()
                        -tab_grey_scale[i-1][j+1].getBlue()
                        +2*tab_grey_scale[i-1][j].getBlue()
                        -2*tab_grey_scale[i+1][j].getBlue()
                        +tab_grey_scale[i-1][j-1].getBlue()
                        -tab_grey_scale[i+1][j+1].getBlue();

                if (red<0) red = 0;
                if (red>255) red = 255;
                if (green<0) green = 0;
                if (green>255) green = 255;
                if (blue<0) blue = 0;
                if (blue>255) blue = 255;

                tab_edge_X[i][j] = new Color(red, green, blue);
                image_edge_X.setRGB(i, j, tab_edge_X[i][j].getRGB());
            }
        outputfile = new File("image_edge_X.bmp");
        ImageIO.write(image_edge_X, "bmp", outputfile);

        //utworzenie zdjecia po zastosowaniu operatora Sobela Y
        BufferedImage image_edge_Y = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 1; i < width-1; i++)
            for (int j = 1; j < height-1; j++) {

                red = tab_edge_X[i-1][j-1].getRed()
                        +2*tab_edge_X[i][j-1].getRed()
                        +tab_edge_X[i+1][j-1].getRed()
                        -tab_edge_X[i-1][j+1].getRed()
                        -2*tab_edge_X[i][j+1].getRed()
                        -tab_edge_X[i+1][j+1].getRed();

                green = tab_edge_X[i-1][j-1].getGreen()
                        +2*tab_edge_X[i][j-1].getGreen()
                        +tab_edge_X[i+1][j-1].getGreen()
                        -tab_edge_X[i-1][j+1].getGreen()
                        -2*tab_edge_X[i][j+1].getGreen()
                        -tab_edge_X[i+1][j+1].getGreen();

                blue = tab_edge_X[i-1][j-1].getBlue()
                        +2*tab_edge_X[i][j-1].getBlue()
                        +tab_edge_X[i+1][j-1].getBlue()
                        -tab_edge_X[i-1][j+1].getBlue()
                        -2*tab_edge_X[i][j+1].getBlue()
                        -tab_edge_X[i+1][j+1].getBlue();

                if (red<0) red = 0;
                if (red>255) red = 255;
                if (green<0) green = 0;
                if (green>255) green = 255;
                if (blue<0) blue = 0;
                if (blue>255) blue = 255;

                tab_edge_Y[i][j] = new Color(red, green, blue);
                image_edge_Y.setRGB(i, j, tab_edge_Y[i][j].getRGB());
            }
        outputfile = new File("image_edge_Y.bmp");
        ImageIO.write(image_edge_Y, "bmp", outputfile);

        //wybielanie i splaszczanie krawedzi
        BufferedImage image_white_edge = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                tab_edge_white[i][j] = (tab_edge_Y[i][j].getRed()<100 && tab_edge_Y[i][j].getGreen()<100 && tab_edge_Y[i][j].getBlue()<100) ? new Color(0, 0, 0) : new Color (255, 255, 255);
                image_white_edge.setRGB(i, j, tab_edge_white[i][j].getRGB());
            }
        outputfile = new File("image_white_edge.bmp");
        ImageIO.write(image_white_edge, "bmp", outputfile);
    }
}