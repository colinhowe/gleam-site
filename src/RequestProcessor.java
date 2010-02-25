import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.colinhowe.glimpse.CompilationError;
import uk.co.colinhowe.glimpse.CompilationResult;
import uk.co.colinhowe.glimpse.Node;
import uk.co.colinhowe.glimpse.View;
import uk.co.colinhowe.glimpse.compiler.CompilationUnit;
import uk.co.colinhowe.glimpse.compiler.GlimpseCompiler;
import Acme.Serve.Serve;

public class RequestProcessor extends HttpServlet {
  public static void main(String[] args) {
    // setting properties for the server, and exchangable Acceptors
    java.util.Properties properties = new java.util.Properties();
    properties.put("port", 8080);
    properties.setProperty(Acme.Serve.Serve.ARG_NOHUP, "nohup");

    final Serve srv = new Serve();
    srv.arguments = properties;
    srv.addDefaultServlets(null); // optional file servlet
    srv.addServlet("/", new RequestProcessor()); // optional
    
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      public void run() {
        try {
          srv.notifyStop();
        } catch (java.io.IOException ioe) {

        }
        srv.destroyAllServlets();
      }
    }));
    srv.serve();
  }
  
  private byte[] readFile(String filename) {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(filename);
      int numberBytes = fis.available();
      byte bytearray[] = new byte[numberBytes];
  
      fis.read(bytearray);
      return bytearray;
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
  
  @SuppressWarnings("deprecation")
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    long startTime = System.currentTimeMillis();
    
    // Locate all the glimpse files
    String requestUri = request.getRequestURI().substring(1);
    if (requestUri.indexOf(".") != -1) {
      // Get from the resources folder
      final byte[] contents = readFile("resource/" + requestUri);
      response.getOutputStream().write(contents);
      return;
    }
    
    final File viewsFolder = new File(this.getClass().getResource("views/").getFile());
    final List<File> viewFiles = new LinkedList<File>();
    for (final File viewFile : viewsFolder.listFiles()) {
      if (viewFile.getAbsolutePath().endsWith(".glimpse")) {
        viewFiles.add(viewFile);
      }
    }

    // Create compilation units for each file
    final List<CompilationUnit> units = new LinkedList<CompilationUnit>();
    for (final File viewFile: viewFiles) {
      final String viewName = viewFile.getName().substring(0, viewFile.getName().indexOf(".glimpse"));
      final BufferedReader reader = new BufferedReader(new FileReader(viewFile));
      final StringBuffer source = new StringBuffer();
      String line;
      while ((line = reader.readLine()) != null) {
        source.append(line + "\n");
      }
      reader.close();
      
      final CompilationUnit unit = new CompilationUnit(viewName, source.toString(), viewFile.getName());
      units.add(unit);
    }
    
    // Compile all the units
    String result = "";
    try {
      final GlimpseCompiler compiler = new GlimpseCompiler();
      
      List<String> classPaths = new LinkedList<String>();
      classPaths.add("../glimpse/bin");
      
      List<CompilationResult> compilationResults = compiler.compile(units, classPaths);
      
      for (CompilationResult compilationResult : compilationResults) {
        for (CompilationError error : compilationResult.getErrors()) {
          result += compilationResult.getFilename() + ": " + error.toString();
        }
      }
      
      if (result.length() == 0) {
        
        final ClassLoader classLoader = new URLClassLoader(new URL[] { new File("temp/").toURL() });
        final Class<?> viewClazz;
        try {
          viewClazz = classLoader.loadClass(request.getRequestURI().substring(1));
        } catch (ClassNotFoundException e) {
          throw new RuntimeException("Failed to instantiate view", e);
        }
        
        for (int i = 0; i < 1; i++) {
          final View view;
          try {
            view = (View)viewClazz.newInstance();
          } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate view", e);
          }
      
          final Object controller = new DummyController();
          final List<Node> nodes = view.view(controller);
          final StringBuilder output = new StringBuilder();
          for (final Node node : nodes) {
            output.append(outputNode(node));
          }
          result = output.toString();
        }
      }
    } catch (Throwable e) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      result = sw.toString();
    }
    
    final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
    writer.write(result);
    writer.flush();
    writer.close();
    
    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    System.out.println("Execution performed in " + totalTime + "ms");
  }
  
  private String outputNode(final Node node) {
    StringBuffer attributes = new StringBuffer();
    StringBuffer result = new StringBuffer();
    for (Entry<String, Object> attribute : node.getAttributes().entrySet()) {
      attributes.append(" " + attribute.getKey() + "=\"" + attribute.getValue() + "\"");
    }
    
    result.append("<" + node.getId() + attributes + ">");
    if (node.getValue() != null) {
      result.append(node.getValue().toString());
    } else {
      for (final Node childNode : node.getNodes()) {
        result.append(outputNode(childNode));
      }
    }
    result.append("</" + node.getId() + ">");
    return result.toString();
  }
}