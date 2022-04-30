package Main;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class DatabaseApplet implements WindowListener {

	JTable informationArea = null;
	JTextArea inputArea = null;
	JTextArea feedback = null;
	JButton submitButton = new JButton("Submit");
	JButton publisherButton = new JButton("Search by Publisher");
	JButton platformButton = new JButton("Search by Platform");
	JButton genreButton = new JButton("Search by Genre");
	JButton gameButton = new JButton("Search by Game");
	JButton customQueryButton = new JButton("Custom Query");
	public void ShowWindow(int x, int y) {
	
		JFrame f = new JFrame("A JFrame");
		f.setSize(x, y);
		f.setLocation(300,200);
		informationArea = new JTable(10, 40);
		inputArea = new JTextArea(5, 20);
		feedback = new JTextArea(5, 20);
		JPanel panel = new JPanel();
		
		panel.add(submitButton);
		panel.add(publisherButton);
		panel.add(platformButton);
		panel.add(genreButton);
		panel.add(gameButton);
		panel.add(customQueryButton);
		
		panel.setBorder(BorderFactory.createTitledBorder("Controls"));
		inputArea.setBorder(BorderFactory.createTitledBorder("Input Field"));
		feedback.setBorder(BorderFactory.createTitledBorder("Console"));
		feedback.setEditable(false);
		JScrollPane pane = new JScrollPane(informationArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		f.getContentPane().add(BorderLayout.EAST, inputArea);
		f.getContentPane().add(BorderLayout.WEST, feedback);
		f.getContentPane().add(pane, BorderLayout.CENTER);
		f.getContentPane().add(BorderLayout.SOUTH, panel);
		
		//NEED THESE
		f.addWindowListener(this);
		f.setVisible(true);
		
  }
  
  public void AddOnText(JTextArea t, String txt) {
	  t.append(txt);
  }
  public void ClearText(JTextArea t) {
	  t.setText(null);
  }
  public void SetTable(ResultSet rs) throws SQLException {
	  
      DefaultTableModel tableModel = new DefaultTableModel();
      ResultSetMetaData metaData = rs.getMetaData();
      int columnCount = metaData.getColumnCount();

      for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++){
          tableModel.addColumn(metaData.getColumnLabel(columnIndex));
      }

      Object[] row = new Object[columnCount];

      while (rs.next()){
          for (int i = 0; i < columnCount; i++){
              row[i] = rs.getObject(i+1);
          }
          tableModel.addRow(row);
      }
      informationArea.setModel(tableModel);
  }

@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	System.exit(0);
}

@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

}