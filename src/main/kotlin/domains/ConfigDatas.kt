package domains

/**
 * Created by yiding.he on 10/26/2017.
 */

data class ServerConfig(var port: Int = 0, var caching: Boolean = false)

data class DebugConfig(var lat: Double = 0.0, var lon: Double = 0.0)

data class DataSourceConfig(var user: String = "", var password: String = "", var jdbcUrl: String = "")