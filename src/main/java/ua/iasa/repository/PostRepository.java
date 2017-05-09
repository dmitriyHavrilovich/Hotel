package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.Post;

public interface PostRepository extends CrudRepository<Post, Long>{
}
