package com.topru.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginWindow extends JFrame {
	private final ChatClient client;
	JTextField loginField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton("Login");
	
	public LoginWindow() {
		super("Login");
		
		this.client = new ChatClient("localhost", Utils.PORT);
		client.connect();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(loginField);
		p.add(passwordField);
		p.add(loginButton);
		
		loginButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
			
		});
		
		getContentPane().add(p, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	
	private void doLogin() {
		String login = loginField.getText();
		String password = passwordField.getText();
		
		try {
			if (client.login(login, password)) {
				// bring up the user list window
								
				UserListPane userListPane = new UserListPane(client);
				JFrame frame = new JFrame("User List");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(400,600);
				
				frame.getContentPane().add(userListPane, BorderLayout.CENTER);
				frame.setVisible(true);
				
				setVisible(false);
			} else {
				// show error message
				JOptionPane.showMessageDialog(this, "Invalid login or password!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		LoginWindow loginWin = new LoginWindow();
		loginWin.setVisible(true);
		
		
	}
}
