package vn.hust.common.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class PagingOut<T> {

	private int page;

	private int maxSize;

	private long totalElement;

	private long totalPages;

	private String sort;

	private String propertiesSort;

	private List<T> data;

	public static <T> PagingOut<T> of(Number total, PageIn pagingDtoIn, List<T> rs) {
		long defaultTotal = Optional.of(total.longValue()).orElse(0L);
		long totalPages = (defaultTotal + pagingDtoIn.getMaxSize() - 1) / pagingDtoIn.getMaxSize();

		PagingOut<T> pagingDTO = new PagingOut<>();
		pagingDTO.setPage(pagingDtoIn.getPage());
		pagingDTO.setMaxSize(pagingDtoIn.getMaxSize());
		pagingDTO.setTotalPages(totalPages);
		pagingDTO.setTotalElement(defaultTotal);

		if (pagingDtoIn.getPage() > totalPages) {
			pagingDTO.setData(new ArrayList<>());
		} else {
			pagingDTO.setData(rs);
		}

		return pagingDTO;
	}

}
