package me.android.plugin.aspectj

import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * https://www.jianshu.com/p/2e8409bc8c3b
 */
class AspectjPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        final def log = project.logger
        // 获取当前是否是application
        // 注意需要放在extensions中，也就是gradle的第一层（无嵌套）
        // extensions是ExtensionContainer
        def aspectj = project.extensions.create("aspectj", AspectJ.class)
        project.task('showAspectj') {
            group = 'andfun'
            description = 'show aspectj config detail info'
            doLast {
                println "aspectj = $aspectj"
            }
        }

        // 想法是为工程自动添加aspectj插件，但是由于project被evaluate之后修改buildscript会报错，所以放弃
//        if (isApplication) {
//            project.rootProject.buildscript.dependencies {
//                classpath 'org.aspectj:aspectjtools:1.9.2'
//                classpath 'org.aspectj:aspectjweaver:1.9.2'
//            }
//        }

        project.dependencies {
            api 'org.aspectj:aspectjrt:1.9.2'
        }

        def variants
        def isApplication = false
        try {
            variants = project.android.libraryVariants
        } catch (Exception ignore) {
            variants = project.android.applicationVariants
            isApplication = true
        }
        log.error "+=================================+"
        log.error "  Aspectj start weaving ${isApplication ? "Application" : "Library"}"
        log.error "+=================================+"

        variants.all { variant->
            def javaCompile = variant.javaCompile
            javaCompile.doLast{
                String[] args = ["-showWeaveInfo",
                                 "-1.8",
                                 "-inpath", javaCompile.destinationDir.toString(),
                                 "-aspectpath", javaCompile.classpath.asPath,
                                 "-d", javaCompile.destinationDir.toString(),
                                 "-classpath", javaCompile.classpath.asPath,
                                 "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
                log.debug "ajc args: " + Arrays.toString(args)

                MessageHandler handler = new MessageHandler(true);
                new Main().run(args, handler);
                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            log.error message.message, message.thrown
                            break;
                        case IMessage.WARNING:
                            log.warn message.message, message.thrown
                            break;
                        case IMessage.INFO:
                            log.info message.message, message.thrown
                            break;
                        case IMessage.DEBUG:
                            log.debug message.message, message.thrown
                            break;
                    }
                }
            }
        }
    }
}