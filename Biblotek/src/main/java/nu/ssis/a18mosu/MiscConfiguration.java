package nu.ssis.a18mosu;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class MiscConfiguration {

	@Bean
	public OkHttpClient okHttpClient() {
		OkHttpClient okHttpClient = new OkHttpClient();
		return okHttpClient;
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

}
