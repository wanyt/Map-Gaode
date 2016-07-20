# 高德地图

这是一个集成了高德地图的工程，包括3D地图，定位，搜索，导航等等功能。

>工程着手之前我是准备使用百度地图的，激活开发者账号时提示我手机号码和邮箱已被占用（难道是因为我的手机号注册了百度账户于是提示手机号被占用了？），不管怎样我给百度客服打了电话，电话里只有机器录音不能解决问题（鉴于问题特殊他们预先设置好的录音没有解决这个问题的选项），挂电话后我找了一个有人工客服的电话打过去，客服妹妹接电话之后当我说“你好，我的账号...”，这时百度的客服妹妹直接把我的电话转接到百度账号中心，然后又是“您好，这里是百度...”，顿时语塞。我又打过去，另个一客服接了电话，我说明情况之后，她委婉的表达了，我这里处理不了，可以帮您转接到地图中心的电话，转接过去之后还是没有人工客服，只有预先设置好的机器录音。到最后问题没有解决，所以使用了高德地图。百度地图的人工客服给我的感觉像是只负责转接电话，不负责解决问题，窥一斑而知全豹，这样的公司一定是有问题了。bb了这么多只是想说，百度似乎已经不是以前那个人人崇拜的百度了。（艹，你变了）

## 使用

集成高德地图比较简单，麻烦的地方可能是获取key的那一步，只要有点经验也不是十分困难。我在工程的build.gradle中配置了代码能够使调试工程使用的签名和发布版本的签名是一样的（代码如下），所以在高德后台的SHA1只填写了发布版本的没有填写调试版本的。工程发布版本的签名，keyAlias，签名密码等等在代码里都有方便你进行调整，key在清单文件里面。

```
    signingConfigs {
        config{
            storeFile file("gaode-map.jks")
            storePassword "123654"
            keyAlias "gaode"
            keyPassword "123654"
        }
    }

    buildTypes {
        release {
            signingConfig  signingConfigs.config
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
        debug {
            signingConfig signingConfigs.config
        }
    }
```

## 关于

如果工程clone到本地之后不能运行，或者你感觉代码有改进的地方，欢迎和我联系：

Email: [me_wanyt@163.com](http://mail.163.com/)