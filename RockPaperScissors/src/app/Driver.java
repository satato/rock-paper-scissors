package app;

import java.util.ArrayList;
import java.util.Arrays;

import gui.DisplayUnit;

public class Driver {

	public static ArrayList<Character> illegalCharacters = new ArrayList<Character>(Arrays.asList(
			'-', '|', '[', ']', ',', ';'));
	public static ArrayList<Variation> versions = new ArrayList<>();
	public static ArrayList<String> usedNames = new ArrayList<>();
	
	public static void main(String[] args) {
		
		//creates default variation, generic rock-paper-scissors
		Variation generic = new Variation("Default");
		usedNames.add("Default");
		generic.addOption("Rock");
		generic.addOption("Paper");
		generic.addOption("Scissors");
		generic.prepArray();
		versions.add(generic);
		DisplayUnit.addVariation(generic);
		
		DisplayUnit.display();

	}

}
