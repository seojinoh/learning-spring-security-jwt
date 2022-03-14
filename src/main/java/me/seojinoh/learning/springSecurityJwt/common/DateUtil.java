package me.seojinoh.learning.springSecurityJwt.common;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

	@Value("${date.timezone}")	private String DATE_TIMEZONE;
	@Value("${date.format}")	private String DATE_FORMAT;

	public String getNowDt() {
		ZonedDateTime nowDt = ZonedDateTime.now(ZoneId.of(DATE_TIMEZONE));

		return nowDt.format(getDateTimeFormatter());
	}

	private DateTimeFormatter getDateTimeFormatter() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

		return dateTimeFormatter;
	}

}
