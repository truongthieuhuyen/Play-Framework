@()
$("#contents").load("routes.TaskList2.login()")

function login(){
    console.log("starting login")
    const username = $("#loginName").val()
        console.log("receive login name"+username)
    const password = $("#loginPass").val()
        console.log("receive login pass"+password)
    $("#contents").load("/validate2?username="+username+"&password="+password);
}
function register(){
    console.log("starting do register")
    const username = $("#registerName").val()
    const password = $("#registerPass").val()
    $("#contents").load("/register2?username="+username+"&password"+password);
}

function deleteTask(index){
    $("#contents").load("/deleteTask2?index="+index)
}
function addNewTask(){
    const task = $("#newTask").val()
    $("#contents").load("/addTask2?task="+encodeURIComponent(task));
}