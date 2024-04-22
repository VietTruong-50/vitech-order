package vn.vnpt.common.constant;

import lombok.Getter;

@Getter
public enum DeploymentName {
	HIVE_METASTORE("hive-metastore"),
	TRINO_COORDINATOR("trino-coordinator"),
	TRINO_WORKER("trino-worker"),
	CDC("cdc");

	private final String name;

	DeploymentName(String name) {
		this.name = name;
	}

}