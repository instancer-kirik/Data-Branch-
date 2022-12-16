package com.instance.dataxbranch.ui.calendar/*
package com.instance.dataxbranch.ui.calendar

import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class _CreateCalendarEvent {

        */
/*fun toIcsString(): String {
            return """
            BEGIN:VCALENDAR
            VERSION:2.0
            BEGIN:VEVENT
            SUMMARY:$summary
            LOCATION:$location
            DTSTART:${start.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))}
            DTEND:${end.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))}
            DESCRIPTION:$description
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()*//*


    class Event(
        val start: LocalDateTime,
        val end: LocalDateTime,
        val summary: String,
        val description: String,
        val location: String,
        val timeZone: ZoneId,
        val title: String = "",
        val startDate: String = "",
        val endDate: String = "",
        val id: String = "",
        val timestamp: String = ""
    ) {
        fun toIcsString(): String {
            val startZoned = ZonedDateTime.of(start, timeZone)
            val endZoned = ZonedDateTime.of(end, timeZone)

            // Use the ICalendar format to create the string representation of the event
            // See https://en.wikipedia.org/wiki/ICalendar for more information
            return """
            BEGIN:VEVENT
            DTSTART;TZID=${timeZone.getId()}:${startZoned.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))}
            DTEND;TZID=${timeZone.getId()}:${endZoned.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))}
            SUMMARY:$summary
            DESCRIPTION:$description
            LOCATION:$location
            END:VEVENT
        """.trimIndent()
        }
    }
 */
/*   This class has four properties: summary, location, start, and end, which correspond to the summary, location, start time, and end time of the event, respectively. It also has a description property for providing a description of the event.

    The toIcsString() method is used to convert the CalendarEvent instance to a string in the .ics format, which can then be written to a file. This method uses the java.time.LocalDateTime class to represent the start and end times of the event, and it uses the java.time.format.DateTimeFormatter class to format these times in the yyyyMMdd'T'HHmmss pattern, as required by the .ics file format.

    You can use this CalendarEvent class in your code like this:
*//*

//    Copy code
    val event = CalendarEvent(
        summary = "Custom Quest",
        location = "The Adventurer's Guild",
        start = LocalDateTime.of(2022, 12, 15, 10, 0),
        end = LocalDateTime.of(2022, 12, 15, 12, 0),
        description = "Join us for an exciting custom quest at the Adventurer's Guild!"
    )

    val icsString = event.toIcsString()
  */
/*  You can then write the icsString to a file in the .ics format, and it can be imported into a calendar application.

    I hope this helps! Let me know if you have any other questions.
*//*




    fun generateIcsFile(event: Event): String {
        val builder = StringBuilder()

        // Add the file header
        builder.append("BEGIN:VCALENDAR\n")
        builder.append("VERSION:2.0\n")
        builder.append("PRODID:-//Your Company//Your Product//EN\n")
        builder.append("CALSCALE:GREGORIAN\n")          // Add the event details
        builder.append("BEGIN:VEVENT\n")
        builder.append("UID:${event.id}\n")
        builder.append("DTSTAMP:${event.timestamp}\n")
        builder.append("DTSTART:${event.startDate}\n")
        builder.append("DTEND:${event.endDate}\n")
        builder.append("SUMMARY:${event.summary}\n")
        builder.append("LOCATION:${event.location}\n")
        builder.append("DESCRIPTION:${event.description}\n")
        builder.append("END:VEVENT\n")
        // Add the file footer
        builder.append("END:VCALENDAR\n")
        return builder.toString()
    }


    */
/*This code uses a StringBuilder to construct the .ics file as a string. It starts by adding the file header, followed by the event details, and then the file footer. You can customize the event details to include any additional information you need.

    To use this function, you would create an Event object with the details of your event, and then pass it to the generateIcsFile function to generate the .ics file as a string. You can then save this string to a file with the .ics file extension, and the file will be recognized as a valid .ics file.

    I hope this helps! Let me know if you have any other questions.

    make a function that downloads a file

    Here is an example of how you could create a function that downloads a file in Kotlin:

    Copy code
*//*

    fun downloadFile(url: String, fileName: String) {
        val url = URL(url)
        val con = url.openConnection() as HttpURLConnection
        val inputStream = con.inputStream
        val fileOutputStream = FileOutputStream(fileName)
        inputStream.use { input ->
            fileOutputStream.use { fileOut ->
                input.copyTo(fileOut)
            }
        }
    }

*/
/*

    This function takes a URL and a file name as input, and uses the URL and HttpURLConnection classes to download the file from the given URL. It then uses the FileOutputStream class to save the file to the local file system with the specified file name.

    To use this function, you would simply call it with the URL of the file you want to download and the file name where you want to save it, like this:

    Copy code
*//*


    //downloadFile("http://www.example.com/file.zip", "file.zip")
*/
/*

    I hope this helps! Let me know if you have any other questions.

    make function to create and download event.ics file

    Here is an example of how you could create a function that generates and downloads an .ics file in Kotlin:

    Copy code
*//*


    fun generateAndDownloadIcsFile(event: Event, fileName: String) {
        // Generate the .ics file as a string
        val icsFile = generateIcsFile(event)
        // Create a temporary file with the specified file name
        val tempFile = File.createTempFile(fileName, ".ics")
        // Write the .ics file contents to the temporary file
        tempFile.writeText(icsFile)
        // Download the temporary file
        downloadFile(tempFile.toURI().toURL().toString(), fileName)
//         Delete the temporary file
        tempFile.delete()
    }
*/
/*

    This function uses the generateIcsFile function from the previous example to generate the .ics file as a string. It then creates a temporary file with the specified file name, writes the .ics file contents to the temporary file, and then uses the downloadFile function from the previous example to download the temporary file. Finally, it deletes the temporary file.

    To use this function, you would create an Event object with the details of your event, and then call the generateAndDownloadIcsFile function with the event and the file name where you want to save the .ics file, like this:

    Copy code
*//*

fun test(){
    val event = Event()
    generateAndDownloadIcsFile (event, "event.ics")
}

*/
/*
    I hope this helps! Let me know if you have any other questions.
    *//*

}*/
