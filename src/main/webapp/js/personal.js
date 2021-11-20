function showError(text) {
    $(".modal-error").show();
    $(".modal-error").text(text);
    setInterval(function () {
        $(".modal-error").hide();
    }, 5000);
}

AOS.init({
    once: true,
});
