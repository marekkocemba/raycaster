package mk.raycaster.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Texture {


    public static List<int[][]> initTextures() {
        try {
            BufferedImage baseWall = ImageIO.read(new File("C:\\Users\\mkocemba\\Desktop\\quake\\tech14_2.png"));
            BufferedImage baseFloor = ImageIO.read(new File("C:\\Users\\mkocemba\\Desktop\\quake\\plat_top1_cable.png"));
            BufferedImage baseDeck = ImageIO.read(new File("C:\\Users\\mkocemba\\Desktop\\quake\\tech01_7.png"));
            // convert BufferedImage to byte[]
            var baseWallBytes = toByteArray512(baseWall);
            var baseFloorBytes = toByteArray512(baseFloor);
            var baseDeckBytes = toByteArray512(baseDeck);

            List<int[][]> result = new ArrayList<>();
            result.add(baseWallBytes);
            result.add(baseFloorBytes);
            result.add(baseDeckBytes);
            return result;

        } catch (IOException e) {
            return null;
        }
    }
    public static int[][] toByteArray(BufferedImage bi){

        int[][] image = new int[64][64];

        for(int x = 0; x < 64 ; x++){
            for(int y = 0; y < 64 ; y++){
                image[x][y] = bi.getRGB(x, y);
            }
        }
        return image;

    }

    public static int[][] toByteArray512(BufferedImage bi){

        int[][] image = new int[512][512];

        for(int x = 0; x < 512 ; x++){
            for(int y = 0; y < 512 ; y++){
                image[x][y] = bi.getRGB(x, y);
            }
        }
        return image;

    }
}
