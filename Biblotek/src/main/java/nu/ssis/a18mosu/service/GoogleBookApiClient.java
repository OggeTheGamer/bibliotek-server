package nu.ssis.a18mosu.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nu.ssis.a18mosu.datatransferobject.RemoteGenericBookDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class GoogleBookApiClient implements RemoteBookApiClient {

	@Autowired
	private OkHttpClient client;
	private static final String URL_PREFIX = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
	
	@Override
	public Optional<RemoteGenericBookDTO> getRemoteGenericBook(String isbn) {
		try {
			Request request = new Request.Builder().url(URL_PREFIX + isbn).build();
			Response response = client.newCall(request).execute();
			
			JSONObject jObject = new JSONObject(response.body().string());
			jObject = jObject.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
			
			RemoteGenericBookDTO book = new RemoteGenericBookDTO();
			book.setIsbn(isbn);
			book.setDescription(jObject.getString("description"));
			book.setTitle(jObject.getString("title"));
			book.setImage(jObject.getJSONObject("imageLinks").getString("thumbnail"));
			book.setLanguage(jObject.getString("language"));
			
			String authors = jObject.getJSONArray("authors").toList()
					.stream()
						.filter(x -> x instanceof String)
						.map(Object::toString)
						.collect(Collectors.joining(", ")
					);
			book.setAuthors(authors);
			
			return Optional.of(book);
			
		} catch(IOException | JSONException e) {
			return Optional.empty();
		}
	}
	
}
