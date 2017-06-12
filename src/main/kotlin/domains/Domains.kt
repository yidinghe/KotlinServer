package domains

/**
 * Created by yiding.he on 6/9/2017.
 */

data class SunriseSunsetResult(var sunrise: String = "", var sunset: String = "", var solar_noon: String = "",
                               var day_length: Long = 0)

data class SunriseSunsetResponse(var results: SunriseSunsetResult = SunriseSunsetResult(), var status: String = "OK")

data class SunInfo(val sunrise: String, val sunset: String)