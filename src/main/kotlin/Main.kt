import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun main(args: Array<String>) {
    println(args.contentToString())

// In file
    Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")

//    In memory
//    Database.connect("jdbc:sqlite:file:test?mode=memory&cache=shared", "org.sqlite.JDBC")

    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(StarWarsFilms)

        StarWarsFilm.new {
            sequelId = 8
            name = "The Last Jedi"
            director = "Ali"
        }

        val films = StarWarsFilm.all()

        for (film in films) {
            println("Film with the name of ${film.id} + ${film.name}")
        }
    }
}