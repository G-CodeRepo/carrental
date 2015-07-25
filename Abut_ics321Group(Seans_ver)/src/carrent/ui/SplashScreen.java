package carrent.ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;
 
public class SplashScreen extends JFrame {
 
    private JLabel imageLabel, progLabel;
    private ImageIcon img;
    private static JProgressBar progBar;
    private JPanel progPanel;
 
    public SplashScreen() {
        super("Splash");
        
        setSize(404, 370);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setLayout(null);
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} 
        //panel = new JPanel(new BorderLayout());
        // displayPanel = new JPanel(new BorderLayout());
        //titlePanel = new JPanel();
        
        progPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        progPanel.setOpaque(false);
        progPanel.setBounds(0, 320, 404, 30);
        progLabel = new JLabel("Starting program...");
        progLabel.setFont(new Font(UIManager.getFont("TextField.font").getFontName(), Font.PLAIN, 20));
        progLabel.setForeground(Color.white);
        progLabel.setOpaque(false);
        
        img = new ImageIcon("splash(2).png");
        imageLabel = new JLabel(img);
        
        progPanel.add(progLabel);
        //displayPanel.add(imageLabel, BorderLayout.CENTER);
        //displayPanel.add(progPanel, BorderLayout.SOUTH);
        imageLabel.setBounds(0, 0, 404, 370);
        
        
        progBar = new JProgressBar();
        progBar.setMinimum(0);
        progBar.setMaximum(100);
        //progBar.setStringPainted(true);
        progBar.setPreferredSize(new Dimension(310, 30));
        progBar.setBounds(0, 350, 404, 20);
        
        add(progBar);
        add(progPanel);
        add(imageLabel);
        
        //panel.add(progBar, BorderLayout.SOUTH);
        //panel.add(displayPanel, BorderLayout.CENTER);
        //panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        //add(panel);
    }
    
    public void addProgress(int progress) {
    	progBar.setValue(progBar.getValue() + progress);
    }
    
    public void setProgStr(String progStr) {
    	progLabel.setText(progStr);
    }
}