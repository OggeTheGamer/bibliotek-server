package nu.ssis.a18mosu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
  
    @Override
    protected String getDatabaseName() {
        return "library";
    }
  
    @Override
    protected String getMappingBasePackage() {
        return "nu.ssis.a18mosu";
    }

	@Override
	public @Bean MongoClient mongoClient() {
		return new MongoClient("127.0.0.1", 27017);
	}
}