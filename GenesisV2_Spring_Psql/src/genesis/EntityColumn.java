package genesis;

public class EntityColumn {
    private String name;
    private String type;
    private boolean primary;
    private boolean foreign;
    private String referencedTable;
    private String referencedColumn;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean isPrimary() {
        return primary;
    }
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
    public boolean isForeign() {
        return foreign;
    }
    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }
    public String getReferencedTable() {
        return referencedTable;
    }
    public void setReferencedTable(String referencedTable) {
        this.referencedTable = referencedTable;
    }
    public String getReferencedColumn() {
        return referencedColumn;
    }
    public void setReferencedColumn(String referencedColumn) {
        this.referencedColumn = referencedColumn;
    }
    
}
