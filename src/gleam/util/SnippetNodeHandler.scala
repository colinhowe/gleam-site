package gleam.util

import gleam.Node
import scala.collection.JavaConversions._


class SnippetNodeHandler(htmlCreator : HtmlCreator, snippetName : String) extends NodeHandler {
  val snippetProcessor = new SnippetProcessor
  
  def handle(node : Node, builder : java.lang.StringBuilder) {
    val attributes = for ((key, value) <- node.getAttributes)
      yield(key, value.toString)
      
    if (node.getNodes != null && node.getNodes.size != 0) {
      val innerBuilder = new java.lang.StringBuilder
      for (innerNode <- node.getNodes) {
        htmlCreator.generate(innerNode, innerBuilder);
      }
      attributes("_inner") = innerBuilder.toString
    } else if (node.getValue != null) {
      attributes("_inner") = node.getValue.toString
    }
    
    builder.append(snippetProcessor.process(
      SnippetProvider.loadSnippet(node.getTagName).getContents,
      attributes.toMap))
  }
}