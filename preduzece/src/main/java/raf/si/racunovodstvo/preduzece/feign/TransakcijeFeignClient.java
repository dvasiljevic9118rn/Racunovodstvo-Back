package raf.si.racunovodstvo.preduzece.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import raf.si.racunovodstvo.preduzece.model.Transakcija;
import raf.si.racunovodstvo.preduzece.requests.ObracunTransakcijeRequest;

import java.util.List;

@FeignClient(value = "knjizenje/api")
public interface TransakcijeFeignClient {

    @PostMapping("/transakcije")
    List<Transakcija> obracunZaradeTransakcije(List<ObracunTransakcijeRequest> obracunTransakcijeRequests, @RequestHeader("Authorization") String token);

}
