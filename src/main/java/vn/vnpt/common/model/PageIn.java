package vn.vnpt.common.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class PageIn {

	private int page = 1;

	private int maxSize = 100;

	private String keySearch = StringUtils.EMPTY;

}
