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

}
