import java.io.File;
import java.sql.Connection;
import java.util.Scanner;

import genesis.Constantes;
import genesis.Credentials;
import genesis.CustomChanges;
import genesis.CustomFile;
import genesis.Database;
import genesis.Entity;
import genesis.EntityField;
import genesis.Language;
import handyman.HandyManUtils;
import utils.Utils;

public class App {
    public static void main(String[] args) throws Throwable {
        Database[] databases = HandyManUtils.fromJson(Database[].class,
                HandyManUtils.getFileContent(Constantes.DATABASE_JSON));
        Language[] languages = HandyManUtils.fromJson(Language[].class,
                HandyManUtils.getFileContent(Constantes.LANGUAGE_JSON));
        Database database;
        Language language;
        String databaseName, user, pwd, host;
        boolean useSSL, allowPublicKeyRetrieval;
        String projectName, entityName;
        Credentials credentials;
        String projectNameTagPath, projectNameTagContent;
        File project, credentialFile, projectVue;
        String customFilePath, customFileContentOuter;
        Entity[] entities;
        String[] models, controllers, views;
        String modelFile, controllerFile, viewFile, customFile;
        String customFileContent;
        String foreignContext;
        String customChanges, changesFile;
        String navLink, navLinkPath;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Choose a database engine:");
            for (int i = 0; i < databases.length; i++) {
                System.out.println((i + 1) + ") " + databases[i].getNom());
            }
            System.out.print("> ");
            database = databases[scanner.nextInt() - 1];
            System.out.println("Choose a framework:");
            for (int i = 0; i < languages.length; i++) {
                System.out.println((i + 1) + ") " + languages[i].getNom());
            }
            System.out.print("> ");
            language = languages[scanner.nextInt() - 1];
            System.out.println("Enter your database credentials:");
            System.out.print("Database name: ");
            databaseName = scanner.next();
            System.out.print("Username: ");
            user = scanner.next();
            System.out.print("Password: ");
            pwd = scanner.next();
            System.out.print("Database host: ");
            host = scanner.next();
            System.out.print("Use SSL ?(Y/n): ");
            useSSL = scanner.next().equalsIgnoreCase("Y");
            System.out.print("Allow public key retrieval ?(Y/n): ");
            allowPublicKeyRetrieval = scanner.next().equalsIgnoreCase("Y");
            System.out.println();
            System.out.print("Enter your project name: ");
            projectName = scanner.next();
            System.out.print("Which entities to import ?(* to select all): ");
            entityName = scanner.next();
            credentials = new Credentials(databaseName, user, pwd, host, useSSL, allowPublicKeyRetrieval);
            project = new File(projectName);
            project.mkdir();
            for (CustomFile c : language.getAdditionnalFiles()) {
                customFilePath = c.getName();
                customFilePath = customFilePath.replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
                HandyManUtils.createFile(customFilePath);
                customFileContentOuter = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + c.getContent())
                        .replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
                HandyManUtils.overwriteFileContent(customFilePath, customFileContentOuter);
            }
            HandyManUtils.extractDir(
                    Constantes.DATA_PATH + "/" + language.getSkeleton() + "." + Constantes.SKELETON_EXTENSION,
                    project.getPath());


//--------------------------------------------
                projectVue = new File(projectName + "_" + language.getVue_skeleton());
                projectVue.mkdir();

                HandyManUtils.extractDir(
                        Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "." + Constantes.SKELETON_EXTENSION, 
                         projectVue.getPath());

                String TemplContent = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/HomePage." + Constantes.VUEIONIC_TEMPLATE_EXT).replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
                HandyManUtils.overwriteFileContent(projectVue.getPath() + language.getVue_views() + "Homepage.vue", TemplContent);

                File dataEntity = new File(projectVue.getPath() + language.getVue_data());
                dataEntity.mkdir(); 
                File flEntity = new File(projectVue.getPath() + language.getVue_data() + "/entity.ts");
                flEntity.createNewFile();

                String realData = ""; 
                String routePage = "";
                String importPage = "";

