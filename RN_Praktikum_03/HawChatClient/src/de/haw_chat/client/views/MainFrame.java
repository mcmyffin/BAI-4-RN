package de.haw_chat.client.views;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import de.haw_chat.client.Main;
import de.haw_chat.client.controllers.MainController;
import de.haw_chat.client.network.packets.client_packets.RequestChatroomListPacket;

import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class MainFrame {
	// General frame settings
	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 600;
	private static final String FRAME_TITLE = "HAW-Chat Client";
	public static final String DEFAULT_HOST = "141.22.65.113"; // D:141.22.65.193, M:141.22.65.113

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
	private JPanel settingsPanel;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	public DefaultListModel<String> listModel;
	public JButton buttonLogin;

	private boolean loggedIn = false;
	public JCheckBox chckbxEnableSounds;
	public JCheckBox chckbxSaveprotocols;

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		//tabbedPane.setEnabledAt(1, loggedIn);
		this.loggedIn = loggedIn;

		if (loggedIn) {
			Thread thread = new Thread() {
				@Override
				public void run() {
				}
			};
			thread.start();
		}
	}

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

	public Map<String, DefaultListModel<String>> chatroomUsers = new HashMap<>();

	public DefaultListModel<String> getChatroomUsers(String chatroom) {
		return chatroomUsers.get(chatroom);
	}

	private Map<String, DefaultListModel<String>> chatroomMessages = new HashMap<>();

	public DefaultListModel<String> getChatroomMessages(String chatroom) {
		return chatroomMessages.get(chatroom);
	}

	public void addChatroomPanel(String chatroomName) {
		JPanel chatroomPanel = new JPanel();

		textField_3 = new JTextField();
		textField_3.setBounds(10, 504, 660, 28);
		chatroomPanel.add(textField_3);
		textField_3.setColumns(10);

		components.add(chatroomPanel);
		tabbedPane.addTab(chatroomName, null, chatroomPanel, null);
		chatroomPanel.setLayout(null);

		JList list_1 = new JList();
		list_1.setSelectionModel(new DefaultListSelectionModel() {
			@Override
			public void setSelectionInterval(int index0, int index1) {
				super.setSelectionInterval(-1, -1);
			}
		});
		DefaultListModel<String> messageModel = new DefaultListModel<>();
		chatroomMessages.put(chatroomName, messageModel);
		list_1.setModel(messageModel);
		list_1.setBounds(10, 11, 660, 471);
		chatroomPanel.add(list_1);

		JList list_2 = new JList();
		list_2.setSelectionModel(new DefaultListSelectionModel() {
			@Override
			public void setSelectionInterval(int index0, int index1) {
				super.setSelectionInterval(-1, -1);
			}
		});
		DefaultListModel<String> userModel = new DefaultListModel<>();
		chatroomUsers.put(chatroomName, userModel);
		list_2.setModel(userModel);
		list_2.setBounds(692, 11, 287, 471);
		chatroomPanel.add(list_2);

		JButton btnSenden = new JButton("Senden");
		btnSenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String chatroom = getChatroomName();
				controller.requestSendMessage(chatroom , textField_3.getText());
				textField_3.setText("");
			}
		});
		textField_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String chatroom = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
				controller.requestSendMessage(chatroom , textField_3.getText());
				textField_3.setText("");
			}
		});
		btnSenden.setBounds(692, 504, 116, 28);
		chatroomPanel.add(btnSenden);

		JButton btnChatraumVerlassen = new JButton("Chatraum verlassen");
		btnChatraumVerlassen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chatroom = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
				//controller.requestLeaveChatroom(chatroom);
			}
		});
		btnChatraumVerlassen.setBounds(829, 504, 150, 28);
		chatroomPanel.add(btnChatraumVerlassen);
		btnChatraumVerlassen.setVisible(false);

		refreshColors();

		tabbedPane.addTab(chatroomName, null, chatroomPanel, null);
	}



	public void removeCurrentChatroomPanel() {
		int selectedIndex = tabbedPane.getSelectedIndex();
		tabbedPane.remove(selectedIndex);
		tabbedPane.setSelectedIndex(0);
	}

	public String getChatroomName() {
		String chatroom = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
		return chatroom;
	}

	public void gotoChatroomPanel() {
		int index = tabbedPane.getTabCount() - 1;
		tabbedPane.setSelectedIndex(index);
	}

	public void gotoChatroomOverview() {
		addChatroomPanel(Main.defaultChatroomName);
		tabbedPane.setSelectedIndex(1);
	}

	private void refreshColors(JPanel panel) {
		for (Component component : panel.getComponents()) {
			if (component instanceof JPanel) {
				refreshColors((JPanel) component);
			} else if (component instanceof JButton) {
				continue;
			} else if (component instanceof JTextField) {
				continue;
			} else if (component instanceof JSeparator) {
				float[] hsb = Color.RGBtoHSB(COLOR_BACKGROUND.getRed(), COLOR_BACKGROUND.getGreen(), COLOR_BACKGROUND.getBlue(), null);
				float factor = 0.9f;

				if (hsb[2] > (255/2.0f)) {
					hsb[2] = hsb[2] * factor;
				} else {
					hsb[2] = hsb[2] / factor;
				}

				Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				component.setBackground(color);
				component.setForeground(color);
				continue;
			} else if (component instanceof JList) {
				float[] hsb = Color.RGBtoHSB(COLOR_BACKGROUND.getRed(), COLOR_BACKGROUND.getGreen(), COLOR_BACKGROUND.getBlue(), null);
				float factor = 0.9f;

				if (hsb[2] > (255/2.0f)) {
					hsb[2] = hsb[2] * factor;
				} else {
					hsb[2] = hsb[2] / factor;
				}

				Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				component.setBackground(color);
				component.setForeground(COLOR_FOREGROUND);
				continue;
			}
			component.setForeground(COLOR_FOREGROUND);
			component.setBackground(COLOR_BACKGROUND);
		}
	}

	private void refreshColors() {
		for (Component component : tabbedPane.getComponents()) {
			if (component instanceof JPanel) {
				refreshColors((JPanel) component);
			} else if (component instanceof JButton) {
				continue;
			} else if (component instanceof JTextField) {
				continue;
			} else if (component instanceof JSeparator) {
				float[] hsb = Color.RGBtoHSB(COLOR_BACKGROUND.getRed(), COLOR_BACKGROUND.getGreen(), COLOR_BACKGROUND.getBlue(), null);
				float factor = 0.9f;

				if (hsb[2] > (255/2.0f)) {
					hsb[2] = hsb[2] * factor;
				} else {
					hsb[2] = hsb[2] / factor;
				}

				Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				component.setBackground(color);
				component.setForeground(color);
				continue;
			} else if (component instanceof JList) {
				float[] hsb = Color.RGBtoHSB(COLOR_BACKGROUND.getRed(), COLOR_BACKGROUND.getGreen(), COLOR_BACKGROUND.getBlue(), null);
				float factor = 0.9f;

				if (hsb[2] > (255/2.0f)) {
					hsb[2] = hsb[2] * factor;
				} else {
					hsb[2] = hsb[2] / factor;
				}

				Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				component.setBackground(color);
				component.setForeground(COLOR_FOREGROUND);
				continue;
			}
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

		settingsPanel = new JPanel();
		components.add(settingsPanel);
		tabbedPane.addTab("Einstellungen", null, settingsPanel, null);
		settingsPanel.setLayout(null);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 205, 969, 2);
		settingsPanel.add(separator_2);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 403, 969, 2);
		settingsPanel.add(separator_1);

		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(null);
		components.add(panelLogin);
		panelLogin.setLayout(null);
		panelLogin.setBounds(0, 205, 989, 187);
		settingsPanel.add(panelLogin);

		JLabel labelLogin = new JLabel("Benutzerdaten");
		components.add(labelLogin);
		labelLogin.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelLogin.setBounds(10, 11, 969, 23);
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
		textFieldUsername.setText("unnamed");

		textFieldPassword = new JPasswordField();
		components.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(451, 99, 175, 20);
		panelLogin.add(textFieldPassword);
		textFieldPassword.setText("unnamed");
		textFieldPassword.setEnabled(false);

		buttonLogin = new JButton("Anmelden");
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textFieldUsername.getText();
				String password = textFieldPassword.getText();
				controller.login(username, password);
			}
		});
		buttonLogin.setBounds(537, 138, 89, 23);
		buttonLogin.setEnabled(false);
		panelLogin.add(buttonLogin);

		JPanel panelServerSettings = new JPanel();
		panelServerSettings.setBorder(null);
		components.add(panelServerSettings);
		panelServerSettings.setLayout(null);
		panelServerSettings.setBounds(0, 0, 989, 200);
		settingsPanel.add(panelServerSettings);

		JLabel labelServerSettings = new JLabel("Servereinstellungen");
		components.add(labelServerSettings);
		labelServerSettings.setHorizontalAlignment(SwingConstants.CENTER);
		labelServerSettings.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelServerSettings.setBounds(10, 11, 969, 23);
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
		textFieldHostname.setText(DEFAULT_HOST);

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
		textFieldPort.setText("12345");

		JCheckBox checkBoxEnableSsl = new JCheckBox("");
		checkBoxEnableSsl.setHorizontalAlignment(SwingConstants.RIGHT);
		components.add(checkBoxEnableSsl);
		checkBoxEnableSsl.setBounds(447, 140, 21, 23);
		panelServerSettings.add(checkBoxEnableSsl);

		JButton buttonConnect = new JButton("Verbinden");
		buttonConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String hostname = textFieldHostname.getText();
				try {
					InetAddress.getByName(hostname);
				} catch (UnknownHostException e1) {
					JOptionPane.showMessageDialog(null,
							"Der Server '" + textFieldHostname.getText() + "' ist unbekannt!",
							"Unbekannter Server!", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int port;
				try {
					port = Integer.valueOf(textFieldPort.getText());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Der Port '" + textFieldPort.getText() + "' ist ungültig!",
							"Ungültiger Port!", JOptionPane.ERROR_MESSAGE);
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
		panelChatSettings.setBorder(null);
		components.add(panelChatSettings);
		panelChatSettings.setLayout(null);
		panelChatSettings.setBounds(0, 403, 989, 121);
		settingsPanel.add(panelChatSettings);

		JLabel labelChatSettings = new JLabel("Chateinstellungen");
		components.add(labelChatSettings);
		labelChatSettings.setHorizontalAlignment(SwingConstants.CENTER);
		labelChatSettings.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelChatSettings.setBounds(10, 11, 969, 23);
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

		JButton buttonChangeForegroundColor = new JButton("Aendern");
		buttonChangeForegroundColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color color = JColorChooser.showDialog(null, "Textfarbe auswaehlen", COLOR_FOREGROUND);
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

		JButton buttonChangeBackgroundColor = new JButton("Aendern");
		buttonChangeBackgroundColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Hintergrundfarbe auswaehlen", COLOR_BACKGROUND);
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

		chckbxEnableSounds = new JCheckBox("   Sounds abspielen");
		chckbxEnableSounds.setFont(new Font("Tahoma", Font.BOLD, 11));
		components.add(chckbxEnableSounds);
		chckbxEnableSounds.setBounds(615, 55, 184, 23);
		chckbxEnableSounds.setSelected(true);
		panelChatSettings.add(chckbxEnableSounds);

		chckbxSaveprotocols = new JCheckBox("   Chatprotokolle speichern");
		chckbxSaveprotocols.setFont(new Font("Tahoma", Font.BOLD, 11));
		components.add(chckbxSaveprotocols);
		chckbxSaveprotocols.setBounds(615, 94, 184, 23);
		chckbxSaveprotocols.setEnabled(false);
		panelChatSettings.add(chckbxSaveprotocols);

		JPanel chatroomOverviewPanel = new JPanel();
		//components.add(chatroomOverviewPanel);
		//tabbedPane.addTab("Chatraeume", null, chatroomOverviewPanel, null);
		chatroomOverviewPanel.setLayout(null);

		JList list = new JList();

		JButton btnChatraumBeitreten = new JButton("Chatraum beitreten");
		btnChatraumBeitreten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String value = (String) list.getSelectedValue();
				//if (value != null)
					//controller.requestJoinChatroom(value, "test"); // TODO: REMOVE!
			}
		});
		btnChatraumBeitreten.setEnabled(false);
		btnChatraumBeitreten.setBounds(141, 479, 153, 23);
		chatroomOverviewPanel.add(btnChatraumBeitreten);


		listModel = new DefaultListModel();
		list.setModel(listModel);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				String value = (String) list.getSelectedValue();
				btnChatraumBeitreten.setEnabled(value != null);
			}
		});
		list.setBounds(28, 69, 388, 368);
		chatroomOverviewPanel.add(list);



		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(475, 21, 2, 511);
		chatroomOverviewPanel.add(separator);

		JLabel lblVerfgbareChatrume = new JLabel("Verf\u00FCgbare Chatr\u00E4ume");
		lblVerfgbareChatrume.setHorizontalAlignment(SwingConstants.CENTER);
		lblVerfgbareChatrume.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVerfgbareChatrume.setBounds(28, 21, 388, 14);
		chatroomOverviewPanel.add(lblVerfgbareChatrume);

		JLabel lblNeuenChatraumErstellen = new JLabel("Neuen Chatraum erstellen");
		lblNeuenChatraumErstellen.setHorizontalAlignment(SwingConstants.CENTER);
		lblNeuenChatraumErstellen.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNeuenChatraumErstellen.setBounds(553, 21, 388, 14);
		chatroomOverviewPanel.add(lblNeuenChatraumErstellen);

		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblName.setBounds(589, 76, 100, 14);
		chatroomOverviewPanel.add(lblName);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(720, 73, 175, 20);
		chatroomOverviewPanel.add(textField);

		textField_1 = new JPasswordField();
		textField_1.setColumns(10);
		textField_1.setBounds(720, 112, 175, 20);
		chatroomOverviewPanel.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(720, 151, 175, 20);
		chatroomOverviewPanel.add(textField_2);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(589, 115, 100, 14);
		chatroomOverviewPanel.add(lblPassword);

		JButton btnErstellen = new JButton("Erstellen");
		btnErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				if (name.contains(" ")) {
					JOptionPane.showMessageDialog(null,
							"Ungueltiger Chatname!",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String password = textField_1.getText();
				password = "test"; // TODO: REMOVE!
				if (password.contains(" ")) {
					JOptionPane.showMessageDialog(null,
							"Ungueltiges Passwort!",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int maxUserCount;
				try {
					maxUserCount = Integer.valueOf(textField_2.getText());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Ungueltige maximale Teilnehmerzahl!",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
					return;
				}

				//controller.requestCreateChatroom(name, password, maxUserCount);
			}
		});
		btnErstellen.setBounds(806, 210, 89, 23);
		chatroomOverviewPanel.add(btnErstellen);

		JLabel lblMaxTeilnehmerzahl = new JLabel("Max. Teilnehmerzahl");
		lblMaxTeilnehmerzahl.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxTeilnehmerzahl.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMaxTeilnehmerzahl.setBounds(553, 154, 136, 14);
		chatroomOverviewPanel.add(lblMaxTeilnehmerzahl);

		setLoggedIn(false);

		refreshColors();
	}
}
