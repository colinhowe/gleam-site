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