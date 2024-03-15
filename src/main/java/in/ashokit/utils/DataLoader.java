package in.ashokit.utils;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.ashokit.entity.City;
import in.ashokit.entity.Country;
import in.ashokit.entity.State;
import in.ashokit.repo.CityRepository;
import in.ashokit.repo.CountryRepository;
import in.ashokit.repo.StateRepository;

@Component
public class DataLoader implements ApplicationRunner{
	@Autowired
	private CountryRepository countryRepo;
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CityRepository cityRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		countryRepo.deleteAll();
		stateRepo.deleteAll();
		cityRepo.deleteAll();
		
		Country c1=new Country(1,"India");
		Country c2=new Country(2,"USA");
		Country c3=new Country(3,"Nepal");
		Country c4=new Country(4,"Australia");
		countryRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		State s1=new State(1,"Odisha",1);
		State s2=new State(2,"HimachalPradesh",1);
		
		State s3=new State(3,"California",2);
		State s4=new State(4,"Michigan",2);
		
		State s5=new State(5,"Bagmati",3);
		State s6=new State(6,"Lumbini",3);
		
		State s7=new State(7,"Queensland",4);
		State s8=new State(8,"Tasmania",4);
		stateRepo.saveAll(Arrays.asList(s1,s2,s3,s4,s5,s6,s7,s8));
		
		City ct1=new City(1,"Angul",1);
		City ct2=new City(2,"Balasore",1);
		
		City ct3=new City(3,"Manali",2);
		City ct4=new City(4,"Dharamsala",2);
		
		City ct5=new City(5,"Los Angeles",3);
		City ct6=new City(6,"San Francisco",3);
		
		City ct7=new City(7,"Detroit",4);
		City ct8=new City(8,"Lansing",4);
		
		City ct9=new City(9,"Kathmandu",5);
		City ct10=new City(10,"Lalitpur",5);
		
		City ct11=new City(11,"Kapilvastu",6);
		City ct12=new City(12,"Siddharthnagar",6);
		
		City ct13=new City(13,"Brisbane",7);
		City ct14=new City(14,"Cairns",7);
		
		City ct15=new City(15,"Hobart",8);
		City ct16=new City(16,"Burnie",8);
		cityRepo.saveAll(Arrays.asList(ct1,ct2,ct3,ct4,ct5,ct6,ct7,ct8,ct9,ct10,ct11,ct12,ct13,ct14,ct15,ct16));
	
	}
	

}
