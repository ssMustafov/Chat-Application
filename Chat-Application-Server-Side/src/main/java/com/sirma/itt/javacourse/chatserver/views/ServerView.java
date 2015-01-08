package com.sirma.itt.javacourse.chatserver.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import com.sirma.itt.javacourse.chatcommon.utils.Date;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;

/**
 * Represents the user interface for the server. Extends {@link Observable}.
 * 
 * @author Sinan
 */
public class ServerView extends Observable implements View, ActionListener {
	public static final String START_BUTTON_ACTION_COMMAND = "start";
	private static final String NEW_LINE = System.lineSeparator();
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final int ONLINE_CLIENTS_LIST_WIDTH = 180;
	private JFrame frame = new JFrame();
	private JList<String> onlineClientsList;
	private DefaultListModel<String> onlineClientsListModel;
	private JButton stopButton;
	private JButton startButton;
	private JTextArea consoleArea;
	private JLabel labelLang;
	private JLabel labelPort;
	private JComboBox<?> portList;
	private JComboBox<?> langList;
	private JScrollPane consoleScrollPane;
	private JScrollPane listScrollPane;

	private ResourceBundle bundle = LanguageBundleSingleton.getServerBundleInstance();
	private int port = Integer.parseInt(ServerConfig.SERVER_PORTS[0]);
	private String language = "English";

	/**
	 * Creates a new user interface for the server.
	 */
	public ServerView() {
		frame.setTitle(bundle.getString("title"));
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createButtons();
		createLabels();
		createComboBoxes();
		createTextAreas();
		createLists();

		consoleScrollPane = new JScrollPane();
		consoleScrollPane.setViewportView(consoleArea);

		TitledBorder consoleBorder = BorderFactory.createTitledBorder(bundle.getString("console"));
		consoleBorder.setTitleJustification(TitledBorder.LEFT);
		consoleScrollPane.setBorder(consoleBorder);

		listScrollPane = new JScrollPane(onlineClientsList);
		TitledBorder onlineClientsBorder = BorderFactory.createTitledBorder(bundle
				.getString("onlineClients"));
		onlineClientsBorder.setTitleJustification(TitledBorder.CENTER);
		listScrollPane.setBorder(onlineClientsBorder);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(startButton);
		bottomPanel.add(stopButton);

		JPanel topPanel = new JPanel();
		topPanel.add(labelLang);
		topPanel.add(langList);
		topPanel.add(labelPort);
		topPanel.add(portList);

		frame.setLayout(new BorderLayout());
		frame.add(topPanel, BorderLayout.PAGE_START);
		frame.add(consoleScrollPane, BorderLayout.CENTER);
		frame.add(listScrollPane, BorderLayout.EAST);
		frame.add(bottomPanel, BorderLayout.PAGE_END);

		frame.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendMessageToConsole(String message) {
		String date = Date.getCurrentDate(Date.SIMPLE_TIME_DATE_FORMAT);
		String formattedMessage = String.format("[%s]: %s%s", date, message, NEW_LINE);
		consoleArea.append(formattedMessage);
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
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		portList.setEnabled(true);
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
		if (START_BUTTON_ACTION_COMMAND.equals(cmd)) {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			portList.setEnabled(false);
			langList.setEnabled(false);

			setChanged();
			notifyObservers(cmd);
		} else if ("stop".equals(cmd)) {
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			portList.setEnabled(true);
			langList.setEnabled(true);

			setChanged();
			notifyObservers(cmd);
		} else if ("langList".equals(cmd)) {
			JComboBox<?> cb = (JComboBox<?>) e.getSource();
			language = (String) cb.getSelectedItem();

			ResourceBundle.clearCache();
			if ("English".equals(language)) {
				LanguageBundleSingleton.setServerLocale(Locale.US);
				bundle = LanguageBundleSingleton.getServerBundleInstance();
			} else {
				LanguageBundleSingleton.setServerLocale(new Locale("bg", "BG"));
				bundle = LanguageBundleSingleton.getServerBundleInstance();
			}
			onLocaleChange();
		} else {
			JComboBox<?> cb = (JComboBox<?>) e.getSource();
			String stringPort = (String) cb.getSelectedItem();
			port = Integer.parseInt(stringPort);

			setChanged();
			notifyObservers(port);
		}
	}

	/**
	 * Initializes the labels.
	 */
	private void createLabels() {
		labelLang = new JLabel(bundle.getString("chooseLang"));
		labelPort = new JLabel(bundle.getString("choosePort"));
	}

	/**
	 * Creates the text areas.
	 */
	private void createTextAreas() {
		consoleArea = new JTextArea(5, 20);
		consoleArea.setEditable(false);
		consoleArea.setLineWrap(true);
		DefaultCaret consoleCaret = (DefaultCaret) consoleArea.getCaret();
		consoleCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	/**
	 * Creates the comboxes.
	 */
	private void createComboBoxes() {
		langList = new JComboBox<>(ServerConfig.AVAILABLE_LANGUAGES);
		langList.setActionCommand("langList");
		langList.setSelectedIndex(0);
		langList.addActionListener(this);

		portList = new JComboBox<>(ServerConfig.SERVER_PORTS);
		portList.setActionCommand("portList");
		portList.setSelectedIndex(0);
		portList.addActionListener(this);
	}

	/**
	 * Creates the buttons.
	 */
	private void createButtons() {
		startButton = new JButton(bundle.getString("start"));
		startButton.setActionCommand("start");
		startButton.addActionListener(this);

		stopButton = new JButton(bundle.getString("stop"));
		stopButton.setEnabled(false);
		stopButton.setActionCommand("stop");
		stopButton.addActionListener(this);
	}

	/**
	 * 
	 */
	private void createLists() {
		onlineClientsListModel = new DefaultListModel<String>();

		onlineClientsList = new JList<String>(onlineClientsListModel);
		onlineClientsList.setFixedCellWidth(ONLINE_CLIENTS_LIST_WIDTH);
	}

	/**
	 * Updates the text of the UI elements. Must be invoked when the locale is changed.
	 */
	private void onLocaleChange() {
		TitledBorder consoleBorder = BorderFactory.createTitledBorder(bundle.getString("console"));
		consoleBorder.setTitleJustification(TitledBorder.LEFT);
		consoleScrollPane.setBorder(consoleBorder);

		TitledBorder onlineClientsBorder = BorderFactory.createTitledBorder(bundle
				.getString("onlineClients"));
		onlineClientsBorder.setTitleJustification(TitledBorder.CENTER);
		listScrollPane.setBorder(onlineClientsBorder);

		frame.setTitle(bundle.getString("title"));
		startButton.setText(bundle.getString("start"));
		stopButton.setText(bundle.getString("stop"));
		labelLang.setText(bundle.getString("chooseLang"));
		labelPort.setText(bundle.getString("choosePort"));
	}

}
