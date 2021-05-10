console.log("modal module...");

function modal(ctx){
    let modalStr = (
        "    <div class='modal' style='display:none;' id='myModal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-modal='true' style='padding-right: 17px; display: block;'>\n" +
        "        <div class='modal-dialog'>\n" +
        "            <div class='modal-content'>\n" +
        "                <div class='modal-header'>\n" +
        "                    <h4 class='modal-title' id='myModalLabel'>Modal title</h4>\n" +
        "                    <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>\n" +
        "                </div>\n" +
        "                <div class='modal-body' id='myModalBody'>content</div>\n" +
        "                <div class='modal-footer' id='myModalFooter'>\n" +
        "                    footer\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>"
    );



    function modalSetting(title, body, footer, footerFunc) {
        $("#myModalLabel").html(title);
        $("#myModalBody").html(body);
        footerSetting(footer, footerFunc);

        $(".close").on("click", function () {
            $(".modal").fadeOut();
        });

        function footerSetting(footer, footerFunc) {

            $(".modal-footer > button").unbind("click");

            let alert = "<button type='button' class='btn btn-primary' data-dismiss='modal'>확인</button>";
            let confirm = "<button type='button' class='btn btn-primary' data-dismiss='modal'>확인</button>" +
                "<button type='button' class='btn btn-danger' data-dismiss='modal'>취소</button>";


            if(footer !== undefined) {
                if(footer === 'alert')
                    $("#myModalFooter").html(alert);
                if(footer === 'confirm')
                    $("#myModalFooter").html(confirm);

                $("#myModalFooter").show();
            }
            else $("#myModalFooter").hide();

            $(".modal-footer > button").on("click",function () {
                if(footerFunc !== undefined) footerFunc();
                else $(".modal").fadeOut();
            });
        }
    }



    $(ctx).html(modalStr);

    return {
        modalHide : function (){
            $(".modal").fadeOut();
        },
        modalShow :function (){
            $("#myModal").fadeIn();
        },
        modalSetting : modalSetting
    };
}