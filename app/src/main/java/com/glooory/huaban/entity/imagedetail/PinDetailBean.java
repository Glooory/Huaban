package com.glooory.huaban.entity.imagedetail;

import com.glooory.huaban.entity.user.UserBoardItemBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/11 0011 14:58.
 */
public class PinDetailBean {


    /**
     * pin_id : 832252280
     * user_id : 17202953
     * board_id : 22236960
     * file_id : 109407389
     * file : {"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"}
     * media_type : 0
     * source : behance.net
     * link : https://www.behance.net/gallery/40772623/Illusionary-Prisms
     * raw_text :
     * text_meta : {}
     * via : 831961636
     * via_user_id : 16745470
     * original : 831961636
     * created_at : 1472106083
     * like_count : 24
     * comment_count : 1
     * repin_count : 126
     * is_private : 0
     * orig_source : https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg
     * user : {"user_id":17202953,"username":"达令☞","urlname":"tdlpukwogz","created_at":1430961383,"avatar":{"id":111551732,"farm":"farm1","bucket":"hbimg","key":"ce9069ec38d0d5af73ee970ae388d9c30c3dc3f05d060-K1OfBk","type":"image/jpeg","width":"640","height":"640","frames":"1"},"extra":{"is_museuser":true}}
     * board : {"board_id":22236960,"user_id":17202953,"title":"model☞","description":"【绝代有佳人，幽居在空谷】","category_id":"apparel","seq":9,"pin_count":1628,"follow_count":1395,"like_count":47,"created_at":1431478225,"updated_at":1473572831,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"702438389"}},"pins":[{"pin_id":849672318,"user_id":17202953,"board_id":22236960,"file_id":114192253,"file":{"bucket":"hbimg","key":"7c45df7adda5b6fad516dc6a3ad98f8b9026530222921-meAuL2","type":"image/jpeg","height":"1050","width":"700","frames":"1"},"media_type":0,"source":"duitang.com","link":"http://www.duitang.com/blog/?id=632001781","raw_text":"","text_meta":{},"via":849496060,"via_user_id":17015112,"original":849496060,}
     * via_user : {"user_id":16745470,"username":"寄由","urlname":"skins","created_at":1420886472,"avatar":{"id":112588184,"farm":"farm1","bucket":"hbimg","key":"e9a93ab9d520cd5ec62bc8ebb269eab7355511c212d91-1ZBRLo","type":"image/jpeg","width":"618","height":"443","frames":"1"},"extra":null}
     * via_pin : {"pin_id":831961636,"user_id":16745470,"board_id":27352895,"file_id":109407389,"file":{"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40772623/Illusionary-Prisms","raw_text":"Illusionary Prisms : Photographers: Gabriel Isak, Roberto GaxiolaStylist: Sarahy CisnerosModel: Madison KannaMUAH: Danielle Fisk Assistant MUAH: Ashley Goodman ","text_meta":{},"via":2,"via_user_id":0,"original":null,"created_at":1472094533,"like_count":1,"comment_count":0,"repin_count":154,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg","board":{"board_id":27352895,"user_id":16745470,"title":"人像","description":"","category_id":"photography","seq":6,"pin_count":651,"follow_count":453,"like_count":0,"created_at":1449137300,"updated_at":1472782982,"deleting":0,"is_private":0,"extra":null}}
     * original_pin : {"pin_id":831961636,"user_id":16745470,"board_id":27352895,"file_id":109407389,"file":{"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40772623/Illusionary-Prisms","raw_text":"Illusionary Prisms : Photographers: Gabriel Isak, Roberto GaxiolaStylist: Sarahy CisnerosModel: Madison KannaMUAH: Danielle Fisk Assistant MUAH: Ashley Goodman ","text_meta":{},"via":2,"via_user_id":0,"original":null,"created_at":1472094533,"like_count":1,"comment_count":0,"repin_count":154,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg","board":{"board_id":27352895,"user_id":16745470,"title":"人像","description":"","category_id":"photography","seq":6,"pin_count":651,"follow_count":453,"like_count":0,"created_at":1449137300,"updated_at":1472782982,"deleting":0,"is_private":0,"extra":null}}
     * repins : [{"pin_id":850420597,"user_id":18560749,"board_id":30931703,"file_id":109407389,"file":{"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40772623/Illusionary-Prisms","raw_text":"","text_meta":{},"via":832252280,"via_user_id":17202953,"original":831961636,"created_at":1473551403,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg","user":{"user_id":18560749,"username":"我是小小小小寶","urlname":"pj6j5cuvgn","created_at":1459214798,"avatar":{"id":97715831,"farm":"farm1","bucket":"hbimg","key":"45437cc64e4b8c5632b371b984ae627cc6b9205552d-gMYA4q","type":"image/jpeg","height":"50","frames":"1","width":"50"},"extra":null},"board":{"board_id":30931703,"user_id":18560749,"title":"服装和造型设计","description":"","category_id":null,"seq":18,"pin_count":47,"follow_count":1,"like_count":0,"created_at":1469453549,"updated_at":1473552545,"deleting":0,"is_private":0,"extra":null,"pins":[{"pin_id":850423638,"user_id":18560749,"board_id":30931703,"file_id":66010122,"file":{"bucket":"hbimg","key":"abf58824b5685b4d3cce0ac119c062a87d7251893e630-vw4Yo3","type":"image/jpeg","height":801,"width":600,"frames":1},"media_type":0,"source":"shenchinlun.com","link":"http://shenchinlun.com/News.Detail.asp?ID=283","raw_text":"Nishimura Meibutsu 2014 A/W/shenchinlun :","text_meta":{},"via":434286783,"via_user_id":14464765,"original":355047759,"created_at":1473552545,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":"http://shenchinlun.com/upload/web/56382094.jpg"},{"pin_id":850423552,"user_id":18560749,"board_id":30931703,"file_id":37836792,"file":{"bucket":"hbimg","key":"84d9fd1d80978e9c16e020d64b493b0fd4e520061f0c7-kzW5Wt","type":"image/jpeg","height":671,"width":510,"frames":1},"media_type":0,"source":"pinterest.com","link":"http://www.pinterest.com/pin/490962796849154066/","raw_text":"OLYMPIC PAPER MAGIC BY SHONA HEATH","text_meta":{},"via":137211778,"via_user_id":6715297,"original":137211778,"created_at":1473552516,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null}]
     * comments : [{"comment_id":2032566,"pin_id":832252280,"user_id":17191514,"raw_text":"好喜欢","text_meta":{},"status":1,"created_at":1473044455,"user":{"user_id":17191514,"username":"吉吉黄a","urlname":"d56rxp0zm7b","created_at":1430907509,"avatar":{"id":108633138,"farm":"farm1","bucket":"hbimg","key":"ca2819bb6b5bc042a035e5a2b71ca71f5c6981d111b2a-yaWO0l","type":"image/jpeg","height":"748","width":"748","frames":"1"},"extra":null}}]
     * likes : [{"user_id":10676744,"username":"Arne","urlname":"arne369963","created_at":1384579559,"avatar":{"id":32507132,"farm":"farm1","bucket":"hbimg","key":"e60471518be7614d3e79149ec54b16122e04d299a5a-rJ0G7D","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null,"liked_at":1472962442},{"user_id":17979778,"username":"siror","urlname":"itvfqmugkn","created_at":1444972731,"avatar":{"id":83709963,"farm":"farm1","bucket":"hbimg","key":"c80a2d4d8688a9cc6955f3eba100aa2c29f126ce682-XRaut5","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null,"liked_at":1472965085}]
     * hide_origin : false
     * prev : {"pin_id":832252045,"user_id":17202953,"board_id":22236960,"file_id":112653973,"file":{"bucket":"hbimg","key":"5ce65fff79222b3dcb7916f9749fe17e7f0aac413ae4f-7f818P","type":"image/jpeg","height":"800","width":"533","frames":"1"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40772623/Illusionary-Prisms","raw_text":"","text_meta":{},"via":831965855,"via_user_id":16745470,"original":831965855,"created_at":1472106077,"like_count":12,"comment_count":0,"repin_count":69,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/disp/0feee640772623.578cf82ac2810.jpg"}
     * next : {"pin_id":840911023,"user_id":17202953,"board_id":22236960,"file_id":110551692,"file":{"bucket":"hbimg","key":"9487c6e15a2b73bfcf9457a2e910b567d4cd63c39d9bd-Gngzw2","type":"image/jpeg","height":"1799","width":"1200","frames":"1"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40682365/ADIDAS-Gazelle-collage-campaign","raw_text":"","text_meta":{},"via":840577237,"via_user_id":12078094,"original":811366843,"created_at":1472779056,"like_count":11,"comment_count":1,"repin_count":48,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/7b649e40682365.5788a2fb38c26.jpg"}
     * siblings : [{"pin_id":850629615,"user_id":8937961,"board_id":31734075,"file_id":59748860,"file":{"id":59748860,"farm":"farm1","bucket":"hbimg","key":"9fa941210887c13416be03753de4ad28bb9477ed29c4c-S5dU0j","type":"image/jpeg","width":600,"height":921,"frames":1},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/19365883/Zombie-Island-game","raw_text":"&quot;Zombie Island&quot; game : Concept, modeling, texturing, rendering and importing objects for &quot;Zombie Island&quot; game","text_meta":{},"via":564044035,"via_user_id":16611417,"original":248544209,"created_at":1473576979,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null,"user":{"user_id":8937961,"username":"HsuChihmo","urlname":"q949tly5rzq","created_at":1374756297,"avatar":{"id":25370401,"farm":"farm1","bucket":"hbimg","key":"e3cc79ba26a95094b152321029c7ff2e753a989f8e2-fcVwSL","type":"image/jpeg","width":50,"height":50,"frames":1},"extra":null},"board":{"board_id":31734075,"user_id":8937961,"title":"2","description":"","category_id":"illustration","seq":2,"pin_count":94,"follow_count":0,"like_count":0,"created_at":1473576309,"updated_at":1473576979,"deleting":0,"is_private":0,"extra":null},"via_user":{"user_id":16611417,"username":"古三儿","urlname":"kxrjjmukar","created_at":1417327141,"avatar":{"id":21122412,"farm":"farm1","bucket":"hbimg","key":"66b95a3bfbfede1894941942996a3c3075122c6519a-fJ3ci9","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null}},{"pin_id":850629355,"user_id":17628424,"board_id":24751992,"file_id":114341079,"file":{"id":114341079,"farm":"farm1","bucket":"hbimg","key":"5d5b73b267432c0b56cc0a6e86ed9d83b5070b71b329-5i6Rsz","type":"image/jpeg","height":"1762","width":"660","frames":"1","colors":[{"color":16734520,"ratio":0.66},{"color":16777215,"ratio":0.3}],"theme":"ff5938"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/30453085/Arciform-Free-Typeface","raw_text":"Arciform","text_meta":{},"via":2,"via_user_id":0,"original":null,"created_at":1473576955,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":"http://blog.spoongraphics.co.uk/wp-content/uploads/2015/free-fonts/7.jpg","user":{"user_id":17628424,"username":"王原子","urlname":"qq917956833","created_at":1438267959,"avatar":{"id":78521182,"farm":"farm1","bucket":"hbimg","key":"5f75149ff0410f3e3bbc59e78e6d020214e1384cec7-I891UO","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null},"board":{"board_id":24751992,"user_id":17628424,"title":"启发 · Composition &amp; Skill","description":"","category_id":"design","seq":1,"pin_count":43,"follow_count":1,"like_count":0,"created_at":1438271903,"updated_at":1473576959,"deleting":0,"is_private":0,"extra":null}},{"pin_id":850629481,"user_id":16718870,"board_id":27814977,"file_id":5071238,"file":{"id":5071238,"farm":"farm1","bucket":"hbimg","key":"17931552882a1894e870be1ee3e0540eb3bd78a147013-TasGzv","type":"image/jpeg","width":350,"height":479,"frames":1,"colors":[{"color":15718086,"ratio":0.24},{"color":12632256,"ratio":0.14},{"color":11711154,"ratio":0.13},{"color":15198166,"ratio":0.11}],"theme":"efd6c6"},"media_type":0,"source":"behance.net","link":"http://www.behance.net/gallery/bakery/3962429","raw_text":"bakery on the Behance Network","text_meta":{},"via":207332172,"via_user_id":13192896,"original":14292780,"created_at":1473576968,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":"http://behance.vo.llnwd.net/profiles16/967399/projects/3962429/ec315e510c95b1a68398963f2205c02f.jpg","user":{"user_id":16718870,"username":"绵宝宝","urlname":"ybsunxlty1","created_at":1420215615,"avatar":{"id":90867962,"farm":"farm1","bucket":"hbimg","key":"a5529d15d7de9946be11e044333151e0e48ad68b1cc53-p2wbaM","type":"image/jpeg","height":"700","frames":"1","width":"700"},"extra":null},"board":{"board_id":27814977,"user_id":16718870,"title":"Q宝宝","description":"","category_id":"anime","seq":2,"pin_count":64,"follow_count":2,"like_count":0,"created_at":1452403903,"updated_at":1473576975,"deleting":0,"is_private":0,"extra":null},"via_user":{"user_id":13192896,"username":"书了个呆子","urlname":"vj8vgjfoy0","created_at":1398672756,"avatar":{"id":42150551,"farm":"farm1","bucket":"hbimg","key":"b0c3e7588d5ccaabd5a27c1cca9610b026658dda1b02-IEEQO3","type":"image/jpeg","width":180,"height":180,"frames":1},"extra":null}},{"pin_id":850629570,"user_id":16718870,"board_id":27814977,"file_id":5071219,"file":{"id":5071219,"farm":"farm1","bucket":"hbimg","key":"d782b6a99abd6a3b493d3356d544e2118ca01dbd42864-LAFrlF","type":"image/jpeg","width":350,"height":479,"frames":1,"colors":[{"color":13408614,"ratio":0.21},{"color":15718086,"ratio":0.2},{"color":13408665,"ratio":0.17},{"color":15198166,"ratio":0.09}],"theme":"cc9966"},"media_type":0,"source":"behance.net","link":"http://www.behance.net/gallery/bakery/3962429","raw_text":"bakery on the Behance Network","text_meta":{},"via":207332322,"via_user_id":13192896,"original":14292746,"created_at":1473576975,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":"http://behance.vo.llnwd.net/profiles16/967399/projects/3962429/383f75dd5443d59481908c2bb6f61717.jpg","user":{"user_id":16718870,"username":"绵宝宝","urlname":"ybsunxlty1","created_at":1420215615,"avatar":{"id":90867962,"farm":"farm1","bucket":"hbimg","key":"a5529d15d7de9946be11e044333151e0e48ad68b1cc53-p2wbaM","type":"image/jpeg","height":"700","frames":"1","width":"700"},"extra":null},"board":{"board_id":27814977,"user_id":16718870,"title":"Q宝宝","description":"","category_id":"anime","seq":2,"pin_count":64,"follow_count":2,"like_count":0,"created_at":1452403903,"updated_at":1473576975,"deleting":0,"is_private":0,"extra":null},"via_user":{"user_id":13192896,"username":"书了个呆子","urlname":"vj8vgjfoy0","created_at":1398672756,"avatar":{"id":42150551,"farm":"farm1","bucket":"hbimg","key":"b0c3e7588d5ccaabd5a27c1cca9610b026658dda1b02-IEEQO3","type":"image/jpeg","width":180,"height":180,"frames":1},"extra":null}},{"pin_id":850629413,"user_id":16718870,"board_id":27814977,"file_id":5071216,"file":{"id":5071216,"farm":"farm1","bucket":"hbimg","key":"6a846a414796d67b1144aefe867ee7da39fc75ce470de-N7s1Z9","type":"image/jpeg","width":350,"height":465,"frames":1,"colors":[{"color":13408665,"ratio":0.21},{"color":15718086,"ratio":0.19},{"color":15198166,"ratio":0.15},{"color":13408614,"ratio":0.14}],"theme":"cc9999"},"media_type":0,"source":"behance.net","link":"http://www.behance.net/gallery/bakery/3962429","raw_text":"bakery on the Behance Network","text_meta":{},"via":207332292,"via_user_id":13192896,"original":14292717,"created_at":1473576961,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":"http://behance.vo.llnwd.net/profiles16/967399/projects/3962429/a1316147c9fa5d556e3746feef465e08.jpg","user":{"user_id":16718870,"username":"绵宝宝","urlname":"ybsunxlty1","created_at":1420215615,"avatar":{"id":90867962,"farm":"farm1","bucket":"hbimg","key":"a5529d15d7de9946be11e044333151e0e48ad68b1cc53-p2wbaM","type":"image/jpeg","height":"700","frames":"1","width":"700"},"extra":null},"board":{"board_id":27814977,"user_id":16718870,"title":"Q宝宝","description":"","category_id":"anime","seq":2,"pin_count":64,"follow_count":2,"like_count":0,"created_at":1452403903,"updated_at":1473576975,"deleting":0,"is_private":0,"extra":null},"via_user":{"user_id":13192896,"username":"书了个呆子","urlname":"vj8vgjfoy0","created_at":1398672756,"avatar":{"id":42150551,"farm":"farm1","bucket":"hbimg","key":"b0c3e7588d5ccaabd5a27c1cca9610b026658dda1b02-IEEQO3","type":"image/jpeg","width":180,"height":180,"frames":1},"extra":null}},{"pin_id":850629524,"user_id":16935544,"board_id":31674800,"file_id":56545944,"file":{"id":56545944,"farm":"farm1","bucket":"hbimg","key":"6fde6a74ec5ae185f4505b080f82f0b923926397b8f0a-xv3hnA","type":"image/jpeg","width":1240,"height":1754,"frames":1},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/6507793/Bomonti-Unfiltered","raw_text":"Bomonti Unfiltered : Print Ad for Bomonti Unfiltered","text_meta":{},"via":239042111,"via_user_id":1103419,"original":239042111,"created_at":1473576971,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null,"user":{"user_id":16935544,"username":"miki2015","urlname":"whorfeeecz","created_at":1426210949,"avatar":{"id":8385295,"farm":"farm1","bucket":"hbimg","key":"eaa8d01bd5cef3ee366590da8aadfb6292c06cc03e29-RBm1Da","type":"image/jpeg","width":200,"height":200,"frames":1},"extra":{"is_museuser":true}},"board":{"board_id":31674800,"user_id":16935544,"title":"酒类参考","description":"","category_id":null,"seq":69,"pin_count":41,"follow_count":34,"like_count":0,"created_at":1473248326,"updated_at":1473576971,"deleting":0,"is_private":0,"extra":null},"via_user":{"user_id":1103419,"username":"laibach0812","urlname":"i3lyzfhcr5","created_at":1352710816,"avatar":{"id":28992810,"farm":"farm1","bucket":"hbimg","key":"de80d1b62a71662f7b3a07240d8e7a4ac1026f69844fc-hrnEVq","type":"image/jpeg","width":1920,"height":1200,"frames":1},"extra":null}},{"pin_id":850629414,"user_id":8937961,"board_id":31734075,"file_id":38926658,"file":{"id":38926658,"farm":"farm1","bucket":"hbimg","key":"e7dc9ecda6733103ace02e23fa98de09a74e42852ecce-VEhPb8","type":"image/jpeg","width":600,"height":453,"frames":1,"theme":"DAB199"},"media_type":0,"source":"behance.net","link":"http://www.behance.net/gallery/Kitchen-Scramble_-Background-and-digital-paint/14378251","raw_text":"Kitchen Scramble_ Background and digital paint on Behance","text_meta":{},"via":535064167,"via_user_id":8391083,"original":142521038,"created_at":1473576961,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null,"user":{"user_id":8937961,"username":"HsuChihmo","urlname":"q949tly5rzq","created_at":1374756297,"avatar":{"id":25370401,"farm":"farm1","bucket":"hbimg","key":"e3cc79ba26a95094b152321029c7ff2e753a989f8e2-fcVwSL","type":"image/jpeg","width":50,"height":50,"frames":1},"extra":null},"board":{"board_id":31734075,"user_id":8937961,"title":"2","description":"","category_id":"illustration","seq":2,"pin_count":94,"follow_count":0,"like_count":0,"created_at":1473576309,"updated_at":1473576979,"deleting":0,"is_private":0,"extra":null},"via_user":{"user_id":8391083,"username":"NNNAA娜","urlname":"zus6zzj9f0","created_at":1372214797,"avatar":{"id":85198648,"farm":"farm1","bucket":"hbimg","key":"79f7385af2dcdfe38996fd30e00e66fc139654cbae45c-ikP7B8","type":"image/jpeg","width":1621,"height":1866,"frames":1},"extra":null}},{"pin_id":850629345,"user_id":17628424,"board_id":24751992,"file_id":114341077,"file":{"id":114341077,"farm":"farm1","bucket":"hbimg","key":"d821fa4945c3ba01d6fd1fb91a2765da33365c4c1bd7e-Sb75o6","type":"image/jpeg","height":"1806","frames":"1","width":"660","colors":[{"color":921102,"ratio":0.19},{"color":460551,"ratio":0.19},{"color":12895428,"ratio":0.17},{"color":1381653,"ratio":0.09}],"theme":"0e0e0e"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/29089719/Handletter-Free-Font","raw_text":"Handletter","text_meta":{},"via":2,"via_user_id":0,"original":null,"created_at":1473576954,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":"http://blog.spoongraphics.co.uk/wp-content/uploads/2015/free-fonts/17.jpg","user":{"user_id":17628424,"username":"王原子","urlname":"qq917956833","created_at":1438267959,"avatar":{"id":78521182,"farm":"farm1","bucket":"hbimg","key":"5f75149ff0410f3e3bbc59e78e6d020214e1384cec7-I891UO","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null},"board":{"board_id":24751992,"user_id":17628424,"title":"启发 · Composition &amp; Skill","description":"","category_id":"design","seq":1,"pin_count":43,"follow_count":1,"like_count":0,"created_at":1438271903,"updated_at":1473576959,"deleting":0,"is_private":0,"extra":null}}]
     * liked : true
     * breadcrumb : null
     * category : apparel
     * recommend : [{"pin_id":134804063,"user_id":6822640,"board_id":13096554,"file_id":37264549,"file":{"id":37264549,"farm":"farm1","bucket":"hbimg","key":"3e82a6d83e261ad583c88c44cff5643780cd8d728476-1KmLLv","type":"image/jpeg","width":440,"height":440,"frames":1},"media_type":0,"source":"weibo.com","link":"http://weibo.com/mygroups?gid=3644579813533745&wvr=5&leftnav=1","raw_text":"主题搭配丨星星元素","text_meta":null,"via":134760665,"via_user_id":7292844,"original":134451615,"created_at":1389941413,"like_count":5,"comment_count":0,"repin_count":14,"is_private":0,"orig_source":null,"user":{"user_id":6822640,"username":"靠-中不中","urlname":"qgqybkpttd","created_at":1363256696,"avatar":{"id":27196716,"farm":"farm1","bucket":"hbimg","key":"f4903d639d0d2178b13da42517abeb1de37d479b8e60-OpAYNj","type":"image/jpeg","width":799,"height":800,"frames":1},"extra":{"is_museuser":true}},"board":{"board_id":13096554,"user_id":6822640,"title":"A街拍","description":"","category_id":"apparel","seq":4,"pin_count":417,"follow_count":450,"like_count":3,"created_at":1375935822,"updated_at":1450764792,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"135323412"}}}},{"pin_id":134921750,"user_id":8530504,"board_id":13561958,"file_id":37285365,"file":{"id":37285365,"farm":"farm1","bucket":"hbimg","key":"894f1ef0a0ffa3e2fa8e7465e27d05515d7f54bc2d107-H8WTc3","type":"image/jpeg","width":800,"height":1199,"frames":1},"media_type":0,"source":"fuckingyoung.es","link":"http://fuckingyoung.es/prada-fallwinter-2014/","raw_text":"Fucking Young! » Prada Fall/Winter 2014","text_meta":null,"via":134566554,"via_user_id":11495187,"original":134566554,"created_at":1389972375,"like_count":6,"comment_count":0,"repin_count":24,"is_private":0,"orig_source":null,"user":{"user_id":8530504,"username":"ForgΕ","urlname":"d1561230004","created_at":1372814675,"avatar":{"id":21581065,"farm":"farm1","bucket":"hbimg","key":"486fb0915d270035e450c4233af4bdcb7590a4f9c6e-u3NLY3","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null},"board":{"board_id":13561958,"user_id":8530504,"title":"服装设计","description":"","category_id":"apparel","seq":3,"pin_count":193,"follow_count":50,"like_count":0,"created_at":1380121155,"updated_at":1395930059,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"121511762"}}}},{"pin_id":142719222,"user_id":59311,"board_id":3226711,"file_id":5808234,"file":{"id":5808234,"farm":"farm1","bucket":"hbimg","key":"d0ccd49d016fb21cb05bcd8856ce7d395b6a223d18ac0-n83OjR","type":"image/jpeg","width":400,"height":600,"frames":1,"colors":[{"color":0,"ratio":0.19},{"color":7829367,"ratio":0.15}],"theme":"000000"},"media_type":0,"source":"douban.com","link":"http://www.douban.com/photos/photo/1275643057/#image","raw_text":"衬衫命的相册-　　　　　　　　\u203b","text_meta":null,"via":18845467,"via_user_id":596419,"original":18845467,"created_at":1393385736,"like_count":14,"comment_count":0,"repin_count":43,"is_private":0,"orig_source":"http://img3.douban.com/view/photo/photo/public/p1275643057.jpg","user":{"user_id":59311,"username":"sweetsq","urlname":"sweetsqsq","created_at":1327910828,"avatar":{"id":458471,"farm":"farm1","bucket":"hbimg","key":"3dcbb322a099a08d2eb3621e39cb8eec5aef067f2824-JrauRF","type":"image/jpeg","width":180,"height":180},"extra":null},"board":{"board_id":3226711,"user_id":59311,"title":"乖衣衣","description":"","category_id":"apparel","seq":6,"pin_count":756,"follow_count":80,"like_count":3,"created_at":1355131306,"updated_at":1438245661,"deleting":0,"is_private":0,"extra":null}},{"pin_id":161694189,"user_id":143400,"board_id":2233441,"file_id":42550901,"file":{"id":42550901,"farm":"farm1","bucket":"hbimg","key":"c8beca2dd5adcd451c1046111665c91dad582d80192d9-fTs8aw","type":"image/jpeg","width":450,"height":675,"frames":1},"media_type":0,"source":"style.com","link":"http://www.style.com/fashionshows/complete/slideshow/2013PF-MMARGIEL/#1","raw_text":"Maison Martin Margiela | Pre-Fall 2013 Collection | Style.com","text_meta":{},"via":2,"via_user_id":0,"original":null,"created_at":1399346488,"like_count":8,"comment_count":1,"repin_count":43,"is_private":0,"orig_source":null,"user":{"user_id":143400,"username":"圆脑袋的洋子","urlname":"kittylee1122","created_at":1332221061,"avatar":{"id":1195002,"farm":"farm1","bucket":"hbimg","key":"c64203ac914f91c4670565bc1b04c13af66871f04db7-c1EhFN","type":"image/jpeg","width":180,"height":180,"frames":1},"extra":null},"board":{"board_id":2233441,"user_id":143400,"title":"about fashion","description":"","category_id":"apparel","seq":3,"pin_count":253,"follow_count":168,"like_count":0,"created_at":1347722779,"updated_at":1399346488,"deleting":0,"is_private":0,"extra":null}},{"pin_id":176564582,"user_id":282640,"board_id":736074,"file_id":41538306,"file":{"id":41538306,"farm":"farm1","bucket":"hbimg","key":"82003e54c103f1a4a895b446fe722ec171ea34714356e-i0vOCp","type":"image/jpeg","width":640,"height":879,"frames":1},"media_type":0,"source":"fubiz.net","link":"http://www.fubiz.net/2014/04/15/women-on-the-verge-fashion-series/","raw_text":"Women On The Verge Fashion Series \u2013 Fubiz™","text_meta":{},"via":156072452,"via_user_id":6300285,"original":156072452,"created_at":1402896582,"like_count":7,"comment_count":0,"repin_count":19,"is_private":0,"orig_source":null,"user":{"user_id":282640,"username":"大格格格","urlname":"weinasidhc","created_at":1336278397,"avatar":{"id":2242459,"farm":"farm1","bucket":"hbimg","key":"ce94d6eb47236172c701e7f0522494955dab3928f9a-EC2wvj","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null},"board":{"board_id":736074,"user_id":282640,"title":"我想要每天美美的","description":"","category_id":"apparel","seq":5,"pin_count":476,"follow_count":173,"like_count":2,"created_at":1336300608,"updated_at":1407209755,"deleting":0,"is_private":0,"extra":null}},{"pin_id":207305868,"user_id":330301,"board_id":2624193,"file_id":13265878,"file":{"id":13265878,"farm":"farm1","bucket":"hbimg","key":"4c6e1797093dea33a64785cc8d7daac2c3fb34767ae5-IiHJDN","type":"image/jpeg","width":296,"height":477,"frames":1},"media_type":0,"source":"tianmeixi.net","link":"http://www.tianmeixi.net/note.php?action=index&sid=1189","raw_text":"这个气质呀~","text_meta":{},"via":207153185,"via_user_id":10900475,"original":54593822,"created_at":1407889483,"like_count":10,"comment_count":1,"repin_count":32,"is_private":0,"orig_source":null,"user":{"user_id":330301,"username":"honghui","urlname":"sxjc1234","created_at":1338277654,"avatar":{"id":58595615,"farm":"farm1","bucket":"hbimg","key":"fd65e835dfa8d0c90ea0125d04c2e986a1dfe9433db4-z1FrKM","type":"image/jpeg","width":200,"height":295,"frames":1},"extra":null},"board":{"board_id":2624193,"user_id":330301,"title":"喜欢","description":"","category_id":"apparel","seq":58,"pin_count":609,"follow_count":3082,"like_count":4,"created_at":1350019602,"updated_at":1458988275,"deleting":0,"is_private":0,"extra":null}},{"pin_id":227435030,"user_id":14771009,"board_id":17031598,"file_id":36266134,"file":{"id":36266134,"farm":"farm1","bucket":"hbimg","key":"54b8272f3cc7fb307a8f53b6ee363157dec0bfb01843a-50uQC5","type":"image/jpeg","width":600,"height":800,"frames":1},"media_type":0,"source":"edu.gmw.cn","link":"http://edu.gmw.cn/2014-01/03/content_10002021_34.htm","raw_text":"美女博士实验照走红 征男友回家过年","text_meta":{},"via":131178543,"via_user_id":1013475,"original":131178543,"created_at":1409997908,"like_count":1,"comment_count":1,"repin_count":8,"is_private":0,"orig_source":null,"user":{"user_id":14771009,"username":"寒雨飞鸥","urlname":"l9ylxzbpcz","created_at":1407306939,"avatar":{"id":48726290,"farm":"farm1","bucket":"hbimg","key":"3768010b07a574031e51b0ed076b77ae8e9ab1592047-Nwz94A","type":"image/png","width":100,"height":100,"frames":1},"extra":null},"board":{"board_id":17031598,"user_id":14771009,"title":"冬天搭配","description":"","category_id":"apparel","seq":12,"pin_count":41,"follow_count":23,"like_count":0,"created_at":1408205792,"updated_at":1451841353,"deleting":0,"is_private":0,"extra":null}},{"pin_id":237020037,"user_id":13853875,"board_id":16070685,"file_id":1983477,"file":{"id":1983477,"farm":"farm1","bucket":"hbimg","key":"b4cec5baa69ad012fd48c7445eecb4b381985ef613662-VedvWm","type":"image/jpeg","width":405,"height":600,"frames":1,"colors":[{"color":1447446,"ratio":0.47}],"theme":"161616"},"media_type":0,"source":"douban.com","link":"http://www.douban.com/photos/photo/1503215869/","raw_text":"","text_meta":{},"via":4501503,"via_user_id":69192,"original":4501503,"created_at":1410690585,"like_count":12,"comment_count":0,"repin_count":45,"is_private":0,"orig_source":null,"user":{"user_id":13853875,"username":"你永远都不知道","urlname":"poodks0qjp","created_at":1402209888,"avatar":{"id":73655349,"farm":"farm1","bucket":"hbimg","key":"7b20a012a3607ae9a0f28d8ac3622bb7dec26f8ec950-CEnObd","type":"image/jpeg","width":440,"height":440,"frames":1},"extra":null},"board":{"board_id":16070685,"user_id":13853875,"title":"#style look","description":"  我是欧美、英伦控。。。。","category_id":"apparel","seq":2,"pin_count":248,"follow_count":137,"like_count":4,"created_at":1402210620,"updated_at":1470891665,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"553181431"}}}},{"pin_id":245169255,"user_id":291599,"board_id":777536,"file_id":58583844,"file":{"id":58583844,"farm":"farm1","bucket":"hbimg","key":"718d6be6ed8c0e1a1030d178032d4b85dd1195f811163-EWBK1X","type":"image/jpeg","width":434,"height":637,"frames":1},"media_type":0,"source":"weibo.com","link":"http://weibo.com/2606938454/BmYSBchc3","raw_text":"Colet 2015 Wedding Dresses","text_meta":{},"via":7,"via_user_id":0,"original":null,"created_at":1411712539,"like_count":10,"comment_count":0,"repin_count":27,"is_private":0,"orig_source":null,"user":{"user_id":291599,"username":"bokebi911","urlname":"yannagu","created_at":1336660146,"avatar":{"id":2347436,"farm":"farm1","bucket":"hbimg","key":"b1bb854727717d88e8da4ff06bcc08136f73bfb84939-z4Wn1I","type":"image/jpeg","width":180,"height":180,"frames":1},"extra":null},"board":{"board_id":777536,"user_id":291599,"title":"华服","description":"超爱的礼服设计","category_id":"apparel","seq":6,"pin_count":78,"follow_count":32,"like_count":0,"created_at":1336746694,"updated_at":1466672060,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"170533926"}}}},{"pin_id":257226155,"user_id":961050,"board_id":3308635,"file_id":60748049,"file":{"id":60748049,"farm":"farm1","bucket":"hbimg","key":"abe9f7039be1c3041a288889f5cb2aad638afaef1d7bc-kVDdqy","type":"image/jpeg","width":400,"height":599,"frames":1},"media_type":0,"source":"52souluo.com","link":"http://www.52souluo.com/107404.html","raw_text":"simple03","text_meta":{},"via":255779958,"via_user_id":279562,"original":255779958,"created_at":1413510801,"like_count":4,"comment_count":0,"repin_count":25,"is_private":0,"orig_source":"http://www.52souluo.com/wp-content/uploads/2014/10/simple03.jpg","user":{"user_id":961050,"username":"じづ双魂のん","urlname":"liyao248819941030","created_at":1349937501,"avatar":{"id":39094518,"farm":"farm1","bucket":"hbimg","key":"ed323e2ec43ed4b830e57d4cfd6ed66f048422de16327-XPbQGE","type":"image/jpeg","width":500,"height":500,"frames":1},"extra":{"is_museuser":true}},"board":{"board_id":3308635,"user_id":961050,"title":"街拍，服装搭配","description":"","category_id":"apparel","seq":32,"pin_count":1536,"follow_count":11642,"like_count":11,"created_at":1356839753,"updated_at":1473214353,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"385899999"}}}},{"pin_id":257923350,"user_id":13147007,"board_id":15710491,"file_id":60832888,"file":{"id":60832888,"farm":"farm1","bucket":"hbimg","key":"651886ddc60a9d82f74681960959297ff0b729371567a-tLcWhy","type":"image/jpeg","width":667,"height":1000,"frames":1},"media_type":0,"source":"pinterest.com","link":"http://www.pinterest.com/pin/235594624232606877/","raw_text":"Balmain Spring 2015","text_meta":{},"via":256647346,"via_user_id":10239656,"original":256467949,"created_at":1413560728,"like_count":4,"comment_count":0,"repin_count":24,"is_private":0,"orig_source":null,"user":{"user_id":13147007,"username":"脸盲症晚期患者","urlname":"b-umblebee","created_at":1398412532,"avatar":{"id":41984020,"farm":"farm1","bucket":"hbimg","key":"a753447dc677c563b0516b534c3f7b7daf63338fb68-qoFnjJ","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null},"board":{"board_id":15710491,"user_id":13147007,"title":"服装","description":"","category_id":"apparel","seq":9,"pin_count":167,"follow_count":62,"like_count":3,"created_at":1398419100,"updated_at":1431412427,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"166975178"}}}},{"pin_id":258862359,"user_id":173443,"board_id":491156,"file_id":61118367,"file":{"id":61118367,"farm":"farm1","bucket":"hbimg","key":"614262fd9f74680390aa15dcb0087bcbabc0256428a86-dMJtsa","type":"image/jpeg","width":500,"height":749,"frames":1},"media_type":0,"source":"douban.com","link":"http://www.douban.com/photos/photo/2202235791/large","raw_text":"","text_meta":{},"via":7,"via_user_id":0,"original":null,"created_at":1413698839,"like_count":2,"comment_count":0,"repin_count":6,"is_private":0,"orig_source":null,"user":{"user_id":173443,"username":"浓的重","urlname":"shirleyssy","created_at":1333195026,"avatar":{"id":1435659,"farm":"farm1","bucket":"hbimg","key":"2eda48fc1ead8d8c365e32d823a58f1f93da7e32357e-NV5GR3","type":"image/jpeg","width":180,"height":180,"frames":1},"extra":null},"board":{"board_id":491156,"user_id":173443,"title":"女搭","description":"take notes","category_id":"apparel","seq":2,"pin_count":1230,"follow_count":16634,"like_count":6,"created_at":1333288814,"updated_at":1459856101,"deleting":0,"is_private":0,"extra":null}},{"pin_id":282375523,"user_id":12382073,"board_id":16055062,"file_id":62580928,"file":{"id":62580928,"farm":"farm1","bucket":"hbimg","key":"013b4fdadb0611e6635efda84526b87cdfd3698eaaa3-R4yOTY","type":"image/jpeg","width":517,"height":1006,"frames":1},"media_type":0,"source":"pinterest.com","link":"https://www.pinterest.com/pin/304485624781306560/","raw_text":"wedding dress","text_meta":{},"via":282367254,"via_user_id":639656,"original":269616736,"created_at":1417085571,"like_count":9,"comment_count":0,"repin_count":57,"is_private":0,"orig_source":null,"user":{"user_id":12382073,"username":"晴空鱼","urlname":"zhaoliyun8063","created_at":1394167729,"avatar":{"id":39416230,"farm":"farm1","bucket":"hbimg","key":"40c082f2349d9975d0de873db34229e43da3451b234b-3K2TWd","type":"image/jpeg","width":180,"height":180,"frames":1},"extra":null},"board":{"board_id":16055062,"user_id":12382073,"title":"高跟鞋与长裙子","description":"There's something so beautiful","category_id":"apparel","seq":30,"pin_count":446,"follow_count":605,"like_count":1,"created_at":1402029564,"updated_at":1434084968,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"360797283"}}}},{"pin_id":287158957,"user_id":12263187,"board_id":17762335,"file_id":23270970,"file":{"id":23270970,"farm":"farm1","bucket":"hbimg","key":"84a120594f1334dc3e21cb5c7f98eb229504c86425d6d-c51yWn","type":"image/jpeg","width":580,"height":825,"frames":1,"theme":"1C120F"},"media_type":0,"source":"blog.sina.com.cn","link":"http://blog.sina.com.cn/s/blog_cf666d3801019gb2.html","raw_text":"美丽的女人脸2","text_meta":{},"via":287125012,"via_user_id":1024937,"original":102579720,"created_at":1417837892,"like_count":8,"comment_count":0,"repin_count":31,"is_private":0,"orig_source":null,"user":{"user_id":12263187,"username":"茶中尘泥","urlname":"wenbingddd","created_at":1393471689,"avatar":{"id":81254691,"farm":"farm1","bucket":"hbimg","key":"889b3f20b8ab90a8086f98ca9d0b8df91b1aae7d71558-tM73WS","type":"image/jpeg","width":743,"height":738,"frames":1},"extra":null},"board":{"board_id":17762335,"user_id":12263187,"title":"时尚斑斓","description":"","category_id":"apparel","seq":27,"pin_count":1164,"follow_count":430,"like_count":2,"created_at":1411692230,"updated_at":1473165489,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"740172911"}}}},{"pin_id":301603755,"user_id":65830,"board_id":17580477,"file_id":65551763,"file":{"id":65551763,"farm":"farm1","bucket":"hbimg","key":"6cbd3642f8dcba5a71ac6ac6d75593f61dde1dec16663-COP8me","type":"image/jpeg","width":679,"height":1000,"frames":1,"theme":"0B0B0B"},"media_type":0,"source":"s-media-cache-ak0.pinimg.com","link":"https://s-media-cache-ak0.pinimg.com/736x/ca/45/c9/ca45c90cdcd45f968c4cce4544db74f3.jpg","raw_text":"","text_meta":{},"via":301390400,"via_user_id":6468074,"original":301390400,"created_at":1419994170,"like_count":13,"comment_count":0,"repin_count":34,"is_private":0,"orig_source":null,"user":{"user_id":65830,"username":"夏娜塔莉亚","urlname":"aier613","created_at":1328259400,"avatar":{"id":498878,"farm":"farm1","bucket":"hbimg","key":"fa2812b73d9d8cb3b76bcbfeaefcca2da4c914c01562-7htUgC","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null},"board":{"board_id":17580477,"user_id":65830,"title":"瞬间の影","description":"","category_id":"photography","seq":14,"pin_count":467,"follow_count":180,"like_count":1,"created_at":1411053839,"updated_at":1450184327,"deleting":0,"is_private":0,"extra":null}},{"pin_id":307036206,"user_id":6863124,"board_id":17487289,"file_id":45844322,"file":{"id":45844322,"farm":"farm1","bucket":"hbimg","key":"20039810022859793afc5276152c87c73d24219948853-NJ8RwA","type":"image/jpeg","width":800,"height":595,"frames":1},"media_type":0,"source":"esquire.com.cn","link":"http://www.esquire.com.cn/stylediary/trendsadvice/article-61473-1.html","raw_text":"年轻阳光 AG Jeans 2014春夏系列造型大片","text_meta":{},"via":181662547,"via_user_id":12474933,"original":181662547,"created_at":1420881774,"like_count":0,"comment_count":0,"repin_count":12,"is_private":0,"orig_source":"http://i2.esquire.com.cn/data/attachment/portal/201406/19/191145vlu86ss8ucu6bfbn.jpg.original.jpg","user":{"user_id":6863124,"username":"左寒left","urlname":"zengzhihuang","created_at":1363498717,"avatar":{"id":64979275,"farm":"farm1","bucket":"hbimg","key":"85e5f9288c36b61418dc01eba64712a3326cc7fc1326c-kBK4Yg","type":"image/jpeg","width":640,"height":640,"frames":1},"extra":{"is_museuser":true}},"board":{"board_id":17487289,"user_id":6863124,"title":"女模  模特","description":"","category_id":"apparel","seq":14,"pin_count":2976,"follow_count":1320,"like_count":61,"created_at":1410436368,"updated_at":1473499369,"deleting":0,"is_private":0,"extra":null}},{"pin_id":310330853,"user_id":9376147,"board_id":14204769,"file_id":35093161,"file":{"id":35093161,"farm":"farm1","bucket":"hbimg","key":"4d7ca54728eb65137d611de107aac7ca079f69e06530-DPTnfj","type":"image/jpeg","width":513,"height":730,"frames":1},"media_type":0,"source":"media-cache-ak0.pinimg.com","link":"http://media-cache-ak0.pinimg.com/736x/9f/87/4c/9f874c9c30e8eeab38f338ff104910a1.jpg","raw_text":"","text_meta":{},"via":310119963,"via_user_id":6311290,"original":126676412,"created_at":1421373822,"like_count":7,"comment_count":0,"repin_count":22,"is_private":0,"orig_source":null,"user":{"user_id":9376147,"username":"佛不跳墙我跳cathy","urlname":"t2r2hrwfiv0","created_at":1376972948,"avatar":{"id":92249249,"farm":"farm1","bucket":"hbimg","key":"589ec9116f6dfaabbcf3fbe071dfb7000a30722cbf43-hO8wyP","type":"image/jpeg","height":"587","frames":"1","width":"440"},"extra":null},"board":{"board_id":14204769,"user_id":9376147,"title":"风情","description":"","category_id":"apparel","seq":7,"pin_count":260,"follow_count":304,"like_count":2,"created_at":1390752382,"updated_at":1471938539,"deleting":0,"is_private":0,"extra":null}},{"pin_id":321611275,"user_id":495515,"board_id":2806507,"file_id":64518609,"file":{"id":64518609,"farm":"farm1","bucket":"hbimg","key":"d15e11098cb83e85ffdb785d6ddb68721ce71167fb63-m37r7i","type":"image/jpeg","width":640,"height":960,"frames":1,"theme":"191919"},"media_type":0,"source":"gd3.alicdn.com","link":"http://gd3.alicdn.com/imgextra/i3/885921093/TB2E6VjaVXXXXcbXXXXXXXXXXXX_!!885921093.jpg","raw_text":"","text_meta":{},"via":321596246,"via_user_id":9518555,"original":290482727,"created_at":1423039276,"like_count":25,"comment_count":0,"repin_count":79,"is_private":0,"orig_source":null,"user":{"user_id":495515,"username":"南山物语","urlname":"hhxxjj555","created_at":1342267859,"avatar":{"id":73273711,"farm":"farm1","bucket":"hbimg","key":"7f3a04039d77f0b3dcabd16a3e7d241534c2a6dc19e27-U3aDET","type":"image/jpeg","width":416,"height":416,"frames":1},"extra":null},"board":{"board_id":2806507,"user_id":495515,"title":"街拍","description":"","category_id":"apparel","seq":5,"pin_count":554,"follow_count":433,"like_count":4,"created_at":1351354914,"updated_at":1469899841,"deleting":0,"is_private":0,"extra":null}},{"pin_id":322990901,"user_id":6459601,"board_id":3492096,"file_id":67856557,"file":{"id":67856557,"farm":"farm1","bucket":"hbimg","key":"c7adab0e5f560f26828a6d476a654a2563343fee27a21-tVMoJf","type":"image/jpeg","width":705,"height":652,"frames":1,"theme":"1F1F23"},"media_type":0,"source":null,"link":null,"raw_text":"","text_meta":{},"via":1,"via_user_id":0,"original":null,"created_at":1423216810,"like_count":6,"comment_count":0,"repin_count":24,"is_private":0,"orig_source":null,"user":{"user_id":6459601,"username":"冰丶若梦","urlname":"libinbin","created_at":1361086184,"avatar":{"id":11378505,"farm":"farm1","bucket":"hbimg","key":"d92277485e70d1034bbfb2516dd201744746827dc39-uBmTht","type":"image/jpeg","width":50,"height":50,"frames":1},"extra":null},"board":{"board_id":3492096,"user_id":6459601,"title":"街拍","description":"","category_id":"apparel","seq":1,"pin_count":908,"follow_count":6652,"like_count":12,"created_at":1361087076,"updated_at":1447306218,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"326055708"}}}},{"pin_id":329648017,"user_id":14926999,"board_id":17022209,"file_id":62561867,"file":{"id":62561867,"farm":"farm1","bucket":"hbimg","key":"94a8ab928fe6ea28148994fa528287d62a7497141bb10-5BmPHG","type":"image/jpeg","width":500,"height":750,"frames":1},"media_type":0,"source":"weheartit.com","link":"http://weheartit.com/entry/143335820/in-set/16261476-sometimes-nothing-to-believe?context_user=buse_halac&page=10","raw_text":"","text_meta":{},"via":329466010,"via_user_id":6350878,"original":269463213,"created_at":1424685913,"like_count":10,"comment_count":0,"repin_count":46,"is_private":0,"orig_source":null,"user":{"user_id":14926999,"username":"~ling~","urlname":"a5xmzedary","created_at":1408176719,"avatar":{"id":50608244,"farm":"farm1","bucket":"hbimg","key":"6d8757a8fea18cda55a600bdc29345604cab3a24e7a-84ddqr","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null},"board":{"board_id":17022209,"user_id":14926999,"title":"欧美风","description":"","category_id":"apparel","seq":2,"pin_count":294,"follow_count":49,"like_count":1,"created_at":1408177227,"updated_at":1438313879,"deleting":0,"is_private":0,"extra":null}}]
     */

