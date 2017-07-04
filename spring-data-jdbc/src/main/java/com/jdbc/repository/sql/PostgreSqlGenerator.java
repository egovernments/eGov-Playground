package com.jdbc.repository.sql;

import org.springframework.data.domain.Pageable;

public class PostgreSqlGenerator extends SqlGenerator {
	@Override
	protected String limitClause(Pageable page) {
		final int offset = page.getPageNumber() * page.getPageSize();
		return " LIMIT " + page.getPageSize() + " OFFSET " + offset;
	}
}
