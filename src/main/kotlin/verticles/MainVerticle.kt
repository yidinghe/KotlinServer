package verticles

import com.alibaba.fastjson.JSON
import com.github.kittinunf.fuel.httpGet
import domains.SunInfo
import domains.SunriseSunsetResponse
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import io.vertx.ext.web.templ.ThymeleafTemplateEngine
import services.SunService
import services.WeatherService
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yiding.he on 6/7/2017.
 */

class MainVerticle : AbstractVerticle() {

    override fun start(startFuture: Future<Void>?) {

        val server = vertx.createHttpServer()
        val router = Router.router(vertx)
        val logger = LoggerFactory.getLogger("VertxServer")
        val templateEngine = ThymeleafTemplateEngine.create()
        val port = 8080
        val lat = 42.3583333
        val lon = -71.0602778

        if (TestConfig.IS_STOP_SERVER)
            vertx.close()

        router.get("/home").handler {
            routingContext ->

            val temperature = WeatherService().getTemperature(lat, lon)
            val sunInfo = SunService().getSunInfo(lat, lon)
            routingContext.put("time", SimpleDateFormat().format(Date()))
            routingContext.put("sunrise", sunInfo.sunrise)
            routingContext.put("sunset", sunInfo.sunset)
            routingContext.put("temperature", temperature)

            templateEngine.render(routingContext, "public/templates/index.html", {
                buf ->
                if (buf.failed()) {
                    logger.error("Template rendering failed", buf.cause())
                } else {
                    val responseOut = routingContext.response()
                    responseOut.end(buf.result())
                }
            })

        }

        server.requestHandler { router.accept(it) }
                .listen(port, {
                    listenHandler ->
                    if (!listenHandler.succeeded()) {
                        logger.error("Failed to listen on port $port")
                    }
                })

    }
}