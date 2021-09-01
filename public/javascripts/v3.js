"use strict"

const validateRoute  = document.getElementById("validateRoute").value;
const createRoute = document.getElementById("createRoute").value;
const taskRoute = document.getElementById("taskRoute").value;
const deleteRoute = document.getElementById("deleteRoute").value;
const addRoute = document.getElementById("addRoute").value;
const csrfToken= document.getElementById("csrfToken").value;
//const logoutRoute = document.getElementById("logoutRoute").value;

function login(){
    console.log("starting login");
    const username = document.getElementById("#loginName").value;
    const password = document.getElementById("#loginPass").value;
    fetch(validateRoute, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: {username,password}
    }).then(res => res.json()).then(data =>{
        console.log(data);
        if (data){
            document.getElementById("login-section").hidden = true
            document.getElementById("task-section").hidden = false
        }else {

        }
    });
}

function loadTask(){
    fetch(taskRoute).then(res => res.json()).then(task =>{

    })
}