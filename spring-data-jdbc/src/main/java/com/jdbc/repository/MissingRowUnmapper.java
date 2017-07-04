package com.jdbc.repository;

import java.util.Map;

public class MissingRowUnmapper<T> implements RowUnmapper<T> {

    public Map<String, Object> mapColumns(Object o) {
		throw new UnsupportedOperationException("This repository is read-only, it can't store or update entities");
	}
}
