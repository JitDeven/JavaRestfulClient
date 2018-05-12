package ModelView;

public class TokenResult {
    public String result;
    public Integer ttl;
    public String msg;
    public String token;

    public TokenResult() {
    }

    public TokenResult(String result, Integer ttl, String msg, String token) {
        this.result = result;
        this.ttl = ttl;
        this.msg = msg;
        this.token = token;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
