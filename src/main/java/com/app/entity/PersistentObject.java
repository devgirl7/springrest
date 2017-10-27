package com.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
public abstract class PersistentObject implements Serializable, Identifiable {

	/**
	 * TODO Auto-generated
	 */
	private static final long serialVersionUID = -2392153778594137436L;
	
	/**
	 * This column holds the inserted time of a row on each table.
	 * <p/>
	 * The purpose of this column is to hold the inserted time of any row.
	 */
	@ApiModelProperty(notes = "Inserted Date of entity object", readOnly= true)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTED", nullable = true)
	private Date inserted;

	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

	public Date getInserted() {
		return inserted;
	}

	@PrePersist
	@PreUpdate
	protected void setTime() {
		// only set the inserted for the new rows.
		if (inserted == null)
			inserted = new Date();
	}
}
