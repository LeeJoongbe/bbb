console.log("Reply Modul..........");


let replyService = (function (){

    function add(reply, callback, error){
        console.log("reply.............");

        $.ajax({
            type : 'post',
            url : '/replies/',
            data : JSON.stringify(reply),
            contentType : "application/json; charset=utf-8",
            success : function (result, status, xhr){
                if(callback){
                    callback(result);
                }
            },
            error : function (xhr, status, er){
                if(error) {
                    error(er);
                }
            }//error
        }); //.ajax
    };

    function getList(param, callback, error){

        var bno = param.bno;
        var page = param.page;
        let pageRequestDTO = {
            "page":page
        };
        console.log("파람 입력값" + page);
        $.getJSON({
            url : "/replies/list/"+bno,
            data : pageRequestDTO, //pageRequestDTO 변경은 MediaType json //page  all_value/text
            contentType: "application/json; charset=utf-8"
            }
            ,function (data) {
            if(callback){
                callback(data); //데이터 결과값
            }
        }).fail(function (xhr,status,err){
            if(error){
                error();
            }
        });
    };

    function displayTime(timeValue){
        let today = new Date();

        let gap = today.getTime() - timeValue;

        let dateValue = new Date(timeValue);

        let str = "";

        if(gap < (1000 * 60 * 60 * 24)){
            let hh = dateValue.getHours();
            let mi = dateValue.getMinutes();
            let ss = dateValue.getSeconds();

            return [ ( hh > 9 ? '' :  '0') + hh, ':' , ( mi > 9 ? '' : '0' ) + mi, ':', ( ss > 9 ? '':'0') + ss ].join('');

        }else {
            let yy = dateValue.getFullYear();
            let mm = dateValue.getMonth() + 1;
            let dd = dateValue.getDate();
            return [ yy, '/', (mm > 9 ? '':'0') + mm, '/', (dd>9 ? '':'0')+dd].join('');
        }//else

    };

    function remove(rno, callback, error){
        $.ajax({
            type: 'delete',
            url: '/replies/'+rno,
            success : function (deleteResult, status, xhr) {
                if(callback){
                    callback(deleteResult);
                }
            },
            error : function (xhr, status, er){
                if(error){
                    error(er);
                }
            }//error
        }); //.ajax
    };//remove

    function update(reply, callback, error){
        console.log("RNO : " + reply.rno);
        $.ajax({
            type: 'put',
            url: '/replies/'+reply.rno,
            data: JSON.stringify(reply),
            contentType : "application/json; charset=utf-8",
            success : function (updateResult, status, xhr) {
                if(callback){
                    callback(updateResult);
                }
            },
            error : function (xhr, status, er){
                if(error){
                    error(er);
                }
            }//error
        });//.ajax
    };//update

    function get(rno, callback, error){
        $.get("/replies/"+rno, function (result) {
            console.log(result);
            if(callback){
                callback(result);
            }
        }).fail(function (xhr, status, err){
            if(error){
                error();
            }
        });
    };

    //return {name: "AAAA"};     //1
    return {
        add :  add,
        getList : getList,
        remove : remove,
        update : update,
        get : get,
        displayTime : displayTime
    };
})();

