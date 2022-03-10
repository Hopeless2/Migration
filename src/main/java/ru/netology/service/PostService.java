package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService {
    private final PostRepositoryImpl repository;

    public PostService(PostRepositoryImpl repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all().stream()
                .filter(p -> !p.isRemoved())
                .collect(Collectors.toList());
    }

    public Post getById(long id) {
        var post = repository.getById(id).orElseThrow(NotFoundException::new);
        if (!post.isRemoved()) {
            return post;
        } else {
            throw new NotFoundException("Post has been removed");
        }
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public String removeById(long id) {
        repository.removeById(id);
        return "Post removed";
    }
}

