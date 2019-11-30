package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import com.desapp.grupoc1e022019.model.menuComponents.RankAverageMenu;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.PickUp;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.ConfirmedOrder;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.DeliveredOrder;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.SendingOrder;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;
import com.desapp.grupoc1e022019.model.providerComponents.location.Coord;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.NormalProvider;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.SetOfBusinessTime;
import com.desapp.grupoc1e022019.persistence.repositories.*;
import com.desapp.grupoc1e022019.services.builder.ClientBuilder;
import com.desapp.grupoc1e022019.services.builder.GoogleAuthBuilder;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.services.builder.OrderBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;

@SpringBootApplication
@Configuration
@EnableAsync
public class GrupoC1e022019Application {

	public static void main(String[] args) {
		SpringApplication.run(GrupoC1e022019Application.class, args);
	}

	@Bean("threadPoolTaskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(20);
		executor.initialize();
		return executor;
	}

	@Bean
	public CommandLineRunner demo(ProviderRepository providerRepository, MenuRepository menuRepository, OrderRepository orderRepository, ClientRepository clientRepository, GoogleTokenRepository googleTokenRepository) {
		return (args) -> {

			Schedule schedule =new Schedule(new HashMap<DayOfWeek, SetOfBusinessTime>());
			Provider jose = new Provider("FAKEID1","Jose","log","Quilmes",
					new Address(new Coord("0","0"),"West Quilmes"),"Josee",
					"jose.com.ar","cualquieracuenta03@gmail.com","13281349",schedule,
					new Credit(),40.0,new ArrayList<>(),new NormalProvider(),8);
			providerRepository.save(jose);

			Client nico = ClientBuilder.aClient()
					.withGoogleId("FAKEID2")
					.withFirstName("Nico")
					.withPhoneNumber("1243143")
					.withLocation("Varela city")
					.withEmail("nicolas.alv390909909@gmail.com")
					.withAddress("Av siempreviva 3029")
					.withLastName("Alvarez")
					.withCredit(new Credit(100.0))
					.build();

			clientRepository.save(nico);

			GoogleToken joseGoogleAuth = new GoogleAuthBuilder().withGoogleId("FAKEID1")
					.withTokenId("FAKETOKENID1")
					.withAccessToken("FAKEACCESSTOKEN1")
					.withExpiresIn(0)
					.build();

			GoogleToken nicoGoogleAuth = new GoogleAuthBuilder().withGoogleId("FAKEID2")
					.withTokenId("FAKETOKENID2")
					.withAccessToken("FAKEACCESSTOKEN2")
					.withExpiresIn(0)
					.build();

			googleTokenRepository.save(joseGoogleAuth);
			googleTokenRepository.save(nicoGoogleAuth);
			Client joseClient = ClientBuilder.aClient()
					.withGoogleId("FAKEID1")
					.withFirstName("Josesin")
					.withPhoneNumber("1243143")
					.withLocation("Quilmes city")
					.withEmail("cualquieracuenta03@gmail.com")
					.withAddress("Av siempreviva 3029")
					.withLastName("Cassanin")
					.withCredit(new Credit(300.0))
					.build();
			clientRepository.save(joseClient);

			this.createMenusAndOrders(clientRepository, menuRepository,orderRepository,jose,nico,joseClient);

		};
	}

	private void createMenusAndOrders(ClientRepository clientRepository,MenuRepository menuRepository,OrderRepository orderRepository,Provider joseProvider,Client nicoClient,Client joseClient) {
		//** Mila napo *//*
		ArrayList<CategoryMenu> cMiladeNico = new ArrayList<>();
		cMiladeNico.add(CategoryMenu.valueOf("PIZZA"));

		Menu milaNapo = MenuBuilder.aMenu()
				.withName("Mila Napo")
				.withCategories(cMiladeNico)
				.withDescription("Milanesa estilo pizza")
				.withProvider(joseProvider)
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.build();

		menuRepository.save(milaNapo);

		//**Burger  *//*
		ArrayList<CategoryMenu> cBurger = new ArrayList<>();
		cBurger.add(CategoryMenu.valueOf("HAMBURGER"));

		Menu burger = MenuBuilder.aMenu()
				.withName("Hamburguesa completa")
				.withCategories(cBurger)
				.withDescription("Tiene de todo señora")
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withProvider(joseProvider)
				.build();

		menuRepository.save(burger);

		//**Pizza  *//*
		ArrayList<CategoryMenu> cPizza = new ArrayList<>();
		cPizza.add(CategoryMenu.valueOf("PIZZA"));

		Menu pizza = MenuBuilder.aMenu()
				.withName("Pizza de palmitos")
				.withCategories(cPizza)
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(300.0,10,40.0,20,30.0))
				.withDescription("Tiene solo 2 palmitos")
				.withProvider(joseProvider)
				.build();
		pizza.addRate(3);
		menuRepository.save(pizza);

