package com.abc.de.vo;

import org.json.simple.JSONObject;
/**
 * Metrics data object
 *
 */
public class MetricsData extends BaseMetrics {
	
	private static final long serialVersionUID = -3249688349785265214L;
	
	private String name;
	private String apmpath;
	private String app;
	private String host;
	private String env;
	private String instance = null;
	private String ds;
	private boolean calculateAverage = true;
	
	public MetricsData(String n) {
		this.name = n;
	}
	
	public MetricsData(String n, String h, String i) {
		this.name = n;
		this.host = h;
		this.instance = i;
	}
	
	public MetricsData(String n, String h, String i, String a) {
		this.name = n;
		this.host = h;
		this.instance = i;
		this.apmpath = a;
	}
	
	public MetricsData(String n, String h, String i, String a, String d) {
		this.name = n;
		this.host = h;
		this.instance = i;
		this.apmpath = a;
		this.ds = d;
	}
	
	public MetricsData(String name, String host, String instance, BaseMetrics baseMetrics) {
		this.name = name;
		this.host = host;
		this.instance = instance;
		this.max = baseMetrics.getMax();
		this.min = baseMetrics.getMin();
		this.avg = baseMetrics.getAvg();
		this.total = baseMetrics.getTotal();
		this.count = baseMetrics.getCount();
		calculateAverage = false;
	}
	
	public BaseMetrics toBaseMetrics() {
		BaseMetrics data = new BaseMetrics();
		data.setAvg(getAvg());
		data.setCount(getCount());
		data.setMax(getMax());
		data.setMin(getMin());
		data.setTotal(getTotal());
		return data;
	}
	
	protected void add(double value) {
		total += value;
		count++;
		if(count>1) {
		if(value>max)
			max = value;
		if(value<min)
			min = value;
		} else {
			max = value;
			min = value;
		}
	}
	
	public void setCalculateAverage(boolean calculateAverage) {
		this.calculateAverage = calculateAverage;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	public String getEnvironment() {
		return env;
	}

	public void setEnvironment(String e) {
		this.env = e;
	}
	
	public boolean isAPMPath(){
		return apmpath==null? false:true;
	}

	public String getApmpath() {
		return apmpath;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		JSONObject obj = toJSONObject();
		if(obj==null)
			return "";
		else
			return obj.toJSONString();
	}
	
	public JSONObject toJSONObject() {
//		if(count<1)
//			return null;
		JSONObject obj = new JSONObject();
		obj.put("host", host);
		if(instance!=null)
			obj.put("appservername", instance);
		obj.put("metricname", name);
		if(apmpath!=null)
			obj.put("apmpath", apmpath);
		if(ds!=null)
			obj.put("dsname", ds);
		obj.put("max", max);
		obj.put("min", min);
		obj.put("total", total);
		obj.put("count", count);
		if(env!=null) {
			obj.put("env", env);
		}
		
		if(calculateAverage)
			obj.put("avg", total/(double)count);
		else
			obj.put("avg", avg);
		
		if (app != null) {
			obj.put("appname", app);
		}
		return obj;
	}
}
