/*
 * Copyright 2010 Colin Howe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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