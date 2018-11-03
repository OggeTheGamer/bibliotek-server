package nu.ssis.a18mosu.clients;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
@PropertySource("secrets.properties")
public class RecaptchaClient {

	@Autowired
	private OkHttpClient okHttpClient;
	
	@Value("${library.recaptcha.secret}")
	private String recaptchaSecret;

	public boolean verify(String response) {
		//@formatter:off
		
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("response", response)
				.addFormDataPart("secret", recaptchaSecret)
				.build();

		Request request = new Request.Builder()
				.url("https://www.google.com/recaptcha/api/siteverify")
				.post(requestBody)
				.build();
		
		//@formatter:on
		
		try {
			Response r = okHttpClient.newCall(request).execute();
			JSONObject jObject = new JSONObject(r.body().string());
			return jObject.getBoolean("success");
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
