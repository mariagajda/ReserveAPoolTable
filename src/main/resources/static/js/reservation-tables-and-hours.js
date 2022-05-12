const checkboxes = document.querySelectorAll("#checkbox")
checkboxes.forEach(item => {
    item.addEventListener("click", function(event){
        event.target.parentElement.style.backgroundColor = "yellow";
    });
});