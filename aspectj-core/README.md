JPoint：
    - java代码中可以切入的点，可以称之为可切点；
    - 包括get/set/block（代码块、构造函数、异常捕获，很奇怪for循环不支持），阶段包括before、after；

Pointcuts：
    用于指定我们的目标切点，可以称之为切入点；
    示例：[modifier] pointcut [pointcut_name():] PointcutType(Signature)
    - modifier为切点的访问类型，public/protected/static/final等，选填；
    - pointcut是定义切点的关键词，必填；
    - pointcut_name():是切点的名称，填了称之为命名切点，不填表示匿名切点，选填；
    - PointcutType：切点的类型，必填。包括：
        call、
        execution（函数与构造方法）、
        get/set、
        handler（异常捕获）、
        staticinitialization/preinitialization/initialization、
        adviceexecution

        staticinitialization示例：
            - staticinitialization(me.hao..TestBase)：表示TestBase类的static block
        hanlder示例：
            - handler(NullPointerException)：表示catch到NullPointerException的JPoint
    - Signature：函数或者属性的签名，必填。
        函数签名格式：[@annotation] [modifier] returnType packageName.methodName(arguments)
            - @annotation：注解，选填；
            - modifier：访问修饰符，选填；
            - returnType：返回值类型，必填，可用通配符；
            - packageName：包名，必填，可用通配符；
            - methodName：方法名，必填，可用通配符；
            - arguments：形参列表，必填，可用通配符；
            returnType示例：
                - String表示返回字符串；
                - *表示任意类型的返回值；
            packageName示例：
                - java.*.Date：可以表示java.sql.Date，也可以表示java.util.Date；
                - Test*：可以表示TestBase，也可以表示TestDervied；
                - java..*Model+：表示Java任意package中名字以Model结尾的子类，比如TabelModel，TreeModel等；
                - java..*：表示java任意子类；
            methodName示例：
                - 一般是具体的函数名；
            arguments示例：
                - (int, char)：表示参数只有两个，并且第一个参数类型是int，第二个参数类型是char；
                - (String, ..)：表示至少有一个参数。并且第一个参数类型是String，后面参数类型不限。在参数匹配中， ..代表任意参数个数和类型
                - (String ...)：表示不定个数的参数，且类型都是String，这里的...不是通配符，而是Java中代表不定参数的意思
                - (..)：表示参数个数和类型都是任意

        构造方法签名格式：[@annotation] [modifier] packageName.new(arguments)
            - new：函数名固定就是new，表示构造方法；
            - 其他与函数签名一样；

        属性签名格式：[@annotation] [modifier] [FieldType] packageName.fieldName
            - FieldType：变量类型，选填，跟函数的returnType一样，用*表示任意类型
            - 其他与函数签名一样
                属性签名示例：
                    - set(me.hao..Student.name)：表示Student.name赋值的切点；

