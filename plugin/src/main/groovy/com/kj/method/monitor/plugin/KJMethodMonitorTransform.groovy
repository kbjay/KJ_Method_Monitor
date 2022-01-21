import com.kj.method.monitor.plugin.KJAbstractTransform
import com.kj.method.monitor.plugin.asm.KJAsmController


class KJMethodMonitorTransform extends KJAbstractTransform {

    @Override
    void handleDirectory(File sourceDir) {
        // 找到其中的class文件  过滤掉R BuildConfig 等系统自动生成的
        if (sourceDir.isDirectory()) {
            // 递归遍历所有的文件夹下所有的文件夹以及文件
            sourceDir.eachFileRecurse { File sourceFile ->
                if (sourceFile.absolutePath.endsWith(".class") &&
                        !sourceFile.absolutePath.endsWith("BuildConfig.class") ) {
                    println("  source_class_path: " + sourceFile.absolutePath)
                    KJAsmController.handleMethod(sourceFile)
                }
            }
        }
    }

    @Override
    void handleJar(File file) {
        // 不处理
    }
}