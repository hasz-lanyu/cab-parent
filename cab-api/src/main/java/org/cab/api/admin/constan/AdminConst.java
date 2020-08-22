package org.cab.api.admin.constan;




import java.util.Arrays;
import java.util.List;

public class AdminConst {
    public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";



    public static final class Cache{


    }


    public static final class Security {
        public static final String DOLOGIN_URL = "/dologin";
        public static final String LOGIN_URL = "/login";
        public static final String SUCCESS_URL = "/index";
        public static final String ROOT_URL = "/";
        public static final String UNAUTHORIZED_URL = "/403";
        public static final String LOGOUT_URL = "/logout";
        public static final String STATIC_URL = "/static/**";
        public static final String FILTER_URL = "/**";
        public static final int PASSWORD_ENCRYPTION_COUNT = 31;
        public static final long SESSION_KEEP_ALIVE_TIME = 144000000L;
        public static final List<String> EXCLUDEURL=Arrays.asList(DOLOGIN_URL,LOGOUT_URL,
                LOGIN_URL,STATIC_URL);

    }
}
