# instant-mail

即时化邮件服务。项目初衷是解决邮件接收不及时的问题，尤其是在拥有多个邮箱账户时，邮件客户端以“拉模式”的高频率后台活动，对手机续航带来严重不利影响。将“拉模式”这种繁重的操作转交给服务端，移动端以被动模式监听消息。

## 基本思想
![instant-mail.png](instant-mail.png)

## spring-boot
属性配置:
```yaml
instant-mail:
  enabled: true
  configs:
    - protocol: imap #收件协议imap/pop3
      host: imap.aliyun.com #收件服务器地址
      user: xxx@aliyun.com #邮箱地址
      password: xxx #密码
  listeners:
    httpListeners:
      - url: https://xxx #webhook
        params:
          key: string_value
        header:
          key: string_value
        use-post: true #是否post true是/false使用get
```

## docker
```
docker run -v /your_config_path:/config instant-mail
```