package services

import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariDataSource
import domains.AuthInfo
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.AuthProvider
import io.vertx.ext.auth.User


/**
 * Created by yiding.he on 6/15/2017.
 */
class DatabaseAuthProvider(val dataSource: HikariDataSource, val jsonMapper: ObjectMapper) : AuthProvider {

    override fun authenticate(authInfoJson: JsonObject?, resultHandler: Handler<AsyncResult<User>>?) {
        val authInfo = jsonMapper.readValue(authInfoJson?.encode(), AuthInfo::class.java)
        //TODO query
    }

}