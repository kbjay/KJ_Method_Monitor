package com.kj.method.monitor.plugin.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class KJAsmController {
    public static void handleMethod(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        // new classReader
        ClassReader classReader = new ClassReader(fis);
        // new ClassWriter
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        // new ClassVisitor
        KJCustomClassVisitor classVisitor = new KJCustomClassVisitor(Opcodes.ASM7,classWriter);
        // read start
        classReader.accept(classVisitor,ClassReader.EXPAND_FRAMES);
        // write to dest
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
}
