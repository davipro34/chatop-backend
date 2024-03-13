package fr.davipro.chatopbackend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

	private JwtEncoder jwtEncoder;
	private JwtDecoder jwtDecoder;

	public JWTService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
	}

	public String generateToken(Authentication authentication) {
	Instant now = Instant.now();
	JwtClaimsSet claims = JwtClaimsSet.builder()
		.issuer("self")
		.issuedAt(now)
		.expiresAt(now.plus(1, ChronoUnit.DAYS))
		.subject(authentication.getName())
		.build();
	JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(SignatureAlgorithm.RS256).build(), claims);
		return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
	}

	public String getEmailFromToken(String token) {
		Jwt jwt = jwtDecoder.decode(token);
		return jwt.getClaimAsString("sub");
	}

	public boolean verifyEmailInToken(String token, String email) {
		String emailInToken = getEmailFromToken(token);
		return email.equals(emailInToken);
	}
}
