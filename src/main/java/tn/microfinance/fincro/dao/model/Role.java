package tn.microfinance.fincro.dao.model;
import static tn.microfinance.fincro.dao.constant.Authority.*;

public enum Role {
    ROLE_CLIENT(CLIENT_AUTHORITIES),
    ROLE_GUARANTOR( GUARANTOR_AUTHORITIES ),
    ROLE_ADMIN(ADMIN_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
