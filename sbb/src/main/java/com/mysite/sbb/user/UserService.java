package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
    
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
    
//    @Value("${kakao.client.id}")
//    private String KAKAO_CLIENT_ID;
//    
//    @Value("${kakao.client.secret}")
//    private String KAKAO_CLIENT_SECRET;
//    
//    @Value("${kakao.redirect.url}")
//    private String KAKAO_REDIRECT_URL;
//    
//    private final static String KAKAO_AUTH_URI = "https//kauth.kakao.com";
//    private final static String KAKAO_API_URI = "https//kapi.kakao.com";
//    
//    public String getKakaoLogin() {
//    	return KAKAO_AUTH_URI + "/oauth/authorize"
//    			+ "?client_id=" + KAKAO_CLIENT_ID
//    			+ "&redirect_url" + KAKAO_REDIRECT_URL
//    			+ "&response_type=code";
//    }
    
    
}