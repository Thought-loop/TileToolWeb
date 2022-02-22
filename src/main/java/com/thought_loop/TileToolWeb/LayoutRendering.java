package com.thought_loop.TileToolWeb;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LayoutRendering extends JPanel {

    private Layout layout;
    private Room room;
    private Tile tile;
    private int pixelsPerInch;
    private int marginPixels;
    private int marginIN;
    private double spacing;
    private String projectName;


    //array of corner max coordinates {x-min, x-max, y-min, y-max} in inches
    private int[] roomCorners;

    public LayoutRendering(Layout layout, int pixelsPerInch, int marginIN, String projectName){
        this.layout = layout;

        this.room = layout.getRoom();
        this.tile = layout.getTile();
        this.marginIN = marginIN;
        this.pixelsPerInch = pixelsPerInch;
        this.marginPixels = marginIN*pixelsPerInch;
        this.spacing = layout.getTileSpacingInches()*pixelsPerInch;
        this.projectName = projectName;
        roomCorners = new int[]
                {(int)((room.getWidthInches()*pixelsPerInch)+.5)
                ,(int)((room.getHeightInches()*pixelsPerInch)+.5)};

    }

    public int[] getWindowSize() {
        //returns window size in pixels {width,height}
        return new int[] {(int)(roomCorners[0]),(int)(roomCorners[1])};
    }

    public int[] getRoomCorners() {
        //System.out.println("room corner dims in pixels" + roomCorners[0] + "," + roomCorners[1]);
        return roomCorners;
    }

    //generate list of tiles, column by column
    //1st tile

    public List<int[]> generateCoordinatesSquareLayoutSymmetrical(int printStartX, int printStartY){
        List<int[]> tiles= new ArrayList<>();
        int xOrigin = ((int)((layout.getPerimeterExpansionGapInches()*pixelsPerInch)))+printStartX;
        int yOrigin = (int)((layout.getPerimeterExpansionGapInches()*pixelsPerInch))+printStartY;
        int xCurrent;
        int yCurrent;
        int xDelta;
        int yDelta;
        //create a list of int arrays, structured for Graphics2D.drawRect.
        //{origin x, origin y, delta x, delta y}
        //first column all have same x origin. 1/2 of leftover tile width
        //first row all have same y origin. 1/2 of leftover tile height
        //num columns (j) is getWholeTilesWide + 2
        //num rows (i) is getWholeTilesTall + 2

        //i represents row
        int tilesAdded=0;
        xCurrent = 0;
        xDelta = 0;
        yCurrent = 0;
        yDelta = 0;
        int xDeltaFirstLast = (int) ((layout.getLeftoverTileDimensionsWidth() * pixelsPerInch)/2);
        int yDeltaFirstLast = (int)((layout.getLeftoverTileDimensionHeight()*pixelsPerInch)/2);
        for (int row = 0; row < layout.getWholeTilesTall() + 2; row++) {

            for (int column = 0; column < layout.getWholeTilesWide() + 2; column++) {

                if (column == 0) {
                    xCurrent = xOrigin;
                    //delta is half of leftover tile * pixelsPerInch
                    xDelta = xDeltaFirstLast;
                } else if (column == layout.getWholeTilesWide()+1) {
                    //setup last column
                    xCurrent = (int) (tiles.get(tilesAdded - 1)[0] + tiles.get(tilesAdded - 1)[2] + spacing);
                    xDelta = xDeltaFirstLast;
                } else if (column>0){
                    //every column xCurrent is last tile xcurrent + last tile delta + spacing.
                    xCurrent = (int) (tiles.get(tilesAdded - 1)[0] + tiles.get(tilesAdded - 1)[2] + spacing);
                    xDelta = (int) (tile.getWidthInches() * pixelsPerInch);
                }


            //j represent column

                if (row == 0) {
                    yCurrent = yOrigin;
                    //delta is half of leftover tile * pixelsPerInch
                    yDelta = yDeltaFirstLast;
                }
                else if(row == layout.getWholeTilesTall()+1){
                    if(column==0){
                        yCurrent =  (int) (yOrigin + (yDeltaFirstLast+spacing) + ((row-1) * (tiles.get(tilesAdded - 1)[3] + spacing)));
                    }
                    yDelta = yDeltaFirstLast;
                    //setup last row width
                } else if (row > 0) {
                    //setup yCurrent once per row - height of first row + spacing + height of previously rendered full tiles w/ spacing
                    if(column==0) {
                        yCurrent = (int) (yOrigin + (yDeltaFirstLast+spacing) + ((row-1) * (tiles.get(tilesAdded - 1)[3] + spacing)));
                    }
                    yDelta = (int) (tile.getHeightInches() * pixelsPerInch);
                }
                //System.out.println("i:" + i + " j:" + j + " xCur:" + xCurrent + " yCur:" + yCurrent);
                tiles.add(tilesAdded ,new int[]{xCurrent,yCurrent,xDelta,yDelta});
                tilesAdded++;
                roomCorners[0] = (int)(xCurrent+xDelta);
                roomCorners[1] = (int)(yCurrent+yDelta);
            }
        }
//        System.out.println(layout.getWholeTilesTall() + " by " + layout.getWholeTilesWide());
        return tiles;
    }




    public List<int[]> generateCoordinatesSquareLayoutNotSymmetrical(int printStartX, int printStartY){
        List<int[]> coordinatesForAllTilesToDraw= new ArrayList<>();
        //List of int[]{xCurrent,yCurrent,xDelta,yDelta}

        int xOrigin = (int)((layout.getPerimeterExpansionGapInches()*pixelsPerInch))+printStartX;;
        int yOrigin = (int)((layout.getPerimeterExpansionGapInches()*pixelsPerInch))+printStartY;;
        int xCurrent;
        int yCurrent;
        int xDelta;
        int yDelta;
        //create a list of int arrays, structured for Graphics2D.drawRect.
        //{origin x, origin y, delta x, delta y}
        //first column all have same x origin. 1/2 of leftover tile width
        //first row all have same y origin. 1/2 of leftover tile height
        //num columns (j) is getWholeTilesWide + 2
        //num rows (i) is getWholeTilesTall + 2

        //i represents row
        int numTilesAddedToCoordinatesList=0;
        xCurrent = 0;
        xDelta = 0;
        yCurrent = 0;
        yDelta = 0;
        int xDeltaFirst = (int)((layout.getLeftoverTileDimensionsWidth() * pixelsPerInch));
        int yDeltaFirst = (int)((layout.getLeftoverTileDimensionHeight()*pixelsPerInch));
        for (int row = 0; row < layout.getWholeTilesTall() + 2; row++) {

            for (int column = 0; column < layout.getWholeTilesWide() + 2; column++) {

                if (column == 0) {
                    xCurrent = xOrigin;
                    //delta is half of leftover tile * pixelsPerInch
                    xDelta = xDeltaFirst;
                } else if (column>0){
                    //every column xCurrent is last tile xcurrent + last tile delta + spacing.
                    xCurrent = (int) (coordinatesForAllTilesToDraw.get(numTilesAddedToCoordinatesList - 1)[0] + coordinatesForAllTilesToDraw.get(numTilesAddedToCoordinatesList - 1)[2] + spacing);
                    xDelta = (int) (tile.getWidthInches() * pixelsPerInch);
                }


                //j represent column

                if (row == 0) {
                    yCurrent = yOrigin;
                    //delta is half of leftover tile * pixelsPerInch
                    yDelta = yDeltaFirst;
                }

                 else if (row > 0) {
                    //setup yCurrent once per row - height of first row + spacing + height of previously rendered full tiles w/ spacing
                    if(column==0) {
                        yCurrent = (int) (yOrigin + (yDeltaFirst+spacing) + ((row-1) * (coordinatesForAllTilesToDraw.get(numTilesAddedToCoordinatesList - 1)[3] + spacing)));
                    }
                    yDelta = (int) (tile.getHeightInches() * pixelsPerInch);
                }
                //System.out.println("i:" + i + " j:" + j + " xCur:" + xCurrent + " yCur:" + yCurrent);
                coordinatesForAllTilesToDraw.add(numTilesAddedToCoordinatesList ,new int[]{xCurrent,yCurrent,xDelta,yDelta});
                numTilesAddedToCoordinatesList++;
                roomCorners[0] = (int)(xCurrent+xDelta);
                roomCorners[1] = (int)(yCurrent+yDelta);
            }
        }
//        System.out.println(layout.getWholeTilesTall() + " by " + layout.getWholeTilesWide());
        return coordinatesForAllTilesToDraw;
    }

    public List<int[]> generateCoordinatesSubwayLayoutSymmetrical(int printStartX, int printStartY){
        List<int[]> tiles= new ArrayList<>();
        int xOrigin = ((int)((layout.getPerimeterExpansionGapInches()*pixelsPerInch)))+printStartX;
        int yOrigin = (int)((layout.getPerimeterExpansionGapInches()*pixelsPerInch))+printStartY;
        int xCurrent;
        int yCurrent;
        int xDelta;
        int yDelta;
        //create a list of int arrays, structured for Graphics2D.drawRect.
        //{origin x, origin y, delta x, delta y}
        //first column all have same x origin. 1/2 of leftover tile width
        //first row all have same y origin. 1/2 of leftover tile height
        //num columns (j) is getWholeTilesWide + 2
        //num rows (i) is getWholeTilesTall + 2

        //i represents row
        //j represents column
        int tilesAdded=0;
        xCurrent = 0;
        xDelta = 0;
        yCurrent = 0;
        yDelta = 0;
        int xDeltaFirstLast = (int) ((layout.getLeftoverTileDimensionsWidth() * pixelsPerInch)/2);
        int yDeltaFirstLast = (int)((layout.getLeftoverTileDimensionHeight()*pixelsPerInch)/2);
        for (int row = 0; row < layout.getWholeTilesTall() + 2; row++) {

            for (int column = 0; column < layout.getWholeTilesWide() + 2; column++) {
                //build even rows here

                if(row%2==0 || row==0) {
                    if (column == 0) {
                        xCurrent = xOrigin;
                        //delta is half of leftover tile * pixelsPerInch
                        xDelta = xDeltaFirstLast;
                    } else if (column == layout.getWholeTilesWide() + 1) {
                        //setup last column
                        xCurrent = (int) (tiles.get(tilesAdded - 1)[0] + tiles.get(tilesAdded - 1)[2] + spacing);
                        xDelta = xDeltaFirstLast;
                    } else if (column > 0) {
                        //every column xCurrent is last tile xcurrent + last tile delta + spacing.
                        xCurrent = (int) (tiles.get(tilesAdded - 1)[0] + tiles.get(tilesAdded - 1)[2] + spacing);
                        xDelta = (int) (tile.getWidthInches() * pixelsPerInch);
                    }

                    if (row == 0) {
                        yCurrent = yOrigin;
                        //delta is half of leftover tile * pixelsPerInch
                        yDelta = yDeltaFirstLast;
                    } else if (row == layout.getWholeTilesTall() + 1) {
                        if (column == 0) {
                            yCurrent = (int) (yOrigin + (yDeltaFirstLast + spacing) + ((row - 1) * (tiles.get(tilesAdded - 1)[3] + spacing)));
                        }
                        yDelta = yDeltaFirstLast;
                        //setup last row width
                    } else if (row > 0) {
                        //setup yCurrent once per row - height of first row + spacing + height of previously rendered full tiles w/ spacing
                        if (column == 0) {
                            yCurrent = (int) (yOrigin + (yDeltaFirstLast + spacing) + ((row - 1) * (tiles.get(tilesAdded - 1)[3] + spacing)));
                        }
                        yDelta = (int) (tile.getHeightInches() * pixelsPerInch);
                    }
                }
                //build odd rows here
                //origin tile width is less than 1/2 a normal tile
                //2nd row first tile delta is leftover of origin tile width from whole tile plus one spacing
                //otherwise 2nd row first tile starts at x-origin and extends to middle of second tile
                //
                else{
                    if (column == 0) {
                        xCurrent = xOrigin;
                        System.out.println("here");
                        System.out.println(layout.getLeftoverTileDimensionsWidth());
                        System.out.println(layout.getTile().getWidthInches()/2);
                        if(layout.getLeftoverTileDimensionsWidth()/2 <= (layout.getTile().getWidthInches()/2)){
                            xDelta = (int)((tile.getWidthInches() * pixelsPerInch) - (xDeltaFirstLast/2) + spacing);
                        }
                        else{//delta is half of leftover tile * pixelsPerInch
                            System.out.println("else");
                            xDelta = xDeltaFirstLast / 2;
                        }
                    } else if (column == layout.getWholeTilesWide() + 1) {
                        //setup last column
                        xCurrent = (int) (tiles.get(tilesAdded - 1)[0] + tiles.get(tilesAdded - 1)[2] + spacing);
                        xDelta = xDeltaFirstLast/2;
                    } else if (column > 0) {
                        //every column xCurrent is last tile xcurrent + last tile delta + spacing.
                        xCurrent = (int) (tiles.get(tilesAdded - 1)[0] + tiles.get(tilesAdded - 1)[2] + spacing);
                        xDelta = (int) (tile.getWidthInches() * pixelsPerInch);
                    }

                    if (row == 0) {
                        yCurrent = yOrigin;
                        //delta is half of leftover tile * pixelsPerInch
                        yDelta = yDeltaFirstLast;
                    } else if (row == layout.getWholeTilesTall() + 1) {
                        if (column == 0) {
                            yCurrent = (int) (yOrigin + (yDeltaFirstLast + spacing) + ((row - 1) * (tiles.get(tilesAdded - 1)[3] + spacing)));
                        }
                        yDelta = yDeltaFirstLast;
                        //setup last row width
                    } else if (row > 0) {
                        //setup yCurrent once per row - height of first row + spacing + height of previously rendered full tiles w/ spacing
                        if (column == 0) {
                            yCurrent = (int) (yOrigin + (yDeltaFirstLast + spacing) + ((row - 1) * (tiles.get(tilesAdded - 1)[3] + spacing)));
                        }
                        yDelta = (int) (tile.getHeightInches() * pixelsPerInch);
                    }
                }
                //System.out.println("i:" + i + " j:" + j + " xCur:" + xCurrent + " yCur:" + yCurrent);
                tiles.add(tilesAdded ,new int[]{xCurrent,yCurrent,xDelta,yDelta});
                tilesAdded++;
                roomCorners[0] = (int)(xCurrent+xDelta);
                roomCorners[1] = (int)(yCurrent+yDelta);
            }
        }
//        System.out.println(layout.getWholeTilesTall() + " by " + layout.getWholeTilesWide());
        return tiles;
    }





    public String generateJPG(int layoutType){
        //layoutType - (1 Square symmetrical, 2 Square cuts along origin axis, 3 subway symmetrical)

        //padding around layout inside of image
        int layoutMarginPX = 30;
        List<int []> coordinatesOfTilesToDraw;
        if(layoutType == 1) {
            coordinatesOfTilesToDraw = generateCoordinatesSquareLayoutSymmetrical(layoutMarginPX, layoutMarginPX);
        }
        else if(layoutType == 2){
            coordinatesOfTilesToDraw = generateCoordinatesSquareLayoutNotSymmetrical(layoutMarginPX, layoutMarginPX);
        }
        else{
            coordinatesOfTilesToDraw = generateCoordinatesSubwayLayoutSymmetrical(layoutMarginPX, layoutMarginPX);
        }
        //list of int[] in form {xCurrent,yCurrent,xDelta,yDelta}

        //calculate size/coordinates of layout extents
        int maxXLayout = coordinatesOfTilesToDraw.get(coordinatesOfTilesToDraw.size()-1)[0]+coordinatesOfTilesToDraw.get(coordinatesOfTilesToDraw.size()-1)[2] - layoutMarginPX + ((int)(layout.getPerimeterExpansionGapInches()*pixelsPerInch));
        int maxYLayout = coordinatesOfTilesToDraw.get(coordinatesOfTilesToDraw.size()-1)[1]+coordinatesOfTilesToDraw.get(coordinatesOfTilesToDraw.size()-1)[3] - layoutMarginPX + ((int)(layout.getPerimeterExpansionGapInches()*pixelsPerInch));

        //calculate coordinates of image extents (includes margin around layout and text area at bottom)
        int maxXImage = maxXLayout + layoutMarginPX*2;
        int maxYImage = (int)(maxYLayout*1.125)+ layoutMarginPX*2;

        //create buffered image of appropriate size
        BufferedImage bufferedImage = new BufferedImage(maxXImage,maxYImage, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        //draw and fill image extents white ()
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0,maxXImage, maxYImage);

        //draw fill layout extents GRAY (appears as expansion gap/grout between tiles)
        g2d.setPaint(new Color(245,245,220));
        g2d.setBackground(Color.GRAY);
        g2d.clearRect(layoutMarginPX,layoutMarginPX,maxXLayout, maxYLayout);

        //draw the list of tile coordinates
        for (int i = 0; i < coordinatesOfTilesToDraw.size(); i++) {
            int xCurrent = coordinatesOfTilesToDraw.get(i)[0];
            int yCurrent = coordinatesOfTilesToDraw.get(i)[1];
            int xDelta = coordinatesOfTilesToDraw.get(i)[2];
            int yDelta = coordinatesOfTilesToDraw.get(i)[3];
            g2d.fillRect(xCurrent,yCurrent,xDelta,yDelta);
        }

        //draw text at bottom: Project name, layout, tile, room key information.
        int textXOrigin = (int)(maxXLayout*.01);
        g2d.setPaint(Color.black);
        g2d.setFont(new Font("Arial", Font.PLAIN, maxXLayout/30));
        g2d.drawString(projectName, textXOrigin, (int)(maxYLayout + ((maxYImage-maxYLayout)*0.35)));
        g2d.setFont(new Font("Arial", Font.PLAIN, maxXLayout/50));
        g2d.drawString(layout + "", textXOrigin, (int)(maxYLayout + ((maxYImage-maxYLayout)*0.55)));
        g2d.drawString("Tile: " + layout.getTile(), textXOrigin, (int)(maxYLayout + ((maxYImage-maxYLayout)*0.75)));
        g2d.drawString("Room: " + layout.getRoom(), textXOrigin, (int)(maxYLayout + ((maxYImage-maxYLayout)*0.925)));
        //g2d.drawString(projectName, (int)(maxXLayout*.05), (int)(maxYLayout-(layout.getTile().getHeightInches()/2*pixelsPerInch)));


        LocalDateTime now = LocalDateTime.now();
        String fileName = now.toString();
        fileName = fileName.replace(":","-");
        fileName = fileName.substring(0,19);
        fileName = "GeneratedImages\\"+ fileName +".jpg";
        File file = new File(fileName);
        try {
            ImageIO.write(bufferedImage, "jpg", file);
            System.out.println("Successfully generated image file: " + fileName);
        } catch (IOException e) {
            System.out.println("There was an error generating the image file");
        }
        return fileName;
    }




    private void doDrawing(Graphics g) throws IOException {

        Graphics2D room2d = (Graphics2D) g;
        //draw room for jframe

        List<int []> tiles = generateCoordinatesSquareLayoutSymmetrical(0,0);

        //draw tile list
        for (int i = 0; i < tiles.size(); i++) {
            int xCurrent = tiles.get(i)[0];
            int yCurrent = tiles.get(i)[1];
            int xDelta = tiles.get(i)[2];
            int yDelta = tiles.get(i)[3];

            room2d.drawRect(xCurrent, yCurrent, xDelta, yDelta);
        }
        room2d.drawRect(marginPixels, marginPixels, roomCorners[0], roomCorners[1]);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        try {
            doDrawing(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
