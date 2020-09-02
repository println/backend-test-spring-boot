package zup.hiring.debts.domains.debt.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zup.hiring.debts.domains.debt.Debt;
import zup.hiring.debts.domains.debt.DebtService;
import zup.hiring.debts.domains.debt.DebtStatus;
import zup.hiring.debts.support.utils.controller.RestApiController;
import zup.hiring.debts.support.web.rest.util.HeaderUtil;
import zup.hiring.debts.support.web.rest.util.PaginationUtil;
import zup.hiring.debts.support.web.rest.util.ResponseUtil;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RestApiController
@Validated
public class DebtResource {
	private final Logger log = LoggerFactory.getLogger(DebtResource.class);

	private static final String ENTITY_NAME = "Debt";

	private final DebtService service;
	private final SimpleDebtDtoToDebtConverter simpleDebtDtoToDebtConverter;
	private final DebtToDebtDtoConverter debtToDebtDtoConverter;


	public DebtResource(
			DebtService service,
			SimpleDebtDtoToDebtConverter simpleDebtDtoToDebtConverter,
			DebtToDebtDtoConverter debtToDebtDtoConverter) {
		this.service = service;
		this.simpleDebtDtoToDebtConverter = simpleDebtDtoToDebtConverter;
		this.debtToDebtDtoConverter = debtToDebtDtoConverter;
	}

	@PostMapping("/debts")
	public ResponseEntity<Debt> createDebt(@Valid @RequestBody SimpleDebtDto simpleDebtDto) throws URISyntaxException {
		this.log.debug("REST request to save Debt : {}", simpleDebtDto);
		Debt debt = this.simpleDebtDtoToDebtConverter.convert(simpleDebtDto);
		Debt result = this.service.create(debt);
		return ResponseEntity.created(new URI("/api/debts/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@PutMapping("/debts/{id}")
	public ResponseEntity<Debt> updateDebt(@PathVariable Long id, @Valid @RequestBody SimpleDebtDto simpleDebtDto) {
		this.log.debug("REST request to update Debt : {}", simpleDebtDto);
		Debt debt = this.simpleDebtDtoToDebtConverter.convert(simpleDebtDto);
		Debt result = this.service.update(id, debt);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@GetMapping("/debts")
	public ResponseEntity<List<DebtDto>> getAllDebts(
			@RequestParam(required = false) UUID uuid,
			@RequestParam(required = false) BigDecimal value,
			@RequestParam(required = false) BigDecimal maxValue,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDueDate,
			@RequestParam(required = false) DebtStatus status,
			@RequestParam(required = false) Long debtor,
			Pageable pageable) {
		this.log.debug("REST request to get a page of Debts");
		Page<Debt> page = this.service
				.findAll(new DebtRequestFilter(uuid, value, maxValue, dueDate, endDueDate, status, debtor), pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(	page, "/api/debts");
		return ResponseEntity.ok().headers(headers)
				.body(this.debtToDebtDtoConverter.convert(page.getContent()));
	}

	@GetMapping("/debts/count")
	public ResponseEntity<Long> countDebts() {
		this.log.debug("REST request to count Debts");
		Long count = this.service.count();
		return ResponseEntity.ok().body(count);
	}
	
	@GetMapping("/debts/{id}")
	public ResponseEntity<Debt> getDebt(@PathVariable Long id) {
		this.log.debug("REST request to get Debt : {}", id);
		Optional<Debt> optional = this.service.findById(id);
		return ResponseUtil.wrapOrNotFound(optional);
	}

	@DeleteMapping("/debts/{id}")
	public ResponseEntity<Void> deleteDebt(@PathVariable Long id) {
		this.log.debug("REST request to delete Debt : {}", id);
		this.service.deleteById(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	@PutMapping("/debts/{id}/payoff")
	public ResponseEntity<Debt> payOffDebt(@PathVariable Long id) {
		this.log.debug("REST request to pay off debt : {}", id);
		Debt result = this.service.payOffById(id);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

}
