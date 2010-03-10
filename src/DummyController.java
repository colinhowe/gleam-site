import java.util.LinkedList;
import java.util.List;

public class DummyController {
  private List<String> names;
  
  public List<String> getNames() {
    return names;
  }
  
  public String getName() {
    return "DummyController";
  }

  public DummyController() {
    names = new LinkedList<String>();
    names.add("Andrew");
    names.add("Bob");
    names.add("Colin");
    names.add("Dan");
  }
}