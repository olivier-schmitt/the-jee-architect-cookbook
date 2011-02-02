package jee.architect.cookbook;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "loginBackingBean")
@RequestScoped
public class LoginBackingBean {

    private String login;
    private String password;

    /**
     * default empty constructor
     */
    public LoginBackingBean() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
        System.out.println("Log in with  " + login + " " + password);
    }
}
