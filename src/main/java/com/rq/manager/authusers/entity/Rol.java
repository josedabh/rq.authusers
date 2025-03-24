package com.rq.manager.authusers.entity;

import jakarta.persistence.Table;

@Table(name = "ROL")
public enum Rol {
	NORMAL, PREMIUM, ADMIN
}
