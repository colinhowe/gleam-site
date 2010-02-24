$(document).ready(function() {
  $(".example a").show();
  $(".example pre").hide();

  $(".example a").click(function() {
    $(this).siblings("pre").slideToggle("fast");
    var find = '+';
    var replace = '-';
    if ($(this).text().indexOf('+') == -1) {
      find = '-';
      replace = '+';
    }
    $(this).text($(this).text().replace(find, replace));
  });
});
