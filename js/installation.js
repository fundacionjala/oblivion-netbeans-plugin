function viewvideo(video,title,description) {
    $(".lock-page").fadeIn({duration: 500, queue: false}).css("display", "flex");
    var caption = "<p class='typing-fx'><b>"+ '<span class="glyphicon glyphicon-console" aria-hidden="true" ></span> ' + title + "</b>" + description + "<span class='cursor-text'>|</span></p>";
    $(".video-description").html(caption);
    $(".video-player").html("<video height='100%' width='100%' controls autoplay><source src='"+video+"' type='video/mp4' >Your browser does not support the video tag. </video>");
}

function closevideo() {
    $(".lock-page").fadeOut( "slow", function() {
    $(".lock-page").css("display", "none");
    $(".video-player").html("");
    });
}

function changeimg(option) {
    if(option == "r") {
        currentImageIndex++;
        if(currentImageIndex > images.image.length - 1) {
            currentImageIndex = 0;
        }
    } else {
        currentImageIndex--;
        if(currentImageIndex < 0) {
            currentImageIndex = images.image.length - 1;
        }
    }

    var item = images.image[currentImageIndex];
    $("#image-install").attr('src', item.url);
    $("#image-page").text((currentImageIndex + 1) +" / " + images.image.length);
    $("#image-title").html(item.description);
}

var currentImageIndex = 0;

var images = {
    "image":[
        {"url":"img/screenshot/Screenshot-1.png","description":"Install modules"},
        {"url":"img/screenshot/Screenshot-2.png","description":"Press the Next button"},
        {"url":"img/screenshot/Screenshot-3.png","description":"Accept the terms and install"},
        {"url":"img/screenshot/Screenshot-4.png","description":"Press the continue button"},
        {"url":"img/screenshot/Screenshot-5.png","description":"Finish"}
    ]
};

$(function() {
    $("#image-page").text("1 / " + images.image.length );
    $("#image-title").text(images.image[0].description);
});
