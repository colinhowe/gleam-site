import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import uk.co.colinhowe.glimpse.Node;

public class HtmlCreator {
  
  private interface NodeHandler {
    public void handle(Node node, StringBuilder builder);
  }
  
  private Map<String, NodeHandler> handlers = new HashMap<String, NodeHandler>();
  
  public HtmlCreator() {
    
    handlers.put("page_node", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append(String.format(SnippetProvider.loadSnippet("page").getContents(), node.getAttribute("title"), subBuilder.toString()));
      }
    });
    
    handlers.put("content", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append(String.format(SnippetProvider.loadSnippet("content").getContents(), node.getAttribute("title"), subBuilder.toString()));
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
    
    handlers.put("ul", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append(String.format(SnippetProvider.loadSnippet("subsection").getContents(), node.getAttribute("title"), subBuilder.toString()));
      }
    });
    
    handlers.put("list", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append("<ul>" + subBuilder.toString() + "</ul>");
      }
    });
    
    handlers.put("item", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append("<li>" + node.getValue() + "</li>");
      }
    });
    
    handlers.put("li", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append(node.getValue());
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
        StringBuilder subBuilder = new StringBuilder();
        for (Node menuItem : node.getNodes()) {
          i++;
          if (i == node.getNodes().size()) {
            subBuilder.append(String.format(SnippetProvider.loadSnippet("menuitem-last").getContents(), 
                menuItem.getAttribute("target"), menuItem.getValue()));
          } else {
            subBuilder.append(String.format(SnippetProvider.loadSnippet("menuitem").getContents(), 
                menuItem.getAttribute("target"), menuItem.getValue()));
          }
        }
        
        builder.append(String.format(SnippetProvider.loadSnippet("menu").getContents(), subBuilder.toString()));
      }
    });
  }
  
  public void generate(Node node, StringBuilder builder) {
    if (handlers.containsKey(node.getId())) {
      handlers.get(node.getId()).handle(node, builder);
    } else {
      throw new IllegalStateException("Unrecognised node type [" + node.getId() + "]");
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