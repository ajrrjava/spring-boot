package com.strateknia.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@SpringBootApplication
@RestController
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private PlanetService planetService;

    public static void main(String[] args) {
        SpringApplication.run(Controller.class, args);
    }

    @GetMapping("/planet")
    public ResponseEntity<Planet> getPlanet(@RequestParam int id) {
        log.info("Getting planet with id = {}", id);

        return planetService.getPlanet(id);
    }

    @GetMapping("/planets")
    public ResponseEntity<List<Planet>> getPlanets() {
        log.info("Getting all planets");

        return planetService.getPlanets();
    }

    @PostMapping("/planet")
    public ResponseEntity<String> addPlanet(@RequestParam String name) {
        log.info("Adding planet with name = {}", name);

        Planet planet = new Planet();
        planet.setName(name);
        return planetService.addPlanet(planet);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlanet(@RequestParam("id") int id) {
        log.info("Deleting planet with id = {}", id);

        return planetService.deletePlanet(id);
    }

    @ExceptionHandler(value = NoSuchPlanetExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNoSuchPlanetExistsException(NoSuchPlanetExistsException e) {
        log.error("Caught: {}", e.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @GetMapping("/test")
    public ResponseEntity<String> testUrl() throws Exception {
        log.info("Testing URL");

        String myUrl = "http://localhost:8080/planets";
        URI uri = URI.create(myUrl);
        URL u = new URL(myUrl);
        URL url = URL.of(uri, null);

        URLConnection conn = u.openConnection();
        BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while((line = r.readLine()) != null) {
            sb.append(line);
        }

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }
}
