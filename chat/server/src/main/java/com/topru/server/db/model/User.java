package com.topru.server.db.model;

import com.topru.server.Utils;

public class User {
	private final String Login;
	private String Password;
	private boolean hashed;

	public User(String Login, String Password) {
		this.Login = Login;
		this.Password = Password;
		this.hashed = false;
	}

	public User(String Login, String Password, boolean hashed) {
		this.Login = Login;
		this.Password = Password;
		this.hashed = hashed;
	}

	public String getLogin() {
		return this.Login;
	}

	public String getPassword() {
		return this.Password;
	}

	public boolean isHashed() {
		return this.hashed;
	}

	public User hash() {
		if (!this.hashed) {
			this.hashed = true;
			try {
				Password = Utils.getSaltedHash(Password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof User) {
			User user = ((User) object);
			if (!user.Login.equals(Login))
				return false;
			try {
				if (user.isHashed() == this.isHashed()) {
					return user.Password.equals(Password);
				} else if (user.isHashed()) {
					return Utils.check(Password, user.Password);
				} else if (this.isHashed()) {
					return Utils.check(user.Password, Password);
				}
			} catch (Exception e) {
				throw new RuntimeException("Something wrong with equals");
			}

		}
		return false;
	}
}
