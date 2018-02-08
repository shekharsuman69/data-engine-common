package com.abc.de.config;

/**
 * This class holds all the constants used across different modules of the
 * application.
 * 
 * @author Shekhar Suman
 * @version 1.0
 * @since 2017-03-01
 *
 */
public final class Constants {

	private Constants() {
	}

	// Application constants
	public static final String DATA_ENGINE_PROPERTIES = "spark.data.engine.properties";
	public static final String APPNAME = "app.name";
	public static final String DURATION = "duration";

	// Kafka constants
	public static final String KAFKA_BROKER = "kafka.broker";
	public static final String KAFKA_TOPIC = "kafka.topic";
	public static final String KAFKA_GROUP_ID = "kafka.group.id";
	public static final String GROUP_ID = "group.id";
	public static final String BROKER_LIST = "metadata.broker.list";
	public static final String KEY_DESER = "key.deserializer";
	public static final String VAL_DESER = "value.deserializer";

	// Zookeeper constants
	public static final String ZOOKEEPER_QUORUM = "zookeeper.quorum";
	public static final String ZOOKEEPER_PORT = "zookeeper.port";
	public static final String ZOOKEEPER_ROOT = "zookeeper.znode.parent";

	public static final String META_TABLE = "meta.table";

	// Phoenix constants
	public static final String PHOENIX_DRIVER = "org.apache.phoenix.jdbc.PhoenixDriver";
	public static final String PHOENIX_JDBC_URL = "phoenix.jdbc.url";

	// HBase constants
	public static final int DEFAULT_HBASE_BATCH_SIZE = 250;
	public static final long DEFAULT_HBASE_BATCH_INTERVAL = 500L;
	public static final int MAX_HBASE_SCAN_SIZE = 100000;
	public static final String CONFIG_HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	public static final String CONFIG_HBASE_ZOOKEEPER_PORT = "hbase.zookeeper.property.clientport";
	public static final String CONFIG_HBASE_ZOOKEEPER_ROOT = "zookeeper.znode.parent";
	public static final String DEFAULT_HBASE_ZOOKEEPER_PORT = "2181";
	public static final String DEFAULT_HBASE_ZOOKEEPER_ROOT = "/hbase-unsecure";
	public static final String DEFAULT_HBASE_TABLE_NAME = "MasterLogs";
	public static final String CONFIG_HBASE_TABLE_NAME = "hbase.table.name";
	public static final String CONFIG_ENVIRONMENT = "env";
	public static final String CONFIG_APPNAME = "appname";

	public static final String CONFIG_BUCKET_ID = "bucketid";
	public static final String CONFIG_HOST_NAME = "host";
	public static final String CONFIG_METRICS_HOST = "hostmetrics";
	public static final String CONFIG_METRICS_JVM = "jvmmetrics";
	public static final String CONFIG_METRICS_APMPATH = "apmpathmetrics";
	public static final String CONFIG_METRICS_DATASOURCE = "datasourcemetrics";
	public static final String CONFIG_METRIC_NAME = "metricname";
	public static final String CONFIG_INSTANCE = "instance";
	public static final String CONFIG_COUNT = "count";
	public static final String CONFIG_AVERAGE = "avg";
	public static final String CONFIG_MAX = "max";
	public static final String CONFIG_MIN = "min";
	public static final String CONFIG_TOTAL = "total";
	public static final String CONFIG_APM_PATH = "apmpath";

	public static final String METRICS_TABLE_NAME = "<METRICS_TABLE>";
	public static final String CONFIG_TABLE_NAME_METRICS = "table.name.metrics";
}