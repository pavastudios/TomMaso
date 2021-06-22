function showError(text) {
    $(".modal-error").show();
    $(".modal-error").text(text);
    setInterval(function () {
        $(".modal-error").hide();
    }, 3000);
}

AOS.init({
    once: true,
});

$("#navbarSearchBlog").click(function () {
    $("#navbarSearchType").text("Blog");
    isUser = false;
    updateActionSearch();
});
$("#navbarSearchUser").click(function () {
    $("#navbarSearchType").text("Utente");
    isUser = true;
    updateActionSearch();
});
navbarSearchText.change(function () {
    updateActionSearch();
});