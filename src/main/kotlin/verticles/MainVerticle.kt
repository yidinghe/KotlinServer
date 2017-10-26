package verticles

import com.alibaba.fastjson.JSON
import com.zaxxer.hikari.HikariDataSource
import domains.DataSourceConfig
import domains.DebugConfig
import domains.ServerConfig
import domains.SunWeatherInfo
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.templ.ThymeleafTemplateEngine
import services.MigrationService
import services.SunService
import services.WeatherService
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yiding.he on 6/7/2017.
 */

class MainVerticle : AbstractVerticle() {

    private var hikariDataSource: HikariDataSource? = null

    private fun initDataSource(config: DataSourceConfig): HikariDataSource {
        val hikariDS = HikariDataSource()
        hikariDS.username = config.user
        hikariDS.password = config.password
        hikariDS.jdbcUrl = config.jdbcUrl
        hikariDataSource = hikariDS
        return hikariDS
    }

    override fun start(startFuture: Future<Void>?) {

        val server = vertx.createHttpServer()
        val router = Router.router(vertx)
        val logger = LoggerFactory.getLogger("VertxServer")
        val templateEngine = ThymeleafTemplateEngine.create()

        val serverConfig = JSON.parseObject(config().getJsonObject("server").encode(), ServerConfig::class.java)
        val debugConfig = JSON.parseObject(config().getJsonObject("debug").encode(), DebugConfig::class.java)
        val dataSourceConfig = JSON.parseObject(config().getJsonObject("dataSource").encode(), DataSourceConfig::class.java)

        fun migrateDb(config: DataSourceConfig) {
            val dataSource = initDataSource(config)
            val migrationService = MigrationService(dataSource)
            val migrationResult = migrationService.migrate()
            migrationResult.fold({ throwable ->
                logger.fatal("Exception occurred while performing migration", throwable)
                vertx.close()
            }, { res ->
                logger.debug("Migration successful or not needed")
            })
        }

        migrateDb(dataSourceConfig)

        val port = serverConfig.port
        val lat = debugConfig.lat
        val lon = debugConfig.lon

        if (TestConfig.IS_STOP_SERVER)
            vertx.close()

        val staticHandler = StaticHandler.create().setWebRoot("public").setCachingEnabled(serverConfig.caching)

        router.route("/public/*").handler(staticHandler)

        router.get("/api/weather/data").handler { routingContext ->
            val temperature = WeatherService().getTemperature(lat, lon)
            val sunInfo = SunService().getSunInfo(lat, lon)
            val sunWeatherInfo = SunWeatherInfo(sunInfo, temperature)
            val json = JSON.toJSONString(sunWeatherInfo)
            val response = routingContext.response()
            response.end(json)
        }

        router.get("/home").handler { routingContext ->
            val temperature = WeatherService().getTemperature(lat, lon)
            val sunInfo = SunService().getSunInfo(lat, lon)
            routingContext.put("time", SimpleDateFormat().format(Date()))
            routingContext.put("sunrise", sunInfo.sunrise)
            routingContext.put("sunset", sunInfo.sunset)
            routingContext.put("temperature", temperature)

            templateEngine.render(routingContext, "public/templates/index.html", { buf ->
                if (buf.failed()) {
                    logger.error("Template rendering failed", buf.cause())
                } else {
                    val responseOut = routingContext.response()
                    responseOut.end(buf.result())
                }
            })

        }

        server.requestHandler { router.accept(it) }
                .listen(port, { listenHandler ->
                    if (!listenHandler.succeeded()) {
                        logger.error("Failed to listen on port $port")
                    }
                })

    }

    override fun stop(stopFuture: Future<Void>?) {
        hikariDataSource?.close()
    }
}