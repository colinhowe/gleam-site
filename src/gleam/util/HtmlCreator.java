package gleam.util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gleam.Node;

public class HtmlCreator {

   
  private Map<String, NodeHandler> handlers = new HashMap<String, NodeHandler>();
  
  public Node findNode(List<Node> nodes, String name) {
    for (Node node : nodes) {
      if (node.getTagName().equals(name)) {
        return node;
      }
    }
    
    return null;
  }
  
  public HtmlCreator() {
    
    handlers.put("page_node", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        Node pageMenu = findNode(node.getNodes(), "menu");
        StringBuilder menuBuilder = new StringBuilder();
        HtmlCreator.this.generate(pageMenu, menuBuilder);

        Node content = findNode(node.getNodes(), "content");
        StringBuilder contentBuilder = new StringBuilder();
        HtmlCreator.this.generate(content, contentBuilder);
        
        node.getAttributes().put("menu", menuBuilder.toString());
        node.getAttributes().put("content", contentBuilder.toString());

        new SnippetNodeHandler(HtmlCreator.this, "page").handle(node, builder);
      }
    });
    
    handlers.put("content", new SnippetNodeHandler(this, "content"));
    handlers.put("section", new SnippetNodeHandler(this, "section"));
    handlers.put("subsection", new SnippetNodeHandler(this, "subsection"));
    handlers.put("p", new SnippetNodeHandler(this, "p"));
    handlers.put("span", new SnippetNodeHandler(this, "span"));
    handlers.put("a", new SnippetNodeHandler(this, "a"));
    handlers.put("list", new SnippetNodeHandler(this, "list"));
    handlers.put("item", new SnippetNodeHandler(this, "item"));
    handlers.put("code", new SnippetNodeHandler(this, "code"));
    handlers.put("example", new SnippetNodeHandler(this, "example"));
    
    handlers.put("menu", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        // Go over each menu item and output a snippet with regards
        // to whether it is the last one or not
        int i = 0;
        builder.append("<div>");
        for (Node menuItem : node.getNodes()) {
          if (i == node.getNodes().size() - 1) {
            builder.append(String.format(SnippetProvider.loadSnippet("menuitem-last").getContents(), 
                menuItem.getAttribute("target"), menuItem.getValue()));
          } else {
            builder.append(String.format(SnippetProvider.loadSnippet("menuitem").getContents(), 
                menuItem.getAttribute("target"), menuItem.getValue()));
          }
          i++;
        }
        builder.append("</div>");
      }
    });
  }
  
  public void generate(Node node, StringBuilder builder) {
    if (handlers.containsKey(node.getTagName())) {
      handlers.get(node.getTagName()).handle(node, builder);
    } else {
      throw new IllegalStateException("Unrecognised node type [" + node.getTagName() + "]");
    }
  }

  public void generate(List<Node> nodes, StringBuilder builder) {
    for (Node node : nodes) {
      generate(node, builder);
    }
  }

  public String generate(List<Node> nodes) {
    StringBuilder builder = new StringBuilder();
    
    generate(nodes, builder);
    
    return builder.toString();
  }
}