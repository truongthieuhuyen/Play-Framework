const loginRoute = $("#loginRoute").val();
const validateRoute  = $("#validateRoute").val();
const createRoute = $("#createRoute").val();
const deleteRoute = $("#deleteRoute").val();
const addRoute = $("#addRoute").val();
const csrfToken= $("#csrfToken").val();

$("#contents").load(loginRoute);

function login(){
    console.log("starting login");
    const username = $("#loginName").val();
    const password = $("#loginPass").val();
    $.post(validateRoute,
        {username, password, csrfToken},
        data => {$("#contents").html(data)};
    );
}
function register(){
    console.log("starting do register");
    const username = $("#registerName").val();
    const password = $("#registerPass").val();
    $.post(create);
}

function deleteTask(index){
    $("#contents").load("/deleteTask2?index="+index)
}
function addNewTask(){
    const task = $("#newTask").val();
    $("#contents").load("/addTask2?task="+encodeURIComponent(task));
}