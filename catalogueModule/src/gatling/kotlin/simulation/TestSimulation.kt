package simulation

import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.CoreDsl.atOnceUsers
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.http.HttpProtocolBuilder


class TestSimulation: Simulation(){
    private val httpProtocol: HttpProtocolBuilder = http
        .baseUrl("http://127.0.0.1:8080")
        .acceptHeader("application/json")


    private val scn: ScenarioBuilder = scenario("Basic Test")
        .exec(
            http("Get Users")
                .get("/catalogue")
                .check(status().`is`(200))
        )

    init {
        setUp(
            scn.injectOpen(atOnceUsers(100))
        ).protocols(httpProtocol)
    }
}