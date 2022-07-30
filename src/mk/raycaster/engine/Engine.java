package mk.raycaster.engine;

import mk.raycaster.Configuration;

import java.util.List;

public class Engine {

    private final BufferSingleton buffer;
    private final List<int[][]> textures;
    int worldMap[][] =
            {
                    {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 4, 4, 6, 4, 4, 6, 4, 6, 4, 4, 4, 6, 4},
                    {8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                    {8, 0, 3, 3, 0, 0, 0, 0, 0, 8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6},
                    {8, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6},
                    {8, 0, 3, 3, 0, 0, 0, 0, 0, 8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                    {8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 4, 0, 0, 0, 0, 0, 6, 6, 6, 0, 6, 4, 6},
                    {8, 8, 8, 8, 0, 8, 8, 8, 8, 8, 8, 4, 4, 4, 4, 4, 4, 6, 0, 0, 0, 0, 0, 6},
                    {7, 7, 7, 7, 0, 7, 7, 7, 7, 0, 8, 0, 8, 0, 8, 0, 8, 4, 0, 4, 0, 6, 0, 6},
                    {7, 7, 0, 0, 0, 0, 0, 0, 7, 8, 0, 8, 0, 8, 0, 8, 8, 6, 0, 0, 0, 0, 0, 6},
                    {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 6, 0, 0, 0, 0, 0, 4},
                    {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 6, 0, 6, 0, 6, 0, 6},
                    {7, 7, 0, 0, 0, 0, 0, 0, 7, 8, 0, 8, 0, 8, 0, 8, 8, 6, 4, 6, 0, 6, 6, 6},
                    {7, 7, 7, 7, 0, 7, 7, 7, 7, 8, 8, 4, 0, 6, 8, 4, 8, 3, 3, 3, 0, 3, 3, 3},
                    {2, 2, 2, 2, 0, 2, 2, 2, 2, 4, 6, 4, 0, 0, 6, 0, 6, 3, 0, 0, 0, 0, 0, 3},
                    {2, 2, 0, 0, 0, 0, 0, 2, 2, 4, 0, 0, 0, 0, 0, 0, 4, 3, 0, 0, 0, 0, 0, 3},
                    {2, 0, 0, 0, 0, 0, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 4, 3, 0, 0, 0, 0, 0, 3},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 4, 4, 4, 4, 4, 6, 0, 6, 3, 3, 0, 0, 0, 3, 3},
                    {2, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 1, 2, 2, 2, 6, 6, 0, 0, 5, 0, 5, 0, 5},
                    {2, 2, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 2, 0, 5, 0, 5, 0, 0, 0, 5, 5},
                    {2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 5, 0, 5, 0, 5, 0, 5, 0, 5},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
                    {2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 5, 0, 5, 0, 5, 0, 5, 0, 5},
                    {2, 2, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 2, 0, 5, 0, 5, 0, 0, 0, 5, 5},
                    {2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 5, 5, 5, 5, 5, 5, 5, 5, 5}
            };

    private double posX = 22.0, posY = 11.5;  //x and y start position
    private double dirX = -1.0, dirY = 0.0; //initial direction vector
    private double planeX = 0.0, planeY = 0.66; //the 2d raycaster version of camera plane
    private String keyPressed;
    public boolean floorCasting = true;

    public Engine() {
        textures = Texture.initTextures();
        buffer = BufferSingleton.getInstance();
    }

    public void calculate() {

        int width = Configuration.WIDTH;
        int height = Configuration.HEIGHT;

        if (floorCasting == true)
            //FLOOR CASTING
            for (int y = height / 2 + 1; y < height; ++y) {
                // rayDir for leftmost ray (x = 0) and rightmost ray (x = w)
                float rayDirX0 = (float) (dirX - planeX);
                float rayDirY0 = (float) (dirY - planeY);
                float rayDirX1 = (float) (dirX + planeX);
                float rayDirY1 = (float) (dirY + planeY);

                // Current y position compared to the center of the screen (the horizon)
                int p = y - height / 2;

                // Vertical position of the camera.
                // NOTE: with 0.5, it's exactly in the center between floor and ceiling,
                // matching also how the walls are being raycasted. For different values
                // than 0.5, a separate loop must be done for ceiling and floor since
                // they're no longer symmetrical.
                float posZ = (float) 0.5 * height;

                // Horizontal distance from the camera to the floor for the current row.
                // 0.5 is the z position exactly in the middle between floor and ceiling.
                // NOTE: this is affine texture mapping, which is not perspective correct
                // except for perfectly horizontal and vertical surfaces like the floor.
                // NOTE: this formula is explained as follows: The camera ray goes through
                // the following two points: the camera itself, which is at a certain
                // height (posZ), and a point in front of the camera (through an imagined
                // vertical plane containing the screen pixels) with horizontal distance
                // 1 from the camera, and vertical position p lower than posZ (posZ - p). When going
                // through that point, the line has vertically traveled by p units and
                // horizontally by 1 unit. To hit the floor, it instead needs to travel by
                // posZ units. It will travel the same ratio horizontally. The ratio was
                // 1 / p for going through the camera plane, so to go posZ times farther
                // to reach the floor, we get that the total horizontal distance is posZ / p.
                float rowDistance = posZ / p;

                // calculate the real world step vector we have to add for each x (parallel to camera plane)
                // adding step by step avoids multiplications with a weight in the inner loop
                float floorStepX = rowDistance * (rayDirX1 - rayDirX0) / width;
                float floorStepY = rowDistance * (rayDirY1 - rayDirY0) / width;

                // real world coordinates of the leftmost column. This will be updated as we step to the right.
                float floorX = (float) posX + rowDistance * rayDirX0;
                float floorY = (float) posY + rowDistance * rayDirY0;

                for (int x = 0; x < width; ++x) {
                    // the cell coord is simply got from the integer parts of floorX and floorY
                    int cellX = (int) (floorX);
                    int cellY = (int) (floorY);

                    // get the texture coordinate from the fractional part
                    int tx = (int) (512 * (floorX - cellX)) & (512 - 1);
                    int ty = (int) (512 * (floorY - cellY)) & (512 - 1);

                    floorX += floorStepX;
                    floorY += floorStepY;

                    // choose texture and draw the pixel
                    int checkerBoardPattern = ((cellX + cellY)) & 1;
                    int floorTexture;
                    if (checkerBoardPattern == 0) floorTexture = 3;
                    else floorTexture = 4;
                    int ceilingTexture = 6;

                    // floor
                    int color = textures.get(1)[ty][tx];
                    color = (color >> 1) & 8355711; // make a bit darker
                    buffer.matrix[x][y] = color;

                    //ceiling (symmetrical, at screenHeight - y - 1 instead of y)
                    color = textures.get(2)[ty][tx];
                    color = (color >> 1) & 8355711; // make a bit darker
                    buffer.matrix[x][height - y - 1] = color;
                }
            }
//#endif // FLOOR_HORIZONTAL


        for (int x = 0; x < width; x++) {
            //calculate ray position and direction
            double cameraX = 2 * x / (double) width - 1; //x-coordinate in camera space
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;

            //which box of the map we're in
            int mapX = (int) posX;
            int mapY = (int) posY;

            //length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;

            //length of ray from one x or y-side to next x or y-side
            double deltaDistX = (rayDirX == 0) ? 1e30 : Math.abs(1 / rayDirX);
            double deltaDistY = (rayDirY == 0) ? 1e30 : Math.abs(1 / rayDirY);
            double perpWallDist;

            //what direction to step in x or y-direction (either +1 or -1)
            int stepX;
            int stepY;

            int hit = 0; //was there a wall hit?
            int side = 0; //was a NS or a EW wall hit?

            //calculate step and initial sideDist
            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (posX - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - posX) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (posY - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - posY) * deltaDistY;
            }
            //perform DDA
            while (hit == 0) {
                //jump to next map square, either in x-direction, or in y-direction
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                //Check if ray has hit a wall
                if (worldMap[mapX][mapY] > 0) hit = 1;
            }

            //Calculate distance of perpendicular ray (Euclidean distance would give fisheye effect!)
            if (side == 0) perpWallDist = (sideDistX - deltaDistX);
            else perpWallDist = (sideDistY - deltaDistY);

            //Calculate height of line to draw on screen
            int lineHeight = (int) (height / perpWallDist);


            int pitch = 0;

            //calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight / 2 + height / 2 + pitch;
            if (drawStart < 0) drawStart = 0;
            int drawEnd = lineHeight / 2 + height / 2 + pitch;
            if (drawEnd >= height) drawEnd = height - 1;


//
//            //texturing calculations
//            int texNum = worldMap[mapX][mapY] - 1; //1 subtracted from it so that texture 0 can be used!
//
            //calculate value of wallX
            double wallX; //where exactly the wall was hit
            if (side == 0) wallX = posY + perpWallDist * rayDirY;
            else wallX = posX + perpWallDist * rayDirX;
            // wallX -= floor((wallX));
            int texWidth = 512;
            int texHeight = 512;
            //x coordinate on the texture
            int texX = ((int) (wallX * (double) (texWidth))) % 512;
            if (side == 0 && rayDirX > 0) texX = texWidth - texX - 1;
            if (side == 1 && rayDirY < 0) texX = texWidth - texX - 1;
//
//            // TODO: an integer-only bresenham or DDA like algorithm could make the texture coordinate stepping faster
//            // How much to increase the texture coordinate per screen pixel
            double step = 1.0 * texHeight / lineHeight;
//            // Starting texture coordinate
            double texPos = (drawStart - pitch - height / 2 + lineHeight / 2) * step;
//
            for (int y = drawStart; y < drawEnd; y++) {

                int texY = ((int) texPos & (texHeight - 1)) % 512;
                texPos += step;
//                var b = (texHeight * texY + texX);
//                var tex = textures.get(0);
//                System.out.println(b < 0 ? -1*b : b);
//                System.out.println(tex.length);
                try {

                    buffer.matrix[x][y] = textures.get(0)[texX][texY];
                    //make color darker for y-sides: R, G and B byte each divided through two with a "shift" and an "and"
//                if(side == 1) color = (()(color >> 1)) & 8355711;
                } catch (Exception e) {
                    buffer.matrix[x][y] = 40;
                }
            }
//            for(int y = drawEnd; y < height; y++){
//                buffer[x][y] = 0;
//            }
        }
    }

