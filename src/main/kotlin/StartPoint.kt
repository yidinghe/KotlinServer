import io.vertx.core.Vertx
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import io.vertx.ext.web.templ.ThymeleafTemplateEngine
import verticles.MainVerticle
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yiding.he on 6/7/2017.
 */


fun main(args: Array<String>) {
//    val vertx = Vertx.vertx()
//    vertx.deployVerticle(MainVerticle())

//    val vertx = Vertx.vertx()
//
//    val server = vertx.createHttpServer()
//    val router = Router.router(vertx)
//    val logger = LoggerFactory.getLogger("VertxServer")
//    val templateEngine = ThymeleafTemplateEngine.create()
//    val port = 10010
//
//    router.get("/home").handler {
//        routingContext ->
//        routingContext.put("time", SimpleDateFormat().format(Date()))
//        templateEngine.render(routingContext, "public/templates/index.html", {
//            buf ->
//            if (buf.failed()) {
//                logger.error("Template rendering failed", buf.cause())
//            } else {
//                val response = routingContext.response()
//                response.end(buf.result())
//            }
//        })
//    }
//
//    server.requestHandler { router.accept(it) }
//            .listen(port, {
//                listenHandler ->
//                if (!listenHandler.succeeded()) {
//                    logger.error("Failed to listen on port $port")
//                }
//            })

}
