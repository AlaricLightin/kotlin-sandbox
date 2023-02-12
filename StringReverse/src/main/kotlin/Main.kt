import java.io.File

fun main() {
    val newLines: List<String> = File("input.txt")
        .readLines()
        .map { it.reversed() }

    File("output.txt").writeText(
        newLines.joinToString(System.lineSeparator())
    )
}