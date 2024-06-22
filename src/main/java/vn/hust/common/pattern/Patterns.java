package vn.hust.common.pattern;

public class Patterns {

	// parten dd/mm/yyyy
	public static final String IDG_DATE_DD_MM_YYYY = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
			+ "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)"
			+ "0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
			+ "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)"
			+ "(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

	public static final String IDG_YYYY = "[\\d]{4}";

	public static final String IDG_DATE = "dd/MM/yyyy";

	public static final String IDG_DATE_TIME = "dd/MM/yyyy HH:mm:ss";

	public static final String IDG_DATE_TIME_DB = "yyyy-MM-dd HH:mm:ss";

	public static final String IDG_HOUR_MINUTES = "^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])?$";

	public static final String IDG_SEX = "^[0-2]$";

	public static final String IDG_CAN_NANG = "\\d+(\\.\\d{1,2})?";

	public static final String IDG_CON_THU = "^\\d{1,2}$";

	public static final String IDG_LAN_SINH = "^\\d{1,2}$";

	public static final String IDG_SO = "^\\d{1,6}$";

	public static final String IDG_CMT = "^\\d{9,11}$";

	public static final String IDG_EMAIL = ".+@.+\\.[a-z]+";

	public static final String IDG_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[^\\s]{8,}$";

	public static final String IDG_MSISDN = "^(84)(3|5|7|8|9)\\d{8}$";

	public static final String IDG_NAME_PROJECT = "[^A-Za-z0-9|-]";

	public static final String IDG_DAY_REQUEST = "^\\d{2}/\\d{2}/\\d{4}$";

	public static final String IDG_TTS_ORIGINAL_TEXT = "^[^ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";

	public static final String IDG_DOMAIN = "^(http(s)?://)?[a-zA-Z0-9-]{1,253}\\.[a-zA-Z0-9-]{2,6}(\\.[a-zA-Z0-9-]{2,6})?$";

	public static final String IDG_CATALOG_SCHEMA_NAME = "^[\\w_\\d]+$";

	public static final String IS_NUMBER = "^-?\\d+(\\.\\d+)?$";
}