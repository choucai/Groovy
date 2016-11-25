package com.william.dream

import com.william.dream.bean.Car
import com.william.dream.bean.User

/**
 * Created by william on 16-11-17.
 */
class FirstDream {


    static void main(String... args) {

        /*注释()*/

        /*标识符()*/

        /*单引号字符串()*/

        /*三重单引号字符串()*/

        /*双引号字符串()*/

        /*多重双引号字符串()*/

        /*斜线字符串()*/

        /*字符Characters()*/

        /*GroovyType()*/

        GroovyOperation()
    }

    private static 注释() {
        /*2 语法基础*/
        /*2-1 注释*/

        /*[#!]特殊的注释，其它的注释和Java一样*/
        /*[#!]这种注释通常是用来给UNIX系统声明允许脚本运行的类型的*/
        println "Hello Groovy"
    }

    private static void 标识符() {
        /*2-3-2 引用标识符*/
        def map = [:]
        /*引用标示符中出现空格也是对的*/
        map."an identifier with a space and double quotes" = "ALLOWED"
        /*引用标示符中出现横线也是对的*/
        map.'with-dash-signs-and-single-quotes' = "ALLOWED"

        assert map."an identifier with a space and double quotes" == "ALLOWED"
        assert map.'with-dash-signs-and-single-quotes' == "ALLOWED"

        /*Groovy的所有字符串都可以当作引用标示符定义*/
        /*如下类型字符串作为引用标识符都是对的*/
        map.'single quote'
        map."double quote"
        map.'''triple single quote'''
        map."""triple double quote"""
        map./slashy string/
        map.$/dollar slashy string/$

        /*稍微特殊的GString，也是对的*/
        def firstname = "Homer"
        map."Simson-${firstname}" = "Homer Simson"
        assert map.'Simson-Homer' == "Homer Simson"
    }


    static void 单引号字符串() {
        def name = 'Test Groovy'
        def body = 'Test $name'

        assert name == 'Test Groovy'
        assert body == 'Test $name'
        assert 'ab' == 'a' + 'b'
    }

    /**
     * TODO 此方法有问题 日后详查资料
     */
    static void 三重单引号字符串() {
        // 三重单引号字符串是java.lang.String类型的，不支持站位符插值操作，可以标示多行字符串
        def aMultilineString = '''line one
line two
line three'''
        println(aMultilineString)

        def strippedFirstNewline = '''\
line one
line two
line three
'''
        println(!strippedFirstNewline.startsWith('\n'))
        println(strippedFirstNewline)

    }

    /**
     * 双引号字符串支持站位插值操作，如果双引号字符串中不包含站位符则是java.lang.String类型的，如果双引号字符串中包含站位符则是groovy.lang.GString类型的
     * 对于插值占位符我们可以用${}或者$来标示，${}用于一般替代字串或者表达式，$主要用于A.B的形式中
     */
    static void 双引号字符串() {
        def name = 'Guillaume'
        def greeting = "Hello ${name}"
        assert greeting.toString() == 'Hello Guillaume'
        println(greeting)

        def sum = "The sum of 2 and 3 equals ${2 + 3}"
        assert sum.toString() == 'The sum of 2 and 3 equals 5'
        println(sum)

        def person = [name: 'Guillaume', age: 36]
        assert "$person.name is $person.age years old" == 'Guillaume is 36 years old'
        println(person)

        /*特别注意，$只对A.B等有效，如果表达式包含括号（像方法调用）、大括号、闭包等符号则是无效的*/
        /*def number = 3.14
        shouldFail(MissingPropertyException) {
            println "$number.toString()"
        }*/
        /*该代码运行抛出groovy.lang.MissingPropertyException异常，因为Groovy认为去寻找number的名为toString的属性，所以异常*/

        /*GString还支持延迟运算，譬如在GString中使用闭包，闭包在调用GString的toString()方法时被延迟执行
        闭包中可以有0或1个参数，若指定一个参数，则参数会被传入一个Writer对象
        我们可以利用这个Writer对象来写入字符，若没有参数，闭包返回值的toString()方法被调用*/

        //无参数闭包
        def sParameterLessClosure = "1 + 2 == ${-> 3}"
        assert sParameterLessClosure == '1 + 2 == 3'
        println(sParameterLessClosure)

        //一个参数闭包
        def sOneParamClosure = "1 + 2 == ${w -> w << 3}"
        assert sOneParamClosure == '1 + 2 == 3'
        println(sOneParamClosure)

        //------------------------------------------------------------------------------

        /*一个普通插值表达式值替换实际是在GString创建的时刻，一个包含闭包的表达式由于延迟运算调运toString()方法，所以会产生一个新的字符串值*/
        def number = 1
        def eagerGString = "value == ${number}"
        def lazyGString = "value == ${-> number}"

        assert eagerGString == "value == 1"
        assert lazyGString == "value == 1"

        number = 2
        assert eagerGString == "value == 1"
        assert lazyGString == "value == 2"

        /*GString和String即使字符串一样他们的HashCode也不会一样，譬如*/
        assert "one: ${1}".hashCode() != "one: 1".hashCode()

        /*由于相同字符串的String与GString的HashCode不同，所以我们一定要避免使用GString作为MAP的key，譬如*/
        def key = "a"
        def m = ["${key}": "letter ${key}"]
        assert m["a"] == null   //由于key的HashCode不同，所以取不到
    }

