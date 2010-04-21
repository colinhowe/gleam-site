package gleam.util

class SnippetProcessor {
  def process(snippet : String, substitutions : Map[String, String]) = {
    // 3.85
    val buffer = new StringBuilder
    var currentIndex = 0
    while (currentIndex < snippet.length) {
      val firstSepIndex = snippet.indexOf("%", currentIndex)
      if (firstSepIndex == -1) {
        buffer.append(snippet.substring(currentIndex))
        currentIndex = snippet.length
      } else {
        buffer.append(snippet.substring(currentIndex, firstSepIndex))
        currentIndex = snippet.indexOf("%", firstSepIndex + 1)
        if (firstSepIndex + 1 == currentIndex) {
          buffer.append("%")
        } else {
          val subName = snippet.substring(firstSepIndex + 1, currentIndex)
          buffer.append(substitutions(subName))
        }
        currentIndex = currentIndex + 1
      }
    }
    
    buffer.toString
  }
}