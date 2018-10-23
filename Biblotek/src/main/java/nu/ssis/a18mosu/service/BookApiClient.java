package nu.ssis.a18mosu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.model.Comment;
import nu.ssis.a18mosu.model.GenericBook;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class BookApiClient {
	
	
	OkHttpClient client = new OkHttpClient();
	private static String urlPrefix = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
	
	public GenericBook getGenericBook(String isbn) throws JSONException, IOException {
		Request request = new Request.Builder().url(urlPrefix + isbn).build();
		Response response = client.newCall(request).execute();
		
		JSONObject jObject = new JSONObject(response.body().string());
		jObject = jObject.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
		
		GenericBook book = new GenericBook();
		book.setDescription(jObject.getString("description"));
		book.setTitle(jObject.getString("title"));
		book.setImage(jObject.getJSONObject("imageLinks").getString("thumbnail"));
		book.setLanguage(jObject.getString("language"));
		book.setIsbn(isbn);
		book.setComments(new ArrayList<Comment>());
		
		
		List<Object> authors = jObject.getJSONArray("authors").toList();
		String authorString = "";
		for(Object author : authors) {
			if(author instanceof String) {
				authorString += " " + (String) author;
			}
		}
		book.setAuthors(authorString);
		
		
		return book;
		
	}
	
}
