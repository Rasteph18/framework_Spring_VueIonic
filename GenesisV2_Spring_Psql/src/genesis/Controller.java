package genesis;

import java.util.HashMap;

public class Controller {
    private String[] controllerImports;
    private String[] controllerAnnotations;
    private String controllerExtends;
    private String controllerName;
    private ControllerField[] controllerFields;
    private ControllerField controllerFieldsForeign;
    private HashMap<String, String> controllerChangeInstanciation, controllerWhereInstanciation, controllerForeignInstanciation;
    private String controllerForeignList;
    private String controllerForeignContextParam, controllerForeignContextInstanciation;
    private String[] controllerConstructors;
    private String controllerForeignInclude;
    private ControllerMethod[] controllerMethods;
    private String controllerSavepath;
    private String controllerExtension;
    private String controllerPackage, controllerTemplate, controllerNameSuffix;
    public String getControllerNameSuffix() {
        return controllerNameSuffix;
    }
    public void setControllerNameSuffix(String controllerNameSuffix) {
        this.controllerNameSuffix = controllerNameSuffix;
    }
    public String getControllerTemplate() {
        return controllerTemplate;
    }
    public void setControllerTemplate(String controllerTemplate) {
        this.controllerTemplate = controllerTemplate;
    }
    public String[] getControllerImports() {
        return controllerImports;
    }
    public void setControllerImports(String[] controllerImports) {
        this.controllerImports = controllerImports;
    }
    public String[] getControllerAnnotations() {
        return controllerAnnotations;
    }
    public void setControllerAnnotations(String[] controllerAnnotations) {
        this.controllerAnnotations = controllerAnnotations;
    }
    public String getControllerExtends() {
        return controllerExtends;
    }
    public void setControllerExtends(String controllerExtends) {
        this.controllerExtends = controllerExtends;
    }
    public String getControllerName() {
        return controllerName;
    }
    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }
    public ControllerField[] getControllerFields() {
        return controllerFields;
    }
    public void setControllerFields(ControllerField[] controllerFields) {
        this.controllerFields = controllerFields;
    }
    public ControllerField getControllerFieldsForeign() {
        return controllerFieldsForeign;
    }
    public void setControllerFieldsForeign(ControllerField controllerFieldsForeign) {
        this.controllerFieldsForeign = controllerFieldsForeign;
    }
    public HashMap<String, String> getControllerChangeInstanciation() {
        return controllerChangeInstanciation;
    }
    public void setControllerChangeInstanciation(HashMap<String, String> controllerChangeInstanciation) {
        this.controllerChangeInstanciation = controllerChangeInstanciation;
    }
    public HashMap<String, String> getControllerWhereInstanciation() {
        return controllerWhereInstanciation;
    }
    public void setControllerWhereInstanciation(HashMap<String, String> controllerWhereInstanciation) {
        this.controllerWhereInstanciation = controllerWhereInstanciation;
    }
    public HashMap<String, String> getControllerForeignInstanciation() {
        return controllerForeignInstanciation;
    }
    public void setControllerForeignInstanciation(HashMap<String, String> controllerForeignInstanciation) {
        this.controllerForeignInstanciation = controllerForeignInstanciation;
    }
    public String getControllerForeignList() {
        return controllerForeignList;
    }
    public void setControllerForeignList(String controllerForeignList) {
        this.controllerForeignList = controllerForeignList;
    }
    public String getControllerForeignContextParam() {
        return controllerForeignContextParam;
    }
    public void setControllerForeignContextParam(String controllerForeignContextParam) {
        this.controllerForeignContextParam = controllerForeignContextParam;
    }
    public String getControllerForeignContextInstanciation() {
        return controllerForeignContextInstanciation;
    }
    public void setControllerForeignContextInstanciation(String controllerForeignContextInstanciation) {
        this.controllerForeignContextInstanciation = controllerForeignContextInstanciation;
    }
    public String[] getControllerConstructors() {
        return controllerConstructors;
    }
    public void setControllerConstructors(String[] controllerConstructors) {
        this.controllerConstructors = controllerConstructors;
    }
    public String getControllerForeignInclude() {
        return controllerForeignInclude;
    }
    public void setControllerForeignInclude(String controllerForeignInclude) {
        this.controllerForeignInclude = controllerForeignInclude;
    }
    public ControllerMethod[] getControllerMethods() {
        return controllerMethods;
    }
    public void setControllerMethods(ControllerMethod[] controllerMethods) {
        this.controllerMethods = controllerMethods;
    }
    public String getControllerSavepath() {
        return controllerSavepath;
    }
    public void setControllerSavepath(String controllerSavepath) {
        this.controllerSavepath = controllerSavepath;
    }
    public String getControllerExtension() {
        return controllerExtension;
    }
    public void setControllerExtension(String controllerExtension) {
        this.controllerExtension = controllerExtension;
    }
    public String getControllerPackage() {
        return controllerPackage;
    }
    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }
    
}
