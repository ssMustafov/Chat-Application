package com.sirma.itt.javacourse.chatclient.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import com.sirma.itt.javacourse.chatclient.client.Login;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;

/**
 * Represents the login form of the client chat application.
 * 
 * @author Sinan
 */
public class LoginForm implements ActionListener, KeyListener {

	private static final String LOGIN_BUTTON_ACTION_COMMAND = "login";
	private static final String LANG_LIST_ACTION_COMMAND = "langList";
	private static final String ENGLISH_STRING = "English";
	private JFrame frame;
	private JTextField nicknameField;
	private JButton loginButton;
	private JComboBox<?> langList;
	private JProgressBar progressBar;
	private JLabel label;
	private ResourceBundle bundle = LanguageBundleSingleton.getClientLoginBundleInstance();
	private String language = ServerConfig.AVAILABLE_LANGUAGES[0];

	/**
	 * Creates a new login form.
	 */
	public LoginForm() {
		frame = new JFrame();
		frame.setTitle(bundle.getString("loginTitle"));
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		createButtons();
		createComboBoxes();
		createProgressBar();
		createLabels();
		createFields();

		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.ipady = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		frame.getContentPane().add(label, constraints);

		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.ipady = 10;
		constraints.gridx = 0;
		constraints.gridy = 1;
		frame.getContentPane().add(nicknameField, constraints);

		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.ipady = 0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		frame.getContentPane().add(langList, constraints);

		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.ipady = 0;
		constraints.gridx = 0;
		constraints.gridy = 3;
		frame.getContentPane().add(loginButton, constraints);

		constraints.insets = new Insets(40, 0, 0, 0);
		constraints.ipady = 5;
		constraints.gridx = 0;
		constraints.gridy = 4;
		frame.getContentPane().add(progressBar, constraints);

		frame.setVisible(true);
	}

	/**
	 * 
	 */
	private void createFields() {
		nicknameField = new JTextField(20);
		nicknameField.addKeyListener(this);
	}

	/**
	 * 
	 */
	private void createLabels() {
		label = new JLabel(bundle.getString("enterNickname"));
	}

	/**
	 * Shows an error dialog with given message.
	 * 
	 * @param message
	 *            - the message to be shown in the error dialog
	 */
	public void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Shows a notice dialog with given message.
	 * 
	 * @param message
	 *            - the message to be shown in the notice dialog
	 */
	public void showNoticeDialog(String message) {
		JOptionPane.showMessageDialog(frame, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Returns the entered nickname.
	 * 
	 * @return the entered nickname
	 */
	public String getNickname() {
		return nicknameField.getText();
	}

	/**
	 * Creates the buttons.
	 */
	private void createButtons() {
		loginButton = new JButton(bundle.getString("login"));
		loginButton.setActionCommand(LOGIN_BUTTON_ACTION_COMMAND);
		loginButton.addActionListener(this);
	}

	/**
	 * Creates the combo boxes.
	 */
	private void createComboBoxes() {
		langList = new JComboBox<>(ServerConfig.AVAILABLE_LANGUAGES);
		langList.setActionCommand(LANG_LIST_ACTION_COMMAND);
		langList.setSelectedIndex(0);
		langList.addActionListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (LOGIN_BUTTON_ACTION_COMMAND.equals(cmd)) {
			connectToServer();
		} else if (LANG_LIST_ACTION_COMMAND.equals(cmd)) {
			JComboBox<?> cb = (JComboBox<?>) e.getSource();
			language = (String) cb.getSelectedItem();

			setLanguage();
		}
		nicknameField.requestFocus();
	}

	/**
	 * 
	 */
	private void setLanguage() {
		ResourceBundle.clearCache();
		if (ENGLISH_STRING.equals(language)) {
			LanguageBundleSingleton.setClientLoginLocale(Locale.US);
			LanguageBundleSingleton.setClientLocale(Locale.US);
			bundle = LanguageBundleSingleton.getClientLoginBundleInstance();
		} else {
			LanguageBundleSingleton.setClientLoginLocale(new Locale("bg", "BG"));
			LanguageBundleSingleton.setClientLocale(new Locale("bg", "BG"));
			bundle = LanguageBundleSingleton.getClientLoginBundleInstance();
		}
		onLocaleChange();
	}

	/**
	 * 
	 */
	private void connectToServer() {
		Login login = new Login(this);
		login.connectToServer();
	}

	/**
	 * Closes this login form.
	 */
	public void dispose() {
		frame.dispose();
	}

	/**
	 * Updates the text of the UI elements. Must be invoked when the locale is changed.
	 */
	private void onLocaleChange() {
		frame.setTitle(bundle.getString("loginTitle"));
		label.setText(bundle.getString("enterNickname"));
		loginButton.setText(bundle.getString("login"));
	}

	/**
	 * Creates the progress bar.
	 */
	private void createProgressBar() {
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);
	}

	public JProgressBar getProgressBar() {
		return progressBar;
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
			loginButton.doClick();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyReleased(KeyEvent e) {

	}

	public static void main(String[] args) {
		new LoginForm();
	}

}
