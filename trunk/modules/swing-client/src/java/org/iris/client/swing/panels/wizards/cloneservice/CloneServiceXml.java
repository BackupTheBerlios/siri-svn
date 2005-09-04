package org.iris.client.swing.panels.wizards.cloneservice;

import java.text.*;

/**
 * Created by IntelliJ IDEA.
 * User: gepo
 * Date: 2004-jun-24
 * Time: 16:14:34
 * To change this template use File | Settings | File Templates.
 */
public class CloneServiceXml
{

    private static final MessageFormat FORMATTER = new MessageFormat(
        "<service type=\"file-file\" id=\"filetest-filetest\" createuniquename=\"true\" usesrelativerootpath=\"false\">\n" +
        "\t\t<infolder>\n" + "\t\t\t<infolderpath>{1}</infolderpath>\n" +
        "\t\t\t<intervallInMilliSeconds>5000</intervallInMilliSeconds>\n" +
        "\t\t\t<skipfirstblankline>false</skipfirstblankline>\n" +
        "\t\t\t<infoldersendchunks sendinfolderchunkson=\"false\">\n" +
        "\t\t\t\t<infolderchunksize>10</infolderchunksize>\n" +
        "\t\t\t\t<infolderchunkthreshold>10</infolderchunkthreshold>\n" + "\t\t\t</infoldersendchunks>\n" +
        "\t\t</infolder>\n" + "\t\t<outfolder>\n" + "\t\t\t<outfolderpath>C:/gepo/mycvsprojects/iris/modules/core-server/services-test/filetest-filetest/out/</outfolderpath>\n" +
        "\t\t</outfolder>\n" + "\t\t<backup backuphandleron=\"true\" archiveron=\"true\">\n" + "\t\t\t<backupfolderpath>C:/gepo/mycvsprojects/iris/modules/core-server/services-test/filetest-filetest/backup/</backupfolderpath>\n" + "\t\t\t<archivefolderpath>C:/gepo/mycvsprojects/iris/modules/core-server/services-test/filetest-filetest/archived/</archivefolderpath>\n" +
        "\t\t</backup>\n" + "\t\t<transform transformon=\"false\"/>\n" + "\t\t<error  errorhandleron=\"true\">\n" + "\t\t\t<errorfolderpath>C:/gepo/mycvsprojects/iris/modules/core-server/services-test/filetest-filetest/error/</errorfolderpath>\n" + "\t\t\t<errorinfofolderpath>C:/gepo/mycvsprojects/iris/modules/core-server/services-test/filetest-filetest/error_info/</errorinfofolderpath>\n" +
        "\t\t\t<sortonresend>false</sortonresend>\n" +
        "\t\t\t<cronintervall>Every 1 minute@0 0 * * * ?</cronintervall>\n" +
        "\t\t\t<errornotification errornotificationon=\"true\">\n" +
        "\t\t\t\t<notifyeveryintervallInMilliSeconds>30000</notifyeveryintervallInMilliSeconds>\n" +
        "\t\t\t\t<numberoferrors>1</numberoferrors>\n" + "\t\t\t\t<emailnotification>\n" + "\t\t\t\t\t<emailonfailure>C:/gepo/mycvsprojects/iris/modules/core-server/services-test/filetest-filetest/template/emailerrornotification.xml</emailonfailure>\n" +
        "\t\t\t\t</emailnotification>\n" + "\t\t\t\t<sms>\n" + "\t\t\t\t\t<smsonfailure>C:/gepo/mycvsprojects/iris/modules/core-server/services-test/filetest-filetest/template/down.xml</smsonfailure>\n" + "\t\t\t\t\t<smsservice>C:/gepo/mycvsprojects/iris/modules/core-server/services-test/filetest-filetest/in/</smsservice>\n" +
        "\t\t\t\t</sms>\n" + "\t\t\t</errornotification>\n" + "\t\t</error>\n" +
        "        <inactivity inactivityOn=\"true\">\n" +
        "            <maxInactivityPeriodInMilliSeconds>120000</maxInactivityPeriodInMilliSeconds>\n" + "            <emailOnInactivity>C:/gepo/mycvsprojects/iris/modules/core-server/services-test/filetest-filetest/template/erroronincativity.xml</emailOnInactivity>\n" +
        "        </inactivity>" + "</service");
    /*"<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"3\" ><b>Time:</b> <code>{0,time,medium}</code>" +
                "&nbsp;&nbsp;<b>Priority:</b> <code>{1}</code>" + "&nbsp;&nbsp;<b>Thread:</b> <code>{2}</code>" +
                "&nbsp;&nbsp;<b>NDC:</b> <code>{3}</code>" + "<br><b>Category:</b> <code>{4}</code>" +
                "<br><b>Location:</b> <code>{5}</code>" + "<br><b>Message:</b>" + "{6}" + "<br><b>Throwable:</b>" +
                "<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"3\" ><pre>{7}<pre></font>"); */


    public static void main(String[] args)
    {
        Object[] testArgs =
                            {
                            new Long(3), "MyDisk"};
        System.out.println("" + FORMATTER.format(testArgs));

    }
}
