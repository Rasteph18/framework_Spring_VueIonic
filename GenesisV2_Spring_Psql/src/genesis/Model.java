package genesis;

public class Model {
    private String[] modelImports;
    private String modelExtends;
    private String[] modelAnnotations;
    private String[] modelPrimaryFieldAnnotations;
    private String[] modelForeignFieldAnnotations;
    private String[] modelFieldAnnotations;
    private String modelFieldCase;
    private String modelFieldContent;
    private String modelGetter;
    private String modelSetter;
    private String[] modelConstructors;
    private String modelSavePath;
    private String modelForeignContextAttr;
    private CustomFile[] modelAdditionnalFiles;
    private String modelExtension;
    private String modelPackage;
    private String modelTemplate;
    public String getModelTemplate() {
        return modelTemplate;
    }
    public void setModelTemplate(String modelTemplate) {
        this.modelTemplate = modelTemplate;
    }
    public String[] getModelImports() {
        return modelImports;
    }
    public void setModelImports(String[] modelImports) {
        this.modelImports = modelImports;
    }
    public String getModelExtends() {
        return modelExtends;
    }
    public void setModelExtends(String modelExtends) {
        this.modelExtends = modelExtends;
    }
    public String[] getModelAnnotations() {
        return modelAnnotations;
    }
    public void setModelAnnotations(String[] modelAnnotations) {
        this.modelAnnotations = modelAnnotations;
    }
    public String[] getModelPrimaryFieldAnnotations() {
        return modelPrimaryFieldAnnotations;
    }
    public void setModelPrimaryFieldAnnotations(String[] modelPrimaryFieldAnnotations) {
        this.modelPrimaryFieldAnnotations = modelPrimaryFieldAnnotations;
    }
    public String[] getModelForeignFieldAnnotations() {
        return modelForeignFieldAnnotations;
    }
    public void setModelForeignFieldAnnotations(String[] modelForeignFieldAnnotations) {
        this.modelForeignFieldAnnotations = modelForeignFieldAnnotations;
    }
    public String[] getModelFieldAnnotations() {
        return modelFieldAnnotations;
    }
    public String getModelForeignContextAttr() {
        return modelForeignContextAttr;
    }
    public void setModelForeignContextAttr(String modelForeignContextAttr) {
        this.modelForeignContextAttr = modelForeignContextAttr;
    }
    public void setModelFieldAnnotations(String[] modelFieldAnnotations) {
        this.modelFieldAnnotations = modelFieldAnnotations;
    }
    public String getModelFieldCase() {
        return modelFieldCase;
    }
    public void setModelFieldCase(String modelFieldCase) {
        this.modelFieldCase = modelFieldCase;
    }
    public String getModelFieldContent() {
        return modelFieldContent;
    }
    public void setModelFieldContent(String modelFieldContent) {
        this.modelFieldContent = modelFieldContent;
    }
    public String getModelGetter() {
        return modelGetter;
    }
    public void setModelGetter(String modelGetter) {
        this.modelGetter = modelGetter;
    }
    public String getModelSetter() {
        return modelSetter;
    }
    public void setModelSetter(String modelSetter) {
        this.modelSetter = modelSetter;
    }
    public String[] getModelConstructors() {
        return modelConstructors;
    }
    public void setModelConstructors(String[] modelConstructors) {
        this.modelConstructors = modelConstructors;
    }
    public String getModelSavePath() {
        return modelSavePath;
    }
    public void setModelSavePath(String modelSavePath) {
        this.modelSavePath = modelSavePath;
    }
    public CustomFile[] getModelAdditionnalFiles() {
        return modelAdditionnalFiles;
    }
    public void setModelAdditionnalFiles(CustomFile[] modelAdditionnalFiles) {
        this.modelAdditionnalFiles = modelAdditionnalFiles;
    }
    public String getModelExtension() {
        return modelExtension;
    }
    public void setModelExtension(String modelExtension) {
        this.modelExtension = modelExtension;
    }
    public String getModelPackage() {
        return modelPackage;
    }
    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }
    
}