                try (Connection connect = database.getConnexion(credentials)) {
                        entities = database.getEntities(connect, credentials, entityName);
                        for (int i = 0; i < entities.length; i++) {
                                entities[i].initialize(connect, credentials, database, language);
                                realData += "{\n";
                                realData += "EntityName:'" + entities[i].getTableName() + "'\n";
                                realData += "},\n";

                                importPage += "import " + HandyManUtils.majStart(entities[i].getTableName()) + "Page from '../views/" + HandyManUtils.majStart(entities[i].getTableName()) + "Page.vue';\n";

                                routePage += "{\n";
                                routePage += "path:'/" + HandyManUtils.majStart(entities[i].getTableName()) + "',\n";
                                routePage += "name:'" + HandyManUtils.majStart(entities[i].getTableName()) + "',\n";
                                routePage += "component: " + HandyManUtils.majStart(entities[i].getTableName()) + "Page\n";
                                routePage += "},\n";


                                File pageEntity = new File(projectVue.getPath() + language.getVue_views() + HandyManUtils.majStart(entities[i].getTableName()) + "Page.vue");
                                pageEntity.createNewFile();

                                String Templpage = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/EntityPage." + Constantes.VUEIONIC_TEMPLATE_EXT);
                                Templpage = Templpage.replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
                                String header = "";
                                String tcontent = "";
                                EntityField pk = null;
                                for (int j = 0; j < entities[i].getFields().length; j++) {
                                        if(entities[i].getFields()[j].isPrimary()) pk = entities[i].getFields()[j];
                                }

                                tcontent += "<ion-row v-for=\"item in " + entities[i].getTableName() +"s\" :key=\"item." +pk.getName()+ "\">\n";
                                for (int j = 0; j < entities[i].getFields().length; j++) {
                                        header += "<ion-col>" + entities[i].getFields()[j].getName() + "</ion-col>\n";
                                        
                                        tcontent += "<ion-col>{{ item."+ entities[i].getFields()[j].getName() +" }}</ion-col>\n";
                                        
                                }
                                header += "<ion-col></ion-col>\n";
                                header += "<ion-col></ion-col>\n";
                                tcontent += "<ion-col><ion-button @click=\"handleDelete(item."+ pk.getName() +")\">delete</ion-button></ion-col>\n";
                                tcontent += "<ion-col><ion-button @click=\"openModal(item."+ pk.getName() + ")\">Update</ion-button></ion-col>\n";
                                tcontent += "</ion-row>\n";

                                String formContent = "";
                                String formContentUpdate =  "";
                                String fieldFormAjout = "";
                                String fieldFormUpdate = "";
                                String fieldHandleAjout = "";
                                String fieldHandleUpdate = "";
                                String dataselected = "";
                                String dataentity = entities[i].getTableName() + "s: [],\n";
                                String apisel = "";
                                
                                for (int j = 0; j < entities[i].getFields().length; j++) {
                                        
                                        if(entities[i].getFields()[j].isPrimary()){
                                                if( j == entities[i].getFields().length - 1){
                                                        fieldFormUpdate += entities[i].getFields()[j].getName() + ":''\n";
                                                        fieldHandleUpdate += entities[i].getFields()[j].getName() + ": formUpdate.value." + entities[i].getFields()[j].getName() + "\n";
                                                } else {
                                                        fieldFormUpdate += entities[i].getFields()[j].getName() + ":'',\n";
                                                        fieldHandleUpdate += entities[i].getFields()[j].getName() + ": formUpdate.value." + entities[i].getFields()[j].getName() + ",\n";
                                                }
                                                continue;
                                        }

                                        if(entities[i].getFields()[j].isForeign()){
                                                String realField = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/Select." + Constantes.VUEIONIC_TEMPLATE_EXT);
                                                realField = realField.replace("[Field]", entities[i].getFields()[j].getName());
                                                realField = realField.replace(fieldHandleAjout, realField);

                                                String realFieldUpdate = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/SelectUpdate." + Constantes.VUEIONIC_TEMPLATE_EXT);
                                                realFieldUpdate = realFieldUpdate.replace("[Field]", entities[i].getFields()[j].getName());

                                                Entity referenced = Entity.getEntity(connect, credentials, database, language, entities[i].getFields()[j].getReferencedTable());
                                                String label="";
                                                for (int k = 0; k < referenced.getFields().length; k++) {
                                                        if(referenced.getFields()[k].getType().equalsIgnoreCase("String")){
                                                                label = referenced.getFields()[k].getName();
                                                                break;
                                                        }
                                                }

                                                String option = "<ion-select v-model=\"form." + referenced.getColumns()[0].getReferencedTable() + "\" interface=\"popover\" placeholder=\"choose a "+ referenced.getColumns()[0].getReferencedTable() +"\">\n";
                                                option += "<ion-select-option v-for=\" option in " + referenced.getColumns()[0].getReferencedTable() + "s \" :key=\"option." + pk.getName() + "\" :value=\"option." + pk.getName() + "\">";
                                                option += "{{option." + label + "}}";
                                                option += "</ion-select-option>\n";
                                                option += "</ion-select>\n";
                                                formContent += option;
                                                dataselected += "selected" + referenced.getColumns()[0].getReferencedTable() + ": null,\n";
                                                dataentity += referenced.getColumns()[0].getReferencedTable() + "s: [],\n";

                                                String optionUpdate = "<ion-select v-model=\"formUpdate." + referenced.getColumns()[0].getReferencedTable() + "\" interface=\"popover\" :placeholder=\"formUpdate."+ referenced.getColumns()[0].getReferencedTable() +"\">\n";
                                                optionUpdate += "<ion-select-option v-for=\" option in " + referenced.getColumns()[0].getReferencedTable() + "s \" :key=\"option." + pk.getName() + "\" :value=\"option." + pk.getName() + "\">";
                                                optionUpdate += "{{option." + label + "}}";
                                                optionUpdate += "</ion-select-option>\n";
                                                optionUpdate += "</ion-select>\n";
                                                formContentUpdate += optionUpdate;

                                                String apiselect = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/Fkselect." + Constantes.VUEIONIC_TEMPLATE_EXT);
                                                apiselect = apiselect.replace("[entityName]", referenced.getColumns()[0].getReferencedTable());
                                                apisel += apiselect + "\n";
                                                
                                        } else{
                                                if(entities[i].getFields()[j].getType().equalsIgnoreCase("Integer") || entities[i].getFields()[j].getType().equalsIgnoreCase("Double") || entities[i].getFields()[j].getType().equalsIgnoreCase("Float")){
                                                        String realField = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/InputNumber." + Constantes.VUEIONIC_TEMPLATE_EXT).replace("[Field]", entities[i].getFields()[j].getName());
                                                        String realFieldUpdate = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/InputNumberUpdate." + Constantes.VUEIONIC_TEMPLATE_EXT).replace("[Field]", entities[i].getFields()[j].getName());
                                                        formContent += realField + "\n";
                                                        formContentUpdate += realFieldUpdate + "\n";
                                                }
                                                if(entities[i].getFields()[j].getType().equalsIgnoreCase("String")){
                                                        String realField = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/InputText." + Constantes.VUEIONIC_TEMPLATE_EXT).replace("[Field]", entities[i].getFields()[j].getName());
                                                        String realFieldUpdate = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/InputTextUpdate." + Constantes.VUEIONIC_TEMPLATE_EXT).replace("[Field]", entities[i].getFields()[j].getName());
                                                        formContent += realField + "\n";
                                                        formContentUpdate += realFieldUpdate + "\n";
                                                }
                                                if(entities[i].getFields()[j].getType().equalsIgnoreCase("Date") || entities[i].getFields()[j].getType().equalsIgnoreCase("java.time.LocalDate")){
                                                        String realField = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/InputDate." + Constantes.VUEIONIC_TEMPLATE_EXT).replace("[Field]", entities[i].getFields()[j].getName());
                                                        String realFieldUpdate = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/InputDateUpdate." + Constantes.VUEIONIC_TEMPLATE_EXT).replace("[Field]", entities[i].getFields()[j].getName());
                                                        formContent += realField + "\n";
                                                        formContentUpdate += realFieldUpdate + "\n";
                                                }
                                        }

                                        
                                        if( j == entities[i].getFields().length - 1){
                                                fieldFormAjout += entities[i].getFields()[j].getName() + ":''\n";
                                                fieldFormUpdate += entities[i].getFields()[j].getName() + ":''\n";
                                                fieldHandleAjout += entities[i].getFields()[j].getName() + ": form.value." + entities[i].getFields()[j].getName() + "\n";
                                                fieldHandleUpdate += entities[i].getFields()[j].getName() + ": formUpdate.value." + entities[i].getFields()[j].getName() + "\n";
                                        } else {
                                                fieldFormAjout += entities[i].getFields()[j].getName() + ":'',\n";
                                                fieldFormUpdate += entities[i].getFields()[j].getName() + ":'',\n";
                                                fieldHandleAjout += entities[i].getFields()[j].getName() + ": form.value." + entities[i].getFields()[j].getName() + ",\n";
                                                fieldHandleUpdate += entities[i].getFields()[j].getName() + ": formUpdate.value." + entities[i].getFields()[j].getName() + ",\n";
                                        }
                                        

                                }

                                dataentity = dataentity.substring(0, dataentity.length()-2) + "";
                                System.out.println(dataentity);


                                Templpage = Templpage.replace("[TableHeader]", header);
                                Templpage = Templpage.replace("[entityName]", entities[i].getTableName());
                                Templpage = Templpage.replace("[TableContent]", tcontent);
                                Templpage = Templpage.replace("[formContent]", formContent);
                                Templpage = Templpage.replace("[formUpdateContent]", formContentUpdate);
                                Templpage = Templpage.replace("[entityName]", entities[i].getTableName());
                                Templpage = Templpage.replace("[FieldFormAjout]", fieldFormAjout);
                                Templpage = Templpage.replace("[FieldHandleAjout]", fieldHandleAjout);
                                Templpage = Templpage.replace("[FieldFormUpdate]", fieldFormUpdate);
                                Templpage = Templpage.replace("[FieldHandleUpdate]", fieldHandleUpdate);
                                Templpage = Templpage.replace("[dataselection]", dataselected);
                                Templpage = Templpage.replace("[dataentity]", dataentity);
                                Templpage = Templpage.replace("[fkapi]", apisel);

                                HandyManUtils.overwriteFileContent(projectVue.getPath() + language.getVue_views() + HandyManUtils.majStart(entities[i].getTableName()) + "Page.vue", Templpage);
                        }
                                
                }

