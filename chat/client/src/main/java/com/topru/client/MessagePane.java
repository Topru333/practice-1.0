package com.topru.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.topru.client.Listeners.MessageListener;

public class MessagePane extends JPanel implements MessageListener {
	private final ChatClient client;
	private final String login;
	
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> messageList = new JList<String>(listModel);
	private JTextField inputField = new JTextField();

	public MessagePane(final ChatClient client, final String login) {
		this.client = client;
		this.login = login;
		
		client.addMessageListener(this);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(messageList), BorderLayout.CENTER);
		add(inputField, BorderLayout.SOUTH);
		
		inputField.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					String text = inputField.getText();
					client.msg(login, text);
					listModel.addElement("You: " + text);
					inputField.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
	
	public void onMessage (String fromLogin, String msgBody) {
		String line = fromLogin + ": " + msgBody;
		listModel.addElement(line);
	}
	
}
