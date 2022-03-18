# Developer ReadMe: The following information details how the program operates

## Running the program

Inside the program folder within the repository just double click on the EXE file.

## How to build the project

The project requires only one library that is not a part of java; The JavafxSDK. This library we have included within our github but only the windows version. If you need a MacOS version you will need to download it from https://gluonhq.com/products/javafx/. The other thing needed when compiling the project from source is to set vm arguments at runtime; special note : to build the project in Eclipse, you must create a new javaFX project. 
Example:
 --module-path "C:\Users\jason\Documents\GitHub\CS480\javafx-sdk-17.0.1(windows)\lib" --add-modules=javafx.controls,javafx.fxml
where the first portion needs to be the file path to the sdk lib folder.

TAselector class contains main along with the code needed to run the UI.
There are three java files that are classes that we use to execute our assignment algorithm. The majority of the operations are carried out by the MainAlgo method within the MainSceneController class, with the Student and Schedule classes being used to store information about all student and class sections passed in by the two file inputs.

## The information and methods in the Student class are as follows

The Student class is designed to make manipulating student information from the given student.csv file easier. In the student class there are attributes for name(string), student id (int), graduating quarter(string), graduating year(int), teacher assistant type(int),  and whether the student is in Ellensburg or not(boolean). All these fields have associated getter methods. There is a 2d boolean array that represents a student’s availability from Monday to Friday from 8 AM to 3 PM. 

Also provided are the attributes taken(boolean array), isAssigned(boolean), and assignedClass(Schedule);these attributes are primarily used during the TA assignment process.Taken will return true if a student has taken a given class. isAssigned return true if a student has been assigned a class to TA for and assignedClass returns siad class. Included in the student class is a hashmap for convenient organization of the classes.

Besides the standard getters for the attributes, there are a few other methods:

-canTA is a method whose input is a Schedule object. It then checks if the class has a 492TA and if the student is a 492 TA and uses a helper method hasTaken to return true if a student canTA the class and false otherwise. 

-hasTaken is a helper method for canTA whose input is a Schedule object. It checks if the student has taken a class using the hashmap and the taken attribute and returns true if they have taken the class and false if not. 

-isFree takes in a 2d boolean array representing a Schedule objects 2d dates array. It checks the 2d dates array attribute for the class that represents the students availability with the given 2d array for the class time and returns true if the student is available when the class is offered or false if not.

-compareTo takes in a Student object "other" as input and compares this instance of a Student to the other one. Returns this or other depending on whichever is determined to be a higher priority based off of checks that take into consideration grad year, grad quarter, and TA type.

-assignQValue takes in a string representation of the grad quarter and uses a switch and case statement to return an integer value representing the quarter

## The information and methods in the Schedule class are as follows

There are attributes for storing information for each individual class. The category(int) and section(string) of an individual class, if the class has a firstTA(boolean) or secondTA(boolean) or has492TA(boolean) , a Student array that holds the student objects after they are assigned to the class, and a 2d boolean array representing the days and time that a class is offered. The constructor and setters populate and update these fields.

Besides the standard getters for these attributes, there is a setTA method where the input is a Student object and an int representing round 1 or round 2 assignment. It sets the firstTA or secondTA attribute to true and checks if the student was a 492TA, setting has492TA true if so. 

## How the assignments are done
The MainSceneController class starts by reading the Schedule and Student info files passed by the user, storing each student and class as an object in a list and instantiates an array that is used to keep track of how many TAs are available to aid each individual class and another list for keeping track of the remaining students to be assigned. At this point in the code you can find multiple commented unit tests to check certain methods.

There are two assignment rounds that we do to assign students to classes, the structure of both is the same. Each round starts by updating the data in the array of TAs available to aid each individual class because as assignments are done this information changes. The next step is to get the class with the least amount of TAs available and check that there are still classes to assign a first TA to. A method is then used to get a list of the students available to TA the selected class, compares all the students to find the highest “priority” based off of the compareTo method in the Student class, and then assigns that student to the class as the first TA. As stated earlier, the second round is the same but assigns students to the second TA position and stops when there are no more classes with possible TAs to assign a second TA to.

After the two assignment rounds there are a few more unit tests and then the information of assignments is outputted to a CSV file. 

## The methods in the MainSceneController class are as follows

-readScheduleFromCSV: Input is the Schedule info file provided by the user. Uses the createScheudleObj helper method to create a Schedule object for each specific class section while putting them into a list which it returns.

-readStudentFromCSV: Input is the student info file provided by the user. Uses the createStudentObj helper method to create a Student object for each student while putting them into a list which it returns.

-createScheduleObj: Input is an array that stores the data from one line in the Schedule info file. Goes through the data, formats it, and then passes it into the Schedule constructor and returns the object. 

-createStudentObj: Input is an array that stores the data from one line in the Student info file. Goes through the data, formats it, and then passes it into the Student constructor and returns the object. 

-numPossible: Input is a class object. Uses canTA, isFree, and getTaType from the Student class as well as getHas492TA from the Schedule class to check all remaining students to see how many of them are eligible to TA the given class. Returns the total number of eligible students.

-possibleStudents: Input is a class object.  Uses canTA, isFree, and getTaType from the Student class as well as getHas492TA from the Schedule class to check all remaining students to see how many of them are eligible to TA the given class. Returns a list of all student objects that are eligible to TA the class.

-classSelect: Input is the array that stores information about the number of possible TAs for each class and an int that represents if it is round 1 or round 2 assignment. Compares all of the classes by checking to make sure a class has not been assigned a TA in that round and then it checks the total number of TAs available to the class. It returns the class that has the lowest amount of TAs available. 

-assignStudent: Input is a Student and a Schedule object as well as an int that represents if it is round 1 or round 2 assignment. Uses setTA from the Schedule class and getId and setAssignedClass from the Student class to assign a student to a class while changing fields in the objects that represent assignment. Removes the assigned student from the list of remaining students to be assigned. 

-getClassDays: Input is a string with no spaces representing the days that a class is offered. Helper method for creating Schedule objects that contains a switch with all possible cases and populates a boolean array whose indices represent Monday - Thursday and is true if the class is offered that day. Returns this boolean array. 

-assignDates: Input is the 2d boolean array where the first index is the days monday-thursday and the second index is the different time slots in that day, representing the dates array in a schedule class. There is also a boolean array representing the days the class is offered (from getClassDays) and an int value representing the time that the class is offered at that are also given as input. This helper method populates the 2d boolean array in each Schedule object by going through each day the class is offered, setting the value at the time slot to true. 

## UI portion of the methods include

-SetFinishedText - which just fills in bottom box with a complete text when program is done running

-StudentPaths - what happens when the student button is clicked

-SchedulePaths - what happens when the Schedule button is clicked

-RunProgram- what happens when run button is clicked; calls MainAlgo > OutputCSV > SetFinishedText

## Executable

- The EXE can be found within the program folder * all files in that exe folder need to be with the exe *

## Other notes
- The program will create an empty csv file if one or more inputs are not entered.
- The program will only change messages once when ran.
- The program will overwrite any csv files with the name “TA_Assignment_File” within the exe directory.
- The student and scheduler files need to be formatted in a specific way otherwise it will not work
