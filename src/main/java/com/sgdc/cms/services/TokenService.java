package com.sgdc.cms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sgdc.cms.models.Token;
import com.sgdc.cms.repositories.TokenRepository;
/**
 * TokenService
 */
@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;
	
	public Token blacklistToken(String token) {
		Token t = new Token(token,true);
		return tokenRepository.save(t);
	}

	public boolean isTokenBlacklisted(String jwtToken) {
		return tokenRepository.findById(jwtToken).isPresent();		
	}
}
