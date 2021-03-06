package domains

/**
 * Created by yiding.he on 6/9/2017.
 */


data class SunriseSunsetResponse(var results: SunriseSunsetResult = SunriseSunsetResult(), var status: String = "OK")

data class SunriseSunsetResult(var sunrise: String = "", var sunset: String = "", var solar_noon: String = "",
                               var day_length: Long = 0)

data class SunInfo(val sunrise: String, val sunset: String)

data class SunWeatherInfo(val sunInfo: SunInfo, val temperature: Double)

data class WeatherResponse(var main: WeatherMainResult = WeatherMainResult())

data class WeatherMainResult(var temp: Double = 0.0, var pressure: Int = 0, var humidity: Int = 0,
                             var temp_min: Int = 0, var temp_max: Int = 0)