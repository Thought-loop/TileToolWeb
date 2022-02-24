package com.thought_loop.TileToolWeb;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TextMenu {

    private static int interfaceID = 0;
    private int currentInterfaceID;
    String currentUserInput;
    Scanner scanner = new Scanner(System.in);

    List<Project> projectList = new ArrayList<>();
    Map<Integer, String> mainMenuOptions = new HashMap<>();
    List<String> sessionImages = new ArrayList<>();

    boolean validEntry = true;


    public TextMenu() {

        this.currentInterfaceID = interfaceID;
        interfaceID++;
        mainMenuOptions.put(0, "Exit program");
        mainMenuOptions.put(1, "Create new project");
        mainMenuOptions.put(9, "Load a saved list of projects from a file");
    }

    public static int getInterfaceIDCounter() {
        return interfaceID;
    }

    public int getInterfaceID() {
        return currentInterfaceID;
    }


    public void startTextMenu() {

        System.out.println();
        System.out.println();
        System.out.println("*******************************************************");
        System.out.println("******* Hello, welcome to the tile layout tool ********");
        System.out.println("*******************************************************");
        System.out.println("*************            ____             *************");
        System.out.println("*************           /    \\            *************");
        System.out.println("*************      ____/      \\____       *************");
        System.out.println("*************     /    \\      /    \\      *************");
        System.out.println("*************    /      \\____/      \\     *************");
        System.out.println("*************    \\      /    \\      /     *************");
        System.out.println("*************     \\____/      \\____/      *************");
        System.out.println("*************     /    \\      /    \\      *************");
        System.out.println("*************    /      \\____/      \\     *************");
        System.out.println("*************    \\      /    \\      /     *************");
        System.out.println("*************     \\____/      \\____/      *************");
        System.out.println("*************          \\      /           *************");
        System.out.println("*************           \\____/            *************");
        System.out.println("*******************************************************");
        System.out.println("*******************************************************");
        System.out.println("*******************************************************");


        mainMenu();

    }

    public void mainMenu() {
        //show menu options based on available actions
        //take user input and call appropriate method

        Integer currentUserInputAsInteger = 0;

        System.out.println();
        System.out.println("*******************************************************");
        System.out.println("*******              Main Menu                 ********");
        System.out.println("*******************************************************");
        if(sessionImages.size()>0){
            mainMenuOptions.put(7, "Delete all images generated in this session");
        }
        do {
            //check valid entry flag (interface launches with valid flag) and display warning if needed
            if (!validEntry) {
                System.out.println();
                System.out.println("***PLEASE ENTER A VALID OPTION***");
                System.out.println();
            }
            //generate list of menu options from menuOptions map and take user input
            System.out.println("Please select from the following options:");
            System.out.println();
            for (Map.Entry<Integer, String> menuOption : mainMenuOptions.entrySet()) {
                System.out.println("(" + menuOption.getKey() + ") " + menuOption.getValue());
//                if((menuOption.getKey()==0) && (mainMenuOptions.size()>2)){
//                    System.out.println();
//                }
            }
            currentUserInput = scanner.nextLine();

            //validate user input is a number, set flag if not and restart mainMenu
            try {
                currentUserInputAsInteger = Integer.valueOf(currentUserInput);

            } catch (NumberFormatException e) {
                validEntry = false;
                mainMenu();
            }

            //set flag if number inputted is not a menu item
            if (!mainMenuOptions.containsKey(currentUserInputAsInteger)) {
                validEntry = false;
            } else {
                validEntry = true;
            }
        }
        //prompt menu again until a valid input is entered
        while (!validEntry);
        //set flag with valid input
        validEntry = true;

        System.out.println();


        //take action based on valid user input
        if (currentUserInput.equals("0")) {
            exit();
        }

        if (currentUserInput.equals("1")) {
            createProject();
            mainMenuOptions.put(2, "Select project to open");
            mainMenuOptions.put(8, "Save current projects to file");

            mainMenu();
        }

        if (currentUserInput.equals("2")) {

            projectMenu();
        }
        if(currentUserInput.equals("7")){
            deleteSessionImages();
        }

        if (currentUserInput.equals("8")) {

            saveProjectsFile();
        }

        if (currentUserInput.equals("9")) {

            openProjectsFile();
        }


    }

    public void projectMenu() {

        Integer currentUserInputAsInteger = 0;


        System.out.println();
        System.out.println("*******************************************************");
        System.out.println("*******           Select a Project             ********");
        System.out.println("*******************************************************");

        do {
            //check valid entry flag (flag true prior bad input) and display warning if needed
            if (!validEntry) {
                System.out.println();
                System.out.println("***Please enter a valid option***");
                System.out.println();
            }
            //generate list of available projects and take user input
            System.out.println("Please select from the following projects:");
            System.out.println();
            for (int i = 0; i < projectList.size(); i++) {
                System.out.println("(" + (i + 1) + ") " + projectList.get(i).getProjectName());
            }
            System.out.println("Enter (0) to return to the main menu");
            currentUserInput = scanner.nextLine();

            //validate user input is a number, set flag if not and restart mainMenu
            try {
                currentUserInputAsInteger = Integer.parseInt(currentUserInput);

            } catch (NumberFormatException e) {
                validEntry = false;
                projectMenu();
            }

            //set flag if number inputted is not a menu item
            if (projectList.size() < currentUserInputAsInteger) {
                validEntry = false;
            } else {
                validEntry = true;
            }

        }
        //prompt menu again until a valid input is entered
        while (!validEntry);
        if (currentUserInputAsInteger == 0) {
            mainMenu();
        }
        //edit project selected by user
        openProject(projectList.get(currentUserInputAsInteger - 1));

    }

    public void openProject(Project currentProject) {
        Integer currentUserInputAsInteger = 0;
        Map<Integer, String> openProjectMenuOptions = new HashMap<>();

        System.out.println();

        System.out.println("*******************************************************");
        System.out.println("*******             Project Menu               ********");
        System.out.println("*******************************************************");

        //setup the openProject menu based on state of currentProject

        if (currentProject.getRoom().getName().equals("")) {
            openProjectMenuOptions.put(1, "Setup the room for " + currentProject.getProjectName());
        }
        if ((currentProject.getRoom().getSqft()!=0)) {
            openProjectMenuOptions.put(1, "Edit the room                 (" + currentProject.getRoom() + ")");
            openProjectMenuOptions.put(2, "Setup the tile for " + currentProject.getProjectName());
        }
        if (currentProject.getTile().getSqft()!=0) {
            openProjectMenuOptions.put(1, "Edit the current room------------(" + currentProject.getRoom() + ")");
            openProjectMenuOptions.put(2, "Edit the current tile------------(" + currentProject.getTile() + ")");
            openProjectMenuOptions.put(3, "Setup the layout parameters for");
        }
        if ((currentProject.getLayout().getPercentOverage()!=(-1))) {
            openProjectMenuOptions.put(1, "Edit the current room------------(" + currentProject.getRoom() + ")");
            openProjectMenuOptions.put(2, "Edit the current tile------------(" + currentProject.getTile() + ")");
            openProjectMenuOptions.put(3, "Edit the current layout----------(" + currentProject.getLayout()+ ")");
            openProjectMenuOptions.put(4, "Calculate the layout");
            openProjectMenuOptions.put(5, "Generate image file from the layout");


        }
        openProjectMenuOptions.put(9, "Erase " + currentProject.getProjectName());
        openProjectMenuOptions.put(0, "Return to the main menu");
        System.out.println("Currently editing project: " + currentProject.getProjectName());
        System.out.println("");


        do {
            //check valid entry flag (interface launches with valid flag) and display warning if needed
            if (!validEntry) {
                System.out.println();
                System.out.println("***Please enter a valid option***");
                System.out.println();
            }
            //generate list of menu options from menuOptions map and take user input
            System.out.println("Please select from the following options:");
            for (Map.Entry<Integer, String> openProjectMenuOption : openProjectMenuOptions.entrySet()) {
                System.out.println("(" + openProjectMenuOption.getKey() + ") " + openProjectMenuOption.getValue());
                if(openProjectMenuOption.getKey()==0){
                    System.out.println();
                }
            }
            currentUserInput = scanner.nextLine();

            //validate user input is a number, set flag if not and restart mainMenu
            try {
                currentUserInputAsInteger = Integer.valueOf(currentUserInput);

            } catch (NumberFormatException e) {
                validEntry = false;
                openProject(currentProject);
            }

            //set flag if number inputted is not a menu item
            if (!openProjectMenuOptions.containsKey(currentUserInputAsInteger)) {
                validEntry = false;
            } else {
                validEntry = true;
            }
        }
        //prompt menu again until a valid input is entered
        while (!validEntry);
        //set flag with valid input
        validEntry = true;

        System.out.println();

        //take action based on valid user input
        if (currentUserInputAsInteger == 0) {
            mainMenu();
        }
        if (currentUserInputAsInteger == 1) {
            double height;
            double width;



            System.out.println("*************      Setup the room       **************");
            System.out.println();
            System.out.println("Please enter height (y-axis) of the room in inches");
            height = Double.parseDouble(scanner.nextLine());
            System.out.println("Please enter width (x-axis) of the room in inches");
            width = Double.parseDouble(scanner.nextLine());
            if(currentProject.getRoom()==null){
                currentProject.setRoom(new Room(height, width));
            }
            else{
                currentProject.getRoom().setDimensions(height,width);
            }
            openProject(currentProject);

        }
        if (currentUserInputAsInteger == 2) {
            double height;
            double width;
            double cost;

            System.out.println("*************      Setup the tile       **************");
            System.out.println();
            System.out.println("Please enter height (y-axis) of the tile in inches");
            height = Double.parseDouble(scanner.nextLine());
            System.out.println("Please enter width (x-axis) of the tile in inches");
            width = Double.parseDouble(scanner.nextLine());
            System.out.println("Please enter the cost of the tile per sqft");
            cost = Double.parseDouble(scanner.nextLine());
            if(currentProject.getTile()==null){
                currentProject.setTile(new Tile(height, width, cost));}
            else{
                currentProject.getTile().setDimensions(height,width);
            }
            openProject(currentProject);

        }
        if (currentUserInputAsInteger == 3) {
            double expansionGap;
            double tileSpacing;
            int overagePercent;


            System.out.println("*************     Setup the layout      **************");
            System.out.println();
            System.out.println("Please enter desired perimeter expansion gap in inches:");
            expansionGap = Double.parseDouble(scanner.nextLine());
            System.out.println("Please enter desired spacing between tiles in inches:");
            tileSpacing = Double.parseDouble(scanner.nextLine());
            System.out.println("Please enter the percent overage (extra tile) to use in calculations - whole number percentage:");
            overagePercent = Integer.parseInt(scanner.nextLine());
            if(currentProject.getLayout().getPercentOverage()==(-1)){
                currentProject.setLayout(new Layout(currentProject.getRoom(), currentProject.getTile(), tileSpacing, expansionGap, overagePercent));
            }
            else{
                currentProject.getLayout().setParameters(tileSpacing,expansionGap,overagePercent);
            }
            openProject(currentProject);

        }
        if (currentUserInputAsInteger == 4) {

            currentProject.getLayout().printLayoutToConsole();
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            openProject(currentProject);
        }

        if (currentUserInputAsInteger == 5) {
            System.out.println("(1) Square symmetrical. (2) Square w/ cuts along origin axis. (3) Subway symmetrical");
            currentUserInput = scanner.nextLine();
            currentUserInputAsInteger = Integer.parseInt(currentUserInput);
            LayoutRendering layoutRendering = new LayoutRendering(currentProject.getLayout(), 32, 5, currentProject.getProjectName());
            String newJpgFilename = layoutRendering.generateJPGFile(currentUserInputAsInteger);
            File newJpgFile = new File(newJpgFilename);
            try {
                Desktop.getDesktop().open(newJpgFile);
                sessionImages.add(newJpgFilename);
            } catch (IOException e) {
                System.out.println("An error occurred trying to open this image file: " + newJpgFilename);
            }
            openProject(currentProject);
        }





        //erase current project. remove project selection menu item if no projects left
        if (currentUserInputAsInteger == 9) {
            System.out.println("CAUTION!!! Are you sure you want to erase? This cannot be undone.");
            System.out.println("Enter (y) to erase, anything else to cancel.");
            currentUserInput = scanner.nextLine();
            if(currentUserInput.equalsIgnoreCase("y")){
                System.out.println(currentProject + " removed successfully.");
                System.out.println("Returning to main menu.");
                projectList.remove(currentProject);
                if (projectList.size() == 0) {
                    mainMenuOptions.remove(2);
                }
                mainMenu();
            }
            else{
                System.out.println("Action cancelled.");
                openProject(currentProject);
            }


        }

    }


    public void createProject() {
        //create a project and add it to the list of projects
        System.out.println("*******************************************************");
        System.out.println("*******           Create a Project             ********");
        System.out.println("*******************************************************");

        System.out.println("Please enter a name for your project:");
        currentUserInput = scanner.nextLine();

        Project newProject = new Project(currentUserInput, new Tile(),
                new Room(), new Layout());
        projectList.add(newProject);
    }

    public void openProjectsFile(){
        System.out.println("*******************************************************");
        System.out.println("*******          Load Saved Projects           ********");
        System.out.println("*******************************************************");

        System.out.println("Please enter the name of your saved projects file:");

        ProjectFileIO projectFileIO = new ProjectFileIO();
        currentUserInput = scanner.nextLine();
        List<Project> openedProjects = projectFileIO.projectsDecode(currentUserInput);
        if(openedProjects != null) {
            for (int i = 0; i < openedProjects.size(); i++) {
                System.out.println("Adding project: " + openedProjects.get(i).getProjectName() + " to project list.");
                projectList.add(openedProjects.get(i));
            }
            System.out.println("Returning to main menu");
            mainMenuOptions.put(2, "Select project to open");
            mainMenuOptions.put(8, "Save current projects to file");
            mainMenu();
        }
        else{
            System.out.println("No valid projects found in file");
            System.out.println("Returning to main menu");
            mainMenu();
        }

    }

    public void saveProjectsFile(){
        System.out.println("*******************************************************");
        System.out.println("*******         Save Current Projects          ********");
        System.out.println("*******************************************************");

        System.out.println("Please enter the name of the file to create:");
        currentUserInput = scanner.nextLine();
        ProjectFileIO projectFileIO = new ProjectFileIO();
        for (int i= 0; i < projectList.size(); i++) {
            if(projectFileIO.writeToFile(currentUserInput, projectFileIO.projectEncoding(projectList.get(i)))) {
                System.out.println("Successfully saved project: " + projectList.get(i).getProjectName());
            }
        }
        System.out.println("Returning to main menu");
        mainMenu();
    }

    private void deleteSessionImages(){
        System.out.println("Are you sure you want to delete:");
        for (int i = 0; i < sessionImages.size(); i++) {
            System.out.println(sessionImages.get(i));
        }
        System.out.println();
        System.out.println("**PLEASE CLOSE ANY OPEN IMAGE FILES BEFORE ATTEMPTING TO DELETE** " +
                System.lineSeparator() + "Enter 'Y' to continue or anything else to abort");
        currentUserInput = scanner.nextLine();
        if(currentUserInput.equalsIgnoreCase("y")) {
            for (int i = 0; i < sessionImages.size(); i++) {
                File file = new File(sessionImages.get(0));
                if (file.delete()) {
                    System.out.println("Deleted the file: " + file.getName());
                    sessionImages.remove(0);
                } else {
                    System.out.println("Failed to delete the file: " + sessionImages.get(0));
                }
            }
            if (sessionImages.size() == 0) {
                mainMenuOptions.remove(7);
            }
        }
        mainMenu();

    }

    public void exit() {

        System.out.println("*************************************");
        System.out.println("*************************************");
        System.out.println("Thanks for using the Tile Layout Tool");
        System.out.println("*************************************");
        System.out.println("*************************************");
        System.exit(0);
    }


}