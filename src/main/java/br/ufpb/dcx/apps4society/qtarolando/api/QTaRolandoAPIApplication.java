package br.ufpb.dcx.apps4society.qtarolando.api;

import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Profile;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@SpringBootApplication
public class QTaRolandoAPIApplication implements CommandLineRunner {
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	private Environment env;

	public static void main(String[] args) {

		SpringApplication.run(QTaRolandoAPIApplication.class, args);

	}

	@GetMapping("/")
	@ResponseBody
	String index() {
		return "Welcome to QTaRolando-API! | VERSION: v0.0.1-SNAPSHOT";
	}

	@Override
	public void run(String... args) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
		/*	Event event1 = new Event("Novo evento","Novo evento criado", 1, "um novo evento", LocalDateTime.parse("2021-12-03T06:00:00"), LocalDateTime.parse("2021-12-02T23:59:00"), "", Modality.IN_PERSON, "", "", "");
			Event event2 = new Event("Novo evento","Novo evento criado", 5, "um novo evento", LocalDateTime.parse("2021-12-09T15:00:00"), LocalDateTime.parse("2021-12-10T23:59:00"), "", Modality.REMOTE,"", "", "");
			eventRepository.save(event1);
			eventRepository.save(event2);*/


			UserAccount user1 = new UserAccount("admin@gmail.com", "admin", bCryptPasswordEncoder.encode("123"));
			UserAccount user2 = new UserAccount("manager@gmail.com", "manager", bCryptPasswordEncoder.encode("321"));
			user1.addProfile(Profile.ADMIN);
			user1.addProfile(Profile.MANAGER);
			user2.addProfile(Profile.MANAGER);


		/*	user1.getEvents().add(event1);
			user2.getEvents().add(event2);*/

			if (userAccountRepository.findByEmail(user1.getEmail()) == null) {
				userAccountRepository.save(user1);
			}

			if (userAccountRepository.findByEmail(user2.getEmail()) == null) {
				userAccountRepository.save(user2);
			}
		}
	}
}
