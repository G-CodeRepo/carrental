package carrent.io;

import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ImageTester {

	public static void main(String[] args) throws IOException{
		
		StringBuilder imgString = new StringBuilder();
		
		BufferedInputStream b = new BufferedInputStream(new FileInputStream("test.png"));
		
		int next;
		while((next = b.read()) != -1){
			imgString.append((char) next);
		}
		b.close();
		
		System.out.println(imgString);
		Image image = ImageIO.read(new BLOBInputStream(imgString.toString()));
		
		JFrame frame = new JFrame();
		
		frame.add(new JLabel(new ImageIcon(image)));
		
		frame.setSize(new Dimension(500, 500));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	
}
