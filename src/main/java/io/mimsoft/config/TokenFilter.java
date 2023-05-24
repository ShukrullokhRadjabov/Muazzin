package io.mimsoft.config;
import io.jsonwebtoken.JwtException;
import io.mimsoft.config.security.CustomUserDetailsService;
import io.mimsoft.dto.JwtDTO;
import io.mimsoft.entity.ProfileEntity;
import io.mimsoft.exceptions.AppBadRequestException;
import io.mimsoft.repository.ProfileRepository;
import io.mimsoft.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;
@Component
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private ProfileRepository profileRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Message", "Token Not Found.");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        JwtDTO jwtDto;
        try {
            jwtDto = JwtUtil.decode(token);
            Optional<ProfileEntity> optional = profileRepository.findByPhone(jwtDto.getPhone());
            if(!optional.isPresent()){
                throw new AppBadRequestException("Wrong token");
            }
            ProfileEntity profile = optional.get();
            if(profile.getSessionCode()==null || !profile.getSessionCode().equals(jwtDto.getSessionCode())){
                throw new AppBadRequestException("Session code wrong");
            }
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtDto.getPhone());

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Message", "Token Not Valid");
        }
    }
}
