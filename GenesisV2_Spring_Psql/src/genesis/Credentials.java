package genesis;

public class Credentials {
    private String databaseName, user, pwd, host;
    private boolean useSSL, allowPublicKeyRetrieval;
    public String getDatabaseName() {
        return databaseName;
    }
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public boolean isUseSSL() {
        return useSSL;
    }
    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }
    public boolean isAllowPublicKeyRetrieval() {
        return allowPublicKeyRetrieval;
    }
    public void setAllowPublicKeyRetrieval(boolean allowPublicKeyRetrieval) {
        this.allowPublicKeyRetrieval = allowPublicKeyRetrieval;
    }
    public Credentials(String databaseName, String user, String pwd, String host, boolean useSSL,
            boolean allowPublicKeyRetrieval) {
        this.databaseName = databaseName;
        this.user = user;
        this.pwd = pwd;
        this.host = host;
        this.useSSL = useSSL;
        this.allowPublicKeyRetrieval = allowPublicKeyRetrieval;
    }
    
    
}
