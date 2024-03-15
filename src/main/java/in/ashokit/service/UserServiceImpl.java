package in.ashokit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.RegisterForm;
import in.ashokit.binding.ResetPasswordForm;
import in.ashokit.entity.City;
import in.ashokit.entity.Country;
import in.ashokit.entity.State;
import in.ashokit.entity.User;
import in.ashokit.repo.CityRepository;
import in.ashokit.repo.CountryRepository;
import in.ashokit.repo.StateRepository;
import in.ashokit.repo.UserRepository;
import in.ashokit.utils.EmailUtils;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public Map<Integer, String> getCountries() {
		Map<Integer,String> countries=new HashMap<>();
		List<Country> findAll = countryRepo.findAll();
		findAll.forEach(c->{
			countries.put(c.getCountryId(), c.getCountryName());			
		});
		return countries;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		Map<Integer,String> states=new HashMap<>();
		List<State> findByCountryId = stateRepo.findByCountryId(countryId);
		findByCountryId.forEach(s ->{
			states.put(s.getStateId(), s.getStateName());
		});
		
		return states;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		Map<Integer,String> cities=new HashMap<>();
		List<City> findByStateId = cityRepo.findByStateId(stateId);
		findByStateId.forEach(c ->{
			cities.put(c.getCityId(), c.getCityName());
		});
		
	
		return cities;
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public boolean saveUser(RegisterForm formObj) {
		
		formObj.setPwd(generateRandomPwd());
		formObj.setPwdUpdated("NO");
		
		User userEntity=new User();
		BeanUtils.copyProperties(formObj, userEntity);
		userRepo.save(userEntity);
		
		String subject="Your account is created - ASHOK IT";
		String body="your password is :"+formObj.getPwd();
		
		return emailUtils.sendEmail(subject, body, formObj.getEmail());
		
	}
	
	
	private String generateRandomPwd() {
		String alphaNumericCharacters="ABCDEFGHIJKLMNOPQRSTUVWXYZYZ0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuffer randomString=new StringBuffer(5);
		Random random=new Random();
		for(int i=0;i<=5;i++) {
			int randomIndex = random.nextInt(alphaNumericCharacters.length()-1);
			char randomChar= alphaNumericCharacters.charAt(randomIndex);
			randomString.append(randomChar);
		}
			return randomString.toString();
			
		
	}

	@Override
	public User login(LoginForm formObj) {
		
		return userRepo.findByEmailAndPwd(formObj.getEmail(), formObj.getPwd());
	}

	@Override
	public boolean resetPwd(ResetPasswordForm formObj) {
		Optional<User> findById = userRepo.findById(formObj.getUid());
		if(findById.isPresent()) {
			User user = findById.get();
			user.setPwd(formObj.getConfirmPwd());
			user.setPwdUpdated("YES");
			userRepo.save(user);
			return true;
		}
		return false;
	}

}
