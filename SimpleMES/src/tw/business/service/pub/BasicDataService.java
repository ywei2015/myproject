package tw.business.service.pub;

import java.util.List;

public interface BasicDataService<T> {
	public List<T> getList(T t);
	public T get(T t);
	public void update(T t);
	public void delete (T t);
	
	public T save(T t);
}
