//全局配置
var isTokenAlert = false;
function loginCallUri(returnuri){
	if(!returnuri){
		returnuri = encodeURIComponent(window.location.href);
	}
    return '/login.html?returnuri='+returnuri;
}
$.ajaxSetup({
	dataType: "json",
	cache: false,
	headers: { // 默认添加请求头  
        "Authorization": window.storage.get('login.access_token',false) 
    },
    success: function(r){
    },
    complete:function(result){
    	var response = result.responseJSON;
    	if(response && response.hasOwnProperty("errcode") && response.errcode == 10003){
    		isTokenAlert = true;
    		var nowTime = new Date().getTime();
    	    var expire = parseInt(window.storage.get('login.expire', false),10)
    	    if (nowTime < expire-1000) {
    	        alert('您的账号在其它地方登录，如非您本人操作，请尽快修改密码！')
    	        parent.window.alert = function () { return false };
    	    }
    	    parent.window.storage.clearLocalSpace("login");
    	    parent.location.href = loginCallUri();
    	}
    }
});

var login = {
	el: null,
	setup : function(oauth2) {
		for ( var ke in oauth2) {
			window.storage.set("login." + ke, oauth2[ke], false);
		}
		var expiresTime = oauth2["expires_in"];
		var now = new Date();
		var t = now.getTime() + expiresTime * 1000;
		var loginTime = new Date(t);
		window.storage.set('login.expire', loginTime.getTime(), false);
	},
	refreshCode : function(obj) {
		$(login.el).attr("src","/captcha.jpg?t=" + $.now());
		$("#captcha").val("");
	},
	action : function(username,password,captcha,fn) {
		if(username == '' || password == '' || captcha == '') {
			//均不能为空
			alert('账号、密码和验证码不能为空!');
			//改变验证码
			return false;
		}
		
		$.ajax({
			type : "GET",
			url : "/oauth2/encrypt",
			data : {
				encrypts : username + "," + encodeURIComponent(password),
				token : secret
			},
			dataType : "json",
			success : function(response) {
				var data = {};
				if (response.errcode == 0) {
					data.username = response.data[0];
					data.password = response.data[1];
					data.captcha = captcha;
					$.ajax({
						type : "POST",
						url : "/oauth2/token",
						data : data,
						dataType : "json",
						success : function(result) {
							if(typeof fn == 'function'){
								fn(response);
							}
							if (result.errcode == 0) {//登录成功
								login.setup(result.data);
								var returnuri = url("returnuri");
								if(!returnuri){
									returnuri = '/index.html';
								}
								parent.location.href = returnuri;
							} else {
								alert(result.errmsg);
								login.refreshCode();
							}
						}
					});
				} else {
					if (response.errcode == 10015) {
						setTimeout(function() {
							parent.location.reload();
						}, 1000);
						return;
					}
					login.refreshCode();
				}
			}
		});
	},
	isLogin: function(){
		var token = parent.window.storage.get('login.access_token',false)
	    var isLogin = true;
	    if(!token){
	    	isLogin = false;
	    }else{
	    	var nowTime = new Date().getTime();
	    	var expire = parseInt(parent.window.storage.get('login.expire', false),10)
	    	if (nowTime > expire) {
	    		isLogin = false;
	    		/*alert('您的账号在其它地方登录，如非您本人操作，请尽快修改密码！')
	    		window.alert = function () { return false };*/
	    	}
	    }
	    return isLogin;
	},
	visitor : function(){
		var _this = this;
		var v = {
			init: function(){
				if(!_this.isLogin()){
					var id = this.getID();
					if(!id){
						window.storage.set('visitor.id',Math.uuid(),true);
					}
				}
			},
			getID: function(){
				return window.storage.get('visitor.id',false,true);
			}
		};
		return v;
	}
};

login.visitor().init();
