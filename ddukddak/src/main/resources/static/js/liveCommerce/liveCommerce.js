$(document).ready(function(){
    $('.slider').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        infinite: true,
        autoplay: true,
        autoplaySpeed: 2000,
        prevArrow: '<button class="slick-prev" aria-label="Previous" type="button">←</button>',
        nextArrow: '<button class="slick-next" aria-label="Next" type="button">→</button>',
    });
});