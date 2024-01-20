package com.xml.guard.tasks

import com.xml.guard.entensions.GuardExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

/**
 *  author : xiaobao
 *  e-mail : yibaoshan@foxmail.com
 *  time   : 2024/01/20
 *  desc   : 打包期间运行，执行全部插件任务
 */
open class PackageTask @Inject constructor(guardExtension: GuardExtension, variantName: String) : DefaultTask() {

    init {
        group = "guard"
        dependsOn("${variantName}XmlClassGuard")
        dependsOn("${variantName}PackageChange")
        dependsOn("${variantName}MoveDir")
    }

    @TaskAction
    fun execute() {
        // 啥也不干，等 dependsOn 执行完
    }

}