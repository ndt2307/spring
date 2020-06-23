package com.example.nonrest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Employee")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Employee {

	@Column(name = "id")
	private @Id @GeneratedValue Long id;
	
	@Column(name = "name")
	@NonNull
	private String name;
	
	@Column(name = "role")
	@NonNull
	private String role;

	
}