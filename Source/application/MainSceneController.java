package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.stage.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainSceneController 
{
	@FXML
	private Button StudentButton;
	@FXML
	private Button ScheduleButton;
	@FXML
	private Button RunButton;
	@FXML
	private TextArea InfoText;
	@FXML
	private TextArea FinishedText;
	
	public static String studentPath = "";
	public static String schedulePath = "";

    public static int numClassesNoSec = 21;
    private static List<Student> allStudents;
    private static List<Student> remainingStudents;
    private static List<Schedule> classes;
	
	public void SetFinishedText(String str)  // for changing the text box text field
	{ 
		FinishedText.clear();
		FinishedText.appendText("\n" + str + "\n"); 
	}
	
	public void StudentPaths(ActionEvent event) throws IOException 
	{
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("CSV", "Students.csv")
				//new FileChooser.ExtensionFilter("CSV", "students.csv")
				);  // only allow student.csv
		File selectedFile = fileChooser.showOpenDialog(stage);
		studentPath = selectedFile.getPath();
		
	}
	
	public void SchedulePaths(ActionEvent event) throws IOException
	{
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("CSV", "Schedule.csv"));
		File selectedFile = fileChooser.showOpenDialog(stage);
		schedulePath = selectedFile.getPath();
		
	}
	
	public void OutputCSV() throws IOException// need to output still
	{
		File assigmentFile = new File("TA_Assignment_File.csv");
        PrintWriter pw = new PrintWriter(assigmentFile);

        pw.write("Subject,Category,Section,LastName,FirstName,TA_Type\n");
        
        for(int i = 0; i < classes.size(); i++) {
            if(classes.get(i).getTAs()[0] != null){
                pw.write("CS" + "," + classes.get(i).getCategory() + "," + classes.get(i).getSection() + "," + classes.get(i).getTAs()[0].getName() + "," + classes.get(i).getTAs()[0].getTaType() +"\n");
                if(classes.get(i).getTAs()[1] != null) {
                    pw.write("CS" + "," + classes.get(i).getCategory() +  "," + classes.get(i).getSection() + "," + classes.get(i).getTAs()[1].getName() + "," + classes.get(i).getTAs()[1].getTaType() +"\n");
                }
            }else {
                pw.write("CS" + "," + classes.get(i).getCategory() + "," + classes.get(i).getSection() + "," + "," + "," + "\n");
            }
        }
        pw.close();
	}
	
	public void RunProgram(ActionEvent event) throws IOException // runs whole algo 
	{
		MainAlgo(studentPath, schedulePath);
		OutputCSV();
		SetFinishedText("Program Is Complete");
	}
	 
	public void MainAlgo(String studentPath, String schedulePath)
	{
		/*  classes = list of all Schedule objects from a file
        allStudents = list of all Student objects from a file
        remainingStudents = list of Student objects that represents students who have not been assigned a TA position
        currCandidates = the current list of Students who are being considered to assign to a class during assignment rounds
        allPossibleTas = an array to keep track of how many TAs are avail to teach each class section */
    classes = readScheduleFromCSV(schedulePath);
    allStudents = readStudentFromCSV(studentPath);
    remainingStudents = readStudentFromCSV(studentPath);
    List<Student> currCandidates;
    int[] allPossibleTas =  new int[classes.size()];

    // // UNIT TEST to see if the dates 2d array is properly filled in a Schedule object
    // int testIndex = 1;
    // boolean[][] dates = classes.get(testIndex).getDates();
    // if(dates != null){
    //     System.out.println("CS " + classes.get(testIndex).getCategory() + " Section " + classes.get(testIndex).getSection() + " dates array info:");
    //     for(int i = 0; i < dates.length; i++){
    //         for(int j = 0; j < dates[i].length; j++){
    //             if(dates[i][j] == true) {
    //                 System.out.println("Day " + i + " @TimeIndex: " + j + " There is class");
    //             }else{
    //                 System.out.println("no class");
    //             }
    //         }
    //     }
    // }else{
    //     System.out.println("CS " + classes.get(testIndex).getCategory() + " Section " + classes.get(testIndex).getSection() + " Has no class times given");
    // } 
    
    // // UNIT TEST to see if the dates 2d array is properly filled in a Student object
    // int testIndex = 0;
    // System.out.println("Looking at student: " + allStudents.get(testIndex).getName());
    // boolean[][] dates = allStudents.get(testIndex).getDates();
    // for(int i = 0; i < dates.length; i++){
    //     for(int j = 0; j < dates[i].length; j++){
    //         if(dates[i][j] == true) {
    //             System.out.println("Day " + i + " @TimeIndex: " + j + " Student is Available");
    //         }else{
    //             System.out.println("not avail");
    //         }
    //     }
    // }

    // // UNIT TEST to check that canTA works and that the student objects are uniform
    // for(int i = 0; i < numClassesNoSec; i++){
    //     System.out.println(allStudents.get(0).canTA(i)); 
    // }

    // // UNIT TEST to check that possibleStudents works 
    // List<Student> qualified = possibleStudents(0);
    // for(int i = 0; i < qualified.size(); i++){
    //     System.out.println(qualified.get(i).getId()); 
    // }

    // // UNIT TEST to check that numPossible works
    // int[] possibleTas =  new int[classes.size()];
    // for(int i = 0; i < classes.size(); i++){
    //     possibleTas[i] = numPossible(i);
    //     System.out.println(possibleTas[i]); 
    // }
       
    // Part 1 UNIT TEST to help track of the number of students before and after assignment rounds
	/*
	 * System.out.println("Number of allStudents: " + allStudents.size());
	 * System.out.println("Number of total classes with unique sections: " +
	 * classes.size()); System.out.println();
	 */

    //first assignment round
    while(true) {
        //gets the number of TA's that could possibly TA each class section
        for(int i = 0; i < classes.size(); i++) {
            allPossibleTas[i] = numPossible(classes.get(i));
        }

        //gets the class index that has the lowest number of possible TA's
        Schedule currClass = classSelect(allPossibleTas, 1);
        
        //if there are no more classes to assign a first TA to, break
        if(currClass == null) {
            break;
        }

        //gets list of students for the current class, and finds the "best" student to assign
        currCandidates = possibleStudents(currClass);
        Student bestOption = currCandidates.get(0);
        if(currCandidates.size() != 1){
            for(int i = 1; i < currCandidates.size(); i++){
                bestOption = bestOption.compareTo(currCandidates.get(i));
            }
        }

        assignStudent(bestOption, currClass, 1);
    }

    // Part 2 UNIT TEST to help track of the number of students before and after assignment rounds
	/*
	 * System.out.println("First round done.");
	 * System.out.println("Number of allStudents: " + allStudents.size());
	 * System.out.println("Number of students who still want to be assigned: " +
	 * remainingStudents.size()); System.out.println();
	 */
    //second round
    while(true) {
        for(int i = 0; i < classes.size(); i++) {
            allPossibleTas[i] = numPossible(classes.get(i));
        }

        //gets the class index that has the lowest number of possible TA's
        Schedule currClass = classSelect(allPossibleTas, 2);
        
        //if there are no more classes to assign a first TA to, break
        if(currClass == null) {
            break;
        }

        //gets list of students for the current class, and finds the "best" student to assign
        currCandidates = possibleStudents(currClass);
        Student bestOption = currCandidates.get(0);
        if(currCandidates.size() != 1){
            for(int i = 1; i < currCandidates.size(); i++){
                bestOption = bestOption.compareTo(currCandidates.get(i));
            }
        }

        assignStudent(bestOption, currClass, 2);
    }

    // Part 3 UNIT TEST to help track of the number of students before and after assignment rounds
	/*
	 * System.out.println("Second round done.");
	 * System.out.println("Number of allStudents: " + allStudents.size());
	 * System.out.println("Number of students who still want to be assigned: " +
	 * remainingStudents.size()); System.out.println();
	 * 
	 * // UNIT TEST to see if the student objects store info about which class the
	 * student has been assigned to
	 * System.out.println("-------------ASSIGNED STUDENTS-------------");
	 */
	/*
	 * for(int i = 0; i < allStudents.size(); i++) {
	 * if(allStudents.get(i).getIsAssigned() == true) {
	 * System.out.println("Student ID: " + allStudents.get(i).getId() +
	 * "  Student Name: " + allStudents.get(i).getName());
	 * System.out.println("    Assigned to: CS" +
	 * allStudents.get(i).getAssignedClass().getCategory());
	 * System.out.println("    Has taken it? " +
	 * allStudents.get(i).hasTaken(allStudents.get(i).getAssignedClass())); } }
	 * System.out.println();
	 * 
	 * // UNIT TEST to see if the class ojects store info about which students have
	 * been assigned to the class
	 * System.out.println("-------------CLASSES AND THEIR TA's-------------");
	 * for(int i = 0; i < classes.size(); i++) { if(classes.get(i).getTAs()[0] !=
	 * null){ System.out.println("CS: " + classes.get(i).getCategory() +
	 * " Section: " + classes.get(i).getSection() + " has the following TA's");
	 * System.out.println("        - " + classes.get(i).getTAs()[0].getName() + " ("
	 * + classes.get(i).getTAs()[0].getTaType() + ")");
	 * if(classes.get(i).getTAs()[1] != null) { System.out.println("        - " +
	 * classes.get(i).getTAs()[1].getName() + " (" +
	 * classes.get(i).getTAs()[1].getTaType() + ")"); } }else {
	 * System.out.println("CS: " + classes.get(i).getCategory() + " Section: " +
	 * classes.get(i).getSection() + " has NO TA's"); } } System.out.println();
	 */
    
    

}

	


