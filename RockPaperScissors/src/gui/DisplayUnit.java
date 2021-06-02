package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import app.Driver;
import app.Utilities;
import app.Variation;

public class DisplayUnit {

	public static String opponent = "";
	
	private static JPanel mainPanel;
	private static JMenuBar menu = new JMenuBar();
	private static JMenu variations = new JMenu("Variations");
	private static Variation currentVariation = Driver.versions.get(0);
	private static GridBagConstraints c;
	private static JLabel winner = new JLabel("");
	private static JLabel playerPlay = new JLabel("You played: ");
	private static JLabel opponentPlay = new JLabel("Opponent played: ");
	private static String play = "Rock";
	private static JComboBox chooser = new JComboBox();
	private static JPanel pickPanel;
	private static JFrame frame;
	
	/**
	 * method that creates the overall display
	 */
	public static void display() {
		frame = new JFrame("Rock - Paper - Scissors");
		frame.setSize(600, 600);
		
		menu.add(variations);
		menu.add(new JMenu("Help"));
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		setDefaultConstraints(1);
		JScrollPane scroller = new JScrollPane(mainPanel);
		
		pickPanel = new JPanel();
		pickPanel.add(new JLabel("Pick: "));
		mainPanel.add(pickPanel, c);
		
		c.gridy += 1;
		JButton playButton = new JButton("Play");
		mainPanel.add(playButton, c);
		
		playButton.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = currentVariation.play(play);
				if(result == -1) {//if tie
					winner.setText("Tie!");
				}
				else if(result == 0) {//if player loses
					winner.setText("You Lost!");
				}
				else if(result == 1) {//if player wins
					winner.setText("You Won!");
				}
				playerPlay.setText("You played: " + play);
				opponentPlay.setText("Opponent played: " + opponent);
			}
		});
		
		JPanel playsPanel = new JPanel();
		JPanel player = new JPanel();
		player.setLayout(new BoxLayout(player, BoxLayout.Y_AXIS));
		//player.add(new JLabel("[image goes here]"));
		player.add(playerPlay);
		
		JPanel computer = new JPanel();
		computer.setLayout(new BoxLayout(computer, BoxLayout.Y_AXIS));
		//computer.add(new JLabel("[image goes here]"));
		computer.add(opponentPlay);
		
		playsPanel.add(player); playsPanel.add(new JLabel("VS."));
		playsPanel.add(computer);
		c.gridy += 1;
		mainPanel.add(playsPanel, c);
		
		c.gridy += 1;
		JPanel resultsPanel = new JPanel();
		resultsPanel.add(winner);
		mainPanel.add(resultsPanel, c);
		
		updateDisplay();
		
		frame.getContentPane().add(BorderLayout.NORTH, menu);
		frame.add(scroller);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * sets the preferred set of default gridbagconstraints
	 * @param n
	 */
	private static void setDefaultConstraints(int n) {
		if(n == 0) {//for input frame
			c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(0, 4, 0, 4);
			c.gridx = 1;
			c.gridy = 1;
		}
		else if(n == 1) {//for mainPanel
			c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(0, 4, 0, 4);
			c.gridx = 0;
			c.gridy = 0;
		}
	}
	
	/**
	 * private helper method that updates the display
	 * to match the current variation and details
	 */
	private static void updateDisplay() {
		if(pickPanel.getComponentCount() > 1)
			pickPanel.remove(1);
		
		//updates the current choice selector contents
		JComboBox chooser = new JComboBox(currentVariation.getOptions());
		chooser.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box = (JComboBox) e.getSource();
				play = (String) box.getSelectedItem();
			}
		});
		pickPanel.add(chooser);
		
		if(!currentVariation.getName().equals("Default")) {
			frame.setTitle("Rock - Paper - Scissors | " + currentVariation.getName());
		}
		else {
			frame.setTitle("Rock - Paper - Scissors");
		}
	}
	
	/**
	 * public method that adds the given variation
	 */
	public static void addVariation(Variation v) {
		//deletes last two menu items (add and delete)
		if(variations.getComponentCount() != 0) {
			variations.remove(variations.getComponentCount() - 1);
			variations.remove(variations.getComponentCount() - 1);
		}
		
		JMenuItem item = new JMenuItem(v.getName());
		item.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String choice = e.getActionCommand();
				for(int i = 0; i < Driver.versions.size(); i++) {
					if(choice.equals(Driver.versions.get(i).getName())) {
						currentVariation = Driver.versions.get(i);
						break;
					}
				}
				updateDisplay();
			}
		});
		variations.add(item);
		//adds back last two menu items
		finalizeVariations();
	}
	
	/**
	 * adds last two variation menu items (add and delete)
	 */
	private static void finalizeVariations() {
		JMenuItem add = new JMenuItem("Add");
		JMenuItem delete = new JMenuItem("Delete");
		
		add.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String response = JOptionPane.showInputDialog("Please Enter the Name of the New Variation: ");
				Variation temp;
				try {
					temp = new Variation(response);
					//ask for play options
					boolean done = false;
					while(!done) {
						
					}
					if(temp.getOptionsCount() < 3) {
						JOptionPane.showMessageDialog(null, "Each variation must have at least 3 play options! " +
					temp.getOptionsCount() + " is not enough.");
					}
					else {
						temp.prepArray();
						Driver.usedNames.add(response);
						Driver.versions.add(temp);
					}
				}
				catch(IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
					return;
				}
			}
		});
		
		delete.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//delete a variation
			}
		});
		
		variations.add(add);
		variations.add(delete);
	}
	
	/**
	 * creates new frame for taking user input
	 */
	public static int[][] setArray() {
		JFrame inputFrame = new JFrame();
		inputFrame.setSize(600, 250);
		
		setDefaultConstraints(0);
		//not yet implemented
		
		inputFrame.setVisible(true);
		inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		return new int[3][3];
	}
}
