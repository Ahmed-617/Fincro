package tn.microfinance.fincro.dao.constant;

public class Authority {
    public static final String[] CLIENT_AUTHORITIES = { "user:read" };
    public static final String[] GUARANTOR_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete" };

}
