package vn.vnpt.common.constant;

import lombok.Getter;

@Getter
public enum DebeziumConstant {

	DEBEZIUM_FORMAT_KEY("debezium.format.key", "json", ""),
	DEBEZIUM_SINK_TYPE("debezium.sink.type", "iceberg", ""),
	DEBEZIUM_TRANSFORMS("debezium.transforms", "unwrap", ""),
	DEBEZIUM_FORMAT_VALUE("debezium.format.value", "json", ""),
	DEBEZIUM_SOURCE_SLOT_NAME("debezium.source.slot.name", null, ""),
	DEBEZIUM_SINK_ICEBERG_URI("debezium.sink.iceberg.uri", null, ""),
	DEBEZIUM_SINK_ICEBERG_TYPE("debezium.sink.iceberg.type", "hive", ""),
	DEBEZIUM_SOURCE_PLUGIN_NAME("debezium.source.plugin.name", "pgoutput", ""),
	DEBEZIUM_SINK_ICEBERG_UPSERT("debezium.sink.iceberg.upsert", true, ""),
	DEBEZIUM_SOURCE_TOPIC_PREFIX("debezium.source.topic.prefix", null, ""),
	DEBEZIUM_SINK_ICEBERG_CLIENTS("debezium.sink.iceberg.clients", null, ""),
	DEBEZIUM_SOURCE_DATABASE_PORT("debezium.source.database.port", null, ""),
	DEBEZIUM_SOURCE_DATABASE_USER("debezium.source.database.user", null, ""),
	DEBEZIUM_SOURCE_MAX_BATCH_SIZE("debezium.source.max.batch.size", null, ""),
	DEBEZIUM_SOURCE_MAX_QUEUE_SIZE("debezium.source.max.queue.size", null, ""),
	DEBEZIUM_SINK_ICEBERG_WAREHOUSE("debezium.sink.iceberg.warehouse", null, ""),
	DEBEZIUM_SOURCE_CONNECTOR_CLASS("debezium.source.connector.class", null, ""),
	DEBEZIUM_SOURCE_DATABASE_DBNAME("debezium.source.database.dbname", null, ""),
	DEBEZIUM_SOURCE_DATABASE_HOSTNAME("debezium.source.database.hostname", null, ""),
	DEBEZIUM_SOURCE_DATABASE_PDB_NAME("debezium.source.database.pdb.name", null, ""),
	DEBEZIUM_SOURCE_DATABASE_PASSWORD("debezium.source.database.password", null, ""),
	DEBEZIUM_FORMAT_KEY_SCHEMAS_ENABLE("debezium.format.key.schemas.enable", true, ""),
	DEBEZIUM_SOURCE_DATABASE_SERVER_ID("debezium.source.database.server.id", null, ""),
	DEBEZIUM_SOURCE_TABLE_INCLUDE_LIST("debezium.source.table.include.list", null, ""),
	DEBEZIUM_SOURCE_TABLE_EXCLUDE_LIST("debezium.source.table.exclude.list", null, ""),
	DEBEZIUM_SINK_ICEBERG_TABLE_PREFIX("debezium.sink.iceberg.table-prefix", null, ""),
	DEBEZIUM_SINK_ICEBERG_FS_DEFAULT_FS("debezium.sink.iceberg.fs.defaultFS", null, ""),
	DEBEZIUM_SINK_BATCH_BATCH_SIZE_WAIT("debezium.sink.batch.batch-size-wait", "MaxBatchSizeWait", ""),
	DEBEZIUM_SOURCE_SNAPSHOT_FETCH_SIZE("debezium.source.snapshot.fetch.size", null, ""),
	DEBEZIUM_SOURCE_SCHEMA_EXCLUDE_LIST("debezium.source.schema.exclude.list", null, ""),
	DEBEZIUM_SOURCE_SCHEMA_INCLUDE_LIST("debezium.source.schema.include.list", null, ""),
	DEBEZIUM_SOURCE_DATABASE_SERVER_NAME("debezium.source.database.server.name", null, ""),
	DEBEZIUM_FORMAT_VALUE_SCHEMAS_ENABLE("debezium.format.value.schemas.enable", true, ""),
	DEBEZIUM_SOURCE_SNAPSHOT_MAX_THREADS("debezium.source.snapshot.max.threads", null, ""),
	DEBEZIUM_SOURCE_DATABASE_INCLUDE_LIST("debezium.source.database.include.list", null, ""),
	DEBEZIUM_SOURCE_DATABASE_EXCLUDE_LIST("debezium.source.database.exclude.list", null, ""),
	DEBEZIUM_SINK_ICEBERG_FS_S3A_ENDPOINT("debezium.sink.iceberg.fs.s3a.endpoint", null, ""),
	DEBEZIUM_SOURCE_INCLUDE_SCHEMA_CHANGES("debezium.source.include.schema.changes", "false", ""),
	DEBEZIUM_SOURCE_OFFSET_FLUSH_INTERVAL_MS("debezium.source.offset.flush.interval.ms", 0, ""),
	DEBEZIUM_SINK_ICEBERG_FS_S3A_ACCESS_KEY("debezium.sink.iceberg.fs.s3a.access.key", null, ""),
	DEBEZIUM_SINK_ICEBERG_FS_S3A_SECRET_KEY("debezium.sink.iceberg.fs.s3a.secret.key", null, ""),
	DEBEZIUM_SINK_ICEBERG_TABLE_NAMESPACE("debezium.sink.iceberg.table-namespace", "default", ""),
	DEBEZIUM_SINK_ICEBERG_HIVE_OTHER_CONFIGS("debezium.sink.iceberg.hive.other.configs", null, ""),
	DEBEZIUM_SINK_ICEBERG_ENGINE_HIVE_ENABLED("debezium.sink.iceberg.engine.hive.enabled", true, ""),
	DEBEZIUM_SINK_ICEBERG_UPSERT_KEEP_DELETES("debezium.sink.iceberg.upsert-keep-deletes", false, ""),
	DEBEZIUM_TRANSFORMS_UNWRAP_DROP_TOMBSTONES("debezium.transforms.unwrap.drop.tombstones", true, ""),
	DEBEZIUM_TRANSFORMS_UNWRAP_ADD_FIELDS("debezium.transforms.unwrap.add.fields", "op,source.ts_ms", ""),
	DEBEZIUM_SINK_ICEBERG_WRITE_FORMAT_DEFAULT("debezium.sink.iceberg.write.format.default", "Parquet", ""),
	DEBEZIUM_SINK_ICEBERG_FS_S3A_PATH_STYLE_ACCESS("debezium.sink.iceberg.fs.s3a.path.style.access", true, ""),
	DEBEZIUM_SINK_BATCH_BATCH_SIZE_WAIT_MAX_WAIT_MS("debezium.sink.batch.batch-size-wait.max-wait-ms", null, ""),
	DEBEZIUM_SOURCE_OFFSET_STORAGE_FILE_FILENAME("debezium.source.offset.storage.file.filename", "/tmp/offset", ""),
	DEBEZIUM_SINK_ICEBERG_ICEBERG_ENGINE_HIVE_ENABLED("debezium.sink.iceberg.iceberg.engine.hive.enabled", true, ""),
	DEBEZIUM_TRANSFORMS_UNWRAP_DELETE_HANDLING_MODE("debezium.transforms.unwrap.delete.handling.mode", "rewrite", ""),
	DEBEZIUM_SOURCE_SCHEMA_HISTORY_INTERNAL_KAFKA_TOPIC("debezium.source.schema.history.internal.kafka.topic", null, ""),
	DEBEZIUM_SINK_ICEBERG_FS_S3A_IMPL("debezium.sink.iceberg.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem", ""),
	DEBEZIUM_TRANSFORMS_UNWRAP_TYPE("debezium.transforms.unwrap.type", "io.debezium.transforms.ExtractNewRecordState", ""),
	DEBEZIUM_SINK_BATCH_BATCH_SIZE_WAIT_WAIT_INTERVAL_MS("debezium.sink.batch.batch-size-wait.wait-interval-ms", null, ""),
	DEBEZIUM_SINK_ICEBERG_CATALOG_IO_IMPL("debezium.sink.iceberg.catalog.io-impl", "org.apache.iceberg.aws.s3.S3FileIO", ""),
	DEBEZIUM_SINK_ICEBERG_COM_AMAZONAWS_SERVICES_S3_ENABLE_V4("debezium.sink.iceberg.com.amazonaws.services.s3.enableV4", true, ""),
	DEBEZIUM_SINK_ICEBERG_COM_AMAZONAWS_SERVICES_S3A_ENABLE_V4("debezium.sink.iceberg.com.amazonaws.services.s3a.enableV4", true, ""),
	DEBEZIUM_SOURCE_SCHEMA_HISTORY_INTERNAL_KAFKA_BOOTSTRAP_SERVERS("debezium.source.schema.history.internal.kafka.bootstrap.servers", null, ""),
	DEBEZIUM_SINK_ICEBERG_FS_S3A_AWS_CREDENTIALS_PROVIDER("debezium.sink.iceberg.fs.s3a.aws.credentials.provider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider", ""),
	DEBEZIUM_SOURCE_SCHEMA_HISTORY_INTERNAL_STORE_ONLY_CAPTURED_TABLES_DDL("debezium.source.schema.history.internal.store.only.captured.tables.ddl", true, "");

	private final String key;
	private final Object defaultValue;
	private final String description;

	public static DebeziumConstant of(String key) {
		for (DebeziumConstant constant : DebeziumConstant.values()) {
			if (constant.getKey().equals(key)) {
				return constant;
			}
		}
		return null;
	}

	DebeziumConstant(String key, Object defaultValue, String description) {
		this.key = key;
		this.defaultValue = defaultValue;
		this.description = description;
	}
}
