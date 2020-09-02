package zup.hiring.debtcollector.domains.debt.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zup.hiring.debtcollector.domains.debt.Debt;
import zup.hiring.debtcollector.domains.debt.DebtService;
import zup.hiring.debtcollector.support.utils.controller.RestApiController;
import zup.hiring.debtcollector.support.web.rest.util.PaginationUtil;
import zup.hiring.debtcollector.support.web.rest.util.ResponseUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RestApiController
@Validated
public class DebtResource {
    private final Logger log = LoggerFactory.getLogger(DebtResource.class);

    private final DebtService service;

    public DebtResource(DebtService service) {
        this.service = service;
    }

    @GetMapping("/debts")
    public ResponseEntity<List<Debt>> getAllDebts(
            @RequestParam(required = false) BigDecimal value,
            @RequestParam(required = false) BigDecimal maxValue,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDueDate,
            @RequestParam(required = false) String debtor,
            Pageable pageable) {
        this.log.debug("REST request to get a page of Debts");
        Page<Debt> page = this.service
                .findAll(new DebtRequestFilter(value, maxValue, dueDate, endDueDate, debtor), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/debts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/debts/count")
    public ResponseEntity<Long> countDebts() {
        this.log.debug("REST request to count Debts");
        Long count = this.service.count();
        return ResponseEntity.ok().body(count);
    }

    @GetMapping("/debts/{uuid}")
    public ResponseEntity<Debt> getDebt(@PathVariable UUID uuid) {
        this.log.debug("REST request to get Debt : {}", uuid);
        Optional<Debt> optional = this.service.findById(uuid);
        return ResponseUtil.wrapOrNotFound(optional);
    }
}
