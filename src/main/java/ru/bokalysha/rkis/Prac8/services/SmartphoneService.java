package ru.bokalysha.rkis.Prac8.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bokalysha.rkis.Prac8.models.Smartphone;
import ru.bokalysha.rkis.Prac8.repositories.SmartphoneRepository;

import java.util.List;

/**
 * Сервис для работы со смартфонами.
 */
@Service
@Transactional(readOnly = true)
public class SmartphoneService {

    private final SmartphoneRepository smartphoneRepository;

    /**
     * Конструктор сервиса.
     *
     * @param smartphoneRepository репозиторий смартфонов
     */
    @Autowired
    public SmartphoneService(SmartphoneRepository smartphoneRepository) {
        this.smartphoneRepository = smartphoneRepository;
    }

    /**
     * Получает все смартфоны.
     *
     * @return список смартфонов.
     */
    public List<Smartphone> findAll() {
        return smartphoneRepository.findAll();
    }

    /**
     * Находит смартфон по идентификатору.
     *
     * @param id идентификатор смартфона.
     * @return найденный смартфон или null, если не найден.
     */
    public Smartphone findOne(int id) {
        return smartphoneRepository.findById(id).orElseThrow(null);
    }

    /**
     * Сохраняет новый смартфон.
     *
     * @param smartphone объект смартфона.
     */
    @Transactional
    public void save(Smartphone smartphone) {
        smartphoneRepository.save(smartphone);
    }

    /**
     * Обновляет информацию о смартфоне.
     *
     * @param id         идентификатор смартфона.
     * @param smartphone объект смартфона.
     */
    @Transactional
    public void update(int id, Smartphone smartphone) {
        smartphone.setId(id);
        smartphoneRepository.save(smartphone);
    }

    /**
     * Удаляет смартфон по идентификатору.
     *
     * @param id идентификатор смартфона.
     */
    @Transactional
    public void delete(int id) {
        smartphoneRepository.deleteById(id);
    }

    /**
     * Фильтрует смартфоны по цене, равной или большей заданной.
     *
     * @param maxPrice максимальная цена.
     * @return список отфильтрованных смартфонов.
     */
    public List<Smartphone> filterByPrice(float maxPrice) {
        return smartphoneRepository.findByPriceGreaterThanEqual(maxPrice);
    }

    /**
     * Заполняет базу данных примерами смартфонов.
     */
    @Transactional
    public void fillExample() {
        smartphoneRepository.insertExample();
    }

    /**
     * Удаляет все смартфоны из базы данных.
     */
    @Transactional
    public void wipe() {
        smartphoneRepository.deleteAll();
    }
}
