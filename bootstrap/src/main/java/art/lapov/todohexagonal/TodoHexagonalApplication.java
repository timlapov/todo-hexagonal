package art.lapov.todohexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@EntityScan("art.lapov.adapterdb")
@EnableJpaRepositories("art.lapov.adapterdb")
public class TodoHexagonalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoHexagonalApplication.class, args);
	}

}
