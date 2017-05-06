package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.Currency;

/**
 * Created by Mahaon on 04.05.2017.
 */
public interface CurrencyRepository extends CrudRepository<Currency, Long>{
}