    /*多重双引号字符串也支持站位插值操作，我们要特别注意在多重双引号字符串中的单引号和双引号转换问题*/

    static void 多重双引号字符串() {
        def name = 'Groovy'
        def template = """
        Dear Mr ${name},

        You're the winner of the lottery!

        Yours sincerly,

        Dave
"""
        assert template.toString().concat('Groovy')
        println(template)
    }

    /*斜线字符串其实和双引号字符串很类似，通常用在正则表达式中*/
    /*特别注意，一个空的斜线字符串会被Groovy解析器解析为一注释*/

    static void 斜线字符串() {
        //普通使用
        def fooPattern = /.*foo.*/
        assert fooPattern == '.*foo.*'
        println(fooPattern)

        //含转义字符使用
        def escapeSlash = /The character \/ is a forward slash/
        assert escapeSlash == 'The character / is a forward slash'
        println(escapeSlash)

        //多行支持
        def multilineSlashy = /one
            two
            three/

        assert multilineSlashy.contains('\n')
        println(multilineSlashy)

        //含站位符使用支持
        def color = 'blue'
        def interpolatedSlashy = /a ${color} car/
        assert interpolatedSlashy == 'a blue car'
        println(interpolatedSlashy)
    }

    /*不像Java，Groovy没有明确的Characters。但是我们可以有如下三种不同的方式来将字符串作为字符处理*/

    static void 字符Characters() {
        char c1 = 'A'
        assert c1 instanceof Character
        println(c1 instanceof Character)

        def c2 = 'B' as char
        assert c2 instanceof Character
        println(c2 instanceof Character)

        def c3 = (char) 'C'
        assert c3 instanceof Character
        println(c3 instanceof Character)
    }

    static void GroovyType() {
        整型()
        浮点型()
        Booleans类型()
        Lists类型()
        Arrays类型()
        Maps类型()
    }

    /*Groovy像Java一样支持如下一些整型，byte、char、short、int、long、java.lang.BigInteger*/

    static def 整型() {
        // primitive types-原始类型-内嵌类型
        byte b = 1
        char c = 2
        short s = 3
        int i = 4
        long l = 5

        // infinite precision - 无限精度
        BigInteger bi = 6

        // 8-进制
        int xInt1 = 077
        println(xInt1 == 63)

        // 16-进制
        int xInt2 = 0x77
        println(xInt2 == 119)

        // 2-进制
        int xInt = 0b10101111
        println(xInt == 175)
    }

    /*Groovy像Java一样支持如下一些浮点型，float、double、java.lang.BigDecimal*/

    static def 浮点型() {
        // primitive types
        float f = 1.234
        double d = 2.345

        // infinite precision
        BigDecimal bd = 3.456

        println(1e3 == 1_000.0)
        println(2E4 == 20_000.0)
        println(3e+1 == 30.0)
        println(4E-2 == 0.04)

    }

    static def Booleans类型() {
        def myBooleanVariable = true
        boolean untypedBooleanVar = false
        def booleanField = true
    }

    /*Groovy同样支持java.util.List类型，在Groovy中同样允许向列表中增加或者删除对象，允许在运行时改变列表的大小，保存在列表中的对象不受类型的限制*/
    /*此外还可以通过超出列表范围的数来索引列表*/

    static def Lists类型() {
        // 使用动态List
        def numbers = [1, 2, 3]
        println numbers instanceof List
        println numbers.size() == 3

        // List中存储任意类型
        def heterogeneous = [1, "a", true]

        // 判断List的默认类型
        def arrayList = [1, 2, 3]
        println arrayList instanceof ArrayList

        // 使用as强转类型
        def linkedList = [2, 3, 4] as LinkedList
        println linkedList instanceof LinkedList

        // 定义指定类型的List
        LinkedList otherLinked = [3, 4, 5]
        println otherLinked instanceof LinkedList

        println("++++++++++++++++++++++++++++++++")

        // 定义List使用
        def letters = ['a', 'b', 'c', 'd']
        // 判断item值
        println letters[0] == 'a'
        println letters[1] == 'b'

        //负数下标则从右向左index
        println letters[-1] == 'd'
        println letters[-2] == 'c'

        println("++++++++++++++++++++++++++++++++")

        //指定item赋值判断
        letters[2] = 'C'
        println letters[2] == 'C'

        //给List追加item
        letters << 'e'
        println letters[4] == 'e'
        println letters[-1] == 'e'

        //获取一段List子集
        println letters[1, 3] == ['b', 'd'] // 含头不含尾
        println letters[2..4] == ['C', 'd', 'e'] // 闭区间

        //多维List支持
        def multi = [[0, 1], [2, 3]]
        println multi[1][0] == 2

    }

