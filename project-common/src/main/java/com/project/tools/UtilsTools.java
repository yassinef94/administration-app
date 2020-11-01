package com.project.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.Data;

@Data
public class UtilsTools {
	
	public boolean isDateValidWithYMD(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.format(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
