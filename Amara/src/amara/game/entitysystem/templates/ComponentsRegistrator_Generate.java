/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amara.game.entitysystem.templates;

import java.io.File;
import amara.engine.files.FileManager;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class ComponentsRegistrator_Generate{
    
    private static final String SOURCE_DIRECTORY = "../src/";
    private static final String PACKAGE_COMPONENTS = "amara.game.entitysystem.components.";
    private static final String LIST_SEPERATOR = ",";
    private static final Class[] notXMLSupportedComponentClasses = new Class[]{
        AddComponentsComponent.class,
        RemoveComponentsComponent.class,
        HitboxComponent.class,
        SpawnTemplateComponent.class,
        CastTypeComponent.class,
        CollisionGroupComponent.class,
        DamageHistoryComponent.class
    };
    private static final String[] nativeDataTypes = new String[]{"boolean","int","long","float","double"};
    private static String code;

    public static void main(String[] args){
        String packageName = ComponentsRegistrator_Generate.class.getPackage().getName();
        code = "package " + packageName + ";\n";
        code += "\n";
        code += "import com.jme3.math.Vector2f;\n";
        code += "import com.jme3.network.serializing.Serializer;\n";
        code += "\n";
        code += "/**GENERATED**/\n";
        code += "public class ComponentsRegistrator{\n\n";
        code += "    public static void registerComponents(){\n";
        code += "        XMLTemplateManager xmlTemplateManager = XMLTemplateManager.getInstance();\n";
        checkDirectory(SOURCE_DIRECTORY + PACKAGE_COMPONENTS.replace(".", "/"));
        code += "    }\n}";
        FileManager.putFileContent(SOURCE_DIRECTORY + packageName.replace(".", "/") + "/ComponentsRegistrator.java", code);
    }
    
    private static void checkDirectory(String directory){
        String[] fileNames = new File(directory).list();
        for(String fileName : fileNames){
            if(fileName.endsWith("Component.java")){
                String componentClassName = fileName.substring(0, fileName.length() - 5);
                try{
                    int subPackageStart = (SOURCE_DIRECTORY.length() + PACKAGE_COMPONENTS.length());
                    String subPackage = directory.substring(subPackageStart).replace("/", ".");
                    Class componentClass = Class.forName(PACKAGE_COMPONENTS + subPackage + componentClassName);
                    code += "        Serializer.registerClass(" + componentClass.getName() + ".class);\n";
                    if(isComponentClassXMLSupported(componentClass)){
                        String elementName = componentClassName.substring(0, 1).toLowerCase() + componentClassName.substring(1, componentClassName.length() - 9);
                        code += "        xmlTemplateManager.registerComponent(" + componentClass.getName() + ".class, new XMLComponentConstructor<" + componentClass.getName() + ">(\"" + elementName + "\"){\n\n";
                        code += "            @Override\n";
                        code += "            public " + componentClass.getName() + " construct(){\n";
                        //Check constructor
                        String resultingConstructorCode = "";
                        String fileContent = FileManager.getFileContent(directory + fileName);
                        String emptyConstructorStartCode = "public " + componentClassName + "()";
                        String constructorStartCode = "public " + componentClassName + "(";
                        int emptyConstructorStart = fileContent.indexOf(emptyConstructorStartCode);
                        int constructorPotentialStart = fileContent.indexOf(constructorStartCode, emptyConstructorStart + emptyConstructorStartCode.length());
                        if(constructorPotentialStart != -1){
                            int constructorStart = (constructorPotentialStart + constructorStartCode.length());
                            int constructorEnd = fileContent.indexOf(")", constructorStart);
                            String constructorCode = fileContent.substring(constructorStart, constructorEnd);
                            String[] parameters = constructorCode.split(", ");
                            boolean wasHandled = true;
                            int childIndex = 0;
                            for(int i=0;i<parameters.length;i++){
                                wasHandled = false;
                                String[] parameterParts = parameters[i].split(" ");
                                String parameterType = parameterParts[0];
                                String parameterName = parameterParts[1];
                                String textAccessCode = ((parameters.length == 1)?"element.getText()":"element.getAttributeValue(\"" + parameterName + "\")");
                                if(parameterType.equals("int") && parameterName.toLowerCase().endsWith("entity")){
                                    code += "                int " + parameterName + " = createChildEntity(" + childIndex + ", \"" + parameterName + "\");\n";
                                    wasHandled = true;
                                }
                                else if(parameterType.equals("int...") && parameterName.toLowerCase().endsWith("entities")){
                                    code += "                int[] " + parameterName + " = createChildEntities(" + childIndex + ", \"" + parameterName + "\");\n";
                                    wasHandled = true;
                                }
                                else if(parameterType.equals("String")){
                                    String parseMethodName = (parameterName.endsWith("Template")?"parseTemplate":"parseValue");
                                    code += "                String " + parameterName + " = xmlTemplateManager." + parseMethodName + "(" + textAccessCode + ");\n";
                                    wasHandled = true;
                                }
                                else if(parameterType.equals("String...") || parameterType.equals("String[]")){
                                    String parseMethodName = (parameterName.endsWith("Templates")?"parseTemplate":"parseValue");
                                    code += "                String[] " + parameterName + " = new String[0];\n";
                                    code += "                String " + parameterName + "Text = " + textAccessCode + ";\n";
                                    code += "                if(" + parameterName + "Text != null){\n";
                                    code += "                    " + parameterName + " = " + parameterName + "Text.split(\"" + LIST_SEPERATOR + "\");\n";
                                    code += "                    for(int i=0;i<" + parameterName + ".length;i++){\n";
                                    code += "                        " + parameterName + "[i] = xmlTemplateManager." + parseMethodName + "(" + parameterName + "[i]);\n";
                                    code += "                    }\n";
                                    code += "                }\n";
                                    wasHandled = true;
                                }
                                else if(parameterType.equals("Vector2f")){
                                    code += "                String[] " + parameterName + "Coordinates = " + textAccessCode + ".split(\"" + LIST_SEPERATOR + "\");\n";
                                    code += "                float " + parameterName + "X = Float.parseFloat(xmlTemplateManager.parseValue(" + parameterName + "Coordinates[0]));\n";
                                    code += "                float " + parameterName + "Y = Float.parseFloat(xmlTemplateManager.parseValue(" + parameterName + "Coordinates[1]));\n";
                                    code += "                Vector2f " + parameterName + " = new Vector2f(" + parameterName + "X, " + parameterName + "Y);\n";
                                    wasHandled = true;
                                }
                                else{
                                    for(int r=0;r<nativeDataTypes.length;r++){
                                        String parseMethodName;
                                        if(nativeDataTypes[r].equals("int")){
                                            parseMethodName = "Integer.parseInt";
                                        }
                                        else{
                                            String datatypeClassName = nativeDataTypes[r].substring(0, 1).toUpperCase() + nativeDataTypes[r].substring(1);
                                            parseMethodName = (datatypeClassName + ".parse" + datatypeClassName);
                                        }
                                        if(parameterType.equals(nativeDataTypes[r])){
                                            code += "                " + nativeDataTypes[r] + " " + parameterName + " = " + parseMethodName + "(xmlTemplateManager.parseValue(" + textAccessCode + "));\n";
                                            wasHandled = true;
                                            break;
                                        }
                                        else if(parameterType.equals(nativeDataTypes[r] + "...") || parameterType.equals(nativeDataTypes[r] + "[]")){
                                            code += "                String[] " + parameterName + "Parts = " + textAccessCode + ".split(\"" + LIST_SEPERATOR + "\");\n";
                                            code += "                " + nativeDataTypes[r] + "[] " + parameterName + " = new " + nativeDataTypes[r] + "[" + parameterName + "Parts.length];\n";
                                            code += "                for(int i=0;i<" + parameterName + ".length;i++){\n";
                                            code += "                    " + parameterName + "[i] = " + parseMethodName + "(xmlTemplateManager.parseValue(" + textAccessCode + "));\n";
                                            code += "                }\n";
                                            wasHandled = true;
                                            break;
                                        }
                                    }
                                }
                                if(i != 0){
                                    resultingConstructorCode += ", ";
                                }
                                resultingConstructorCode += parameterName;
                                if(!wasHandled){
                                    break;
                                }
                            }
                            if(!wasHandled){
                                throw new UnsupportedOperationException("Unsupported constructor '" + constructorCode + "' of class '" + directory.substring(SOURCE_DIRECTORY.length()).replaceAll("/", ".") + componentClassName + "'.");
                            }
                        }
                        code += "                return new " + componentClass.getName() + "(" + resultingConstructorCode + ");\n";
                        code += "            }\n";
                        code += "        });\n";
                    }
                }catch(ClassNotFoundException ex){
                    ex.printStackTrace();
                }
            }
            else{
                code += "        //" + fileName + "\n";
                checkDirectory(directory + fileName + "/");
            }
        }
    }
    
    private static boolean isComponentClassXMLSupported(Class componentClass){
        for(Class notXMLSupportedComponentClass : notXMLSupportedComponentClasses){
            if(notXMLSupportedComponentClass == componentClass){
                return false;
            }
        }
        return true;
    }
}
