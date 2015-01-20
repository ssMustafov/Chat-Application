package com.sirma.itt.javacourse.chatclient.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import com.sirma.itt.javacourse.chatclient.client.Client;
import com.sirma.itt.javacourse.chatcommon.utils.Date;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;
import com.sirma.itt.javacourse.chatcommon.utils.Validator;

/**
 * Represents the user interface for the client.
 * 
 * @author Sinan
 */
public class ClientView implements View, ActionListener, KeyListener {
	public static final String SEND_MESSAGE_BUTTON_ACTION_COMMAND = "send";
	public static final String LOGOUT_BUTTON_ACTION_COMMAND = "logout";
	public static final String DISCONNECT_BUTTON_ACTION_COMMAND = "disconnect";
	private static final String NEW_LINE = System.lineSeparator();
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final int ONLINE_CLIENTS_LIST_WIDTH = 180;
	private JFrame frame = new JFrame();
	private JList<String> onlineClientsList;
	private DefaultListModel<String> onlineClientsListModel;
	private JButton logoutButton;
	private JButton sendMessageButton;
	private JTextArea chatMessagesArea;
	private JTextField clientField;
	private ResourceBundle bundle = LanguageBundleSingleton.getClientBundleInstance();

	private Client client;

	/**
	 * Creates a new user interface for the server.
	 */
	public ClientView(Client client) {
		frame.setTitle(bundle.getString("title") + " - ");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createButtons();
		createTextAreas();
		createLists();
		createFields();

		JScrollPane consoleScrollPane = new JScrollPane();
		consoleScrollPane.setViewportView(chatMessagesArea);
		TitledBorder chatMessagesBorder = BorderFactory.createTitledBorder(bundle
				.getString("chatMessages"));
		chatMessagesBorder.setTitleJustification(TitledBorder.CENTER);
		consoleScrollPane.setBorder(chatMessagesBorder);

		JScrollPane listScrollPane = new JScrollPane(onlineClientsList);
		TitledBorder onlineClientsBorder = BorderFactory.createTitledBorder(bundle
				.getString("onlineClients"));
		onlineClientsBorder.setTitleJustification(TitledBorder.CENTER);
		listScrollPane.setBorder(onlineClientsBorder);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(clientField);
		bottomPanel.add(sendMessageButton);
		bottomPanel.add(logoutButton);

		frame.setLayout(new BorderLayout());
		frame.add(consoleScrollPane, BorderLayout.CENTER);
		frame.add(listScrollPane, BorderLayout.EAST);
		frame.add(bottomPanel, BorderLayout.PAGE_END);

		frame.setVisible(true);
		this.client = client;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendMessageToChatArea(String message) {
		String date = Date.getCurrentDate(Date.SIMPLE_TIME_DATE_FORMAT);
		String formattedMessage = String.format("[%s] %s%s", date, message, NEW_LINE);
		chatMessagesArea.append(formattedMessage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOnlineClient(String nickname) {
		onlineClientsListModel.addElement(nickname);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeOnlineClient(String nickname) {
		onlineClientsListModel.removeElement(nickname);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showNoticeDialog(String message) {
		JOptionPane.showMessageDialog(frame, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetUI() {
		sendMessageButton.setEnabled(false);
		logoutButton.setEnabled(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearOnlineClientsList() {
		onlineClientsListModel.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (SEND_MESSAGE_BUTTON_ACTION_COMMAND.equals(cmd)) {
			if (!Validator.isWhitespaceMessage(clientField.getText())) {
				client.sendMessage(clientField.getText());
				clientField.setText("");
			}
			clientField.requestFocus();
		} else if (LOGOUT_BUTTON_ACTION_COMMAND.equals(cmd)) {
			client.logout();
			frame.dispose();
			new LoginForm();
		}
	}

	/**
	 * Creates the text areas.
	 */
	private void createTextAreas() {
		chatMessagesArea = new JTextArea(5, 20);
		chatMessagesArea.setEditable(false);
		chatMessagesArea.setLineWrap(true);
		DefaultCaret consoleCaret = (DefaultCaret) chatMessagesArea.getCaret();
		consoleCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	/**
	 * Creates the buttons.
	 */
	private void createButtons() {
		sendMessageButton = new JButton(bundle.getString("send"));
		sendMessageButton.setActionCommand(SEND_MESSAGE_BUTTON_ACTION_COMMAND);
		sendMessageButton.addActionListener(this);

		logoutButton = new JButton(bundle.getString("logout"));
		logoutButton.setActionCommand(LOGOUT_BUTTON_ACTION_COMMAND);
		logoutButton.addActionListener(this);
	}

	/**
	 * Creates the lists in the view.
	 */
	private void createLists() {
		onlineClientsListModel = new DefaultListModel<String>();

		onlineClientsList = new JList<String>(onlineClientsListModel);
		onlineClientsList.setFixedCellWidth(ONLINE_CLIENTS_LIST_WIDTH);
	}

	/**
	 * Creates the fields in the view.
	 */
	private void createFields() {
		clientField = new JTextField(35);
		clientField.addKeyListener(this);
		clientField.setDocument(new DocumentLengthFilter(
				ServerConfig.CLIENT_CHAT_MESSAGE_MAX_LENGTH));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			sendMessageButton.doClick();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		frame.dispose();
	}

}
