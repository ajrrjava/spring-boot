package com.strateknia.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    public ResponseEntity<String> addPlanet(Planet planet) {
        planetRepository.save(planet);
        return new ResponseEntity<>("Planet was added", HttpStatus.OK);
    }

    public ResponseEntity<String> deletePlanet(int id) {
        return planetRepository.findById(id)
                .map(planet -> {
                    planetRepository.deleteById(planet.getId());
                    return new ResponseEntity<>("Planet was deleted", HttpStatus.OK);
                })
                .orElseThrow(() ->
                        new NoSuchPlanetExistsException("Planet with Id = "+id+" does not exists!")
                );
    }

    public ResponseEntity<Planet> getPlanet(int id) {
        Optional<Planet> found = planetRepository.findById(id);
        return found.map(planet -> new ResponseEntity<>(planet, HttpStatus.OK))
                .orElseThrow(() ->
                        new NoSuchPlanetExistsException("Planet with Id = "+id+" does not exists!"));
    }

    public ResponseEntity<List<Planet>> getPlanets() {
        List<Planet> planets = planetRepository.findAll();
        return new ResponseEntity<>(planets, HttpStatus.OK);
    }
}
