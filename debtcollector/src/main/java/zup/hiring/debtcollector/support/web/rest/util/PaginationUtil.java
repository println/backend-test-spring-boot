package zup.hiring.debtcollector.support.web.rest.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for handling pagination.
 *
 * <p>
 * Pagination uses the same principles as the
 * <a href="https://developer.github.com/v3/#pagination">GitHub API</a>, and
 * follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link
 * header)</a>.
 */
public final class PaginationUtil {

	private final static boolean ONE_INDEXED_PARAMETERS = true;

	private PaginationUtil() {
	}

	public static <T> HttpHeaders generatePaginationHttpHeaders(Page<T> page, String baseUrl) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", Long.toString(page.getTotalElements()));

		StringBuilder link = generateSiblingPage(page, baseUrl, ONE_INDEXED_PARAMETERS);
		link.append(generateLastPage(page, baseUrl, ONE_INDEXED_PARAMETERS));
		link.append(generateFirstPage(page, baseUrl, ONE_INDEXED_PARAMETERS));

		headers.add(HttpHeaders.LINK, link.toString());
		return headers;
	}

	private static <T> String generateLastPage(Page<T> page, String baseUrl, boolean oneIndexedParameters) {
		int lastPage = 0;
		if (page.getTotalPages() > 0) {
			lastPage = getNumber(page.getTotalPages() - 1, oneIndexedParameters);
		}
		return "<" + generateUri(baseUrl, lastPage, page.getSize()) + ">; rel=\"last\",";
	}

	private static <T> String generateFirstPage(Page<T> page, String baseUrl, boolean oneIndexedParameters) {
		return "<" + generateUri(baseUrl, getNumber(0, oneIndexedParameters), page.getSize()) + ">; rel=\"first\"";
	}

	private static <T> StringBuilder generateSiblingPage(Page<T> page, String baseUrl, boolean oneIndexedParameters) {
		StringBuilder builder = new StringBuilder();

		int totalPages = page.getTotalPages();
		int pageSize = page.getSize();
		int pageNumber = page.getNumber();

		if (pageNumber + 1 < totalPages) {
			builder.append("<" + generateUri(baseUrl, getNumber(pageNumber, oneIndexedParameters) + 1, pageSize) + ">; rel=\"next\",");
		}

		if (pageNumber > 0) {
			builder.append("<" + generateUri(baseUrl, getNumber(pageNumber, oneIndexedParameters) - 1, pageSize) + ">; rel=\"prev\",");
		}

		return builder;
	}

	private static int getNumber(int pageNumber, boolean oneIndexedParameters) {
		if (oneIndexedParameters) {
			return pageNumber + 1;
		}
		return pageNumber;
	}

	private static String generateUri(String baseUrl, int page, int size) {
		return UriComponentsBuilder.fromUriString(baseUrl)
				.queryParam("page", page)
				.queryParam("size", size)
				.toUriString();
	}
}
