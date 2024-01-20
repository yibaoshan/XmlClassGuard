package com.xml.guard.model

/**
 *  author : xiaobao
 *  e-mail : yibaoshan@foxmail.com
 *  time   : 2024/01/20
 *  desc   : 对应 json 文件格式
 */
class AppConfig {

    var version = 0
    var lastUpdate: String? = null
    var routeList: List<RouteListDTO>? = null
    var urlList: UrlListDTO? = null

    class UrlListDTO {
        var privacyProtocol: String? = null
        var userProtocol: String? = null
        var callCoinNotEnoughTip: String? = null
        var familyDescription: String? = null
        var goddessMoneyBook: String? = null
        var withdrawalInstructions: String? = null
    }

    data class RouteListDTO(
        var desc: String?,
        var template: String?,
        var url: String?,
        var iosClass: String?,
        var androidClass: String?,
        var needLogin: Boolean = false
    )


}