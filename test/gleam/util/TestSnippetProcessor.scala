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

import org.junit.Test
import org.junit.Assert._

class TestSnippetProcessor {
  @Test
  def simpleSnippet = {
    val result = new SnippetProcessor().process(
        "<div></div>", Map())
    assertEquals("<div></div>", result)
  }
  
  @Test
  def singleSubstitution = {
    val result = new SnippetProcessor().process(
        "<div>%inputName%</div>", 
        Map("inputName" -> "name"))
    assertEquals("<div>name</div>", result)
  }
  
  @Test
  def multipleSubstitutions = {
    val result = new SnippetProcessor().process(
        "<div>%first%%second%</div>", 
        Map("first" -> "a", "second" -> "b"))
    assertEquals("<div>ab</div>", result)
  }
  
  @Test
  def substitutionFirst = {
    val result = new SnippetProcessor().process(
        "%first%", Map("first" -> "a"))
    assertEquals("a", result)
  }

  @Test
  def percentSign = {
    val result = new SnippetProcessor().process(
        "<div>%amount%%%</div>", 
        Map("amount" -> "5"))
    assertEquals("<div>5%</div>", result)
  }

}