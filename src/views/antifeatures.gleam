page(title: "Anti-features") {
  section(title: "What are Anti-features?") {
    p {
      a(href: "http://en.wikipedia.org/wiki/Anti-feature") "Anti-features"
      span " are design features that reduce the amount of functionality in something."
    }
    
    p ""
      Usually this is viewed as a bad thing. However, in the case of Gleam it is intended as a good thing.
      Certain features have been added as a means of encouraging developer's to put their business logic
      in the right place and not in the view layer.
    ""
    
    p ""
      Gleam also lacks certain features, such as classes. This is part of the
      design of Gleam and it is hoped that it will encourage better seperation of
      concerns in your views. 
    ""
    
    subsection(title: "Omitted Features") {
      p ""
        Below is a list of features that are in many languages but have been purposefully omitted
        from Gleam.
      ""
      
      list {
        item "Most mathematical operations"
        item "The new keyword from Java/C++"
        item "Classes - they can be used, just not created"
        item "Closures"
        item "Reflection"
        item "While loops"
        item "Switch statements"
      }
    }
    
    subsection(title: "Anti-features") {
      p ""
        Below is a list of features that have been purposefully added to restrict the
        what can be done in a view.
      ""
      
      list {
        item ""
          Generators, not closures - generators are very similar to closures but you
          can't pass them around in an ad-hoc manner
        ""
      }
    }
  }
}
