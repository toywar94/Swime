console.log("studyService Module.....");

let studyService = (function(){

    function get(stdSn, callback, error) {

        console.log("get stdSn = " + stdSn);

        $.get("/study/ajaxGet?stdSn=" + stdSn, function (result) {
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
        get : get
    };
})();