//reads a file for schedule objects
private static List<Schedule> readScheduleFromCSV(String fileName) {
    List<Schedule> classes = new ArrayList<>();
    Path pathToFile = Paths.get(fileName);

    try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
        //reads first line, then skips over it
        String line = br.readLine();
        line = br.readLine();

        while (line != null) {
            //deliminates the data using ","
            String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            //creates a schedule object form the data using helper method
            Schedule schedule = createScheduleObj(data);

            //adds the schedule object to the array list
            classes.add(schedule);
            line = br.readLine();
        }
    } catch (IOException ioe) {
        ioe.printStackTrace();
    }
    return classes;
}

//identical a file for Studnet objects
private static List<Student> readStudentFromCSV(String fileName) {
    List<Student> students = new ArrayList<>();
    Path pathToFile = Paths.get(fileName);

    try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)){
        //reads first line, then skips over it
        String line = br.readLine();
        line = br.readLine();
        line = br.readLine();

        while (line != null) {
            //System.out.println(line) //for debugging

            //deliminates the data using ","
            String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); //for debugging

            //creates a schedule object form the data using helper method
            Student student = createStudentObj(data);

            //adds the schedule object to the array list
            students.add(student);
            line = br.readLine();
        }
    } catch (IOException ioe) {
        ioe.printStackTrace();
    }
    return students;
}

