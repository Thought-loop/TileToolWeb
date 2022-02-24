package com.thought_loop.TileToolWeb;

public abstract class AreaObject {

    private double height;
    private double width;
    private String unit;
    private String name;

    public AreaObject() {
        height = 0;
        width = 0;
        unit = "in";
        name = "";
    }

    /**
     * @param height y-axis measurement
     * @param width x-axis measurement
     * @param unit unit of measurement (in) or (cm)
     * @param name Nickname
     */



    public AreaObject(double height, double width, String unit, String name) {
        this.height = height;
        this.width = width;
        this.unit = unit;
        this.name = name;
    }

    /**
     * @param height y-axis measurement in inches
     * @param width x-axis measurement in inches
     */

    public AreaObject(double height, double width) {
        this.height = height;
        this.width = width;
        unit = "in";
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    public void setDimensions(double height, double width, String unit) {
        this.height = height;
        this.width = width;
        this.unit = unit;
    }

    public void setDimensions(double height, double width) {
        this.height = height;
        this.width = width;
    }


    public double getHeightInches() {
        if (unit.equalsIgnoreCase("in")) {
            return height;
        } else if (unit.equalsIgnoreCase("cm")) {
            return (height / 2.54);
        }
        return 0.0;
    }

    public double getHeightCentimeters() {
        if (unit.equalsIgnoreCase("cm")) {
            return height;
        } else if (unit.equalsIgnoreCase("in")) {
            return (height * 2.54);
        }
        return 0.0;
    }

    public double getWidthInches() {
        if (unit.equalsIgnoreCase("in")) {
            return width;
        } else if (unit.equalsIgnoreCase("cm")) {
            return (width / 2.54);
        }
        return 0.0;
    }

    public double getWidthCentimeters() {
        if (unit.equalsIgnoreCase("cm")) {
            return width;
        } else if (unit.equalsIgnoreCase("in")) {
            return (width * 2.54);
        }
        return 0.0;
    }

    public double getSqin() {
        if (unit.equalsIgnoreCase("in")) {
            return width * height;
        } else if (unit.equalsIgnoreCase("cm")) {
            return getWidthInches() * getHeightInches();
        }
        return 0.0;
    }

    public double getSqcm() {
        if (unit.equalsIgnoreCase("cm")) {
            return width * height;
        } else if (unit.equalsIgnoreCase("in")) {
            return getWidthCentimeters() * getHeightCentimeters();
        }
        return 0.0;
    }

    public double getSqft() {
        if (unit.equalsIgnoreCase("in")) {
            return (width * height) / 144;
        } else if (unit.equalsIgnoreCase("cm")) {
            return (getWidthInches() * getHeightInches()) / 144;
        }
        return 0.0;
    }

    public void rotate() {
        double currentHeight = height;
        height = width;
        width = currentHeight;

    }

    public String getDataFormatted(){
        return getHeight()+","+getWidth()+","+getUnit()+","+getName();
    }


    @Override
    public String toString() {
        return getHeightInches() + " in. by " + getWidthInches() + " in.";
    }
}

