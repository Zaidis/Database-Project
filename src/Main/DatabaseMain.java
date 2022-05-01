package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JButton;

public class DatabaseMain {

	static DatabaseQuery dq;
	static DatabaseApplet da;
	static int searchMode = 0;
	public static void main(String[] args) {
		
		da = new DatabaseApplet();
		Scanner s = new Scanner(System.in);
		// TODO Auto-generated method stub
		dq = new DatabaseQuery();
		dq.Connection();
		da.ShowWindow(500, 250);
		
		da.customQueryButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					da.SetTable((dq.simpleQuery(da.inputArea.getText())));
					da.feedback.append("\nThere are " + da.informationArea.getRowCount() + " entries");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	da.ClearText(da.inputArea);
		        }
		    });
		da.submitButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
		    		 if(da.inputArea.getText().equalsIgnoreCase("help")) {
		    			 DisplayHelp();
		    			 return;
		    		 }
		    		 
					da.SetTable((dq.simpleQuery(AdvancedQuery(da.inputArea.getText()))));
					da.feedback.append("\nThere are " + da.informationArea.getRowCount() + " entries");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	da.ClearText(da.inputArea);
		        }
		    });
		
		
		SetUpButton("Game", da.gameButton);
		SetUpButton("Platform", da.platformButton);
		SetUpButton("Genre", da.genreButton);
		SetUpButton("Publisher", da.publisherButton);
		DisplayHelp();
		
	}
	
	 static void SetUpButton(String s, JButton button) {
		button.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					da.SetTable((dq.simpleQuery("select * from " + s + ";")));
					da.feedback.append("\nThere are " + da.informationArea.getRowCount() + " entries for " + s);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	da.ClearText(da.inputArea);
		        }
		    });
	}
	 
	 static void DisplayHelp() {
		 da.feedback.setText("Welcome to the game database! Type in the corresponding number and click submit. Instruction Template: <NUM>, <INPUT>, <INPUT> etc.\nType help to clear console.\nHere's a list of \n possible instructions: \n");
	     da.feedback.append("1: Find game by rank range X\n2: Find publisher whose sales are in range X\n3: Search for games made by specific publisher\n4: Find games on specific platform\n5: Search by Publisher and Genre"
	     		+ "\n6: Search by game year and publisher\n7: Search publisher and platform\n8: Find games by publisher, platform and within X years\n9: Find games and years of given publisher and platform\n10: Find games, platform and NA sales by publisher\n11: Find games by platform and specific year"
	     		+ "\n12: Find global sales of given publisher and platform ");
	 }
	 
	 static String AdvancedQuery(String q) {
		
		 String[] answer = q.split(",");
		 
		 for(int i = 1; i < answer.length; i++) {
			 answer[i] = answer[i].strip();
		 }
		 
		 int input = Integer.parseInt(answer[0]);
		 String s = "";
		 switch(input) {
		 case 1: {s += "SELECT game_rank, game_name FROM Game WHERE game_rank <= " + answer[1] + ";\r\n"; break;}
		 case 2: {s += "SELECT publisher_name FROM Publisher WHERE publisher_NAsales > " + answer[1] + ";\r\n"; break;}
		 case 3: {s += "SELECT g.game_name FROM Game g, MadeBy p WHERE g.game_rank = p.game_rank AND p.publisher_name = '" + answer[1] + "' ORDER BY g.game_year;\r\n"; break;}
		 case 4: {s += "SELECT g.game_rank, g.game_name FROM Game g, IsOn o WHERE g.game_name = o.game_name AND o.platform_name = '" + answer[1] + "' ORDER BY g.game_rank;\r\n";  break;}
		 case 5: {s += "SELECT g.game_rank, g.game_name FROM Game g, Has genre, Publisher p, MadeBy b WHERE g.game_rank = b.game_rank AND b.publisher_name = p.publisher_name AND p.publisher_name = '" + answer[1] + "' AND genre.genre_name = '" + answer[2] + "' AND genre.game_rank = g.game_rank ORDER BY g.game_rank; \r\n"; break;}
		 case 6: {s += "SELECT g.game_name, g.game_year FROM Game g, MadeBy b WHERE g.game_rank = b.game_rank AND g.game_year >= '" + answer[1] + "' AND b.publisher_name = '" + answer[2] + "' ORDER BY g.game_rank;\r\n"; break;}
		 case 7: {s += "SELECT g.game_name, o.platform_name, b.publisher_name FROM Game g, MadeBy b, IsOn o WHERE g.game_rank = b.game_rank AND b.publisher_name = '" + answer[1] + "' AND g.game_name = o.game_name AND o.platform_name = '" + answer[2] +"' ORDER BY g.game_rank;"; break;}
		 case 8: {s += "SELECT g.game_name, g.game_year FROM Game g, MadeBy b, IsOn o WHERE g.game_rank = b.game_rank AND b.publisher_name = '" + answer[1] + "' AND g.game_name = o.game_name AND o.platform_name = '" + answer[2] + "' AND g.game_year >= '" + answer[3] + "' ORDER BY g.game_rank;\r\n"; break;}
		 case 9: {s += "SELECT g.game_name, g.game_year FROM Game g, MadeBy b, IsOn o WHERE g.game_rank = b.game_rank AND b.publisher_name = '" + answer[1] + "' AND g.game_name = o.game_name AND o.platform_name = '" + answer[2] + "' ORDER BY g.game_rank;"; break;}
		 case 10: {s += "SELECT g.game_name, o.platform_name, g.game_NAsales FROM Game g, MadeBy b, IsOn o WHERE g.game_rank = b.game_rank AND b.publisher_name = '" + answer[1] + "' AND g.game_name = o.game_name ORDER BY g.game_rank;\r\n"; break;}
		 case 11: {s += "SELECT g.game_rank, g.game_name FROM Game g, IsOn o WHERE g.game_name = o.game_name AND o.platform_name = '" + answer[1] + "' AND g.game_year = " + answer[2] + " ORDER BY g.game_rank;\r\n"; break;}
		 case 12: {s += "SELECT g.game_name, o.platform_name, b.publisher_name, g.game_Gsales FROM Game g, MadeBy b, IsOn o WHERE g.game_rank = b.game_rank AND b.publisher_name = '" + answer[1] + "' AND g.game_name = o.game_name AND o.platform_name = '" + answer[2] + "' ORDER BY g.game_rank;\r\n"; break;}

		 }
		 da.feedback.append("\n" + s);
		 return s;
	 }

}