//creates the schedule objects given the data from a CSV file line
private static Schedule createScheduleObj(String[] data) {
    int cat = Integer.parseInt(data[1]);
    String sec = data[2];
    String days = data[5].replaceAll(" ", "");
    String sTime = data[6];
    //String eTime = data[7]; //IMPORTANT if classes can be longer than an hr?????
    boolean [][] dates = new boolean[4][8];
    boolean [] dayIndicator = getClassDays(days);

    //populates the 2d dates array
	if(days.isEmpty()){
		dates = null;
	}else {
        //System.out.println("Assigning dates for CS " + cat + " " + sec + ". sTime substring: " + sTime.substring(0,2)); //FOR DEBUGGING
		switch(sTime.substring(0,2)) {
            case "8:": assignDates(dates, dayIndicator, 0); break;
            case "9:": assignDates(dates, dayIndicator, 1); break;
            case "10": assignDates(dates, dayIndicator, 2); break;
            case "11": assignDates(dates, dayIndicator, 3); break;
            case "12": assignDates(dates, dayIndicator, 4); break;
            case "1:": assignDates(dates, dayIndicator, 5); break;
            case "2:": assignDates(dates, dayIndicator, 6); break;
            case "3:": assignDates(dates, dayIndicator, 7); break;
        }
	}
    return new Schedule(cat, sec, dates);
}

//creates the student objects given the data from a CSV file line
private static Student createStudentObj(String[] data) {
    String firstN = data[0];
    String lastN = data[1];
    int id = Integer.parseInt(data[2]);
    String gradQ = data[4];
    int gradY = Integer.parseInt(data[5]);
    int taType = Integer.parseInt(data[6]);
    boolean eburg = false;
    boolean [] taken = new boolean[numClassesNoSec];
    boolean [][] dates = new boolean[4][8];
    int index = 8;
    
    if (data[7] == "Yes") {
        eburg = true;
    }

    for (int i = 0; i < dates.length; i++) {
        for (int k = 0; k < dates[i].length; k++) {
            if (data[index].equals("Open")) {
                dates[i][k] = true;
            }
            index++;
        }
    }

    index = 42;
    for (int i = 0; i < taken.length; i++) {
        taken[i] = false;
        if (data[index].equals("X")) {
            taken[i] = true;
        }
        index++;
    }
    return new Student(firstN, lastN, id, gradQ, gradY, taType, eburg, dates, taken);
}

