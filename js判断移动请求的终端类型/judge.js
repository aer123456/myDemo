(function(win) {
    var Utils = function () {};
    var ua = window.navigator.userAgent.toLowerCase();

    Utils.isWeiXin = function () {
        return ua.match(/MicroMessenger/i) == 'micromessenger';
    };

    Utils.isIOS = function () {
        return ua.match(/iPhone|iPad|iPod/i);
    };

    Utils.isAndroid = function () {
        return ua.match(/Android/i);
    };

    var log = function (msg) {
        console && console.log(msg);
    };

    var encodeText = function (txt, flag) {
        var result = '';

        if (flag) {
            var specialMap = [
                ['[/wave]', '\\~'],
                ['[/underline]', '_'],
                ['[/dot]', '\\.'],
                ['[/sigh]', '\\!'],
                ['[/middleline]', '\\-'],
                ['[/star]', '\\*'],
                ['[/quot]', "'"],
                ['[/leftbracket]', '\\('],
                ['[/rightbracket]', '\\)']
            ];

            for (var i = 0; i < specialMap.length; i++) {
                var reg = new RegExp(specialMap[i][1]);
                result = text.replace(reg, specialMap[i][0]);
                text = result;
            }
        } else {
            result = txt;
        }
        return encodeURIComponent(result);
    };

    var query = {
        stringify: function(params) {
            var str = '1=1';
            for (var p in params) {
                str += '&' + p + '=' + encodeText(params[p]);
            }
            return str;
        }
    }

    win.Hybird = Hybird = (function () {
        if (Utils.isIOS()) {
            return {
                gotoHomePage: function () {
                    window.open('objc://gotoHomePage', '_blank');
                },
                gotoP2PProjectListPage: function () {
                    window.open('objc://gotoP2PProjectListPage', '_blank');
                },
                gotoP2PProjectDetailPage: function (projectId) {
                    var params = query.stringify({
                        projectId: projectId
                    });
                    window.open('objc://gotoP2PProjectDetailPage?' + params, '_blank');
                },
                gotoFundProjectListPage: function () {
                    window.open('objc://gotoFundProjectListPage', '_blank');
                },
                gotoFundProjectDetailPage: function (fundCode) {
                    var params = query.stringify({
                        fundCode: fundCode
                    });
                    window.open('objc://gotoFundProjectDetailPage?' + params, '_blank');
                },
                gotoPage: function (title, url) {
                    var params = query.stringify({
                        title: title,
                        url: url
                    });
                    window.open('objc://gotoActivityPage?' + params, '_blank');
                },
                gotoRegistPage: function() {
                    window.open('objc://gotoRegistPage', '_blank');
                },
                gotoLoginPage: function () {
                    window.open('objc://gotoLoginPage', '_blank');
                },
                openCmbc: function() {
                    window.open('objc://gotoOpenCmbcPage', '_blank');
                },
                openRecharge: function () {
                    window.open('objc://gotoRechargePage', '_blank');
                },
                captureAndShare: function(opts) {
                    var opts = opts || {
                            title: '截图并分享标题',
                            content: '截图并分享内容',
                            url: 'https://m.jimubox.com',
                            imageUrl: 'https://m.jimubox.com',
                            method: 'weixin',
                            shareType: 'image'
                        };

                    var params = query.stringify(opts);
                    window.open('objc://openShare?' + params, '_blank');
                },
                shareToWeixin: function(opts) {
                    var opts = opts || {
                            title: '截图并分享标题',
                            content: '截图并分享内容',
                            url: 'https://m.jimubox.com',
                            imageUrl: 'https://m.jimubox.com',
                            method: 'weixin',
                            shareType: 'text'
                        };

                    var params = query.stringify(opts);
                    window.open('objc://openShare?' + params, '_blank');
                }
            };
        } else if (Utils.isAndroid()) {
            return {
                gotoHomePage: function () {
                    try {
                        HostApp.gotoHomePage();
                    } catch (e) {
                        log(e.message);
                    }
                },
                gotoP2PProjectListPage: function () {
                    try {
                        HostApp.gotoP2PProjectListPage();
                    } catch (e) {
                        log(e.message);
                    }
                },
                gotoP2PProjectDetailPage: function (projectId) {
                    try {
                        HostApp.gotoP2PProjectDetailPage(projectId);
                    } catch (e) {
                        log(e.message);
                    }
                },
                gotoFundProjectListPage: function () {
                    try {
                        HostApp.gotoFundProjectListPage();
                    } catch (e) {
                        log(e.message);
                    }
                },
                gotoFundProjectDetailPage: function (fundcode, tano) {
                    try {
                        HostApp.gotoFundProjectDetailPage(fundcode + '', tano + '');
                    } catch (e) {
                        log(e.message);
                    }
                },
                gotoPage: function (title, url) {
                    try {
                        HostApp.openNewActivity(title, url);
                    } catch (e) {
                        log(e.message);
                    }
                },
                gotoRegistPage: function() {
                    HostApp.gotoRegistPage();
                },
                gotoLoginPage: function () {
                    try {
                        HostApp.gotoLoginPage();
                    } catch (e) {
                        log(e.message);
                    }
                },
                openCmbc: function() {
                    HostApp.openCmbc();
                },
                openRecharge: function () {
                    try {
                        HostApp.openRecharge();
                    } catch (e) {
                        log(e.message);
                    }
                },
                captureAndShare: function(opts) {
                    var opts = opts || {title: '截图并分享标题', content: '截图并分享内容', link: '', wechatOnly: true};
                    try {
                        HostApp.openShare(opts.title, opts.content, opts.link, opts.wechatOnly);
                    } catch (e) {
                        log(e.message);
                    }
                },
                shareToWeixin: function(opts) {
                    var opts = opts || {title: '截图并分享标题', content: '截图并分享内容', link: 'https://m.jimubox.com', wechatOnly: true};
                    try {
                        HostApp.openShare(opts.title, opts.content, opts.link, opts.link, opts.wechatOnly);
                    } catch (e) {
                        log(e.message);
                    }
                }
            };
        } else {
            // 未知设备
        }
    })();
})(window);