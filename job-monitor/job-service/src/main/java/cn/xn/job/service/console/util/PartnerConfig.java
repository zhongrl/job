package cn.xn.job.service.console.util;

/**
* 商户配置信息
* @author guoyx e-mail:guoyx@lianlian.com
* @date:2013-6-25 下午01:45:40
* @version :1.0
*
*/
public interface PartnerConfig{
    // 银通公钥
    String YT_PUB_KEY     = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSPp6BH3hZotD13rTtAfIb4rlTWVt+T9xd2UWe617xglnMqcIUey3FnbGa9r084afcPIWDSfhiPqfrV0at+oGkWea3+ub4Xqm94KlGW8oF700OmEvWwjLriOGt61xvPyGMs0lA0tSKGZs2LP1lscwb9wTig62yz/bB5swFOve50wIDAQAB";
    // 商户私钥
    String TRADER_PRI_KEY = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBANI+noEfeFmi0PXetO0B8hviuVNZW35P3F3ZRZ7rXvGCWcypwhR7LcWdsZr2vTzhp9w8hYNJ+GI+p+tXRq36gaRZ5rf65vheqb3gqUZbygXvTQ6YS9bCMuuI4a3rXG8/IYyzSUDS1IoZmzYs/WWxzBv3BOKDrbLP9sHmzAU697nTAgMBAAECgYEAvTGKc0m9hHQGtGqfx3PryfpBqWaM9eKpvG5Lv1GxU4Voh8rvknyyWNWqQHfvHP03j/GKd/W1pbsaYqZcPS1jQL/C0CKFTHNAxzopnJc0GQChReNSQNHq3N+QPqCktawMd07LokIHE1KmoRPgwmqt2hugF9H7LtaU69IwTX5QLrECQQDvXdmiPDRFSEWBgttgLK3+hoCg9mFXE98a6NvQwUjQRFqo4YLjqsB5QpmAnvg4BIF8EEQvWiLBkUXyJTsYNca7AkEA4Nq1+scL7b3QlPAaLeQxivTBio1dDpf8GhXF7sizi6owc2Wflaz8yLzZ+OKfK//q+Ff6W93NJCzlPwW1F9eDyQJBANQZDdnyjMKDOot6vxZmILqyxdFa33/ALOX0RaqhSfq5Rrf0Mv229Ju+BrRwqDNC0tVyuGhY7wkjx527nbRjGjcCQQCknfqU+py1FT5bi9zetC3BOadoNE5onMSopEAgrTcljRejpEbNYbQ/kmNSXzeBEv+HiYF1OFEzptq+5QLLzbBhAkEAlzf1IjPHvOR7j5tgst6obKcbiZVzfemFC2rANGEwV/dYMud49/7oV1GoMMdW9fQc9yI0Whw4egAZdA3Nz1kJNg==";
    // MD5 KEY
    String MD5_KEY        = "U6LZyPfl0uF9c28kpnYrii8bM8OTCdoNiUMvRkL1";
    // 接收异步通知地址
    String NOTIFY_URL     = "http://domain/wepdemo/notify.htm";
    // 支付结束后返回地址
    String URL_RETURN     = "http://domain/order.htm";
 // 商户编号
    String            OID_PARTNER="201502051000206504";    
    // 签名方式 RSA或MD5
    String            SIGN_TYPE="MD5";            
}
