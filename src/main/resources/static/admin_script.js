const url = 'http://localhost:8080/api/admin';
//On JS doc loading
getUsers();



let form_edit = document.getElementById('form_edit');

function getUsers() {

    fetch('http://localhost:8080/api/admin')
        .then(response => response.json())
        .then(users => {
            let userTable = document.getElementById('userList');
            userTable.innerHTML = '';

            for (let index = 0; index < users.length; index++) {
                let tr = document.createElement('tr');
                let user = users[index];
                tr.innerHTML = `
    <td>${user.id}</td> 
    <td>${user.name}</td> 
    <td>${user.surname}</td> 
    <td>${user.email}</td> 
    <td>${user.age}</td> 
    <td id=${'role' + user.id}>${user.roles.map(role => role.name).join(' ')}</td> 
    <td><button class='btn btn-info' type='button' data-bs-toggle='modal' data-bs-target='#editModal' onclick='modalEditUser(${user.id})'>Edit user</button></td> 
    <td><button class='btn btn-danger' type='button' data-bs-toggle='modal' data-bs-target='#deleteUserModal' onclick='ModalDeleteUser(${user.id})'>Delete user</button></td>`;
                userTable.append(tr);
            }
        });
}

function modalEditUser(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => res.json())
        .then(user => {
            let editId = document.getElementById('editId');
            let editName = document.getElementById('editName');
            let editSurname = document.getElementById('editSurname');
            let editAge = document.getElementById('editAge');
            let editEmail = document.getElementById('editEmail');
            let editPass = document.getElementById('editPassword');


            editId.value = user.id;
            editName.value = user.name;
            editSurname.value = user.surname;
            editAge.value = user.age;
            editEmail.value = user.email;
            editPass.value = user.password;
        })
}

function ModalDeleteUser(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => res.json())
        .then(user => {
            let deleteId = document.getElementById('deleteId');
            let deleteName = document.getElementById('deleteName');
            let deleteSurname = document.getElementById('deleteSurname');
            let deleteAge = document.getElementById('deleteAge');
            let deleteEmail = document.getElementById('deleteEmail');
            let deleteRoles = document.getElementById('deleteRoles');

            deleteId.value = user.id;
            deleteName.value = user.name;
            deleteSurname.value = user.surname;
            deleteAge.value = user.age;
            deleteEmail.value = user.email;
            deleteRoles.value = user.roles.map(role => role.name).join(' ');
        })
}

function closeModal() {
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}

async function editUser() {
    let editId = document.getElementById('editId').value;
    let editName = document.getElementById('editName').value;
    let editSurname = document.getElementById('editSurname').value;
    let editAge = document.getElementById('editAge').value;
    let editEmail = document.getElementById('editEmail').value;
    let editPass = document.getElementById('editPassword').value;

    let editRole = form_edit.querySelectorAll('option');

    let listOfRole = [];
    for (let i = 0; i < editRole.length; i++) {
        if (editRole[i].selected) {
            listOfRole.push({id: editRole[i].value, name: 'ROLE_' + editRole[i].innerHTML});
        }
    }
    let user = {
        id: editId,
        name: editName,
        surname: editSurname,
        age: editAge,
        email: editEmail,
        password: editPass,
        roles: listOfRole
    }
    await fetch(url + '/' + user.id, {
        method: "PATCH",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
    closeModal();
    getUsers();
}

async function deleteUser() {
    let deleteId = document.getElementById('deleteId');
    await fetch(url + "/" + deleteId.value, {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    });
    closeModal();
    getUsers();
}
document.getElementById('new-user').addEventListener('submit', (e) => {
    e.preventDefault();
    let newName = document.getElementById('new-user-name').value;
    let newSurname = document.getElementById('new-user-surname').value;
    let newAge = document.getElementById('new-user-age').value;
    let newEmail = document.getElementById('new-user-email').value;
    let newPass = document.getElementById('new-user-password').value;
    let newRole = document.getElementById('new-user').querySelectorAll('option');
    let roleList = [];
    for (let i = 0; i < newRole.length; i++) {
        if (newRole[i].selected) {
            roleList.push({id: newRole[i].value, name: 'ROLE_' + newRole[i].innerHTML});
        }
    }
    let newUser = {
        name: newName,
        surname: newSurname,
        age: newAge,
        email: newEmail,
        password: newPass,
        roles: roleList
    }
    fetch(url, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(newUser)
    }).then((response) => {
        console.log(response)
        if (response.ok) {
            getUsers();
            document.getElementById('users-table-button').click();
        }
    })

})