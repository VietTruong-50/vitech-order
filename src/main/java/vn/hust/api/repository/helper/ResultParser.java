package vn.hust.api.repository.helper;

public interface ResultParser<T> {
	T parse(Object[] r);
}
