package ru.bokalysha.rkis.Prac8.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.bokalysha.rkis.Prac8.exceptions.find.SmartphoneNotFoundException;
import ru.bokalysha.rkis.Prac8.messaging.MessageProducer;
import ru.bokalysha.rkis.Prac8.models.Smartphone;
import ru.bokalysha.rkis.Prac8.services.SmartphoneService;

import java.util.stream.Collectors;

/**
 * Контроллер для работы со смартофном.
 */
@Controller
@RequestMapping("/smartphone")
public class SmartphoneController {

    private final SmartphoneService smartphoneService;
    private final MessageProducer messageProducer;

    /**
     * Конструктор контроллера.
     *
     * @param smartphoneService сервис смартфонов
     * @param messageProducer отправщик сообщений
     */
    @Autowired
    public SmartphoneController(MessageProducer messageProducer, SmartphoneService smartphoneService) {
        this.messageProducer = messageProducer;
        this.smartphoneService = smartphoneService;
    }

    /**
     * Обрабатывает запрос на получение списка смартфонов.
     *
     * @param price Цена для фильтрации.
     * @param model Объект модели.
     * @return Представление списка смартфонов.
     */
    @GetMapping()
    public String index(@RequestParam(name = "price", required = false) Float price, Model model) {
        if (price != null) {
            model.addAttribute("smartphone", smartphoneService.filterByPrice(price));
        } else {
            model.addAttribute("smartphone", smartphoneService.findAll());
        }
        return "smartphone/main";
    }

    /**
     * Обрабатывает запрос на редактирование информации о смартфонах.
     *
     * @param id    Идентификатор смартфона.
     * @param model Объект смартфона.
     * @return Представление для редактирования.
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("smartphone", smartphoneService.findOne(id));
        return "smartphone/edit";
    }

    /**
     * Обрабатывает запрос на добавление нового смартфона.
     *
     * @param smartphone Объект смартфона для добавления.
     * @return Представление для добавления смартфона.
     */
    @GetMapping("/add")
    public String addSmartphone(@ModelAttribute("smartphone") Smartphone smartphone) {
        return "smartphone/add";
    }

    /**
     * Обрабатывает запрос на создание нового смартфона.
     *
     * @param smartphone    Объект смартфона для создания.
     * @param bindingResult Результаты валидации.
     * @return Представление для добавления смартфонов или перенаправление на список смартфонов.
     */
    @PostMapping()
    public String create(
            @ModelAttribute("smartphone") @Valid Smartphone smartphone,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            messageProducer.sendMessage("При добавлении объекта  произошла(и) ошибка(и): " + errors);
            return "smartphone/add";
        }
        messageProducer.sendMessage("Добавлен объект " + smartphone);
        smartphoneService.save(smartphone);
        return "redirect:/smartphone";
    }

    /**
     * Обрабатывает запрос на обновление информации о смартфонов.
     *
     * @param smartphone    Объект смартфона для обновления.
     * @param bindingResult Результаты валидации.
     * @param id            Идентификатор смартфона.
     * @return Представление для редактирования или перенаправление на список смартфонов.
     */
    @PatchMapping("/{id}")
    public String update(
            @ModelAttribute("smartphone") @Valid Smartphone smartphone,
            BindingResult bindingResult,
            @PathVariable("id") int id
    ) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            messageProducer.sendMessage("При изменении объекта с id=" + id + " произошла(и) ошибка(и): " + errors);
            return "smartphone/edit";
        }
        try {
            smartphoneService.update(id, smartphone);
            messageProducer.sendMessage("Объект с id=" + id + " изменен на " + smartphone);
        } catch (SmartphoneNotFoundException e) {
            messageProducer.sendMessage("При изменении объекта с id=" + id + " произошла ошибка: " + e.getMessage());
        }
        return "redirect:/smartphone";
    }

    /**
     * Обрабатывает запрос на удаление смартфона.
     *
     * @param id Идентификатор смартфона.
     * @return Перенаправление на список смартфонов.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        try {
            smartphoneService.delete(id);
            messageProducer.sendMessage("Объект с id=" + id + " удален");
        } catch (SmartphoneNotFoundException e) {
            messageProducer.sendMessage("При удалении объекта с id=" + id + " произошла ошибка: " + e.getMessage());
        }
        return "redirect:/smartphone";
    }

    @PatchMapping("/{id}/buy")
    public String buy(@PathVariable("id") int id) {
        try {
            messageProducer.sendMessage("\nУспешно купленный смартфон=" + id);
        } catch (SmartphoneNotFoundException e) {
            messageProducer.sendMessage("\nОшибки, возникшие при покупке смартфона=" + id + ": " + e.getMessage());
        }
        return "redirect:/smartphone";
    }
}