		//**Pizza  *//*
		Menu pizzaNapo = MenuBuilder.aMenu()
				.withName("Pizza Napolitana")
				.withCategories(cPizza)
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(300.0,10,40.0,20,30.0))
				.withDescription("Tiene napoli y tana")
				.withProvider(joseProvider)
				.build();
		pizzaNapo.addRate(5);

		menuRepository.save(pizzaNapo);

		//**Pizza  *//*
		Menu pizzaSalchi = MenuBuilder.aMenu()
				.withName("Salchipizza")
				.withCategories(cPizza)
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(400.0,10,40.0,20,30.0))
				.withDescription("Tiene salchi y pizza")
				.withProvider(joseProvider)
				.build();
		pizzaSalchi.addRate(4);

		menuRepository.save(pizzaSalchi);

		//**Pizza  *//*
		Menu pizzaAnana = MenuBuilder.aMenu()
				.withName("Pizza ananá")
				.withCategories(cPizza)
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(400.0,10,40.0,20,30.0))
				.withDescription("Tiene ana y ná")
				.withProvider(joseProvider)
				.build();
		pizzaAnana.addRate(1);

		menuRepository.save(pizzaAnana);

		//**Pizza  *//*
		Menu pizzaCalabresa = MenuBuilder.aMenu()
				.withName("Pizza calabresa")
				.withCategories(cPizza)
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(400.0,10,40.0,20,30.0))
				.withDescription("Tiene cala y bresa")
				.withProvider(joseProvider)
				.build();
		pizzaCalabresa.addRate(5);

		menuRepository.save(pizzaCalabresa);

		//**SUSHI  *//*
		ArrayList<CategoryMenu> cSushi = new ArrayList<>();
		cSushi.add(CategoryMenu.valueOf("SUSHI"));

		Menu sushi = MenuBuilder.aMenu()
				.withName("Sushi chino")
				.withCategories(cSushi)
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withDescription("Basicamente aló con palito")
				.withProvider(joseProvider)
				.build();

		menuRepository.save(sushi);

		//**PLATO VEGANO  *//*
		ArrayList<CategoryMenu> cVegan = new ArrayList<>();
		cVegan.add(CategoryMenu.valueOf("VEGAN"));

		Menu platoVegano = MenuBuilder.aMenu()
				.withName("Lechuga con lentejas")
				.withCategories(cVegan)
				.withMaxSalesPerDay(5)
				.withDescription("Bajo en calorias...")
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withProvider(joseProvider)
				.build();

		menuRepository.save(platoVegano);

		//**PLATO GREEN  *//*
		ArrayList<CategoryMenu> cGreen = new ArrayList<>();
		cGreen.add(CategoryMenu.valueOf("GREEN"));

		Menu platoGreen = MenuBuilder.aMenu()
				.withName("Alcachofas al vapor")
				.withCategories(cGreen)
				.withMaxSalesPerDay(5)
				.withDescription("Pocas alcachofas, mucho vapor...")
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withProvider(joseProvider)
				.build();

		menuRepository.save(platoGreen);

		//** Ceveza artesanal  *//*
		ArrayList<CategoryMenu> cCerveza = new ArrayList<>();
		cCerveza.add(CategoryMenu.valueOf("BEER"));

		Menu cerveza = MenuBuilder.aMenu()
				.withName("Cerveza artesanal")
				.withCategories(cCerveza)
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withDescription("Tomar poquito xq es fuerte")
				.withProvider(joseProvider)
				.withRankAverageMenu(new RankAverageMenu(19,38))
				.build();

		menuRepository.save(cerveza);

		//**Empanadas  *//*
		ArrayList<CategoryMenu> cEmpanadas = new ArrayList<>();
		cEmpanadas.add(CategoryMenu.valueOf("EMPANADAS"));

		Menu empanadas = MenuBuilder.aMenu()
				.withName("Empanadas")
				.withCategories(cEmpanadas)
				.withMaxSalesPerDay(5)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withDescription("Una de carne,dos de pollo, una de jyq, una de pollo, dos de carne...")
				.withProvider(joseProvider)
				.withRankAverageMenu(new RankAverageMenu(19,38))
					.build();

		menuRepository.save(empanadas);

		Order order1 = OrderBuilder.anOrder()
				.withClient(nicoClient)
				.withMenusAmount(1)
				.withState(new ConfirmedOrder())
				.withMenu(empanadas)
				.withDeliverType(new PickUp(LocalDateTime.now().plusDays(10)))
				.build();

		orderRepository.save(order1);

		Order order2 = OrderBuilder.anOrder()
				.withClient(nicoClient)
				.withMenusAmount(3)
				.withState(new SendingOrder())
				.withMenu(empanadas)
				.withDeliverType(new PickUp(LocalDateTime.now().plusDays(9)))
				.build();

		orderRepository.save(order2);


		Order order3 = OrderBuilder.anOrder()
				.withClient(nicoClient)
				.withMenusAmount(3)
				.withState(new DeliveredOrder())
				.withMenu(cerveza)
				.withDeliverType(new PickUp(LocalDateTime.now().plusDays(9)))
				.build();

		orderRepository.save(order3);

		nicoClient.haveToRankOrder();
		nicoClient.haveToRankOrder();
		nicoClient.haveToRankOrder();
		clientRepository.save(nicoClient);
	}
}
