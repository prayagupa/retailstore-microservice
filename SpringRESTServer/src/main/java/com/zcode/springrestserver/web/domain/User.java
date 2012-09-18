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
public class User {
	private long id;
	private String name;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
