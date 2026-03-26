package com.packt.cardatabase;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.packt.cardatabase.domain.AppUser;
import com.packt.cardatabase.domain.AppUserRepository;
import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner{
	
	private static final Logger Logger=
			LoggerFactory.getLogger(CardatabaseApplication.class);
	private final CarRepository repository;
	private final OwnerRepository orepository;
	private final AppUserRepository urepository;
	private final PasswordEncoder passwordEncoder;
	public 	CardatabaseApplication(CarRepository repository, OwnerRepository orepository, AppUserRepository urepository, PasswordEncoder passwordEncoder) {
		this.repository= repository;
		this.orepository = orepository;
		this.urepository = urepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		
		//Add owners
		Owner owner1 = new Owner("Romaric","Sieyamdji");
		Owner owner2 = new Owner("Rhodian","Ndamen");
		Owner owner3 = new Owner("Sara","Chartrand");
		orepository.saveAll(Arrays.asList(owner1, owner2, owner3));
		
		repository.save (new Car( "Ford", "Mustang", "Red", "ADF-1121", 2023, 59000, owner3));
		repository.save (new Car("Mercedes", "Class S", "Black", "ADF-1221", 2023, 50000, owner1));
		repository.save (new Car( "Honda", "CR-V", "Yellow", "ADF-1321", 2024, 39000, owner2));
		repository.save (new Car("Toyota", "Sequoia", "Blue", "ADF-1421", 2026, 89000, owner1));
		
		//fetch all cars and log them in the console
		for(Car car: repository.findAll()) {
			Logger.info("brand:{}, model:{}", car.getBrand(), car.getModel());
		}
		//username:user,password:user
		urepository.save(new AppUser("user", passwordEncoder.encode("user"), "USER"));
		urepository.save(new AppUser("admin", passwordEncoder.encode("admin"), "ADMIN"));
	}

}
