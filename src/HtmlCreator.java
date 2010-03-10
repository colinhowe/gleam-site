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

  
  private String loadSnippet(String snippetName) {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream("snippets/" + snippetName + ".html");
      int numberBytes = fis.available();
      byte bytearray[] = new byte[numberBytes];
  
      fis.read(bytearray);
      return new String(bytearray);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
  
  public HtmlCreator() {
    
    handlers.put("page_node", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append(String.format(loadSnippet("page"), node.getAttribute("title"), subBuilder.toString()));
      }
    });
    
    handlers.put("content", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append(String.format(loadSnippet("content"), node.getAttribute("title"), subBuilder.toString()));
      }
    });
    
    handlers.put("section", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append(String.format(loadSnippet("section"), node.getAttribute("title"), subBuilder.toString()));
      }
    });
    
    handlers.put("subsection", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        StringBuilder subBuilder = new StringBuilder();
        generate(node.getNodes(), subBuilder);

        builder.append(String.format(loadSnippet("subsection"), node.getAttribute("title"), subBuilder.toString()));
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

        builder.append(String.format(loadSnippet("subsection"), node.getAttribute("title"), subBuilder.toString()));
      }
    });
    
    handlers.put("li", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append(node.getValue());
      }
    });
    
    handlers.put("code", new NodeHandler() {
      public void handle(Node node, StringBuilder builder) {
        builder.append(String.format(loadSnippet("code"), node.getValue()));
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
          if (i == node.getNodes().size()) {
            subBuilder.append(String.format(loadSnippet("menuitem-last"), 
                node.getAttribute("target"), node.getValue()));
          } else {
            subBuilder.append(String.format(loadSnippet("menuitem"), 
                node.getAttribute("target"), node.getValue()));
          }
          i++;
        }
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