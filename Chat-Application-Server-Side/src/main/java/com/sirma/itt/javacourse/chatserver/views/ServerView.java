package com.sirma.itt.javacourse.chatserver.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
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
import com.sirma.itt.javacourse.chatcommon.utils.LanguageConstants;
import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;
import com.sirma.itt.javacourse.chatserver.server.Server;

/**
 * Represents the user interface for the server.
 * 
 * @author Sinan
 */
public class ServerView implements View, ActionListener {
	private static final String LANG_LIST_ACTION_COMMAND = "langList";
	private static final String PORT_LIST_ACTION_COMMAND = "portList";
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

	private Server server;

	/**
	 * Creates a new user interface for the server.
	 */
	public ServerView() {
		frame.setTitle(bundle.getString(LanguageConstants.SERVER_TITLE_MESSAGE));
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createButtons();
		createLabels();
		createComboBoxes();
		createTextAreas();
		createLists();
		setOnWindowClosing();

		consoleScrollPane = new JScrollPane();
		consoleScrollPane.setViewportView(consoleArea);

		TitledBorder consoleBorder = BorderFactory.createTitledBorder(bundle
				.getString(LanguageConstants.SERVER_CONSOLE_MESSAGE));
		consoleBorder.setTitleJustification(TitledBorder.LEFT);
		consoleScrollPane.setBorder(consoleBorder);

		listScrollPane = new JScrollPane(onlineClientsList);
		TitledBorder onlineClientsBorder = BorderFactory.createTitledBorder(bundle
				.getString(LanguageConstants.ONLINE_CLIENTS_MESSAGE));
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
		if (LANG_LIST_ACTION_COMMAND.equals(cmd)) {
			JComboBox<?> cb = (JComboBox<?>) e.getSource();
			language = (String) cb.getSelectedItem();

			changeLanguage();
		} else {
			JComboBox<?> cb = (JComboBox<?>) e.getSource();
			String stringPort = (String) cb.getSelectedItem();
			port = Integer.parseInt(stringPort);
		}
	}

	/**
	 * Changes the language of the server ui.
	 */
	private void changeLanguage() {
		ResourceBundle.clearCache();
		if (ServerConfig.AVAILABLE_LANGUAGES[0].equals(language)) {
			LanguageBundleSingleton.setServerLocale(Locale.US);
			bundle = LanguageBundleSingleton.getServerBundleInstance();
		} else {
			LanguageBundleSingleton.setServerLocale(new Locale("bg", "BG"));
			bundle = LanguageBundleSingleton.getServerBundleInstance();
		}
		onLocaleChange();
	}

	/**
	 * Initializes the labels.
	 */
	private void createLabels() {
		labelLang = new JLabel(bundle.getString(LanguageConstants.SERVER_CHOOSE_LANGUAGE_MESSAGE));
		labelPort = new JLabel(bundle.getString(LanguageConstants.SERVER_CHOOSE_PORT_MESSAGE));
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
	 * Creates the combo boxes.
	 */
	private void createComboBoxes() {
		langList = new JComboBox<>(ServerConfig.AVAILABLE_LANGUAGES);
		langList.setActionCommand(LANG_LIST_ACTION_COMMAND);
		langList.setSelectedIndex(0);
		langList.addActionListener(this);

		portList = new JComboBox<>(ServerConfig.SERVER_PORTS);
		portList.setActionCommand(PORT_LIST_ACTION_COMMAND);
		portList.setSelectedIndex(0);
		portList.addActionListener(this);
	}

	/**
	 * Creates the buttons.
	 */
	private void createButtons() {
		startButton = new JButton(bundle.getString(LanguageConstants.SERVER_START_MESSAGE));
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				server = new Server(ServerView.this, port);
				server.startServer();

				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				portList.setEnabled(false);
			}
		});

		stopButton = new JButton(bundle.getString(LanguageConstants.SERVER_STOP_MESSAGE));
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (server != null) {
					server.stopServer();

					startButton.setEnabled(true);
					stopButton.setEnabled(false);
					langList.setEnabled(true);
					portList.setEnabled(true);
				}
			}
		});
	}

	/**
	 * Sets the server to stop on closing the window.
	 */
	private void setOnWindowClosing() {
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (server != null) {
					server.stopServer();
				}

				super.windowClosing(e);
			}
		});
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
	 * Updates the text of the UI elements. Must be invoked when the locale is changed.
	 */
	private void onLocaleChange() {
		TitledBorder consoleBorder = BorderFactory.createTitledBorder(bundle
				.getString(LanguageConstants.SERVER_CONSOLE_MESSAGE));
		consoleBorder.setTitleJustification(TitledBorder.LEFT);
		consoleScrollPane.setBorder(consoleBorder);

		TitledBorder onlineClientsBorder = BorderFactory.createTitledBorder(bundle
				.getString(LanguageConstants.ONLINE_CLIENTS_MESSAGE));
		onlineClientsBorder.setTitleJustification(TitledBorder.CENTER);
		listScrollPane.setBorder(onlineClientsBorder);

		frame.setTitle(bundle.getString(LanguageConstants.SERVER_TITLE_MESSAGE));
		startButton.setText(bundle.getString(LanguageConstants.SERVER_START_MESSAGE));
		stopButton.setText(bundle.getString(LanguageConstants.SERVER_STOP_MESSAGE));
		labelLang.setText(bundle.getString(LanguageConstants.SERVER_CHOOSE_LANGUAGE_MESSAGE));
		labelPort.setText(bundle.getString(LanguageConstants.SERVER_CHOOSE_PORT_MESSAGE));
	}

}
