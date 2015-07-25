package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.entity.Car;
import carrent.util.SearchOperator;
import carrent.util.SearchRequest;

public class QuickSearchPanel extends JPanel implements DatabaseConstants{

	private JComboBox<String> yearField;
	private AutoSuggest makeField, modelField;
	private JButton search, clear, advanced;
	
	private CarScrollPane carPanels;
	
	public QuickSearchPanel(CarScrollPane carPanels){
		super(new FlowLayout(FlowLayout.CENTER));
		
		this.carPanels = carPanels;
		
		JPanel makeGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		makeGroup.add(new JLabel("Make"));
		makeField = new AutoSuggest(DBInterface.getStrEnum(CAR_MAKE, CAR_TABLE));
		makeGroup.add(makeField);
		makeGroup.setBorder(BorderFactory.createEtchedBorder());
		this.add(makeGroup);
		
		JPanel modelGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		modelGroup.add(new JLabel("Model"));
		modelField = new AutoSuggest(DBInterface.getStrEnum(CAR_MODEL, CAR_TABLE));
		modelGroup.add(modelField);
		modelGroup.setBorder(BorderFactory.createEtchedBorder());
		this.add(modelGroup);
		
		JPanel yearPanel = new JPanel(new BorderLayout());
		JPanel yearGroup = new JPanel(new FlowLayout(FlowLayout.CENTER));
		yearGroup.add(new JLabel("Year"));
		int minYear = DBInterface.getMin(CAR_YEAR, CAR_TABLE);
		int maxYear = DBInterface.getMax(CAR_YEAR, CAR_TABLE);
		ArrayList<String> years = new ArrayList<String>();
		years.add("All");
		
		if(minYear != Integer.MAX_VALUE && maxYear != Integer.MIN_VALUE){
			for(int i = minYear; i < maxYear + 1; i++) {
				years.add(i + "");
			}
		}
		else{
			int year = Calendar.getInstance().get(Calendar.YEAR);
			for(int i = year - 25; i < year + 2; i++) {
				years.add(i + "");
			}
		}
		yearField = new JComboBox(years.toArray());
		yearField.setSelectedIndex(0);
		yearField.setBackground(Color.white);
		
		yearGroup.add(yearField);
		yearGroup.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		yearPanel.setBorder(BorderFactory.createEtchedBorder());
		yearPanel.setPreferredSize(new Dimension(150, 54));
		yearPanel.add(yearGroup);
		this.add(yearPanel);
		
		
		search = new JButton("Go");
		advanced = new JButton("Advanced");
		clear = new JButton("Clear");
		
		QuickSearchListener listener = new QuickSearchListener(this);
		
		search.setActionCommand("search");
		advanced.setActionCommand("advanced");
		clear.setActionCommand("clear");
		
		search.addActionListener(listener);
		advanced.addActionListener(listener);
		clear.addActionListener(listener);
		
		this.add(search);
		this.add(advanced);
		this.add(clear);
		
	}
	
	public void sendSearch(SearchRequest s){
		MainPanel.conditionalPrint("QUERY - Starting car search query");
		long time = System.currentTimeMillis();
		ArrayList<Car> updateList = DBInterface.queryCars(s.generateSearchQuery().toString());
		time = System.currentTimeMillis() - time;
		MainPanel.conditionalPrint(String.format("QUERY - Query completed in %.3f seconds.", ((double) time) / 1000));
		carPanels.updateCarList(updateList);
	}
	
	public void sendWindowClosedEvent(){
		search.setEnabled(true);
		advanced.setEnabled(true);
	}
	
	public class QuickSearchListener implements ActionListener{
		
		private QuickSearchPanel parent;
		
		public QuickSearchListener(QuickSearchPanel parent){
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			
			case "search"	  : SearchRequest basicSearch = new SearchRequest();
								if(!makeField.textField.getText().equals("")){
									basicSearch.setMake(makeField.textField.getText());
								}
								if(!modelField.textField.getText().equals("")){
									basicSearch.setModel(modelField.textField.getText());
								}
								if(!yearField.getSelectedItem().toString().equals("All")){
									basicSearch.setYear(Integer.parseInt(yearField.getSelectedItem().toString()));
									basicSearch.setYearOp(SearchOperator.EQUAL);
								}
								MainPanel.conditionalPrint("QUERY - Starting car search query");
								long time = System.currentTimeMillis();
								ArrayList<Car> updateList = DBInterface.queryCars(basicSearch.generateSearchQuery().toString());
								time = System.currentTimeMillis() - time;
								MainPanel.conditionalPrint(String.format("QUERY - Query completed in %.3f seconds.", ((double) time) / 1000));
								carPanels.updateCarList(updateList);
								
								break;
								
			case "advanced"   : AdvancedSearchWindow.createASW(parent);
								search.setEnabled(false);
								advanced.setEnabled(false);
								break;
								
			case "clear" 	  : 
								makeField.textField.setText(null);
								modelField.textField.setText(null);
								yearField.setSelectedIndex(0);
								break;
								
			}
		}
		
	}
	
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		
		frame.setSize(new Dimension(700, 80));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new QuickSearchPanel(null));
		frame.setVisible(true);
	}
	
}
