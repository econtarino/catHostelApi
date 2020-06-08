import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(){
    println("Hello from kotlin Web")
    val port = 8000

    val server = embeddedServer(Netty, port, module = Application::mainModule)

    server.start()
}

fun Application.mainModule(){
    install(io.ktor.features.ContentNegotiation){
        jackson{
            enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT)

        }
    }
    routing {
        trace{
            application.log.debug(it.buildText())
        }
        get{
            context.respond(kotlin.collections.mapOf("Welcome" to "our Cat Hostel"))

        }
        get("/{name}"){
            val name = context.parameters["name"]
            context.respond(kotlin.collections.mapOf("Cat name" to name))
        }
    }
}