    public void setKeyPressed(String key) {
        this.keyPressed = key;
    }

    public void updatePosition() {
        double moveSpeed = 0.1;// frameTime * 5.0; //the constant value is in squares/second
        double rotSpeed = 0.1;//frameTime * 3.0; //the constant value is in radians/second
        //    if(this.keyPressed.equals("UP")){
        //         System.out.println(this.keyPressed);
        //    }
//        readKeys();
        //move forward if no wall in front of you
        if (this.keyPressed != null) {
            if (this.keyPressed.equals("UP")) {
                if (worldMap[(int) (posX + dirX * moveSpeed)][(int) (posY)] == 0) posX += dirX * moveSpeed;
                if (worldMap[(int) (posX)][(int) (posY + dirY * moveSpeed)] == 0) posY += dirY * moveSpeed;
            }
            //move backwards if no wall behind you
            if (this.keyPressed.equals("DOWN")) {
                if (worldMap[(int) (posX - dirX * moveSpeed)][(int) (posY)] == 0) posX -= dirX * moveSpeed;
                if (worldMap[(int) (posX)][(int) (posY - dirY * moveSpeed)] == 0) posY -= dirY * moveSpeed;
            }
            if (this.keyPressed.equals("RIGHT")) {
                //both camera direction and camera plane must be rotated
                double oldDirX = dirX;
                dirX = dirX * Math.cos(-rotSpeed) - dirY * Math.sin(-rotSpeed);
                dirY = oldDirX * Math.sin(-rotSpeed) + dirY * Math.cos(-rotSpeed);
                double oldPlaneX = planeX;
                planeX = planeX * Math.cos(-rotSpeed) - planeY * Math.sin(-rotSpeed);
                planeY = oldPlaneX * Math.sin(-rotSpeed) + planeY * Math.cos(-rotSpeed);
            }
            //rotate to the left
            if (this.keyPressed.equals("LEFT")) {
                //both camera direction and camera plane must be rotated
                double oldDirX = dirX;
                dirX = dirX * Math.cos(rotSpeed) - dirY * Math.sin(rotSpeed);
                dirY = oldDirX * Math.sin(rotSpeed) + dirY * Math.cos(rotSpeed);
                double oldPlaneX = planeX;
                planeX = planeX * Math.cos(rotSpeed) - planeY * Math.sin(rotSpeed);
                planeY = oldPlaneX * Math.sin(rotSpeed) + planeY * Math.cos(rotSpeed);
            }
        }
    }
}