package in.ashokit.service;

import java.util.Map;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.RegisterForm;
import in.ashokit.binding.ResetPasswordForm;
import in.ashokit.entity.User;

public interface UserService {
	
	public Map<Integer,String> getCountries();
	public Map<Integer,String> getStates(Integer countryId);
	public Map<Integer,String> getCities(Integer stateId);
	public User getUser(String email);
	public boolean saveUser(RegisterForm formObj);
	public User login(LoginForm formObj);
	public boolean resetPwd(ResetPasswordForm formObj);
	

}
