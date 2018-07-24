package com.example.webservice.data;

import javax.persistence.*;

@Entity
@Table(name="users")
public class Users {
	@Id
	@Column(columnDefinition = "VARCHAR(50)")
	String id;
	@Column(columnDefinition = "VARCHAR(32)")
	String salt;
	@Column(columnDefinition = "VARCHAR(200)")
	String digest;

	public String getId() {
		return id;
	}

	public String getSalt() {
		return salt;
	}

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setId(String id) {
		this.id = id;
	}

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
