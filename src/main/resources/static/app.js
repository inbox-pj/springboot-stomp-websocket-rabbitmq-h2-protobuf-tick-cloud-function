const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/stock'
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

