import java.io.File

const val INFO_FILE_NAME = "__page.opt"
const val UID_PREFIX = "uid = "

class NoteIndex(private val source: File) {
    val noteList: List<Note> by lazy { createNoteList() }

    private fun createNoteList(): List<Note> {
        if (!source.isDirectory)
            throw IllegalArgumentException("Source must be directory")
        return source
            .walk()
            .filter { it.isDirectory }
            .filter { File(it, NOTE_FILENAME).exists() }
            .map(this::getNote)
            .filterNotNull()
            .toList()
    }

    private fun getNote(directory: File): Note? {
        val infoFile = File(directory, INFO_FILE_NAME)
        val uidString = infoFile.readLines()
            .find { it.startsWith(UID_PREFIX) }
            ?.substring(UID_PREFIX.length)
        return Note(directory.name, uidString, directory)
    }
}

data class Note(val name: String, val uid: String?, val directory: File)