package com.thought_loop.TileToolWeb;

public class WebLayout extends Layout{
    String roomX;
    String roomY;
    String tileX;
    String tileY;
    String spacing;
    String exGap;
    String name;
    String patternType;


    //specialized constructor for spring post request
    //sets all parameters via parent methods
    public WebLayout(String roomX, String roomY, String tileX, String tileY, String spacing, String exGap, String name, String patternType) {
        this.roomX = roomX;
        this.roomY = roomY;
        this.tileX = tileX;
        this.tileY = tileY;
        this.spacing = spacing;
        this.exGap = exGap;
        this.patternType = patternType;

        Room room;
        Tile tile;
        if(roomX.isEmpty() || roomY.isEmpty() || tileX.isEmpty() || tileY.isEmpty()){
            room = new Room(48,48);
            tile = new Tile(48,48);
            setParameters(0,0,0);
            this.name = "You left something blank, please try again";
        }
        else {
            room = new Room(Double.parseDouble(roomY), Double.parseDouble(roomX));
            tile = new Tile(Double.parseDouble(tileY), Double.parseDouble(tileX));
            this.name = name;
        }
        setParameters(Double.parseDouble(spacing), Double.parseDouble(exGap),15);
        setRoom(room);
        setTile(tile);
    }

    public String getPatternType() {
        return patternType;
    }

    public void setPatternType(String patternType) {
        this.patternType = patternType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomX() {
        return roomX;
    }

    public void setRoomX(String roomX) {
        this.roomX = roomX;
    }

    public String getRoomY() {
        return roomY;
    }

    public void setRoomY(String roomY) {
        this.roomY = roomY;
    }

    public String getTileX() {
        return tileX;
    }

    public void setTileX(String tileX) {
        this.tileX = tileX;
    }

    public String getTileY() {
        return tileY;
    }

    public void setTileY(String tileY) {
        this.tileY = tileY;
    }

    public String getSpacing() {
        return spacing;
    }

    public void setSpacing(String spacing) {
        this.spacing = spacing;
    }

    public String getExGap() {
        return exGap;
    }

    public void setExGap(String exGap) {
        this.exGap = exGap;
    }

//    @Override
//    public String toString() {
//        return "WebLayout{" +
//                "roomX='" + roomX + '\'' +
//                ", roomY='" + roomY + '\'' +
//                ", tileX='" + tileX + '\'' +
//                ", tileY='" + tileY + '\'' +
//                ", spacing='" + spacing + '\'' +
//                ", exGap='" + exGap + '\'' +
//                '}';
//    }
}
