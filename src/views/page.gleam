macro script with scode : string {
  script(type: "text/javascript") scode
}

node menu_item(target: string) with string 

node menu with generator

macro page_menu {
  menu {
    menu_item(target: "index") "What is Gleam?"
    menu_item(target: "features") "Features"
    menu_item(target: "antifeatures") "Anti-features"
    menu_item(target: "quickstart") "Quick start"
    menu_item(target: "roadmap") "Roadmap"
  }
}

node content with generator

node page_node(title: string) with generator

macro page(title : string) with g : generator {
  page_node(title: title) {
    page_menu
    content {
      include g
    }
  }
}

node script(type : string) with string restrict to script