page(title: "A View Layer Language") {
  section(title: "What is Gleam?") {
    leading_section {
      p {
        span "Writing web applications using HTML is "
        a(href: "http://www.colinhowe.co.uk/2010/04/14/html-machine-code-for-web-pages/") "too low level"
        span ". "
        
        span "Whenever you write a page directly in HTML you are losing some of the meaning of the page."
      }
      
      p ""
        Gleam aims to make it easier to write pages in a higher level by separating the creation
        of a page's meaning from it's representation as HTML.
        of a page's meaning from it's representation as HTML.
      ""
    }
    
   subsection(title: "A short example") {
      p "Below is a snippet of the source for the features page."

      code ""
        page(title: "Features") {
          section(title: "Features") {
            subsection(title: "Macros") {
              p "Macros allow for the re-use of basic building blocks"
              
              example &quot;&quot;
                node h3 with string
                ...
              &quot;&quot;
            }
          }
        }
      ""
      
      p ""
        In the above, what look like function calls in other languages are 
        calls to macros in Gleam.
      ""
      
      p ""
        Each macro is a light-weight definition by the user, for example: 
      ""
      
      code ""
        node example with string
      ""
        
      p ""
        This defines the macro example as a node macro that can have a string
        as its value - Gleam is statically typed.
      ""
      
      p ""
        A page consists of a series of macro invocations. Each of these 
        invocations will result in one or more nodes being created. These
        nodes are joined together to form a node tree.
      ""
      
      p ""
        The node tree can then be transformed however you want. In the case of
        this site the node tree is turned into HTML using a set of snippets:
      ""
      
      code ""
        <div class="section"><h3>%title%</h3>%_inner%</div>
      ""
      
      p ""
        This separates the HTML from the node tree and gives two big benefits:
      ""
      
      list {
        item "HTML can be created independently by a designer"
        item "Front-end developers can concentrate on the semantic meaning of a page" 
      }
    }
    
    subsection(title: "Separation of Concerns") {
      p ""
        This style of creating views is an example of separation of concerns.
        The meaning of the page is now separated more distinctly from it's
        representation.
      ""
      
      p ""
        Amongst other things, this makes it far easier to create multiple
        versions of a single page. Making it easier to perform design
        changes and provide accessible versions of pages.
      ""
    }
    
    subsection(title: "Learn More") {
      p ""
        Gleam hasn't yet been released... but, do explore the site to learn
        more.
      ""
    }
  }
}