    private PinBean pin;
    /**
     * pin : {"pin_id":832252280,"user_id":17202953,"board_id":22236960,"file_id":109407389,"file":{"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40772623/Illusionary-Prisms","raw_text":"","text_meta":{},"via":831961636,"via_user_id":16745470,"original":831961636,"created_at":1472106083,"like_count":24,"comment_count":1,"repin_count":126,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg","user":{"user_id":17202953,"username":"达令☞","urlname":"tdlpukwogz","created_at":1430961383,"avatar":{"id":111551732,"farm":"farm1","bucket":"hbimg","key":"ce9069ec38d0d5af73ee970ae388d9c30c3dc3f05d060-K1OfBk","type":"image/jpeg","width":"640","height":"640","frames":"1"},"extra":{"is_museuser":true}},"board":{"board_id":22236960,"user_id":17202953,"title":"model☞","description":"【绝代有佳人，幽居在空谷】","category_id":"apparel","seq":9,"pin_count":1628,"follow_count":1395,"like_count":47,"created_at":1431478225,"updated_at":1473572831,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"702438389"}}}]}
     * stores : {}
     * promotions : false
     * ads : {"fixedAds":[],"normalAds":[]}
     */

    private boolean promotions;

    public static PinDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, PinDetailBean.class);
    }

    public PinBean getPin() {
        return pin;
    }

    public void setPin(PinBean pin) {
        this.pin = pin;
    }

    public boolean isPromotions() {
        return promotions;
    }

    public void setPromotions(boolean promotions) {
        this.promotions = promotions;
    }

    public static class PinBean {
        private int pin_id;
        private int user_id;
        private int board_id;
        private int file_id;
        /**
         * id : 109407389
         * farm : farm1
         * bucket : hbimg
         * key : 43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms
         * type : image/jpeg
         * height : 1000
         * frames : 1
         * width : 667
         * colors : [{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}]
         * theme : ff9999
         */

        private FileBean file;
        private int media_type;
        private String source;
        private String link;
        private String raw_text;
        private int via;
        private int via_user_id;
        private int original;
        private int created_at;
        private int like_count;
        private int comment_count;
        private int repin_count;
        private int is_private;
        private String orig_source;
        /**
         * user_id : 17202953
         * username : 达令☞
         * urlname : tdlpukwogz
         * created_at : 1430961383
         * avatar : {"id":111551732,"farm":"farm1","bucket":"hbimg","key":"ce9069ec38d0d5af73ee970ae388d9c30c3dc3f05d060-K1OfBk","type":"image/jpeg","width":"640","height":"640","frames":"1"}
         * extra : {"is_museuser":true}
         */

        private UserBean user;
        /**
         * board_id : 22236960
         * user_id : 17202953
         * title : model☞
         * description : 【绝代有佳人，幽居在空谷】
         * category_id : apparel
         * seq : 9
         * pin_count : 1628
         * follow_count : 1395
         * like_count : 47
         * created_at : 1431478225
         * updated_at : 1473572831
         * deleting : 0
         * is_private : 0
         * extra : {"cover":{"pin_id":"702438389"}}
         * pins : [{"pin_id":849672318,"user_id":17202953,"board_id":22236960,"file_id":114192253,"file":{"bucket":"hbimg","key":"7c45df7adda5b6fad516dc6a3ad98f8b9026530222921-meAuL2","type":"image/jpeg","height":"1050","width":"700","frames":"1"},"media_type":0,"source":"duitang.com","link":"http://www.duitang.com/blog/?id=632001781","raw_text":"","text_meta":{},"via":849496060,"via_user_id":17015112,"original":849496060,"created_at":1473470707,"like_count":0,"comment_count":0,"repin_count":6,"is_private":0,"orig_source":null},{"pin_id":849672163,"user_id":17202953,"board_id":22236960,"file_id":114227275,"file":{"bucket":"hbimg","key":"5c556c9ff19fedb4ad9bbd8c1deab2e50e405e4221e0d-lpMKKD","type":"image/jpeg","height":"1050","width":"700","frames":"1"},"media_type":0,"source":"duitang.com","link":"http://www.duitang.com/blog/?id=632002035","raw_text":" ","text_meta":{},"via":849493609,"via_user_id":17015112,"original":849493609,"created_at":1473470698,"like_count":2,"comment_count":0,"repin_count":8,"is_private":0,"orig_source":null},{"pin_id":848480410,"user_id":17202953,"board_id":22236960,"file_id":98037526,"file":{"bucket":"hbimg","key":"96247814e6e16bdd4668e5a0f264dc3131b436a9469ba-E4GgE6","type":"image/jpeg","height":"1009","width":"690","frames":"1"},"media_type":0,"source":"weibo.com","link":"http://weibo.com/1249685117/DnaFLe45r?from=page_1005051249685117_profile&wvr=6&mod=weibotime","raw_text":"","text_meta":{},"via":848476701,"via_user_id":18204915,"original":667272112,"created_at":1473384886,"like_count":4,"comment_count":0,"repin_count":5,"is_private":0,"orig_source":null},{"pin_id":848473697,"user_id":17202953,"board_id":22236960,"file_id":112901497,"file":{"bucket":"hbimg","key":"6d073558eb5beb3d060ae06d9f3639d4cd287be7d80d6-iExe5h","type":"image/jpeg","height":"1155","width":"920","frames":"1"},"media_type":0,"source":"cnu.cc","link":"http://www.cnu.cc/works/146173","raw_text":"\n","text_meta":{},"via":848468985,"via_user_id":17133213,"original":834806828,"created_at":1473384692,"like_count":2,"comment_count":0,"repin_count":13,"is_private":0,"orig_source":null},{"pin_id":844302133,"user_id":17202953,"board_id":22236960,"file_id":75444935,"file":{"bucket":"hbimg","key":"553381b56c34fd6a8bcb16224d28d019e2f9b7046dd38-dKjWcR","type":"image/jpeg","height":2048,"width":1366,"frames":1},"media_type":0,"source":"style.com","link":"http://www.style.com/slideshows/fashion-shows/resort-2016/elie-saab/collection/18","raw_text":"","text_meta":{},"via":844301784,"via_user_id":18468653,"original":401923379,"created_at":1473074665,"like_count":11,"comment_count":0,"repin_count":42,"is_private":0,"orig_source":null},{"pin_id":844295622,"user_id":17202953,"board_id":22236960,"file_id":48922356,"file":{"bucket":"hbimg","key":"45ca8c19bd87a42fb83458c93ceedb7a59a9de5c1444a-ogaLGF","type":"image/jpeg","height":1044,"width":736,"frames":1},"media_type":0,"source":null,"link":null,"raw_text":"Larissa Hofmann","text_meta":{},"via":446101429,"via_user_id":6468074,"original":446101429,"created_at":1473073990,"like_count":9,"comment_count":0,"repin_count":49,"is_private":0,"orig_source":null},{"pin_id":844295271,"user_id":17202953,"board_id":22236960,"file_id":79080616,"file":{"bucket":"hbimg","key":"45711f088e0583fb3c2211642c00ffd58774f8741f6f4-8v2vxg","type":"image/jpeg","height":977,"width":736,"frames":1},"media_type":0,"source":null,"link":null,"raw_text":"","text_meta":{},"via":446116423,"via_user_id":6468074,"original":446116423,"created_at":1473073954,"like_count":4,"comment_count":1,"repin_count":29,"is_private":0,"orig_source":null},{"pin_id":844289276,"user_id":17202953,"board_id":22236960,"file_id":113653536,"file":{"bucket":"hbimg","key":"5eea78ba96ab130326004bc4894535214312b44b9ae4a-kGiQAL","type":"image/jpeg","height":"1380","width":"920","frames":"1"},"media_type":0,"source":"cnu.cc","link":"http://www.cnu.cc/works/148514","raw_text":"","text_meta":{},"via":843695707,"via_user_id":16987611,"original":843092690,"created_at":1473073308,"like_count":7,"comment_count":0,"repin_count":27,"is_private":0,"orig_source":null},{"pin_id":840911023,"user_id":17202953,"board_id":22236960,"file_id":110551692,"file":{"bucket":"hbimg","key":"9487c6e15a2b73bfcf9457a2e910b567d4cd63c39d9bd-Gngzw2","type":"image/jpeg","height":"1799","width":"1200","frames":"1"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40682365/ADIDAS-Gazelle-collage-campaign","raw_text":"","text_meta":{},"via":840577237,"via_user_id":12078094,"original":811366843,"created_at":1472779056,"like_count":11,"comment_count":1,"repin_count":48,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/7b649e40682365.5788a2fb38c26.jpg"},{"pin_id":832252280,"user_id":17202953,"board_id":22236960,"file_id":109407389,"file":{"bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","width":"667","frames":"1"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40772623/Illusionary-Prisms","raw_text":"","text_meta":{},"via":831961636,"via_user_id":16745470,"original":831961636,"created_at":1472106083,"like_count":24,"comment_count":1,"repin_count":126,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg"},{"pin_id":832252045,"user_id":17202953,"board_id":22236960,"file_id":112653973,"file":{"bucket":"hbimg","key":"5ce65fff79222b3dcb7916f9749fe17e7f0aac413ae4f-7f818P","type":"image/jpeg","height":"800","width":"533","frames":"1"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40772623/Illusionary-Prisms","raw_text":"","text_meta":{},"via":831965855,"via_user_id":16745470,"original":831965855,"created_at":1472106077,"like_count":12,"comment_count":0,"repin_count":69,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/disp/0feee640772623.578cf82ac2810.jpg"},{"pin_id":830772725,"user_id":17202953,"board_id":22236960,"file_id":105579715,"file":{"bucket":"hbimg","key":"9143c6462884e9ea40323a5bd0b0a35b8febbf929ba3b-QCfEKA","type":"image/jpeg","height":"1195","width":"801","frames":"1"},"media_type":0,"source":"chuu.co.kr","link":"http://www.chuu.co.kr/product/detail.html?product_no=16401&cate_no=12&display_group=1","raw_text":"                                                                ","text_meta":{},"via":830730044,"via_user_id":257305,"original":830709825,"created_at":1472016957,"like_count":13,"comment_count":0,"repin_count":68,"is_private":0,"orig_source":"http://www.chuu.co.kr/chuu-up/myang/2016/06/0608-tb-01_26.jpg"},{"pin_id":830772561,"user_id":17202953,"board_id":22236960,"file_id":105579707,"file":{"bucket":"hbimg","key":"8517a717690bd57f9f633c4f2c0b94dc0ee8f1a891e15-q205i7","type":"image/jpeg","height":"1256","width":"799","frames":"1"},"media_type":0,"source":"chuu.co.kr","link":"http://www.chuu.co.kr/product/detail.html?product_no=16401&cate_no=12&display_group=1","raw_text":"                                                              ","text_meta":{},"via":830730006,"via_user_id":257305,"original":830709824,"created_at":1472016951,"like_count":11,"comment_count":0,"repin_count":64,"is_private":0,"orig_source":"http://www.chuu.co.kr/chuu-up/myang/2016/06/0608-tb-01_60.jpg"},{"pin_id":829028826,"user_id":17202953,"board_id":22236960,"file_id":68456940,"file":{"bucket":"hbimg","key":"89688d08f625f6221eae1f54fa10204aaad7d82f23985-gH6UXa","type":"image/jpeg","height":660,"width":950,"frames":1},"media_type":0,"source":"weibo.com","link":"http://weibo.com/mygroups?gid=3518147338677242&wvr=6&leftnav=1#_rnd1424179921896","raw_text":"","text_meta":{},"via":328254872,"via_user_id":9011340,"original":328254872,"created_at":1471919013,"like_count":1,"comment_count":0,"repin_count":18,"is_private":0,"orig_source":null},{"pin_id":829028705,"user_id":17202953,"board_id":22236960,"file_id":68456935,"file":{"bucket":"hbimg","key":"891c39e47c1902897992466f910a2255437fd3b024743-XPJW3F","type":"image/jpeg","height":660,"width":950,"frames":1},"media_type":0,"source":"weibo.com","link":"http://weibo.com/mygroups?gid=3518147338677242&wvr=6&leftnav=1#_rnd1424179921896","raw_text":"","text_meta":{},"via":328254810,"via_user_id":9011340,"original":328254810,"created_at":1471919011,"like_count":1,"comment_count":0,"repin_count":7,"is_private":0,"orig_source":null},{"pin_id":829023871,"user_id":17202953,"board_id":22236960,"file_id":76141812,"file":{"bucket":"hbimg","key":"e62dd5dfae9e3b9f9c924c400800ddd79af0bd2b50189-OuBPiW","type":"image/jpeg","height":1191,"width":1024,"frames":1},"media_type":0,"source":"weibo.com","link":"http://weibo.com/tamashinotina?from=myfollow_all#_rnd1435280216118","raw_text":"","text_meta":{},"via":410719053,"via_user_id":9011340,"original":410719053,"created_at":1471918892,"like_count":5,"comment_count":0,"repin_count":65,"is_private":0,"orig_source":null},{"pin_id":829019729,"user_id":17202953,"board_id":22236960,"file_id":80500921,"file":{"bucket":"hbimg","key":"59decafeb8ad6b8cc0d1c43555db2a30865237fc5c1c7-k5lOCy","type":"image/jpeg","height":1310,"width":1024,"frames":1},"media_type":0,"source":"weibo.com","link":"http://weibo.com/mygroups?gid=3518147338677242&wvr=6&leftnav=1#1440771221772","raw_text":"","text_meta":{},"via":463834966,"via_user_id":9011340,"original":463834966,"created_at":1471918794,"like_count":1,"comment_count":0,"repin_count":17,"is_private":0,"orig_source":null},{"pin_id":829019527,"user_id":17202953,"board_id":22236960,"file_id":80500930,"file":{"bucket":"hbimg","key":"1e9544f4af61d19cc7768ddfc0ae75c86c05b50651268-8xIJyU","type":"image/jpeg","height":1448,"width":1024,"frames":1},"media_type":0,"source":"weibo.com","link":"http://weibo.com/mygroups?gid=3518147338677242&wvr=6&leftnav=1#1440771221772","raw_text":"","text_meta":{},"via":463835038,"via_user_id":9011340,"original":463835038,"created_at":1471918789,"like_count":4,"comment_count":0,"repin_count":35,"is_private":0,"orig_source":null},{"pin_id":829019412,"user_id":17202953,"board_id":22236960,"file_id":80500932,"file":{"bucket":"hbimg","key":"8b70550712ef6823d8554b8477189e9968dcf6644b4d0-rQKPA8","type":"image/jpeg","height":1303,"width":1024,"frames":1},"media_type":0,"source":"weibo.com","link":"http://weibo.com/mygroups?gid=3518147338677242&wvr=6&leftnav=1#1440771221772","raw_text":"","text_meta":{},"via":463835068,"via_user_id":9011340,"original":463835068,"created_at":1471918786,"like_count":5,"comment_count":0,"repin_count":28,"is_private":0,"orig_source":null},{"pin_id":827975394,"user_id":17202953,"board_id":22236960,"file_id":69565269,"file":{"bucket":"hbimg","key":"08f6ffc4382105171fcfd38309c934f4dd1d595a201e3-Pj0MyM","type":"image/jpeg","height":700,"width":538,"frames":1},"media_type":0,"source":"esquire.com.cn","link":"http://www.esquire.com.cn/stylediary/trendsadvice/article-98820-12.html","raw_text":"","text_meta":{},"via":688800882,"via_user_id":17061199,"original":337369514,"created_at":1471844969,"like_count":12,"comment_count":0,"repin_count":67,"is_private":0,"orig_source":"http://i2.esquire.com.cn/data/attachment/portal/201503/05/000717omwlo9q9bolz2hqq.jpg.original.jpg"}]
         * following : false
         */

        private UserBoardItemBean board;
        private UserBean via_user;
        /**
         * pin_id : 831961636
         * user_id : 16745470
         * board_id : 27352895
         * file_id : 109407389
         * file : {"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"}
         * media_type : 0
         * source : behance.net
         * link : https://www.behance.net/gallery/40772623/Illusionary-Prisms
         * raw_text : Illusionary Prisms : Photographers: Gabriel Isak, Roberto GaxiolaStylist: Sarahy CisnerosModel: Madison KannaMUAH: Danielle Fisk Assistant MUAH: Ashley Goodman
         * text_meta : {}
         * via : 2
         * via_user_id : 0
         * original : null
         * created_at : 1472094533
         * like_count : 1
         * comment_count : 0
         * repin_count : 154
         * is_private : 0
         * orig_source : https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg
         * board : {"board_id":27352895,"user_id":16745470,"title":"人像","description":"","category_id":"photography","seq":6,"pin_count":651,"follow_count":453,"like_count":0,"created_at":1449137300,"updated_at":1472782982,"deleting":0,"is_private":0,"extra":null}
         */

        private ViaPinBean via_pin;
        private ViaPinBean original_pin;
        private boolean hide_origin;
        private PinsBean prev;
        private PinsBean next;
        private boolean liked;
        private Object breadcrumb;
        private String category;
        /**
         * pin_id : 850420597
         * user_id : 18560749
         * board_id : 30931703
         * file_id : 109407389
         * file : {"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"}
         * media_type : 0
         * source : behance.net
         * link : https://www.behance.net/gallery/40772623/Illusionary-Prisms
         * raw_text :
         * text_meta : {}
         * via : 832252280
         * via_user_id : 17202953
         * original : 831961636
         * created_at : 1473551403
         * like_count : 0
         * comment_count : 0
         * repin_count : 0
         * is_private : 0
         * orig_source : https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg
         * user : {"user_id":18560749,"username":"我是小小小小寶","urlname":"pj6j5cuvgn","created_at":1459214798,"avatar":{"id":97715831,"farm":"farm1","bucket":"hbimg","key":"45437cc64e4b8c5632b371b984ae627cc6b9205552d-gMYA4q","type":"image/jpeg","height":"50","frames":"1","width":"50"},"extra":null}
         * board : {"board_id":30931703,"user_id":18560749,"title":"服装和造型设计","description":"","category_id":null,"seq":18,"pin_count":47,"follow_count":1,"like_count":0,"created_at":1469453549,"updated_at":1473552545,"deleting":0,"is_private":0,"extra":null,"pins":[{"pin_id":850423638,"user_id":18560749,"board_id":30931703,"file_id":66010122,"file":{"bucket":"hbimg","key":"abf58824b5685b4d3cce0ac119c062a87d7251893e630-vw4Yo3","type":"image/jpeg","height":801,"width":600,"frames":1},"media_type":0,"source":"shenchinlun.com","link":"http://shenchinlun.com/News.Detail.asp?ID=283","raw_text":"Nishimura Meibutsu 2014 A/W/shenchinlun :","text_meta":{},"via":434286783,"via_user_id":14464765,"original":355047759,"created_at":1473552545,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":"http://shenchinlun.com/upload/web/56382094.jpg"},{"pin_id":850423552,"user_id":18560749,"board_id":30931703,"file_id":37836792,"file":{"bucket":"hbimg","key":"84d9fd1d80978e9c16e020d64b493b0fd4e520061f0c7-kzW5Wt","type":"image/jpeg","height":671,"width":510,"frames":1},"media_type":0,"source":"pinterest.com","link":"http://www.pinterest.com/pin/490962796849154066/","raw_text":"OLYMPIC PAPER MAGIC BY SHONA HEATH","text_meta":{},"via":137211778,"via_user_id":6715297,"original":137211778,"created_at":1473552516,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null},{"pin_id":850423369,"user_id":18560749,"board_id":30931703,"file_id":37594100,"file":{"bucket":"hbimg","key":"eb1493d90c7d8344af6479cda363ce88c6d43e807b8d5-2uR1Ft","type":"image/jpeg","height":871,"width":600,"frames":1},"media_type":0,"source":"zcool.com.cn","link":"http://www.zcool.com.cn/show/ZMTU4NDEy/2.html","raw_text":"佳作欣赏：《小小的碎片-艺术》","text_meta":{},"via":206706373,"via_user_id":961066,"original":206706373,"created_at":1473552461,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":"http://zcimg.zcool.com.cn/zcimg/m_781a53dd95fa00000115b7dc69e8.jpg"},{"pin_id":850423349,"user_id":18560749,"board_id":30931703,"file_id":30951888,"file":{"bucket":"hbimg","key":"28cfeeeed7feda6d54df1132f0d0e3a5f94b30fe5bec8-k2HFgL","type":"image/jpeg","height":1588,"width":600,"frames":1},"media_type":0,"source":"abc.2008php.com","link":"http://abc.2008php.com/Design_news.php?id=928249&topy=2","raw_text":"美女绸带造型秀封面大图","text_meta":{},"via":113087710,"via_user_id":6440751,"original":113087710,"created_at":1473552454,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null}]}
         */

        private List<RepinsBean> repins;
        /**
         * comment_id : 2032566
         * pin_id : 832252280
         * user_id : 17191514
         * raw_text : 好喜欢
         * text_meta : {}
         * status : 1
         * created_at : 1473044455
         * user : {"user_id":17191514,"username":"吉吉黄a","urlname":"d56rxp0zm7b","created_at":1430907509,"avatar":{"id":108633138,"farm":"farm1","bucket":"hbimg","key":"ca2819bb6b5bc042a035e5a2b71ca71f5c6981d111b2a-yaWO0l","type":"image/jpeg","height":"748","width":"748","frames":"1"},"extra":null}
         */

        private List<CommentsBean> comments;
        /**
         * user_id : 10676744
         * username : Arne
         * urlname : arne369963
         * created_at : 1384579559
         * avatar : {"id":32507132,"farm":"farm1","bucket":"hbimg","key":"e60471518be7614d3e79149ec54b16122e04d299a5a-rJ0G7D","type":"image/jpeg","width":100,"height":100,"frames":1}
         * extra : null
         * liked_at : 1472962442
         */

        private List<LikesBean> likes;
        /**
         * pin_id : 850629615
         * user_id : 8937961
         * board_id : 31734075
         * file_id : 59748860
         * file : {"id":59748860,"farm":"farm1","bucket":"hbimg","key":"9fa941210887c13416be03753de4ad28bb9477ed29c4c-S5dU0j","type":"image/jpeg","width":600,"height":921,"frames":1}
         * media_type : 0
         * source : behance.net
         * link : https://www.behance.net/gallery/19365883/Zombie-Island-game
         * raw_text : &quot;Zombie Island&quot; game : Concept, modeling, texturing, rendering and importing objects for &quot;Zombie Island&quot; game
         * text_meta : {}
         * via : 564044035
         * via_user_id : 16611417
         * original : 248544209
         * created_at : 1473576979
         * like_count : 0
         * comment_count : 0
         * repin_count : 0
         * is_private : 0
         * orig_source : null
         * user : {"user_id":8937961,"username":"HsuChihmo","urlname":"q949tly5rzq","created_at":1374756297,"avatar":{"id":25370401,"farm":"farm1","bucket":"hbimg","key":"e3cc79ba26a95094b152321029c7ff2e753a989f8e2-fcVwSL","type":"image/jpeg","width":50,"height":50,"frames":1},"extra":null}
         * board : {"board_id":31734075,"user_id":8937961,"title":"2","description":"","category_id":"illustration","seq":2,"pin_count":94,"follow_count":0,"like_count":0,"created_at":1473576309,"updated_at":1473576979,"deleting":0,"is_private":0,"extra":null}
         * via_user : {"user_id":16611417,"username":"古三儿","urlname":"kxrjjmukar","created_at":1417327141,"avatar":{"id":21122412,"farm":"farm1","bucket":"hbimg","key":"66b95a3bfbfede1894941942996a3c3075122c6519a-fJ3ci9","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null}
         */

        private List<SiblingsBean> siblings;
        private List<List<RepinsBean>> recommend;

        public static PinBean objectFromData(String str) {

            return new Gson().fromJson(str, PinBean.class);
        }

        public int getPin_id() {
            return pin_id;
        }

        public void setPin_id(int pin_id) {
            this.pin_id = pin_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public int getFile_id() {
            return file_id;
        }

        public void setFile_id(int file_id) {
            this.file_id = file_id;
        }

        public FileBean getFile() {
            return file;
        }

        public void setFile(FileBean file) {
            this.file = file;
        }

        public int getMedia_type() {
            return media_type;
        }

        public void setMedia_type(int media_type) {
            this.media_type = media_type;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getRaw_text() {
            return raw_text;
        }

        public void setRaw_text(String raw_text) {
            this.raw_text = raw_text;
        }

        public int getVia() {
            return via;
        }

        public void setVia(int via) {
            this.via = via;
        }

        public int getVia_user_id() {
            return via_user_id;
        }

        public void setVia_user_id(int via_user_id) {
            this.via_user_id = via_user_id;
        }

        public int getOriginal() {
            return original;
        }

        public void setOriginal(int original) {
            this.original = original;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getRepin_count() {
            return repin_count;
        }

        public void setRepin_count(int repin_count) {
            this.repin_count = repin_count;
        }

        public int getIs_private() {
            return is_private;
        }

        public void setIs_private(int is_private) {
            this.is_private = is_private;
        }

        public String getOrig_source() {
            return orig_source;
        }

        public void setOrig_source(String orig_source) {
            this.orig_source = orig_source;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public UserBoardItemBean getBoard() {
            return board;
        }

        public void setBoard(UserBoardItemBean board) {
            this.board = board;
        }

        public UserBean getVia_user() {
            return via_user;
        }

        public void setVia_user(UserBean via_user) {
            this.via_user = via_user;
        }

        public ViaPinBean getVia_pin() {
            return via_pin;
        }

        public void setVia_pin(ViaPinBean via_pin) {
            this.via_pin = via_pin;
        }

        public ViaPinBean getOriginal_pin() {
            return original_pin;
        }

        public void setOriginal_pin(ViaPinBean original_pin) {
            this.original_pin = original_pin;
        }

        public boolean isHide_origin() {
            return hide_origin;
        }

        public void setHide_origin(boolean hide_origin) {
            this.hide_origin = hide_origin;
        }

        public PinsBean getPrev() {
            return prev;
        }

        public void setPrev(PinsBean prev) {
            this.prev = prev;
        }

        public PinsBean getNext() {
            return next;
        }

        public void setNext(PinsBean next) {
            this.next = next;
        }

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public Object getBreadcrumb() {
            return breadcrumb;
        }

        public void setBreadcrumb(Object breadcrumb) {
            this.breadcrumb = breadcrumb;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<RepinsBean> getRepins() {
            return repins;
        }

        public void setRepins(List<RepinsBean> repins) {
            this.repins = repins;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public List<LikesBean> getLikes() {
            return likes;
        }

        public void setLikes(List<LikesBean> likes) {
            this.likes = likes;
        }

        public List<SiblingsBean> getSiblings() {
            return siblings;
        }

        public void setSiblings(List<SiblingsBean> siblings) {
            this.siblings = siblings;
        }

        public List<List<RepinsBean>> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<List<RepinsBean>> recommend) {
            this.recommend = recommend;
        }

        public static class FileBean {
            private int id;
            private String farm;
            private String bucket;
            private String key;
            private String type;
            private int height;
            private String frames;
            private int width;
            private String theme;
            /**
             * color : 16751001
             * ratio : 0.34
             */

            private List<ColorsBean> colors;

            public static FileBean objectFromData(String str) {

                return new Gson().fromJson(str, FileBean.class);
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFarm() {
                return farm;
            }

            public void setFarm(String farm) {
                this.farm = farm;
            }

            public String getBucket() {
                return bucket;
            }

            public void setBucket(String bucket) {
                this.bucket = bucket;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getFrames() {
                return frames;
            }

            public void setFrames(String frames) {
                this.frames = frames;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public String getTheme() {
                return theme;
            }

            public void setTheme(String theme) {
                this.theme = theme;
            }

            public List<ColorsBean> getColors() {
                return colors;
            }

            public void setColors(List<ColorsBean> colors) {
                this.colors = colors;
            }

            public static class ColorsBean {
                private int color;
                private double ratio;

                public static ColorsBean objectFromData(String str) {

                    return new Gson().fromJson(str, ColorsBean.class);
                }

                public int getColor() {
                    return color;
                }

                public void setColor(int color) {
                    this.color = color;
                }

                public double getRatio() {
                    return ratio;
                }

                public void setRatio(double ratio) {
                    this.ratio = ratio;
                }
            }
        }

        public static class UserBean {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
            private FileBean avatar;
            /**
             * is_museuser : true
             */

            private ExtraBean extra;

            public static UserBean objectFromData(String str) {

                return new Gson().fromJson(str, UserBean.class);
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUrlname() {
                return urlname;
            }

            public void setUrlname(String urlname) {
                this.urlname = urlname;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public FileBean getAvatar() {
                return avatar;
            }

            public void setAvatar(FileBean avatar) {
                this.avatar = avatar;
            }

            public ExtraBean getExtra() {
                return extra;
            }

            public void setExtra(ExtraBean extra) {
                this.extra = extra;
            }

            public static class ExtraBean {
                private boolean is_museuser;

                public static ExtraBean objectFromData(String str) {

                    return new Gson().fromJson(str, ExtraBean.class);
                }

                public boolean isIs_museuser() {
                    return is_museuser;
                }

                public void setIs_museuser(boolean is_museuser) {
                    this.is_museuser = is_museuser;
                }
            }
        }

        public static class ViaPinBean {
            private int pin_id;
            private int user_id;
            private int board_id;
            private int file_id;
            private FileBean file;
            private int media_type;
            private String source;
            private String link;
            private String raw_text;
            private int via;
            private int via_user_id;
            private Object original;
            private int created_at;
            private int like_count;
            private int comment_count;
            private int repin_count;
            private int is_private;
            private String orig_source;
            private UserBoardItemBean board;

            public static ViaPinBean objectFromData(String str) {

                return new Gson().fromJson(str, ViaPinBean.class);
            }

            public int getPin_id() {
                return pin_id;
            }

            public void setPin_id(int pin_id) {
                this.pin_id = pin_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getBoard_id() {
                return board_id;
            }

            public void setBoard_id(int board_id) {
                this.board_id = board_id;
            }

            public int getFile_id() {
                return file_id;
            }

            public void setFile_id(int file_id) {
                this.file_id = file_id;
            }

            public FileBean getFile() {
                return file;
            }

            public void setFile(FileBean file) {
                this.file = file;
            }

            public int getMedia_type() {
                return media_type;
            }

            public void setMedia_type(int media_type) {
                this.media_type = media_type;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getRaw_text() {
                return raw_text;
            }

            public void setRaw_text(String raw_text) {
                this.raw_text = raw_text;
            }

            public int getVia() {
                return via;
            }

            public void setVia(int via) {
                this.via = via;
            }

            public int getVia_user_id() {
                return via_user_id;
            }

            public void setVia_user_id(int via_user_id) {
                this.via_user_id = via_user_id;
            }

            public Object getOriginal() {
                return original;
            }

            public void setOriginal(Object original) {
                this.original = original;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getRepin_count() {
                return repin_count;
            }

            public void setRepin_count(int repin_count) {
                this.repin_count = repin_count;
            }

            public int getIs_private() {
                return is_private;
            }

            public void setIs_private(int is_private) {
                this.is_private = is_private;
            }

            public String getOrig_source() {
                return orig_source;
            }

            public void setOrig_source(String orig_source) {
                this.orig_source = orig_source;
            }

            public UserBoardItemBean getBoard() {
                return board;
            }

            public void setBoard(UserBoardItemBean board) {
                this.board = board;
            }
        }

        public static class RepinsBean {
            private int pin_id;
            private int user_id;
            private int board_id;
            private int file_id;
            private FileBean file;
            private int media_type;
            private String source;
            private String link;
            private String raw_text;
            private int via;
            private int via_user_id;
            private int original;
            private int created_at;
            private int like_count;
            private int comment_count;
            private int repin_count;
            private int is_private;
            private String orig_source;
            private UserBean user;
            private UserBoardItemBean board;

            public static RepinsBean objectFromData(String str) {

                return new Gson().fromJson(str, RepinsBean.class);
            }

            public int getPin_id() {
                return pin_id;
            }

            public void setPin_id(int pin_id) {
                this.pin_id = pin_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getBoard_id() {
                return board_id;
            }

            public void setBoard_id(int board_id) {
                this.board_id = board_id;
            }

            public int getFile_id() {
                return file_id;
            }

            public void setFile_id(int file_id) {
                this.file_id = file_id;
            }

            public FileBean getFile() {
                return file;
            }

            public void setFile(FileBean file) {
                this.file = file;
            }

            public int getMedia_type() {
                return media_type;
            }

            public void setMedia_type(int media_type) {
                this.media_type = media_type;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getRaw_text() {
                return raw_text;
            }

            public void setRaw_text(String raw_text) {
                this.raw_text = raw_text;
            }

            public int getVia() {
                return via;
            }

            public void setVia(int via) {
                this.via = via;
            }

            public int getVia_user_id() {
                return via_user_id;
            }

            public void setVia_user_id(int via_user_id) {
                this.via_user_id = via_user_id;
            }

            public int getOriginal() {
                return original;
            }

            public void setOriginal(int original) {
                this.original = original;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getRepin_count() {
                return repin_count;
            }

            public void setRepin_count(int repin_count) {
                this.repin_count = repin_count;
            }

            public int getIs_private() {
                return is_private;
            }

            public void setIs_private(int is_private) {
                this.is_private = is_private;
            }

            public String getOrig_source() {
                return orig_source;
            }

            public void setOrig_source(String orig_source) {
                this.orig_source = orig_source;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public UserBoardItemBean getBoard() {
                return board;
            }

            public void setBoard(UserBoardItemBean board) {
                this.board = board;
            }
        }

        public static class CommentsBean {
            private int comment_id;
            private int pin_id;
            private int user_id;
            private String raw_text;
            private int status;
            private int created_at;
            private UserBean user;

            public static CommentsBean objectFromData(String str) {

                return new Gson().fromJson(str, CommentsBean.class);
            }

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public int getPin_id() {
                return pin_id;
            }

            public void setPin_id(int pin_id) {
                this.pin_id = pin_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getRaw_text() {
                return raw_text;
            }

            public void setRaw_text(String raw_text) {
                this.raw_text = raw_text;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }
        }

        public static class LikesBean {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
            private FileBean avatar;
            private Object extra;
            private int liked_at;

            public static LikesBean objectFromData(String str) {

                return new Gson().fromJson(str, LikesBean.class);
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUrlname() {
                return urlname;
            }

            public void setUrlname(String urlname) {
                this.urlname = urlname;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public FileBean getAvatar() {
                return avatar;
            }

            public void setAvatar(FileBean avatar) {
                this.avatar = avatar;
            }

            public Object getExtra() {
                return extra;
            }

            public void setExtra(Object extra) {
                this.extra = extra;
            }

            public int getLiked_at() {
                return liked_at;
            }

            public void setLiked_at(int liked_at) {
                this.liked_at = liked_at;
            }
        }

        public static class SiblingsBean {
            private int pin_id;
            private int user_id;
            private int board_id;
            private int file_id;
            private FileBean file;
            private int media_type;
            private String source;
            private String link;
            private String raw_text;
            private int via;
            private int via_user_id;
            private int original;
            private int created_at;
            private int like_count;
            private int comment_count;
            private int repin_count;
            private int is_private;
            private Object orig_source;
            private UserBean user;
            private UserBoardItemBean board;
            private UserBean via_user;

            public static SiblingsBean objectFromData(String str) {

                return new Gson().fromJson(str, SiblingsBean.class);
            }

            public int getPin_id() {
                return pin_id;
            }

            public void setPin_id(int pin_id) {
                this.pin_id = pin_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getBoard_id() {
                return board_id;
            }

            public void setBoard_id(int board_id) {
                this.board_id = board_id;
            }

            public int getFile_id() {
                return file_id;
            }

            public void setFile_id(int file_id) {
                this.file_id = file_id;
            }

            public FileBean getFile() {
                return file;
            }

            public void setFile(FileBean file) {
                this.file = file;
            }

            public int getMedia_type() {
                return media_type;
            }

            public void setMedia_type(int media_type) {
                this.media_type = media_type;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getRaw_text() {
                return raw_text;
            }

            public void setRaw_text(String raw_text) {
                this.raw_text = raw_text;
            }

            public int getVia() {
                return via;
            }

            public void setVia(int via) {
                this.via = via;
            }

            public int getVia_user_id() {
                return via_user_id;
            }

            public void setVia_user_id(int via_user_id) {
                this.via_user_id = via_user_id;
            }

            public int getOriginal() {
                return original;
            }

            public void setOriginal(int original) {
                this.original = original;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getRepin_count() {
                return repin_count;
            }

            public void setRepin_count(int repin_count) {
                this.repin_count = repin_count;
            }

            public int getIs_private() {
                return is_private;
            }

            public void setIs_private(int is_private) {
                this.is_private = is_private;
            }

            public Object getOrig_source() {
                return orig_source;
            }

            public void setOrig_source(Object orig_source) {
                this.orig_source = orig_source;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public UserBoardItemBean getBoard() {
                return board;
            }

            public void setBoard(UserBoardItemBean board) {
                this.board = board;
            }

            public UserBean getVia_user() {
                return via_user;
            }

            public void setVia_user(UserBean via_user) {
                this.via_user = via_user;
            }
        }
    }

    private static class PinsBean {
    }
}
