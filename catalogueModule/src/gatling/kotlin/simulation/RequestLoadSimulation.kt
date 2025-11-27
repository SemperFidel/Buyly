package simulation

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.*
import io.gatling.javaapi.core.*
import io.gatling.javaapi.http.*

class RequestLoadSimulation: Simulation(){
    private val httpProtocol: HttpProtocolBuilder = http
        .baseUrl("http://127.0.0.1:8080")
        .acceptHeader("application/json")

    private val scn: ScenarioBuilder = scenario("Basic Test")
        .exec(
            http("Get Users")
                .get("/catalogue")
                .check(status().`is`(200))
        )
}