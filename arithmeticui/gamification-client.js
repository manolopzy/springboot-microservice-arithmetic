function updateLeaderBoard() {
    $.ajax({
        url: serverURL + "/gamification/leaders",
        type: 'GET',
		contentType: "application/json; charset=utf-8",
		dataType: "json"
    }).then(function(data) {
        console.log("leader board data = " + data);
        if(data == null || data ==""){
            return;
        }
        $('#leaderboard-body').empty();
        data.forEach(function(row) {
            console.log("row = " + row);
            if(row == null || row ==""){
                return;
            }
            $('#leaderboard-body').append('<tr><td>' + row.userId + '</td>' +
                '<td>' + row.totalScore + '</td>');
        });
    });
}

function updateStats(userId) {
    $.ajax({
        url: serverURL + "/gamification/stats?userId=" + userId,
        type: 'GET',
		contentType: "application/json; charset=utf-8",
		dataType: "json",
        success: function(data) {
            console.log("user stats data = " + data);
            $('#stats-div').show();
            $('#stats-user-id').empty().append(userId);
            $('#stats-score').empty().append(data.score);
            $('#stats-badges').empty().append(data.badges.join());
        },
        error: function(data) {
            $('#stats-div').show();
            $('#stats-user-id').empty().append(userId);
            $('#stats-score').empty().append(0);
            $('#stats-badges').empty();
        }
    });
}

$(document).ready(function() {

    updateLeaderBoard();

    $("#refresh-leaderboard").click(function( event ) {
        updateLeaderBoard();
    });

});
