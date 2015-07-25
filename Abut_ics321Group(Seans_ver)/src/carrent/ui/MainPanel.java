package carrent.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import carrent.db.DBInterface;
import carrent.db.DatabaseConstants;
import carrent.db.QueryBuilder;
import carrent.entity.Account;
import carrent.entity.Car;
import carrent.entity.Transaction;
import carrent.io.ConfigParser;

public class MainPanel extends JPanel implements DatabaseConstants{

	private MenuBar menuBar;
	private CarScrollPane scrollPane;
	private QuickSearchPanel search;
	private static SplashScreen splash;
	
	public static boolean verbose = false;
	public static boolean compact = true;
	public static boolean debugMenu = false;
	
	public MainPanel(){
		super(new BorderLayout());
		
		conditionalPrint("QUERY - Starting initial car query");
		splash.setProgStr("Building initial car catalog");
		long time = System.currentTimeMillis();
		ArrayList<Car> initialList = DBInterface.queryCars(new QueryBuilder("*", CAR_TABLE).toString());
		time = System.currentTimeMillis() - time;
		splash.addProgress(20);
		conditionalPrint(String.format("QUERY - Query completed in %.3f seconds.", ((double) time) / 1000));
		splash.setProgStr("Drawing GUI assets");
		conditionalPrint("LOG - Drawing scroll pane");
		scrollPane = new CarScrollPane(initialList, this);
		splash.addProgress(30);
		conditionalPrint("LOG - Drawing search panel");
		search = new QuickSearchPanel(scrollPane);
		splash.addProgress(5);
		conditionalPrint("LOG - Drawing menu bar");
		menuBar = new MenuBar(this);
		splash.addProgress(5);
		
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(search, BorderLayout.NORTH);
		
	}
	
	public boolean requestCarRental(){
		Car c = scrollPane.getSelectedCar();
		if(c == null){
			JOptionPane.showMessageDialog(null, "No car currently selected.", "Rental error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		Account a = menuBar.getActiveAccount();
		if(a == null){
			JOptionPane.showMessageDialog(null, "Must be logged in to rent a car.", "Rental error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(c.isRented()){
			JOptionPane.showMessageDialog(null, "Selected car is not available.", "Rental error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		ReservationWindow.showReservationWindow(c, a, scrollPane);
		return true;
	}
	
	public void sendTransaction(Transaction t){
		DBInterface.addTransaction(t);
	}
	
	public static void createAndShowUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		conditionalPrint("LOG - Starting up Car Rental");
		splash.setProgStr("Starting up Car Rental");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		JFrame frame = new JFrame();
		splash.addProgress(10);
		frame.setTitle("Car Rental Database");
		splash.setProgStr("Connecting to Database");
		DBInterface.loadDB();
		splash.addProgress(20);
		
		conditionalPrint("LOG - Successfully connected to database");
		MainPanel mp = new MainPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(900, 600));
		frame.setResizable(false);
		frame.add(mp);
		frame.setJMenuBar(mp.menuBar);
		frame.setLocationRelativeTo(null);
		splash.dispose();
		frame.setVisible(true);
		conditionalPrint("LOG - Initialization complete");
	}
	
	public static void main(String[] args){
		splash = new SplashScreen();
		splash.setVisible(true);
		conditionalPrint("LOG - Loading config file");
		ConfigParser config = new ConfigParser(new File("config.ini"));
		if(!config.loadedOkay()){
			System.out.println("WARNING - Config file not loaded. Using default values.");
		}
		else{
			conditionalPrint("LOG - Successfully loaded config file");
			verbose = config.getBooleanFromInt("verbose", false);
			compact = config.getBooleanFromInt("compact", true);	
			debugMenu = config.getBooleanFromInt("debug", false);
		}
		
		//SwingUtilities.invokeLater(new Runnable(){
			//public void run(){
				try{
					createAndShowUI();
				}
				catch(ClassNotFoundException e){
					System.err.println("CRITICAL ERROR - Failed to load DB2 JCC Driver");
				}
				catch(NullPointerException e){
					if(e.getMessage().contains("bad query")){
						System.err.println("CRITICAL ERROR - Failed to connect to database");
					}
					else{
						e.printStackTrace();
					}
				}
				catch(Exception e) {
					System.out.println("Error: unable to start program.");
				}
				
			}
		//});
		
	//}
	
	public static void conditionalPrint(String str){
		if(verbose){
			System.out.println(str);
		}
	}
	
}
