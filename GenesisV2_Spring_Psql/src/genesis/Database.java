package genesis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class Database {
    private int id;
    private String nom;
    private String driver;
    private String port;
    private HashMap<String, String> types;
    private String getcolumnsQuery;
    private String gettablesQuery;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public HashMap<String, String> getTypes() {
        return types;
    }
    public void setTypes(HashMap<String, String> types) {
        this.types = types;
    }
    public String getGetcolumnsQuery() {
        return getcolumnsQuery;
    }
    public void setGetcolumnsQuery(String getcolumnsQuery) {
        this.getcolumnsQuery = getcolumnsQuery;
    }
    public String getGettablesQuery() {
        return gettablesQuery;
    }
    public void setGettablesQuery(String gettablesQuery) {
        this.gettablesQuery = gettablesQuery;
    }
    public Connection getConnexion(Credentials credentials) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        String url="jdbc:%s://%s:%s/%s?user=%s&password=%s&useSSL=%s&allowPublicKeyRetrieval=%s";
        url=String.format(url, getNom(), credentials.getHost(), getPort(), credentials.getDatabaseName(), credentials.getUser(), credentials.getPwd(), credentials.isUseSSL(), credentials.isAllowPublicKeyRetrieval());
        Connection connex=DriverManager.getConnection(url);
        connex.setAutoCommit(false);
        return connex; 
    }
    public Entity[] getEntities(Connection connex, Credentials credentials, String entityName) throws ClassNotFoundException, SQLException{
        boolean opened=false;
        Connection connect=connex;
        if(connect==null){
            connect=getConnexion(credentials);
            opened=true;
        }
        String query=getGettablesQuery().replace("[databaseName]", credentials.getDatabaseName());
        PreparedStatement statement=connect.prepareStatement(query);
        try{
            Vector<Entity> liste=new Vector<>();
            Entity entity;
            try(ResultSet result=statement.executeQuery()){
                while(result.next()){
                    entity=new Entity();
                    entity.setTableName(result.getString("table_name"));
                    liste.add(entity);
                }
            }
            Entity[] entities=new Entity[liste.size()];
            for(int i=0;i<entities.length;i++){
                entities[i]=liste.get(i);
            }
            return entities;
        }finally{
            statement.close();
            if(opened){
                connect.close();
            }
        }
    }
}
