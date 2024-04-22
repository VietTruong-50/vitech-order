package vn.vnpt.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.vnpt.common.annotation.SortType;

@Data
@EqualsAndHashCode(callSuper = true)
public class SortPageIn extends PageIn {

	@SortType
	private String sort = "asc";

	private String propertiesSort;

}
