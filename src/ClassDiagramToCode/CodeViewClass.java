package ClassDiagramToCode;

/**
 *
 * @author Fhad khan
 */

import java.io.*;
import java.util.Scanner;
import java.lang.reflect.*;
import java.util.StringTokenizer;
import java.util.ArrayList;

class AutoCompile {

	private ProcessBuilder runCode;
	private File commands, output, errors;
	
	public AutoCompile(String cPath, String oPath, String ePath) {
		try {
			commands = new File(cPath);
			output = new File(oPath);
			errors = new File(ePath);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setCommands(String cPath) {
		try {
			commands = new File(cPath);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setOutput(String oPath) {
		try {
			output = new File(oPath);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setErrors(String ePath) {
		try {
			errors = new File(ePath);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}   
	}
	
	public boolean compileCode() {
		runCode = new ProcessBuilder("cmd");
		runCode.redirectInput(commands);
		runCode.redirectOutput(output);
		runCode.redirectError(errors);
		try {
			runCode.start();
			Thread.sleep(1000);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		if (errors.length() == 0)
			return true;
		else
			return false;
	}
	
}

final class MethodsInfo {

	String[] names,returnTypes, access;
	String[][] parameters;
	boolean[] isStatic, isAbstract;
	int numberOfConstructors;
	
	public MethodsInfo(Method[] methods, Constructor[] constructors) {
		names = new String[methods.length + constructors.length];
		returnTypes = new String[methods.length];
		access = new String[methods.length + constructors.length];
		parameters = new String[methods.length + constructors.length][];
		isStatic = new boolean[methods.length];
		isAbstract = new boolean[methods.length];
		numberOfConstructors = constructors.length;
		int i = 0, j = 0;
                                
                for(Constructor constructor : constructors) {
                    constructor.setAccessible(true);
                    names[i] = constructor.getName();
                    Class[] params = constructor.getParameterTypes();
                    if(params.length != 0)
                        parameters[i] = new String[params.length];
                        int k = 0;
                    if(params.length != 0) {
                            for(Class param : params) {
                                parameters[i][k++] = param.getName();
                            }
                        }
                    setAccessLevel(constructor.getModifiers(), i++);
                }
  
                for(Method method : methods) {
                    method.setAccessible(true);
                    names[i] = method.getName();
                    returnTypes[j] = method.getReturnType().getName();
                    Class[] params = method.getParameterTypes();
                       if(params.length != 0)
                            parameters[i] = new String[params.length];
                            int k = 0;
                            if(params.length != 0) {
                                for(Class param : params){
                                    parameters[i][k++] = param.getName();
                                }
                            }
                     setModifiers(method.getModifiers(), j++, i++);
                } 
        }
	
	void setAccessLevel(int modifiers, int pos) {
		if (Modifier.isPublic(modifiers))
			access[pos] = "+";
		else if (Modifier.isProtected(modifiers))
			access[pos] = "-";
		else
			access[pos] = "-";
	}
	
	void setModifiers(int modifiers, int pos, int accessPos) {
		isStatic[pos] = Modifier.isStatic(modifiers);
		isAbstract[pos] = Modifier.isAbstract(modifiers);
		setAccessLevel(modifiers, accessPos);	
	}
	
        boolean checkAbstract() {
            for (boolean flag : isAbstract) {
                if (flag)
                    return true;
            }
            return false;
        }        
}

class FieldsInfo {

	String[] names, types, access;
	boolean[] isStatic, isPrimitive;
	String composed;
	
	public FieldsInfo(Field[] fields) {
		names = new String[fields.length];
		types = new String[fields.length];
		access = new String[fields.length];
		isStatic = new boolean[fields.length];
		isPrimitive = new boolean[fields.length];
                composed = null;
		int i = 0;
                
		for(Field field : fields) {
			field.setAccessible(true);
			names[i] = field.getName();
			types[i] = field.getType().toString();
			if (types[i].equals("int") || types[i].equals("float") ||
			types[i].equals("double") || types[i].equals("class java.lang.String") || 
			types[i].equals("boolean")) 
				isPrimitive[i] = true;
			else {
				String[] temp = types[i].split("\\s");
				composed = temp[1].substring(10);
				isPrimitive[i] = false;}
			setModifiers(field.getModifiers(), i++);
		}
	}
	
	void setModifiers(int modifiers, int pos) {
		isStatic[pos] = Modifier.isStatic(modifiers);
		if (Modifier.isPublic(modifiers))
			access[pos] = "+";
		else if (Modifier.isProtected(modifiers))
			access[pos] = "-";
		else
			access[pos] = "-";
	}
}


class ClassInfo {

	String name, parentName, access ;
	String[] interfaces;
	Class classObject;
	boolean isAbstract, isInterface, realizesParent  ;
	FieldsInfo fieldsInfo;
	MethodsInfo methodsInfo;
	
	public ClassInfo(String name) {
		try {	
                    classObject = Class.forName(name);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
                this.name = name.substring(10);
		fieldsInfo = new FieldsInfo(classObject.getDeclaredFields());
		methodsInfo = new MethodsInfo(classObject.getDeclaredMethods(), classObject.getDeclaredConstructors());
		Class[] interfacesClass = classObject.getInterfaces();
		interfaces = new String[interfacesClass.length];
		for (int i = 0; i < interfaces.length; i++){
			interfaces[i] = interfacesClass[i].getName().substring(10);
                }
                setModifiers();
	}
	
	void setModifiers() {
		int modifiers = classObject.getModifiers();
		isAbstract = Modifier.isAbstract(modifiers);
		isInterface = Modifier.isInterface(modifiers);
                Class parentClass = classObject.getSuperclass();
                realizesParent = false;
                if (parentClass != null) {
                    parentName = parentClass.getName();
                    int parentMods = parentClass.getModifiers();
                    if (Modifier.isAbstract(parentMods) || interfaces.length != 0) {
                        if (!isAbstract)    
                            realizesParent = true;
                    }
                }
		if (Modifier.isPublic(modifiers))
			access = "Public";
		else if (Modifier.isProtected(modifiers))
			access = "Protected";
		else
			access = "Private";
	}
	
	String[] getFieldNames() {
		return fieldsInfo.names;
	}
	
	String[] getFieldTypes() {
		return fieldsInfo.types;                         
	}
	
	String[] getFieldAccess() {
		return fieldsInfo.access;
	}
	
	boolean[] getFieldIsStatic() {
		return fieldsInfo.isStatic;
	}
	
	boolean[] getFieldIsPrimitive() {
		return fieldsInfo.isPrimitive;
	}
	
	String getComposed() {
		return fieldsInfo.composed;
	}

	
	String[] getMethodNames() {
		return methodsInfo.names;
	}
	
	String[] getMethodReturnTypes() {
		return methodsInfo.returnTypes;
	}
	
	String[] getMethodAccess() {
		return methodsInfo.access;
	}
	
	String[] getMethodParameters(int i) {
		return methodsInfo.parameters[i];
	}
	
	boolean[] getMethodIsStatic() {
		return methodsInfo.isStatic;
	}
	
	boolean[] getMethodIsAbstract() {
		return methodsInfo.isAbstract;
	}
	
	int getNumberOfConstructors() {
		return methodsInfo.numberOfConstructors;
	}
        
}


public class CodeViewClass {

    public CodeViewClass() {}
    
    public static int mainFunction(String filePath) throws Exception{
        att= new ArrayList<>();
        func= new ArrayList<String>();
        classAtt= new ArrayList<String>();
        relationsInfo = new ArrayList<String>();
        
        try{
           FileOutputStream batchFile= new FileOutputStream(new File("..\\ClassDiagramToCode\\src\\resources\\commands.bat"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(batchFile));
                writer.write("D:");
                writer.newLine();
                writer.write("cd D:\\UCP\\FYP\\Source Code\\ClassDiagramToCode\\src\\resources");
                writer.newLine();
                writer.write("set path=\"C:\\Program Files\\Java\\jdk1.8.0_112\\bin\"");
                writer.newLine();
                String path= filePath.split("Resources")[1].substring(1);
                writer.write("javac " + path);
                writer.newLine();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }  
      AutoCompile auto = new AutoCompile("..\\ClassDiagramToCode\\src\\resources\\commands.bat", "..\\ClassDiagramToCode\\src\\resources\\output.txt", "..\\ClassDiagramToCode\\src\\resources\\errors.txt");
      String path = filePath;
        
        if(!auto.compileCode()){
                    System.out.println("Compilation Error!!!!");
                    return 1;		
        }else{
                
                ArrayList<String> className;
	        className= new ArrayList<String>();
                        
                String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
                String delim=" \t\n\r";

                StringTokenizer token= new StringTokenizer(content, delim);
                String temp = null;
                while(token.hasMoreTokens()){
                    String flag=token.nextToken();
                    if(flag.equals("class") || flag.equals("interface")) {
                        temp = token.nextToken().toString();
                        if (temp.substring(temp.length() - 1).equals("{"))
                            className.add("Resources." + temp.substring(0, temp.length()-1));
                        else
                            className.add("Resources." + temp);
                    }
                }
                info = new ClassInfo[className.size()];
                for(int i = 0; i < info.length; i++) {
                    info[i] = new ClassInfo(className.get(i));
                }                
                
                 className.clear();
                
                //---------------- FILLING CLASSES STRING-------------------- 
                
                classes = new String[info.length];
                int classesCounter = 0;
                for (ClassInfo ci : info) {
                    classes[classesCounter] = ci.name;
                    classesCounter++;
                }
                
        String omg ="";
        int temp2;
        for (int i=0; i<info.length; i++){
            classAtt.add(info[i].name);
            classAtt.add(Integer.toString(info[i].fieldsInfo.names.length));
            classAtt.add(Integer.toString(info[i].methodsInfo.names.length));
            temp2 = 0;

            if(info[i].fieldsInfo.names != null && info[i].fieldsInfo.names.length != 0){
                for(int j=0; j<info[i].fieldsInfo.names.length; j++){
                    omg = "";
                     
                    /** Data member Type */
                    omg += info[i].fieldsInfo.access[j];

                    /** Data member Name */
                    omg += info[i].fieldsInfo.names[j];                        
                    omg += ": ";
                        
                    /** Data member ReturnType */
                    if(info[i].fieldsInfo.types[j].contains("class java.lang")){
                      omg +=info[i].fieldsInfo.types[j].substring(16);
                    }else{
                        if(info[i].fieldsInfo.types[j].contains("class Resources")){
                           omg +=info[i].fieldsInfo.types[j].substring(16);
                        }else{
                            omg +=info[i].fieldsInfo.types[j];
                        }
                    }
                    att.add(omg);
                  }                    
                }

            omg = "";
            if(info[i].methodsInfo.names != null && info[i].methodsInfo.names.length != 0){
                for(int k=0; k<info[i].methodsInfo.names.length; k++){
                    if(!info[i].methodsInfo.names[k].contains(".")){
                        omg = "";

                        /** Function Type */
                        omg += info[i].methodsInfo.access[k];
                          
                        /** Function Name */    
                        omg += info[i].methodsInfo.names[k];
                        omg += "(";
                        if(info[i].methodsInfo.parameters[k] != null){
                            for(int l=0; l<info[i].methodsInfo.parameters[k].length; l++){
                                if(info[i].methodsInfo.parameters[k][l].contains("java.lang")) {
                                    omg += info[i].methodsInfo.parameters[k][l].substring(10);
                                }else if(info[i].methodsInfo.parameters[k][l].contains("Resources")) {
                                    omg += info[i].methodsInfo.parameters[k][l].substring(10);
                                }else {
                                    omg += info[i].methodsInfo.parameters[k][l];
                                }
                                omg += ",";
                                }
                            omg += '\b';
                        }
                        omg += ")"; 
                        omg +=": ";    
                        
                        /** Function ReturnType */                        
                        
                        if(info[i].methodsInfo.returnTypes[temp2].contains("java.lang")){
                            omg +=info[i].methodsInfo.returnTypes[temp2].substring(10);
                        }else{
                            if(info[i].methodsInfo.returnTypes[temp2].contains("Resources")){
                                omg += info[i].methodsInfo.returnTypes[temp2].substring(10);
                            }else{
                                omg += info[i].methodsInfo.returnTypes[temp2];
                            }
                        }                        
                             temp2++;                                               
                        }else{
                            omg += info[i].methodsInfo.names[k].substring(10)+"()";
                        }
                        func.add(omg);
                }
                    
            }
                
                //---------------- FILLING RELATIONSINFO ------------------- 
                
                
                String relate = "";
                boolean realizedByExtends = false;
                if (info[i].isInterface) {
                    if (info[i].interfaces.length != 0) {
                        relate += info[i].name + " Inherits " + info[i].interfaces[0];
                        relationsInfo.add(relate);
                    }
                }else{
                    if (info[i].realizesParent) {
                       if (!info[i].parentName.equals("java.lang.Object")) {
                           relate += info[i].name + " Realizes "; 
                           relate += info[i].parentName.substring(10);
                           realizedByExtends = true;
                           relationsInfo.add(relate);
                           relate = "";
                        }
                        if (info[i].interfaces.length != 0) {
                            relate += info[i].name + " Realizes ";
                            relate += info[i].interfaces[0];
                            relationsInfo.add(relate);
                            relate = "";
                        }
                    }
                    if ((info[i].parentName != null) && (!info[i].parentName.equals("java.lang.Object")) && !realizedByExtends) {
                        relate += info[i].name + " Inherits ";
                        relate += info[i].parentName.substring(10);
                        relationsInfo.add(relate);
                        relate = "";
                    }
                    if (info[i].fieldsInfo.composed != null) {
                        relate += info[i].name + " Composes ";
                        relate += info[i].fieldsInfo.composed;
                        relationsInfo.add(relate);
                        relate = "";
                    }
                }
        }

        //-----------------ENDS-----------------
  
        
        /** Drawing Class Diagram */         
                
         GraphSceneTest.testGraphScene();
        }
        return 0;
    }
    
     //To Be Removed
//    public static void main(String[] args) throws Exception{
//         func= new ArrayList<String>();
//         classAtt= new ArrayList<String>();
//         att= new ArrayList<String>();
//        CodeViewClass.mainFunction("..\\ClassDiagramToCode\\src\\resources\\relations.java");
//    }
    
    
    public static String[] forMethods() {
        String[] allMethods = new String[info.length];
        String[] temp;        
        for(int i = 0; i < info.length; i++) {
            allMethods[i] = "";
            for(int j = 0; j < info[i].getMethodNames().length; j++) {
                allMethods[i] += info[i].getMethodNames()[j] + " ";
                allMethods[i] += info[i].getMethodAccess()[j] + " ";
                allMethods[i] += info[i].getMethodReturnTypes()[j] + " ";
                temp = info[i].getMethodParameters(j);
                for(int k = 0; k < temp.length; k++)
                    allMethods[i] += temp[k] + " ";
            }
        }
       return allMethods;
    }
    
   public static ArrayList<String> att;
   public static ArrayList<String> classAtt;
   public static ArrayList<String> func;
   public static ClassInfo[] info;
   public static String[] classes;
   public static ArrayList<String> relationsInfo;
}