Team members:
- Jason Nickell
- Long Thai
- John Fornah
- Garrett Marshall
 
# General program design
We will do continuous delivery with a timeline of the project in steps, and only when we are done with one step we will then move on to another. If any problems or conflicts arise, they are addressed immediately by all members of the team.

In the first part our plan is to do a Scrum meeting to determine the strength of each individual in our group and skills that can give us a picture of what tool(s) we can use on our project. We have determined that Java, with it’s hybrid between compiling and runtime execution, will be the fastest way we can develop this project and it is also a tool that most of our team is familiar with.

We will be taking an incremental approach to the development of our program because we want to be flexible to possible requirement changes and we know our software will evolve as we progress through the class. This will also provide us with room for feedback from the testing that we do later on in the quarter.

## Match students algorithm

Firstly, we will create a Student class and a schedule class to hold the data from the CSV files.The program will read the inputs, in this case CSV files, and segment each line of interest into objects with the delimiter being a “,” to separate categories and a new line to separate the data of each category. The objects will have get operators to get all data from categories. 

The program will make class sections, e.g CS 301A01, CS302A02, CS 312, and students into student vertices. Class vertices cannot connect with each other and the same for student vertices, but student vertices can connect to multiple class vertices. The edges in this graph represent the ability for a student to TA the class. These edges will be created by evaluating the open time slots of the students. From this we have relations between students and class; a student cannot TA for classes that they do not have relation/edge to in our graph. 

From here, classes will be ordered by the amount of students available to TA the class. From here, the class will look for a 492 student who can TA the class, and mark that that student has been assigned thus removing him from the pool of candidates. This will ensure that all 492 students are assigned where they can be, and that the classes with the lowest number of possible TA’s are assigned a TA. Then the classes can be re-evaluated to assign 392 students, which can be doubled up to a class if no 492 students have been assigned to it, same for 492. It uses graduation date to choose between multiple 492 or 392 candidates. If the students share the same graduation date, then a student is randomly selected. 
 
## Problems encountered

While the CSV files are very well organized making it easy to read we encountered a problem where we did not know how to interpret the x’s in student; classes taken, whether or not they meant the student has or has not taken the class. Another problem was determining the knowledge base of group members to split the algorithm up in ways we could each work on the code. For example, not everyone in the group knows graph theory.
 
## Summary 

So far, our team has come up with a method to utilize each member’s strengths to split up our work to be more efficient, ruling out pair programming as a viable strategy. Analyzing the CSV files we were able to determine the process of making the selections based on the minimum requirements while also keeping in mind that we will eventually have more requirements like graduation date priority and experience required. 
 
## Current state of your project
At the time of writing this report, we have developed a general plan for how we will approach this initial problem as well as our implementation of the matching algorithm. Starting next week, we will schedule Scrum meetings in order to determine the fine details of our program and matching algorithm, and assign each member coding tasks to complete. 
