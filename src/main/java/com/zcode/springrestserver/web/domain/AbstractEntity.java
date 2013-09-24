package com.zcode.springrestserver.web.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * 
 * @author prayag
 * 
 * @param <PK>
 */
@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> extends AbstractPersistable<PK> {

	private static final long serialVersionUID = 8453654076725018243L;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	@Version
	@Column
	private int version;

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	@PrePersist
	protected void onCreate() {
		this.created = new java.util.Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastModified = new java.util.Date();
	}

}
