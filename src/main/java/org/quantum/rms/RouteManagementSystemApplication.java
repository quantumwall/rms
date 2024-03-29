package org.quantum.rms;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.quantum.rms.model.Cargo;
import org.quantum.rms.model.Customer;
import org.quantum.rms.model.Driver;
import org.quantum.rms.model.Role;
import org.quantum.rms.model.Route;
import org.quantum.rms.model.User;
import org.quantum.rms.service.CustomerService;
import org.quantum.rms.service.DriverService;
import org.quantum.rms.service.RouteService;
import org.quantum.rms.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RouteManagementSystemApplication {

    public static void main(String[] args) {
	SpringApplication.run(RouteManagementSystemApplication.class, args);
    }

    @Bean
    ApplicationRunner addAdmin(UserService userService, PasswordEncoder encoder) {
	return args -> {
	    userService.findByEmail("admin").ifPresentOrElse(a -> {
	    }, () -> {
		var admin = new User();
		admin.setName("admin");
		admin.setEmail("admin");
		admin.setPassword(encoder.encode("admin"));
		admin.setRole(Role.ADMIN);
		userService.save(admin);
	    });

	};
    }

    @Bean
    @Profile("dev")
    ApplicationRunner loadData(UserService userService, CustomerService customerService, DriverService driverService,
	    RouteService routeService, PasswordEncoder encoder) {
	return args -> {
	    var user1 = new User();
	    var user2 = new User();
	    user1.setName("Петров Петр Петрович");
	    user1.setEmail("user1@user1.com");
	    user1.setPassword(encoder.encode("password"));
	    user2.setName("Иванов Иван Иванович");
	    user2.setEmail("user2@user2.com");
	    user2.setPassword(encoder.encode("password"));

	    var driver1 = new Driver();
	    var driver2 = new Driver();
	    driver1.setName("Сидоров Сидор Сидорович");
	    driver1.setUser(user1);
	    driver2.setName("Алексеев Алексей Алексеевич");
	    driver2.setUser(user2);

	    user1.addDriver(driver1);
	    user2.addDriver(driver2);

	    var customer1 = new Customer();
	    var customer2 = new Customer();
	    var customer3 = new Customer();
	    customer1.setName("Талина");
	    customer1.setUser(user1);
	    customer2.setName("Крафтер");
	    customer2.setUser(user1);
	    customer3.setName("Монополия");
	    customer3.setUser(user2);

	    user1.addCustomer(customer1);
	    user1.addCustomer(customer2);
	    user2.addCustomer(customer3);

	    var cargo1 = new Cargo();
	    var cargo2 = new Cargo();
	    var cargo3 = new Cargo();
	    var cargo4 = new Cargo();
	    cargo1.setName("Мясо");
	    cargo1.setWeight(10000.);
	    cargo2.setName("Палеты");
	    cargo2.setWeight(5000.);
	    cargo3.setName("Котлеты");
	    cargo3.setWeight(8000.);
	    cargo4.setName("Пиво");
	    cargo4.setWeight(15000.);

	    var route1 = new Route();
	    var route2 = new Route();
	    var route3 = new Route();
	    var route4 = new Route();

	    route1.setBillNumber("1234");
	    route1.setCargo(cargo1);
	    route1.setCustomer(customer1);
	    route1.setDepartureCity("Торбеево");
	    route1.setDestinationCity("Саранск");
	    route1.setDriver(driver1);
	    route1.setPaid(false);
	    route1.setPrice(BigDecimal.valueOf(15000));
	    route1.setShipmentDate(LocalDate.of(2023, 4, 15));
	    route1.setUser(user1);

	    route2.setBillNumber("2345");
	    route2.setCargo(cargo2);
	    route2.setCustomer(customer1);
	    route2.setDepartureCity("Волгоград");
	    route2.setDestinationCity("Самара");
	    route2.setDriver(driver2);
	    route2.setPaid(true);
	    route2.setPrice(BigDecimal.valueOf(34000));
	    route2.setShipmentDate(LocalDate.of(2023, 4, 1));
	    route2.setUser(user1);

	    route3.setBillNumber("2134");
	    route3.setCargo(cargo3);
	    route3.setCustomer(customer2);
	    route3.setDepartureCity("Атяшево");
	    route3.setDestinationCity("Торбеево");
	    route3.setDriver(driver1);
	    route3.setPaid(true);
	    route3.setPrice(BigDecimal.valueOf(23000));
	    route3.setShipmentDate(LocalDate.of(2023, 4, 10));
	    route3.setUser(user2);

	    route4.setBillNumber("8765");
	    route4.setCargo(cargo4);
	    route4.setCustomer(customer1);
	    route4.setDepartureCity("Самара");
	    route4.setDestinationCity("Уфа");
	    route4.setDriver(driver2);
	    route4.setPaid(false);
	    route4.setPrice(BigDecimal.valueOf(2000));
	    route4.setShipmentDate(LocalDate.of(2023, 3, 30));
	    route4.setUser(user2);

	    userService.save(user1);
	    userService.save(user2);

	    routeService.save(route1);
	    routeService.save(route2);
	    routeService.save(route3);
	    routeService.save(route4);
	};
    }
}
