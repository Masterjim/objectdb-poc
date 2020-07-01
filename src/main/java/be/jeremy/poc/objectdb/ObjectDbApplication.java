package be.jeremy.poc.objectdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class ObjectDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObjectDbApplication.class, args);
	}

	@Bean(name="entityManagerFactory")
	public EntityManagerFactory getEntityManagerFactoryBean() {
		return Persistence.createEntityManagerFactory("spring-data-jpa-test.odb");
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);

		return txManager;
	}
}
