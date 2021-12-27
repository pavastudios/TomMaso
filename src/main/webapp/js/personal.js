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
$("#navbarSearchText").change(function () {
    updateActionSearch();
});

function isStandardName(str) {
    return str.match(/^[a-zA-Z_][a-zA-Z0-9-._]{7,19}$/);
}
