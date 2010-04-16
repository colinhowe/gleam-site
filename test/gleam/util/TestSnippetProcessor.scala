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