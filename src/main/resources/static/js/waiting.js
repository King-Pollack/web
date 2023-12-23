$(document).ready(function () {

    waitingCheck();

    $('#waitingBtn').click(function (event) {
        event.preventDefault();
        var phoneNumber = $('#phoneNumber').val();
        var partySize = $('#partySize').val();
        var data = {
            phoneNumber: phoneNumber,
            partySize: partySize
        };
        $.ajax({
            type: 'POST',
            url: '/api/waiting',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json',
            success: function (response) {
                alert('대기 등록이 완료되었습니다.');
                waitingStream();
                waitingSuccess();
            },
            error: function (xhr, status, error) {
                if (xhr.status === 200) {
                    return;
                }
                if (xhr.status === 308) {
                    alert('이미 대기중입니다.');
                    waitingStream();
                    waitingSuccess();
                    return;
                }
                alert('error');
                nonWaitingStream();
            }
        });
    });
});

function waitingSuccess() {
    $('#waitingBtn').remove();
    $('#waitingForm').append('<div><button id="moveBackBtn" type="button" class="btn btn-warning">1팀 뒤로가기</button></div>');
    $('#waitingForm').append('<div><button id="moveToLastBtn" type="button" class="btn btn-warning">맨 뒤로가기</button></div>');
    // 새로운 버튼에 이벤트 핸들러 추가
    $('#moveBackBtn').click(moveBack);
    $('#moveToLastBtn').click(moveToLast);
}

function moveBack(event) {
    event.preventDefault();
    var phoneNumber = $('#phoneNumber').val();
    var partySize = $('#partySize').val();
    var data = {
        phoneNumber: phoneNumber,
        partySize: partySize
    };
    $.ajax({
        type: 'POST',
        url: '/api/waiting/move-one-step-back',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: function (response) {
            alert('1팀 뒤로 이동하였습니다.');
        },
        error: function (xhr, status, error) {
            if (xhr.status === 200) {
                alert('1팀 뒤로 이동하였습니다.');
                return;
            }
            alert('error');
        }
    });
}

function moveToLast(event) {
    event.preventDefault();
    var phoneNumber = $('#phoneNumber').val();
    var partySize = $('#partySize').val();
    var data = {
        phoneNumber: phoneNumber,
        partySize: partySize
    };
    $.ajax({
        type: 'POST',
        url: '/api/waiting/move-to-last',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: function (response) {
            alert('맨 뒤로 이동하였습니다.');
        },
        error: function (xhr, status, error) {
            if (xhr.status === 200) {
                alert('맨 뒤로 이동하였습니다.');
                return;
            }
            alert('error');
        }
    });
}


function nonWaitingStream() {
    var eventSource = new EventSource('/waiting/stream/total-team');
    eventSource.onmessage = function (event) {
        var dataContainer = document.getElementById("data-container");
        dataContainer.innerHTML = "";
        var newData = document.createElement("p");
        newData.innerText = '현재 대기 팀 : ' + event.data + ' 팀'
        dataContainer.appendChild(newData);
    };
    eventSource.onerror = function(event) {
        setTimeout(function() {
            eventSource =  new EventSource('/waiting/stream/total-team');
        }, 10000);
    };
}

function waitingStream() {
    var eventSource = new EventSource('/waiting/stream/my-ranking');
    eventSource.onmessage = function (event) {
        var dataContainer = document.getElementById("data-container");
        dataContainer.innerHTML = "";
        var newData = document.createElement("p");
        let data = event.data.split(':');
        newData.innerText = '현재 대기 팀 : ' + data[0] + ' 팀' + '\n' + '나의 순위 : ' + data[1] + ' 번째 '
        dataContainer.appendChild(newData);
    };
    eventSource.onerror = function(event) {
        setTimeout(function() {
            eventSource =  new EventSource('/waiting/stream/my-ranking');
        }, 10000);
    };
}

function waitingCheck() {
    $.ajax({
        type: 'POST',
        url: '/api/waiting/check',
        success: function (response) {
            waitingStream();
            waitingSuccess();
        },
        error: function (xhr, status, error) {
            nonWaitingStream();
        }
    });
}