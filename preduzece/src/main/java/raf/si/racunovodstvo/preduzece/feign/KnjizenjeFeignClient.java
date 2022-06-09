package raf.si.racunovodstvo.preduzece.feign;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.si.racunovodstvo.knjizenje.model.Knjizenje;

import javax.validation.Valid;


public interface KnjizenjeFeignClient {


    @PostMapping("/api/knjizenje")
    ResponseEntity<?> createDnevnikKnjizenja(@Valid @RequestBody Knjizenje dnevnikKnjizenja, @RequestHeader("Authorization") String token);

}
