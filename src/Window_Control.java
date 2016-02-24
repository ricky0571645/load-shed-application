import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fazecast.jSerialComm.SerialPort;


public class Window_Control extends JPanel
{
	//-----------------------------------------------------------------
	//-----------------------VARIABLE DECLARATIONS---------------------
	//-----------------------------------------------------------------
	//Labels
	private JLabel systemStatus;
	private JLabel autoOrManual;
	private JLabel statusImage;
	
	//Buttons
	private JButton exportDataButton;
	private JButton nullButton;
	private JButton logOffButton;
	private JButton powerOffButton;
	
	//Insets
	private Insets insets;
	
	//Graphic Icon
	ImageIcon disconnectStatusImage;
	ImageIcon connectStatusImage;
	
	//GUI Elements
	private Container contents;
	
	//An instance of Window_Credential for swapping
	private Window_Credential tester;
	
	private static final int ROW1 = 0;
	private static final int ROW2 = 1;
	private static final int ROW3 = 2;
	private static final int ROW4 = 3;
	private static final int ROW5 = 4;
	
	private boolean arduinoConnected;
	private SerialPort serialPort;
	
	//--------------FOR DEBUGGING-----------
	//--------------------------------------
	boolean poweredOn = true;
		
	//-----------------------------------------------------------------
	//---------------------------GUI SETUP-----------------------------
	//-----------------------------------------------------------------
	public Window_Control()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints constraint = new GridBagConstraints();
		
		insets = new Insets(0,0,0,0);
		
		//image for status
		connectStatusImage = new ImageIcon("status_good.png", "");
		disconnectStatusImage = new ImageIcon("status_bad.png", "");
		
		if(poweredOn)
			statusImage = new JLabel(connectStatusImage);
		else
			statusImage = new JLabel(disconnectStatusImage);
		
		//add the image to the top
		systemStatus = new JLabel("Status: ");
		systemStatus.setFont(new Font("Calibri", Font.PLAIN, 30));
		systemStatus.setBackground(Color.blue);
		constraint.fill = GridBagConstraints.NONE;
		constraint.weightx = .5;
		constraint.gridwidth = 1;
		constraint.ipadx = 0;  
		constraint.ipady = 0;
		insets.top = 0;
		insets.left = 0;
		constraint.insets = insets;
		//constraint.anchor = GridBagConstraints.FIRST_LINE_START;
		constraint.gridx = 0;
		constraint.gridy = ROW1;
		add(systemStatus, constraint);
				
		//add the image to the top
		constraint.fill = GridBagConstraints.NONE;
		constraint.weightx = .5;
		constraint.gridwidth = 1;
		constraint.ipadx = 0;  
		constraint.ipady = 0;
		insets.top = 0;
		insets.left = 0;
		constraint.insets = insets;
		constraint.gridx = 1;
		constraint.gridy = ROW1;
		add(statusImage, constraint);
		
		//Add button for exporting data
		exportDataButton = new JButton("Export Data To File");
		exportDataButton.setForeground(Color.white);
		exportDataButton.setBackground(Color.decode("#7ec0ee"));
	    constraint.fill = GridBagConstraints.HORIZONTAL;
	    constraint.weightx = 0.5;
		constraint.gridwidth = 1;
		constraint.ipadx = 10;
	    constraint.ipady = 10;
	    insets.top = 20;
	    insets.left = 0;
	    constraint.gridx = 0;
	    constraint.gridy = ROW2;
	    add(exportDataButton, constraint);
	    
	    //Add button for 
	    nullButton = new JButton("No Function");
	    nullButton.setForeground(Color.white);
	    nullButton.setBackground(Color.decode("#7ec0ee"));
  	    constraint.fill = GridBagConstraints.HORIZONTAL;
  	    constraint.weightx = 0.5;
  		constraint.gridwidth = 1;
  		constraint.ipadx = 10;
  	    constraint.ipady = 10;
  	    insets.top = 20;
  	    insets.left = 0;
  	    constraint.gridx = 1;
  	    constraint.gridy = ROW2;
  	    add(nullButton, constraint);
	    
	  	//Add button for 
  	    logOffButton = new JButton("Log Off");
  	    logOffButton.setForeground(Color.white);
  	    logOffButton.setBackground(Color.decode("#7ec0ee"));
	    constraint.fill = GridBagConstraints.HORIZONTAL;
	    constraint.weightx = 0.5;
		constraint.gridwidth = 1;
		constraint.ipadx = 10;
	    constraint.ipady = 10;
	    insets.top = 20;
	    insets.left = 0;
	    constraint.gridx = 0;
	    constraint.gridy = ROW3;
	    add(logOffButton, constraint);
	    
		 //Add button for 
	    powerOffButton = new JButton("Power Load Off");
	    powerOffButton.setForeground(Color.white);
	    powerOffButton.setBackground(Color.decode("#ff6666"));
	    constraint.fill = GridBagConstraints.HORIZONTAL;
	    constraint.weightx = 0.5;
		constraint.gridwidth = 1;
		constraint.ipadx = 10;
	    constraint.ipady = 10;
	    insets.top = 20;
	    insets.left = 0;
	    constraint.gridx = 1;
	    constraint.gridy = ROW3;
	    add(powerOffButton, constraint);
		
		setBackground(Color.white);
		setVisible(true);
		
		//-----------------------------------------------------------------
		//--------------------ARDUINO SERIAL SETUP-------------------------
		//-----------------------------------------------------------------
				
	    
	    //check whether the port is open
	    boolean portOpen = false;
	    //find which port belongs to the Arduino
		int arduinoPort = 0;
		//incrementer for different ports
		int i = 0;
		//check whether the arduino is connected
		arduinoConnected = false;
		
		//get the ports on the computer
		SerialPort[] ports = SerialPort.getCommPorts();
		//will display the ports we have
		System.out.println("To display the ports that we have available: ");
		for(SerialPort port : ports)
		{
			System.out.println(port.getSystemPortName() + "\n");
			if(port.getSystemPortName().equalsIgnoreCase("COM3"))
			{
				//if the port name is COM3 then the Arduino is connected
				arduinoConnected = true;
				arduinoPort = i;
			}
			//increment the ports if any
			i++;
		}
		
		//if the arduino is connected do the following
		if(arduinoConnected)
		{
			serialPort = ports[arduinoPort];
			//check whether port can open
			if(serialPort.openPort())
			{
				System.out.println("Port opened successfully.");
			}
			else 
			{
				System.out.println("Unable to open the port.");
				return;
			}
		}
		
		//-----------------------------------------------------------------
		//---------------------------BUTTON ACTION-------------------------
		//-----------------------------------------------------------------
		
		//power off signal to Arduino
		powerOffButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				poweredOn = false;
				statusImage.setIcon(disconnectStatusImage);
				//and the arduino is connected
				if(arduinoConnected)
				{
					//send data 'd' to arduino to disable power
					byte buffer[] = {'d'};
					int bytesToWrite = buffer.length;
					serialPort.writeBytes(buffer, bytesToWrite);
				}
			}
		});
		
		logOffButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(tester != null)
				{
					tester.swapView("credentialPanel");
					tester.setSize(350, 400);
				}
			}
		});

	}
	
	public void setTester(Window_Credential tester)
	{
		this.tester = tester;
	}
}
