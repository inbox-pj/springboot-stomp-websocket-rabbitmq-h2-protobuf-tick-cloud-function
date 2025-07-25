const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwamFpc3dhbCIsInJvbGUiOiJBRE1JTiIsImlzcyI6InN0b2NrZXhjaGFuZ2UiLCJpYXQiOjE3NTIxMzYxNjEsImV4cCI6MTc1MjIyMjU2MX0.eVrWm_J1MFQzbR28duo6SfQOas-OiB2z9xbOBOYebLE'
//const encodedToken = encodeURIComponent(token); // Properly encode the token

const stompClient = new StompJs.Client({
    brokerURL: `ws://localhost:8080/stock-exchange?token=${token}`,
    reconnectDelay: 5000, // Attempt reconnection after 5 seconds on disconnection
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/stockPrices', (stock) => {
        showStockPrice(JSON.parse(stock.body));
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#stocks").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    const name = $("#stockName").val()
    const price = parseFloat($("#stockPrice").val());

    stompClient.publish({
        destination: "/app/updateStock",
        body: JSON.stringify({ name: name, price: price })
    });
}

function showStockPrice(message) {
    $("#stocks").append("<tr><td>" + message.name + "</td><td>" + message.price.toFixed(2) + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#addStock" ).click(() => sendName());
});