                String Templroute = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/Router." + Constantes.VUEIONIC_TEMPLATE_EXT);
                Templroute = Templroute.replace("[ImportPage]", importPage);
                Templroute = Templroute.replace("[RoutePage]", routePage);
                HandyManUtils.overwriteFileContent(projectVue.getPath() + language.getVue_router(), Templroute);

                String TemplData = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + language.getVue_skeleton() + "/DataEntity." + Constantes.VUEIONIC_TEMPLATE_EXT).replace("[dataEntity]", realData);
                HandyManUtils.overwriteFileContent(projectVue.getPath() + language.getVue_data() + "/entity.ts", TemplData);
                
//--------------------------------------------


            credentialFile = new File(project.getPath(), Constantes.CREDENTIAL_FILE);
            credentialFile.createNewFile();
            HandyManUtils.overwriteFileContent(credentialFile.getPath(), HandyManUtils.toJson(credentials));

            String pathWebConfig = project.getPath() + Constantes.PATH_CONFIG_WEB;
            Utils utils = new Utils();
            utils.copieWebConfig(Constantes.PATH_SOURCE_CONFIG_WEB, pathWebConfig);


            for (String replace : language.getProjectNameTags()) {
                projectNameTagPath = replace.replace("[projectNameMaj]", HandyManUtils.majStart(projectName))
                        .replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                projectNameTagContent = HandyManUtils.getFileContent(projectNameTagPath).replace("[projectNameMaj]",
                        HandyManUtils.majStart(projectName));
                projectNameTagContent = projectNameTagContent.replace("[databaseHost]", credentials.getHost());
                projectNameTagContent = projectNameTagContent.replace("[databasePort]", database.getPort());
                projectNameTagContent = projectNameTagContent.replace("[databaseName]", credentials.getDatabaseName());
                projectNameTagContent = projectNameTagContent.replace("[user]", credentials.getUser());
                projectNameTagContent = projectNameTagContent.replace("[pwd]", credentials.getPwd());
                projectNameTagContent = projectNameTagContent.replace("[projectNameMin]",
                        HandyManUtils.minStart(projectName));
                HandyManUtils.overwriteFileContent(projectNameTagPath, projectNameTagContent);
            }
            try (Connection connect = database.getConnexion(credentials)) {
                entities = database.getEntities(connect, credentials, entityName);
                for (int i = 0; i < entities.length; i++) {
                    entities[i].initialize(connect, credentials, database, language);
                }
                models = new String[entities.length];
                controllers = new String[entities.length];
                // views = new String[entities.length];
                navLink = "";
                for (int i = 0; i < models.length; i++) {
                    models[i] = language.generateModel(entities[i], projectName);
                    controllers[i] = language.generateController(entities[i], database, credentials, projectName);
                //     views[i] = language.generateView(entities[i], projectName);
                    modelFile = language.getModel().getModelSavePath().replace("[projectNameMaj]",
                            HandyManUtils.majStart(projectName));
                    controllerFile = language.getController().getControllerSavepath().replace("[projectNameMaj]",
                            HandyManUtils.majStart(projectName));
                //     viewFile = language.getView().getViewSavePath().replace("[projectNameMaj]",
                //             HandyManUtils.majStart(projectName));
                //     viewFile = viewFile.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                //     viewFile = viewFile.replace("[classNameMaj]", HandyManUtils.majStart(entities[i].getClassName()));
                //     viewFile = viewFile.replace("[classNameMin]", HandyManUtils.minStart(entities[i].getClassName()));
                    modelFile = modelFile.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                    controllerFile = controllerFile.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                    modelFile += "/" + HandyManUtils.majStart(entities[i].getClassName()) + "."
                            + language.getModel().getModelExtension();
                    controllerFile += "/" + HandyManUtils.majStart(entities[i].getClassName())
                            + language.getController().getControllerNameSuffix() + "."
                            + language.getController().getControllerExtension();
                //     viewFile += "/" + language.getView().getViewName() + "." + language.getView().getViewExtension();
                //     viewFile = viewFile.replace("[classNameMin]", HandyManUtils.minStart(entities[i].getClassName()));
                    HandyManUtils.createFile(modelFile);
                    for (CustomFile f : language.getModel().getModelAdditionnalFiles()) {
                        foreignContext = "";
                        for (EntityField ef : entities[i].getFields()) {
                            if (ef.isForeign()) {
                                foreignContext += language.getModel().getModelForeignContextAttr();
                                foreignContext = foreignContext.replace("[classNameMaj]",
                                        HandyManUtils.majStart(ef.getType()));
                            }
                        }
                        customFile = f.getName().replace("[classNameMaj]",
                                HandyManUtils.majStart(entities[i].getClassName()));
                        customFile = language.getModel().getModelSavePath().replace("[projectNameMaj]",
                                HandyManUtils.majStart(projectName)) + "/" + customFile;
                        customFile = customFile.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                        customFileContent = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + f.getContent())
                                .replace("[classNameMaj]", HandyManUtils.majStart(entities[i].getClassName()));
                        customFileContent = customFileContent.replace("[projectNameMin]",
                                HandyManUtils.minStart(projectName));
                        customFileContent = customFileContent.replace("[projectNameMaj]",
                                HandyManUtils.majStart(projectName));
                        customFileContent = customFileContent.replace("[databaseHost]", credentials.getHost());
                        customFileContent = customFileContent.replace("[databaseName]", credentials.getDatabaseName());
                        customFileContent = customFileContent.replace("[user]", credentials.getUser());
                        customFileContent = customFileContent.replace("[pwd]", credentials.getPwd());
                        customFileContent = customFileContent.replace("[modelForeignContextAttr]", foreignContext);
                        HandyManUtils.createFile(customFile);
                        HandyManUtils.overwriteFileContent(customFile, customFileContent);
                    }
                    HandyManUtils.createFile(controllerFile);
                //     HandyManUtils.createFile(viewFile);
                    HandyManUtils.overwriteFileContent(modelFile, models[i]);
                    HandyManUtils.overwriteFileContent(controllerFile, controllers[i]);
                //     HandyManUtils.overwriteFileContent(viewFile, views[i]);
                    navLink += language.getNavbarLinks().getLink();
                    navLink = navLink.replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
                    navLink = navLink.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                    navLink = navLink.replace("[classNameMin]", HandyManUtils.minStart(entities[i].getClassName()));
                    navLink = navLink.replace("[classNameMaj]", HandyManUtils.majStart(entities[i].getClassName()));
                    navLink = navLink.replace("[classNameformattedMin]",
                            HandyManUtils.minStart(HandyManUtils.formatReadable(entities[i].getClassName())));
                    navLink = navLink.replace("[classNameformattedMaj]",
                            HandyManUtils.majStart(HandyManUtils.formatReadable(entities[i].getClassName())));
                }
                navLinkPath = language.getNavbarLinks().getPath().replace("[projectNameMaj]",
                        HandyManUtils.majStart(projectName));
                navLinkPath = navLinkPath.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                //System.out.println(navLinkPath);
                HandyManUtils.overwriteFileContent(navLinkPath,
                        HandyManUtils.getFileContent(navLinkPath).replace("[navbarLinks]", navLink));
                for (CustomChanges c : language.getCustomChanges()) {
                    customChanges = "";
                    for (Entity e : entities) {
                        customChanges += c.getChanges();
                        customChanges = customChanges.replace("[classNameMaj]",
                                HandyManUtils.majStart(e.getClassName()));
                        customChanges = customChanges.replace("[classNameMin]",
                                HandyManUtils.minStart(e.getClassName()));
                        customChanges = customChanges.replace("[databaseHost]", credentials.getHost());
                        customChanges = customChanges.replace("[user]", credentials.getUser());
                        customChanges = customChanges.replace("[databaseName]", credentials.getDatabaseName());
                        customChanges = customChanges.replace("[pwd]", credentials.getPwd());
                    }
                    if (c.isWithEndComma() == false) {
                        customChanges = customChanges.substring(0, customChanges.length() - 1);
                    }
                    changesFile = c.getPath().replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
                    HandyManUtils.overwriteFileContent(changesFile,
                            HandyManUtils.getFileContent(changesFile).replace("[customChanges]", customChanges));
                }
            }
        }
    }
}
