package in.ashokit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.RegisterForm;
import in.ashokit.binding.ResetPasswordForm;
import in.ashokit.entity.User;
import in.ashokit.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService; 
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("login", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String loginCheck(@ModelAttribute("login") LoginForm login,Model model) {
		User user = userService.login(login);
		if(user==null) {
			model.addAttribute("errMsg", "Invalid Credentials");
		}
		if(user.getPwdUpdated().equals("NO")) {
			//model.addAttribute("userId", user.getUid());
			ResetPasswordForm formObj=new ResetPasswordForm();
			formObj.setUid(user.getUid());
			
			model.addAttribute("pwd", formObj);
			return "resetPwd";
		}
		return "redirect:dashboard";
	}
	@PostMapping("/resetPwd")
	public String resetPwd(ResetPasswordForm reset,Model model) {
		
		if(!reset.getNewPwd().equals(reset.getConfirmPwd())) {
			model.addAttribute("errMsg", "Both password should be same");
			return "resetPwd";
		}
		
		boolean status = userService.resetPwd(reset);
		if(status) {
			return "redirect:dashboard";
		}
		model.addAttribute("errMsg", "Password is failed to update");
		return "resetPwd";
	}
	@GetMapping("/register")
	public String loadRegisterPage(Model model) {
		model.addAttribute("register", new RegisterForm());
		
		Map<Integer, String> countries = userService.getCountries();
		model.addAttribute("countries", countries);
		
	    return "register";
	}
	
	@GetMapping("/states")
	@ResponseBody
	public Map<Integer,String> getStates(@RequestParam("countryId") Integer countryId){
		Map<Integer, String> states = userService.getStates(countryId);
		return states;
	}
	
	@GetMapping("/cities")
	@ResponseBody
	public Map<Integer,String> getCities(@RequestParam("stateId") Integer stateId){
		return userService.getCities(stateId);
		
	}
	
	@PostMapping("/register")
	public String saveUser(@ModelAttribute("register") RegisterForm form,Model model) {
		
		
		boolean status = userService.saveUser(form);
		if(status) {
			model.addAttribute("succMsg", "Registration Successful");
			
		}
		else {
			model.addAttribute("errMsg", "Registration Failed");
		}
		
		Map<Integer, String> countries = userService.getCountries();
		model.addAttribute("countries", countries);
		
		
		return "register";
		
	}
}




