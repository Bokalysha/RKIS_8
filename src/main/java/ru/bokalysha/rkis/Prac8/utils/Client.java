package ru.bokalysha.rkis.Prac8.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.bokalysha.rkis.Prac8.models.Smartphone;
import ru.bokalysha.rkis.Prac8.services.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.bokalysha.rkis.Prac8.utils.Logger.log;

@Component
public class Client implements CommandLineRunner {

    private final ClientService clientService;
    List<Integer> ids = new ArrayList<>();
    List<Float> prices = new ArrayList<>();


    @Autowired
    public Client(ClientService clientService) {
        this.clientService = clientService;
    }

    public void showAll() {
        Mono<List<Smartphone>> smartphoneMono = clientService.getAll();
        smartphoneMono
                .doOnSuccess(smartphones -> log("Table:\n" + smartphones.stream()
                        .map(smartphone -> {
                            ids.add(smartphone.getId());
                            prices.add(smartphone.getPrice());
                            return smartphone.toString();
                        })
                        .collect(Collectors.joining("\n")), "Client"))
                .subscribeOn(Schedulers.boundedElastic())
                .block();
    }

    public void showOne(int id) {
        Mono<Smartphone> smartphoneMono = clientService.getById(id);
        smartphoneMono
                .doOnSuccess(smartphone -> log("Smartphone " + id + ": " + smartphone, "Client"))
                .subscribeOn(Schedulers.boundedElastic())
                .block();
    }

    public void showFilteredByPrice(Float price) {
        Mono<List<Smartphone>> smartphoneMono = clientService.getFilteredByPrice(price);
        smartphoneMono
                .doOnSuccess(smartphones -> log("Smartphone with price " + price + " :\n" + smartphones.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n")), "Client"))
                .subscribeOn(Schedulers.boundedElastic())
                .block();
    }

    public void addSmartphone() {
        Smartphone smartphone = new Smartphone("brand_before", "model_before", "operating_system_before", 128,
                999f);
        log("Adding new smartphone: " + smartphone, "Client");
        clientService.create(smartphone);
    }

    public void updateSmartphone(int id) {
        Smartphone smartphone = new Smartphone("brand_after", "model_after", "operating_system_after", 256,
                1199f);
        log(String.format("Updating smartphone %d: ", id) + smartphone, "Client");
        clientService.update(id, smartphone);
    }

    public void deleteSmartphone(int id) {
        log(String.format("Deleting smartphone %d", id), "Client");
        clientService.delete(id);
    }

    public void wipeSmartphones() {
        log("Wiping smartphones...", "Client");
        clientService.wipeAll();
    }

    public void fillByExample() {
        log("Seeding example data...", "Client");
        clientService.fillExample();
    }


    @Override
    public void run(String... args) {
        String allowRun = System.getProperty("allow.run");

        if (!(allowRun == null || "true".equals(allowRun))) {
            return;
        }
        log("Client running\n", "Client");

        fillByExample();
        log("################################################");

        showAll();
        log("################################################");

        if (!ids.isEmpty()) {
            showOne(ids.get(0));
        } else {
            log("DB is empty", "Client");
        }
        log("################################################");

        if (!ids.isEmpty()) {
            showFilteredByPrice(
                    ((float) prices.stream().mapToDouble(Float::doubleValue).average().orElse(999f)));
        } else {
            log("DB is empty", "Client");
        }
        log("################################################");

        addSmartphone();
        showAll();
        log("################################################");

        if (!ids.isEmpty()) {
            updateSmartphone(ids.get(0));
        } else {
            log("DB is empty", "Client");
        }
        showAll();
        log("################################################");

        if (!ids.isEmpty()) {
            deleteSmartphone(ids.get(0));
        } else {
            log("DB is empty", "Client");
        }
        showAll();
        log("################################################");

        wipeSmartphones();
        showAll();
        log("################################################");

        log("Application works normally!");
    }


}