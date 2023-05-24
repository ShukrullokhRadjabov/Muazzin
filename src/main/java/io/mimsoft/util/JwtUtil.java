package io.mimsoft.util;

import io.jsonwebtoken.*;
import io.mimsoft.dto.JwtDTO;
import io.mimsoft.enums.UserRole;
import io.mimsoft.exceptions.MethodNotAllowedException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24;
    private static final String secretKey = "dasda143mazgi";

    public static String encode(String phone, UserRole role, String sessionCode) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("session_code", sessionCode);
        jwtBuilder.claim("phone", phone);
        jwtBuilder.claim("role", role);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Muazzin");
        return jwtBuilder.compact();
    }
    public static JwtDTO decode(String token) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(token);
        Claims claims = jws.getBody();

        String sessionCode = (String) claims.get("session_code");
        String phone = (String) claims.get("phone");
        String role = (String) claims.get("role");
        UserRole profileRole = UserRole.valueOf(role);
        return new JwtDTO(sessionCode, phone, profileRole);
    }



    public static JwtDTO getJwtDTO(String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        return JwtUtil.decode(jwt);
    }

    public static JwtDTO getJwtUtil(String authorization, UserRole... roleList) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        boolean roleFound = false;
        for (UserRole role : roleList) {
            if (jwtDTO.getRole().equals(role)) {
                roleFound = true;
                break;
            }
        }
        if (!roleFound) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return jwtDTO;
    }
    public static void checkForRequiredRole(HttpServletRequest request, UserRole... roleList) {
        UserRole jwtRole = (UserRole) request.getAttribute("role");
        boolean roleFound = false;
        for (UserRole role : roleList) {
            if (jwtRole.equals(role)) {
                roleFound = true;
                break;
            }
        }
        if (!roleFound) {
            throw new MethodNotAllowedException("Method not allowed");
        }
    }

}
