/**
 * 
 */
package com.zcode.springrestserver.web.domain;

import javax.persistence.Entity;

/**
 * @author prayag
 * 
 */
@Entity
public class User extends AbstractEntity<Long> {
	private String fullName;

	private String userName;

	private String password;

	private String authority;

	private boolean deleted;

	private boolean disabled;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String name) {
		this.fullName = name;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getAuthority() {
		return authority;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
