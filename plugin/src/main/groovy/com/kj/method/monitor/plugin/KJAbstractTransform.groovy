package com.kj.method.monitor.plugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

/**
 * 模板代码
 */
abstract class KJAbstractTransform extends Transform {
    @Override
    String getName() {
        return this.class.simpleName
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        // 这里处理class文件
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        // 这里只处理自己project的method，不包含三方库
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        def outputProvider = transformInvocation.getOutputProvider()

        // 处理class文件
        transformInvocation.inputs.each { TransformInput input ->
            input.jarInputs.each {
                JarInput jarInput ->
                    // 处理jar
                    processJarInput(jarInput, outputProvider)
            }
            input.directoryInputs.each {
                DirectoryInput directoryInput ->
                    // 处理源码文件
                    processDirectoryInput(directoryInput, outputProvider)
            }
        }
    }

    void processJarInput(JarInput jarInput, TransformOutputProvider outputProvider) {
        File dest = outputProvider.getContentLocation(
                jarInput.getFile().getAbsolutePath(),
                jarInput.getContentTypes(),
                jarInput.getScopes(),
                Format.JAR)

        println("  jar dest: " + dest.absolutePath)
        println("  jarInput file: " + jarInput.getFile().absolutePath)

        handleJar(jarInput.getFile())

        //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
        FileUtils.copyFile(jarInput.getFile(), dest)
    }

    void processDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        File dest = outputProvider.getContentLocation(directoryInput.getName(),
                directoryInput.getContentTypes(), directoryInput.getScopes(),
                Format.DIRECTORY)
        //建立文件夹
        FileUtils.mkdirs(dest)

        println("  directory dest: "+ dest.absolutePath)
        println("  directory input: " + directoryInput.getFile().absolutePath)

        handleDirectory(directoryInput.getFile())

        //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
        FileUtils.copyDirectory(directoryInput.getFile(), dest)
    }

    abstract void handleDirectory(File file)
    abstract void handleJar(File file)
}