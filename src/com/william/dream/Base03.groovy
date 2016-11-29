package com.william.dream

/**
 * Created by william on 16-11-29.
 *
 * GDK(Groovy Development Kit)
 * http://www.groovy-lang.org/gdk.html
 */
class Base03 {


    static void main(String... args) {

        IO操作()


    }

    static void IO操作() {
        读文件操作()
        写文件操作()
        文件树操作()
        执行外部程序()
    }


    static def 读文件操作() {
        File dir = new File('')
        def baseDir = dir.getCanonicalPath()

        File fileScript = new File(baseDir,'dream.txt')

        // 读取文件打印脚本
        fileScript.eachLine { line -> println(line) }

        println("---------------------------------")

        // 读文件打印内容以及行号
        fileScript.eachLine { line,nb -> println "Line $nb: $line" }

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
        def list = fileScript.collect {it}
        println(list)

        //把读到的文件行内容全部存入String数组列表中
        def array = fileScript as String[]
        println(array)

        //把读到的文件内容全部转存为byte数组
        byte[] contents = fileScript.bytes
        println(contents)

        //把读到的文件转为InputStream，切记此方式需要手动关闭流
        def is = fileScript.newInputStream()
        // do
        is.close()



    }

    static def 写文件操作() {


    }


    static def 文件树操作() {


    }


    static void 执行外部程序() {


    }


}
