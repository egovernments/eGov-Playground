package com.jdbc.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.util.Assert;

import com.jdbc.repository.sql.SqlGenerator;

/**
 * Implementation of {@link PagingAndSortingRepository} using {@link JdbcTemplate}
 */
public abstract class JdbcRepository<T extends Persistable<ID>, ID extends Serializable> implements PagingAndSortingRepository<T, ID>, InitializingBean, BeanFactoryAware {

	public static Object[] pk(Object... idValues) {
		return idValues;
	}
	private final TableDescription table;


	private final RowMapper<T> rowMapper;
	private final RowUnmapper<T> rowUnmapper;

	private SqlGenerator sqlGenerator;
	private BeanFactory beanFactory;
	private JdbcOperations jdbcOperations;

	public JdbcRepository(RowMapper<T> rowMapper, RowUnmapper<T> rowUnmapper, SqlGenerator sqlGenerator, TableDescription table) {
		Assert.notNull(rowMapper);
		Assert.notNull(rowUnmapper);
		Assert.notNull(table);

		this.rowUnmapper = rowUnmapper;
		this.rowMapper = rowMapper;
		this.sqlGenerator = sqlGenerator;
		this.table = table;
	}

	public JdbcRepository(RowMapper<T> rowMapper, RowUnmapper<T> rowUnmapper, TableDescription table) {
		this(rowMapper, rowUnmapper, null, table);
	}

	public JdbcRepository(RowMapper<T> rowMapper, RowUnmapper<T> rowUnmapper, String tableName, String idColumn) {
		this(rowMapper, rowUnmapper, null, new TableDescription(tableName, idColumn));
	}

	public JdbcRepository(RowMapper<T> rowMapper, RowUnmapper<T> rowUnmapper, String tableName) {
		this(rowMapper, rowUnmapper, new TableDescription(tableName, "id"));
	}

	public JdbcRepository(RowMapper<T> rowMapper, TableDescription table) {
		this(rowMapper, new MissingRowUnmapper<T>(), null, table);
	}

	public JdbcRepository(RowMapper<T> rowMapper, String tableName, String idColumn) {
		this(rowMapper, new MissingRowUnmapper<T>(), null, new TableDescription(tableName, idColumn));
	}

	public JdbcRepository(RowMapper<T> rowMapper, String tableName) {
		this(rowMapper, new MissingRowUnmapper<T>(), new TableDescription(tableName, "id"));
	}

	public void afterPropertiesSet() throws Exception {
		obtainJdbcTemplate();
		if (sqlGenerator == null) {
			obtainSqlGenerator();
		}
	}

	protected JdbcOperations getJdbcOperations() {
		return jdbcOperations;
	}

	protected TableDescription getTable() {
		return table;
	}

	private void obtainSqlGenerator() {
		try {
			sqlGenerator = beanFactory.getBean(SqlGenerator.class);
		} catch (NoSuchBeanDefinitionException e) {
			sqlGenerator = new SqlGenerator();
		}
	}

