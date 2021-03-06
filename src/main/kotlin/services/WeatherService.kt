package services

import com.alibaba.fastjson.JSON
import com.github.kittinunf.fuel.httpGet
import domains.WeatherResponse
import java.nio.charset.StandardCharsets

/**
 * Created by yiding.he on 6/12/2017.
 */

class WeatherService {
    fun getTemperature(lat: Double, lon: Double): Double {
        val tempUrl = "http://api.openweathermap.org/data/2.5/" +
                "weather?lat=$lat&lon=$lon&units=metric&APPID=0ea458f7d27494c11d1d17780f627944"
        val (_, response, _) = tempUrl.httpGet().responseString()
        val jsonStr = String(response.data, StandardCharsets.UTF_8)
        println("jsonStr: $jsonStr")
        val weatherResponse = JSON.parseObject(jsonStr, WeatherResponse::class.java)
        return weatherResponse.main.temp
    }
}
