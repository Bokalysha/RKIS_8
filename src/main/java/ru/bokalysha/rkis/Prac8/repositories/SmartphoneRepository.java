package ru.bokalysha.rkis.Prac8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bokalysha.rkis.Prac8.models.Smartphone;

import java.util.List;

/**
 * Репозиторий для работы со смартфонами.
 */
@Repository
public interface SmartphoneRepository extends JpaRepository<Smartphone, Integer> {

    /**
     * Поиск смартфонов по цене, равной или большей заданной.
     *
     * @param price Цена смартфонов, относительно которой производится поиск.
     * @return Список смартфонов, у которых цена равна или больше заданной.
     */
    List<Smartphone> findByPriceGreaterThanEqual(float price);

    String example = """
            INSERT INTO smartphone (brand, model, operating_system, storage_capacity_gb, price)
            VALUES
                ('Galaxy A50', 'Samsung', 'Android', 128, 500),
                ('Galaxy A52', 'Samsung', 'Android', 128, 620),
                ('iPhone 14 Pro Max', 'Apple', 'iOS', 128, 1000),
                ('iPhone 15 Pro Max', 'Apple', 'iOS', 256, 1299);
            """;

    /**
     * Вставка примеров смартфонов в базу данных.
     */
    @Modifying
    @Query(value = example, nativeQuery = true)
    void insertExample();
}
