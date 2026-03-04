package com.cloudpawprint.server;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final JdbcTemplate jdbc;

    public AuthController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (body == null) {
                result.put("code", 400);
                result.put("message", "request body is empty");
                return result;
            }
            Object openidObj = body.get("openid");
            if (openidObj == null || openidObj.toString().isBlank()) {
                result.put("code", 400);
                result.put("message", "openid required");
                return result;
            }
            String openid = openidObj.toString();
            String nickname = body.getOrDefault("nickname", "").toString();

            // safer check: queryForList / SqlRowSet instead of queryForObject
            SqlRowSet rs = jdbc.queryForRowSet("SELECT id FROM user WHERE openid = ? AND is_deleted = 0 LIMIT 1", openid);
            Long userId = null;
            if (rs.next()) {
                userId = rs.getLong("id");
            }

            if (userId == null) {
                // insert
                jdbc.update("INSERT INTO user (openid, nickname) VALUES (?, ?)", openid, nickname);
                userId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            }

            result.put("code", 200);
            result.put("userId", userId);
            return result;

        } catch (Exception ex) {
            // return error info but don't expose stacktrace to client
            result.put("code", 500);
            result.put("message", "server error: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
            // also print stacktrace to console for debugging
            ex.printStackTrace();
            return result;
        }
    }
}