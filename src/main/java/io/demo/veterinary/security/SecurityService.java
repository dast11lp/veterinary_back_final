package io.demo.veterinary.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.demo.veterinary.entity.AppUser;
import io.demo.veterinary.entity.Role;
import io.demo.veterinary.repository.AppUserRepository;



@Service("securityService")
public class SecurityService {

    @Autowired
    private AppUserRepository myUserService;
    
    Authentication authentication;

        public boolean hasUser(Long idUser){
        	
        System.out.println("hola " + idUser);

        AppUser user = this.myUserService.findById(idUser).orElse(null);
        
        System.out.println("hola: "+ user.getFirstname());
        
        Collection<GrantedAuthority>  authoritiesUser = new ArrayList<GrantedAuthority>();
        
        for (Role role: user.getRoles()) {
			authoritiesUser.add(new SimpleGrantedAuthority(role.getRole()));
		}
        
        for (Role role: user.getRoles()) {
			System.out.println("tus roles: " + role.getRole());
		}
		
		Collection<GrantedAuthority> authoritiesContext = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		
		for (GrantedAuthority authority: authoritiesContext) {
			System.out.println("roles en el contexto: " + authority);
		}
		
		
		for(GrantedAuthority authority: authoritiesUser) {
			if(authoritiesContext.contains(authority) || authoritiesContext.contains("ROLE_VET")) {
				return true;
			}
			break;
		}
        

        return false;  
    }
}
