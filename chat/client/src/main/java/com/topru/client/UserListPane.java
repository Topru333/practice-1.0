package com.topru.client;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.topru.client.Listeners.UserStatusListener;

public class UserListPane extends JPanel implements UserStatusListener {
	
	private final ChatClient client;
	private JList<String> userListUI;
	private DefaultListModel<String> userListModel; 

	public UserListPane(final ChatClient client) {
		this.client = client;
		this.client.addUserStatusListener(this);
		
		userListModel = new DefaultListModel<String>();
		userListUI = new JList<String>(userListModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(userListUI), BorderLayout.CENTER);
		
		userListUI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked (MouseEvent e) {
				if (e.getClickCount() > 1) {
					String login = userListUI.getSelectedValue();
					MessagePane messagePane = new MessagePane(client, login);
					
					JFrame f = new JFrame("Message: " + login);
					f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					f.setSize(500,500);
					f.getContentPane().add(messagePane, BorderLayout.CENTER);
					f.setVisible(true);
				}
				
			}
		});
	}
	
	public static void main(String[] args) {
		ChatClient client = new ChatClient("localhost", Utils.PORT);
		
		UserListPane userListPane = new UserListPane(client);
		JFrame frame = new JFrame("User List");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,600);
		
		frame.getContentPane().add(userListPane, BorderLayout.CENTER);
		frame.setVisible(true);
		
		if (client.connect()) {
			try {
				client.login("guest", "guest");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void online(String login) {
		userListModel.addElement(login);
		
	}

	public void offline(String login) {
		userListModel.removeElement(login);
	}
}
