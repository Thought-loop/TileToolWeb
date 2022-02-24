package com.thought_loop.TileToolWeb;

public class Layout {

    private Room room;
    private Tile tile;
    private double tileSpacingInches;
    private double perimeterExpansionGapInches;
    private int percentOverage;

    public Layout() {
        this.room = new Room();
        this.tile = new Tile();
        this.tileSpacingInches = 0;
        this.perimeterExpansionGapInches = 0;
        this.percentOverage = -1;
    }

    public Layout(Room room, Tile tile, double tileSpacingInches, double perimeterExpansionGapInches, int percentOverage) {
        this.room = room;
        this.tile = tile;
        this.tileSpacingInches = tileSpacingInches;
        this.perimeterExpansionGapInches = perimeterExpansionGapInches;
        this.percentOverage = percentOverage;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public double getTileSpacingInches() {
        return tileSpacingInches;
    }

    public void setTileSpacingInches(double tileSpacingInches) {
        this.tileSpacingInches = tileSpacingInches;
    }

    public double getPerimeterExpansionGapInches() {
        return perimeterExpansionGapInches;
    }

    public void setPerimeterExpansionGapInches(double perimeterExpansionGapInches) {
        this.perimeterExpansionGapInches = perimeterExpansionGapInches;
    }

    public int getPercentOverage() {
        return percentOverage;
    }

    public void setPercentOverage(int percentOverage) {
        this.percentOverage = percentOverage;
    }



    public void setParameters(double tileSpacingInches, double perimeterExpansionGapInches, int percentOverage){
        this.tileSpacingInches = tileSpacingInches;
        this.perimeterExpansionGapInches = perimeterExpansionGapInches;
        this.percentOverage = percentOverage;
    }

    //calculate usable area - total room dimension less expansion gap on both ends.
    public double getUsableWidthInches() {
        return room.getWidthInches() - (2* perimeterExpansionGapInches);
    }

    public double getUsableHeightInches() {
        return room.getHeightInches() - (2* perimeterExpansionGapInches);
    }




    //returns number of whole tiles that will fit on width axis given including spacing between tiles
    public int getWholeTilesWide(){
        int numTilesWide;
        //calculate max number of tiles fit width-wise with no spacing between tiles.
        numTilesWide = (int) (getUsableWidthInches()/tile.getWidthInches());

        //check if max number of tiles completely cover usable area. If true, adjust for one less tile to accommodate spacing.
        if(getUsableWidthInches()%tile.getWidthInches()==0){
            numTilesWide--;
        }
        //check if current number of tiles plus spacing is too wide, decrement number of tiles if true.
        else if((numTilesWide*tile.getWidthInches())+((numTilesWide-1)*tileSpacingInches)>getUsableWidthInches()){
            numTilesWide--;
        }
        return numTilesWide;
    }


    //returns number of whole tiles that will fit on height axis including spacing between tiles
    public int getWholeTilesTall(){
        int numTilesTall;
        //calculate max number of tiles fit height-wise with no spacing between tiles.
        numTilesTall = (int) (getUsableHeightInches()/tile.getHeightInches());

        //check if max number of tiles completely cover usable area. If true, adjust for one less tile to accommodate spacing.
        if(getUsableHeightInches()%tile.getHeightInches()==0){
            numTilesTall--;
        }
        //check if current number of tiles plus spacing is too tall, decrement number of tiles if true.
        else if((numTilesTall*tile.getHeightInches())+((numTilesTall-1)*tileSpacingInches)>getUsableHeightInches()){
            numTilesTall--;
        }
        return numTilesTall;
    }


    //returns dimension of remaining (less-than-full) tile on height (y) axis
    public double getLeftoverTileDimensionHeight(){
      return getUsableHeightInches() - ((getWholeTilesTall()*tile.getHeightInches())+((getWholeTilesTall()-1)*tileSpacingInches));
    }

    //returns dimension of remaining (less-than-full) tile on width (x) axis
    public double getLeftoverTileDimensionsWidth(){
        return getUsableWidthInches() - ((getWholeTilesWide()*tile.getWidthInches())+((getWholeTilesWide()-1)*tileSpacingInches));
    }

    //returns needed square footage of tile including a (whole number) percentage of overage
    //result is rounded up to the nearest whole number
    //does not account for spacing between tiles
    public int getSqftTileNeeded(){

        return (int)Math.ceil(room.getSqft()*(1+(percentOverage/100.0)));

    }

    //returns needed quantity of tile including a (whole number) percentage of overage.
    //result is rounded up to the nearest whole number.

    public int getNumTilesNeeded(){
        return (int)(Math.ceil(getSqftTileNeeded())/tile.getSqft());
    }

    public String getTotalTileCost(){

        return "$" + getNumTilesNeeded()*tile.getCostPerTile();

    }

    @Override
    public String toString() {
        return "Tile spacing: (" + Spacers.stdInchSpacers(tileSpacingInches) +
                ")    Wall expansion gap: (" + Spacers.stdInchSpacers(perimeterExpansionGapInches) + ")";
    }

    public String getDataFormatted(){
        return getTileSpacingInches() + "," + getPerimeterExpansionGapInches() + "," + getPercentOverage();
    }


    public void printLayoutToConsole(){
        System.out.println("*************    Layout Calculation     **************");
        if(getNumTilesNeeded() == 0){
            System.out.println();
            System.out.println("The tile is too big, or the room is too small. Please adjust your room/tile dimensions");
            System.out.println();
        }
        else {
            System.out.println();
            System.out.println("The calculated layout uses a tile: " + tile);
            System.out.println("The calculated layout uses a room: " + room);
            System.out.println("There is a spacing of " + Spacers.stdInchSpacers(tileSpacingInches) + " between tiles.");
            System.out.println("There is an expansion spacing of " + Spacers.stdInchSpacers(perimeterExpansionGapInches) + " around the perimeter of the room.");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Along the height (y) axis - " + getWholeTilesTall() + " full tiles and a perimeter cut tile " + getLeftoverTileDimensionHeight() + "in. tall");
            System.out.println("Along the width (x) axis - " + getWholeTilesWide() + " full tiles and a perimeter cut tile " + getLeftoverTileDimensionsWidth() + "in. wide");
            System.out.println("Corner tiles will have dimensions height (y): " + getLeftoverTileDimensionHeight() + " by width (x): " + getLeftoverTileDimensionsWidth());
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("This layout was calculated with a " + percentOverage + "% overage.");
            System.out.println("This layout requires a total of " + getSqftTileNeeded() + "sqft of tile.");
            System.out.println("This layout requires " + getNumTilesNeeded() + " pieces of tile.");
            System.out.println("At a cost of $" + tile.getCostPerSqft() + " per sqft, ($" + tile.getCostPerTile() + "per tile) the tiles will cost " + getTotalTileCost());
            System.out.println();
        }



    }


}
