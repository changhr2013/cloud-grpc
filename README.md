# spring Cloud 集成 gRPC

目前，Spring Cloud 官方并没有提供除了 Restful API 之外的服务调用方案，在业内，许多公司自研了 RPC 框架，自研的目的就是要 RESTAPI 低效问题。现有的更高效的内部服务调用方案就是 gRPC。

到目前为止，Spring Cloud 官方并不支持 gRPC，但有非常优秀的第三方开源项目对其做出了很好的支持，比如开源项目 [grpc-spring-boot-starter](https://github.com/yidongnan/grpc-spring-boot-starter)。该项目作者目前就职于腾讯，就非常丰富的微服务实战经验。该项目也是 Spring Cloud 中国社区推荐的一个 gRPC 项目，已经有公司将该项目应用到生产环境中，有很好的可靠性和稳定性。

### 使用的相关版本

- spring cloud: Greenwich.SR1
- spring boot: 2.1.4.RELEASE
- grpc-spring-boot-starter: 2.3.0.RELEASE