package cn.LoveUO.controller;

import cn.LoveUO.entity.WxPayResultEntity;
import cn.LoveUO.service.IUserService;

import cn.LoveUO.util.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController{
    @Autowired
    private IUserService userService;

    @RequestMapping("/startOrder")
    public ResponseResult<Void> save(Long productId, Model model, HttpServletRequest request) throws Exception {
       /* //根据商品id查询商品详细信息(假数据)
        double price = 0.01;//(0.01元)
        String productName = "叩丁狼VIP会员";
        //生成订单编号
        int number = (int)((Math.random()*9)*1000);//随机数
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//时间
        String orderNumber = dateFormat.format(new Date()) + number;
        //准备调用接口需要的参数
        WxOrderEntity order = new WxOrderEntity();
        //appid
        order.setAppid(WeChatUtil.APPID);
        //商户号
        order.setMch_id(WeChatUtil.MCH_ID);
        //商品描述
        order.setBody(productName);
        //交易类型
        order.setTrade_type("NATIVE");
        //商户订单号
        order.setOut_trade_no(orderNumber);
        //支付金额(单位：分)
        order.setTotal_fee((int)(price*100));
        //用户ip地址
        order.setSpbill_create_ip(RequestUtil.getIPAddress(request));
        //设置商品id
        order.setProduct_id(productId);
        //接收支付结果的地址
        order.setNotify_url("localhost：8080/lover/order/receive");
        //32位随机数(UUID去掉-就是32位的)
        String uuid = UUID.randomUUID().toString().replace("-", "");
        order.setNonce_str(uuid);
        //生成签名
        String sign = WeChatUtil.getPaySign(order);
        order.setSign(sign);
        //调用微信支付统一下单接口,让微信也生成一个预支付订单
        String xmlResult = HttpUtil.post(GET_PAY_URL, XMLUtil.toXmlString(order));
        //把返回的xml字符串转成对象
        WxOrderResultEntity entity = XMLUtil.toObject(xmlResult,WxOrderResultEntity.class);
        //微信预支付单成功创建
        if(entity.getReturn_code().equals("SUCCESS")&&entity.getResult_code().equals("SUCCESS")){
            //使用二维码生成工具，把微信返回的codeUrl转为二维码图片，保存到磁盘
            String codeUrl = entity.getCode_url();
            //使用订单号来作为二维码的图片名称
            File file = new File(QRCodeUtil.PAY_PATH,orderNumber+".jpg");
            QRCodeUtil.createImage(codeUrl,new FileOutputStream(file));
            //把订单号传到支付页面
            model.addAttribute("orderNumber",orderNumber);
        }
        */
        //跳转到支付页进行支付
        return  new ResponseResult<>(SUCCESS);
    }


    /**
     * 计算微信支付的签名
     * @param obj
     * @return
     * @throws IllegalAccessException
     *//*
    public static String getPaySign(Object obj) throws IllegalAccessException, IOException {
        StringBuilder sb = new StringBuilder();
        //把对象转为TreeMap集合(按照key的ASCII 码从小到大排序)
        TreeMap<String, Object> map;
        if(!(obj instanceof Map)) {
            map = ObjectUtils.objectToMap(obj);
        }else{
            map = (TreeMap)obj;
        }
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        //遍历键值对
        for (Map.Entry<String, Object> entry : entrySet) {
            //如果值为空，不参与签名
            if(entry.getValue()!=null) {
                //格式key1=value1&key2=value2…
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        //最后拼接商户的API密钥
        String stringSignTemp = sb.toString()+"key="+WeChatUtil.KEY;
        //进行md5加密并转为大写
        return SecurityUtil.MD5(stringSignTemp).toUpperCase();
    }*/

    @RequestMapping("getQrCode")
    public ResponseResult<String> getQrCode(String orderNumber, HttpServletResponse response) throws IOException {
        //从磁盘中获取付款二维码并输出给response
        /*File file = new File(QRCodeUtil.PAY_PATH,orderNumber+".jpg");
        if(file.exists()){
            IOUtils.copy(new FileInputStream(file),response.getOutputStream());
        }*/

        return new ResponseResult<>(SUCCESS,"../images/index/erweima.png");
    }

    @RequestMapping("receive")
    @ResponseBody
    public ResponseResult<Void> receive(@RequestBody WxPayResultEntity result) throws IOException {
        //判断是否支付成功
        if(result.getReturn_code().equals("SUCCESS")&&result.getResult_code().equals("SUCCESS")){
            //避免结果出现差异，安全起见，会再调用预支付订单查询的接口，检查该订单的状态是否是已支付
            //代码省略
            //.....
        }
        //通知微信我们收到了，如果微信没有收到回复，会间隔一段时间又通知一遍，这样的话容易出现业务重复处理操作

        return new ResponseResult<>();
    }





}
