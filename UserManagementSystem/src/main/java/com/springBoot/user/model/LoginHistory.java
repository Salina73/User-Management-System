package com.springBoot.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class LoginHistory 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Long userId;
	private java.time.LocalDateTime localDateTime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public java.time.LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(java.time.LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	
}
