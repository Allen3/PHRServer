package Beans;

import Beans.PersonInfo;

/**
 * Created by Allen on 2015/7/27.
 */
public class LoginResponse extends PersonInfo {
    private String _LOGIN_RESPONSE_;

    public LoginResponse() {
        super();
    }

    public LoginResponse(PersonInfo personInfo) {
        super(personInfo);
    }

    public LoginResponse(String _LOGIN_RESPONSE_) {
        this._LOGIN_RESPONSE_ = _LOGIN_RESPONSE_;
    }

    public void set_LOGIN_RESPONSE(String _LOGIN_RESPONSE_) {
        this._LOGIN_RESPONSE_ = _LOGIN_RESPONSE_;
    }

    public String get_LOGIN_RESPONSE() {
        return _LOGIN_RESPONSE_;
    }
}
