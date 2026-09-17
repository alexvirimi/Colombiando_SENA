package com.sena.colombiando.colombiando_backend.repositories;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SqlScriptsRepository {
    private final String tableName;

    public SqlScriptsRepository(String tableName) {
        this.tableName = tableName;
    }

    private String columnList(String... columns) {
        return String.join(",", columns);
    }

    private String placeholderList(String... columns) {
        String placeholders = "?, ".repeat(columns.length);
        return placeholders.substring(0, placeholders.length() - 2);
    }

    private String updateColumnList(String... columns) {
        return Arrays.stream(columns)
                .map(column -> column + " = ?")
                .collect(Collectors.joining(", "));
    }

    public String insertSql(String... columns) {
        return "INSERT INTO " + this.tableName +
                " (" + columnList(columns) + ")" +
                " VALUES (" + placeholderList(columns) + ")";
    }

    public String updateSql(
            String condition_column,
            String... columns_to_update
    ) {
        return "UPDATE " + this.tableName +
                "SET " + updateColumnList(columns_to_update) +
                " WHERE " + condition_column + " = ?";
    }

    public String deleteByIdSql() {
        return "DELETE FROM " + this.tableName +
                " WHERE id = ?";
    }

    public String selectByIdSql(String... columns) {
        return "SELECT " + columnList(columns) +
                " FROM " + this.tableName +
                " WHERE id = ?";
    }

    public String selectAllSql(String... columns) {
        return "SELECT " + columnList(columns) +
                " FROM " + this.tableName;
    }

}
