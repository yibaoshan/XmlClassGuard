package com.xml.guard.entensions

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import java.io.File

/**
 * User: ljx
 * Date: 2022/3/2
 * Time: 12:46
 */
open class GuardExtension {

    var name: String? = null

    /*
    * 是否查找约束布局的constraint_referenced_ids属性的值，并添加到AndResGuard的白名单中，
    * 是的话，要求你在XmlClassGuard前依赖AabResGuard插件，默认false
    */
    var findAndConstraintReferencedIds = false

    /*
     * 是否查找约束布局的constraint_referenced_ids属性的值，并添加到AabResGuard的白名单中，
     * 是的话，要求你在XmlClassGuard前依赖AabResGuard插件，默认false
     */
    var findAabConstraintReferencedIds = false

    var mappingFile: File? = null

    var packageChange = HashMap<String, String>()

    var moveDir = HashMap<String, String>()

    // 本地的 route 列表，随着 manifest 文件中 package 路径一并变更
    var assetsConfigPath: String? = null

    constructor(name: String?) {
        this.name = name
    }
}

open class GuardVariantConfigExtension {

    var variantConfig: NamedDomainObjectContainer<GuardExtension>

    constructor(variantConfig: NamedDomainObjectContainer<GuardExtension>) {
        this.variantConfig = variantConfig
    }

    fun variantConfig(action: Action<NamedDomainObjectContainer<GuardExtension>>) {
        action.execute(variantConfig)
    }

}