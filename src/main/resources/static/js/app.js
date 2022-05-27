const checkboxes = document.querySelectorAll("#checkbox");

checkboxes.forEach(item => {
    item.addEventListener("click", function(event){
        // if(event.target.parentElement.style.backgroundColor == "lightgreen"){
            event.target.parentElement.style.backgroundColor = "yellow";
        // };
    });
    // item.addEventListener("click", function (event){
    //         if(event.target.parentElement.style.backgroundColor == "yellow"){
    //         event.target.parentElement.style.backgroundColor = "lightgreen";
    //     }
    // });
});