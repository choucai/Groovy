package com.william.dream

import groovy.io.FileType

/**
 * Created by william on 16-11-29.
 *
 * GDK(Groovy Development Kit)
 * http://www.groovy-lang.org/gdk.html
 */
class Base03 {


    static void main(String... args) {

        IO操作()

        UsedTool()

    }

    static void IO操作() {
        /*读文件操作()*/
        /*写文件操作()*/
        /*文件树操作()*/
        /*执行外部程序()*/
    }


    static def 读文件操作() {
        File dir = new File('')
        def baseDir = dir.getCanonicalPath()
        File fileScript = new File(baseDir, 'dream.txt')

        // 读取文件打印脚本
        fileScript.eachLine { line -> println(line) }

        println("---------------------------------")

        // 读文件打印内容以及行号
        fileScript.eachLine { line, nb -> println "Line $nb: $line" }

        println("---------------------------------")

        /*def  count = 0,MAXSIZE = 3
        File fileSl = new File(baseDir,'dream.txt')
        fileSl.withReader {
            reader ->
                while (reader.readLine()){
                    if(++count > MAXSIZE) throw new RuntimeException('Dream should only have 3 verses')
                }
        }*/

        //把读到的文件行内容全部存入List列表中
        def list = fileScript.collect { it }
        println(list)

        //把读到的文件行内容全部存入String数组列表中
        def array = fileScript as String[]
        println(array)

        //把读到的文件内容全部转存为byte数组
        byte[] contents = fileScript.bytes
        println(contents)

        //把读到的文件转为InputStream，切记此方式需要手动关闭流
        def is = fileScript.newInputStream()
        // do something
        is.close()

        //把读到的文件以InputStream闭包操作，此方式不需要手动关闭流
        fileScript.withInputStream { stream ->
            // do something
        }


    }

    static def 写文件操作() {
        File fileScript = new File('william.txt')

        //向一个文件以utf-8编码写三行文字
        fileScript.withWriter('utf-8') { writer ->
            writer.writeLine 'Into the ancient pond'
            writer.writeLine 'A frog jumps'
            writer.writeLine 'Water’s sound!'
        }

        //上面的写法可以直接替换为此写法
        fileScript << '''Into the ancient pond
A frog jumps
Water’s sound!'''

        // 直接一byte数组形式写入文件
        // fileScript.bytes = [66, 22, 11]

        //类似上面读操作，可以使用OutputStream进行输出流操作，记得手动关闭
        /*def os = fileScript.newOutputStream()
        // do someting
        os.close()*/

        //类似上面读操作，可以使用OutputStream闭包进行输出流操作，不用手动关闭
        /*fileScript.withOutputStream {stream ->
            // do something
        }*/


    }


    static def 文件树操作() {
        File dir = new File('').getCanonicalFile()

        //遍历所有指定路径下文件名打印
        dir.eachFile { file ->
            println(file.name)
        }

        println('...............遍历所有指定路径下符合正则匹配的文件名打印................')

        //遍历所有指定路径下符合正则匹配的文件名打印
        dir.eachFileMatch(~/.*\.txt/) { file ->
            println file.name
        }

        /*println('..............深度遍历打印名字.................')
        //深度遍历打印名字
        dir.eachFileRecurse {file ->
            println file.name
        }*/

        println('..............深度遍历打印名字，只包含文件类型.................')
        //深度遍历打印名字，只包含文件类型
        dir.eachFileRecurse(FileType.FILES) { file ->
            println file.name
        }

        println('..............允许设置特殊标记规则的遍历操作.................')
        //允许设置特殊标记规则的遍历操作
        /*dir.traverse { file ->
            if (file.directory && file.name=='bin') {
                FileVisitResult.TERMINATE
            } else {
                println file.name
                FileVisitResult.CONTINUE
            }
        }*/
    }


    static void 执行外部程序() {
        def progress = "ls -l".execute()
        println "Found text ${progress.text}"

        println('..............................................................')

        def progresser = "ls -l".execute()
        progresser.in.eachLine {line ->
            println line
        }

    }

    /**
     * 有用的工具类
     */
    static void UsedTool() {

        /*ConfigSlurper是一个配置管理文件读取工具类，类似于Java的*.properties文件*/
        def config = new ConfigSlurper().parse('''
            app.date = new Date()
            app.age = 42
            app {
                name = "Test${42}"
            }
        ''')

        println(config.app.date instanceof Date)
        println(config.app.age == 42)
        println(config.app.name == 'Test42')

        println('..............................................................')

        /*Expando扩展*/
        def expando = new Expando()
        expando.toString = {-> 'John'}
        expando.say = {String s -> "John says:${s}"}

        println(expando as String == 'John')
        println(expando.say('Hi') == 'John says:Hi')
    }

}
