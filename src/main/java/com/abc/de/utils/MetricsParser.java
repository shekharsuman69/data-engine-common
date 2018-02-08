package com.abc.de.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.abc.de.config.Constants;
import com.abc.de.vo.MetricsData;

/**
 *
 * Class object that parses and stores metric data from JSON format.
 * 
 * @author Shekhar Suman
 * @version 1.0
 * @since 2017-03-01
 */
public class MetricsParser implements Serializable {

	private static final long serialVersionUID = 4826651709614602433L;
	/**
	 * 2 Level Map of collection of metrics Key: {(bucket ID) | (host name) |
	 * (jvm name)[optional]} Value: Map containing metric name as key and
	 * metrics data as value
	 */
	private final Map<String, Map<String, MetricsData>> metricsCollection;
	private final Map<String, Map<String, List<JSONObject>>> dsMetricsCollection;

	/**
	 * Constructor for the metrics parser that initiates the metrics collection
	 * hashmap
	 */
	public MetricsParser() {
		metricsCollection = new HashMap<String, Map<String, MetricsData>>();
		dsMetricsCollection = new HashMap<String, Map<String, List<JSONObject>>>();
	}

	/**
	 * Parses the json input and stores the data in the metrics collection
	 * hashmap
	 *
	 * @param json
	 *            json object to be parsed
	 */
	@SuppressWarnings("rawtypes")
	public void parse(String json) throws Exception {
		try {
			// create a JSON object
			JSONParser parser = new JSONParser();
			Map jsonObject = (Map) parser.parse(json);
			// get bucket ID
			String bucketID = (String) jsonObject.get(Constants.CONFIG_BUCKET_ID);
			// get host name
			String hostName = (String) jsonObject.get(Constants.CONFIG_HOST_NAME);
			String env = (String) jsonObject.get(Constants.CONFIG_ENVIRONMENT);
			String app = (String) jsonObject.get(Constants.CONFIG_APPNAME);

			// System.out.println("[ bucket ID: " + bucketID + " ]");
			// System.out.println("[ host name: " + hostName + " ]");
			JSONArray jsonArrayHost = null;
			JSONArray jsonArrayJVM = null;
			JSONArray jsonArrayAPMPath = null;
			JSONArray jsonArrayDS = null;
			try {
				// see if host metrics exist in the json
				jsonArrayHost = (JSONArray) jsonObject.get(Constants.CONFIG_METRICS_HOST);
			} catch (Exception e) {
				System.err.println("Host metrics don't exist: " + e.getMessage());
			}
			try {
				// see if jvm metrics exist in the json
				jsonArrayJVM = (JSONArray) jsonObject.get(Constants.CONFIG_METRICS_JVM);
			} catch (Exception e) {
				System.err.println("JVM metrics don't exist: " + e.getMessage());
			}
			try {
				// see if host metrics exist in the json
				jsonArrayAPMPath = (JSONArray) jsonObject.get(Constants.CONFIG_METRICS_APMPATH);
			} catch (Exception e) {
				System.err.println("APMPath metrics don't exist: " + e.getMessage());
			}
			try {
				jsonArrayDS = (JSONArray) jsonObject.get(Constants.CONFIG_METRICS_DATASOURCE);
			} catch (Exception e) {
				System.err.println("Datasource metrics don't exist: " + e.getMessage());
			}
			// if jvm metrics exist
			if (jsonArrayJVM != null) {
				// create a map reference to store jvm metrics just in case more
				// are found in the json array
				Map<String, Map<String, MetricsData>> mapReference = new HashMap<String, Map<String, MetricsData>>();
				// map of the jvm metrics
				Map<String, MetricsData> jvmMetrics = null;
				String metricName;
				String instance;
				MetricsData metricsData;
				JSONObject jvmMetric;
				// iterate through the json array
				for (int i = 0; i < jsonArrayJVM.size(); i++) {
					// grab the json object and extract the data from it
					// jvmMetric = jsonArrayJVM.getJSONObject(i);
					jvmMetric = (JSONObject) jsonArrayJVM.get(i);
					metricName = (String) jvmMetric.get(Constants.CONFIG_METRIC_NAME);
					instance = (String) jvmMetric.get(Constants.CONFIG_INSTANCE);
					metricsData = new MetricsData(metricName, hostName, instance);
					metricsData.setCalculateAverage(false);
					metricsData.setCount((Long) jvmMetric.get(Constants.CONFIG_COUNT));
					metricsData.setAvg((Double) jvmMetric.get(Constants.CONFIG_AVERAGE));
					metricsData.setMax((Double) jvmMetric.get(Constants.CONFIG_MAX));
					metricsData.setMin((Double) jvmMetric.get(Constants.CONFIG_MIN));
					metricsData.setTotal((Double) jvmMetric.get(Constants.CONFIG_TOTAL));
					String evnInternal = null;
					if ((String) jvmMetric.get(Constants.CONFIG_ENVIRONMENT) != null)
						evnInternal = (String) jvmMetric.get(Constants.CONFIG_ENVIRONMENT);
					else
						evnInternal = env;
					metricsData.setEnvironment(evnInternal);
					String appInternal = null;
					if ((String) jvmMetric.get(Constants.CONFIG_APPNAME) != null)
						appInternal = (String) jvmMetric.get(Constants.CONFIG_APPNAME);
					else
						appInternal = app;
					metricsData.setApp(appInternal);
					// if the map reference contains the jvm name alread, add it
					// to that map
					if (mapReference.containsKey(instance)) {
						jvmMetrics = mapReference.get(instance);
						jvmMetrics.put(metricName, metricsData);
					}
					// else create a new map, add the metrics to it and store it
					// in the map reference
					else {
						jvmMetrics = new HashMap<String, MetricsData>();
						jvmMetrics.put(metricName, metricsData);
						mapReference.put(instance, jvmMetrics);
					}
				}
				// map reference is finished populating, store it in the metric
				// collection map
				Set<String> keySet = mapReference.keySet();
				for (String key : keySet) {
					metricsCollection.put(bucketID + '|' + hostName + '|' + key, mapReference.get(key));
				}
			}

			// if host metrics exist
			if (jsonArrayHost != null) {
				// create a map that stores all the host metrics in metrics data
				// objects
				Map<String, MetricsData> hostMetrics = new HashMap<String, MetricsData>();
				String metricName;
				MetricsData metricsData;
				JSONObject hostMetric;
				for (int i = 0; i < jsonArrayHost.size(); i++) {
					// get the json objects and extract all the data from it,
					// storing it in a metrics data object
					hostMetric = (JSONObject) jsonArrayHost.get(i);
					metricName = (String) hostMetric.get(Constants.CONFIG_METRIC_NAME);
					metricsData = new MetricsData(metricName, hostName, null);
					metricsData.setCalculateAverage(false);
					metricsData.setCount((Long) hostMetric.get(Constants.CONFIG_COUNT));
					metricsData.setAvg((Double) hostMetric.get(Constants.CONFIG_AVERAGE));
					metricsData.setMax((Double) hostMetric.get(Constants.CONFIG_MAX));
					metricsData.setMin((Double) hostMetric.get(Constants.CONFIG_MIN));
					metricsData.setTotal((Double) hostMetric.get(Constants.CONFIG_TOTAL));
					metricsData.setEnvironment((String) hostMetric.get(Constants.CONFIG_ENVIRONMENT));
					// store the host metrics in the host metrics map
					hostMetrics.put(metricName, metricsData);

				}
				// Once the host metrics map has been populated, store it in the
				// metrics collection
				metricsCollection.put(bucketID + '|' + hostName, hostMetrics);

			}

			// if apmpath metrics exist
			if (jsonArrayAPMPath != null) {
				// create a map reference to store apmpath metrics just in case
				// more
				// are found in the json array
				Map<String, Map<String, MetricsData>> mapReference = new HashMap<String, Map<String, MetricsData>>();
				// map of the jvm metrics
				Map<String, MetricsData> apmpathMetrics = null;
				Map<String, String> appMapping = new HashMap<String, String>();
				String metricName;
				String instance;
				String apmpath;
				MetricsData metricsData;
				JSONObject apmpathMetric;
				// iterate through the json array
				for (int i = 0; i < jsonArrayAPMPath.size(); i++) {
					// grab the json object and extract the data from it
					// apmpathMetric = jsonArrayJVM.getJSONObject(i);
					apmpathMetric = (JSONObject) jsonArrayAPMPath.get(i);
					metricName = (String) apmpathMetric.get(Constants.CONFIG_METRIC_NAME);
					instance = (String) apmpathMetric.get(Constants.CONFIG_INSTANCE);
					apmpath = (String) apmpathMetric.get(Constants.CONFIG_APM_PATH);
					appMapping.put(instance, app);
					metricsData = new MetricsData(metricName, hostName, instance, apmpath);
					metricsData.setCalculateAverage(false);
					metricsData.setCount((Long) apmpathMetric.get(Constants.CONFIG_COUNT));
					metricsData.setAvg((Double) apmpathMetric.get(Constants.CONFIG_AVERAGE));
					metricsData.setMax((Double) apmpathMetric.get(Constants.CONFIG_MAX));
					metricsData.setMin((Double) apmpathMetric.get(Constants.CONFIG_MIN));
					metricsData.setTotal((Double) apmpathMetric.get(Constants.CONFIG_TOTAL));
					metricsData.setEnvironment(env);
					metricsData.setApp(app);
					// if the map reference contains the jvm name alread, add it
					// to that map
					if (mapReference.containsKey(instance)) {
						apmpathMetrics = mapReference.get(instance);
						apmpathMetrics.put(apmpath, metricsData);
					}
					// else create a new map, add the metrics to it and store it
					// in the map reference
					else {
						apmpathMetrics = new HashMap<String, MetricsData>();
						apmpathMetrics.put(apmpath, metricsData);
						mapReference.put(instance, apmpathMetrics);
					}
				}
				// map reference is finished populating, store it in the metric
				// collection map
				Set<String> keySet = mapReference.keySet();
				for (String jvm : keySet) {
					metricsCollection.put(
							bucketID + '|' + env + '|' + Constants.CONFIG_APM_PATH + "|" + appMapping.get(jvm),
							mapReference.get(jvm));
				}
			}
			if (jsonArrayDS != null) {
				// create a map reference to store ds metrics just in case more
				// are found in the json array
				Map<String, Map<String, List<JSONObject>>> mapReference = new HashMap<String, Map<String, List<JSONObject>>>();
				// map of the ds metrics
				Map<String, List<JSONObject>> dsMetrics = null;
				String metricName;
				String instance;
				JSONObject dsMetric;
				// iterate through the json array
				for (int i = 0; i < jsonArrayDS.size(); i++) {
					// grab the json object and extract the data from it
					// jvmMetric = jsonArrayJVM.getJSONObject(i);
					dsMetric = (JSONObject) jsonArrayDS.get(i);
					instance = (String) dsMetric.get(Constants.CONFIG_INSTANCE);
					metricName = (String) dsMetric.get(Constants.CONFIG_METRIC_NAME);
					// if the map reference contains the jvm name already, add
					// it
					// to that map
					List<JSONObject> metric;
					if (mapReference.containsKey(instance)) {
						dsMetrics = mapReference.get(instance);
						metric = dsMetrics.get(metricName);
						if (metric == null)
							metric = new ArrayList<>();
						metric.add(dsMetric);
						dsMetrics.put(metricName, metric);
					}
					// else create a new map, add the metrics to it and store it
					// in the map reference
					else {
						dsMetrics = new HashMap<>();
						metric = new ArrayList<>();
						metric.add(dsMetric);
						dsMetrics.put(metricName, metric);
						mapReference.put(instance, dsMetrics);
					}
				}
				// map reference is finished populating, store it in the metric
				// collection map
				Set<String> keySet = mapReference.keySet();
				for (String key : keySet) {
					dsMetricsCollection.put(bucketID + '|' + hostName + '|' + key, mapReference.get(key));
				}
			}

		} catch (ParseException e) {
			System.err.println("Error parsing JSON string");
			throw new Exception(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

	}

	/**
	 * Getter for the metrics collection map object
	 *
	 * @return the 2 level map collection of metrics
	 */
	public Map<String, Map<String, MetricsData>> getMetricsCollection() {
		return this.metricsCollection;
	}

	public Map<String, Map<String, List<JSONObject>>> getDSMetricsCollection() {
		return this.dsMetricsCollection;
	}

	public void reset() {
		metricsCollection.clear();
		dsMetricsCollection.clear();
	}

	// Main function for testing
	public static void main(String[] args) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
