package com.way.asmdemo;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Generator {
    public static void main(String... args) throws Exception{
        FileInputStream fis = new FileInputStream("./asmDemo/build/classes/java/main/com/way/asmdemo/Base.class");

        ClassReader classReader = new ClassReader(fis);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        ClassVisitor classVisitor = new MyClassVisitor(Opcodes.ASM7, classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();
        File file = new File("./asmDemo/build/classes/java/main/com/way/asmdemo/Base.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
        System.out.println("now generator cc success!!!!!");
    }
}
