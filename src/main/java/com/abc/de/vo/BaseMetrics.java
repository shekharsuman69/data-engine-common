package com.abc.de.vo;

import java.io.Serializable;

/**
 * Base Metrics object
 *
 * @author Shekhar Suman
 * @version 1.0
 * @since 2017-02-23
 */
public class BaseMetrics implements Serializable {
	private static final long serialVersionUID = -3249688349785265214L;
	protected double max = 0.0;
	protected double min = 0.0;
	protected double avg = 0.0;
	protected double total = 0.0;
	protected long count = 0;

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