	private void obtainJdbcTemplate() {
		try {
			jdbcOperations = beanFactory.getBean(JdbcOperations.class);
		} catch (NoSuchBeanDefinitionException e) {
			final DataSource dataSource = beanFactory.getBean(DataSource.class);
			jdbcOperations = new JdbcTemplate(dataSource);
		}
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

//	public long count() {
//		return jdbcOperations.queryForLong(sqlGenerator.count(table));
//	}

	public void delete(ID id) {
		jdbcOperations.update(sqlGenerator.deleteById(table), idToObjectArray(id));
	}

	public void delete(T entity) {
		jdbcOperations.update(sqlGenerator.deleteById(table), entity.getId());
	}

	public void delete(Iterable<? extends T> entities) {
		for (T t : entities) {
			delete(t);
		}
	}

	public void deleteAll() {
		jdbcOperations.update(sqlGenerator.deleteAll(table));
	}

//	public boolean exists(ID id) {
//		return jdbcOperations.queryForInt(sqlGenerator.countById(table), idToObjectArray(id)) > 0;
//	}

	public List<T> findAll() {
		return jdbcOperations.query(sqlGenerator.selectAll(table), rowMapper);
	}

	public T findOne(ID id) {
		final Object[] idColumns = idToObjectArray(id);
		final List<T> entityOrEmpty = jdbcOperations.query(sqlGenerator.selectById(table), idColumns, rowMapper);
		return entityOrEmpty.isEmpty() ? null : entityOrEmpty.get(0);
	}

	private Object[] idToObjectArray(ID id) {
		if (id instanceof Object[])
			return (Object[]) id;
		else
			return new Object[]{id};
	}

	public T save(T entity) {
		if (entity.isNew()) {
			return create(entity);
		} else {
			return update(entity);
		}
	}

	protected T update(T entity) {
		final Map<String, Object> columns = preUpdate(entity, columns(entity));
		final List<Object> idValues = removeIdColumns(columns);
		final String updateQuery = sqlGenerator.update(table, columns);
		for (int i = 0; i < table.getIdColumns().size(); ++i) {
			columns.put(table.getIdColumns().get(i), idValues.get(i));
		}
		final Object[] queryParams = columns.values().toArray();
		jdbcOperations.update(updateQuery, queryParams);
		return postUpdate(entity);
	}

	protected Map<String,Object> preUpdate(T entity, Map<String, Object> columns) {
		return columns;
	}

	protected T create(T entity) {
		final Map<String, Object> columns = preCreate(columns(entity), entity);
		if (entity.getId() == null) {
			return createWithAutoGeneratedKey(entity, columns);
		} else {
			return createWithManuallyAssignedKey(entity, columns);
		}
	}

	private T createWithManuallyAssignedKey(T entity, Map<String, Object> columns) {
		final String createQuery = sqlGenerator.create(table, columns);
		final Object[] queryParams = columns.values().toArray();
		jdbcOperations.update(createQuery, queryParams);
		return postCreate(entity, null);
	}

	private T createWithAutoGeneratedKey(T entity, Map<String, Object> columns) {
		removeIdColumns(columns);
		final String createQuery = sqlGenerator.create(table, columns);
		final Object[] queryParams = columns.values().toArray();
		final GeneratedKeyHolder key = new GeneratedKeyHolder();
		jdbcOperations.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				final String idColumnName = table.getIdColumns().get(0);
				final PreparedStatement ps = con.prepareStatement(createQuery, new String[]{idColumnName});
				for (int i = 0; i < queryParams.length; ++i) {
					ps.setObject(i + 1, queryParams[i]);
				}
				return ps;
			}
		}, key);
		return postCreate(entity, key.getKey());
	}

	private List<Object> removeIdColumns(Map<String, Object> columns) {
		List<Object> idColumnsValues = new ArrayList<Object>(columns.size());
		for (String idColumn : table.getIdColumns()) {
			idColumnsValues.add(columns.remove(idColumn));
		}
		return idColumnsValues;
	}

	protected Map<String, Object> preCreate(Map<String, Object> columns, T entity) {
		return columns;
	}

	private LinkedHashMap<String, Object> columns(T entity) {
		return new LinkedHashMap<String, Object>(rowUnmapper.mapColumns(entity));
	}

	protected T postUpdate(T entity) {
		return entity;
	}

	/**
	 * General purpose hook method that is called every time {@link #create} is called with a new entity.
	 * OVerride this method e.g. if you want to fetch auto-generated key from database
	 *
	 *
	 * @param entity Entity that was passed to {@link #create}
	 * @param generatedId ID generated during INSERT or NULL if not available/not generated.
	 * todo: Type should be ID, not Number
	 * @return Either the same object as an argument or completely different one
	 */
	protected T postCreate(T entity, Number generatedId) {
		return entity;
	}
	
	public List<T> findAll(Sort sort) {
		return jdbcOperations.query(sqlGenerator.selectAll(table, sort), rowMapper);
	}

	public Page<T> findAll(Pageable page) {
		String query = sqlGenerator.selectAll(table, page);
		return new PageImpl<T>(jdbcOperations.query(query, rowMapper), page, count());
	}

}


