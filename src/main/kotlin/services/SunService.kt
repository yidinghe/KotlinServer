package services

import com.alibaba.fastjson.JSON
import com.github.kittinunf.fuel.httpGet
import domains.SunInfo
import domains.SunriseSunsetResponse
import java.nio.charset.StandardCharsets

/**
 * Created by yiding.he on 6/12/2017.
 */
class SunService{
    fun getSunInfo(lat: Double, lon: Double): SunInfo {
        val sunUrl = "http://api.sunrise-sunset.org/json?" +
                "lat=$lat&lng=$lon&formatted=0"
        val (_, response, _) = sunUrl.httpGet().responseString()
        val jsonStr = String(response.data, StandardCharsets.UTF_8)
        println("jsonStr: $jsonStr")
        val sunriseSunsetResponse = JSON.parseObject(jsonStr, SunriseSunsetResponse::class.java)
        return SunInfo(sunriseSunsetResponse.results.sunrise, sunriseSunsetResponse.results.sunset)
    }
}

