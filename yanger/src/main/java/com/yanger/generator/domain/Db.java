package com.yanger.generator.domain;

import lombok.Data;

@Data
public class Db {

	private String driverName;

	private String user;

	private String schema;

	private String password;

	private String url;

}