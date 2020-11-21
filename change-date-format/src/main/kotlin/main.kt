import java.io.File

val dateRegex = Regex("""([0-9]{4})\.([0-9]{2})\.([0-9]{2})""")

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Not enough arguments.")
        return
    }

    val source = File(args[0])
    if (!source.isDirectory) {
        println("Source must be directory")
        return
    }

    source
        .walk()
        .filter { !it.isDirectory }
        .forEach {
            val content = it.readText()
            val newContent = dateRegex.replace(content) {m: MatchResult ->
                val newDateStr = m.groupValues[1] + "-" + m.groupValues[2] + "-" + m.groupValues[3]
                println("$it: ${m.value} -> $newDateStr")
                newDateStr
            }
            if (content != newContent)
                it.writeText(newContent)
        }
}