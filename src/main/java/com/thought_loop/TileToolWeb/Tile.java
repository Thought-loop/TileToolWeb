package com.thought_loop.TileToolWeb;

public class Tile extends AreaObject {

//    private double thickness;
//    private String material;
//    private String color;
    private double costPerSqft = 0.0;

    public Tile() {
        super();
    }

    public Tile(double height, double width, String unit, String name, double costPerSqft) {
        super(height, width, unit, name);
        this.costPerSqft = costPerSqft;
    }

    public Tile(double height, double width) {
        super(height, width);
    }

    public Tile(double height, double width, double costPerSqft) {
        super(height, width);
        this.costPerSqft = costPerSqft;
    }

    public void setCostPerSqft(double costPerSqft) {
        this.costPerSqft = costPerSqft;
    }

    public double getCostPerSqft() {
        return costPerSqft;
    }

    public double getCostPerTile() {
        return super.getSqft() * costPerSqft;
    }

    public String getDataFormatted(){
        return getHeight()+","+getWidth()+","+getUnit()+","+getName()+","+getCostPerSqft();
    }
}
