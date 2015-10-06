package de.floeke.gui;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.floeke.operations.FileWorker;

/**
 * Graphic User Interface
 * @author Floeke
 *
 */
public class GraphicUserInterface extends JFrame
{
	private static final long serialVersionUID = 7817437834171261715L;
	private int width;
	private int height;
	private JTextField txtPasswort;
	private JPasswordField passwordField;
	private JTextField textField;
	private FileWorker fw;
	private String filename;
	private FileDialog browser;
	
	public GraphicUserInterface() 
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		
		//Set size, location, title, image
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);	
		setLocation((int) (width/3), (int) (height/3));
		setTitle("Pictocrypt"); 
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
		setResizable(false);
		getContentPane().setLayout(null);
		
		//Password-Display
		txtPasswort = new JTextField();
		txtPasswort.setText("Password : ");
		txtPasswort.setBounds(10, 189, 71, 20);
		txtPasswort.setColumns(10);
		txtPasswort.setEditable(false);
		txtPasswort.setBorder(null);
		getContentPane().add(txtPasswort);		
		
		//Passwordfield
		passwordField = new JPasswordField();
		passwordField.setBounds(83, 189, 206, 20);
		getContentPane().add(passwordField);
		
		//Read-Button
		final JRadioButton rdbtnRead = new JRadioButton("read");
		rdbtnRead.setBounds(317, 66, 71, 50);
		rdbtnRead.setSelected(true);
		getContentPane().add(rdbtnRead);
		
		//Write-Button
		final JRadioButton rdbtnWrite = new JRadioButton("write");
		rdbtnWrite.setBounds(317, 103, 71, 36);
		getContentPane().add(rdbtnWrite);
		
		//ButtonGroup
		final ButtonGroup radiobuttons = new ButtonGroup();
		radiobuttons.add(rdbtnRead);
		radiobuttons.add(rdbtnWrite);
		
		//FilepathField
		textField = new JTextField();
		textField.setBounds(10, 220, 279, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		//WindowFocusListener (used for event of FileBrowser)
		addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent arg0) {}
			
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				
				try {
					filename = (browser.getDirectory() + browser.getFile()).replace("\\", "\\\\");
					textField.setText(browser.getDirectory()+browser.getFile());
				} catch(NullPointerException e)
				{
					textField.setText("Please enter path or browse");
				}				
			}
		});
		
		//Browser
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(302, 219, 89, 23);
		getContentPane().add(btnBrowse);
		btnBrowse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				browser = new FileDialog(GraphicUserInterface.this);
				browser.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
				browser.setFile("*.bmp");
				browser.setMultipleMode(false);
				browser.setTitle("Choose File");
				browser.setResizable(false);
				browser.setVisible(true);				
				
			}
		});
		
		//TextDisplay
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 11, 279, 167);
		textArea.setColumns(30);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		getContentPane().add(textArea);
		
		//Execute-Button
		JButton btnExecute = new JButton("Execute");
		btnExecute.setBounds(302, 185, 89, 23);
		getContentPane().add(btnExecute);
		btnExecute.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				long startTime = System.nanoTime();
				filename = textField.getText().replace("\\", "\\\\");
				fw = new FileWorker(filename);
				
				if(radiobuttons.isSelected(rdbtnRead.getModel()))
				{
					//Read
					String output = fw.getMessage(new String(passwordField.getPassword()));
					textArea.setText(output+"\n\n\nTime needed: " + (float)(System.nanoTime()-startTime)/1000000000+"s");
				}
				if(radiobuttons.isSelected(rdbtnWrite.getModel()))
				{
					//Write
					String input= textArea.getText();
					fw.toFile(input, new String(passwordField.getPassword()));
					textArea.setText("Done.\n\n\n\nTime needed: " + (float)(System.nanoTime()-startTime)/1000000000+"s");
				}				
								
			}
		});
			
		//Menubar
		JMenuBar menuBar = new JMenuBar(); 
		setJMenuBar(menuBar);
		
		//Menu -> Datei
		JMenu mnDatei = new JMenu("File");
		menuBar.add(mnDatei);
		
		//Menu-Item: Hilfe -> Opens Help-Dialog
		JMenuItem mntmHilfe = new JMenuItem("Help");
		mnDatei.add(mntmHilfe);
		mntmHilfe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				String hilfe = "Mit diesem Programm können Sie in ein BMP-Bild eine Nachricht verstecken und diese auslesen.\n" +
								"Mit Hilfe eines Passworts wird dieses geschützt. \n\n\n ©Floeke";
				JOptionPane.showMessageDialog(null, hilfe, "Hilfe", JOptionPane.OK_CANCEL_OPTION);			
			}
		});
				
		//Menu-Item: Beenden -> Closes Windows and every process hold by it
		JMenuItem mntmBeenden = new JMenuItem("Close");
		mnDatei.add(mntmBeenden);
		mntmBeenden.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
			}
		});
	}
}
