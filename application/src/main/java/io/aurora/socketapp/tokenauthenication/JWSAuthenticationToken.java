package io.aurora.socketapp.tokenauthenication;

import io.aurora.socketapp.channel.domain.User;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JWSAuthenticationToken extends AbstractAuthenticationToken implements Authentication
{

    private static final long serialVersionUID = 1L;

    private String token;
    private User user;

    public JWSAuthenticationToken(String token) {
        this(token, null, null);
    }

    public JWSAuthenticationToken(String token, User user, Collection<GrantedAuthority> authorities)
    {
        super(authorities);
        this.token = token;
        this.user = user;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal()
    {
        return user.toJsonString();
    }

}