package app;

import java.util.ArrayList;
import java.util.Random;

import gui.DisplayUnit;

public class Variation {

	private static Random rand = new Random();
	private ArrayList<String> options = new ArrayList<>();
	private int[][] resultsArray;
	private String name;
	private static int untitleds = 0;
	
	/**
	 * Default Constructor
	 */
	public Variation() {
		if(untitleds == 0) {
			this.name = "Untitled";
			untitleds = 1;
		}
		else {
			throw new IllegalArgumentException("An untitled variation already exists.");
		}
	}
	
	/**
	 * Constructor
	 * @param name
	 */
	public Variation(String name) {
		if(!Utilities.checkIllegal(name) || Driver.usedNames.contains(name)) {
			throw new IllegalArgumentException("Invalid Variation Name");
		}
		this.name = name;
	}
	
	/**
	 * adds an option to the variation after checking its validity
	 */
	public void addOption(String option) {
		if(this.options.contains(option))
			throw new IllegalArgumentException("Option already exists for this variation");
		else if(!Utilities.checkIllegal(option))
			throw new IllegalArgumentException("This option name contains illegal characters");
		else {
			this.options.add(option);
		}
	}
	
	/**
	 * prepares the resultsArray to be filled with user input on what beats what
	 */
	public void prepArray() {
		/*resultsArray = new int[this.options.size()][this.options.size()];
		for(int i = 0; i < resultsArray.length; i++) {
			for(int n = 0; n < resultsArray[i].length; n++) {
				if(i == n)
					resultsArray[i][n] = -1; //if two results are the same, they by default tie
			}
		}*/
		if(this.name.equals("Default")) {
			int[][] arr = {{-1,0,1},{1,-1,0},{0,1,-1}};
			setArray(arr);
		}
		else {
			setArray(DisplayUnit.setArray());
		}
	}
	
	private void setArray(int[][] array) {
		if(!(array == null))
			this.resultsArray = array;
	}
	
	/**
	 * allows external classes to access the results array
	 * particularly meant to be used with the DisplayUnit for assigning appropriate user input to the array
	 * @return
	 */
	public int[][] accessArray() {
		return this.resultsArray; //mutability is acceptable
	}
	
	/**
	 * calls the computer to randomly play one of the options available
	 * then checks who wins and returns a result indicating the winner accordingly
	 * @return -1 if computer & player tie, 0 if player loses, 1 if player wins/computer loses
	 */
	public int play(String playerPlay) {
		int player = this.options.indexOf(playerPlay);
		int computer = rand.nextInt(this.options.size()); //randomly generates the computer's play
		DisplayUnit.opponent = this.options.get(computer);
		
		int result = this.resultsArray[player][computer];
		
		return result;
	}
	
	/**
	 * @return an array of this variation's options
	 */
	public String[] getOptions() {
		String[] result = new String[this.options.size()];
		
		for(int i = 0; i < this.options.size(); i++) {
			result[i] = this.options.get(i);
		}
		
		return result;
	}
	
	/**
	 * @return this variation's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return int count of this variation's options
	 */
	public int getOptionsCount() {
		return this.options.size();
	}
	
	public static void resetUntitleds() {
		untitleds = 0;
	}
}
