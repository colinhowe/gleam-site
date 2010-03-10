$(document).ready(function() {
  $(".example a").show();
  $(".example div").hide();

  $(".example a").click(function() {
    $(this).siblings("div").slideToggle('fast');
    var find = '+';
    var replace = '-';
    if ($(this).text().indexOf('+') == -1) {
      find = '-';
      replace = '+';
    }
    $(this).text($(this).text().replace(find, replace));
    return false;
  });
  
  SyntaxHighlighter.defaults['gutter'] = false;
  SyntaxHighlighter.all();
});
