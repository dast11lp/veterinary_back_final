package io.demo.veterinary.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtil {
	
	private static final Algorithm KEY = Algorithm.HMAC256("YAVE CEQRETA");
	
	public String jwtCreator(String email){
	
		
		return JWT.create().withSubject("User Details")
				.withClaim("email", email)
				.withIssuedAt(new Date())
			//	.withExpiresAt(new Date(System.currentTimeMillis() + 3600000 * 4 ))
				.withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
				.sign(KEY);
	}
	
	
	public String  jwtVerifier(String token) {
		JWTVerifier verifier = JWT.require(KEY)
				   .withSubject("User Details")
				   .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
				   .build();
		
		DecodedJWT jwt = verifier.verify(token);
		
		
		
		return jwt.getClaim("email").asString();
		
	}
}
