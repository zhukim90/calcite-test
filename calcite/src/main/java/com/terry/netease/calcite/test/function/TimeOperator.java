package com.terry.netease.calcite.test.function;

import java.sql.Date;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.terry.netease.calcite.test.DateFormat;

public class TimeOperator {

	private static final Logger logger = LoggerFactory.getLogger(TimeOperator.class);

	public int THE_YEAR(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		System.out.println("year is :" + cal.get(Calendar.YEAR));
		return cal.get(Calendar.YEAR);
	}
	public int THE_SYEAR(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		System.out.println("year is :" + cal.get(Calendar.YEAR));
		return cal.get(Calendar.YEAR);
	}

	public int THE_YEAR(int date) {
		long mills = (long) date * (1000 * 3600 * 24);
		Date dt = Date.valueOf(DateFormat.formatToDateStr(mills));
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(Calendar.YEAR);
	}

	public Integer THE_MONTH(Date date) {
		return 6;
	}

	public Integer THE_DAY(Date date) {
		return 16;
	}

	public Integer THE_SYEAR(Date date, String type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		logger.info("year is :{} ---->THE_SYEAR" , cal.get(Calendar.YEAR));
		return cal.get(Calendar.YEAR);
	}
}
