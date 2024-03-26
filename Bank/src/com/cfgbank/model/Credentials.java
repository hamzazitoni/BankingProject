package com.cfgbank.model;

public class Credentials {
		private int accountId;
	    private int customerId;
	    private String password;
		public Credentials() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Credentials(int accountId, int customerId, String password) {
			super();
			this.accountId = accountId;
			this.customerId = customerId;
			this.password = password;
		}
		public int getAccountId() {
			return accountId;
		}
		public void setAccountId(int accountId) {
			this.accountId = accountId;
		}
		public int getCustomerId() {
			return customerId;
		}
		public void setCustomerId(int customerId) {
			this.customerId = customerId;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		@Override
		public String toString() {
			return "Credentials [accountId=" + accountId + ", customerId=" + customerId + ", password=" + password
					+ "]";
		}
	    
	    
}
