page(title: "Quick Start") {
  section(title: "Quick start") {
    subsection(title: "Overview") {
      p "
        The quick start application makes it easier for you to get
        started with Gleam and experiment. It consists of:
      "
      
      list {
        item "A simple web-server that loads .gleam files and displays them"
        item "Some basic macros (macros.gleam) to demonstrate macros"
        item "An index.gleam page and Index.java class for a basic page"
        item "A simple transformer (HtmlCreator) for converting the generated node tree to HTML" 
        item "A couple of HTML snippets used by the transformer"
      }
    }
        
    subsection(title: "1. Prerequisites") {
      list {
        item "JDK 6"
        item "Ant"
        item "That's all, everything else is in the zip"
      }
    }
    subsection(title: "2. Download") {
      p {
        span "Version 0.1.0 is available "
        a(href: "resource/gleam-quickstart-0-1-0.zip") "here"
      }
    }
    subsection(title: "3. Unzip") {
      p "
        Unzip the downloaded file to a folder of your choice.
      "
    }
    subsection(title: "4. Start the application using Ant") {
      p "
        Open a terminal window and run the ant target 'run'.
      "
      
      code "
        ant run
      "
      
      p "
        If all is well, you will be greeted with something like:
      "  
      code "
        2010-04-25 20:54:50.441:INFO::jetty-7.0.2.v20100331
        2010-04-25 20:54:50.484:INFO::Started SelectChannelConnector@0.0.0.0:8080
      "
    }
    
    subsection(title: "5. Enjoy!") {
      p "
        Point your browser at localhost:8080/index and you will be greeted
        with a page telling you the time and a simple form.
      "
      
      p "
        By default, the quick start application is in developer mode.
        Any changes you make to .gleam files will be reflected when 
        you refresh the page. Any changes to Java classes will require
        restarting the application.
      "
    }
    
    subsection(title: "6. Changing the created HTML") {
      p "
        To get you familiar with the quick start application you can try
        changing the HTML snippets.
      "
      
      p "Open up snippets/field.html"
      
      p "
        Everything contained with two % signs is substituted (at run-time)
        with an attribute from the node being transformed. The only exception
        is %_inner% which substitutes in the HTML for child nodes.
      "
      
      p "
        Append a colon to %label% so that it looks like:
      "

      code "
        &lt;span&gt;%%label%%:&lt;/span&gt;&lt;input name=\"%%name%%\" value=\"%%value%%\" /&gt;&lt;br /&gt;
      "
    }
    
    subsection(title: "7. Changing the view") {
      p "Let's add some more text to our page. Open up views/index.gleam"
      
      p "Add the following in the page block:"
      
      code "
        paragraph {
          text \"
            I just changed my first view file :)
          \"
        }
      "
      
      p "
        Refresh your browser - the page will now be updated with
        your message.
      "
    }
    
    subsection(title: "8. Change the controller") {
      p "
        Open up src/pages/Index.java and add a new method getMagicNumber()
        that returns an integer, 42.
      "
      code "
        public int getMagicNumber() {
          return 42;
        }
      "
      
      p "
        Now open up index.gleam again and add the following:
      "
      code "
        text \"The magic number is: \"
        text c.magicNumber
      "
      
      p "
        Refresh your browser and you will be greeted with the magic number.
      "
    }
    
    
    subsection(title: "9. Explore") {
      p {
        span "
          You've now had a tour of the quick start application. To learn more
          go to the 
        " 
        a(href: "features") "features page"
        span "
           and try out some of the features for yourself!   
        "
      }
    }
  }
}
