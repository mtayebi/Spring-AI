package com.example.demo.server.tools;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DatabaseTools {

    private final JdbcTemplate jdbc;

    @Value("${default.schema}")
    private String schema;
    @Value("${default.table}")
    private String table;

    public DatabaseTools(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @McpTool(
            name = "databaseSchema",
            description = "Get the database tables and columns."
    )
    public List<Map<String, Object>> schema() {

        return jdbc.queryForList("""
                SELECT table_name, column_name, data_type
                FROM information_schema.columns
                WHERE table_schema = ?
                """, schema);
    }

    @McpTool(
            name = "queryDatabase",
            description = "Execute a SQL select query. with high privilege"
    )
    public List<Map<String, Object>> queryDatabase(String sql) {
        return jdbc.queryForList(sql);
    }

    @McpTool(
            name = "transferMoney",
            description = "Transfer money between accounts." +
                    " This is a sensitive operation and requires authentication(password)."
    )
    public List<Map<String, Object>> transferMoney(String sql, String password, Long userId) {
        List<Map<String, Object>> users = jdbc.queryForList(
                "SELECT * FROM users WHERE id = ? AND password = ?",
                userId,
                password
        );
        users.stream().findFirst().orElseThrow(() -> new RuntimeException("user not found"));
        return jdbc.queryForList(sql);
    }

    @McpTool(
            name = "depositMoney",
            description = "Deposit money into an account. " +
                    "This is a sensitive operation and requires authentication(password)."
    )
    public List<Map<String, Object>> depositMoney(String sql, String password, Long userId) {
        List<Map<String, Object>> users = jdbc.queryForList(
                "SELECT * FROM users WHERE id = ? AND password = ?",
                userId,
                password
        );
        users.stream().findFirst().orElseThrow(() -> new RuntimeException("user not found"));
        return jdbc.queryForList(sql);
    }

    //    more secure
    //  1. Don't expose a generic execute_sql

//    @McpTool(
//            name = "query_database_read_only",
//            description = "Execute a read-only SQL SELECT query against the cafe database."
//    )
//    public List<Map<String, Object>> queryDatabaseSecure(String sql) {
//
//        if (!sql.trim().toLowerCase().startsWith("select")) {
//            throw new IllegalArgumentException("Only SELECT queries are allowed");
//        }
//
//        return jdbc.queryForList(sql);
//    }

   //    2. Use a PostgreSQL read-only user


    /*
    * 3. Don't let the model choose arbitrary tables
    *SELECT table_name, column_name, data_type
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name IN ('orders');
    * */


    /*
    * 4. Validate generated SQL
    *
    String normalized = sql.trim().toLowerCase();

    if (!normalized.startsWith("select")) {
        throw new IllegalArgumentException("Only SELECT statements are allowed");
    }

    if (normalized.contains(";")) {
        throw new IllegalArgumentException("Multiple SQL statements are not allowed");
    }

    *
    * */
    /*
    6. MCP authentication
    @PreAuthorize("hasRole('AI_SERVICE')")
    * # MCP server :1010
    spring:
      datasource:
        username: cafe_admin
        password: ${DB_PASSWORD}
    * */

    /*
    * make transaction record and check each time
    * */
}