package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;
import com.desapp.grupoc1e022019.model.providerComponents.location.Coord;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.NormalProvider;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.SetOfBussinessTime;
import com.desapp.grupoc1e022019.persistence.ClientRepository;
import com.desapp.grupoc1e022019.persistence.MenuRepository;
import com.desapp.grupoc1e022019.persistence.ProviderRepository;
import com.desapp.grupoc1e022019.services.ProviderService;
import com.desapp.grupoc1e022019.services.builder.ClientBuilder;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.services.builder.ProviderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class GrupoC1e022019Application {

	public static void main(String[] args) {
		SpringApplication.run(GrupoC1e022019Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(ProviderRepository providerRepository, MenuRepository menuRepository, ClientRepository clientRepository) {
		return (args) -> {
			Schedule schedule =new Schedule(new HashMap<DayOfWeek, SetOfBussinessTime>());
			Provider jose = new Provider("Jose","log","Quilmes",new Address(new Coord(0.0d,0.0d),"West Quilmes"),"Josee","jose.com.ar","Jose@gmail.com","13281349",schedule,new Credit(),40.0,new ArrayList(),new NormalProvider(),0);
			providerRepository.save(jose);

			this.createMenus(menuRepository,jose);

			Client nico = ClientBuilder.aClient()
					.withFirstName("Nico")
					.withPhoneNumber("1243143")
					.withLocation("Varela city")
					.withEmail("nico@gmail.com")
					.withAddress("Av siempreviva 3029")
					.withLastName("Alvarez")
					.withCredit(new Credit(100.0))
					.build();
			clientRepository.save(nico);
		};
	}

	private void createMenus(MenuRepository menuRepository,Provider jose) {
		//** Mila napo *//*
		ArrayList<CategoryMenu> cMiladeNico = new ArrayList();
		cMiladeNico.add(CategoryMenu.valueOf("PIZZA"));

		Menu milaNapo = MenuBuilder.aMenu()
				.withName("Mila Napo")
				.withCategories(cMiladeNico)
				.withDescription("Milanesa estilo pizza")
				.withProvider(jose)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.build();

		menuRepository.save(milaNapo);

		//**Burger  *//*
		ArrayList<CategoryMenu> cBurger = new ArrayList();
		cBurger.add(CategoryMenu.valueOf("HAMBURGER"));

		Menu burger = MenuBuilder.aMenu()
				.withName("Hamburguesa completa")
				.withCategories(cBurger)
				.withDescription("Tiene de todo señora")
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withProvider(jose)
				.build();

		menuRepository.save(burger);

		//**Pizza  *//*
		ArrayList<CategoryMenu> cPizza = new ArrayList();
		cPizza.add(CategoryMenu.valueOf("PIZZA"));

		Menu pizza = MenuBuilder.aMenu()
				.withName("Pizza de palmitos")
				.withCategories(cBurger)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withDescription("Tiene solo 2 palmitos")
				.withProvider(jose)
				.build();

		menuRepository.save(pizza);

		//**SUSHI  *//*
		ArrayList<CategoryMenu> cSushi = new ArrayList();
		cSushi.add(CategoryMenu.valueOf("SUSHI"));

		Menu sushi = MenuBuilder.aMenu()
				.withName("Sushi chino")
				.withCategories(cSushi)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withDescription("Basicamente aló con palito")
				.withProvider(jose)
				.build();

		menuRepository.save(sushi);

		//**PLATO VEGANO  *//*
		ArrayList<CategoryMenu> cVegan = new ArrayList();
		cVegan.add(CategoryMenu.valueOf("VEGAN"));

		Menu platoVegano = MenuBuilder.aMenu()
				.withName("Lechuga con lentejas")
				.withCategories(cVegan)
				.withDescription("Bajo en calorias...")
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withProvider(jose)
				.build();

		menuRepository.save(platoVegano);

		//**PLATO GREEN  *//*
		ArrayList<CategoryMenu> cGreen = new ArrayList();
		cGreen.add(CategoryMenu.valueOf("GREEN"));

		Menu platoGreen = MenuBuilder.aMenu()
				.withName("Alcachofas al vapor")
				.withCategories(cGreen)
				.withDescription("Pocas alcachofas, mucho vapor...")
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withProvider(jose)
				.build();

		menuRepository.save(platoGreen);

		//** Ceveza artesanal  *//*
		ArrayList<CategoryMenu> cCerveza = new ArrayList();
		cCerveza.add(CategoryMenu.valueOf("BEER"));

		Menu cerveza = MenuBuilder.aMenu()
				.withName("Cerveza artesanal")
				.withCategories(cCerveza)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withDescription("Tomar poquito xq es fuerte")
				.withProvider(jose)
				.build();

		menuRepository.save(cerveza);

		//**Empanadas  *//*
		ArrayList<CategoryMenu> cEmpanadas = new ArrayList();
		cEmpanadas.add(CategoryMenu.valueOf("EMPANADAS"));

		Menu empanadas = MenuBuilder.aMenu()
				.withName("Empanadas")
				.withCategories(cEmpanadas)
				.withMenuPriceCalculator(new MenuPriceCalculator(50.0,10,40.0,20,30.0))
				.withDescription("Una de carne,dos de pollo, una de jyq, una de pollo, dos de carne...")
				.withProvider(jose)
				.build();

		menuRepository.save(empanadas);

	}

}
