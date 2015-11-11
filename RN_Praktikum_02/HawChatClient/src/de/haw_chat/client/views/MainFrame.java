package de.haw_chat.client.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import de.haw_chat.client.controllers.MainController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class MainFrame {
	// General frame settings
	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 600;
	private static final String FRAME_TITLE = "HAW-Chat Client";
	
	// Color settings
	private static Color COLOR_FOREGROUND = Color.RED;
	private static Color COLOR_BACKGROUND = Color.DARK_GRAY;
	
	// Controller
	private MainController controller;
	
	// List of all GUI objects
	private List<Component> components;
	
	// Generated code
	private JFrame frmHawchatClient;
	private JTabbedPane tabbedPane;
	private JTextField textFieldUsername;
	private JTextField textFieldPassword;
	private JTextField textFieldHostname;
	private JTextField textFieldPort;
	private JPanel panelForegroundColor;
	private JPanel panelBackgroundColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainFrame window = new MainFrame();
					MainController controller = new MainController(window);
					window.setController(controller);
					window.frmHawchatClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void setController(MainController controller) {
		this.controller = controller;
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}
	
	private void addChatroomPanel(String chatroomName) {
		JPanel chatroomPanel = new JPanel();
		chatroomPanel.setForeground(COLOR_FOREGROUND);
		chatroomPanel.setBackground(COLOR_BACKGROUND);
		tabbedPane.addTab(chatroomName, null, chatroomPanel, null);
	}
	
	private void removeCurrentChatroomPanel() {
		int selectedIndex = tabbedPane.getSelectedIndex();
		tabbedPane.remove(selectedIndex);
		tabbedPane.setSelectedIndex(0);
	}
	
	private void refreshColors() {
		for (Component component : components) {
			component.setForeground(COLOR_FOREGROUND);
			component.setBackground(COLOR_BACKGROUND);
		}
		panelForegroundColor.setBackground(COLOR_FOREGROUND);
		panelBackgroundColor.setBackground(COLOR_BACKGROUND);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		components = new ArrayList<>();
		
		frmHawchatClient = new JFrame();
		frmHawchatClient.setTitle(FRAME_TITLE);
		frmHawchatClient.setResizable(false);
		frmHawchatClient.setBounds(100, 100, FRAME_WIDTH, FRAME_HEIGHT);
		frmHawchatClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		frmHawchatClient.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 994, 571);
		mainPanel.add(tabbedPane);
		
		JPanel settingsPanel = new JPanel();
		components.add(settingsPanel);
		tabbedPane.addTab("Einstellungen", null, settingsPanel, null);
		settingsPanel.setLayout(null);
		
		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		components.add(panelLogin);
		panelLogin.setLayout(null);
		panelLogin.setBounds(0, 200, 989, 200);
		settingsPanel.add(panelLogin);
		
		JLabel labelLogin = new JLabel("Benutzerdaten");
		components.add(labelLogin);
		labelLogin.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelLogin.setBounds(10, 11, 969, 14);
		panelLogin.add(labelLogin);
		
		JLabel labelUsername = new JLabel("Benutzername:");
		labelUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		components.add(labelUsername);
		labelUsername.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelUsername.setBounds(320, 63, 100, 14);
		panelLogin.add(labelUsername);
		
		JLabel labelPassword = new JLabel("Passwort:");
		labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		components.add(labelPassword);
		labelPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPassword.setBounds(320, 102, 100, 14);
		panelLogin.add(labelPassword);
		
		textFieldUsername = new JTextField();
		components.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		textFieldUsername.setBounds(451, 60, 175, 20);
		panelLogin.add(textFieldUsername);
		
		textFieldPassword = new JPasswordField();
		components.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(451, 99, 175, 20);
		panelLogin.add(textFieldPassword);
		
		JButton buttonLogin = new JButton("Anmelden");
		buttonLogin.setBounds(537, 138, 89, 23);
		panelLogin.add(buttonLogin);
		
		JPanel panelServerSettings = new JPanel();
		panelServerSettings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		components.add(panelServerSettings);
		panelServerSettings.setLayout(null);
		panelServerSettings.setBounds(0, 0, 989, 200);
		settingsPanel.add(panelServerSettings);
		
		JLabel labelServerSettings = new JLabel("Servereinstellungen");
		components.add(labelServerSettings);
		labelServerSettings.setHorizontalAlignment(SwingConstants.CENTER);
		labelServerSettings.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelServerSettings.setBounds(10, 11, 969, 14);
		panelServerSettings.add(labelServerSettings);
		
		JLabel labelHostname = new JLabel("Hostname:");
		labelHostname.setHorizontalAlignment(SwingConstants.RIGHT);
		components.add(labelHostname);
		labelHostname.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelHostname.setBounds(320, 65, 100, 14);
		panelServerSettings.add(labelHostname);
		
		textFieldHostname = new JTextField();
		components.add(textFieldHostname);
		textFieldHostname.setColumns(10);
		textFieldHostname.setBounds(451, 62, 175, 20);
		panelServerSettings.add(textFieldHostname);
		
		JLabel labelPort = new JLabel("Port:");
		labelPort.setHorizontalAlignment(SwingConstants.RIGHT);
		components.add(labelPort);
		labelPort.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPort.setBounds(320, 104, 100, 14);
		panelServerSettings.add(labelPort);
		
		textFieldPort = new JTextField();
		components.add(textFieldPort);
		textFieldPort.setColumns(10);
		textFieldPort.setBounds(451, 101, 175, 20);
		panelServerSettings.add(textFieldPort);
		
		JCheckBox checkBoxEnableSsl = new JCheckBox("");
		checkBoxEnableSsl.setHorizontalAlignment(SwingConstants.RIGHT);
		components.add(checkBoxEnableSsl);
		checkBoxEnableSsl.setBounds(447, 140, 21, 23);
		panelServerSettings.add(checkBoxEnableSsl);
		
		JButton buttonConnect = new JButton("Verbinden");
		buttonConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String hostname = textFieldHostname.getText();
				
				int port;
				try {
					port = Integer.valueOf(textFieldPort.getText());
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Ungültiger Port: " + textFieldPort.getText(),
							"Fehlerhafte Eingabe!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				boolean enableSsl = checkBoxEnableSsl.isSelected();
				controller.setServerConfiguration(hostname, port, enableSsl);
			}
		});
		buttonConnect.setBounds(537, 140, 89, 23);
		panelServerSettings.add(buttonConnect);
		
		JLabel labelEnableSsl = new JLabel("SSL-Verbindung:");
		labelEnableSsl.setHorizontalAlignment(SwingConstants.RIGHT);
		components.add(labelEnableSsl);
		labelEnableSsl.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelEnableSsl.setBounds(320, 143, 100, 14);
		panelServerSettings.add(labelEnableSsl);
		
		JPanel panelChatSettings = new JPanel();
		panelChatSettings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		components.add(panelChatSettings);
		panelChatSettings.setLayout(null);
		panelChatSettings.setBounds(0, 400, 989, 143);
		settingsPanel.add(panelChatSettings);
		
		JLabel labelChatSettings = new JLabel("Chateinstellungen");
		components.add(labelChatSettings);
		labelChatSettings.setHorizontalAlignment(SwingConstants.CENTER);
		labelChatSettings.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelChatSettings.setBounds(10, 11, 969, 14);
		panelChatSettings.add(labelChatSettings);
		
		JLabel labelForegroundColor = new JLabel("Textfarbe:");
		labelForegroundColor.setHorizontalAlignment(SwingConstants.RIGHT);
		components.add(labelForegroundColor);
		labelForegroundColor.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelForegroundColor.setBounds(58, 59, 150, 14);
		panelChatSettings.add(labelForegroundColor);
		
		JLabel lblBackgroundColor = new JLabel("Hintergrundfarbe:");
		lblBackgroundColor.setHorizontalAlignment(SwingConstants.RIGHT);
		components.add(lblBackgroundColor);
		lblBackgroundColor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBackgroundColor.setBounds(58, 98, 150, 14);
		panelChatSettings.add(lblBackgroundColor);
		
		JButton buttonChangeForegroundColor = new JButton("Ändern");
		buttonChangeForegroundColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color color = JColorChooser.showDialog(null, "Textfarbe auswählen", COLOR_FOREGROUND);
				if (color != null) {
					color = new Color(color.getRed(), color.getGreen(), color.getBlue());
					if (color.equals(COLOR_BACKGROUND))
						return;
					
					COLOR_FOREGROUND = color;
					refreshColors();
				}
			}
		});
		buttonChangeForegroundColor.setBounds(275, 55, 89, 23);
		panelChatSettings.add(buttonChangeForegroundColor);
		
		JButton buttonChangeBackgroundColor = new JButton("Ändern");
		buttonChangeBackgroundColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Hintergrundfarbe auswählen", COLOR_BACKGROUND);
				if (color != null) {
					color = new Color(color.getRed(), color.getGreen(), color.getBlue());
					if (color.equals(COLOR_FOREGROUND))
						return;
					
					COLOR_BACKGROUND = color;
					refreshColors();
				}
			}
		});
		buttonChangeBackgroundColor.setBounds(275, 94, 89, 23);
		panelChatSettings.add(buttonChangeBackgroundColor);
		
		panelForegroundColor = new JPanel();
		components.add(panelForegroundColor);
		panelForegroundColor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelForegroundColor.setBounds(230, 55, 22, 23);
		panelChatSettings.add(panelForegroundColor);
		
		panelBackgroundColor = new JPanel();
		components.add(panelBackgroundColor);
		panelBackgroundColor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelBackgroundColor.setBounds(230, 94, 22, 23);
		panelChatSettings.add(panelBackgroundColor);
		
		JCheckBox chckbxEnableSounds = new JCheckBox("   Sounds abspielen");
		chckbxEnableSounds.setFont(new Font("Tahoma", Font.BOLD, 11));
		components.add(chckbxEnableSounds);
		chckbxEnableSounds.setBounds(615, 55, 184, 23);
		panelChatSettings.add(chckbxEnableSounds);
		
		JCheckBox chckbxSaveprotocols = new JCheckBox("   Chatprotokolle speichern");
		chckbxSaveprotocols.setFont(new Font("Tahoma", Font.BOLD, 11));
		components.add(chckbxSaveprotocols);
		chckbxSaveprotocols.setBounds(615, 94, 184, 23);
		panelChatSettings.add(chckbxSaveprotocols);
		
		JPanel chatroomOverviewPanel = new JPanel();
		components.add(chatroomOverviewPanel);
		tabbedPane.addTab("Chaträume", null, chatroomOverviewPanel, null);
		chatroomOverviewPanel.setLayout(null);
		
		String chatroomName = "Chatroom_001";
		// TODO: REPLACE WITH addChatroomPanel(chatroomName);
		JPanel chatroomPanel = new JPanel();
		components.add(chatroomPanel);
		tabbedPane.addTab(chatroomName, null, chatroomPanel, null);
		chatroomPanel.setLayout(null);
		// TODO: COPY ABOVE SOURCE TO addChatroomPanel(chatroomName);
		
		refreshColors();
	}
}
