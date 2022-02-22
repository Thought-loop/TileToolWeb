package com.thought_loop.TileToolWeb;

public class Project {

    private String projectName;
    private Tile tile;
    private Room room;
    private Layout layout;
    private static int projectIDCounter;
    private int projectID;


    //private List<Room> rooms = new ArrayList<>();
    //private List<Tile> tiles = new ArrayList<>();

    public Project(String projectName){
        this.projectName = projectName;
        projectID = projectIDCounter;
        projectIDCounter++;

    }

    public Project(String projectName, Tile tile, Room room, Layout layout) {
        this.projectName = projectName;
        this.tile = tile;
        this.room = room;
        this.layout = layout;
        projectID = projectIDCounter;
        projectIDCounter++;
    }

    public int getProjectID() {
        return projectID;
    }

    public static int getProjectIDCounter() {
        return projectIDCounter;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Layout getLayout() {
        return layout;
    }

    public String getProjectName() {
        return projectName;
    }

    public Tile getTile() {
        return tile;
    }

    public Room getRoom() {
        return room;
    }


    //    public void addTile(Tile tile){
//        tiles.add(tile);
//    }
//
//    public void addRoom(Room room){
//        rooms.add(room);
//    }


    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", tile=" + tile +
                ", room=" + room +
                ", layout=" + layout +
                '}';
    }

    public String getDataFormatted(){
        return getProjectID() + "," + getProjectName();
    }

}
