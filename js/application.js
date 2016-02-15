$(window).scroll(function() {
    if ($(this).scrollTop() > 500) {
        $('#scroll-up').fadeIn();
    } else {
        $('#scroll-up').fadeOut();
    }
});

$(document).ready(function(){
    $('#scroll-up').click(function(){
        $("html, body").animate({ scrollTop: 0 }, 600);
        return false;
    });
    getCurrentYear();
});

function getCurrentYear() {
    var currentYear = new Date().getFullYear();
    $("#copyright").append(currentYear);
}
