package vn.hust.common.model;

import lombok.Data;
import vn.hust.common.annotation.SortType;

@Data
public class SortIn {

	private String keySearch;

	private String propertiesSort;

	@SortType
	private String sort = "asc";

	public String getSort() {
		return sort.trim().toLowerCase();
	}
}
