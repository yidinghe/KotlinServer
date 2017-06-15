package domains

import io.vertx.core.AsyncResult
import io.vertx.core.CompositeFuture
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.AbstractUser
import io.vertx.ext.auth.AuthProvider
import java.util.*

/**
 * Created by yiding.he on 6/15/2017.
 */
class DatabaseUser(val id: UUID, val username: String, val passwordHash: String) : AbstractUser() {
    override fun doIsPermitted(permission: String?, resultHandler: Handler<AsyncResult<Boolean>>?) {
        val result = CompositeFuture.factory.succeededFuture(true)
        resultHandler?.handle(result)
    }

    override fun setAuthProvider(authProvider: AuthProvider?) {
    }

    override fun principal(): JsonObject {
        return JsonObject().put("username", username)
    }

}