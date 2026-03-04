package com.cloudpawprint.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/posts")
    public List<Map<String, Object>> listPosts(@RequestParam(defaultValue="0") int page,
                                               @RequestParam(defaultValue="20") int size) {
        int offset = page * size;
        return jdbc.queryForList("SELECT p.id, p.user_id, p.pet_name, p.media_url, p.caption, p.like_count FROM pet_post p ORDER BY p.created_at DESC LIMIT ? OFFSET ?",
                new Object[]{size, offset});
    }

    @PostMapping("/posts")
    public Map<String, Object> createPost(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        String mediaUrl = body.get("media_url").toString();
        String caption = body.getOrDefault("caption","").toString();
        String petName = body.getOrDefault("pet_name", "").toString();
        jdbc.update("INSERT INTO pet_post (user_id, pet_name, media_url, caption) VALUES (?, ?, ?, ?)",
                userId, petName, mediaUrl, caption);
        Long id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return Map.of("code",200,"postId", id);
    }
}