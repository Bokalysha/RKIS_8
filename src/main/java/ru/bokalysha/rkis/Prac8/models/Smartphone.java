package ru.bokalysha.rkis.Prac8.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * ККласс Smartphone представляет смартфон.
 */
@Entity
@Table(name = "smartphone")
public class Smartphone {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand")
    @Size(min = 1, max = 255, message = "Длина бренда должна быть от 1 до 255")
    private String brand;

    @Column(name = "model")
    @Size(min = 1, max = 255, message = "Длина модели должна быть от 1 до 255")
    private String model;

    @Column(name = "operating_system")
    @Size(min = 1, max = 255, message = "Длина цвета должна быть от 1 до 255")
    private String operating_system;

    @Column(name = "storage_capacity_gb")
    @Min(value = 0, message = "Объем памяти не может быть отрицательным")
    private int storage_capacity_gb;

    @Column(name = "price")
    @Min(value = 0, message = "Цена должна быть неотрецательной")
    private float price;

    /**
     * Конструктор с параметрами
     *
     * @param id идентификатор смартфона.
     * @param brand бренд смартфона.
     * @param model модель смартфона.
     * @param operating_system операционная система смартфона.
     * @param storage_capacity_gb объем памяти смартфона.
     * @param price цена смартфона.
     */
    public Smartphone(int id, String brand, String model, String operating_system, int storage_capacity_gb,
                      float price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.operating_system = operating_system;
        this.storage_capacity_gb = storage_capacity_gb;
        this.price = price;
    }

    /**
     * Конструктор с параметрами
     *
     * @param brand бренд смартфона.
     * @param model модель смартфона.
     * @param operating_system операционная система смартфона.
     * @param storage_capacity_gb объем памяти смартфона.
     * @param price цена смартфона.
     */
    public Smartphone(String brand, String model, String operating_system, int storage_capacity_gb,
                      float price) {
        this.brand = brand;
        this.model = model;
        this.operating_system = operating_system;
        this.storage_capacity_gb = storage_capacity_gb;
        this.price = price;
    }

    /**
     * Пустой конструктор.
     */
    public Smartphone() {

    }

    /**
     * Возвращает идентификатор смартфона.
     *
     * @return идентификатор смартфона
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор смартфона.
     *
     * @param id идентификатор для установки
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает бренд смартфона.
     *
     * @return бренд смартфона
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Устанавливает бренд смартфона.
     *
     * @param brand бренд для установки
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Возвращает модель смартфона.
     *
     * @return модель смартфона
     */
    public String getModel() {
        return model;
    }

    /**
     * Устанавливает модель смартфона.
     *
     * @param model модель для установки
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Возвращает операционную систему смартфона.
     *
     * @return операционная система смартфона
     */
    public String getOperating_system() {
        return operating_system;
    }

    /**
     * Устанавливает операционную систему смартфона.
     *
     * @param operating_system операционная система для установки
     */
    public void setOperating_system(String operating_system) {
        this.operating_system = operating_system;
    }

    /**
     * Возвращает объем памяти смартфона в гигабайтах.
     *
     * @return объем памяти смартфона
     */
    public int getStorage_capacity_gb() {
        return storage_capacity_gb;
    }

    /**
     * Устанавливает объем памяти смартфона в гигабайтах.
     *
     * @param storage_capacity_gb объем памяти для установки.
     */
    public void setStorage_capacity_gb(int storage_capacity_gb) {
        this.storage_capacity_gb = storage_capacity_gb;
    }

    /**
     * Возвращает цену смартфона в долларах США.
     *
     * @return цена смартфона.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Устанавливает цену смартфона в долларах США.
     *
     * @param price цена для установки.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Переопределение метода toString().
     */
    @Override
    public String toString() {
        return "Smartphone{" + "id=" + id + ", brand='" + brand + '\'' + ", model='" + model + '\'' + ", operating_system='" + operating_system + '\'' + ", storage_capacity_gb=" + storage_capacity_gb + ", price=" + price + '}';
    }

    /**
     * Переопределение метода equals().
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Smartphone smartphone)) return false;
        return id == smartphone.id && Float.compare(smartphone.price, price) == 0 && storage_capacity_gb == smartphone.storage_capacity_gb && Objects.equals(brand, smartphone.brand) && Objects.equals(model, smartphone.model) && Objects.equals(operating_system, smartphone.operating_system);
    }

    /**
     * Переопределение метода hashCode().
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, operating_system, storage_capacity_gb, price);
    }
}