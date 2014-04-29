package mine.mythos.base;

import java.util.List;

import mine.mythos.vo.Page;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unchecked")
public class BaseDao<T extends BaseEntity> {

	@Autowired
	private SessionFactory sessionFactory;

	public Query createQuery(String queryString, Object... values) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public List<T> find(String hql, Object... values) {
		return createQuery(hql, values).list();
	}

	public <X> X findUnique(String hql, Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	public List<T> findAll(String entity) {
		return createQuery("from " + entity).list();
	}

	protected Long countHqlResult(String hql, Object... values) {
		String countHql = prepareCountHql(hql);
		return findUnique(countHql, values);
	}

	private String prepareCountHql(String hql) {
		hql = "from " + StringUtils.substringAfter(hql, "from");
		hql = StringUtils.substringBefore(hql, "order by");
		String countHql = "select count(*) " + hql;
		return countHql;
	}

	public Page<T> findPage(Page<T> page, String hql, Object... values) {
		Query query = createQuery(hql, values);
		long totalCount = countHqlResult(hql, values);
		page.setTotalCount(totalCount);
		query.setFirstResult((page.getPageNo() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		if (totalCount % page.getPageSize() == 0) {
			page.setTotalPages(totalCount / page.getPageSize());
		} else {
			page.setTotalPages(totalCount / page.getPageSize() + 1);
		}
		page.setResult(query.list());
		return page;
	}

	public void saveOrUpdate(T entity) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(entity);
	}

	public void delete(T entity) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(entity);
	}
}
