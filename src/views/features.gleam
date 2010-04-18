page(title: "Features") {
  section(title: "Features") {
    
    subsection(title: "Node Macros") {
      p "Macros are similar to functions in other languages."
      p "Node macros are a simple form of macro."
      
      example ""
        node text with string
        node section with generator
        
        section {
          text "Hi there"
        }
        
        // This view would create a node tree with the text node as a child
        // of the section node.
      ""
    }
    
    subsection(title: "Named Arguments") {
      p ""
        Macros are invoked using named arguments and can be provided in
        any order.
      "" 
      
      example ""
        node detail(label : string, value : string)
        
        detail(label: "Username", value: "Bob")        
        detail(value: "Red", label: "Favourite colour")        
      ""
    }
    
    subsection(title: "Default Arguments") {
      p ""
        Arguments can have default values.
      "" 
      
      example ""
        node detail(label : string, value : string = "Unknown")
        
        detail(label: "Username", value: "Bob")        
        detail(label: "Favourite colour")
        
        // The last detail node will contain a value of "Unknown" as it
        // has not been specified when calling the macro.
      ""
    }
    
    subsection(title: "Controllers") {
      p ""
        Controllers are the only way of passing data in to a view. This allows
        the view to reference properties on a controller in a type safe manner.
      ""
      
      example ""
        controller uk.co.colinhowe.example.WikiPageController
        
        node text with string
        
        text "Page name: "
        text c.pageName // Note this calls the controller's getPageName() method
      ""
    }
    
    subsection(title: "Macros") {
      p ""
        Proper macros allow for the execution of code to decide what nodes
        to output yet still retain simple views.
      "" 
      
      example ""
        macro profile(detailed : bool, profile : UserProfile) with s : string {
          detail(label: "Name", value: profile.name)
          
          if (detailed) {
            detail(label: "E-mail", value: profile.email)
            detail(label: "Phone number", value: profile.phoneNumber)
          }
        }
        
        profile(detailed : true, c.customer)
      ""
    }
    
    subsection(title: "Generators") {
      p ""
        Gleam allows you to pass generators to macros. Generators are
        just blocks of code contained with curly braces { }. These can then
        be included in the node tree by macros.
      "" 
      
      example ""
        macro two_times with generator g {
          title "First"
          include g
          title "Second"
          include g
        }
        
        two_times {
          text "Repeat me!"
        }
        
        /*
         * Creates a node tree like:
         *   title "First"
         *   text "Repeat me!"
         *   title "Second"
         *   text "Repeat me!"
         */
      ""
    }
    
    subsection(title: "Iteration") {
      p ""
        Iteration using a for-each style is catered for.
      ""
      
      example ""
        for (person : Person in c.people) {
          detail(label: "Name", value: person.name)
        }
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
        
        // Note, we don't use get/set here, Gleam will figure this out
        field(p: @c.name) "Name"
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
          include g
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
        macro p with g : generator {
          span "top called!"
          include g
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
        Generics are supported in Gleam in a similar way to Java.
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
    
    subsection(title: "Basic type inference") {
      p "Gleam supports very basic type inference when declaring variables."
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
        
        section {
          subsection {
            p &quot;&quot;
              This string won't contain any leading spaces because of the indentation
              rules.
            &quot;&quot;
          }
        }
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
          text "Field1 called"
          text s
        }
        
        macro field2 with string s {
          text "Field2 called"
          text s
        }
        
        field = field1
        field "A"
        field = field2
        field "B"
        
        /*
         * The above would output the following:
         *   text "Field1 called"
         *   text "A"
         *   text "Field2 called"
         *   text "B"
         */
      ""
    }
  }
}
