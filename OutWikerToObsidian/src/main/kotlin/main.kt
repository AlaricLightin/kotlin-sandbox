import java.io.File

fun main(args: Array<String>) {
    if (args.size < 2) {
        println("Not enough arguments!")
        return
    }

    val sourceDir = args[0]
    val destinationDir = args[1]

    val noteIndex = NoteIndex(File(sourceDir))
    val noteProcessor = NoteProcessor(noteIndex, File(destinationDir))
    noteProcessor.process()
    println("Processed ${noteProcessor.count} notes.")
}