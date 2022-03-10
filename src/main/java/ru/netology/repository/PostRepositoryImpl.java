package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepositoryImpl {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private long newId = 1L;


    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(newId);
            posts.putIfAbsent(newId, post);
            newId++;
        } else if (post.getId() < newId && post.getId() > 0) {
            posts.replace(post.getId(), post);
        } else {
            throw new NotFoundException("Post not found");
        }
        if (!post.isRemoved()) {
            return post;
        } else {
            throw new NotFoundException("Post has been removed");
        }
    }

    public void removeById(long id) {
        posts.get(id).setRemoved(true);
    }

    //    public List<Post> all() {
//        return new ArrayList<>(posts.values());
//    }
//
//    public Optional<Post> getById(long id) {
//        if(posts.containsKey(id)){
//            return Optional.of(posts.get(id));
//        } else{
//            throw new NotFoundException("Post not found");
//        }
//    }
//
//    public Post save(Post post) {
//        if (post.getId() == 0) {
//            post.setId(newId);
//            posts.putIfAbsent(newId, post);
//            newId++;
//            return post;
//        } else if (post.getId() < newId && post.getId() > 0 && !post.isRemoved()) {
//            posts.replace(post.getId(), post);
//            return post;
//        } else {
//            throw new NotFoundException("Post not found");
//        }
//    }
//
//    public Optional<Post> removeById(long id) {
//        if(posts.containsKey(id)){
//            posts.get(id).setRemoved(true);
//            return Optional.of(posts.get(id));
//        } else{
//            throw new NotFoundException("Post not found");
//        }
//    }
}

