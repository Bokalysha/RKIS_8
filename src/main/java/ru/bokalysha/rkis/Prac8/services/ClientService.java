package ru.bokalysha.rkis.Prac8.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.bokalysha.rkis.Prac8.models.Smartphone;

import java.util.List;

@Service
public class ClientService {

    private final WebClient webClient;

    @Autowired
    public ClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<Smartphone>> getAll() {
        return webClient.get()
                .uri("/smartphones/api")
                .retrieve()
                .bodyToFlux(Smartphone.class)
                .collectList();
    }

    public Mono<Smartphone> getById(int id) {
        return webClient.get()
                .uri("/smartphones/api/" + id)
                .retrieve().bodyToMono(Smartphone.class);
    }

    public Mono<List<Smartphone>> getFilteredByPrice(Float price) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/smartphones/api")
                        .queryParam("price", price)
                        .build())
                .retrieve()
                .bodyToFlux(Smartphone.class)
                .collectList();
    }

    public void create(Smartphone smartphone) {
        webClient.post()
                .uri("/smartphones/api")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(smartphone), Smartphone.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void update(int id, Smartphone updatedSmartphone) {
        webClient.put()
                .uri("/smartphones/api/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedSmartphone))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void delete(int id) {
        webClient.delete()
                .uri("/smartphones/api/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void wipeAll() {
        webClient.delete()
                .uri("/smartphones/api")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void fillExample() {
        webClient.patch()
                .uri("/smartphones/api")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}