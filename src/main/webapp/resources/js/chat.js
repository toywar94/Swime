console.log("chatService Module.....");

let chatService = (function(){

    function getList(userId, callback, error) {

        $.get("/chat/ajaxList?userId=" + userId, function (result) {
            if (callback) {
                callback(result);
            }
        }).fail(function (xhr, status, err) {
            if (error) {
                error();
            }
        })
    }

    function getMsg(chatRoomId, callback, error) {
        $.get("/chat/getMsg?chatRoomId=" + chatRoomId, function (result) {
            if (callback) {
                callback(result);
            }
        }).fail(function (xhr, status, err) {
            if (error) {
                error();
            }
        })
    }

    return {
        getList : getList,
        getMsg : getMsg
    };
})();