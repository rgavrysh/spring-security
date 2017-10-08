package com.dao.user;

import java.util.HashSet;
import java.util.Set;

public class UserRepository {
    public static Role admin = new Role(1, "ROLE_ADMIN");
    public static Role user = new Role(2, "ROLE_USER");

    public static User roman = new User(1, "roman", "roman", "qwe123", new HashSet<>());
    public static User vova = new User(2, "vova", "vova", "1111", new HashSet<>());
//
    private static Set<Role> fullAccess = new HashSet<>();
    static {
        fullAccess.add(admin);
        fullAccess.add(user);
    }
    private static Set<Role> userAccess = new HashSet<>();
    static {
        userAccess.add(user);
    }
    static {
        roman.setRoles(fullAccess);
    }

    static {
        vova.setRoles(userAccess);
    }


    public static User findByLogin(String username) {
        if (username.equalsIgnoreCase(roman.getLogin())) {
            return roman;
        } else if (username.equalsIgnoreCase(vova.getLogin())) {
            return vova;
        } else {
            return null;
        }
    }
}
