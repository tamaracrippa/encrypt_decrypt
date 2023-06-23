import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

    fun validateTimestamp(timestamp: String): Boolean {
        if (timestamp.isNullOrEmpty())
            return false

        var timestamp_long = timestamp.toLong()
        var request_time: LocalDateTime = Timestamp(timestamp_long).toLocalDateTime()
        var hoje = LocalDateTime.now();

        println(timestamp)
        println(timestamp_long)
        println(request_time)
        println(hoje)
        var time_difference = request_time.until(hoje, ChronoUnit.SECONDS)
        println(time_difference)
        if (time_difference <= 1L) {
            return true;
        } else {
            return false
        }
    }

    fun main() {

        var diferenca = validateTimestamp("1651763714466")
        println(diferenca)

    }
