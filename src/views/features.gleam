page(title: "Glimpse - Features") {
  section(title: "Features") {
    subsection(title: "Macros") {
      p "Macros allow for the re-use of basic building blocks"
      
      example ""
        node h3 with string
        node pre with string
        macro code with s : string {
          h3 "Code"
          pre s
        }
        
        code &quot;&quot;
          // We put our code here
        &quot;&quot;
      ""
    }
    
    subsection(title: "Macro/Node Restrictions") {
      p ""
        Restrict macros so that they can only be called within macros with
        certain names.
      ""
      
      example ""
        node h3 with string
        node p with string restrict to section
        macro section(title : string) with g : generator 
            restrict to top level {
          h3 s
        }
        
        section(title: "Welcome") {
          p "Welcome to my page!"
        }
        
        p "This won't compile, because p isn't allowed here!"
      ""
    }
    
    subsection(title: "Cascading Arguments") {
      p ""
        Cascading arguments allow an argument to be specified in a
        macro statement and then the argument is passed in wherever
        possible to any child macros.
      ""
      
      example ""
        node form(cascade readonly : boolean) with generator
        node textinput(id : string, readonly : boolean) with string
        
        form(readonly: true) {
          // readonly: true will be passed into the call to textinput
          // as it is a cascading argument on the readonly argument in
          // the form macro
          textinput(id: "name") "Name"
        }
      ""
    }
    
    subsection(title: "Macro overloading") {
      p ""
        Multiple macros can have the same name. Arguments will be used to
        find the macro to call. If more than one macro matches then a
        compiler error will occur.
      ""
      
      example ""
        node span with string
        node p with generator
        macro p with g : generator {
          node p { // Specify that the node should be called and not the macro
            span "top called!"
            include g
          }
        }
        
        macro p with s : string {
          span s
          span "bottom called!"
        }
        
        p {
          span "This will call the top p macro"
        }
        
        p "This will call the bottom p macro"
      ""
    }
    
    subsection(title: "Generics") {
      p ""
        Generics are supported in Glimpse in a similar way to Java.
      ""
      
      example ""
        macro result_set&lt;T&gt;(list : java.util.List&lt;T&gt;) with g : generator(row : T) {
          for (T t in list) {
            include g(row: t)
          }
        }
        
        result_set(list: c.people) { person : Person =&gt;
          span person.name
        }
      ""
    }
    
    subsection(title: "Iteration") {
      p ""
        Iteration using a for-each style is catered for.
      ""
      
      example ""
        for (Person person in c.people) {
          span "Name: "
          span person.name
        }
      ""
    }
    
    subsection(title: "Controllers") {
      p ""
        Controllers are the only way of passing data in to a view. This allows
        the view to reference properties on a controller in a type safe manner.
      ""
      
      example ""
        controller uk.co.colinhowe.example.WikiPageController
        
        span "Page name: "
        span c.pageName // Note this calls the controller's getPageName() method
      ""
    }

    subsection(title: "Static property references") {
      p ""
        Static property references allow you to generate a reference to a 
        bean on a view's controller. This then gives you access to not only
        the value of the bean but also the path of the bean in relation to
        the controller.
      ""
      
      example ""
        controller uk.co.colinhowe.example.SignupController
        
        macro field(field : ref) with label : string {
          node span label
          node input(id: p.path) p.value
          // This will create an input text box that uses the path
          // of the bean as the ID
        }
        
        // Note, we don't use get/set here, Glimpse will figure this out
        field(p: @c.name) "Name"
      ""
    }
    
    subsection(title: "if/else") {
      p ""
        Basic if/else expressions are supported.
      ""
      
      example ""
        var condition = true
        if (condition) {
          h1 "Condition was true"
        } else {
          h1 "Condition was false"
        }
      ""
    }
    
    subsection(title: "Basic type inference") {
      p "Glimpse supports very basic type inference when declaring variables."
      p ""
        The inference will assume that the variable takes the type of whatever
        is being assigned to it.
      ""
      
      example ""
        var name = "someString"
        name = 5 // This won't compile as name is a string and 5 is not a string
      ""
    }
    
    subsection(title: "Tidy multi-line strings") {
      p "Multi-line strings are supported in a way that handles indentation."
      
      example ""
        p &quot;&quot;
          This string is over several lines.
          However, it has no leading spaces until the very last line.
          This allows you to add indentation if you want but doesn't create
              messy code :)
        &quot;&quot;
      ""
    }
    
    subsection(title: "Dynamic macros") {
      p ""
        Dynamic macros allow for macro implementations to be switched at run time.
        This can be useful in various scenarios. For example, you could specify a
        table macro that takes a list as a parameter and then takes a generator to
        be called multiple times that defines only the columns.
      ""
      
      code ""
        table(items: c.people) { person : Person =&gt;
          col(name: "name") person.name
          col(name: "age") person.age
        }
      ""
      
      p ""
        In the above example the table macro would invoke the generator once
        with the col macro set to something that outputs headings. Then it
        would iterate over each person with the col macro set to output individual
        cells in the table.
      ""
      example ""
        dynamic macro field with string s
        macro field1 with string s {
          node field1 s
        }
        macro field2 with string s {
          node field2 s
        }
        field = field1
        field "A"
        field = field2
        field "B"
        // The above would output two nodes, field1 and then field2
      ""
    }
  }
}