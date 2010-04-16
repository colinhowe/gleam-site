import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gleam.Node;

public class HtmlCreator {
  
  private interface NodeHandler {
    public void handle(Node node, StringBuilder builder);
  }
  
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
        
        builder.append(String.format(SnippetProvider.loadSnippet("page").getContents(), 
            node.getAttribute("title"), 
            node.getAttribute("title"), 
            menuBuilder,
            contentBuilder));
      }
    });
    
    handlers.put("content", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        generate(node.getNodes(), builder);
      }
    });
    
    handlers.put("section", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append(String.format(SnippetProvider.loadSnippet("section").getContents(), node.getAttribute("title"), subBuilder.toString()));
      }
    });
    
    handlers.put("subsection", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append(String.format(SnippetProvider.loadSnippet("subsection").getContents(), node.getAttribute("title"), subBuilder.toString()));
      }
    });
    
    handlers.put("p", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        if (node.getValue() != null) {
          builder.append("<p>" + node.getValue() + "</p>");
        } else {
          StringBuilder subBuilder = new StringBuilder();
          generate(node.getNodes(), subBuilder);
          builder.append("<p>" + subBuilder + "</p>");
        }
      }
    });
    
    handlers.put("span", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append("<span>" + node.getValue() + "</span>");
      }
    });
    
    handlers.put("a", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append("<a href=\"" + node.getAttribute("href") + "\">" + node.getValue() + "</a>");
      }
    });
    
    handlers.put("list", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append("<ul>" + subBuilder + "</ul>");
      }
    });
    
    handlers.put("item", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append("<li>" + node.getValue() + "</li>");
      }
    });
    
    handlers.put("code", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append(String.format(SnippetProvider.loadSnippet("code").getContents(), node.getValue()));
      }
    });
    
    handlers.put("example", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append(String.format(SnippetProvider.loadSnippet("example").getContents(), node.getValue()));
      }
    });
    
    handlers.put("example", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append(String.format(SnippetProvider.loadSnippet("example").getContents(), node.getValue()));
      }
    });
    
    handlers.put("page_title", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append(node.getValue());
      }
    });
    
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