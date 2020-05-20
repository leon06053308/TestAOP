package com.way.javassistdemo;

import com.way.javassistdemo.annotation.AnnoOpera;
import com.way.javassistdemo.annotation.TestAnno;

import java.io.FileInputStream;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;

public class Generator {
    public static void main(String... args) throws Exception {
        String classPath = "./javassistDemo/build/classes/java/main/com/way/javassistDemo";
        FileInputStream fis = new FileInputStream(classPath + "/Base.class");

        ClassPool cp = ClassPool.getDefault();

        CtClass cc = cp.makeClass(fis);

        CtMethod m = cc.getDeclaredMethod("process");

        testAttibute(m);

        for (Object annotation : m.getAnnotations()) {



            if (annotation instanceof TestAnno){
                System.out.println("TestAnno--");
            }else if (annotation instanceof AnnoOpera){
                System.out.println("AnnoOpera--");
            }
        }


        m.insertBefore(" System.out.println(\"start\"); ");
        m.insertAfter("System.out.println(\"end\");");

        Class c = cc.toClass();
        cc.writeFile("./javassistDemo/build/classes/java/main");

        /*Base base = (Base)c.newInstance();
        base.process();*/
    }

    private static String[] getMethodVariableName(CtClass cc, String methodName){
        try {
            CtMethod cm = cc.getDeclaredMethod(methodName);


            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            String[] paramNames = new String[cm.getParameterTypes().length];
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr != null){
                int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
                for (int i = 0; i < paramNames.length; i++){
                    paramNames[i] = attr.variableName(i + pos);
                }

                return paramNames;
            }
        }catch (Exception e){

        }

        return null;
    }

    private static void testAttibute(CtMethod method){

        MethodInfo methodInfo = method.getMethodInfo();
        AnnotationsAttribute annotations = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);

        for (Annotation annotation : annotations.getAnnotations()){
            System.out.println(annotation.getTypeName());
        }
    }
}
