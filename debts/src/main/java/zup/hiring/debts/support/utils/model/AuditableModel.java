package zup.hiring.debts.support.utils.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableModel<T extends AuditableModel<T>> implements Serializable {

	private static final long serialVersionUID = -3395799298232751572L;

	@CreatedDate
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@LastModifiedDate
	@Column(name = "last_modified")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	public abstract Long getId();

	public abstract void setId(Long id);

	public Date getCreatedAt() {
		return this.createdAt;
	}

	@SuppressWarnings("unchecked")
	public T createdAt(Date createdAt) {
		this.createdAt = createdAt;
		return (T) this;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	@SuppressWarnings("unchecked")
	public T lastModified(Date lastModified) {
		this.lastModified = lastModified;
		return (T) this;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
