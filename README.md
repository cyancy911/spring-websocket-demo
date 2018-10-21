## 目录结构
```
sockjs-stomp-demo
websocket-demo
```

## websocket实现逻辑
```
WebSocketApplication启动 
  -> 扫描WebSocketComponent相应ServerEndpoint配置
  -> WebSocketConfig中的ServerEndpointExporter装载ServerEndpoint配置
  -> WebSocketComponent中的@On*注解开始生效
```