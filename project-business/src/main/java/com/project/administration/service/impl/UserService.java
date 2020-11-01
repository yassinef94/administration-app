package com.project.administration.service.impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.administration.view.model.VAdmUser;
import com.project.administration.view.repository.IVAdmUserRepository;

@Service
public class UserService implements UserDetailsService {

//	private static final Logger logger = LogManager.getLogger(UserService.class);

	@Autowired
	private IVAdmUserRepository vAdmUserRepository;

	@Override
	public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
		VAdmUser user = vAdmUserRepository.findVAdmUserById(UUID.fromString(uuid));
		if (user == null) {
			throw new UsernameNotFoundException("User not found with identif :: " + uuid);
		}
		return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(), getAuthorities(user));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(VAdmUser admUser) {
        String[] userRoles = {admUser.getRole()};
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
