package com.sirma.itt.javacourse.chatclient.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import com.sirma.itt.javacourse.chatclient.client.Login;
import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;
import com.sirma.itt.javacourse.chatcommon.utils.Validator;

/**
 * Represents the login form of the client chat application.
 * 
 * @author Sinan
 */
public class LoginForm implements ActionListener {

	private JFrame frame;
	private JTextField nicknameField;
	private JButton loginButton;
	private JProgressBar progressBar;
	private JComboBox<?> langList;

	/**
	 * Creates a new login form.
	 */
	public LoginForm() {
		frame = new JFrame();
		frame.setTitle("Chat Login Form");
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		createButtons();
		createProgressBar();
		createComboBoxes();

		JLabel label = new JLabel("Enter nickname:");
		nicknameField = new JTextField(20);

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
		loginButton = new JButton("Login");
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
				showNoticeDialog("The nickname is empty.");
				return;
			}
			if (!Validator.isValidNickname(nickname)) {
				showNoticeDialog("The nickname contains forbidden symbols. Can consist of letters, numbers, dash and underscore.");
				return;
			}
			if (nickname.length() > Validator.MAX_NICKNAME_LENGHT) {
				showNoticeDialog("The max allowed nickname length is: "
						+ Validator.MAX_NICKNAME_LENGHT + ". Your nickname's length is: "
						+ nickname.length());
				return;
			}

			// progressBar.setVisible(true);
			nicknameField.setEnabled(false);
			loginButton.setEnabled(false);
			langList.setEnabled(false);
			Login login = new Login(this);
			login.connectToServer();
			login.start();
		}
	}

	/**
	 * Closes this login form.
	 */
	public void dispose() {
		frame.dispose();
	}

	// /**
	// * Updates the text of the UI elements. Must be invoked when the locale is changed.
	// */
	// private void onLocaleChange() {
	// startButton.setText(bundle.getString("start"));
	// stopButton.setText(bundle.getString("stop"));
	// labelLang.setText(bundle.getString("chooseLang"));
	// labelPort.setText(bundle.getString("choosePort"));
	// }

}
