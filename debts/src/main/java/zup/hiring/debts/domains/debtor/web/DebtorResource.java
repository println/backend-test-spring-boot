package zup.hiring.debts.domains.debtor.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import zup.hiring.debts.domains.debtor.Debtor;
import zup.hiring.debts.domains.debtor.DebtorService;
import zup.hiring.debts.support.utils.controller.RestApiController;
import zup.hiring.debts.support.web.rest.util.HeaderUtil;
import zup.hiring.debts.support.web.rest.util.PaginationUtil;
import zup.hiring.debts.support.web.rest.util.ResponseUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RestApiController
public class DebtorResource {

	private final Logger log = LoggerFactory.getLogger(DebtorResource.class);

	private static final String ENTITY_NAME = "Debtor";

	private final DebtorService service;

	public DebtorResource(DebtorService service) {
		this.service = service;
	}

	@PostMapping("/debtors")
	public ResponseEntity<Debtor> createDebtor(@Valid @RequestBody Debtor debtor) throws URISyntaxException {
		this.log.debug("REST request to save Debtor : {}", debtor);
		Debtor result = this.service.create(debtor);
		return ResponseEntity.created(new URI("/api/debtors/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@PutMapping("/debtors/{id}")
	public ResponseEntity<Debtor> updateDebtor(@PathVariable Long id, @Valid @RequestBody Debtor debtor) {
		this.log.debug("REST request to update Debtor : {}", debtor);
		Debtor result = this.service.update(id, debtor);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@GetMapping("/debtors")
	public ResponseEntity<List<Debtor>> getAllDebtors(
			@RequestParam(required = false) String cpf,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String cellNumber,
			Pageable pageable) {
		this.log.debug("REST request to get a page of Debtors");
		Page<Debtor> page = this.service.findAll(new DebtorRequestFilter(cpf, name, email, cellNumber), pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(	page, "/api/debtors");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/debtors/count")
	public ResponseEntity<Long> countDebtors() {
		this.log.debug("REST request to count Debtors");
		Long count = this.service.count();
		return ResponseEntity.ok().body(count);
	}

	@GetMapping("/debtors/{id}")
	public ResponseEntity<Debtor> getDebtor(@PathVariable Long id) {
		this.log.debug("REST request to get Debtor : {}", id);
		Optional<Debtor> optional = this.service.findById(id);
		return ResponseUtil.wrapOrNotFound(optional);
	}

	@DeleteMapping("/debtors/{id}")
	public ResponseEntity<Void> deleteDebtor(@PathVariable Long id) {
		this.log.debug("REST request to delete Debtor : {}", id);
		this.service.deleteById(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	@GetMapping("/debtors/{id}/debts")
	public ModelAndView getDebtorDebts(@PathVariable Long id) {
		this.log.debug("REST request to get Debtor debts : {}", id);
		return new ModelAndView("forward:/api/debts?debtor=" + id);
	}
}
