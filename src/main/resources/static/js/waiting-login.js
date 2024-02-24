var eventSource = new EventSource('/waiting/stream/total-team');
eventSource.onmessage = function(event) {
    var dataContainer = document.getElementById("data-container");
    dataContainer.innerHTML = "";
    var newData = document.createElement("p");
    newData.innerText = '현재 대기 팀 : ' + event.data + ' 팀'
    dataContainer.appendChild(newData);
    eventSource.onerror = function(event) {
        setTimeout(function() {
            eventSource.close();
            eventSource =  new EventSource('/waiting/stream/total-team');
        }, 10000);
    };
};