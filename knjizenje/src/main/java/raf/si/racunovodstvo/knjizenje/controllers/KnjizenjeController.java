package raf.si.racunovodstvo.knjizenje.controllers;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.si.racunovodstvo.knjizenje.model.Knjizenje;
import raf.si.racunovodstvo.knjizenje.responses.KnjizenjeResponse;
import raf.si.racunovodstvo.knjizenje.services.IKnjizenjeService;
import raf.si.racunovodstvo.knjizenje.specifications.RacunSpecificationsBuilder;
import raf.si.racunovodstvo.knjizenje.utils.ApiUtil;
import raf.si.racunovodstvo.knjizenje.utils.SearchUtil;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;


@CrossOrigin
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/knjizenje")
public class KnjizenjeController {


    private final IKnjizenjeService knjizenjaService;
    private final SearchUtil<Knjizenje> searchUtil;


    public KnjizenjeController(IKnjizenjeService knjizenjaService) {
        this.knjizenjaService = knjizenjaService;
        this.searchUtil = new SearchUtil<>();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDnevnikKnjizenja(@Valid @RequestBody Knjizenje dnevnikKnjizenja) {
        return ResponseEntity.ok(knjizenjaService.save(dnevnikKnjizenja));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDnevnikKnjizenja(@Valid @RequestBody Knjizenje dnevnikKnjizenja) {
        Optional<Knjizenje> optionalDnevnik = knjizenjaService.findById(dnevnikKnjizenja.getKnjizenjeId());
        if (optionalDnevnik.isPresent()) {
            return ResponseEntity.ok(knjizenjaService.save(dnevnikKnjizenja));
        }

        throw new EntityNotFoundException();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteDnevnikKnjizenja(@PathVariable("id") Long id) {
        Optional<Knjizenje> optionalDnevnik = knjizenjaService.findById(id);
        if (optionalDnevnik.isPresent()) {
            knjizenjaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        throw new EntityNotFoundException();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getDnevnikKnjizenjaId(@PathVariable("id") Long id) {
        Optional<Knjizenje> optionalDnevnik = knjizenjaService.findById(id);
        if (optionalDnevnik.isPresent()) {
            return ResponseEntity.ok(optionalDnevnik.get());
        }

        throw new EntityNotFoundException();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestParam(name = "search") String search,
                                    @RequestParam(defaultValue = ApiUtil.DEFAULT_PAGE) @Min(ApiUtil.MIN_PAGE) Integer page,
                                    @RequestParam(defaultValue = ApiUtil.DEFAULT_SIZE) @Min(ApiUtil.MIN_SIZE) @Max(ApiUtil.MAX_SIZE) Integer size,
                                    @RequestParam(defaultValue = "-datumKnjizenja") String[] sort) {
        RacunSpecificationsBuilder<Knjizenje> builder = new RacunSpecificationsBuilder<>();
        Pageable pageSort = ApiUtil.resolveSortingAndPagination(page, size, sort);


        Specification<Knjizenje> spec = searchUtil.getSpec(search);

        Page<KnjizenjeResponse> result = knjizenjaService.findAll(spec, pageSort);

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(knjizenjaService.findAllKnjizenjeResponse());
    }
}
