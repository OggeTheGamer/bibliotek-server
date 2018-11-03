package nu.ssis.a18mosu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@PropertySource("secrets.properties")
public class CaptchaSettings {

	@Getter @Value("${library.recaptcha.site}")
	private String site;

}