package org.engripaye.secureinternaladminportal.service;

import org.engripaye.secureinternaladminportal.model.Permission;
import org.engripaye.secureinternaladminportal.model.Roles;
import org.engripaye.secureinternaladminportal.model.Users;
import org.engripaye.secureinternaladminportal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final String allowedDomain;

    public CustomOidcUserService(UserService userService, RoleRepository roleRepository,@Value("${app.allowed-google-domain}") String allowedDomain) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.allowedDomain = allowedDomain;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getEmail();
        String hd = (String) oidcUser.getClaims().get("hd");

        if (email == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("Invalid token", "no email in token", ""));
        }

        String domain = email.substring(email.indexOf("@") + 1);
        if (!domain.equalsIgnoreCase(allowedDomain) && (hd == null || !hd.equalsIgnoreCase(allowedDomain))) {
            throw new OAuth2AuthenticationException(new OAuth2Error("access_denied", "You must sign in with a company account (" + allowedDomain + ")", ""));
        }

        // create/update local user record
        String name = oidcUser.getFullName();
        String picture = (String) oidcUser.getClaims().get("picture");
        Users users = userService.createOrUpdateFromOidc(email, name, picture);

        // map roles/permission to granted authorities
        Set<GrantedAuthority> mappedAuthority = new HashSet<>();

        for (Roles r : users.getRoles()) {
            mappedAuthority.add(new SimpleGrantedAuthority(r.getName())); //ROLE_X
            for (Permission p : r.getPermissions()) {

                //Use authority prefix "PERM_" to avoid collision

                mappedAuthority.add(new SimpleGrantedAuthority("PERM_" + p.getName()));
            }


    }
        // Build a new Oidc with authorities
        Map<String, Object> attrs = new HashMap<>(oidcUser.getClaims());
        OidcIdToken idToken = oidcUser.getIdToken();
        OidcUserInfo userInfo = oidcUser.getUserInfo();

        return new DefaultOidcUser(mappedAuthority, idToken, userInfo, "email");
    }
}
