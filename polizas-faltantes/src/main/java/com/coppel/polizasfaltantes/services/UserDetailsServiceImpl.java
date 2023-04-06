package com.coppel.polizasfaltantes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coppel.polizasfaltantes.models.Usuario;
import com.coppel.polizasfaltantes.repositories.UsuariosRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UsuariosRepository usuariosRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = usuariosRepository.findByEmail(email)
				.orElseThrow(
					() -> new UsernameNotFoundException("User Not Found with email: " + email)
				);

		return UserDetailsImpl.build(user);
	}

}