console.log("running JS");

$("#random-btn").click(function(){
    $("#random-here").load("/random");
});
//$("#random-str-btn").click(function(){
//    $("#random-str").load("/randomString/9");
//});

//const stringText = document.getElementById("randomStrBtn");
//stringText.onClick = () => {
//    const lengthInput = document.getElementById("lengthVal");
//    const url = "/randomString/"+lengthInput.value;
//    console.log(url);
//    fetch(url).then((response) => {
//        return response.text();
//    }).then((responseText) => {
//        const randomStr = document.getElementById("randomStr");
//        randomStr.innerHTML = responseText;
//    });
//}

const stringText = document.getElementById("randomStringBtn");
stringText.onclick = () => {
	const lengthInput = document.getElementById("lengthValue");
	const url = "/randomString/" + lengthInput.value;
	console.log(url);
	fetch(url).then((response) => {
		return response.text();
	}).then((responseText) => {
		const randomString = document.getElementById("randomStr");
		randomString.innerHTML = responseText;
	})
};