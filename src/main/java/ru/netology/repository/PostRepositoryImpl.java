package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private long newId = 1L;

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(newId);
            posts.putIfAbsent(newId, post);
            newId++;
        } else if (post.getId() < newId && post.getId() > 0) {
            posts.replace(post.getId(), post);
        } else {
            throw new NotFoundException("Пост не найден");
        }
        return post;
    }

    public Optional<Post> removeById(long id) {
        return Optional.of(posts.remove(id));
    }
}

