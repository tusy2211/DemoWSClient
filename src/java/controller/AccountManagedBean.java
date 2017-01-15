package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ws.*;

/**
 *
 * @author khanh
 */
@ManagedBean
@SessionScoped
public class AccountManagedBean {

    private String username;
    private String password;
    private UserWebService_Service wsac = new UserWebService_Service();
    private Account account = new Account();
    @ManagedProperty(value = "#{sessionManagedBean}")
    SessionManagedBean sessionManagedBean;

    public SessionManagedBean getSessionManagedBean() {
        return sessionManagedBean;
    }

    public void setSessionManagedBean(SessionManagedBean sessionManagedBean) {
        this.sessionManagedBean = sessionManagedBean;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Creates a new instance of AccountManagedBean
     */
    public AccountManagedBean() {
    }

    public String login() {
        if (wsac.getUserWebServicePort().login(username, password) != null) {
            sessionManagedBean.setUsername(username);
            return "index";
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null,
                new FacesMessage("Wrong username or password"));
        return null;
    }

    public List<Account> findAll() {
        return wsac.getUserWebServicePort().findall();
    }

    public String insert() {
        wsac.getUserWebServicePort().insert(account);
        return "index";
    }

    public void delete(String id) {
        wsac.getUserWebServicePort().delete(id);
    }

    public String update(Account accounts) {
        this.account = accounts;
        return "updateaccount";
    }

    public String processupdate() {
        wsac.getUserWebServicePort().update(account.getId(), account.getAccountnumber(), account.getFirstname(), account.getLastname(), account.getBalance());
        return "index";
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        return "login";
    }
}
