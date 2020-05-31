# MapAdapter
Android地图中间件

#### 介绍：
Android地图中间件，是一套适配多种地图的中间层接口。
目前，该library库已经适配了高德地图、百度地图。后续会添加对谷歌地图、MapBox地图的适配。
已经适配的接口，包括地图显示（MapView和MapFragment两种形式）、绘制Marker和Poyline、室内地图、手机定位等。后续会不断完善。

#### 应用场景：
业务中需要支持多种地图厂商。
这时，业务流程中只需要调用中间件中接口即可。所有的适配工作交由中间件去处理。