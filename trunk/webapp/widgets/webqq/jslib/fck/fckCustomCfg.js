FCKConfig.AutoDetectLanguage = false ; //是否自动检测语言，false表示不检测，最好是修改fckconfig.js，而不是放在这

FCKConfig.DefaultLanguage = 'zh-cn' ;//汉语

FCKConfig.ToolbarCanCollapse = false ;//不允许折叠工具栏

FCKConfig.FullPage = true ;//编辑代码时，显示的是整个HTML文件，而不是body的内容，可根据情况自定

//加上几种常用的字体
FCKConfig.FontNames	= '宋体;楷体_GB2312;黑体;隶书;Times New Roman;Arial';

//修改“回车”和”Shift+回车”的换行行为
FCKConfig.EnterMode = 'br' ;			// p | div | br
FCKConfig.ShiftEnterMode = 'p' ;	// p | div | br

//插件：多媒体
//FCKConfig.Plugins.Add( 'Media', 'en,zh,zh-cn' ) ;  //启用插件

//自定义ToolbarSet,去掉一些功能
FCKConfig.ToolbarSets["Custom"] = [
	['Source','DocProps','-'],
	['Cut','Copy','Paste','PasteText','PasteWord'],
	['Undo','Redo','-','Find','Replace'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink'],
	
	['Image','Flash','Table','Rule','SpecialChar','PageBreak'],
	'/',
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['FontFormat','FontName','FontSize'],
	['TextColor','BGColor']	
] ;

//注意此处的FCKConfig.BasePath的路径-->ie http://www.shyhao.com/fckeditor/eidtor

FCKConfig.LinkBrowser = false ;
FCKConfig.LinkBrowserURL=FCKConfig.BasePath+"filemanager/browser/default/browser.html?Connector="+encodeURIComponent( FCKConfig.BasePath + '/filemanager/connectors/jspconnector');

FCKConfig.ImageBrowser = false ;
FCKConfig.ImageBrowserURL=FCKConfig.BasePath+"filemanager/browser/default/browser.html?Type=Image&Connector="+encodeURIComponent( FCKConfig.BasePath + '/filemanager/connectors/jspconnector');

FCKConfig.FlashBrowser = false ;
FCKConfig.FlashBrowserURL=FCKConfig.BasePath+"filemanager/browser/default/browser.html?Type=Flash&Connector="+encodeURIComponent( FCKConfig.BasePath + '/filemanager/connectors/jspconnector');

FCKConfig.LinkUploadURL=FCKConfig.BasePath+'/filemanager/upload/simpleuploader?Type=File' ;
FCKConfig.FlashUploadURL=FCKConfig.BasePath+'/filemanager/upload/simpleuploader?Type=Flash' ;
FCKConfig.ImageUploadURL=FCKConfig.BasePath+'/filemanager/upload/simpleuploader?Type=Image' ;

//FCKConfig.MediaBrowser = false ;
//FCKConfig.ImageBrowserURL=FCKConfig.BasePath+"filemanager/browser/default/browser.html?Type=Media&Connector="+encodeURIComponent( FCKConfig.BasePath + '/filemanager/connectors/jspconnector');
//FCKConfig.MediaUpload = true ;
//FCKConfig.MediaUploadURL=FCKConfig.BasePath+'/filemanager/upload/simpleuploader?Type=Media' ;
//FCKConfig.MediaUploadAllowedExtensions = ".(swf|fla|flv|wmv|mpg|mpeg|avi|qt|rm|rmvb)$" ;  // empty for all
//FCKConfig.MediaUploadDeniedExtensions = "" ;       // empty for no one

//在编辑器域内可以使用Tab键 默认 FCKConfig.TabSpaces = 0
FCKConfig.TabSpaces = 1 ;
//测试
//alert('自定义文件加载完毕!');