    static def Arrays类型() {
        // 定义初始化数组【String】
        String[] arrStr = ['Ananas', 'Banana', 'Kiwi']
        println arrStr instanceof String[]
        println !(arrStr instanceof List)

        println("++++++++++++++++++++++++++++++++")

        //使用def定义初始化int数组
        def numArr = [1, 2, 3] as int[]
        println numArr instanceof int[]
        println numArr.size() == 3

        println("++++++++++++++++++++++++++++++++")

        //声明定义多维数组指定宽度
        def matrix3 = new Integer[3][3]
        println matrix3.size() == 3

        //声明多维数组不指定宽度
        Integer[][] matrix2
        matrix2 = [[1, 2], [3, 4]]
        println matrix2 instanceof Integer[][]

        println("++++++++++++++++++++++++++++++++")

        //数组的元素使用及赋值操作
        String[] names = ['Cédric', 'Guillaume', 'Jochen', 'Paul']
        println names[0] == 'Cédric'

        names[2] = 'Blackdrag'
        println names[2] == 'Blackdrag'

    }

    /*Map是“键-值”对的集合，在Groovy中键key不一定是String，可以是任何对象(实际上Groovy中的Map就是java.util.Linke dHashMap)*/

    static void Maps类型() {

        // 定义一个Map
        def colors = [red: '#FF0000', green: '#00FF00', blue: '#0000FF']

        // 获取一些指定的key的value进行判断
        println(colors['red'] == '#FF0000')
        println(colors.green == '#00FF00')

        println("++++++++++++++++++++++++++++++++")

        //给指定key的对赋值value操作与判断
        colors['pink'] = '#FF00FF'
        colors.yellow = '#FFFF00'
        println colors.pink == '#FF00FF'
        println colors['yellow'] == '#FFFF00'

        println("++++++++++++++++++++++++++++++++")

        //判断Map的类型
        println colors instanceof java.util.LinkedHashMap

        //访问Map中不存在的key为null
        println colors.unknown == null

        //定义key类型为数字的Map
        def numbers = [1: 'one', 2: 'two']
        println numbers[1] == 'one'

        println("++++++++++++++++++++++++++++++++")

        //把一个定义的变量作为Map的key，访问Map的该key是失败的
        def key = 'name'
        def person = [key: 'Guillaume']
        println !person.containsKey('name')
        println person.containsKey('key')

        //把一个定义的变量作为Map的key的正确写法---添加括弧，访问Map的该key是成功的
        person = [(key): 'Guillaume']
        println person.containsKey('name')
        println !person.containsKey('key')
    }

    /*运算符*/
    /*关于Groovy的运算符介绍类似于上面一样，我们重点突出与Java的不同点，相同点自行脑补*/

    static void GroovyOperation() {


        /*Groovy支持**次方运算符*/
        println(2**3 == 8)
        def f = 3
        f **= 2 // 3的平方
        println f == 9


        /*Groovy非运算符*/
        println((!true) == false)
        println((!'foo') == false)
        println((!'') == true)


        /*Groovy支持?.安全占位符，这个运算符主要用于避免空指针异常*/
//        def person = Person.find { it.id == 123 }
//        def name = person?.name
//        assert name == null


        /*Groovy支持.@直接域访问操作符，因为Groovy自动支持属性getter方法，但有时候我们有一个自己写的特殊getter方法，当不想调用这个特殊的getter方法则可以用直接域访问操作符*/
        def user = new User('Bob')
        println(user.name == 'Name:Bob')
        println(user.@name == 'Bob')


        /*Groovy支持.&方法指针操作符，因为闭包可以被作为一个方法的参数，如果想让一个方法作为另一个方法的参数则可以将一个方法当成一个闭包作为另一个方法的参数*/
        def list = ['a', 'b', 'c', 'd']
        // 常规写法
        list.each {
            println(it)
        }
        //方法指针操作符写法
        list.each {this.&printName}

        /*Groovy支持将?:三目运算符简化为二目*/
        String displayName = user.name ? user.name : 'Anonymous'
        displayName = user.name ?: 'Anonymous'


        /*Groovy支持【*.】展开运算符，一个集合使用【展开运算符】可以得到一个元素为原集合各个元素执行后面指定方法所得值的集合*/
        def cars = [
                new Car(make: 'Peugeot', model: '508'),
                null,
                new Car(make: 'Renault', model: 'Clio')]
        assert cars*.make == ['Peugeot', null, 'Renault']
        assert null*.make == null
    }


    String printName(name) {
        println(name)
    }
}
