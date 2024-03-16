package genesis;

public class CustomChanges {
    private String path;
    private String changes;
    private boolean withEndComma;
    public boolean isWithEndComma() {
        return withEndComma;
    }
    public void setWithEndComma(boolean withEndComma) {
        this.withEndComma = withEndComma;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getChanges() {
        return changes;
    }
    public void setChanges(String changes) {
        this.changes = changes;
    }
    
}
