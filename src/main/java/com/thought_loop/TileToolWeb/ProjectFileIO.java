package com.thought_loop.TileToolWeb;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectFileIO {

    public boolean writeToFile(String fileName, String data) {

        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, true));
            myWriter.write(data);
            myWriter.newLine();
            myWriter.flush();
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred. Please try a different filename");
            e.printStackTrace();
            return false;
        }
    }

    public String projectEncoding(Project projectToEncode){

        String output = "TILETOOLSAVE,";
        output += projectToEncode.getDataFormatted()+","+projectToEncode.getLayout().getDataFormatted()+","+
                projectToEncode.getRoom().getDataFormatted()+","+projectToEncode.getTile().getDataFormatted();

        return output;


    }

    public List<Project> projectsDecode(String dataFileName){
        List<Project> decodedProjects = new ArrayList<>();
        File dataFile = new File(dataFileName);
        try (Scanner dataInput = new Scanner(dataFile)) {
            while (dataInput.hasNextLine()) {
                String projectData = dataInput.nextLine();
                if(projectData.startsWith("TILETOOLSAVE")) {
                    String[] projectDataArray = projectData.split(",");
                    Room room = new Room(Double.parseDouble(projectDataArray[6]), Double.parseDouble(projectDataArray[7]),
                            projectDataArray[8], projectDataArray[9]);
                    Tile tile = new Tile(Double.parseDouble(projectDataArray[10]), Double.parseDouble(projectDataArray[11]),
                            projectDataArray[12], projectDataArray[13], Double.parseDouble(projectDataArray[14]));
                    Layout layout = new Layout(room, tile,
                            Double.parseDouble(projectDataArray[3]), Double.parseDouble(projectDataArray[4]),
                            Integer.parseInt(projectDataArray[5]));
                    Project project = new Project(projectDataArray[2], tile, room, layout);
                    decodedProjects.add(project);
                }
            }
        }catch (FileNotFoundException e) {
            System.err.println("The file does not exist.");
        }
        return decodedProjects;
    }

}





