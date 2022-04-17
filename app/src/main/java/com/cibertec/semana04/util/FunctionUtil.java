package com.cibertec.semana04.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FunctionUtil {

	public static int ESTADO_ACTIVO = 1;
	public static int ESTADO_INACTIVO = 0;

	public static String getFechaActualStringDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		return sdf.format(new Date());
	}

	public static String getFechaActualString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String getFechaStringDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static String getFechaString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	


}
