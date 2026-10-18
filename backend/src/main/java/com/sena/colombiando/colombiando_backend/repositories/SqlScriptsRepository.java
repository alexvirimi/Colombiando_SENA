package com.sena.colombiando.colombiando_backend.repositories;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SqlScriptsRepository {
    private final String tableName;

/** Inicializa la instancia.
 * @param tableName parametro de entrada.
 */
    public SqlScriptsRepository(String tableName) {
        this.tableName = tableName;
    }

/** Ejecuta la operacion column list.
 * @param columns parametro de entrada.
 * @return resultado de la operacion.
 */
    private String columnList(String... columns) {
        return String.join(",", columns);
    }

/** Ejecuta la operacion placeholder list.
 * @param columns parametro de entrada.
 * @return resultado de la operacion.
 */
    private String placeholderList(String... columns) {
        String placeholders = "?, ".repeat(columns.length);
        return placeholders.substring(0, placeholders.length() - 2);
    }

/** Actualiza column list.
 * @param columns parametro de entrada.
 * @return resultado de la operacion.
 */
    private String updateColumnList(String... columns) {
        return Arrays.stream(columns)
                .map(column -> column + " = ?")
                .collect(Collectors.joining(", "));
    }

/** Inserta sql.
 * @param columns parametro de entrada.
 * @return resultado de la operacion.
 */
    public String insertSql(String... columns) {
        return "INSERT INTO " + this.tableName +
                " (" + columnList(columns) + ")" +
                " VALUES (" + placeholderList(columns) + ")";
    }

/** Actualiza sql.
 * @param condition_column parametro de entrada.
 * @param columns_to_update parametro de entrada.
 * @return resultado de la operacion.
 */
    public String updateSql(
            String condition_column,
            String... columns_to_update
    ) {
        return "UPDATE " + this.tableName +
                "SET " + updateColumnList(columns_to_update) +
                " WHERE " + condition_column + " = ?";
    }

/** Elimina by id sql.
 * @return resultado de la operacion.
 */
    public String deleteByIdSql() {
        return "DELETE FROM " + this.tableName +
                " WHERE id = ?";
    }

/** Consulta select by id sql.
 * @param columns parametro de entrada.
 * @return resultado de la operacion.
 */
    public String selectByIdSql(String... columns) {
        return "SELECT " + columnList(columns) +
                " FROM " + this.tableName +
                " WHERE id = ?";
    }

/** Consulta select all sql.
 * @param columns parametro de entrada.
 * @return resultado de la operacion.
 */
    public String selectAllSql(String... columns) {
        return "SELECT " + columnList(columns) +
                " FROM " + this.tableName;
    }

}
