//Brian Huang

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;


public class pingGUI 				
{
	private JFrame mainFrame;		//initializing objects
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JLabel pingNumLabel;
	private JPanel controlPanel;
	private JTextField inputIP;
	private JTextField numPing;
	private JButton pingButton;
	private JTextArea textArea;
	private JScrollPane scroll;
	//private JCheckBox continuous;
	

	public pingGUI(){
		prepareGUI();			//calling prepare GUI method
	}
	public static void main(String[] args){		//displaying window
		pingGUI window = new pingGUI();  
		window.showEvent();       
	}
	private void prepareGUI(){
		mainFrame = new JFrame("Ping Test");			//creating mainFrame
		mainFrame.setSize(400,450);
		mainFrame.setLayout(new GridLayout(4, 0));
		mainFrame.setResizable(false);

		headerLabel = new JLabel("",JLabel.CENTER);		//creating labels
		statusLabel = new JLabel("",JLabel.CENTER);
		statusLabel.setVerticalAlignment(0);
		statusLabel.setSize(350,100);
		pingNumLabel = new JLabel("<html><center># Pings<br>(Default = 4)</center></html>");
		//pingNumLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		mainFrame.addWindowListener(new WindowAdapter() {		//including an exit button
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});    
		controlPanel = new JPanel();					//creating control panel
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);						//adding labels and panel, setting visible
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);						//setting main frame to visible
	}
	private void showEvent() {							//setting object values
		headerLabel.setText("Enter a IP address to ping."); 
		inputIP = new JTextField(25);
		pingButton = new JButton("Ping");
		numPing = new JTextField(3);
		//continuous = new JCheckBox("Ping continuously?", false);
		
		textArea = new JTextArea();		//setting text area properties
		textArea.setColumns(10);
		textArea.setRows(10);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setVisible(true);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();	//caret for auto scroll down
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//continuous.setVisible(true);
		scroll = new JScrollPane(textArea);				//scroll bar
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		pingButton.setActionCommand("Ping");			//setting command sent to ButtonClickListener method
		pingButton.addActionListener(new ButtonClickListener());
		
		controlPanel.add(pingButton);					//adding buttons and labels to control panel
		controlPanel.add(inputIP);
		//controlPanel.add(continuous);
		controlPanel.add(pingNumLabel);
		controlPanel.add(numPing);
		mainFrame.add(scroll);
		mainFrame.setVisible(true); 					//setting visible
	}
	/*
	 *  Accepts string as argument. String in this case is the ping information.
	 * Appends to textArea
	 */
	public void printIP(String s) {						
		   textArea.append(s + "\n");
	   }
	/*
	 * Class for accepting commands from button clicks
	 */
	private class ButtonClickListener implements ActionListener {	
		/*
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 * Accepts ActionEvents when buttons are clicked. 
		 */
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			String numP = numPing.getText();		//setting numP value to value of numPing text field
			if (numPing.getText().isEmpty())		//if left blank, ping # set to 4 (default)
				numP = "4";
				
			if (command.equals( "Ping" ))  {		//if "Ping" command is received from the ping button
				if (!inputIP.getText().isEmpty()) {	
					
					/*if (continuous.isSelected()) {
						statusLabel.setText("Pinging IP Address until connection is stopped...");
						runPing("ping " + inputIP.getText() + " -t ");
					*/
					runPing("ping " + inputIP.getText() + " -n " + numP);	//send command if IP address text field is not empty
					statusLabel.setText("Ping another IP?");
					}
				else													//message displayed when IP text field is left empty
					statusLabel.setText("No IP address entered.");
			}	
		}
	}
	/*
	 * method that accepts command in the form of a string. Uses the command prompt functions to ping IP address and return ping info.
	 */
	public void runPing(String command) { 	//Ping user specified IP address
		try {
			Process p = Runtime.getRuntime().exec(command);	//accepts a command and returns according to command received.
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String str = "";
			while ((str = input.readLine()) != null) {
				printIP(str);		//calling method to append text to text area
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}