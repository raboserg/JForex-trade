package edu.rabo.jfx.account;

final public class AccountService {

	private String url;
	private String user;
	private String password;
	private boolean isLiveAccount;

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public boolean isLiveAccount() {
		return isLiveAccount;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setLiveAccount(final boolean isLiveAccount) {
		this.isLiveAccount = isLiveAccount;
	}
}
