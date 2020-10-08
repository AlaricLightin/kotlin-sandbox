import java.io.File

const val NOTE_FILENAME = "__page.text"


class NoteProcessor(private val noteIndex: NoteIndex, private val destination: File) {
    var count: Int = 0
    private val noteLinkRegex = Regex("""\[(.+)]\(page://(__[a-z0-9-]+)\)""")

    fun process() {
        if (!destination.isDirectory)
            throw IllegalArgumentException("Source must be directory")

        val noteList: List<Note> = noteIndex.noteList
        noteList.forEach{ note ->
            val source = File(note.directory, NOTE_FILENAME)
            val content = source.readText()
            val newContent = noteLinkRegex.replace(content) { m: MatchResult ->
                val uid = m.groupValues[2]
                val name = noteList.find { it.uid == uid }?.name ?: "Битая ссылка"
                "[[${name}|${m.groupValues[1]}]]"
            }
            File(destination, note.name + ".md").writeText(newContent)
            count++
        }
    }
}
