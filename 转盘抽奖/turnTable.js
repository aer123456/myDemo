/**
 * Created by huguantao on 15/10/14.
 */

function rotate(){
    var deg = Math.random()*(360-0)+0;
    var prize_type;  //用于给后台传送奖品类型
    function roolit(){
        $(".rooler_arrow").rotate({
            angle: 0,
            animateTo: 1800+deg,
            duration: 5000,
        });
    }
    roolit();
    prize(deg);
    function prize(deg){
        if(deg>=0 && deg<30){
            setTimeout("$('#msgModal1').modal('show')",5000);
            prize_type = 'deepSearch';
        }
        else if(deg>=30 && deg<90){
            setTimeout("$('#msgModal3').modal('show')",5000);
            prize_type = 'tenPoints';
        }
        else if(deg>=90 && deg<150){
            setTimeout("$('#msgModal2').modal('show')",5000);
            prize_type = 'simpleSearch';
        }
        else if(deg>=150 && deg<210){
            setTimeout("$('#msgModal1').modal('show')",5000);
            prize_type = 'deepSearch';
        }
        else if(deg>=210 && deg<270){
            setTimeout("$('#msgModal3').modal('show')",5000);
            prize_type = 'tenPoints';
        }
        else if(deg>=270 && deg<330){
            setTimeout("$('#msgModal2').modal('show')",5000);
            prize_type = 'simpleSearch';
        }
        else if(deg>=330 && deg<360){
            setTimeout("$('#msgModal1').modal('show')",5000);
            prize_type = 'deepSearch';
        }
        else{}
    }
}

