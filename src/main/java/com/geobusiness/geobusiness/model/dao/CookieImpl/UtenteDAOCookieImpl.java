package com.geobusiness.geobusiness.model.dao.CookieImpl;

import com.geobusiness.geobusiness.model.dao.UtenteDAO;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Utente;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UtenteDAOCookieImpl implements UtenteDAO {

    HttpServletRequest request;
    HttpServletResponse response;

    public UtenteDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
    @Override
    public Utente create(
            Integer id,
            String Username,
            String Paswword
    ) {
        Utente loggedUser = new Utente();
        loggedUser.setId(id);
        loggedUser.setUsername(Username);

        Cookie cookie;
        cookie = new Cookie("loggedUser", encode(loggedUser));
        cookie.setPath("/");
        response.addCookie(cookie);

        return loggedUser;
    }

    @Override
    public void update(Utente loggedUser) {
        Cookie cookie;
        cookie = new Cookie("loggedUser", encode(loggedUser));
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    @Override
    public void delete(Utente utente) {
        Cookie cookie;
        cookie = new Cookie("loggedUser", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public Utente findLoggedUtente() {
        Cookie[] cookies = request.getCookies();
        Utente loggedUser = null;

        if (cookies != null) {
            for (int i = 0; i < cookies.length && loggedUser == null; i++) {
                if (cookies[i].getName().equals("loggedUser")) {
                    loggedUser = decode(cookies[i].getValue());
                }
            }
        }

        return loggedUser;
    }

    @Override
    public Utente findByUtenteId(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String encode(Utente loggedUser) {

        String encodedLoggedUser;
        encodedLoggedUser = loggedUser.getId() + "#" + loggedUser.getUsername();
        return encodedLoggedUser;

    }

    private Utente decode(String encodedLoggedUser) {

        Utente loggedUser = new Utente();

        String[] values = encodedLoggedUser.split("#");

        loggedUser.setId(Integer.parseInt(values[0]));
        loggedUser.setUsername(values[1]);

        return loggedUser;

    }
}
