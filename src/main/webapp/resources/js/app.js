const checkboxes = document.querySelectorAll("#checkbox");

checkboxes.forEach(item => {
    item.addEventListener("change", function (event) {
        if (event.target.checked) {
            event.target.parentElement.style.backgroundColor = "yellow";
        } else {
            event.target.parentElement.style.backgroundColor = "lightgreen";
        }

    });
});
