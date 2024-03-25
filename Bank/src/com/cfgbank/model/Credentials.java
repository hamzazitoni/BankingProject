package com.cfgbank.model;

public class Credentials {
	  private String accountId;
	    private String customerId;
	    private String password;
		public Credentials() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Credentials(String accountId, String customerId, String password) {
			super();
			this.accountId = accountId;
			this.customerId = customerId;
			this.password = password;
		}
		public String getAccountId() {
			return accountId;
		}
		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}
		public String getCustomerId() {
			return customerId;
		}
		public void setCustomerId(String customerId) {
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
