package ru.bokalysha.rkis.Prac8.controllers;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bokalysha.rkis.Prac8.exceptions.create.SmartphoneNotCreatedException;
import ru.bokalysha.rkis.Prac8.exceptions.delete.SmartphoneNotDeletedException;
import ru.bokalysha.rkis.Prac8.exceptions.find.SmartphoneNotFoundException;
import ru.bokalysha.rkis.Prac8.exceptions.update.SmartphoneNotUpdatedException;
import ru.bokalysha.rkis.Prac8.models.Smartphone;
import ru.bokalysha.rkis.Prac8.services.SmartphoneService;
import ru.bokalysha.rkis.Prac8.utils.ErrorHandler;

@RestController
@RequestMapping("/smartphones/api")
public class SmartphoneRestController extends ErrorHandler<SmartphoneNotCreatedException, SmartphoneNotFoundException,
        SmartphoneNotUpdatedException, SmartphoneNotDeletedException> {

    private final SmartphoneService service;

    @Autowired
    public SmartphoneRestController(SmartphoneService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Smartphone>> getAll(@RequestParam(name = "price", required = false) Float price) {
        if (price != null) {
            return new ResponseEntity<>(service.filterByPrice(price), HttpStatus.OK);
        }
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Smartphone> show(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.findOne(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Smartphone smartphone, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SmartphoneNotCreatedException(getError(bindingResult));
        }
        service.save(smartphone);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid Smartphone smartphone, BindingResult bindingResult,
                                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            throw new SmartphoneNotUpdatedException(getError(bindingResult));
        }
        try {
            service.update(id, smartphone);
        } catch (SmartphoneNotFoundException e) {
            throw new SmartphoneNotUpdatedException(e.getMessage());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        try {
            service.delete(id);
        } catch (SmartphoneNotFoundException e) {
            throw new SmartphoneNotDeletedException(e.getMessage());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        service.wipe();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> fillExample() {
        service.fillExample();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private String getError(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error:
                errors) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMsg.toString();
    }

}
