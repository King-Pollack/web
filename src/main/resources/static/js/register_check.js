const joinForm = document.joinForm;

function sendit() {
    const uid = joinForm.userid;
    if (uid.value == "") {
        swal({
            text: "아이디를 입력하세요.",
            closeOnClickOutside: false
        }).then(function () {
            uid.focus();
        });
        return false;
    }
    const upw = joinForm.pwd;
    if (upw.value == "") {
        swal({
            text: "비밀번호를 입력하세요.",
            closeOnClickOutside: false
        }).then(function () {
            upw.focus();
        });
        return false;
    }
    if (upw.value.length < 6) {
        swal({
            text: "비밀번호는 6글자 이상이어야 합니다.",
            closeOnClickOutside: false
        }).then(function () {
            upw.focus();
        });
        return false;
    }
    const upwCheck = joinForm.userpwd;
    if (upwCheck.value == "") {
        swal({
            text: "비밀번호를 확인해주세요.",
            closeOnClickOutside: false
        }).then(function () {
            upwCheck.focus();
        });
        return false;
    }
    if (upw.value != upwCheck.value) {
        swal({
            text: "비밀번호가 일치하지 않습니다.",
            closeOnClickOutside: false
        }).then(function () {
            upw.focus();
        });
        return false;
    }
    const uname = joinForm.username;
    if (uname.value == "") {
        swal({
            text: "이름을 입력하세요.",
            closeOnClickOutside: false
        }).then(function () {
            uname.focus();
        });
        return false;
    }
    const uphone = joinForm.phone;
    if (uphone.value == "") {
        swal({
            text: "전화번호를 입력하세요.",
            closeOnClickOutside: false
        }).then(function () {
            uphone.focus();
        });
        return false;
    }
}