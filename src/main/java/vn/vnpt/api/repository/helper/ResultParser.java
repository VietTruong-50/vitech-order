package vn.vnpt.api.repository.helper;

public interface ResultParser<T> {
	T parse(Object[] r);
}
