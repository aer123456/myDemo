var imgmaps = {
    "/::)":"0.png","/::~":"1.png","/::B":"2.png","/::|":"3.png","/:8-)":"4.png","/::&lt;":"5.png","/::$":"6.png","/::X":"7.png","/::Z":"8.png","/::’(":"9.png","/::-|":"10.png","/::@":"11.png","/::P":"12.png","/::D":"13.png","/::O":"14.png","/::(":"15.png","/::+":"16.png","/:–b":"17.png","/::Q":"18.png","/::T":"19.png","/:,@P":"20.png","/:,@-D":"21.png","/::d":"22.png","/:,@o":"23.png","/::g":"24.png","/:|-)":"25.png","/::!":"26.png","/::L":"27.png","/::&gt;":"28.png","/::,@":"29.png","/:,@f":"30.png","/::-S":"31.png","/:?":"32.png","/:,@x":"33.png","/:,@@":"34.png","/::8":"35.png","/:,@!":"36.png","/:!!!":"37.png","/:xx":"38.png","/:bye":"39.png","/:wipe":"40.png","/:dig":"41.png","/:handclap":"42.png","/:&amp;-(":"43.png","/:B-)":"44.png","/:&lt;@":"45.png","/:@&gt;":"46.png","/::-O":"47.png","/:&gt;-|":"48.png","/:P-(":"49.png","/::’|":"50.png","/:X-)":"51.png","/::*":"52.png","/:@x":"53.png","/:8*":"54.png","/:pd":"55.png","/:&lt;W&gt;":"56.png","/:beer":"57.png","/:basketb":"58.png","/:oo":"59.png","/:coffee":"60.png","/:eat":"61.png","/:pig":"62.png","/:rose":"63.png","/:fade":"64.png","/:showlove":"65.png","/:heart":"66.png","/:break":"67.png","/:cake":"68.png","/:li":"69.png"
};
var imgreg = /(\/::\))|(\/::~)|(\/::B)|(\/::\|)|(\/:8-\))|(\/::&lt;)|(\/::\$)|(\/::X)|(\/::Z)|(\/::’\()|(\/::-\|)|(\/::@)|(\/::P)|(\/::D)|(\/::O)|(\/::\()|(\/::\+)|(\/:–b)|(\/::Q)|(\/::T)|(\/:,@P)|(\/:,@-D)|(\/::d)|(\/:,@o)|(\/::g)|(\/:\|-\))|(\/::\!)|(\/::L)|(\/::&gt;)|(\/::,@)|(\/:,@f)|(\/::-S)|(\/:\?)|(\/:,@x)|(\/:,@@)|(\/::8)|(\/:,@\!)|(\/:\!\!\!)|(\/:xx)|(\/:bye)|(\/:wipe)|(\/:dig)|(\/:handclap)|(\/:&amp;-\()|(\/:B-\))|(\/:&lt;@)|(\/:@&gt;)|(\/::-O)|(\/:&gt;-\|)|(\/:P-\()|(\/::’\|)|(\/:X-\))|(\/::\*)|(\/:@x)|(\/:8\*)|(\/:pd)|(\/:&lt;W&gt;)|(\/:beer)|(\/:basketb)|(\/:oo)|(\/:coffee)|(\/:eat)|(\/:pig)|(\/:rose)|(\/:fade)|(\/:showlove)|(\/:heart)|(\/:break)|(\/:cake)|(\/:li)/ig;

//调用这个函数对字符串进行处理即可自动匹配到imgs目录下的表情
function getExpressionImg(text){
    var maparr = text.match(imgreg);
    if (maparr){
        var len = maparr.length;
        var i = 0;
        while(i < len){
            if (maparr[i] != "" || maparr[i]){
                var regstr = maparr[i];
                regstr = regstr.replace("/", "\\/");
                regstr = regstr.replace(")", "\\)");
                regstr = regstr.replace("(", "\\(");
                var reg = new RegExp(regstr);
                console.log(regstr);
                text = text.replace(reg, "<img src='/imgs/"+imgmaps[maparr[i]]+"'/>");
            }

            i++;
        }
    }
    return text;
}