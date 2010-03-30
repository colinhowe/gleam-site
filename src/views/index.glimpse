page(title: "Glimpse - A View Layer Language") {
  section(title: "What is Glimpse?") {
    p {
      span "Glimpse is a statically typed view layer language for the JVM."
    }
    
    subsection(title: "A view layer language?") {
      p {
        span "The standard paradigm for doing web development is the "
        a(href: "http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller") "MVC"
        span " pattern."
      }
      
      p {
        span ""
          Glimpse positions itself as a language that is well suited for writing
          views.
        ""
      }
    }
    
    subsection(title: "What are the benefits?") {
      p {
        span "Glimpse offers many benefits:"
      }
            
      list {
        item "Static typing"
        item "Compile time checking to ensure valid structures are produced"
        item "Increased re-use of design elements"
        item "Abstraction of design elements away from the page itself"
        item "Fully compiled - making it very quick"
      }
    }
    
   subsection(title: "Enough already, give me some examples") {
      p { 
        span "Sure."
      }
      code ""
        node text with string
        text "Hello world!"
      ""
    }
    
    subsection(title: "... something more complex please") {
      code ""
        // Definitions will normally belong in a few files
        node h3 with string
        node p with string
        macro section(title : string) with contents : string {
          h3 title
          p contents
        }
        
        // Uses of the macros belong in individual views
        section(title : "Hello world") &quot;&quot;
          This will output two nodes.
          A h3 followed by a p ready for output
          straight to HTML.
        &quot;&quot;
      ""
      
      p {
        span ""
          What we see above is a macro definition and usage. Macros are similar 
          to functions in most languages.
        ""
      }
      p {
        span ""
          The real beauty here is that you can change the code macro and the
          changes will be found everywhere you used the code macro. This leaves
          your designers free to make code blocks look beautiful, and your
          developers free to get on with content and basic logic.
        ""
      }
    }
  }
}
