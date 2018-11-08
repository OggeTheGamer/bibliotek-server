package nu.ssis.a18mosu.clients;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import nu.ssis.a18mosu.datatransferobject.RemoteGenericBookDTO;

@Component
public class AdlibrisBookApiClient implements RemoteBookApiClient {
	
	private static final String URL_PREFIX = "https://www.adlibris.com/se/bok/-";
	
	@Override
	public Optional<RemoteGenericBookDTO> getRemoteGenericBook(String isbn) {
		try {
			Document adlibrisPage = Jsoup.connect(URL_PREFIX + isbn).get();

			RemoteGenericBookDTO genericBook = new RemoteGenericBookDTO();
			genericBook.setTitle(adlibrisPage.getElementsByClass("heading--product-title").get(0).text());
			genericBook.setAuthors(adlibrisPage.getElementsByClass("heading--product-title-more").text());
			genericBook.setDescription(adlibrisPage.getElementById("product-description").text());
			genericBook.setImage(adlibrisPage.getElementsByClass("product-header__img").attr("src"));

			return Optional.of(genericBook);
		} catch (IOException | IndexOutOfBoundsException e) {
			return Optional.empty();
		}
	}

}
