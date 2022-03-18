package application;

import java.util.HashMap;

public class Student {
    // fields
    private String name;
    private int id;
    private String gradQ;
    private int gradY;
    private int taType;
    private boolean eburg;
    // 4x8 array representing day and timeslots  
    private boolean [][] dates; 
    //array to hold info for what a student has taken.
    //true if the student has taken the class
    private boolean [] taken;
    private boolean isAssigned; 
    private Schedule assignedClass;
    private static final HashMap<Integer,Integer> classMap = new HashMap<Integer,Integer>();
    static {
    	classMap.put(102, 0);
    	classMap.put(105, 1);
    	classMap.put(107, 2);
    	classMap.put(109, 3);
    	classMap.put(110, 4);
    	classMap.put(111, 5);
    	classMap.put(112, 6);
    	classMap.put(301, 7);
    	classMap.put(302, 8);
    	classMap.put(311, 9);
    	classMap.put(312, 10);
    	classMap.put(361, 11);
    	classMap.put(362, 12);
    	classMap.put(380, 13);
    	classMap.put(420, 14);
    	classMap.put(427, 15);
    	classMap.put(430, 16);
    	classMap.put(440, 17);
    	classMap.put(467, 18);
    	classMap.put(470, 19);
    	classMap.put(480, 20);
    }
    

    // constructor
    public Student (String firstN, String lastN, int id, String gradQ, int gradY, 
                    int ta, boolean eb, boolean [][] dates, boolean [] taken) {
        this.name = lastN + ", " + firstN;
        this.id = id;
        this.gradQ = gradQ;
        this.gradY = gradY;
        this.taType = ta;
        this.eburg = eb;
        this.taken = taken;
        this.dates = dates;
        this.isAssigned = false;
        this.assignedClass = null;
    }

    public int getId() { 
        return this.id; 
    }

    public String getGradQ() {
        return this.gradQ;
    }

    public int getGradY() {
        return this.gradY;
    }

    public int getTaType() {
        return this.taType;
    }

    public boolean inEburg() {
        return this.eburg;
    }

    public boolean getIsAssigned() {
        return this.isAssigned;
    }

    public Schedule getAssignedClass() {
        return this.assignedClass;
    }

    public void setAssignedClass(Schedule input) {
        this.isAssigned = true;
        this.assignedClass = input;
    }

    public String getName() {
        return name;
    }

    public boolean[][] getDates() {
		return dates;
	}

    // return true if a student can TA the given class
    public boolean canTA (Schedule givenClass) {
        if(givenClass.getHas492TA() == true && this.taType == 492) {
            return false;
        }
        boolean hasTaken = hasTaken(givenClass);
        return hasTaken;
    }

    public boolean hasTaken (Schedule givenClass) {
        if(classMap.containsKey(givenClass.getCategory())) {
            int classIndex = classMap.get(givenClass.getCategory());
            return taken[classIndex];
        }else {
            return false;
        }
    }

    // return true if student does not have a time conflict
    public boolean isFree (boolean[][] days) {
        if(days != null){
            for(int i = 0; i < days.length; i++) {
                for(int j = 0; j < days[i].length; j++) {
                    if(days[i][j] == true && this.dates[i][j] == true) {
                        return true;
                    } 
                }
            }
        }
        return false;
    }

    //@Override
    // returns the student who graduates sooner
    public Student compareTo(Student other) {
        if(this.gradY > other.gradY) {
            return this;
        }
        if(this.gradY == other.gradY) {
            if(this.taType > other.taType){
                return this;
            }else if(this.taType == other.taType && assignQValue(this.gradQ) < assignQValue(other.gradQ)) {
                return this;
            }
        }
        return other;
    }

    private int assignQValue(String gradQ) {
        switch(gradQ) {
            case "Winter":
                return 1;
            case "Spring":
                return 2;
            case "Summer":
                return 3;
            case "Fall":
                return 4;
        }
        return 0;

    }
}