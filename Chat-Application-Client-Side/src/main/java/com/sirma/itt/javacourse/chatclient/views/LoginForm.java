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
import com.sirma.itt.javacourse.chatcommon.utils.Validator;

/**
 * Represents the login form of the client chat application.
 * 
 * @author Sinan
 */
public class LoginForm implements ActionListener, KeyListener {

	private JFrame frame;
	private JTextField nicknameField;
	private JButton loginButton;
	private JProgressBar progressBar;
	private JComboBox<?> langList;
	private JLabel label;
	private ResourceBundle bundle = LanguageBundleSingleton.getClientLoginBundleInstance();
	private String language = "English";

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
		createProgressBar();
		createComboBoxes();

		label = new JLabel(bundle.getString("enterNickname"));
		nicknameField = new JTextField(20);
		nicknameField.addKeyListener(this);

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

		constraints.insets = new Insets(50, 0, 0, 0);
		constraints.ipady = 5;
		constraints.gridx = 0;
		constraints.gridy = 4;
		frame.getContentPane().add(progressBar, constraints);

		frame.setVisible(true);
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
	 * Creates the progress bar.
	 */
	private void createProgressBar() {
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);
	}

	/**
	 * Creates the buttons.
	 */
	private void createButtons() {
		loginButton = new JButton(bundle.getString("login"));
		loginButton.setActionCommand("login");
		loginButton.addActionListener(this);
	}

	/**
	 * Creates the comboxes.
	 */
	private void createComboBoxes() {
		langList = new JComboBox<>(ServerConfig.AVAILABLE_LANGUAGES);
		langList.setActionCommand("langList");
		langList.setSelectedIndex(0);
		langList.addActionListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("login".equals(cmd)) {
			String nickname = nicknameField.getText();
			if (nickname.isEmpty()) {
				showNoticeDialog(bundle.getString("emptyNickname"));
				return;
			}
			if (!Validator.isValidNickname(nickname)) {
				showNoticeDialog(bundle.getString("invalidNickname"));
				return;
			}
			if (nickname.length() > Validator.MAX_NICKNAME_LENGHT) {
				showNoticeDialog(bundle.getString("maxAllowedNicknameLength")
						+ Validator.MAX_NICKNAME_LENGHT + ". "
						+ bundle.getString("yourNicknameLength") + nickname.length());
				return;
			}

			Login login = new Login(this);
			login.connectToServer();
			login.start();
		} else if ("langList".equals(cmd)) {
			JComboBox<?> cb = (JComboBox<?>) e.getSource();
			language = (String) cb.getSelectedItem();

			ResourceBundle.clearCache();
			if ("English".equals(language)) {
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
		nicknameField.requestFocus();
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

}
