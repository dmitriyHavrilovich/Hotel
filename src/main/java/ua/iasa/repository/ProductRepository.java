package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.Product;

/**
 * Created by Mahaon on 04.05.2017.
 */
public interface ProductRepository extends CrudRepository<Product, Long>{
}
