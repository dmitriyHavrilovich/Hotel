package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.Post;

/**
 * Created by Mahaon on 09.05.2017.
 */
public interface PostRepository extends CrudRepository<Post, Long>{
}