//Takes in a class object and returns how many students can TA that class
private static int numPossible(Schedule givenClass) {
	int num = 0;
	for(int i = 0; i < remainingStudents.size(); i++) {
		if(remainingStudents.get(i).canTA(givenClass) && remainingStudents.get(i).isFree(givenClass.getDates())) {
			if(givenClass.getHas492TA() && remainingStudents.get(i).getTaType() == 492) {
                continue;
            }else{
                num++;
            }
		}
	}
	return num;
}

//Takes in a class object and returns a list of students who can TA the class
private static List<Student> possibleStudents(Schedule givenClass) {
	List<Student> qualified = new ArrayList<>();
	for(int i = 0; i < remainingStudents.size(); i++) {
		if(remainingStudents.get(i).canTA(givenClass) && remainingStudents.get(i).isFree(givenClass.getDates())) {
            if(givenClass.getHas492TA() && remainingStudents.get(i).getTaType() == 492) {
                continue;
            }else {
                qualified.add(remainingStudents.get(i));
            }
		}
	}
	return qualified;
}

//returns the Schedule object for the class that has the lowest amount of TA's avail and hasnt been assigned in the given round
private static Schedule classSelect(int[] possibleTAs, int roundNum) {
    int index = 0;
    int lowestValue = Integer.MAX_VALUE;
    for(int i = 0; i < possibleTAs.length; i++) {
        if(roundNum == 1) {
            if(possibleTAs[i] < lowestValue && classes.get(i).hasFirstTA() == false && possibleTAs[i] != 0) {
                lowestValue = possibleTAs[i];
                index = i;
            }
        }else {
            if(possibleTAs[i] < lowestValue && classes.get(i).hasSecondTA() == false && possibleTAs[i] != 0) {
                lowestValue = possibleTAs[i];
                index = i;
            }
        }
    }
    if(lowestValue == Integer.MAX_VALUE) {
        return null;
    }
    return classes.get(index);
}

//assigns a given student to a given class taking into account the first or second round
private static void assignStudent(Student student, Schedule currClass, int roundNum) {
    currClass.setTA(student, roundNum);
    int studentID = student.getId();
    for(int i = 0; i < allStudents.size(); i++) {
        if(studentID == allStudents.get(i).getId()) {
            allStudents.get(i).setAssignedClass(currClass);
        }
    }
    int removableIndex = 0;
    for(int i = 0; i < remainingStudents.size(); i++) {
        if(student.getId() == remainingStudents.get(i).getId()) {
            removableIndex = i;
        }
    }
    remainingStudents.remove(removableIndex);
}

//helper function that aids in the creation of 2d dates array for Schedule objects
//takes in the days a class is offered as a string
//returns a bool array representing if the class is offered on a given day 0-4 (mon-thurs)
private static boolean[] getClassDays(String days) {
    boolean [] results = new boolean[4];
    switch(days) {
        case("M"): results[0] = true; break;
        case("MT"): results[0] = true; results[1] = true; break;
        case("MW"): results[0] = true; results[2] = true; break;
        case("MTH"): results[0] = true; results[3] = true; break;
        case("MTW"): results[0] = true; results[1] = true; results[2] = true; break;
        case("MTTH"): results[0] = true; results[1] = true; results[3] = true; break;
        case("MWTH"): results[0] = true; results[2] = true; results[3] = true; break;
        case("MTWTH"): results[0] = true; results[1] = true; results[2] = true; results[3] = true; break;
        case("T"): results[1] = true; break;
        case("TW"): results[1] = true; results[2] = true; break;
        case("TTH"): results[1] = true; results[3] = true; break;
        case("TWTH"): results[1] = true; results[2] = true; results[3] = true; break;
        case("W"): results[2] = true; break;
        case("WTH"): results[2] = true; results[3] = true; break;
        case("TH"): results[3] = true; break;
    }
    return results;
}

//helper function to fill in the 2d dates array for Schedule objects
private static void assignDates(boolean[][] result, boolean[] dayIndicator, int timeSlot) 
{
    for(int i = 0; i < dayIndicator.length; i++) 
    {
        if (dayIndicator[i] == true)
        {
            result[i][timeSlot] = true;
        }
    }
}
	 
}
