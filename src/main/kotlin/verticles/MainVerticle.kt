package verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import io.vertx.ext.web.templ.ThymeleafTemplateEngine
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
        val port = 10011

        if (TestConfig.IS_STOP_SERVER)
            vertx.close()

        router.get("/home").handler {
            routingContext ->
            routingContext.put("time", SimpleDateFormat().format(Date()))
            routingContext.put("time", SimpleDateFormat().format(Date()) + "Yiding He")
            templateEngine.render(routingContext, "public/templates/index.html", {
                buf ->
                if (buf.failed()) {
                    logger.error("Template rendering failed", buf.cause())
                } else {
                    val response = routingContext.response()
                    response.end(buf.result())
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