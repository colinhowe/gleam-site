page(title: "Use Patterns") {
  section(title: "Use Patterns") {
    leading_section {
      p "
        There are two main use patterns to Gleam
      "
      
      list {
        item "Straight to HTML"
        item "Creating semantic trees"
      }
    }
        
    subsection(title: "Straight to HTML") {
      p "
        You can use Gleam to build a HTML node tree directly. This can then be
        turned into a string and sent out as a page without much thought.
      "
      
      p "
        It is envisioned that this should be sufficient for the majority of users.
      "
      
      p "
        Using Gleam in this manner doesn't make it difficult to later switch to...
      "
    }
    
    subsection(title: "Creating Semantic Trees") {
      p "
        Instead of making macros create nodes of HTML you can use them to create
        a semantic view of a page. E.g.
      "
      
      code "
        macro field(property : ref) with label : string {
          node field(path : property.path, value : property.value) label
        }
      "
      
      p "
        This tree can then be turned into any representation that you want. Multiple
        HTML representations or even something like SWT invocations.
      "
      
      p "
        Separating out the creation of what your page means and how your page is
        output can yield many benefits.
      "
      
      list {
        item "
          Complex manipulation based on the node tree - perhaps the last row in a table
          of fields should have a special class applied to it for CSS purposes
        "
        
        item "
          Use in testing - this tree could be used to assist with testing pages
        "
        
        item "
          Use of HTML templates - as part of your process of turning the tree into
          a HTML page you could use HTML snippets directly from files provided by
          a designer.
        "
      }
      
      p "
        When used like this you can think of Gleam as a technology that allows you
        to create an API that can be used in a safe and consistent way.
      "
    }
  }
}
